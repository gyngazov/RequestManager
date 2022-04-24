package backend.reader.extract;

import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.jetbrains.annotations.NotNull;

public final class URIE extends ExtractFTS {
    private String orgINN;                                              // inn
    private String OGRNIP;                                              // ogrnip

    private String lastName;                                            // lastName
    private String firstName;                                           // firstName
    private String middleName;                                          // middleName

    public URIE(String text) {
        super(formatText(text));
        setValue();
    }

    public static @NotNull String formatText(@NotNull String text) {
        return text.replaceAll("Страница \\d+ из Выписка из ЕГРИП \\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2} ОГРНИП \\d{15} \\d+ ", "");
    }

    private void setValue() {
        date = getValue(getFormattedText(),
                "(?<=из Единого государственного реестра индивидуальных предпринимателей )\\d{2}\\.\\d{2}\\.\\d{4}");

        entrepreneurshipEnum = EntrepreneurshipEnum.SOLE_PROPRIETOR;

        orgINN = getValue(getFormattedText(),
                "(?<=Идентификационный номер налогоплательщика \\(ИНН\\) )\\d{12}");
        OGRNIP = getValue(getFormattedText(),
                "(?<=ОГРНИП )\\d{15}(?= \\d+ Дата (присвоения ОГРНИП|регистрации))");

        String personalInformation = getValue(getFormattedText(),
                "(?<=Фамилия Имя Отчество ).*?(?= \\d+ )");
        if (personalInformation != null) {
            String[] personalInformationSplit = personalInformation.split(" ");
            if (personalInformationSplit.length == 3) {
                lastName = capitalize(personalInformationSplit[0]);
                firstName = capitalize(personalInformationSplit[1]);
                middleName = capitalize(personalInformationSplit[2]);
            }
        }
    }

    public String getOrgINN() {
        return orgINN;
    }

    public String getOGRNIP() {
        return OGRNIP;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void print() {
        super.print();

        System.out.println("ИНН: " + orgINN);
        System.out.println("ОГРНИП: " + OGRNIP);
        System.out.print(System.lineSeparator());

        System.out.println("Фамилия заявителя: " + lastName);
        System.out.println("Имя заявителя: " + firstName);
        System.out.println("Отчество заявителя: " + middleName);
    }
}
