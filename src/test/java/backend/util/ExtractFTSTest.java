package backend.util;

import backend.reader.extract.ExtractFTS;
import backend.reader.Readable;
import backend.reader.extract.URIE;
import backend.reader.extract.USRLE;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

class ExtractFTSTest {

    private static Readable @NotNull [] getListReaders(String pathname) {
        File[] files = new File(pathname)
                .getAbsoluteFile()
                .listFiles((dir, name) -> name.endsWith(".pdf"));
        assert files != null;
        Readable[] readables = new Readable[files.length];
        for (int i = 0; i < files.length; i++) {
            readables[i] = Readable.of(files[i]);
        }
        return readables;
    }

    private static Readable @NotNull [] getListEGRJUL() {
        return getListReaders("target/test-classes/egrjul");
    }

    private static Readable @NotNull [] getListEGRIP() {
        return getListReaders("target/test-classes/egrip");
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getReleaseDate_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotEquals(LocalDate.of(1970, 1, 1), extractFTS.getDate());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getReleaseDate_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotEquals(LocalDate.of(1970, 1, 1), extractFTS.getDate());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getEntrepreneurshipConstant_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertEquals(EntrepreneurshipEnum.JURIDICAL_PERSON, extractFTS.getEntrepreneurshipEnum());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getEntrepreneurshipConstant_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertEquals(EntrepreneurshipEnum.SOLE_PROPRIETOR, extractFTS.getEntrepreneurshipEnum());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getCommonName_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getCommonName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getKPP_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getKPP());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getOrgINN_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getOrgINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getOrgINN_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((URIE) extractFTS).getOrgINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getOGRN_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getOGRN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getOGRNIP_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((URIE) extractFTS).getOGRNIP());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getIndex_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getIndex());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getStateOrProvinceNameLaw_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotEquals(0, ((USRLE) extractFTS).getStateOrProvinceNameLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getLocalityNameLaw_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getLocalityNameLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getStreetAddressLaw_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getStreetAddressLaw());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadLastName_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getHeadLastName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadFirstName_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getHeadFirstName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadMiddleName_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getHeadMiddleName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadPersonINN_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getHeadPersonINN());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRJUL")
    void getHeadTitle_EGRJUL(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((USRLE) extractFTS).getHeadTitle());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getLastName_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((URIE) extractFTS).getLastName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getFirstName_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((URIE) extractFTS).getFirstName());
    }

    @ParameterizedTest
    @MethodSource(value = "getListEGRIP")
    void getMiddleName_EGRIP(Readable readable) throws IOException {
        ExtractFTS extractFTS = ExtractFTS.of(readable);
        Assertions.assertNotNull(((URIE) extractFTS).getMiddleName());
    }
}
