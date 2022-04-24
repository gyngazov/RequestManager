package backend.window.main.bar.listener;

import backend.reader.extract.ExtractFTS;
import backend.reader.Readable;
import backend.reader.extract.URIE;
import backend.reader.extract.USRLE;
import backend.window.main.form.FormData;
import frontend.window.optionDialog.MessageDialog;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public final class ImportDataPDF extends DataConverter implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        File selectedFile = Readable.getSelectedFile(new FileNameExtensionFilter("Выписка из ЕГРЮЛ/ЕГРИП (*.pdf)", "pdf"));
        if (selectedFile != null) {
            try {
                ExtractFTS extractFTS = ExtractFTS.of(Readable.of(selectedFile));
                FormData data = getDisplayData(false);
                if (data == null || data.getEntrepreneurshipEnum() != extractFTS.getEntrepreneurshipEnum()) {
                    new MessageDialog.Error("В карточке организации укажите её форму - \"" + extractFTS.getEntrepreneurshipEnum() + "\".");
                } else {
                    switch (extractFTS.getEntrepreneurshipEnum()) {
                        case JURIDICAL_PERSON -> {
                            data.setCommonName(((USRLE) extractFTS).getCommonName());
                            data.setKPP(((USRLE) extractFTS).getKPP());
                            data.setOrgINN(((USRLE) extractFTS).getOrgINN());
                            data.setOGRN(((USRLE) extractFTS).getOGRN());
                            data.setIndex(((USRLE) extractFTS).getIndex());
                            data.setStateOrProvinceNameLaw(((USRLE) extractFTS).getStateOrProvinceNameLaw());
                            data.setLocalityNameLaw(((USRLE) extractFTS).getLocalityNameLaw());
                            data.setStreetAddressLaw(((USRLE) extractFTS).getStreetAddressLaw());
                            data.setHeadLastName(((USRLE) extractFTS).getHeadLastName());
                            data.setHeadFirstName(((USRLE) extractFTS).getHeadFirstName());
                            data.setHeadMiddleName(((USRLE) extractFTS).getHeadMiddleName());
                            data.setHeadPersonINN(((USRLE) extractFTS).getHeadPersonINN());
                            data.setHeadTitle(((USRLE) extractFTS).getHeadTitle());
                        }
                        case SOLE_PROPRIETOR -> {
                            data.setOrgINN(((URIE) extractFTS).getOrgINN());
                            data.setOGRNIP(((URIE) extractFTS).getOGRNIP());

                            data.setLastName(((URIE) extractFTS).getLastName());
                            data.setFirstName(((URIE) extractFTS).getFirstName());
                            data.setMiddleName(((URIE) extractFTS).getMiddleName());
                        }
                    }

                    displayData(data);

                    if (extractFTS.isDateToday()) {
                        new MessageDialog.Info("Запрос успешно обработан!");
                    } else {
                        new MessageDialog.Warning("<html><body>Запрос успешно обработан!<br>Для работы с данными используйте актуальные сведения из ЕГРЮЛ/ЕГРИП!</body></html>");
                    }
                }
            } catch (IOException message) {
                new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
            }
        }
    }
}
