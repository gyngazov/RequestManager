package backend.window.main.bar.listener;

import backend.window.main.form.FormData;
import backend.window.main.form.Regions;
import backend.window.main.form.constant.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import window.main.MainForm;

import java.awt.event.ActionListener;

abstract class DataManipulation implements ActionListener {
    private final MainForm mainForm;

    DataManipulation(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    final @Nullable FormData getDisplayData() {
        EntrepreneurshipEnum entrepreneurshipEnum = (EntrepreneurshipEnum) mainForm.getEntrepreneurshipComboBox().getSelectedItem();
        if (entrepreneurshipEnum == null) return null;

        FormData data = new FormData(entrepreneurshipEnum);
        int regionCode = Regions.getRegionCode((String) mainForm.getStateOrProvinceNameComboBox().getSelectedItem());

        switch (entrepreneurshipEnum) {
            case JURIDICAL_PERSON -> {
                data.setOrgINN(mainForm.getOrgINNTextField().getText());
                data.setOGRN(mainForm.getOGRNTextField().getText());
                data.setStateOrProvinceNameLaw(regionCode);
                data.setLocalityNameLaw(mainForm.getLocalityNameTextField().getText());
                data.setStreetAddressLaw(mainForm.getStreetAddressTextField().getText());

                data.setPersonINN(mainForm.getPersonINNTextField().getText());
            }
            case SOLE_PROPRIETOR -> {
                data.setOGRNIP(mainForm.getOGRNTextField().getText());
                data.setStateOrProvinceName(regionCode);
                data.setLocalityName(mainForm.getLocalityNameTextField().getText());
                data.setStreetAddress(mainForm.getStreetAddressTextField().getText());

                data.setOrgINN(mainForm.getPersonINNTextField().getText());
            }
            case NATURAL_PERSON -> {
                data.setStateOrProvinceName(regionCode);
                data.setLocalityName(mainForm.getLocalityNameTextField().getText());
                data.setStreetAddress(mainForm.getStreetAddressTextField().getText());

                data.setOrgINN(mainForm.getPersonINNTextField().getText());
            }
        }

        data.setCommonName(mainForm.getCommonNameTextField().getText());
        data.setDepartment(mainForm.getDepartmentTextField().getText());
        data.setKPP(mainForm.getKPPTextField().getText());
        data.setOrgPhone(mainForm.getOrgPhoneTextField().getText());
        data.setIndex(mainForm.getIndexTextField().getText());
        data.setCountryName(mainForm.getCountryNameTextField().getText());
        data.setHeadLastName(mainForm.getHeadLastNameTextField().getText());
        data.setHeadFirstName(mainForm.getHeadFirstNameTextField().getText());
        data.setHeadMiddleName(mainForm.getHeadMiddleNameTextField().getText());
        data.setHeadPersonINN(mainForm.getHeadPersonINNTextField().getText());
        data.setHeadTitle(mainForm.getHeadTitleTextField().getText());

        data.setIdentificationKindEnum((IdentificationKindEnum) mainForm.getIdentificationKindComboBox().getSelectedItem());
        data.setLastName(mainForm.getLastNameTextField().getText());
        data.setFirstName(mainForm.getFirstNameTextField().getText());
        data.setMiddleName(mainForm.getMiddleNameTextField().getText());
        data.setGenderEnum((GenderEnum) mainForm.getGenderComboBox().getSelectedItem());
        data.setSNILS(mainForm.getSNILSTextField().getText());
        data.setBirthDate(mainForm.getBirthDateTextField().getText());
        data.setEmailAddress(mainForm.getEmailAddressTextField().getText());
        data.setPersonPhone(mainForm.getPersonPhoneTextField().getText());
        data.setTitle(mainForm.getTitleTextField().getText());
        data.setTypeEnum((TypeEnum) mainForm.getTypeComboBox().getSelectedItem());
        data.setCitizenship(mainForm.getCitizenshipTextField().getText());
        data.setIssueDate(mainForm.getIssueDateTextField().getText());
        data.setDivision(mainForm.getDivisionTextField().getText());
        data.setSeries(mainForm.getSeriesTextField().getText());
        data.setNumber(mainForm.getNumberTextField().getText());
        data.setIssueId(mainForm.getIssueIdTextField().getText());

        return data;
    }

    final void displayApplicantData(@NotNull FormData data) {
        switch (data.getEntrepreneurshipEnum()) {
            case JURIDICAL_PERSON -> mainForm.getPersonINNTextField().setText(data.getPersonINN());
            case SOLE_PROPRIETOR, NATURAL_PERSON -> mainForm.getPersonINNTextField().setText(data.getOrgINN());
        }

        mainForm.getIdentificationKindComboBox().setSelectedItem(data.getIdentificationKindEnum());
        mainForm.getLastNameTextField().setText(data.getLastName());
        mainForm.getFirstNameTextField().setText(data.getFirstName());
        mainForm.getMiddleNameTextField().setText(data.getMiddleName());
        mainForm.getGenderComboBox().setSelectedItem(data.getGenderEnum());
        mainForm.getSNILSTextField().setText(data.getSNILS());
        mainForm.getBirthDateTextField().setText(data.getBirthDate());
        mainForm.getEmailAddressTextField().setText(data.getEmailAddress());
        mainForm.getPersonPhoneTextField().setText(data.getPersonPhone());
        mainForm.getTitleTextField().setText(data.getTitle());
        mainForm.getCitizenshipTextField().setText(data.getCitizenship());
        mainForm.getIssueDateTextField().setText(data.getIssueDate());
        mainForm.getSeriesTextField().setText(data.getSeries());
        mainForm.getNumberTextField().setText(data.getNumber());
        mainForm.getIssueIdTextField().setText(data.getIssueId());

        mainForm.getTypeComboBox().setSelectedItem(data.getTypeEnum());
        mainForm.getEntrepreneurshipComboBox().setSelectedItem(data.getEntrepreneurshipEnum());
    }

    final void displayOrganizationData(@NotNull FormData data) {
        switch (data.getEntrepreneurshipEnum()) {
            case JURIDICAL_PERSON -> {
                mainForm.getOrgINNTextField().setText(data.getOrgINN());
                mainForm.getOGRNTextField().setText(data.getOGRN());
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(Regions.getRegionName(data.getStateOrProvinceNameLaw()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityNameLaw());
                mainForm.getStreetAddressTextField().setText(data.getStreetAddressLaw());
            }
            case SOLE_PROPRIETOR -> {
                mainForm.getOGRNTextField().setText(data.getOGRNIP());
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(Regions.getRegionName(data.getStateOrProvinceName()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityName());
                mainForm.getStreetAddressTextField().setText(data.getStreetAddress());
            }
            case NATURAL_PERSON -> {
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(Regions.getRegionName(data.getStateOrProvinceName()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityName());
                mainForm.getStreetAddressTextField().setText(data.getStreetAddress());
            }
        }

        mainForm.getCommonNameTextField().setText(data.getCommonName());
        mainForm.getDepartmentTextField().setText(data.getDepartment());
        mainForm.getKPPTextField().setText(data.getKPP());
        mainForm.getOrgPhoneTextField().setText(data.getOrgPhone());
        mainForm.getIndexTextField().setText(data.getIndex());
        mainForm.getHeadLastNameTextField().setText(data.getHeadLastName());
        mainForm.getHeadFirstNameTextField().setText(data.getHeadFirstName());
        mainForm.getHeadMiddleNameTextField().setText(data.getHeadMiddleName());
        mainForm.getHeadPersonINNTextField().setText(data.getHeadPersonINN());
        mainForm.getHeadTitleTextField().setText(data.getHeadTitle());

        mainForm.getEntrepreneurshipComboBox().setSelectedItem(data.getEntrepreneurshipEnum());
    }

    final void displayData(@NotNull FormData data) {
        displayApplicantData(data);
        displayOrganizationData(data);
    }
}
