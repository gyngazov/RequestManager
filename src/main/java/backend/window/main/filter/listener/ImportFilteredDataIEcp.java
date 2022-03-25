package backend.window.main.filter.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONFilter;
import backend.iEcp.POSTRequest;
import backend.window.main.filter.FilterData;
import backend.window.main.filter.TableModelIEcp;
import backend.window.main.filter.constant.StatusEnum;
import backend.window.main.form.FormData;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import window.main.filter.Options;
import window.optionDialog.MessageDialog;

import javax.net.ssl.HttpsURLConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public record ImportFilteredDataIEcp(@NotNull Options options) implements ActionListener {

    private boolean selectedRequestId() {
        return options.getRequestIdTextField().getText().length() > 0;
    }

    private @NotNull FilterData getFilterData() {
        FilterData data = new FilterData();
        data.setCommonName(options.getCommonNameTextField().getText());
        data.setOrgINN(options.getOrgINNTextField().getText());
        data.setLastName(options.getLastNameTextField().getText());
        data.setSNILS(options.getSNILSTextField().getText());
        data.setCreationDate(options.getCreationDateTextField().getText());
        data.setStatusId((StatusEnum) Objects.requireNonNullElse(options.getStatusComboBox().getSelectedItem(), StatusEnum.WITHOUT_STATUS));
        return data;
    }

    private String generateJSON(FilterData data) {
        return new Gson().toJson(new JSONFilter(data), JSONFilter.class);
    }

    private @NotNull ArrayList<Object> generateTableRow(@NotNull FormData data) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(data.getRequestId());
        row.add(data.getCommonName());
        row.add(data.getOrgINN());
        row.add(data.getLastName());
        row.add(data.getSNILS());
        row.add(data.getCreateDate());
        row.add(data.getStatusEnum().toString());
        row.add(data.getComment());
        return row;
    }

    private void setData(@NotNull ArrayList<ArrayList<Object>> tabularData) {
        tabularData.sort(Comparator.comparingInt(o -> (Integer) o.get(0)));
        TableModelIEcp dataModel = options.getTable().getDataModel();
        dataModel.setDataArrayList(tabularData, options.getTable().getColumnNames());
        changeNumberOfFoundRequestId(dataModel);
    }

    private void changeNumberOfFoundRequestId(@NotNull TableModelIEcp dataModel) {
        options.getNumberOfFoundRequestIdLabel().setText(String.valueOf(dataModel.getRowCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ArrayList<Object>> tabularData = new ArrayList<>();
        try {
            if (selectedRequestId()) {
                String[] requestsId = options.getRequestIdTextField().getText().split("[ ,;]+");
                for (String requestId : requestsId) {
                    FormData data = FormData.generateOnRequestId(requestId);
                    tabularData.add(generateTableRow(data));
                }
                setData(tabularData);
            } else {
                POSTRequest request = new POSTRequest(POSTRequest.LIST_REQUEST, generateJSON(getFilterData()));
                if (request.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    JsonArray array = JsonParser.parseString(request.getResponse()).getAsJsonObject().getAsJsonArray("info");
                    for (JsonElement element : array) {
                        FormData data = new Gson().fromJson(element, FormData.class);
                        tabularData.add(generateTableRow(data));
                    }
                    setData(tabularData);
                }
            }
        } catch (NumberFormatException exception) {
            new MessageDialog.Error("Проверьте правильность написания номера заявки.");
        } catch (BadRequestException exception) {
            new MessageDialog.Error(exception.getMessage());
        } catch (SocketTimeoutException exception) {
            new MessageDialog.Error("Время ожидания операции истекло, попробуйте повторить запрос позже.");
        } catch (IOException exception) {
            new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
        } catch (Exception exception) {
            new MessageDialog.Error(exception.getMessage());
        }
    }
}
