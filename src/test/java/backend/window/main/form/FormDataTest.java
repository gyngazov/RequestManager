package backend.window.main.form;

import backend.window.main.form.constant.EntrepreneurshipEnum;
import backend.window.main.form.constant.GenderEnum;
import backend.window.main.form.constant.IdentificationKindEnum;
import backend.window.main.form.constant.TypeEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FormDataTest {

    @Test
    void generateOnRequestID_JP_1() throws IOException {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setCommonName("НАИМЕНОВАНИЕ ОРГАНИЗАЦИИ");
        data.setDepartment("НАИМЕНОВАНИЕ ФИЛИАЛА");
        data.setKPP("381001001");
        data.setOrgINN("7710248390");
        data.setOGRN("1084823000360");
        data.setOrgPhone("9998887766");
        data.setIndex("123456");
        data.setStateOrProvinceNameLaw(77);
        data.setLocalityNameLaw("Населенный пункт");
        data.setStreetAddressLaw("Адрес места нахождения");
        data.setHeadLastName("Фамилия руководителя");
        data.setHeadFirstName("Имя руководителя");
        data.setHeadMiddleName("Отчество руководителя");
        data.setHeadTitle("Должность руководителя");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setPersonINN("575213293038");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTitle("Должность заявителя");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setDivision("Кем выдан");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, FormData.generateOnRequestID(161424));
    }

    @Test
    void generateOnRequestID_JP_2() throws IOException {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setCommonName("НАИМЕНОВАНИЕ ОРГАНИЗАЦИИ");
        data.setKPP("381001001");
        data.setOrgINN("7710248390");
        data.setOGRN("1084823000360");
        data.setOrgPhone("9998887766");
        data.setStateOrProvinceNameLaw(77);
        data.setLocalityNameLaw("Населенный пункт");
        data.setStreetAddressLaw("Адрес места нахождения");
        data.setHeadLastName("Фамилия руководителя");
        data.setHeadFirstName("Имя руководителя");
        data.setHeadTitle("Должность руководителя");

        data.setIdentificationKindEnum(IdentificationKindEnum.CURRENT_CEP);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setGenderEnum(GenderEnum.F);
        data.setSNILS("08093032045");
        data.setPersonINN("575213293038");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTitle("Должность заявителя");
        data.setTypeEnum(TypeEnum.FID_DOC);
        data.setIssueDate("15.03.2000");
        data.setSeries("0000");
        data.setNumber("000000");
        data.setIssueId("000-000");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, FormData.generateOnRequestID(161423));
    }

    @Test
    void generateOnRequestID_SP_1() throws IOException {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.SOLE_PROPRIETOR;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setOGRNIP("321774600378760");
        data.setOrgPhone("9998887766");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, FormData.generateOnRequestID(161421));
    }

    @Test
    void generateOnRequestID_NP_1() throws IOException {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.NATURAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");
        data.setStreetAddress("Адрес места нахождения");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setDivision("Кем выдан");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, FormData.generateOnRequestID(161420));
    }

    @Test
    void generateOnRequestID_NP_2() throws IOException {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.NATURAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");

        data.setIdentificationKindEnum(IdentificationKindEnum.CURRENT_CEP);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setGenderEnum(GenderEnum.F);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.FID_DOC);
        data.setIssueDate("15.03.2000");
        data.setSeries("0000");
        data.setNumber("000000");
        data.setIssueId("000-000");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, FormData.generateOnRequestID(161419));
    }

    @Test
    void copy_JP_1() {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setCommonName("НАИМЕНОВАНИЕ ОРГАНИЗАЦИИ");
        data.setDepartment("НАИМЕНОВАНИЕ ФИЛИАЛА");
        data.setKPP("381001001");
        data.setOrgINN("7710248390");
        data.setOGRN("1084823000360");
        data.setOrgPhone("9998887766");
        data.setIndex("123456");
        data.setCountryName("Россия");
        data.setStateOrProvinceNameLaw(77);
        data.setLocalityNameLaw("Населенный пункт");
        data.setStreetAddressLaw("Адрес места нахождения");
        data.setHeadLastName("Фамилия руководителя");
        data.setHeadFirstName("Имя руководителя");
        data.setHeadMiddleName("Отчество руководителя");
        data.setHeadPersonINN("682700908409");
        data.setHeadTitle("Должность руководителя");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setPersonINN("575213293038");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTitle("Должность заявителя");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setDivision("Кем выдан");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, data.clone(false));
    }

    @Test
    void copy_JP_2() {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.JURIDICAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setCommonName("НАИМЕНОВАНИЕ ОРГАНИЗАЦИИ");
        data.setKPP("381001001");
        data.setOrgINN("7710248390");
        data.setOGRN("1084823000360");
        data.setOrgPhone("9998887766");
        data.setStateOrProvinceNameLaw(77);
        data.setLocalityNameLaw("Населенный пункт");
        data.setStreetAddressLaw("Адрес места нахождения");
        data.setHeadLastName("Фамилия руководителя");
        data.setHeadFirstName("Имя руководителя");
        data.setHeadTitle("Должность руководителя");

        data.setIdentificationKindEnum(IdentificationKindEnum.CURRENT_CEP);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setGenderEnum(GenderEnum.F);
        data.setSNILS("08093032045");
        data.setPersonINN("575213293038");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTitle("Должность заявителя");
        data.setTypeEnum(TypeEnum.FID_DOC);
        data.setIssueDate("15.03.2000");
        data.setSeries("0000");
        data.setNumber("000000");
        data.setIssueId("000-000");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, data.clone(false));
    }

    @Test
    void copy_SP_1() {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.SOLE_PROPRIETOR;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setOGRNIP("321774600378760");
        data.setOrgPhone("9998887766");
        data.setCountryName("Россия");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, data.clone(false));
    }

    @Test
    void copy_NP_1() {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.NATURAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setCountryName("Россия");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");
        data.setStreetAddress("Адрес места нахождения");

        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setMiddleName("Отчество заявителя");
        data.setGenderEnum(GenderEnum.M);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        data.setIssueDate("15.03.2000");
        data.setDivision("Кем выдан");
        data.setSeries("1111");
        data.setNumber("222222");
        data.setIssueId("333-333");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, data.clone(false));
    }

    @Test
    void copy_NP_2() {
        EntrepreneurshipEnum entrepreneurshipEnum = EntrepreneurshipEnum.NATURAL_PERSON;
        FormData data = new FormData(entrepreneurshipEnum, false);

        data.setOrgINN("575213293038");
        data.setStateOrProvinceName(77);
        data.setLocalityName("Населенный пункт");

        data.setIdentificationKindEnum(IdentificationKindEnum.CURRENT_CEP);
        data.setLastName("Фамилия заявителя");
        data.setFirstName("Имя заявителя");
        data.setGenderEnum(GenderEnum.F);
        data.setSNILS("08093032045");
        data.setBirthDate("14.02.1978");
        data.setEmailAddress("info@info.ru");
        data.setPersonPhone("1112223344");
        data.setTypeEnum(TypeEnum.FID_DOC);
        data.setIssueDate("15.03.2000");
        data.setSeries("0000");
        data.setNumber("000000");
        data.setIssueId("000-000");

        data.setProduct(entrepreneurshipEnum);

        assertEquals(data, data.clone(false));
    }
}
