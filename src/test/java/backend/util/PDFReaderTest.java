package backend.util;

import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

class PDFReaderTest {

    private static File[] getListEGRJUL() {
        return new File("target/test-classes/egrjul")
                .getAbsoluteFile()
                .listFiles((dir, name) -> name.endsWith(".pdf"));
    }

    private static File[] getListEGRIP() {
        return new File("target/test-classes/egrip")
                .getAbsoluteFile()
                .listFiles((dir, name) -> name.endsWith(".pdf"));
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getReleaseDate_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotEquals(LocalDate.of(1970, 1, 1), pdfReader.getReleaseDate());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getReleaseDate_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotEquals(LocalDate.of(1970, 1, 1), pdfReader.getReleaseDate());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getEntrepreneurshipConstant_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertEquals(EntrepreneurshipEnum.JURIDICAL_PERSON, pdfReader.getEntrepreneurshipConstant());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getEntrepreneurshipConstant_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertEquals(EntrepreneurshipEnum.SOLE_PROPRIETOR, pdfReader.getEntrepreneurshipConstant());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getCommonName_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getCommonName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getKPP_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getKPP());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getOrgINN_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getOrgINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getOrgINN_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getOrgINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getOGRN_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getOGRN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getOGRNIP_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getOGRNIP());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getIndex_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getIndex());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getStateOrProvinceNameLaw_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotEquals(0, pdfReader.getStateOrProvinceNameLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getLocalityNameLaw_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getLocalityNameLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getStreetAddressLaw_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getStreetAddressLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadLastName_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getHeadLastName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadFirstName_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getHeadFirstName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadMiddleName_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getHeadMiddleName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadPersonINN_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getHeadPersonINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadTitle_EGRJUL(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getHeadTitle());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getLastName_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getLastName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getFirstName_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getFirstName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getMiddleName_EGRIP(File file) throws IOException {
        PDFReader pdfReader = new PDFReader(file);
        Assertions.assertNotNull(pdfReader.getMiddleName());
    }
}
