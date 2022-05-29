package backend.window.main.bar.listener;

import backend.reader.Certificate;
import backend.reader.PDFFileReader;
import backend.reader.Readable;
import backend.reader.Request;
import backend.reader.extract.ExtractFTS;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class LinkingDocument implements ActionListener {

    private List<File> walk(Path rootDir) throws IOException {
        try (Stream<Path> entries = Files.walk(rootDir)) {
            return entries.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toList();
        }
    }

    private List<File> selectFiles(@NotNull List<File> entries, String extension) {
        return entries.stream()
                .filter(path -> path.getName().toLowerCase().endsWith(extension.toLowerCase()))
                .toList();
    }

    private @NotNull Map<String, File> createMap(@NotNull List<File> entries) {
        Map<String, File> map = new HashMap<>();

        entries.forEach(path -> {
            String[] extension = path.getName().split("\\.");
            String publicKey = null;
            switch (extension[extension.length - 1].toLowerCase()) {
                case "req" -> {
                    try {
                        publicKey = new Request(path).getPublicKey().substring(1);
                    } catch (Exception ignored) {}
                }
                case "cer" -> {
                    try {
                        publicKey = new Certificate(path).getPublicKey();
                        if (publicKey != null) {
                            publicKey = publicKey.substring(1);
                        }
                    } catch (Exception ignored) {}
                }
                case "pdf" -> {
                    Readable readable = new PDFFileReader(path);
                    try {
                        publicKey = ExtractFTS.getValue(ExtractFTS.formatText(readable.read()),
                                "(?<=Открытый ключ Алгоритм подписи ГОСТ Р 34\\.10-2012 с ключом 256 ).*?(?= Использование ключа)");
                        if (publicKey != null) {
                            publicKey = publicKey.replaceAll("\\s+", "").substring(4).toLowerCase();
                        }
                    } catch (IOException ignored) {}
                }
            }

            if (publicKey != null && !publicKey.isBlank()) {
                map.put(publicKey, path);
            }
        });

        return map;
    }

    private void moveFile(@NotNull File start, File finish, List<String> errorList) {
        if (!start.renameTo(finish)) {
            errorList.add("Ошибка при перемещении файла \"" + start.getName() + "\".");
        }
    }

    public void link(@NotNull File currentDirectory) throws Exception {
        List<File> entries = walk(currentDirectory.toPath());

        Map<String, File> requestMap = createMap(selectFiles(entries, ".req"));
        Map<String, File> certificateMap = createMap(selectFiles(entries, ".cer"));
        Map<String, File> receiptMap = createMap(selectFiles(entries, ".pdf"));

        int numberRequests = requestMap.size();
        int numberCertificates = certificateMap.size();
        int numberReceipts = receiptMap.size();

        if (numberRequests == numberCertificates && numberRequests == numberReceipts) {
            Map<String, List<String>> notificationMap = new TreeMap<>();

            for (Map.Entry<String, File> entry : requestMap.entrySet()) {
                List<String> errorList = new ArrayList<>();

                String requestPublicKey = entry.getKey();
                File pathToRequest = entry.getValue();

                File pathToCertificate = certificateMap.get(requestPublicKey);
                if (pathToCertificate == null) {
                    errorList.add("Не найден соответствующий сертификат.");
                } else {
                    File newPathToCertificate = new File(pathToRequest.getParentFile(), pathToCertificate.getName());
                    moveFile(pathToCertificate, newPathToCertificate, errorList);
                }

                File pathToReceipt = receiptMap.get(requestPublicKey);
                if (pathToReceipt == null) {
                    errorList.add("Не найден соответствующий КСКПЭП на бумажном носителе.");
                } else {
                    File newPathToReceipt = new File(pathToRequest.getParentFile(), pathToReceipt.getName());
                    moveFile(pathToReceipt, newPathToReceipt, errorList);
                }

                notificationMap.put(pathToRequest.getName(), errorList);
            }

            new Notification<String>(numberRequests).showNotificationDisplay(notificationMap);
        } else throw new Exception("Общее количество уникальных запросов ("
                + numberRequests + " шт.), сертификатов ("
                + numberCertificates + " шт.) и КСКПЭП на б.н. ("
                + numberReceipts + " шт.) различно.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {
            try {
                link(currentDirectory);
            } catch (IOException exception) {
                new MessageDialog.Error("Ошибка при попытке доступа к указанной папке.");
            } catch (Exception exception) {
                new MessageDialog.Error(exception.getMessage());
            }
        }
    }
}
