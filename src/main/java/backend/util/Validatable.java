package backend.util;

import backend.window.main.form.constant.TypeEnum;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public interface Validatable {

    private static boolean matches(@NotNull String regex, @NotNull String text) {
        return Pattern.compile(regex).matcher(text).matches();
    }

    static boolean isNonBlank(String value) {
        return value != null && !value.isBlank();
    }

    static @NotNull String replaceSpaces(@NotNull String text) {
        return text.replaceAll("\\s+", " ").strip();
    }

    static @NotNull String replaceQuotes(@NotNull String text) {
        return text.replaceAll("[\"'\\\\«»‘’‚‛“”„‟‹›]+", "'");
    }

    static @NotNull String getFormattedOrganizationName(@NotNull String organizationName) {
        return replaceQuotes(replaceSpaces(organizationName.toUpperCase()));
    }

    static @NotNull String getFormattedKPP(@NotNull String KPP) {
        return replaceSpaces(KPP);
    }

    static @NotNull String getFormattedOrgINN(@NotNull String orgINN) {
        return replaceSpaces(orgINN);
    }

    static @NotNull String getFormattedOGRN(@NotNull String OGRN) {
        return replaceSpaces(OGRN);
    }

    static @NotNull String getFormattedOGRNIP(@NotNull String OGRNIP) {
        return replaceSpaces(OGRNIP);
    }

    static @NotNull String getFormattedPhone(@NotNull String phone) {
        return replaceSpaces(phone);
    }

    static @NotNull String getFormattedIndex(@NotNull String index) {
        return replaceSpaces(index);
    }

    static @NotNull String getFormattedCountryName(@NotNull String countryName) {
        return replaceSpaces(countryName);
    }

    static @NotNull String getFormattedLocalityName(@NotNull String localityName) {
        return replaceQuotes(replaceSpaces(localityName));
    }

    static @NotNull String getFormattedStreetAddress(@NotNull String streetAddress) {
        return replaceQuotes(replaceSpaces(streetAddress));
    }

    static @NotNull String getFormattedPersonLastName(@NotNull String personLastName) {
        return replaceSpaces(personLastName);
    }

    static @NotNull String getFormattedPersonFirstName(@NotNull String personFirstName) {
        return replaceSpaces(personFirstName);
    }

    static @NotNull String getFormattedPersonMiddleName(@NotNull String personMiddleName) {
        return replaceSpaces(personMiddleName);
    }

    static @NotNull String getFormattedPersonINN(@NotNull String personINN) {
        return replaceSpaces(personINN);
    }

    static @NotNull String getFormattedPersonTitle(@NotNull String personTitle) {
        return replaceQuotes(replaceSpaces(personTitle));
    }

    static @NotNull String getFormattedSNILS(@NotNull String SNILS) {
        return replaceSpaces(SNILS).replaceAll("[ \\-]+", "");
    }

    static @NotNull String getFormattedDate(@NotNull String date) {
        return replaceSpaces(date).replaceAll("[\\-/]+", ".");
    }

    static @NotNull String getFormattedEmailAddress(@NotNull String emailAddress) {
        return replaceSpaces(emailAddress);
    }

    static @NotNull String getFormattedCitizenship(@NotNull String citizenship) {
        return replaceSpaces(citizenship.toUpperCase());
    }

    static @NotNull String getFormattedDivision(@NotNull String division) {
        return replaceQuotes(replaceSpaces(division));
    }

    static @NotNull String getFormattedSeries(@NotNull String series) {
        return replaceSpaces(series);
    }

    static @NotNull String getFormattedNumber(@NotNull String number) {
        return replaceSpaces(number);
    }

    static @NotNull String getFormattedIssueId(@NotNull String issueId) {
        return replaceSpaces(issueId).replaceAll("[ \\-]+", "");
    }

    static @NotNull String getFormattedCurrentDirectory(@NotNull String currentDirectory) {
        return replaceSpaces(currentDirectory);
    }

    static boolean isCorrectOrganizationName(String organizationName) {
        return isNonBlank(organizationName)
                && matches("(?=[А-ЯЁ ]{3,})[ '()+,\\-.0-9А-ЯЁ]{5,}", getFormattedOrganizationName(organizationName));
    }

    static boolean isCorrectKPP(String KPP) {
        return isNonBlank(KPP)
                && matches("[0-9]{4}[0-9A-Z]{2}[0-9]{3}", getFormattedKPP(KPP));
    }

    static boolean isCorrectOrgINN(String orgINN) {
        if (isNonBlank(orgINN)) {
            orgINN = getFormattedOrgINN(orgINN);
            if (Pattern.compile("[0-9]{10}").matcher(orgINN).matches()) {
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

    static boolean isCorrectOGRN(String OGRN) {
        if (isNonBlank(OGRN)) {
            OGRN = getFormattedOGRN(OGRN);
            if (Pattern.compile("[0-9]{13}").matcher(OGRN).matches()) {
                long k1 = Long.parseLong(OGRN.substring(0, OGRN.length() - 1)) % 11;
                long k2 = Long.parseLong(OGRN.substring(OGRN.length() - 1));

                return (k1 > 9 ? 0 : k1) == k2;
            }
        }

        return false;
    }

    static boolean isCorrectOGRNIP(String OGRNIP) {
        if (isNonBlank(OGRNIP)) {
            OGRNIP = getFormattedOGRNIP(OGRNIP);
            if (Pattern.compile("[0-9]{15}").matcher(OGRNIP).matches()) {
                long k1 = Long.parseLong(OGRNIP.substring(0, OGRNIP.length() - 1)) % 13;
                long k2 = Long.parseLong(OGRNIP.substring(OGRNIP.length() - 1));

                return (k1 > 9 ? k1 % 10 : k1) == k2;
            }
        }

        return false;
    }

    static boolean isCorrectPhone(String phone) {
        return isNonBlank(phone)
                && matches("[0-9]{10}", getFormattedPhone(phone));
    }

    static boolean isCorrectIndex(String index) {
        return isNonBlank(index)
                && matches("[0-9]{6}", getFormattedIndex(index));
    }

    private static boolean isCorrectPersonName(String name) {
        return isNonBlank(name)
                && matches("(?=[А-ЯЁ]{1})[ \\-А-ЯЁа-яё]{3,}", name);
    }

    static boolean isCorrectPersonLastName(String personLastName) {
        return isNonBlank(personLastName)
                && isCorrectPersonName(getFormattedPersonLastName(personLastName));
    }

    static boolean isCorrectPersonFirstName(String personFirstName) {
        return isNonBlank(personFirstName)
                && isCorrectPersonName(getFormattedPersonFirstName(personFirstName));
    }

    static boolean isCorrectPersonMiddleName(String personMiddleName) {
        return personMiddleName == null
                || personMiddleName.isBlank()
                || isCorrectPersonName(getFormattedPersonMiddleName(personMiddleName));
    }

    static boolean isCorrectPersonINN(String personINN) {
        if (isNonBlank(personINN)) {
            personINN = getFormattedPersonINN(personINN);
            if (Pattern.compile("[0-9]{12}").matcher(personINN).matches()) {
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

    static boolean isCorrectPersonTitle(String personTitle) {
        return isNonBlank(personTitle)
                && matches("(?=[А-ЯЁ]{1})[ '\\-0-9A-Za-zА-ЯЁа-яё]{5,}", getFormattedPersonTitle(personTitle));
    }

    static boolean isCorrectSNILS(String SNILS) {
        if (isNonBlank(SNILS)) {
            SNILS = getFormattedSNILS(SNILS);
            if (Pattern.compile("[0-9]{11}").matcher(SNILS).matches()) {
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

    static boolean isCorrectBirthDate(String birthDate) {
        if (isNonBlank(birthDate)) {
            birthDate = getFormattedDate(birthDate);
            if (Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}").matcher(birthDate).matches()) {
                try {
                    LocalDate dateBirth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    int age = Period.between(dateBirth, LocalDate.now()).getYears();
                    return age >= 14 && age <= 90;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    static boolean isCorrectEmailAddress(String emailAddress) {
        return isNonBlank(emailAddress)
                && emailAddress.length() < 255
                && matches("[\\-\\.0-9A-Za-z_]{2,}@[\\-\\.0-9A-Za-z]{2,}\\.[A-Za-z]{2,}", getFormattedEmailAddress(emailAddress));
    }

    static boolean isCorrectCitizenship(String citizenship) {
        return isNonBlank(citizenship)
                && matches("[A-Z]{3}", getFormattedCitizenship(citizenship));
    }

    static boolean isCorrectIssueDate(String issueDate, String birthDate) {
        if (isCorrectBirthDate(birthDate) && isNonBlank(issueDate)) {
            issueDate = getFormattedDate(issueDate);
            if (Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}").matcher(issueDate).matches()) {
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

    static boolean isCorrectSeries(String series, TypeEnum typeEnum) {
        if (typeEnum != null && isNonBlank(series)) {
            series = getFormattedSeries(series);
            if ("RF_PASSPORT".equals(typeEnum.getCode())) {
                return matches("[0-9]{4}", series);
            } else {
                return !series.isBlank();
            }
        }

        return false;
    }

    static boolean isCorrectNumber(String number, TypeEnum typeEnum) {
        if (typeEnum != null && isNonBlank(number)) {
            number = getFormattedNumber(number);
            if ("RF_PASSPORT".equals(typeEnum.getCode())) {
                return matches("[0-9]{6}", number);
            } else {
                return !number.isBlank();
            }
        }

        return false;
    }

    static boolean isCorrectIssueId(String issueId, TypeEnum typeEnum) {
        if (typeEnum != null && isNonBlank(issueId)) {
            issueId = getFormattedIssueId(issueId);
            if ("RF_PASSPORT".equals(typeEnum.getCode())) {
                return matches("[0-9]{6}", issueId);
            } else {
                return !issueId.isBlank();
            }
        }

        return false;
    }

    static boolean isCorrectCurrentDirectory(File currentDirectory) {
        return currentDirectory != null && currentDirectory.exists() && currentDirectory.isDirectory();
    }

    static boolean isCorrectCurrentDirectory(String currentDirectory) {
        return isCorrectCurrentDirectory(new File(getFormattedCurrentDirectory(currentDirectory)));
    }

    static boolean isCorrectPassword(String password) {
        return isNonBlank(password)
                && password.length() > 7
                && matches("(?=.*[0-9])(?=.*[A-Z])[A-Za-z0-9]{8,}", password);
    }
}
