package backend.window.main.form.constant;

public enum TypeEnum implements Suppliable<String> {
    RF_PASSPORT("Паспорт гражданина РФ", "RF_PASSPORT"),
    FID_DOC("Иностранный паспорт", "FID_DOC");

    public static final String FID_DOC_SERIES = "0000";
    public static final String FID_DOC_NUMBER = "000000";
    public static final String FID_DOC_ISSUE_ID = "000000";
    public static final String CITIZENSHIP_RF = "RUS";

    private final String title;
    private final String code;

    TypeEnum(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return title;
    }
}