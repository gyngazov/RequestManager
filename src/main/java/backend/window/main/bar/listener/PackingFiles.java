package backend.window.main.bar.listener;

import backend.reader.Readable;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class PackingFiles implements ActionListener {
    private static final String PROCURATION_CODE_NAME = "dD";
    private static final String APPLICATION_CODE_NAME = "zZ";
    private static final String PHOTO_CODE_NAME = "fF";
    private static final String PASSPORT_CODE_NAME = "pP";
    private static final String SNILS_CODE_NAME = "sS";
    private static final String ARCHIVE_START_NAME = "documents";

    private @Nullable File[] getListFiles(@NotNull File currentDirectory, String fileCodeName, String requestID) {
        return currentDirectory.listFiles(((dir, name) -> name.matches("[" + fileCodeName + "]"
                + requestID
                + "\\.[a-zA-z]{2,}")));
    }

    private @Nullable File[] getListFiles(@NotNull File currentDirectory, String fileCodeName) {
        return getListFiles(currentDirectory, fileCodeName, "(?:_\\d+|)");
    }

    private boolean checkCompleteness(@NotNull File currentDirectory) throws Exception {
        File[] files = currentDirectory.listFiles();
        if (files == null || files.length == 0 || Arrays.stream(files).mapToLong(File::length).sum() == 0) {
            throw new Exception("Операция не завершена, т.к. папка \""
                    + currentDirectory.getName()
                    + "\" пуста.");
        }

        File[] passports = getListFiles(currentDirectory, PASSPORT_CODE_NAME);
        if (passports == null || passports.length != 1) {
            throw new Exception("Проверьте наличие электронного образа паспорта.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположен один образ паспорта и повторите попытку.");
        }

        File[] SNILS = getListFiles(currentDirectory, SNILS_CODE_NAME);
        if (SNILS == null || SNILS.length != 1) {
            throw new Exception("Проверьте наличие электронного образа СНИЛС.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположен один образ документа и повторите попытку.");
        }

        File[] procurations = getListFiles(currentDirectory, PROCURATION_CODE_NAME);
        if (procurations != null && procurations.length > 1) {
            throw new Exception("Обнаружено наличие нескольких электронных образов доверенностей.<br>Удалите ненужные файлы и повторите попытку.");
        }

        File[] applications = getListFiles(currentDirectory, APPLICATION_CODE_NAME);
        if (applications == null || applications.length == 0) {
            throw new Exception("Проверьте наличие электронного образа заявления.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одного образа документа и повторите попытку.");
        }

        File[] photos = getListFiles(currentDirectory, PHOTO_CODE_NAME);
        if (photos == null || photos.length == 0) {
            throw new Exception("Проверьте наличие фотографии заявителя.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одной фотографии и повторите попытку.");
        }

        if (applications.length != photos.length) {
            throw new Exception("Общее количество электронных образов заявлений не совпадает с общим количеством фотографий.");
        }

        for (File application : applications) {
            String applicationRequestID = application.getName().replaceAll("[^\\d_]+", "");
            photos = getListFiles(currentDirectory, PHOTO_CODE_NAME, applicationRequestID);
            if (photos == null || photos.length != 1) {
                throw new Exception("Операция не завершена, т.к. электронному образу заявления \""
                        + application.getName()
                        + "\" не найдена фотография.");
            }
        }

        return true;
    }

    private @NotNull String rename(@NotNull File pathname, String filename) {
        return pathname.getName().replaceAll(".+(?=\\.)", filename);
    }

    private void writeFile(@NotNull ZipOutputStream zipOut,
                           BufferedOutputStream bufOut,
                           File file,
                           String filename) throws IOException {
        int data;
        byte[] b = new byte[262144];
        try (BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(file))) {
            zipOut.putNextEntry(new ZipEntry(filename));
            while ((data = bufIn.read(b, 0, b.length)) != -1) {
                bufOut.write(b, 0, data);
            }
            bufOut.flush();
            zipOut.closeEntry();
        }
    }

    private void pack(File currentDirectory) {
        Map<String, String> notificationMap = new TreeMap<>();

        File[] passports = getListFiles(currentDirectory, PASSPORT_CODE_NAME);
        File[] SNILS = getListFiles(currentDirectory, SNILS_CODE_NAME);
        File[] procurations = getListFiles(currentDirectory, PROCURATION_CODE_NAME);
        File[] applications = getListFiles(currentDirectory, APPLICATION_CODE_NAME);

        for (File application : applications) {
            String applicationRequestID = application.getName().replaceAll("[^\\d_]+", "");
            File[] photos = getListFiles(currentDirectory, PHOTO_CODE_NAME, applicationRequestID);
            File archive = new File(currentDirectory, ARCHIVE_START_NAME + applicationRequestID + ".zip");

            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(archive, false), Charset.forName("cp866"));
                 BufferedOutputStream bufOut = new BufferedOutputStream(zipOut)) {

                writeFile(zipOut, bufOut, passports[0], rename(passports[0], "Паспорт"));
                writeFile(zipOut, bufOut, SNILS[0], rename(SNILS[0], "СНИЛС"));
                if (procurations != null && procurations.length == 1) {
                    writeFile(zipOut, bufOut, procurations[0], rename(procurations[0], "Доверенность"));
                }
                writeFile(zipOut, bufOut, application, rename(application, "Заявление"));
                writeFile(zipOut, bufOut, photos[0], rename(photos[0], "Фото заявителя"));
            } catch (IOException e) {
                if (archive.delete()) {
                    notificationMap.put(archive.getName(), "Ошибка при записи файлов.");
                } else {
                    notificationMap.put(archive.getName(), "Ошибка при запаковке файлов.");
                }
            }
        }

        new Notification<String>(applications.length).showNotificationDisplay(notificationMap);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {
            try {
                if (checkCompleteness(currentDirectory)) {
                    pack(currentDirectory);
                }
            } catch (Exception exception) {
                new MessageDialog.Error("<html><body>" + exception.getMessage() + "</body></html>");
            }
        }
    }
}
