package backend.reader.extract;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.*;

import backend.reader.Readable;
import backend.window.main.form.constant.EntrepreneurshipEnum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExtractFTS {
    private final String formattedText;

    protected String date;
    protected EntrepreneurshipEnum entrepreneurshipEnum;

    public ExtractFTS(String formattedText) {
        this.formattedText = formattedText;
    }

    public static @NotNull String formatText(@NotNull String text) {
        return text.replaceAll("\\s+", " ");
    }

    public static @Nullable String getValue(String text, String regex) {
        Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(text);
        if (m.find()) {
            return m.group().strip().toUpperCase();
        }

        return null;
    }

    public static @NotNull ExtractFTS of(@NotNull Readable readable) throws IOException {
        String formattedText = formatText(readable.read());

        String entrepreneurship = getValue(formattedText,
                "из Единого государственного реестра юридических лиц");
        if (entrepreneurship != null && entrepreneurship.contains("ЮРИДИЧЕСКИХ")) {
            return new USRLE(formattedText);
        } else {
            entrepreneurship = getValue(formattedText,
                    "из Единого государственного реестра индивидуальных предпринимателей");
            if (entrepreneurship != null && entrepreneurship.contains("ИНДИВИДУАЛЬНЫХ")) {
                return new URIE(formattedText);
            } else {
                throw new IllegalArgumentException("Не удается получить сведения из ЕГРЮЛ/ЕГРИП.");
            }
        }
    }

    public String getFormattedText() {
        return formattedText;
    }

    public LocalDate getDate() {
        return LocalDate.parse(Objects.requireNonNullElse(date, "01.01.1970"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public EntrepreneurshipEnum getEntrepreneurshipEnum() {
        return entrepreneurshipEnum;
    }

    public boolean isDateToday() {
        return LocalDate.now().isEqual(getDate());
    }

    public @NotNull String capitalize(final @NotNull String line) {
        String subLine = line.substring(1);
        return line.replace(subLine, subLine.toLowerCase());
    }

    public void print() {
        System.out.println("Дата выписки: " + getDate());
        System.out.print(System.lineSeparator());

        System.out.println("Форма организации: " + entrepreneurshipEnum.getTitle());
        System.out.print(System.lineSeparator());
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Укажите путь к сведениям из ЕГРЮЛ/ЕГРИП, включая диск: ");
            File file = new File(scanner.nextLine());
            try {
                ExtractFTS extractFTS = ExtractFTS.of(Readable.of(file));

                System.out.print(System.lineSeparator());
                System.out.println(extractFTS.formattedText);

                System.out.print(System.lineSeparator());
                extractFTS.print();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
