package backend.window.main.bar.listener;

import backend.window.main.form.FormData;
import backend.window.main.form.Region;
import backend.window.main.form.constant.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import frontend.window.main.MainForm;

public abstract class DataConverter {
    private final MainForm mainForm;
    private final Region region;

    public DataConverter() {
        mainForm = MainForm.getInstance();
        region = Region.getInstance();
    }

    public final @Nullable FormData getDisplayData(boolean verifiable) {
        EntrepreneurshipEnum entrepreneurshipEnum = (EntrepreneurshipEnum) mainForm.getEntrepreneurshipComboBox().getSelectedItem();
        if (entrepreneurshipEnum != null) {
            FormData data = new FormData(entrepreneurshipEnum, verifiable);
            int regionCode = region.getRegionCode((String) mainForm.getStateOrProvinceNameComboBox().getSelectedItem());

            switch (entrepreneurshipEnum) {
                case JURIDICAL_PERSON -> {
                    data.setCommonName(mainForm.getCommonNameTextField().getText());
                    data.setDepartment(mainForm.getDepartmentTextField().getText());
                    data.setKPP(mainForm.getKPPTextField().getText());
                    data.setOrgINN(mainForm.getOrgINNTextField().getText());
                    data.setOGRN(mainForm.getOGRNTextField().getText());
                    data.setOrgPhone(mainForm.getOrgPhoneTextField().getText());
                    data.setIndex(mainForm.getIndexTextField().getText());
                    data.setStateOrProvinceNameLaw(regionCode);
                    data.setLocalityNameLaw(mainForm.getLocalityNameTextField().getText());
                    data.setStreetAddressLaw(mainForm.getStreetAddressTextField().getText());
                    data.setHeadLastName(mainForm.getHeadLastNameTextField().getText());
                    data.setHeadFirstName(mainForm.getHeadFirstNameTextField().getText());
                    data.setHeadMiddleName(mainForm.getHeadMiddleNameTextField().getText());
                    data.setHeadPersonINN(mainForm.getHeadPersonINNTextField().getText());
                    data.setHeadTitle(mainForm.getHeadTitleTextField().getText());

                    data.setTitle(mainForm.getTitleTextField().getText());
                }
                case SOLE_PROPRIETOR -> {
                    data.setOGRNIP(mainForm.getOGRNTextField().getText());
                    data.setOrgPhone(mainForm.getOrgPhoneTextField().getText());
                    data.setStateOrProvinceName(regionCode);
                    data.setLocalityName(mainForm.getLocalityNameTextField().getText());
                    data.setStreetAddress(mainForm.getStreetAddressTextField().getText());
                }
                case NATURAL_PERSON -> {
                    data.setStateOrProvinceName(regionCode);
                    data.setLocalityName(mainForm.getLocalityNameTextField().getText());
                    data.setStreetAddress(mainForm.getStreetAddressTextField().getText());
                }
            }

            data.setCountryName(mainForm.getCountryNameTextField().getText());

            data.setIdentificationKindEnum((IdentificationKindEnum) mainForm.getIdentificationKindComboBox().getSelectedItem());
            data.setLastName(mainForm.getLastNameTextField().getText());
            data.setFirstName(mainForm.getFirstNameTextField().getText());
            data.setMiddleName(mainForm.getMiddleNameTextField().getText());
            data.setGenderEnum((GenderEnum) mainForm.getGenderComboBox().getSelectedItem());
            data.setSNILS(mainForm.getSNILSTextField().getText());
            data.setPersonINN(mainForm.getPersonINNTextField().getText());
            data.setBirthDate(mainForm.getBirthDateTextField().getText());
            data.setEmailAddress(mainForm.getEmailAddressTextField().getText());
            data.setPersonPhone(mainForm.getPersonPhoneTextField().getText());
            data.setTypeEnum((TypeEnum) mainForm.getTypeComboBox().getSelectedItem());
            data.setCitizenship(mainForm.getCitizenshipTextField().getText());
            data.setIssueDate(mainForm.getIssueDateTextField().getText());
            data.setDivision(mainForm.getDivisionTextField().getText());
            data.setSeries(mainForm.getSeriesTextField().getText());
            data.setNumber(mainForm.getNumberTextField().getText());
            data.setIssueId(mainForm.getIssueIdTextField().getText());

            data.setProduct(entrepreneurshipEnum);

            return data;
        }

        return null;
    }

    public final void displayApplicantData(@NotNull FormData data) {
        mainForm.getHeadApplicantCheckBox().setSelected(false);

        if (data.getEntrepreneurshipEnum() == EntrepreneurshipEnum.JURIDICAL_PERSON) {
            mainForm.getTitleTextField().setText(data.getTitle());
        }

        mainForm.getIdentificationKindComboBox().setSelectedItem(data.getIdentificationKindEnum());
        mainForm.getLastNameTextField().setText(data.getLastName());
        mainForm.getFirstNameTextField().setText(data.getFirstName());
        mainForm.getMiddleNameTextField().setText(data.getMiddleName());
        mainForm.getGenderComboBox().setSelectedItem(data.getGenderEnum());
        mainForm.getSNILSTextField().setText(data.getSNILS());
        mainForm.getPersonINNTextField().setText(data.getPersonINN());
        mainForm.getBirthDateTextField().setText(data.getBirthDate());
        mainForm.getEmailAddressTextField().setText(data.getEmailAddress());
        mainForm.getPersonPhoneTextField().setText(data.getPersonPhone());
        mainForm.getCitizenshipTextField().setText(data.getCitizenship());
        mainForm.getIssueDateTextField().setText(data.getIssueDate());
        mainForm.getSeriesTextField().setText(data.getSeries());
        mainForm.getNumberTextField().setText(data.getNumber());
        mainForm.getIssueIdTextField().setText(data.getIssueId());

        mainForm.getTypeComboBox().setSelectedItem(data.getTypeEnum());
    }

    public final void displayOrganizationData(@NotNull FormData data) {
        mainForm.getHeadApplicantCheckBox().setSelected(false);

        switch (data.getEntrepreneurshipEnum()) {
            case JURIDICAL_PERSON -> {
                mainForm.getCommonNameTextField().setText(data.getCommonName());
                mainForm.getDepartmentTextField().setText(data.getDepartment());
                mainForm.getKPPTextField().setText(data.getKPP());
                mainForm.getOrgINNTextField().setText(data.getOrgINN());
                mainForm.getOGRNTextField().setText(data.getOGRN());
                mainForm.getOrgPhoneTextField().setText(data.getOrgPhone());
                mainForm.getIndexTextField().setText(data.getIndex());
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(region.getRegionName(data.getStateOrProvinceNameLaw()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityNameLaw());
                mainForm.getStreetAddressTextField().setText(data.getStreetAddressLaw());
                mainForm.getHeadLastNameTextField().setText(data.getHeadLastName());
                mainForm.getHeadFirstNameTextField().setText(data.getHeadFirstName());
                mainForm.getHeadMiddleNameTextField().setText(data.getHeadMiddleName());
                mainForm.getHeadPersonINNTextField().setText(data.getHeadPersonINN());
                mainForm.getHeadTitleTextField().setText(data.getHeadTitle());
            }
            case SOLE_PROPRIETOR -> {
                mainForm.getOGRNTextField().setText(data.getOGRNIP());
                mainForm.getOrgPhoneTextField().setText(data.getOrgPhone());
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(region.getRegionName(data.getStateOrProvinceName()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityName());
            }
            case NATURAL_PERSON -> {
                mainForm.getStateOrProvinceNameComboBox().setSelectedItem(region.getRegionName(data.getStateOrProvinceName()));
                mainForm.getLocalityNameTextField().setText(data.getLocalityName());
            }
        }

        mainForm.getEntrepreneurshipComboBox().setSelectedItem(data.getEntrepreneurshipEnum());
    }

    public final void displayData(@NotNull FormData data) {
        displayApplicantData(data);
        displayOrganizationData(data);
    }
}
