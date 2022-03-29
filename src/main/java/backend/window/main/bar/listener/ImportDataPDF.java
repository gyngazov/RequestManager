package backend.window.main.bar.listener;

import backend.util.PDFReader;
import backend.window.main.form.FormData;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.settings.Options;
import frontend.window.main.MainForm;
import frontend.window.optionDialog.MessageDialog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.IOException;

public final class ImportDataPDF extends DataManipulation {
    private final MainForm mainForm;

    public ImportDataPDF(MainForm mainForm) {
        super(mainForm);
        this.mainForm = mainForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser file = new JFileChooser(Options.read().getCurrentDirectory());
        file.setFileFilter(new FileNameExtensionFilter("Выписка из ЕГРЮЛ/ЕГРИП (*.pdf)", "pdf"));
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (file.showOpenDialog(mainForm) == JFileChooser.APPROVE_OPTION) {
            try {
                PDFReader reader = new PDFReader(file.getSelectedFile());
                final EntrepreneurshipEnum entrepreneurshipEnumPDF = reader.getEntrepreneurshipConstant();
                FormData data = getDisplayData(false);
                if (data == null) {
                    new MessageDialog.Error("В карточке организации укажите её форму - \"" + entrepreneurshipEnumPDF.getTitle() + "\".");
                } else {
                    final EntrepreneurshipEnum entrepreneurshipEnumDisplay = data.getEntrepreneurshipEnum();
                    if (entrepreneurshipEnumDisplay != entrepreneurshipEnumPDF) {
                        new MessageDialog.Error("В карточке организации укажите её форму - \"" + entrepreneurshipEnumPDF.getTitle() + "\".");
                    } else {
                        switch (entrepreneurshipEnumPDF) {
                            case JURIDICAL_PERSON -> {
                                data.setCommonName(reader.getCommonName());
                                data.setKPP(reader.getKPP());
                                data.setOrgINN(reader.getOrgINN());
                                data.setOGRN(reader.getOGRN());
                                data.setIndex(reader.getIndex());
                                data.setStateOrProvinceNameLaw(reader.getStateOrProvinceNameLaw());
                                data.setLocalityNameLaw(reader.getLocalityNameLaw());
                                data.setStreetAddressLaw(reader.getStreetAddressLaw());
                                data.setHeadLastName(reader.getHeadLastName());
                                data.setHeadFirstName(reader.getHeadFirstName());
                                data.setHeadMiddleName(reader.getHeadMiddleName());
                                data.setHeadPersonINN(reader.getHeadPersonINN());
                                data.setHeadTitle(reader.getHeadTitle());
                            }
                            case SOLE_PROPRIETOR -> {
                                data.setOrgINN(reader.getOrgINN());
                                data.setOGRNIP(reader.getOGRNIP());

                                data.setLastName(reader.getLastName());
                                data.setFirstName(reader.getFirstName());
                                data.setMiddleName(reader.getMiddleName());
                            }
                        }

                        displayData(data);

                        if (reader.isDateToday()) {
                            new MessageDialog.Info("Запрос успешно обработан!");
                        } else {
                            new MessageDialog.Warning("<html><body>Запрос успешно обработан!<br>Для работы с данными используйте актуальные сведения из ЕГРЮЛ/ЕГРИП!</body></html>");
                        }
                    }
                }
            } catch (IOException message) {
                new MessageDialog.Error("Ошибка получения данных, попробуйте повторить запрос позже.");
            } catch (Exception exception) {
                new MessageDialog.Error(exception.getMessage());
            }
        }
    }
}
