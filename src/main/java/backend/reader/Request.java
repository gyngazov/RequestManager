package backend.reader;

import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Request {

    private enum OID {
        ORGANIZATION_NAME("2.5.4.10"),
        COMMON_NAME("2.5.4.3"),
        ORGANIZATIONAL_UNIT_NAME("2.5.4.11"),
        KPP("1.2.840.113549.1.9.2"),
        ORG_INN("1.2.643.100.4"),
        OGRN("1.2.643.100.1"),
        OGRNIP("1.2.643.100.5"),
        COUNTRY_NAME("2.5.4.6"),
        STATE_OR_PROVINCE_NAME("2.5.4.8"),
        LOCALITY_NAME("2.5.4.7"),
        STREET_ADDRESS("2.5.4.9"),

        IDENTIFICATION_KIND("1.2.643.100.114"),
        LAST_NAME("2.5.4.4"),
        GIVEN_NAME("2.5.4.42"),
        SNILS("1.2.643.100.3"),
        PERSON_INN("1.2.643.3.131.1.1"),
        EMAIL_ADDRESS("1.2.840.113549.1.9.1"),
        TITLE("2.5.4.12");

        private final String objectIdentifier;

        OID(String objectIdentifier) {
            this.objectIdentifier = objectIdentifier;
        }
    }

    private final byte[] src;
    private String filename = "unnamed";

    private EntrepreneurshipEnum entrepreneurshipEnum;
    private String organizationName;          // 2.5.4.10
    private String commonName;                // 2.5.4.3
    private String organizationalUnitName;    // 2.5.4.11
    private String orgINN;                    // 1.2.643.100.4
    private String OGRN;                      // 1.2.643.100.1
    private String OGRNIP;                    // 1.2.643.100.5
    private String countryName;               // 2.5.4.6
    private int stateOrProvinceName;          // 2.5.4.8
    private String localityName;              // 2.5.4.7
    private String streetAddress;             // 2.5.4.9

    private int identificationKind;           // 1.2.643.100.114
    private String lastName;                  // 2.5.4.4
    private String givenName;                 // 2.5.4.42
    private String SNILS;                     // 1.2.643.100.3
    private String personINN;                 // 1.2.643.3.131.1.1
    private String emailAddress;              // 1.2.840.113549.1.9.1
    private String title;                     // 2.5.4.12

    private String publicKey;

    public Request(byte[] src) throws Exception {
        this.src = src;

        try (ASN1InputStream asn1InputStream = new ASN1InputStream(Base64.getMimeDecoder().decode(src))) {
            ASN1Primitive object = asn1InputStream.readObject();
            CertificationRequest certificationRequest = CertificationRequest.getInstance(object);
            CertificationRequestInfo certificationRequestInfo = certificationRequest.getCertificationRequestInfo();
            setIdentificationKind(certificationRequestInfo);
            setX500Name(certificationRequestInfo);
            setPublicKey(certificationRequestInfo);

            switch (entrepreneurshipEnum) {
                case JURIDICAL_PERSON -> organizationalUnitName = Objects.requireNonNullElse(organizationalUnitName, "");
                case SOLE_PROPRIETOR, NATURAL_PERSON -> streetAddress = Objects.requireNonNullElse(streetAddress, "");
            }
        }
    }

    public Request(File requestFile) throws Exception {
        this(new TextFileReader(requestFile).readAllBytes());

        this.filename = requestFile.getName();
    }

    private void setIdentificationKind(@NotNull CertificationRequestInfo requestInfo) {
        ASN1Set attributes = requestInfo.getAttributes();
        DLSequence var1 = (DLSequence) attributes.getObjectAt(0);
        DLSet var2 = (DLSet) var1.getObjectAt(1);
        DLSequence var3 = (DLSequence) var2.getObjectAt(0);
        DLSequence var4 = (DLSequence) var3.getObjectAt(4);
        DEROctetString var5 = (DEROctetString) var4.getObjectAt(1);
        identificationKind = var5.getOctets()[2];
    }

    private @Nullable String getValue(@NotNull X500Name x500Name, String objectIdentifier) {
        RDN[] RDNs = x500Name.getRDNs(new ASN1ObjectIdentifier(objectIdentifier));
        return RDNs.length != 1 ? null : RDNs[0].getFirst().getValue().toString();
    }

    private void setX500Name(@NotNull CertificationRequestInfo requestInfo) {
        X500Name x500Name = requestInfo.getSubject();

        organizationName = getValue(x500Name, OID.ORGANIZATION_NAME.objectIdentifier);
        commonName = getValue(x500Name, OID.COMMON_NAME.objectIdentifier);
        organizationalUnitName = getValue(x500Name, OID.ORGANIZATIONAL_UNIT_NAME.objectIdentifier);
        orgINN = getValue(x500Name, OID.ORG_INN.objectIdentifier);
        OGRN = getValue(x500Name, OID.OGRN.objectIdentifier);
        OGRNIP = getValue(x500Name, OID.OGRNIP.objectIdentifier);
        countryName = getValue(x500Name, OID.COUNTRY_NAME.objectIdentifier);
        String stateOrProvinceName = getValue(x500Name, OID.STATE_OR_PROVINCE_NAME.objectIdentifier);
        if (stateOrProvinceName != null) {
            try {
                this.stateOrProvinceName = Integer.parseInt(stateOrProvinceName.substring(0, 2));
            } catch (NumberFormatException e) {
                this.stateOrProvinceName = 0;
            }
        }
        localityName = getValue(x500Name, OID.LOCALITY_NAME.objectIdentifier);
        streetAddress = getValue(x500Name, OID.STREET_ADDRESS.objectIdentifier);

        lastName = getValue(x500Name, OID.LAST_NAME.objectIdentifier);
        givenName = getValue(x500Name, OID.GIVEN_NAME.objectIdentifier);
        SNILS = getValue(x500Name, OID.SNILS.objectIdentifier);
        personINN = getValue(x500Name, OID.PERSON_INN.objectIdentifier);
        emailAddress = getValue(x500Name, OID.EMAIL_ADDRESS.objectIdentifier);
        title = getValue(x500Name, OID.TITLE.objectIdentifier);

        entrepreneurshipEnum = OGRN == null || OGRN.isEmpty() ?
                OGRNIP == null || OGRNIP.isEmpty() ?
                        EntrepreneurshipEnum.NATURAL_PERSON
                        : EntrepreneurshipEnum.SOLE_PROPRIETOR
                : EntrepreneurshipEnum.JURIDICAL_PERSON;
    }

    private void setPublicKey(@NotNull CertificationRequestInfo requestInfo) {
        try {
            publicKey = requestInfo.getSubjectPublicKeyInfo().parsePublicKey().toString();
        } catch (IOException e) {
            publicKey = null;
        }
    }

    public byte[] getSrc() {
        return src;
    }

    public String getFilename() {
        return filename;
    }

    public EntrepreneurshipEnum getEntrepreneurshipEnum() {
        return entrepreneurshipEnum;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
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

    public String getCountryName() {
        return countryName;
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

    public int getIdentificationKind() {
        return identificationKind;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getSNILS() {
        return SNILS;
    }

    public String getPersonINN() {
        return personINN;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "Request{" +
                "entrepreneurshipEnum=" + entrepreneurshipEnum +
                ", organizationName='" + organizationName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", organizationalUnitName='" + organizationalUnitName + '\'' +
                ", orgINN='" + orgINN + '\'' +
                ", OGRN='" + OGRN + '\'' +
                ", OGRNIP='" + OGRNIP + '\'' +
                ", countryName='" + countryName + '\'' +
                ", stateOrProvinceName=" + stateOrProvinceName +
                ", localityName='" + localityName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", identificationKind=" + identificationKind +
                ", lastName='" + lastName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", SNILS='" + SNILS + '\'' +
                ", personINN='" + personINN + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", title='" + title + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
