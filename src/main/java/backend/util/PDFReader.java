package backend.util;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

import backend.window.main.form.Regions;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;

public final class PDFReader {
    private static final int END_PAGE_VALUE = 4;

    private final String text;

    private EntrepreneurshipEnum entrepreneurshipEnum;

    private String commonName;                                          // company
    private String KPP;                                                 // KPP
    private String orgINN;                                              // INN
    private String OGRN;                                                // OGRN
    private String OGRNIP;                                              // OGRNIP
    private String index;                                               // index
    private int stateOrProvinceNameLaw;                                 // regionLaw (regions code)
    private String localityNameLaw;                                     // cityLaw
    private String streetAddressLaw;                                    // addressLaw
    private String headLastName;                                        // headLastName
    private String headFirstName;                                       // headFirstName
    private String headMiddleName;                                      // headMiddleName
    private String headPersonINN;
    private String headTitle;                                           // headPosition

    private String lastName;                                            // lastName
    private String firstName;                                           // firstName
    private String middleName;                                          // middleName

    public PDFReader(File file) throws IOException {
        String text = formatText(readPDF(file));
        if (checkPDF(text)) {
            this.text = text;
            switch (entrepreneurshipEnum) {
                case JURIDICAL_PERSON -> setJPValue();
                case SOLE_PROPRIETOR -> setSPValue();
            }
        } else {
            throw new IOException();
        }
    }

    private String readPDF(File file) throws IOException {
        try (PDDocument pdDocument = new PDFParser(new RandomAccessReadBufferedFile(file)).parse()) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setStartPage(1);
            pdfTextStripper.setEndPage(END_PAGE_VALUE);
            pdfTextStripper.setLineSeparator(System.lineSeparator());
            return pdfTextStripper.getText(pdDocument);
        }
    }

    private @NotNull String formatText(@NotNull String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("Страница \\d+ из Выписка из ЕГР(ЮЛ|ИП) \\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2} ОГРН(|ИП) \\d{13,15} \\d+ ", "");
    }

    private @NotNull String getValue(String text, String regex) {
        Matcher m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            return m.group().strip().toUpperCase();
        }
        return "";
    }

    private boolean checkPDF(String text) {
        if (getValue(text, "из Единого государственного реестра юридических лиц").contains("ЮРИДИЧЕСКИХ")) {
            entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;
            return true;
        }
        if (getValue(text, "из Единого государственного реестра индивидуальных предпринимателей").contains("ИНДИВИДУАЛЬНЫХ")) {
            entrepreneurshipEnum = EntrepreneurshipEnum.SOLE_PROPRIETOR;
            return true;
        }
        return false;
    }

    private void setJPValue() {
        commonName = getValue(text, "(?<=Сокращенное наименование на русском языке ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        KPP = getValue(text, "(?<=КПП юридического лица )\\d{9}");
        orgINN = getValue(text, "(?<=ИНН юридического лица )\\d{10}");
        OGRN = getValue(text, "(?<=ОГРН )\\d{13}(?= \\d+ Дата (присвоения ОГРН|регистрации))");
        index = getValue(text, "(?<=Адрес юридического лица )\\d{6}");

        String location = getValue(text, "(?<=Место нахождения юридического лица ).*?(?= \\d+? ГРН и дата внесения в ЕГРЮЛ записи)");
        String address = getValue(text, "(?<=Адрес юридического лица \\d{6}, ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        if (!location.isBlank() && !address.isBlank()) {
            String[] locationSplit = location.split(", ");
            String[] addressSplit = address.split(", ");
            List<String> locationList = new ArrayList<>(Arrays.asList(locationSplit));
            List<String> addressList = new ArrayList<>(Arrays.asList(addressSplit));
            addressList.removeAll(locationList);
            stateOrProvinceNameLaw = Regions.getRegionCode(locationSplit[0]);
            streetAddressLaw = String.join(", ", addressList).replaceAll(",* -,*?", "");
            if (locationList.size() > 1) {
                locationList.remove(locationSplit[0]);
                localityNameLaw = String.join(", ", locationList);
            } else {
                localityNameLaw = locationSplit[0];
            }
        }

        String personalInformation = getValue(text, "(?<=Фамилия Имя Отчество ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        if (!personalInformation.isBlank()) {
            String[] personalInformationSplit = personalInformation.split(" \\d+ ИНН ");
            if (personalInformationSplit.length == 2) {
                String[] headNameSplit = personalInformationSplit[0].split(" ");
                headPersonINN = personalInformationSplit[1];
                if (headNameSplit.length == 3) {
                    headLastName = headNameSplit[0];
                    headFirstName = headNameSplit[1];
                    headMiddleName = headNameSplit[2];
                }
            }
            headTitle = getValue(text, "(?<=Должность ).*?(?= \\d+ ГРН и дата внесения в ЕГРЮЛ записи)");
        }
    }

    private void setSPValue() {
        orgINN = getValue(text, "(?<=Идентификационный номер налогоплательщика \\(ИНН\\) )\\d{12}");
        OGRNIP = getValue(text, "(?<=ОГРНИП )\\d{15}(?= \\d+ Дата регистрации)");

        String personalInformation = getValue(text, "(?<=Фамилия Имя Отчество ).*?(?= \\d+ ГРН и дата внесения в ЕГРИП записи)");
        if (!personalInformation.isBlank()) {
            String[] personalInformationSplit = personalInformation.split(" ");
            if (personalInformationSplit.length == 3) {
                lastName = personalInformationSplit[0];
                firstName = personalInformationSplit[1];
                middleName = personalInformationSplit[2];
            }
        }
    }

    private LocalDate getReleaseDate() {
        String date = "01.01.1970";
        switch (entrepreneurshipEnum) {
            case JURIDICAL_PERSON -> date = getValue(text, "(?<=из Единого государственного реестра юридических лиц )\\d{2}\\.\\d{2}\\.\\d{4}");
            case SOLE_PROPRIETOR -> date = getValue(text, "(?<=из Единого государственного реестра индивидуальных предпринимателей )\\d{2}\\.\\d{2}\\.\\d{4}");
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public boolean checkDate() {
        return LocalDate.now().isEqual(getReleaseDate());
    }

    public void print() {
        System.out.print(System.lineSeparator());

        System.out.println("Дата выписки: " + getReleaseDate());
        System.out.print(System.lineSeparator());

        System.out.println("Форма организации: " + entrepreneurshipEnum.getTitle());
        System.out.print(System.lineSeparator());

        System.out.println("Сокращенное наименование организации: " + commonName);
        System.out.println("КПП: " + KPP);
        System.out.println("ИНН: " + orgINN);
        System.out.println("ОГРН: " + OGRN);
        System.out.println("ОГРНИП: " + OGRNIP);
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
        System.out.print(System.lineSeparator());

        System.out.println("Фамилия заявителя: " + lastName);
        System.out.println("Имя заявителя: " + firstName);
        System.out.println("Отчество заявителя: " + middleName);
    }

    public EntrepreneurshipEnum getEntrepreneurshipConstant() {
        return entrepreneurshipEnum;
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

    public String getOGRNIP() {
        return OGRNIP;
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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Укажите путь к файлу *.pdf, включая диск: ");
            File file = new File(scanner.nextLine());
            try {
                if (file.exists() && file.isFile() && file.getName().endsWith(".pdf")) {
                    PDFReader reader = new PDFReader(file);
                    System.out.println(reader.text);
                    reader.print();
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.err.println("Проверьте правильность указания пути и имени файла.");
            } catch (Exception e) {
                System.err.println("Произошла ошибка при попытке чтения указанного файла.");
            }
        }
    }
}