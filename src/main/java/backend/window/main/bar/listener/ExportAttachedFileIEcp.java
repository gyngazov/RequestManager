package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONAttachFile;
import backend.iEcp.POSTRequest;
import backend.reader.Readable;
import com.google.gson.Gson;
import frontend.window.main.filter.Table;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

public record ExportAttachedFileIEcp() implements ActionListener {

    private @Nullable File[] getListFiles(@NotNull File currentDirectory, String regex) {
        return currentDirectory.listFiles(((dir, name) -> name.matches(regex)));
    }

    private boolean checkCompleteness(@NotNull File currentDirectory) throws Exception {
        File[] files = currentDirectory.listFiles();
        if (files == null || files.length == 0 || Arrays.stream(files).mapToLong(File::length).sum() == 0) {
            throw new Exception("Операция не завершена, т.к. папка \""
                    + currentDirectory.getName()
                    + "\" пуста.");
        }

        File[] listZip = getListFiles(currentDirectory, "documents(?:_\\d+|)\\.zip");
        if (listZip == null || listZip.length == 0) {
            throw new Exception("Проверьте наличие пакета документов.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одного пакета и повторите попытку.");
        }

        File[] listSig = getListFiles(currentDirectory, "documents(?:_\\d+|)\\.zip.sig");
        if (listSig == null || listSig.length == 0) {
            throw new Exception("Проверьте наличие открепленной ЭП пакета документов.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одной открепленной ЭП пакета и повторите попытку.");
        }

        if (listZip.length != listSig.length) {
            throw new Exception("Общее количество пакетов документов не совпадает с общим количеством открепленной ЭП.");
        }

        for (File archive : listZip) {
            if (!new File(archive.getAbsolutePath() + ".sig").exists()) {
                throw new Exception("Операция не завершена, т.к. пакету документов \""
                        + archive.getName()
                        + "\" не найдена открепленная ЭП.");
            }
        }

        return true;
    }

    private String encode(File filename) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            return Base64.getMimeEncoder().encodeToString(inputStream.readAllBytes());
        }
    }

    private void sendPackageDocuments(@NotNull File currentDirectory,
                                      Integer requestID,
                                      String extension) throws BadRequestException, IOException {
        File file = new File(currentDirectory.getAbsoluteFile(), "documents_" + requestID + extension);
        if (!file.exists()) {
            file = new File(currentDirectory.getAbsoluteFile(), "documents" + extension);
            if (!file.exists()) {
                throw new FileNotFoundException("Не удалось найти пакет документов.");
            }
        }
        String json = new Gson().toJson(new JSONAttachFile("documents" + extension,
                encode(file),
                ".zip".equals(extension) ? 8 : 9,
                requestID));
        POSTRequest request = new POSTRequest(POSTRequest.ATTACH_FILE, json);
        if (request.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new BadRequestException(request.getResponse());
        }
    }

    private void sendPackagesDocuments(@NotNull File currentDirectory) throws Exception {
        Integer[] requestIDs = Table.getInstance().getSelectedRequestID();
        if (requestIDs == null || requestIDs.length == 0) {
            throw new Exception("Используя фильтр заявок в нижней части экрана,<br>выберите заявки для отправки документов и повторите попытку.");
        }

        Map<Integer, String> notificationMap = new TreeMap<>();

        for (Integer requestID : requestIDs) {
            try {
                sendPackageDocuments(currentDirectory, requestID, ".zip");
                sendPackageDocuments(currentDirectory, requestID, ".zip.sig");
            } catch (FileNotFoundException | BadRequestException e) {
                notificationMap.put(requestID, e.getMessage());
            } catch (SocketTimeoutException exception) {
                notificationMap.put(requestID, "Время ожидания операции истекло, попробуйте повторить запрос позже.");
            } catch (IOException exception) {
                notificationMap.put(requestID, "Ошибка получения данных, попробуйте повторить запрос позже.");
            } catch (Exception exception) {
                notificationMap.put(requestID, exception.getMessage());
            }
        }

        new Notification<Integer>(requestIDs.length).showNotificationDisplay(notificationMap);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {
            try {
                if (checkCompleteness(currentDirectory)) {
                    sendPackagesDocuments(currentDirectory);
                }
            } catch (Exception exception) {
                new MessageDialog.Error("<html><body>" + exception.getMessage() + "</body></html>");
            }
        }
    }
}
