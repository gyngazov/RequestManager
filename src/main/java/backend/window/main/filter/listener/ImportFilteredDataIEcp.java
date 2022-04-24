package backend.window.main.filter.listener;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONFilter;
import backend.iEcp.POSTRequest;
import backend.window.main.bar.listener.DataConverter;
import backend.window.main.filter.FilterData;
import backend.window.main.filter.TableModelIEcp;
import backend.window.main.filter.constant.StatusEnum;
import backend.window.main.form.FormData;
import com.google.gson.*;
import frontend.window.main.filter.Table;
import org.jetbrains.annotations.NotNull;
import frontend.window.main.filter.FilterOptions;
import frontend.window.optionDialog.MessageDialog;

import javax.net.ssl.HttpsURLConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public record ImportFilteredDataIEcp(@NotNull FilterOptions filterOptions) implements ActionListener {

    private @NotNull FilterData getFilterData() {
        FilterData data = new FilterData();
        data.setCommonName(filterOptions.getCommonNameTextField().getText());
        data.setOrgINN(filterOptions.getOrgINNTextField().getText());
        data.setLastName(filterOptions.getLastNameTextField().getText());
        data.setSNILS(filterOptions.getSNILSTextField().getText());
        data.setCreationDate(filterOptions.getCreationDateTextField().getText());
        data.setStatusId((StatusEnum) Objects.requireNonNullElse(filterOptions.getStatusComboBox().getSelectedItem(), StatusEnum.WITHOUT_STATUS));
        return data;
    }

    private String generateJSON(FilterData data) {
        return new Gson().toJson(new JSONFilter(data), JSONFilter.class);
    }

    private @NotNull ArrayList<Object> generateTableRow(@NotNull FormData data) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(data.getRequestID());
        row.add(data.getCommonName());
        row.add(data.getOrgINN());
        row.add(data.getLastName());
        row.add(data.getSNILS());
        row.add(data.getCreateDate());
        row.add(data.getStatusEnum().toString());
        row.add(data.getComment());
        return row;
    }

    private void filterByParameters(ArrayList<ArrayList<Object>> tabularData) throws IOException {
        POSTRequest request = new POSTRequest(POSTRequest.LIST_REQUEST, generateJSON(getFilterData()));
        if (request.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            JsonArray array = JsonParser.parseString(request.getResponse()).getAsJsonObject().getAsJsonArray("info");
            for (JsonElement element : array) {
                FormData data = new Gson().fromJson(element, FormData.class);
                tabularData.add(generateTableRow(data));
            }
        } else {
            throw new BadRequestException(request.getResponse());
        }
    }

    private void filterByRequestID(ArrayList<ArrayList<Object>> tabularData, @NotNull String requestIDString) throws IOException {
        int[] requestIDs = Arrays.stream(requestIDString.split("[^\\d]+"))
                .mapToInt(Integer::parseInt)
                .filter(requestID -> requestID >= POSTRequest.MIN_REQUEST_ID)
                .distinct()
                .toArray();
        for (int ID : requestIDs) {
            FormData data = FormData.generateOnRequestID(ID);
            tabularData.add(generateTableRow(data));
        }
    }

    private void setData(@NotNull ArrayList<ArrayList<Object>> tabularData) {
        tabularData.sort(Comparator.comparingInt(o -> (Integer) o.get(0)));
        TableModelIEcp dataModel = (TableModelIEcp) Table.getInstance().getModel();
        dataModel.setDataArrayList(tabularData);

        filterOptions.getFoundRequestIDLabel().setText(String.valueOf(dataModel.getRowCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String requestIDString = filterOptions.getRequestIDTextField().getText();
            ArrayList<ArrayList<Object>> tabularData = new ArrayList<>();
            if (requestIDString == null || requestIDString.isBlank()) {
                filterByParameters(tabularData);
            } else {
                filterByRequestID(tabularData, requestIDString);
            }
            setData(tabularData);
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
