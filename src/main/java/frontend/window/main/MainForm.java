package frontend.window.main;

import backend.util.Constants;
import backend.util.Validation;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.main.form.constant.TypeEnum;
import backend.window.main.form.listener.EntrepreneurshipListener;
import backend.window.main.form.listener.HeadApplicantListener;
import backend.window.main.form.listener.TypeListener;
import backend.window.main.form.constant.GenderEnum;
import backend.window.main.form.constant.IdentificationKindEnum;
import frontend.controlElement.CheckBox;
import frontend.controlElement.ComboBox;
import frontend.controlElement.Label;
import frontend.controlElement.TextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

import static backend.window.main.form.constant.EntrepreneurshipEnum.JURIDICAL_PERSON;
import static backend.window.main.form.constant.TypeEnum.RF_PASSPORT;

public class MainForm extends JPanel {
    private final Label identificationKindLabel = new Label("Способ идентификации");
    private final Label lastNameLabel = new Label("Фамилия");
    private final Label firstNameLabel = new Label("Имя");
    private final Label middleNameLabel = new Label("Отчество");
    private final Label genderLabel = new Label("Пол");
    private final Label SNILSLabel = new Label("СНИЛС");
    private final Label personINNLabel = new Label("ИНН");
    private final Label birthDateLabel = new Label("Дата рождения");
    private final Label emailAddressLabel = new Label("Электронная почта");
    private final Label personPhoneLabel = new Label("Телефон");
    private final Label titleLabel = new Label("Должность");
    private final Label citizenshipLabel = new Label("Гражданство");
    private final Label typeLabel = new Label("Вид документа");
    private final Label issueDateLabel = new Label("Дата выдачи");
    private final Label divisionLabel = new Label("Кем выдан");
    private final Label seriesLabel = new Label("Серия");
    private final Label numberLabel = new Label("Номер");
    private final Label issueIdLabel = new Label("Код подразделения");

    private final Label entrepreneurshipLabel = new Label("Форма организации");
    private final Label commonNameLabel = new Label("Сокращенное наименование организации");
    private final Label departmentLabel = new Label("Сокращенное наименование филиала");
    private final Label KPPLabel = new Label("КПП");
    private final Label orgINNLabel = new Label("ИНН");
    private final Label OGRNLabel = new Label("ОГРН");
    private final Label orgPhoneLabel = new Label("Телефон");
    private final Label indexLabel = new Label("Индекс");
    private final Label countryNameLabel = new Label("Страна");
    private final Label stateOrProvinceNameLabel = new Label("Регион");
    private final Label localityNameLabel = new Label("Населенный пункт");
    private final Label streetAddressLabel = new Label("Адрес места нахождения");
    private final Label headLastNameLabel = new Label("Фамилия руководителя");
    private final Label headFirstNameLabel = new Label("Имя руководителя");
    private final Label headMiddleNameLabel = new Label("Отчество руководителя");
    private final Label headPersonINNLabel = new Label("ИНН руководителя");
    private final Label headTitleLabel = new Label("Должность руководителя");
    private final Label headApplicantLabel = new Label("Он же заявитель");

    private final ComboBox<IdentificationKindEnum> identificationKindComboBox = new ComboBox.IdentificationKind();
    private final TextField lastNameTextField = new TextField(Validation::isCorrectPersonLastName);
    private final TextField firstNameTextField = new TextField(Validation::isCorrectPersonFirstName);
    private final TextField middleNameTextField = new TextField(Validation::isCorrectPersonMiddleName);
    private final ComboBox<GenderEnum> genderComboBox = new ComboBox.Gender();
    private final TextField SNILSTextField = new TextField(Validation::isCorrectSNILS);
    private final TextField personINNTextField = new TextField(Validation::isCorrectPersonINN);
    private final TextField birthDateTextField = new TextField(Validation::isCorrectBirthDate);
    private final TextField emailAddressTextField = new TextField(Validation::isCorrectEmailAddress);
    private final TextField personPhoneTextField = new TextField(Validation::isCorrectPhone);
    private final TextField titleTextField = new TextField(Validation::isCorrectPersonTitle);
    private final ComboBox<TypeEnum> typeComboBox = new ComboBox.Type();
    private final TextField citizenshipTextField = new TextField(Validation::isCorrectCitizenship);
    private final TextField issueDateTextField = new TextField(text -> Validation.isCorrectIssueDate(text, birthDateTextField.getText()));
    private final TextField divisionTextField = new TextField.Division();
    private final TextField seriesTextField = new TextField(text -> Validation.isCorrectSeries(text, (TypeEnum) typeComboBox.getSelectedItem()));
    private final TextField numberTextField = new TextField(text -> Validation.isCorrectNumber(text, (TypeEnum) typeComboBox.getSelectedItem()));
    private final TextField issueIdTextField = new TextField(text -> Validation.isCorrectIssueId(text, (TypeEnum) typeComboBox.getSelectedItem()));

    private final ComboBox<EntrepreneurshipEnum> entrepreneurshipComboBox = new ComboBox.Entrepreneurship();
    private final TextField commonNameTextField = new TextField(Validation::isCorrectOrganizationName);
    private final TextField departmentTextField = new TextField(Validation::isCorrectOrganizationName) {

        private void setText(int length) {
            if (length == 0) {
                KPPLabel.setText("КПП");
                stateOrProvinceNameLabel.setText("Регион");
                localityNameLabel.setText("Населенный пункт");
                streetAddressLabel.setText("Адрес места нахождения");
            } else {
                KPPLabel.setText("КПП филиала");
                stateOrProvinceNameLabel.setText("Регион филиала");
                localityNameLabel.setText("Населенный пункт филиала");
                streetAddressLabel.setText("Адрес места нахождения филиала");
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            setText(e.getDocument().getLength());
            super.insertUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setText(e.getDocument().getLength());
            super.removeUpdate(e);
        }
    };
    private final TextField KPPTextField = new TextField(Validation::isCorrectKPP);
    private final TextField orgINNTextField = new TextField(Validation::isCorrectOrgINN);
    private final TextField OGRNTextField = new TextField(Validation::isCorrectOGRN);
    private final TextField orgPhoneTextField = new TextField(Validation::isCorrectPhone);
    private final TextField indexTextField = new TextField(Validation::isCorrectIndex);
    private final TextField countryNameTextField = new TextField.CountryName();
    private final ComboBox<String> stateOrProvinceNameComboBox = new ComboBox.StateOrProvinceName();
    private final TextField localityNameTextField = new TextField();
    private final TextField streetAddressTextField = new TextField();
    private final TextField headLastNameTextField = new TextField(Validation::isCorrectPersonLastName);
    private final TextField headFirstNameTextField = new TextField(Validation::isCorrectPersonFirstName);
    private final TextField headMiddleNameTextField = new TextField(Validation::isCorrectPersonMiddleName);
    private final TextField headPersonINNTextField = new TextField(Validation::isCorrectPersonINN);
    private final TextField headTitleTextField = new TextField(Validation::isCorrectPersonTitle);
    private final CheckBox headApplicantCheckBox = new CheckBox("Да", false);

    public MainForm() {
        final JPanel organization = new JPanel();
        final JPanel applicant = new JPanel();

        setOrganizationLayout(organization);
        setApplicantLayout(applicant);

        setPanelBorder(organization, "КАРТОЧКА ОРГАНИЗАЦИИ");
        setPanelBorder(applicant, "ПЕРСОНАЛЬНЫЕ ДАННЫЕ ЗАЯВИТЕЛЯ");

        setMainFormLayout(organization, applicant);

        setListener();
        setDefaults();
    }

    private void setOrganizationLayout(final JPanel organization) {
        GroupLayout layout = new GroupLayout(organization);
        organization.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(entrepreneurshipLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(entrepreneurshipComboBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(commonNameLabel, Constants.LABEL_WIDTH * 2, Constants.LABEL_WIDTH * 2, Short.MAX_VALUE)
                                .addComponent(commonNameTextField, Constants.TEXT_FIELD_WIDTH * 2, Constants.TEXT_FIELD_WIDTH * 2, Short.MAX_VALUE)))
                .addComponent(departmentLabel, Constants.LABEL_WIDTH * 3, Constants.LABEL_WIDTH * 3, Short.MAX_VALUE)
                .addComponent(departmentTextField, Constants.TEXT_FIELD_WIDTH * 3, Constants.TEXT_FIELD_WIDTH * 3, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(KPPLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(KPPTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH)
                                .addComponent(orgPhoneLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(orgPhoneTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH)
                                .addComponent(stateOrProvinceNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(stateOrProvinceNameComboBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(orgINNLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                                .addComponent(orgINNTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                                .addComponent(indexLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                                .addComponent(indexTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(OGRNLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                                .addComponent(OGRNTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                                .addComponent(countryNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                                .addComponent(countryNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)))
                                .addComponent(localityNameLabel, Constants.TEXT_FIELD_WIDTH * 2, Constants.TEXT_FIELD_WIDTH * 2, Short.MAX_VALUE)
                                .addComponent(localityNameTextField, Constants.TEXT_FIELD_WIDTH * 2, Constants.TEXT_FIELD_WIDTH * 2, Short.MAX_VALUE)))
                .addComponent(streetAddressLabel, Constants.LABEL_WIDTH * 3, Constants.LABEL_WIDTH * 3, Short.MAX_VALUE)
                .addComponent(streetAddressTextField, Constants.TEXT_FIELD_WIDTH * 3, Constants.TEXT_FIELD_WIDTH * 3, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(headLastNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(headLastNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH)
                                .addComponent(headPersonINNLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH)
                                .addComponent(headPersonINNTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(headFirstNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(headFirstNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(headTitleLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(headTitleTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(headMiddleNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(headMiddleNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(headApplicantLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(headApplicantCheckBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(entrepreneurshipLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(commonNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(entrepreneurshipComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(commonNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addComponent(departmentLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(departmentTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(KPPLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(orgINNLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(OGRNLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(KPPTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(orgINNTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(OGRNTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(orgPhoneLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(indexLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(countryNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(orgPhoneTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(indexTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(countryNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(stateOrProvinceNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(localityNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(stateOrProvinceNameComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(localityNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addComponent(streetAddressLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(streetAddressTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(headLastNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(headFirstNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(headMiddleNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(headLastNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(headFirstNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(headMiddleNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(headPersonINNLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(headTitleLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(headApplicantLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(headPersonINNTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(headTitleTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(headApplicantCheckBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)));
    }

    private void setApplicantLayout(final JPanel applicant) {
        GroupLayout layout = new GroupLayout(applicant);
        applicant.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(identificationKindLabel, Constants.LABEL_WIDTH * 3, Constants.LABEL_WIDTH * 3, Short.MAX_VALUE)
                .addComponent(identificationKindComboBox, Constants.TEXT_FIELD_WIDTH * 3, Constants.TEXT_FIELD_WIDTH * 3, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lastNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(lastNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(genderLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(genderComboBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(birthDateLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(birthDateTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(firstNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(firstNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(SNILSLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(SNILSTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(emailAddressLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(emailAddressTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(middleNameLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(middleNameTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(personINNLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(personINNTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)
                                .addComponent(personPhoneLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(personPhoneTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)))
                .addComponent(titleLabel, Constants.LABEL_WIDTH * 3, Constants.LABEL_WIDTH * 3, Short.MAX_VALUE)
                .addComponent(titleTextField, Constants.TEXT_FIELD_WIDTH * 3, Constants.TEXT_FIELD_WIDTH * 3, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(typeLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(typeComboBox, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(citizenshipLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(citizenshipTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(issueDateLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(issueDateTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE)))
                .addComponent(divisionLabel, Constants.LABEL_WIDTH * 3, Constants.LABEL_WIDTH * 3, Short.MAX_VALUE)
                .addComponent(divisionTextField, Constants.TEXT_FIELD_WIDTH * 3, Constants.TEXT_FIELD_WIDTH * 3, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(seriesLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(seriesTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(numberLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(numberTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(issueIdLabel, Constants.LABEL_WIDTH, Constants.LABEL_WIDTH, Short.MAX_VALUE)
                                .addComponent(issueIdTextField, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_WIDTH, Short.MAX_VALUE))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(identificationKindLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(identificationKindComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lastNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(firstNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(middleNameLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lastNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(firstNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(middleNameTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(genderLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(SNILSLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(personINNLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(genderComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(SNILSTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(personINNTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(birthDateLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(emailAddressLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(personPhoneLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(birthDateTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(emailAddressTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(personPhoneTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addComponent(titleLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(titleTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(citizenshipLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(typeLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(issueDateLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(typeComboBox, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(citizenshipTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(issueDateTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT))
                .addComponent(divisionLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                .addComponent(divisionTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(seriesLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(numberLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT)
                        .addComponent(issueIdLabel, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT, Constants.LABEL_HEIGHT))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(seriesTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(numberTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                        .addComponent(issueIdTextField, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)));
    }

    private void setMainFormLayout(final JPanel organization, final JPanel applicant) {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(organization)
                .addComponent(applicant));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(organization)
                .addComponent(applicant));
    }

    private void setPanelBorder(final JPanel jPanel, final String value) {
        jPanel.setBorder(Constants.createDefaultTitledBorder(value));
    }

    private void setListener() {
        typeComboBox.addItemListener(new TypeListener(this));
        headApplicantCheckBox.addItemListener(new HeadApplicantListener(this));
        entrepreneurshipComboBox.addItemListener(new EntrepreneurshipListener(this));
    }

    private void setDefaults() {
        typeComboBox.setSelectedItem(RF_PASSPORT);
        entrepreneurshipComboBox.setSelectedItem(JURIDICAL_PERSON);
    }

    public Label getOGRNLabel() {
        return OGRNLabel;
    }

    public ComboBox<IdentificationKindEnum> getIdentificationKindComboBox() {
        return identificationKindComboBox;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public TextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public TextField getMiddleNameTextField() {
        return middleNameTextField;
    }

    public ComboBox<GenderEnum> getGenderComboBox() {
        return genderComboBox;
    }

    public TextField getSNILSTextField() {
        return SNILSTextField;
    }

    public TextField getPersonINNTextField() {
        return personINNTextField;
    }

    public TextField getBirthDateTextField() {
        return birthDateTextField;
    }

    public TextField getEmailAddressTextField() {
        return emailAddressTextField;
    }

    public TextField getPersonPhoneTextField() {
        return personPhoneTextField;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public ComboBox<TypeEnum> getTypeComboBox() {
        return typeComboBox;
    }

    public TextField getCitizenshipTextField() {
        return citizenshipTextField;
    }

    public TextField getIssueDateTextField() {
        return issueDateTextField;
    }

    public TextField getDivisionTextField() {
        return divisionTextField;
    }

    public TextField getSeriesTextField() {
        return seriesTextField;
    }

    public TextField getNumberTextField() {
        return numberTextField;
    }

    public TextField getIssueIdTextField() {
        return issueIdTextField;
    }

    public ComboBox<EntrepreneurshipEnum> getEntrepreneurshipComboBox() {
        return entrepreneurshipComboBox;
    }

    public TextField getCommonNameTextField() {
        return commonNameTextField;
    }

    public TextField getDepartmentTextField() {
        return departmentTextField;
    }

    public TextField getKPPTextField() {
        return KPPTextField;
    }

    public TextField getOrgINNTextField() {
        return orgINNTextField;
    }

    public TextField getOGRNTextField() {
        return OGRNTextField;
    }

    public TextField getOrgPhoneTextField() {
        return orgPhoneTextField;
    }

    public TextField getIndexTextField() {
        return indexTextField;
    }

    public TextField getCountryNameTextField() {
        return countryNameTextField;
    }

    public ComboBox<String> getStateOrProvinceNameComboBox() {
        return stateOrProvinceNameComboBox;
    }

    public TextField getLocalityNameTextField() {
        return localityNameTextField;
    }

    public TextField getStreetAddressTextField() {
        return streetAddressTextField;
    }

    public TextField getHeadLastNameTextField() {
        return headLastNameTextField;
    }

    public TextField getHeadFirstNameTextField() {
        return headFirstNameTextField;
    }

    public TextField getHeadMiddleNameTextField() {
        return headMiddleNameTextField;
    }

    public TextField getHeadPersonINNTextField() {
        return headPersonINNTextField;
    }

    public TextField getHeadTitleTextField() {
        return headTitleTextField;
    }

    public CheckBox getHeadApplicantCheckBox() {
        return headApplicantCheckBox;
    }
}
