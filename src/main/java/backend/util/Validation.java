package backend.util;

import backend.window.main.form.constant.TypeEnum;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class Validation {

    private Validation() {
    }

    private static String replaceSpaces(@NotNull String text) {
        return text.replaceAll("\\s+", " ").strip();
    }

    private static String replaceQuotes(@NotNull String text) {
        return text.replaceAll("[\"'\\\\«»‘’‚‛“”„‟‹›]+", "'");
    }

    private static boolean matches(String regex, String text) {
        return Pattern.compile(regex, Pattern.MULTILINE).matcher(text).matches();
    }

    public static String getFormattedOrganizationName(@NotNull String organizationName) {
        return replaceQuotes(replaceSpaces(organizationName.toUpperCase()));
    }

    public static String getFormattedKPP(String KPP) {
        return replaceSpaces(KPP);
    }

    public static String getFormattedOrgINN(String orgINN) {
        return replaceSpaces(orgINN);
    }

    public static String getFormattedOGRN(String OGRN) {
        return replaceSpaces(OGRN);
    }

    public static String getFormattedOGRNIP(String OGRNIP) {
        return getFormattedOGRN(OGRNIP);
    }

    public static String getFormattedPhone(String phone) {
        return replaceSpaces(phone);
    }

    public static String getFormattedIndex(String index) {
        return replaceSpaces(index);
    }

    public static String getFormattedCountryName(String countryName) {
        return replaceSpaces(countryName);
    }

    public static String getFormattedLocalityName(String localityName) {
        return replaceQuotes(replaceSpaces(localityName));
    }

    public static String getFormattedStreetAddress(String streetAddress) {
        return replaceQuotes(replaceSpaces(streetAddress));
    }

    public static String getFormattedPersonLastName(String personLastName) {
        return replaceSpaces(personLastName);
    }

    public static String getFormattedPersonFirstName(String personFirstName) {
        return replaceSpaces(personFirstName);
    }

    public static String getFormattedPersonMiddleName(String personMiddleName) {
        return replaceSpaces(personMiddleName);
    }

    public static String getFormattedPersonINN(String personINN) {
        return replaceSpaces(personINN);
    }

    public static String getFormattedPersonTitle(String personTitle) {
        return replaceQuotes(replaceSpaces(personTitle));
    }

    public static String getFormattedSNILS(String SNILS) {
        return replaceSpaces(SNILS).replaceAll("[ \\-]+", "");
    }

    public static String getFormattedDate(String date) {
        return replaceSpaces(date).replaceAll("[\\-/]+", ".");
    }

    public static String getFormattedEmailAddress(String emailAddress) {
        return replaceSpaces(emailAddress);
    }

    public static String getFormattedCitizenship(String citizenship) {
        return replaceQuotes(replaceSpaces(citizenship.toUpperCase()));
    }

    public static String getFormattedDivision(String division) {
        return replaceQuotes(replaceSpaces(division));
    }

    public static String getFormattedSeries(String series) {
        return replaceSpaces(series);
    }

    public static String getFormattedNumber(String number) {
        return replaceSpaces(number);
    }

    public static String getFormattedIssueId(String issueId) {
        return replaceSpaces(issueId).replaceAll("[ \\-]+", "");
    }

    public static String getFormattedRequestId(String requestId) {
        return replaceSpaces(requestId);
    }

    public static String getFormattedCurrentDirectory(String currentDirectory) {
        return replaceSpaces(currentDirectory);
    }

    public static boolean isCorrectOrganizationName(String organizationName) {
        return matches("^(?=[А-ЯЁ ]{3,})[ '()+,\\-.0-9А-ЯЁ]{5,}$", getFormattedOrganizationName(organizationName));
    }

    public static boolean isCorrectKPP(String KPP) {
        return matches("^[0-9]{4}[0-9A-Z]{2}[0-9]{3}$", getFormattedKPP(KPP));
    }

    public static boolean isCorrectOrgINN(String orgINN) {
        orgINN = getFormattedOrgINN(orgINN);
        if (orgINN.length() == 10) {
            Pattern pattern = Pattern.compile("^[0-9]{10}$", Pattern.MULTILINE);
            if (pattern.matcher(orgINN).matches()) {
                char[] chars = orgINN.toCharArray();
                int[] k = {2, 4, 10, 3, 5, 9, 4, 6, 8};

                int n = Integer.parseInt(Character.toString(chars[0])) * k[0];
                for (int i = 1; i < k.length; i++) {
                    n += Integer.parseInt(Character.toString(chars[i])) * k[i];
                }

                return (n % 11 == 10 ? 0 : n % 11) == Integer.parseInt(Character.toString(chars[chars.length - 1]));
            }
        }

        return false;
    }

    public static boolean isCorrectOGRN(String OGRN) {
        OGRN = getFormattedOGRN(OGRN);
        if (OGRN.length() == 13) {
            Pattern pattern = Pattern.compile("^[0-9]{13}$", Pattern.MULTILINE);
            if (pattern.matcher(OGRN).matches()) {
                long k1 = Long.parseLong(OGRN.substring(0, OGRN.length() - 1)) % 11;
                long k2 = Long.parseLong(OGRN.substring(OGRN.length() - 1));

                return (k1 > 9 ? 0 : k1) == k2;
            }
        }

        return false;
    }

    public static boolean isCorrectOGRNIP(String OGRNIP) {
        OGRNIP = getFormattedOGRNIP(OGRNIP);
        if (OGRNIP.length() == 15) {
            Pattern pattern = Pattern.compile("^[0-9]{15}$", Pattern.MULTILINE);
            if (pattern.matcher(OGRNIP).matches()) {
                long k1 = Long.parseLong(OGRNIP.substring(0, OGRNIP.length() - 1)) % 13;
                long k2 = Long.parseLong(OGRNIP.substring(OGRNIP.length() - 1));

                return (k1 > 9 ? k1 % 10 : k1) == k2;
            }
        }

        return false;
    }

    public static boolean isCorrectPhone(String phone) {
        return matches("^[0-9]{10}$", getFormattedPhone(phone));
    }

    public static boolean isCorrectIndex(String index) {
        return matches("^[0-9]{6}$", getFormattedIndex(index));
    }

    private static boolean isCorrectPersonName(String name) {
        return matches("^(?=[А-ЯЁ]{1})[ \\-А-ЯЁа-яё]{3,}$", name);
    }

    public static boolean isCorrectPersonLastName(String personLastName) {
        return isCorrectPersonName(getFormattedPersonLastName(personLastName));
    }

    public static boolean isCorrectPersonFirstName(String personFirstName) {
        return isCorrectPersonName(getFormattedPersonFirstName(personFirstName));
    }

    public static boolean isCorrectPersonMiddleName(String personMiddleName) {
        personMiddleName = getFormattedPersonMiddleName(personMiddleName);
        return personMiddleName.isBlank() || isCorrectPersonName(personMiddleName);
    }

    public static boolean isCorrectPersonINN(String personINN) {
        personINN = getFormattedPersonINN(personINN);
        if (personINN.length() == 12) {
            Pattern pattern = Pattern.compile("^[0-9]{12}$", Pattern.MULTILINE);
            if (pattern.matcher(personINN).matches()) {
                char[] chars = personINN.toCharArray();
                int[] k1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
                int[] k2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

                int checkNumber1 = Integer.parseInt(personINN.substring(10, 11));
                int checkNumber2 = Integer.parseInt(personINN.substring(11));

                int c0 = Integer.parseInt(Character.toString(chars[0]));
                int n1 = c0 * k1[0];
                int n2 = c0 * k2[0];
                for (int j = 1; j < k1.length; j++) {
                    n1 += Integer.parseInt(Character.toString(chars[j])) * k1[j];
                }
                for (int j = 1; j < k2.length; j++) {
                    n2 += Integer.parseInt(Character.toString(chars[j])) * k2[j];
                }

                return (n1 % 11 == 10 ? 0 : n1 % 11) == checkNumber1 && (n2 % 11 == 10 ? 0 : n2 % 11) == checkNumber2;
            }
        }

        return false;
    }

    public static boolean isCorrectPersonTitle(String personTitle) {
        return matches("^(?=[А-ЯЁ]{1})[ '\\-0-9A-Za-zА-ЯЁа-яё]{5,}$", getFormattedPersonTitle(personTitle));
    }

    public static boolean isCorrectSNILS(String SNILS) {
        SNILS = getFormattedSNILS(SNILS);
        if (SNILS.length() == 11) {
            Pattern pattern = Pattern.compile("^[0-9]{11}$", Pattern.MULTILINE);
            if (pattern.matcher(SNILS).matches()) {
                String number = SNILS.substring(0, 9);
                int checkNumber = Integer.parseInt(SNILS.substring(9));
                if (Integer.parseInt(number) > 1001998) {
                    char[] numbers = number.toCharArray();

                    int k = 0;
                    for (int i = 0; i < numbers.length; i++) {
                        k += Integer.parseInt(String.valueOf(numbers[i])) * (9 - i);
                    }

                    if (k > 101) {
                        k %= 101;
                    }

                    if (k < 100 && k == checkNumber) {
                        return true;
                    } else {
                        return (k == 100 || k == 101) && (0 == checkNumber);
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isCorrectBirthDate(String birthDate) {
        birthDate = getFormattedDate(birthDate);
        if (birthDate.length() == 10) {
            Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
            if (pattern.matcher(birthDate).matches()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate dateBirth = LocalDate.parse(birthDate, formatter);
                    int age = Period.between(dateBirth, LocalDate.now()).getYears();
                    return age >= 14 && age <= 90;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    public static boolean isCorrectEmailAddress(String emailAddress) {
        emailAddress = getFormattedEmailAddress(emailAddress);
        return emailAddress.length() <= 254
                && matches("^[\\-\\.0-9A-Za-z_]{2,}@[\\-\\.0-9A-Za-z]{2,}\\.[A-Za-z]{2,}$", emailAddress);
    }

    public static boolean isCorrectCitizenship(String citizenship) {
        return matches("^[A-Z]{3}$", getFormattedCitizenship(citizenship));
    }

    public static boolean isCorrectIssueDate(String issueDate, String birthDate) {
        birthDate = getFormattedDate(birthDate);
        issueDate = getFormattedDate(issueDate);
        if (isCorrectBirthDate(birthDate) && issueDate.length() == 10) {
            Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
            if (pattern.matcher(issueDate).matches()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate dateBirth = LocalDate.parse(birthDate, formatter);
                    LocalDate dateIssue = LocalDate.parse(issueDate, formatter);
                    LocalDate localDate = LocalDate.now();

                    if (dateIssue.isBefore(localDate) || dateIssue.isEqual(localDate)) {
                        LocalDate dateBirthPlus14 = dateBirth.plusYears(14);
                        LocalDate dateBirthPlus20 = dateBirth.plusYears(20);
                        LocalDate dateBirthPlus45 = dateBirth.plusYears(45);

                        int age = Period.between(dateBirth, localDate).getYears();
                        if (age >= 14 && age < 20) {
                            return (dateIssue.isAfter(dateBirthPlus14) && dateIssue.isBefore(dateBirthPlus20));
                        } else if (age >= 20 && age < 45) {
                            if (dateIssue.isAfter(dateBirthPlus20) && dateIssue.isBefore(dateBirthPlus45)) {
                                return true;
                            } else if (dateIssue.isAfter(dateBirthPlus14) && dateIssue.isBefore(dateBirthPlus20)) {
                                return localDate.isAfter(dateBirthPlus20)
                                        && (localDate.isBefore(dateBirthPlus20.plusDays(90)) || localDate.isEqual(dateBirthPlus20.plusDays(90)));
                            }
                        } else if (age >= 45) {
                            if (dateIssue.isAfter(dateBirthPlus45)) {
                                return true;
                            } else if (dateIssue.isAfter(dateBirthPlus20) && dateIssue.isBefore(dateBirthPlus45)) {
                                return localDate.isAfter(dateBirthPlus45)
                                        && (localDate.isBefore(dateBirthPlus45.plusDays(90)) || localDate.isEqual(dateBirthPlus45.plusDays(90)));
                            }
                        }
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    public static boolean isCorrectSeries(String series, TypeEnum typeEnum) {
        if (typeEnum == null) return false;
        return isCorrectSeries(series, typeEnum.getCode());
    }

    public static boolean isCorrectSeries(String series, String type) {
        series = getFormattedSeries(series);
        if ("RF_PASSPORT".equals(type)) {
            return matches("^[0-9]{4}$", series);
        } else return series.isBlank();
    }

    public static boolean isCorrectNumber(String number, TypeEnum typeEnum) {
        if (typeEnum == null) return false;
        return isCorrectNumber(number, typeEnum.getCode());
    }

    public static boolean isCorrectNumber(String number, String type) {
        number = getFormattedNumber(number);
        if ("RF_PASSPORT".equals(type)) {
            return matches("^[0-9]{6}$", number);
        } else return !number.isBlank();
    }

    public static boolean isCorrectIssueId(String issueId, TypeEnum typeEnum) {
        if (typeEnum == null) return false;
        return isCorrectIssueId(issueId, typeEnum.getCode());
    }

    public static boolean isCorrectIssueId(String issueId, String type) {
        issueId = getFormattedIssueId(issueId);
        if ("RF_PASSPORT".equals(type)) {
            return matches("^[0-9]{6}$", issueId);
        } else return !issueId.isBlank();
    }

    public static boolean isCorrectRequestId(String requestId) {
        return matches("^[0-9]{5,}$", getFormattedRequestId(requestId));
    }

    public static boolean isCorrectCurrentDirectory(String currentDirectory) {
        File pathname = new File(getFormattedCurrentDirectory(currentDirectory));
        return pathname.exists() && pathname.isDirectory();
    }

    public static boolean isCorrectPassword(@NotNull String password) {
        return password.length() >= 8 && matches("^(?=.*[0-9])(?=.*[A-Z])[A-Za-z0-9]{8,}$", password);
    }
}