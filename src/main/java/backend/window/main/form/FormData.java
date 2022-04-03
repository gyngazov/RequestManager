package backend.window.main.form;

import backend.exception.BadRequestException;
import backend.iEcp.JSON.JSONView;
import backend.iEcp.POSTRequest;
import backend.util.Validation;
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

    private boolean nonBlankString(String text) {
        return text != null && !text.isBlank();
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
        if (nonBlankString(commonName)) {
            if (verifiable) {
                commonName = Validation.getFormattedOrganizationName(commonName);
                if (Validation.isCorrectOrganizationName(commonName)) {
                    this.commonName = commonName;
                }
            } else {
                this.commonName = commonName;
            }
        }
    }

    public void setDepartment(String department) {
        if (nonBlankString(department)) {
            if (verifiable) {
                department = Validation.getFormattedOrganizationName(department);
                if (Validation.isCorrectOrganizationName(department)) {
                    this.department = department;
                }
            } else {
                this.department = department;
            }
        }
    }

    public void setKPP(String KPP) {
        if (nonBlankString(KPP)) {
            if (verifiable) {
                KPP = Validation.getFormattedKPP(KPP);
                if (Validation.isCorrectKPP(KPP)) {
                    this.KPP = KPP;
                }
            } else {
                this.KPP = KPP;
            }
        }
    }

    public void setOrgINN(String orgINN) {
        if (nonBlankString(orgINN)) {
            if (verifiable) {
                orgINN = Validation.getFormattedOrgINN(orgINN);
                if (Validation.isCorrectOrgINN(orgINN)) {
                    this.orgINN = orgINN;
                }
            } else {
                this.orgINN = orgINN;
            }
        }
    }

    public void setOGRN(String OGRN) {
        if (nonBlankString(OGRN)) {
            if (verifiable) {
                OGRN = Validation.getFormattedOGRN(OGRN);
                if (Validation.isCorrectOGRN(OGRN)) {
                    this.OGRN = OGRN;
                }
            } else {
                this.OGRN = OGRN;
            }
        }
    }

    public void setOGRNIP(String OGRNIP) {
        if (nonBlankString(OGRNIP)) {
            if (verifiable) {
                OGRNIP = Validation.getFormattedOGRNIP(OGRNIP);
                if (Validation.isCorrectOGRNIP(OGRNIP)) {
                    this.OGRNIP = OGRNIP;
                }
            } else {
                this.OGRNIP = OGRNIP;
            }
        }
    }

    public void setOrgPhone(String orgPhone) {
        if (nonBlankString(orgPhone)) {
            if (verifiable) {
                orgPhone = Validation.getFormattedPhone(orgPhone);
                if (Validation.isCorrectPhone(orgPhone)) {
                    this.orgPhone = orgPhone;
                }
            } else {
                this.orgPhone = orgPhone;
            }
        }
    }

    public void setIndex(String index) {
        if (nonBlankString(index)) {
            if (verifiable) {
                index = Validation.getFormattedIndex(index);
                if (Validation.isCorrectIndex(index)) {
                    this.index = index;
                }
            } else {
                this.index = index;
            }
        }
    }

    public void setCountryName(String countryName) {
        if (nonBlankString(countryName)) {
            if (verifiable) {
                this.countryName = Validation.getFormattedCountryName(countryName);
            } else {
                this.countryName = countryName;
            }
        }
    }

    public void setStateOrProvinceNameLaw(int stateOrProvinceNameLaw) {
        this.stateOrProvinceNameLaw = stateOrProvinceNameLaw;
    }

    public void setLocalityNameLaw(String localityNameLaw) {
        if (nonBlankString(localityNameLaw)) {
            if (verifiable) {
                this.localityNameLaw = Validation.getFormattedLocalityName(localityNameLaw);
            } else {
                this.localityNameLaw = localityNameLaw;
            }
        }
    }

    public void setStreetAddressLaw(String streetAddressLaw) {
        if (nonBlankString(streetAddressLaw)) {
            if (verifiable) {
                this.streetAddressLaw = Validation.getFormattedStreetAddress(streetAddressLaw);
            } else {
                this.streetAddressLaw = streetAddressLaw;
            }
        }
    }

    public void setStateOrProvinceName(int stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }

    public void setLocalityName(String localityName) {
        if (nonBlankString(localityName)) {
            if (verifiable) {
                this.localityName = Validation.getFormattedLocalityName(localityName);
            } else {
                this.localityName = localityName;
            }
        }
    }

    public void setStreetAddress(String streetAddress) {
        if (nonBlankString(streetAddress)) {
            if (verifiable) {
                this.streetAddress = Validation.getFormattedStreetAddress(streetAddress);
            } else {
                this.streetAddress = streetAddress;
            }
        }
    }

    public void setHeadLastName(String headLastName) {
        if (nonBlankString(headLastName)) {
            if (verifiable) {
                headLastName = Validation.getFormattedPersonLastName(headLastName);
                if (Validation.isCorrectPersonLastName(headLastName)) {
                    this.headLastName = headLastName;
                }
            } else {
                this.headLastName = headLastName;
            }
        }
    }

    public void setHeadFirstName(String headFirstName) {
        if (nonBlankString(headFirstName)) {
            if (verifiable) {
                headFirstName = Validation.getFormattedPersonFirstName(headFirstName);
                if (Validation.isCorrectPersonFirstName(headFirstName)) {
                    this.headFirstName = headFirstName;
                }
            } else {
                this.headFirstName = headFirstName;
            }
        }
    }

    public void setHeadMiddleName(String headMiddleName) {
        if (nonBlankString(headMiddleName)) {
            if (verifiable) {
                headMiddleName = Validation.getFormattedPersonMiddleName(headMiddleName);
                if (Validation.isCorrectPersonMiddleName(headMiddleName)) {
                    this.headMiddleName = headMiddleName;
                }
            } else {
                this.headMiddleName = headMiddleName;
            }
        }
    }

    public void setHeadPersonINN(String headPersonINN) {
        if (nonBlankString(headPersonINN)) {
            if (verifiable) {
                headPersonINN = Validation.getFormattedPersonINN(headPersonINN);
                if (Validation.isCorrectPersonINN(headPersonINN)) {
                    this.headPersonINN = headPersonINN;
                }
            } else {
                this.headPersonINN = headPersonINN;
            }
        }
    }

    public void setHeadTitle(String headTitle) {
        if (nonBlankString(headTitle)) {
            if (verifiable) {
                headTitle = Validation.getFormattedPersonTitle(headTitle);
                if (Validation.isCorrectPersonTitle(headTitle)) {
                    this.headTitle = headTitle;
                }
            } else {
                this.headTitle = headTitle;
            }
        }
    }

    public void setIdentificationKindEnum(IdentificationKindEnum identificationKindEnum) {
        this.identificationKindEnum = identificationKindEnum;
    }

    public void setLastName(String lastName) {
        if (nonBlankString(lastName)) {
            if (verifiable) {
                lastName = Validation.getFormattedPersonLastName(lastName);
                if (Validation.isCorrectPersonLastName(lastName)) {
                    this.lastName = lastName;
                }
            } else {
                this.lastName = lastName;
            }
        }
    }

    public void setFirstName(String firstName) {
        if (nonBlankString(firstName)) {
            if (verifiable) {
                firstName = Validation.getFormattedPersonFirstName(firstName);
                if (Validation.isCorrectPersonFirstName(firstName)) {
                    this.firstName = firstName;
                }
            } else {
                this.firstName = firstName;
            }
        }
    }

    public void setMiddleName(String middleName) {
        if (nonBlankString(middleName)) {
            if (verifiable) {
                middleName = Validation.getFormattedPersonMiddleName(middleName);
                if (Validation.isCorrectPersonMiddleName(middleName)) {
                    this.middleName = middleName;
                }
            } else {
                this.middleName = middleName;
            }
        }
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public void setSNILS(String SNILS) {
        if (nonBlankString(SNILS)) {
            if (verifiable) {
                SNILS = Validation.getFormattedSNILS(SNILS);
                if (Validation.isCorrectSNILS(SNILS)) {
                    this.SNILS = SNILS;
                }
            } else {
                this.SNILS = SNILS;
            }
        }
    }

    public void setPersonINN(String personINN) {
        if (nonBlankString(personINN)) {
            if (verifiable) {
                personINN = Validation.getFormattedPersonINN(personINN);
                if (Validation.isCorrectPersonINN(personINN)) {
                    this.personINN = personINN;
                }
            } else {
                this.personINN = personINN;
            }
        }
    }

    public void setBirthDate(String birthDate) {
        if (nonBlankString(birthDate)) {
            if (verifiable) {
                birthDate = Validation.getFormattedDate(birthDate);
                if (Validation.isCorrectBirthDate(birthDate)) {
                    this.birthDate = birthDate;
                }
            } else {
                this.birthDate = birthDate;
            }
        }
    }

    public void setEmailAddress(String emailAddress) {
        if (nonBlankString(emailAddress)) {
            if (verifiable) {
                emailAddress = Validation.getFormattedEmailAddress(emailAddress);
                if (Validation.isCorrectEmailAddress(emailAddress)) {
                    this.emailAddress = emailAddress;
                }
            } else {
                this.emailAddress = emailAddress;
            }
        }
    }

    public void setPersonPhone(String personPhone) {
        if (nonBlankString(personPhone)) {
            if (verifiable) {
                personPhone = Validation.getFormattedPhone(personPhone);
                if (Validation.isCorrectPhone(personPhone)) {
                    this.personPhone = personPhone;
                }
            } else {
                this.personPhone = personPhone;
            }
        }
    }

    public void setTitle(String title) {
        if (nonBlankString(title)) {
            if (verifiable) {
                title = Validation.getFormattedPersonTitle(title);
                if (Validation.isCorrectPersonTitle(title)) {
                    this.title = title;
                }
            } else {
                this.title = title;
            }
        }
    }

    public void setCitizenship(String citizenship) {
        if (nonBlankString(citizenship)) {
            if (verifiable) {
                citizenship = Validation.getFormattedCitizenship(citizenship);
                if (Validation.isCorrectCitizenship(citizenship)) {
                    this.citizenship = citizenship;
                }
            } else {
                this.citizenship = citizenship;
            }
        }
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public void setIssueDate(String issueDate) {
        if (nonBlankString(issueDate)) {
            if (verifiable) {
                if (nonBlankString(birthDate)) {
                    issueDate = Validation.getFormattedDate(issueDate);
                    if (Validation.isCorrectIssueDate(issueDate, birthDate)) {
                        this.issueDate = issueDate;
                    }
                }
            } else {
                this.issueDate = issueDate;
            }
        }
    }

    public void setDivision(String division) {
        if (nonBlankString(division)) {
            if (verifiable) {
                this.division = Validation.getFormattedDivision(division);
            } else {
                this.division = division;
            }
        }
    }

    public void setSeries(String series) {
        if (nonBlankString(series)) {
            if (verifiable) {
                series = Validation.getFormattedSeries(series);
                if (Validation.isCorrectSeries(series, typeEnum)) {
                    this.series = series;
                }
            } else {
                this.series = series;
            }
        }
    }

    public void setNumber(String number) {
        if (nonBlankString(number)) {
            if (verifiable) {
                number = Validation.getFormattedNumber(number);
                if (Validation.isCorrectNumber(number, typeEnum)) {
                    this.number = number;
                }
            } else {
                this.number = number;
            }
        }
    }

    public void setIssueId(String issueId) {
        if (nonBlankString(issueId)) {
            if (verifiable) {
                issueId = Validation.getFormattedIssueId(issueId);
                if (Validation.isCorrectIssueId(issueId, typeEnum)) {
                    this.issueId = issueId;
                }
            } else {
                this.issueId = issueId;
            }
        }
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
        result = 31 * result + Arrays.hashCode(product);
        return result;
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
            Integer ID = externalObject.get("requestId").getAsInt();
            Integer statusID = externalObject.get("statusId").getAsInt();

            JsonObject internalObject = externalObject.getAsJsonObject("info");

            FormData data = new Gson().fromJson(internalObject, FormData.class);
            data.setRequestID(ID);
            data.setStatusEnum(statusID);
            data.setTypeEnum(Objects.equals(Validation.getFormattedSeries(data.getSeries()), TypeEnum.FID_DOC_SERIES)
                    && Objects.equals(Validation.getFormattedNumber(data.getNumber()), TypeEnum.FID_DOC_NUMBER)
                    && Objects.equals(Validation.getFormattedIssueId(data.getIssueId()), TypeEnum.FID_DOC_ISSUE_ID) ?
                    TypeEnum.FID_DOC
                    : TypeEnum.RF_PASSPORT);
            data.setCitizenship(data.getTypeEnum() == TypeEnum.RF_PASSPORT ? TypeEnum.CITIZENSHIP_RF : null);
            return data;
        } else {
            throw new BadRequestException(request.getResponse());
        }
    }

    public static @NotNull FormData copy(@NotNull FormData data) {
        FormData copied = new FormData(data.getEntrepreneurshipEnum(), false);

        copied.setProduct(data.getProduct());

        copied.setCommonName(data.getCommonName());
        copied.setDepartment(data.getDepartment());
        copied.setKPP(data.getKPP());
        copied.setOrgINN(data.getOrgINN());
        copied.setOGRN(data.getOGRN());
        copied.setOGRNIP(data.getOGRNIP());
        copied.setOrgPhone(data.getOrgPhone());
        copied.setIndex(data.getIndex());
        copied.setCountryName(data.getCountryName());
        copied.setStateOrProvinceNameLaw(data.getStateOrProvinceNameLaw());
        copied.setLocalityNameLaw(data.getLocalityNameLaw());
        copied.setStreetAddressLaw(data.getStreetAddressLaw());
        copied.setStateOrProvinceName(data.getStateOrProvinceName());
        copied.setLocalityName(data.getLocalityName());
        copied.setStreetAddress(data.getStreetAddress());
        copied.setHeadLastName(data.getHeadLastName());
        copied.setHeadFirstName(data.getHeadFirstName());
        copied.setHeadMiddleName(data.getHeadMiddleName());
        copied.setHeadPersonINN(data.getHeadPersonINN());
        copied.setHeadTitle(data.getHeadTitle());

        copied.setIdentificationKindEnum(data.getIdentificationKindEnum());
        copied.setLastName(data.getLastName());
        copied.setFirstName(data.getFirstName());
        copied.setMiddleName(data.getMiddleName());
        copied.setGenderEnum(data.getGenderEnum());
        copied.setSNILS(data.getSNILS());
        copied.setPersonINN(data.getPersonINN());
        copied.setBirthDate(data.getBirthDate());
        copied.setEmailAddress(data.getEmailAddress());
        copied.setPersonPhone(data.getPersonPhone());
        copied.setTitle(data.getTitle());
        copied.setTypeEnum(data.getTypeEnum());
        copied.setCitizenship(data.getCitizenship());
        copied.setIssueDate(data.getIssueDate());
        copied.setDivision(data.getDivision());
        copied.setSeries(data.getSeries());
        copied.setNumber(data.getNumber());
        copied.setIssueId(data.getIssueId());

        return copied;
    }
}
