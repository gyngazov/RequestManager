package backend.window.main.bar.listener;

import backend.reader.Readable;
import backend.window.main.form.FormData;
import backend.window.main.form.Region;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ExportDataCFG extends DataConverter implements ActionListener {

    private void write(@NotNull FormData data, File currentDirectory) throws IOException {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(new File(currentDirectory, "cfg.txt"), StandardCharsets.UTF_8, false))) {
            out.write("#1 СНИЛС заявителя");
            out.newLine();
            out.write("SNILS=" + data.getSNILS());

            out.newLine();
            out.newLine();
            out.write("#2 ОГРН организации");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("OGRN=" + data.getOGRN());
                case SOLE_PROPRIETOR -> out.write("OGRN=" + data.getOGRNIP());
                case NATURAL_PERSON -> out.write("OGRN=");
            }

            out.newLine();
            out.newLine();
            out.write("#3 ИНН организации");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("orgINN=" + data.getOrgINN());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("orgINN=");
            }

            out.newLine();
            out.newLine();
            out.write("#4 Электронный адрес заявителя");
            out.newLine();
            out.write("emailAddress=" + data.getEmailAddress());

            out.newLine();
            out.newLine();
            out.write("#5 Страна");
            out.newLine();
            out.write("countryName=RU");

            out.newLine();
            out.newLine();
            out.write("#6 Регион");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("stateOrProvinceName="
                        + data.getStateOrProvinceNameLaw() + " "
                        + Region.getInstance().getRegionName(data.getStateOrProvinceNameLaw()));
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("stateOrProvinceName="
                        + data.getStateOrProvinceName() + " "
                        + Region.getInstance().getRegionName(data.getStateOrProvinceName()));
            }

            out.newLine();
            out.newLine();
            out.write("#7 Населенный пункт");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("localityName=" + data.getLocalityNameLaw());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("localityName=" + data.getLocalityName());
            }

            out.newLine();
            out.newLine();
            out.write("#8 Сокращенное наименовании организации");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("organizationName=" + data.getCommonName());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("organizationName=");
            }

            out.newLine();
            out.newLine();
            out.write("#9 Подразделение");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("organizationalUnitName=" + data.getDepartment());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("organizationalUnitName=");
            }

            out.newLine();
            out.newLine();
            out.write("#10 Общее имя");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("commonName=" + data.getCommonName());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("commonName="
                        + (data.getLastName() + " " + data.getFirstName() + " " + data.getMiddleName()).strip());
            }

            out.newLine();
            out.newLine();
            out.write("#11 Название улицы, номер дома");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("streetAddress=" + data.getStreetAddressLaw());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("streetAddress=");
            }

            out.newLine();
            out.newLine();
            out.write("#12 КПП организации");
            out.newLine();
            out.write("KPP=");

            out.newLine();
            out.newLine();
            out.write("#13 Должность заявителя");
            out.newLine();
            switch (data.getEntrepreneurshipEnum()) {
                case JURIDICAL_PERSON -> out.write("title=" + data.getTitle());
                case SOLE_PROPRIETOR, NATURAL_PERSON -> out.write("title=");
            }

            out.newLine();
            out.newLine();
            out.write("#14 Имя заявителя");
            out.newLine();
            out.write("firstName=" + data.getFirstName());

            out.newLine();
            out.newLine();
            out.write("#15 Фамилия заявителя");
            out.newLine();
            out.write("lastName=" + data.getLastName());

            out.newLine();
            out.newLine();
            out.write("#16 Отчество заявителя");
            out.newLine();
            out.write("middleName=" + data.getMiddleName());

            out.newLine();
            out.newLine();
            out.write("#17 Пол заявителя <M - мужской, F - женский>");
            out.newLine();
            out.write("gender=" + data.getGenderEnum().getCode());

            out.newLine();
            out.newLine();
            out.write("#18 Дата рождения заявителя в формате ДД.ММ.ГГГГ");
            out.newLine();
            out.write("birthDate=" + data.getBirthDate());

            out.newLine();
            out.newLine();
            out.write("#19 Место рождения заявителя");
            out.newLine();
            out.write("birthplace=");

            out.newLine();
            out.newLine();
            out.write("#20 ИНН заявителя");
            out.newLine();
            out.write("personINN=" + data.getPersonINN());

            out.newLine();
            out.newLine();
            out.write("#21 Вид документа, удостоверяющего личность заявителя <RF_PASSPORT или FID_DOC>");
            out.newLine();
            out.write("type=" + data.getTypeEnum().getCode());

            out.newLine();
            out.newLine();
            out.write("#22 Серия документа, удостоверяющего личность заявителя");
            out.newLine();
            out.write("series=" + Objects.requireNonNullElse(data.getSeries(), ""));

            out.newLine();
            out.newLine();
            out.write("#23 Номер документа, удостоверяющего личность заявителя");
            out.newLine();
            out.write("number=" + data.getNumber());

            out.newLine();
            out.newLine();
            out.write("#24 Дата выдачи документа, удостоверяющего личность заявителя, в формате ДД.ММ.ГГГГ");
            out.newLine();
            out.write("issueDate=" + data.getIssueDate());

            out.newLine();
            out.newLine();
            out.write("#25 Код подразделения, выдавшего паспорт гражданина РФ, либо наименование органа, выдавшего документ, удостоверяющий личность заявителя");
            out.newLine();
            out.write("issueId="
                    + (data.getIssueId() == null ? "000-000" : new StringBuilder(data.getIssueId()).insert(3, '-').toString()));

            out.newLine();
            out.newLine();
            out.write("#26 Гражданство заявителя");
            out.newLine();
            out.write("citizenship=" + Objects.requireNonNullElse(data.getCitizenship(), ""));

            out.newLine();
            out.newLine();
            out.write("#27 ОГРНИП");
            out.newLine();
            out.write("OGRNIP=");

            out.newLine();
            out.newLine();
            out.write("#28 Тип идентификации заявителя <0 - лично, 1 - по действующей КЭП, 2 - загранпаспорт с электронным носителем, 3 - ЕСИА/ЕБС>");
            out.newLine();
            out.write("identification=" + data.getIdentificationKindEnum().getCode());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {
            try {
                FormData data = getDisplayData(true);
                if (data == null) {
                    new MessageDialog.Error("В карточке организации укажите её форму и повторите попытку.");
                } else {
                    write(data, currentDirectory);

                    new MessageDialog.Info("Операция успешно завершена!");
                }
            } catch (IOException exception) {
                new MessageDialog.Error("Ошибка при записи файла, повторите попытку.");
            }
        }
    }
}
