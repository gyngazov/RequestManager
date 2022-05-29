package backend.reader;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Certificate {
    private String publicKey;

    public Certificate(byte[] src) throws Exception {
        try (ASN1InputStream asn1InputStream = new ASN1InputStream(src)) {
            ASN1Primitive object = asn1InputStream.readObject();
            org.bouncycastle.asn1.x509.Certificate certificate = org.bouncycastle.asn1.x509.Certificate.getInstance(object);
            setPublicKey(certificate);
        }
    }

    public Certificate(File requestFile) throws Exception {
        this(new TextFileReader(requestFile).readAllBytes());
    }

    private void setPublicKey(@NotNull org.bouncycastle.asn1.x509.Certificate certificate) {
        SubjectPublicKeyInfo subjectPublicKeyInfo = certificate.getSubjectPublicKeyInfo();
        try {
            publicKey = subjectPublicKeyInfo.parsePublicKey().toString();
        } catch (IOException e) {
            publicKey = null;
        }
    }

    public @Nullable String getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "publicKey='" + publicKey + '\'' +
                '}';
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Укажите путь к сертификату: ");
            try {
                System.out.println(new Certificate(new File(scanner.nextLine())));
            } catch (Exception e) {
                System.err.println("Произошла ошибка при попытке чтения указанного файла.");
            }
        }
    }
}
