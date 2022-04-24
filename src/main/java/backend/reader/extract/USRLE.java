package backend.reader.extract;

import backend.window.main.form.Region;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class USRLE extends ExtractFTS {
    private String commonName;                                          // company
    private String KPP;                                                 // kpp
    private String orgINN;                                              // inn
    private String OGRN;                                                // ogrn
    private String index;                                               // index
    private int stateOrProvinceNameLaw;                                 // regionLaw
    private String localityNameLaw;                                     // cityLaw
    private String streetAddressLaw;                                    // addressLaw
    private String headLastName;                                        // headLastName
    private String headFirstName;                                       // headFirstName
    private String headMiddleName;                                      // headMiddleName
    private String headPersonINN;
    private String headTitle;                                           // headPosition

    public USRLE(String text) {
        super(formatText(text));
        setValue();
    }

    public static @NotNull String formatText(@NotNull String text) {
        return text.replaceAll("Страница \\d+ из Выписка из ЕГРЮЛ \\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2} ОГРН \\d{13} \\d+ ", "");
    }

    private void setValue() {
        date = getValue(getFormattedText(),
                "(?<=из Единого государственного реестра юридических лиц )\\d{2}\\.\\d{2}\\.\\d{4}");

        entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;

        commonName = getValue(getFormattedText(),
                "(?<=Сокращенное наименование на русском языке ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        KPP = getValue(getFormattedText(),
                "(?<=КПП юридического лица )\\d{9}");
        orgINN = getValue(getFormattedText(),
                "(?<=ИНН юридического лица )\\d{10}");
        OGRN = getValue(getFormattedText(),
                "(?<=ОГРН )\\d{13}(?= \\d+ Дата (присвоения ОГРН|регистрации))");
        index = getValue(getFormattedText(),
                "(?<=Адрес юридического лица )\\d{6}");

        String location = getValue(getFormattedText(),
                "(?<=Место нахождения юридического лица ).*?(?= \\d+? ГРН и дата внесения в ЕГРЮЛ записи)");
        String[] locations = null;
        if (location != null) {
            locations = location.split(", ");
            stateOrProvinceNameLaw = Region.getInstance().getRegionCode(locations[0]);
        }

        String address = getValue(getFormattedText(),
                "(?<=Адрес юридического лица \\d{6}, ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        if (locations != null && address != null) {
            String[] addresses = address.split(", ");
            List<String> locationList = new ArrayList<>(Arrays.asList(locations));
            List<String> addressList = new ArrayList<>(Arrays.asList(addresses));
            addressList.removeAll(locationList);
            streetAddressLaw = String.join(", ", addressList).replaceAll(",* -,*?", "");
            if (locationList.size() > 1) {
                locationList.remove(locations[0]);
                localityNameLaw = String.join(", ", locationList);
            } else {
                localityNameLaw = locations[0];
            }
        }

        String personalData = getValue(getFormattedText(),
                "(?<=Сведения о лице, имеющем право без доверенности действовать от имени юридического лица ).*(?= Сведения об участниках / учредителях юридического лица)");
        if (personalData == null) {
            personalData = getValue(getFormattedText(),
                    "(?<=Сведения о лице, имеющем право без доверенности действовать от имени юридического лица ).*(?= Сведения об учете в налоговом органе)");
        }
        if (personalData != null) {
            String fullName = getValue(personalData,
                    "(?<=Фамилия Имя Отчество ).*?(?= \\d+ )");
            if (fullName != null) {
                String[] names = fullName.split(" ");
                if (names.length == 3) {
                    headLastName = capitalize(names[0]);
                    headFirstName = capitalize(names[1]);
                    headMiddleName = capitalize(names[2]);
                }

                headPersonINN = getValue(personalData,
                        "(?<=ИНН ).*?(?= \\d+ )");
                headTitle = getValue(personalData,
                        "(?<=Должность ).*?(?= \\d+ )");
                if (headTitle != null) {
                    headTitle = capitalize(headTitle);
                }
            }
        }
    }

    public String getCommonName() {
        return commonName;
    }

    public String getKPP() {
        return KPP;
    }

    public String getOrgINN() {
        return orgINN;
    }

    public String getOGRN() {
        return OGRN;
    }

    public String getIndex() {
        return index;
    }

    public int getStateOrProvinceNameLaw() {
        return stateOrProvinceNameLaw;
    }

    public String getLocalityNameLaw() {
        return localityNameLaw;
    }

    public String getStreetAddressLaw() {
        return streetAddressLaw;
    }

    public String getHeadLastName() {
        return headLastName;
    }

    public String getHeadFirstName() {
        return headFirstName;
    }

    public String getHeadMiddleName() {
        return headMiddleName;
    }

    public String getHeadPersonINN() {
        return headPersonINN;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    @Override
    public void print() {
        super.print();

        System.out.println("Сокращенное наименование организации: " + commonName);
        System.out.println("КПП: " + KPP);
        System.out.println("ИНН: " + orgINN);
        System.out.println("ОГРН: " + OGRN);
        System.out.println("Индекс: " + index);
        System.out.println("Регион: " + stateOrProvinceNameLaw);
        System.out.println("Населенный пункт: " + localityNameLaw);
        System.out.println("Адрес места нахождения: " + streetAddressLaw);
        System.out.print(System.lineSeparator());

        System.out.println("Фамилия руководителя: " + headLastName);
        System.out.println("Имя руководителя: " + headFirstName);
        System.out.println("Отчество руководителя: " + headMiddleName);
        System.out.println("ИНН руководителя: " + headPersonINN);
        System.out.println("Должность руководителя: " + headTitle);
    }
}
