package backend.window.main.bar.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONResult;
import backend.iEcp.JSON.JSONView;
import backend.iEcp.POSTRequest;
import backend.reader.Readable;
import com.google.gson.Gson;
import frontend.controlElement.CheckBox;
import frontend.controlElement.RadioButton;
import frontend.window.main.filter.Table;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.*;

public record ImportDocumentIEcp(CheckBox[] documentType, RadioButton[] saveMethod) implements ActionListener {

    private JSONResult @Nullable [] getResponse(Integer requestID, List<String> errorList) {
        try {
            POSTRequest postRequest = new POSTRequest(POSTRequest.RESULT, new Gson().toJson(new JSONView(requestID)));
            if (postRequest.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return new Gson().fromJson(postRequest.getResponse(), JSONResult[].class);
            } else throw new BadRequestException(postRequest.getResponse());
        } catch (SocketTimeoutException e) {
            errorList.add("Время ожидания операции истекло.");
        } catch (IOException e) {
            errorList.add("Ошибка получения данных.");
        } catch (Exception e) {
            errorList.add(e.getMessage());
        }

        return null;
    }

    private void saveDocument(String name, String contentBase64, @NotNull File savePath, List<String> errorList) {
        if (!savePath.exists() && !savePath.mkdirs()) {
            errorList.add("Ошибка при сохранении файла.");
        } else {
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(savePath, name)))) {
                out.write(Base64.getMimeDecoder().decode(contentBase64));
            } catch (IOException e) {
                errorList.add("Ошибка при сохранении файла.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Integer[] requestIDs = Table.getInstance().getSelectedRequestID();
            if (requestIDs == null || requestIDs.length == 0) {
                throw new Exception("Используя фильтр заявок в правой части главного экрана,<br>выберите заявки для выгрузки документов и повторите попытку.");
            }

            File currentDirectory = Readable.getSelectedDir();
            if (currentDirectory != null) {
                Map<Integer, List<String>> notificationMap = new TreeMap<>();

                for (Integer requestID : requestIDs) {
                    List<String> errorList = new ArrayList<>();

                    File savePath = new File(currentDirectory, saveMethod[0].isSelected() ? "" : requestID.toString());
                    JSONResult[] jsonResults = getResponse(requestID, errorList);
                    if (jsonResults != null) {
                        if (documentType[0].isSelected()) {
                            saveDocument(jsonResults[0].getName(), jsonResults[0].getContentBase64(), savePath, errorList);
                        }
                        if (documentType[1].isSelected()) {
                            saveDocument(jsonResults[1].getName(), jsonResults[1].getContentBase64(), savePath, errorList);
                        }
                    }

                    notificationMap.put(requestID, errorList);
                }

                new Notification<Integer>(requestIDs.length).showNotificationDisplay(notificationMap);
            }
        } catch (Exception exception) {
            new MessageDialog.Error("<html><body>" + exception.getMessage() + "</body></html>");
        }
    }
}
