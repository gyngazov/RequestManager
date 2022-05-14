package backend.window.main.form;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONView;
import backend.iEcp.POSTRequest;
import backend.util.Validatable;
import backend.window.main.filter.constant.StatusEnum;
import backend.window.main.form.constant.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class FormData {
    @Expose(serialize = false, deserialize = false)
    private final boolean verifiable;

    @Expose
    @SerializedName(value = "type")
    private final EntrepreneurshipEnum entrepreneurshipEnum;            // type
    @Expose
    @SerializedName(value = "products")
    private int[] product;                                              // products
    @Expose
    @SerializedName(value = "offerJoining")
    private final boolean offerJoining = true;                          // offerJoining

    @Expose(serialize = false)
    @SerializedName(value = "requestId")
    private Integer requestID;                                          // requestID
    @Expose(serialize = false)
    @SerializedName(value = "createDate")
    private String createDate;                                          // createDate
    @Expose(serialize = false)
    @SerializedName(value = "statusId")
    private StatusEnum statusEnum;                                      // statusId
    @Expose(serialize = false)
    @SerializedName(value = "comment")
    private String comment;                                             // comment

    @Expose
    @SerializedName(value = "company")
    private String commonName;                                          // company
    @Expose()
    @SerializedName(value = "department")
    private String department;                                          // department
    @Expose()
    @SerializedName(value = "kpp")
    private String KPP;                                                 // kpp
    @Expose()
    @SerializedName(value = "inn")
    private String orgINN;                                              // inn
    @Expose()
    @SerializedName(value = "ogrn")
    private String OGRN;                                                // ogrn
    @Expose()
    @SerializedName(value = "ogrnip")
    private String OGRNIP;                                              // ogrnip
    @Expose()
    @SerializedName(value = "companyPhone")
    private String orgPhone;                                            // companyPhone
    @Expose()
    @SerializedName(value = "index")
    private String index;                                               // index
    @Expose(serialize = false, deserialize = false)
    private String countryName;
    @Expose()
    @SerializedName(value = "regionLaw")
    private int stateOrProvinceNameLaw;                                 // regionLaw
    @Expose()
    @SerializedName(value = "cityLaw")
    private String localityNameLaw;                                     // cityLaw
    @Expose()
    @SerializedName(value = "addressLaw")
    private String streetAddressLaw;                                    // addressLaw
    @Expose()
    @SerializedName(value = "region")
    private int stateOrProvinceName;                                    // region
    @Expose()
    @SerializedName(value = "city")
    private String localityName;                                        // city
    @Expose()
    @SerializedName(value = "address")
    private String streetAddress;                                       // address
    @Expose()
    @SerializedName(value = "headLastName")
    private String headLastName;                                        // headLastName
    @Expose()
    @SerializedName(value = "headFirstName")
    private String headFirstName;                                       // headFirstName
    @Expose()
    @SerializedName(value = "headMiddleName")
    private String headMiddleName;                                      // headMiddleName
    @Expose(serialize = false, deserialize = false)
    private String headPersonINN;
    @Expose()
    @SerializedName(value = "headPosition")
    private String headTitle;                                           // headPosition

    @Expose()
    @SerializedName(value = "identificationKind")
    private IdentificationKindEnum identificationKindEnum;              // identificationKind
    @Expose()
    @SerializedName(value = "lastName")
    private String lastName;                                            // lastName
    @Expose()
    @SerializedName(value = "firstName")
    private String firstName;                                           // firstName
    @Expose()
    @SerializedName(value = "middleName")
    private String middleName = "";                                     // middleName
    @Expose()
    @SerializedName(value = "gender")
    private GenderEnum genderEnum;                                      // gender
    @Expose()
    @SerializedName(value = "snils")
    private String SNILS;                                               // snils
    @Expose()
    @SerializedName(value = "personInn")
    private String personINN;                                           // personInn
    @Expose()
    @SerializedName(value = "birthDate")
    private String birthDate;                                           // birthDate
    @Expose()
    @SerializedName(value = "email")
    private String emailAddress;                                        // email
    @Expose()
    @SerializedName(value = "phone")
    private String personPhone;                                         // phone
    @Expose()
    @SerializedName(value = "position")
    private String title;                                               // position
    @Expose(serialize = false, deserialize = false)
    private TypeEnum typeEnum;
    @Expose(serialize = false, deserialize = false)
    private String citizenship;
    @Expose()
    @SerializedName(value = "passportDate")
    private String issueDate;                                           // passportDate
    @Expose()
    @SerializedName(value = "passportDivision")
    private String division = "";                                       // passportDivision
    @Expose()
    @SerializedName(value = "passportSerial")
    private String series;                                              // passportSerial
    @Expose()
    @SerializedName(value = "passportNumber")
    private String number;                                              // passportNumber
    @Expose()
    @SerializedName(value = "passportCode")
    private String issueId;                                             // passportCode

    public FormData(@NotNull EntrepreneurshipEnum entrepreneurshipEnum, boolean verifiable) {
        this.verifiable = verifiable;
        this.entrepreneurshipEnum = entrepreneurshipEnum;

        switch (entrepreneurshipEnum) {
            case JURIDICAL_PERSON -> {
                department = "";
                index = "";
                headMiddleName = "";
            }
            case SOLE_PROPRIETOR, NATURAL_PERSON -> streetAddress = "";
        }
    }

    private @Nullable String getFieldValue(String originalText,
                                           UnaryOperator<String> formattedText, Predicate<String> isFormattedTextCorrect,
                                           String defaultText) {
        return Validatable.isNonBlank(originalText) ?
                verifiable ?
                        isFormattedTextCorrect.test(originalText) ?
                                formattedText.apply(originalText)
                                : null
                        : originalText
                : defaultText;
    }

    private @Nullable String getFieldValue(String originalText,
                                           UnaryOperator<String> operator, Predicate<String> isFormattedTextCorrect) {
        return getFieldValue(originalText, operator, isFormattedTextCorrect, null);
    }

    private void setProduct(int[] product) {
        this.product = product;
    }

    public void setProduct(@NotNull EntrepreneurshipEnum entrepreneurshipEnum) {
        switch (entrepreneurshipEnum) {
            case JURIDICAL_PERSON -> {
                if (Objects.equals(headLastName, lastName)
                        && Objects.equals(headFirstName, firstName)
                        && Objects.equals(headMiddleName, middleName)
                        && Objects.equals(headTitle, title)) {
                    setProduct(new int[]{ProductEnum.P3512.getCode()});
                } else {
                    setProduct(new int[]{ProductEnum.P7904.getCode()});
                }
            }
            case SOLE_PROPRIETOR -> setProduct(new int[]{ProductEnum.P3514.getCode()});
            case NATURAL_PERSON -> setProduct(new int[]{ProductEnum.P3513.getCode()});
        }
    }

    private void setRequestID(@Nullable Integer requestID) {
        this.requestID = requestID;
    }

    private void setStatusEnum(@Nullable StatusEnum statusEnum) {
        this.statusEnum = Objects.requireNonNullElse(statusEnum, StatusEnum.WITHOUT_STATUS);
    }

    private void setStatusEnum(Integer statusId) {
        setStatusEnum(new EnumProvider<>(StatusEnum.values()).get(statusId));
    }

    public void setCommonName(String commonName) {
        this.commonName = getFieldValue(commonName,
                Validatable::getFormattedOrganizationName, Validatable::isCorrectOrganizationName);
    }

    public void setDepartment(String department) {
        this.department = getFieldValue(department,
                Validatable::getFormattedOrganizationName, Validatable::isCorrectOrganizationName,
                "");
    }

    public void setKPP(String KPP) {
        this.KPP = getFieldValue(KPP,
                Validatable::getFormattedKPP, Validatable::isCorrectKPP);
    }

    public void setOrgINN(String orgINN) {
        this.orgINN = getFieldValue(orgINN,
                Validatable::getFormattedOrgINN, Validatable::isCorrectOrgINN);
    }

    public void setOGRN(String OGRN) {
        this.OGRN = getFieldValue(OGRN,
                Validatable::getFormattedOGRN, Validatable::isCorrectOGRN);
    }

    public void setOGRNIP(String OGRNIP) {
        this.OGRNIP = getFieldValue(OGRNIP,
                Validatable::getFormattedOGRNIP, Validatable::isCorrectOGRNIP);
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = getFieldValue(orgPhone,
                Validatable::getFormattedPhone, Validatable::isCorrectPhone);
    }

    public void setIndex(String index) {
        this.index = getFieldValue(index,
                Validatable::getFormattedIndex, Validatable::isCorrectIndex,
                "");
    }

    public void setCountryName(String countryName) {
        this.countryName = getFieldValue(countryName,
                Validatable::getFormattedCountryName, anyText -> true);
    }

    public void setStateOrProvinceNameLaw(int stateOrProvinceNameLaw) {
        this.stateOrProvinceNameLaw = stateOrProvinceNameLaw;
    }

    public void setLocalityNameLaw(String localityNameLaw) {
        this.localityNameLaw = getFieldValue(localityNameLaw,
                Validatable::getFormattedLocalityName, anyText -> true);
    }

    public void setStreetAddressLaw(String streetAddressLaw) {
        this.streetAddressLaw = getFieldValue(streetAddressLaw,
                Validatable::getFormattedStreetAddress, anyText -> true);
    }

    public void setStateOrProvinceName(int stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = getFieldValue(localityName,
                Validatable::getFormattedLocalityName, anyText -> true);
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = getFieldValue(streetAddress,
                Validatable::getFormattedStreetAddress, anyText -> true,
                "");
    }

    public void setHeadLastName(String headLastName) {
        this.headLastName = getFieldValue(headLastName,
                Validatable::getFormattedPersonLastName, Validatable::isCorrectPersonLastName);
    }

    public void setHeadFirstName(String headFirstName) {
        this.headFirstName = getFieldValue(headFirstName,
                Validatable::getFormattedPersonFirstName, Validatable::isCorrectPersonFirstName);
    }

    public void setHeadMiddleName(String headMiddleName) {
        this.headMiddleName = getFieldValue(headMiddleName,
                Validatable::getFormattedPersonMiddleName, Validatable::isCorrectPersonMiddleName,
                "");
    }

    public void setHeadPersonINN(String headPersonINN) {
        this.headPersonINN = getFieldValue(headPersonINN,
                Validatable::getFormattedPersonINN, Validatable::isCorrectPersonINN);
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = getFieldValue(headTitle,
                Validatable::getFormattedPersonTitle, Validatable::isCorrectPersonTitle);
    }

    public void setIdentificationKindEnum(IdentificationKindEnum identificationKindEnum) {
        this.identificationKindEnum = identificationKindEnum;
    }

    public void setLastName(String lastName) {
        this.lastName = getFieldValue(lastName,
                Validatable::getFormattedPersonLastName, Validatable::isCorrectPersonLastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = getFieldValue(firstName,
                Validatable::getFormattedPersonFirstName, Validatable::isCorrectPersonFirstName);

    }

    public void setMiddleName(String middleName) {
        this.middleName = getFieldValue(middleName,
                Validatable::getFormattedPersonMiddleName, Validatable::isCorrectPersonMiddleName,
                "");
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public void setSNILS(String SNILS) {
        this.SNILS = getFieldValue(SNILS,
                Validatable::getFormattedSNILS, Validatable::isCorrectSNILS);
    }

    public void setPersonINN(String personINN) {
        this.personINN = getFieldValue(personINN,
                Validatable::getFormattedPersonINN, Validatable::isCorrectPersonINN);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = getFieldValue(birthDate,
                Validatable::getFormattedDate, Validatable::isCorrectBirthDate);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = getFieldValue(emailAddress,
                Validatable::getFormattedEmailAddress, Validatable::isCorrectEmailAddress);
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = getFieldValue(personPhone,
                Validatable::getFormattedPhone, Validatable::isCorrectPhone);
    }

    public void setTitle(String title) {
        this.title = getFieldValue(title,
                Validatable::getFormattedPersonTitle, Validatable::isCorrectPersonTitle);
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = getFieldValue(citizenship,
                Validatable::getFormattedCitizenship, Validatable::isCorrectCitizenship);
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = getFieldValue(issueDate,
                Validatable::getFormattedDate, date -> Validatable.isCorrectIssueDate(date, birthDate));
    }

    public void setDivision(String division) {
        this.division = getFieldValue(division,
                Validatable::getFormattedDivision, anyText -> true,
                "");
    }

    public void setSeries(String series) {
        this.series = getFieldValue(series,
                Validatable::getFormattedSeries, s -> Validatable.isCorrectSeries(s, typeEnum));
    }

    public void setNumber(String number) {
        this.number = getFieldValue(number,
                Validatable::getFormattedNumber, n -> Validatable.isCorrectNumber(n, typeEnum));
    }

    public void setIssueId(String issueId) {
        this.issueId = getFieldValue(issueId,
                Validatable::getFormattedIssueId, id -> Validatable.isCorrectIssueId(id, typeEnum));
    }

    public boolean isVerifiable() {
        return verifiable;
    }

    public EntrepreneurshipEnum getEntrepreneurshipEnum() {
        return entrepreneurshipEnum;
    }

    public int[] getProduct() {
        return product;
    }

    public boolean isOfferJoining() {
        return offerJoining;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public String getComment() {
        return comment;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getDepartment() {
        return department;
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

    public String getOrgPhone() {
        return orgPhone;
    }

    public String getIndex() {
        return index;
    }

    public String getCountryName() {
        return countryName;
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

    public int getStateOrProvinceName() {
        return stateOrProvinceName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public String getStreetAddress() {
        return streetAddress;
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

    public IdentificationKindEnum getIdentificationKindEnum() {
        return identificationKindEnum;
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

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public String getSNILS() {
        return SNILS;
    }

    public String getPersonINN() {
        return personINN;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public String getTitle() {
        return title;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getDivision() {
        return division;
    }

    public String getSeries() {
        return series;
    }

    public String getNumber() {
        return number;
    }

    public String getIssueId() {
        return issueId;
    }

    public @NotNull FormData clone(boolean verifiable) {
        FormData clone = new FormData(entrepreneurshipEnum, verifiable);

        clone.product = product;

        clone.commonName = commonName;
        clone.department = department;
        clone.KPP = KPP;
        clone.orgINN = orgINN;
        clone.OGRN = OGRN;
        clone.OGRNIP = OGRNIP;
        clone.orgPhone = orgPhone;
        clone.index = index;
        clone.countryName = countryName;
        clone.stateOrProvinceNameLaw = stateOrProvinceNameLaw;
        clone.localityNameLaw = localityNameLaw;
        clone.streetAddressLaw = streetAddressLaw;
        clone.stateOrProvinceName = stateOrProvinceName;
        clone.localityName = localityName;
        clone.streetAddress = streetAddress;
        clone.headLastName = headLastName;
        clone.headFirstName = headFirstName;
        clone.headMiddleName = headMiddleName;
        clone.headPersonINN = headPersonINN;
        clone.headTitle = headTitle;

        clone.identificationKindEnum = identificationKindEnum;
        clone.lastName = lastName;
        clone.firstName = firstName;
        clone.middleName = middleName;
        clone.genderEnum = genderEnum;
        clone.SNILS = SNILS;
        clone.personINN = personINN;
        clone.birthDate = birthDate;
        clone.emailAddress = emailAddress;
        clone.personPhone = personPhone;
        clone.title = title;
        clone.typeEnum = typeEnum;
        clone.citizenship = citizenship;
        clone.issueDate = issueDate;
        clone.division = division;
        clone.series = series;
        clone.number = number;
        clone.issueId = issueId;

        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormData data = (FormData) o;
        return entrepreneurshipEnum == data.entrepreneurshipEnum
                && Arrays.equals(product, data.product)
                && offerJoining == data.offerJoining

                && Objects.equals(commonName, data.commonName)
                && Objects.equals(department, data.department)
                && Objects.equals(KPP, data.KPP)
                && Objects.equals(orgINN, data.orgINN)
                && Objects.equals(OGRN, data.OGRN)
                && Objects.equals(OGRNIP, data.OGRNIP)
                && Objects.equals(orgPhone, data.orgPhone)
                && Objects.equals(index, data.index)
                && Objects.equals(countryName, data.countryName)
                && stateOrProvinceNameLaw == data.stateOrProvinceNameLaw
                && Objects.equals(localityNameLaw, data.localityNameLaw)
                && Objects.equals(streetAddressLaw, data.streetAddressLaw)
                && stateOrProvinceName == data.stateOrProvinceName
                && Objects.equals(localityName, data.localityName)
                && Objects.equals(streetAddress, data.streetAddress)
                && Objects.equals(headLastName, data.headLastName)
                && Objects.equals(headFirstName, data.headFirstName)
                && Objects.equals(headMiddleName, data.headMiddleName)
                && Objects.equals(headPersonINN, data.headPersonINN)
                && Objects.equals(headTitle, data.headTitle)

                && identificationKindEnum == data.identificationKindEnum
                && Objects.equals(lastName, data.lastName)
                && Objects.equals(firstName, data.firstName)
                && Objects.equals(middleName, data.middleName)
                && genderEnum == data.genderEnum
                && Objects.equals(SNILS, data.SNILS)
                && Objects.equals(personINN, data.personINN)
                && Objects.equals(birthDate, data.birthDate)
                && Objects.equals(emailAddress, data.emailAddress)
                && Objects.equals(personPhone, data.personPhone)
                && Objects.equals(title, data.title)
                && typeEnum == data.typeEnum
                && Objects.equals(citizenship, data.citizenship)
                && Objects.equals(issueDate, data.issueDate)
                && Objects.equals(division, data.division)
                && Objects.equals(series, data.series)
                && Objects.equals(number, data.number)
                && Objects.equals(issueId, data.issueId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(entrepreneurshipEnum,
                offerJoining,
                commonName,
                department,
                KPP,
                orgINN,
                OGRN,
                OGRNIP,
                orgPhone,
                index,
                countryName,
                stateOrProvinceNameLaw,
                localityNameLaw,
                streetAddressLaw,
                stateOrProvinceName,
                localityName,
                streetAddress,
                headLastName,
                headFirstName,
                headMiddleName,
                headPersonINN,
                headTitle,
                identificationKindEnum,
                lastName,
                firstName,
                middleName,
                genderEnum,
                SNILS,
                personINN,
                birthDate,
                emailAddress,
                personPhone,
                title,
                typeEnum,
                citizenship,
                issueDate,
                division,
                series,
                number,
                issueId);
        return 31 * result + Arrays.hashCode(product);
    }

    @Override
    public String toString() {
        return "FormData{" + "verifiable=" + verifiable +
                ", entrepreneurshipEnum=" + entrepreneurshipEnum +
                ", product=" + Arrays.toString(product) +
                ", offerJoining=" + offerJoining +
                ", commonName='" + commonName + '\'' +
                ", department='" + department + '\'' +
                ", KPP='" + KPP + '\'' +
                ", orgINN='" + orgINN + '\'' +
                ", OGRN='" + OGRN + '\'' +
                ", OGRNIP='" + OGRNIP + '\'' +
                ", orgPhone='" + orgPhone + '\'' +
                ", index='" + index + '\'' +
                ", countryName='" + countryName + '\'' +
                ", stateOrProvinceNameLaw=" + stateOrProvinceNameLaw +
                ", localityNameLaw='" + localityNameLaw + '\'' +
                ", streetAddressLaw='" + streetAddressLaw + '\'' +
                ", stateOrProvinceName=" + stateOrProvinceName +
                ", localityName='" + localityName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", headLastName='" + headLastName + '\'' +
                ", headFirstName='" + headFirstName + '\'' +
                ", headMiddleName='" + headMiddleName + '\'' +
                ", headPersonINN='" + headPersonINN + '\'' +
                ", headTitle='" + headTitle + '\'' +
                ", identificationKindEnum=" + identificationKindEnum +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", genderEnum=" + genderEnum +
                ", SNILS='" + SNILS + '\'' +
                ", personINN='" + personINN + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", personPhone='" + personPhone + '\'' +
                ", title='" + title + '\'' +
                ", typeEnum=" + typeEnum +
                ", citizenship='" + citizenship + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", division='" + division + '\'' +
                ", series='" + series + '\'' +
                ", number='" + number + '\'' +
                ", issueId='" + issueId + '\'' +
                '}';
    }

    public static @NotNull FormData generateByDefault(@NotNull EntrepreneurshipEnum entrepreneurshipEnum) {
        FormData data = new FormData(entrepreneurshipEnum, true);
        data.setCountryName("Россия");
        data.setStateOrProvinceNameLaw(77);
        data.setStateOrProvinceName(77);
        data.setIdentificationKindEnum(IdentificationKindEnum.PERSONALLY);
        data.setGenderEnum(GenderEnum.M);
        data.setTypeEnum(TypeEnum.RF_PASSPORT);
        data.setCitizenship("RUS");
        return data;
    }

    public static @NotNull FormData generateOnRequestID(int requestID) throws IOException {
        POSTRequest request = new POSTRequest(POSTRequest.VIEW_REQUEST, new Gson().toJson(new JSONView(requestID)));
        if (request.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            JsonObject externalObject = JsonParser.parseString(request.getResponse()).getAsJsonObject();

            FormData data = new Gson().fromJson(externalObject.getAsJsonObject("info"), FormData.class);
            data.setRequestID(externalObject.get("requestId").getAsInt());
            data.setStatusEnum(externalObject.get("statusId").getAsInt());
            data.setTypeEnum(Objects.equals(Validatable.getFormattedSeries(data.getSeries()), TypeEnum.FID_DOC_SERIES)
                    && Objects.equals(Validatable.getFormattedNumber(data.getNumber()), TypeEnum.FID_DOC_NUMBER)
                    && Objects.equals(Validatable.getFormattedIssueId(data.getIssueId()), TypeEnum.FID_DOC_ISSUE_ID)
                    ? TypeEnum.FID_DOC : TypeEnum.RF_PASSPORT);
            data.setCitizenship(data.getTypeEnum() == TypeEnum.RF_PASSPORT ? TypeEnum.CITIZENSHIP_RF : null);
            return data;
        } else {
            throw new BadRequestException(request.getResponse());
        }
    }
}
