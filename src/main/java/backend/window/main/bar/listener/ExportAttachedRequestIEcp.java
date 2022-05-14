package backend.window.main.bar.listener;

import backend.iEcp.JSON.JSONAttachFile;
import backend.iEcp.POSTRequest;
import backend.reader.Readable;
import backend.reader.Request;
import backend.window.main.form.FormData;
import com.google.gson.Gson;
import frontend.window.main.filter.Table;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

public record ExportAttachedRequestIEcp() implements ActionListener {

    private @NotNull List<File> searchRequest(@NotNull File pathname) {
        List<File> list = new ArrayList<>();

        File[] files = pathname.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchRequest(file));
                } else if (file.getName().endsWith(".req")) {
                    list.add(file);
                }
            }
        }

        return list;
    }

    private boolean checkCompleteness(Integer[] requestIDs, List<File> listRequest) throws Exception {
        if (requestIDs == null || requestIDs.length == 0) {
            throw new Exception("Используя фильтр заявок в правой части главного экрана,<br>выберите заявки для отправки запросов и повторите попытку.");
        }

        if (listRequest == null || listRequest.size() == 0) {
            throw new Exception("Не удалось найти запросы.");
        }

        if (requestIDs.length != listRequest.size()) {
            throw new Exception("Количество запросов не совпадает с количеством выбранных заявок.");
        }

        return true;
    }

    private boolean compareField(@Nullable Object request,
                                 @Nullable Object data,
                                 @Nullable String filename,
                                 @Nullable Integer requestID,
                                 @Nullable Map<Integer, List<String>> notificationMap) {
        if (Objects.equals(request, data)) {
            return true;
        } else {
            if (requestID != null && notificationMap != null) {
                List<String> errorList = Objects.requireNonNullElse(notificationMap.get(requestID), new ArrayList<>());
                errorList.add(filename == null || filename.isBlank() ? "unnamed" : filename + ": \"" + request + "\" ≠ \"" + data + "\"");
                notificationMap.put(requestID, errorList);
            }
            return false;
        }
    }

    public boolean compareFields(@NotNull Request request,
                                 @NotNull FormData data,
                                 @Nullable Integer requestID,
                                 @Nullable Map<Integer, List<String>> notificationMap) {
        boolean result = false;

        if (compareField(request.getEntrepreneurshipEnum(), data.getEntrepreneurshipEnum(), request.getFilename(), requestID, notificationMap)) {
            switch (request.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> result = compareField(request.getCommonName(), data.getCommonName(), request.getFilename(), requestID, notificationMap)
                                & compareField(request.getOrgINN(), data.getOrgINN(), request.getFilename(), requestID, notificationMap)
                                & compareField(request.getStateOrProvinceName(), data.getStateOrProvinceNameLaw(), request.getFilename(), requestID, notificationMap)
                                & compareField(request.getLocalityName(), data.getLocalityNameLaw(), request.getFilename(), requestID, notificationMap)
                                & compareField(request.getStreetAddress(), data.getStreetAddressLaw(), request.getFilename(), requestID, notificationMap)
                                & compareField(request.getPersonINN(), data.getPersonINN(), request.getFilename(), requestID, notificationMap);
                case SOLE_PROPRIETOR, NATURAL_PERSON -> result = compareField(request.getCommonName(),
                        (data.getLastName() + " " + data.getFirstName() + " " + data.getMiddleName()).strip(), request.getFilename(), requestID, notificationMap)
                        & compareField(request.getOrgINN(), null, request.getFilename(), requestID, notificationMap)
                        & compareField(request.getStateOrProvinceName(), data.getStateOrProvinceName(), request.getFilename(), requestID, notificationMap)
                        & compareField(request.getLocalityName(), data.getLocalityName(), request.getFilename(), requestID, notificationMap)
                        & compareField(request.getStreetAddress(), data.getStreetAddress(), request.getFilename(), requestID, notificationMap)
                        & compareField(request.getPersonINN(), data.getOrgINN(), request.getFilename(), requestID, notificationMap);
            }

            result &= compareField(request.getOrganizationName(), data.getCommonName(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getOrganizationalUnitName(), data.getDepartment(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getOGRN(), data.getOGRN(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getOGRNIP(), data.getOGRNIP(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getIdentificationKind(), data.getIdentificationKindEnum().getCode(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getLastName(), data.getLastName(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getGivenName(), data.getFirstName() + " " + data.getMiddleName(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getSNILS(), data.getSNILS(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getEmailAddress(), data.getEmailAddress(), request.getFilename(), requestID, notificationMap)
                    & compareField(request.getTitle(), data.getTitle(), request.getFilename(), requestID, notificationMap);
        }

        return result;
    }

    private void sendRequests(Integer @NotNull [] requestIDs, List<File> listRequest) {
        Map<Integer, List<String>> notificationMap = new TreeMap<>();
        List<String> publicKeyList = new ArrayList<>();

        for (int i = 0; i < requestIDs.length; i++) {
            List<String> errorList = new ArrayList<>();
            File requestFile = listRequest.get(i);
            try {
                FormData data = FormData.generateOnRequestID(requestIDs[i]);
                Request request = new Request(requestFile);

                if (publicKeyList.contains(request.getPublicKey())) {
                    errorList.add(requestFile.getName() + ": Неуникальный открытый ключ");
                    notificationMap.put(requestIDs[i], errorList);
                } else {
                    publicKeyList.add(request.getPublicKey());

                    if (compareFields(request, data, requestIDs[i], notificationMap)) {
                        String json = new Gson().toJson(new JSONAttachFile(requestFile.getName(),
                                Base64.getMimeEncoder().encodeToString(request.getSrc()), 0, requestIDs[i]));
                        POSTRequest postRequest = new POSTRequest(POSTRequest.ATTACH_FILE, json);
                        if (postRequest.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                            errorList.add(postRequest.getResponse());
                            notificationMap.put(requestIDs[i], errorList);
                        }
                    }
                }
            } catch (Exception e) {
                errorList.add(requestFile.getName() + ": Ошибка получения данных");
                notificationMap.put(requestIDs[i], errorList);
            }
        }

        new Notification<Integer>(requestIDs.length).showNotificationDisplay(notificationMap);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {
            try {
                List<File> listRequest = searchRequest(currentDirectory);
                Integer[] requestIDs = Table.getInstance().getSelectedRequestID();
                if (checkCompleteness(requestIDs, listRequest)) {
                    sendRequests(requestIDs, listRequest);
                }
            } catch (Exception exception) {
                new MessageDialog.Error("<html><body>" + exception.getMessage() + "</body></html>");
            }
        }
    }
}
