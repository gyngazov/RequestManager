package backend.window.main.form.constant;

public enum GenderEnum implements Suppliable<String> {
    M("Мужской", "M"),
    F("Женский", "F");

    private final String title;
    private final String code;

    GenderEnum(String title, String code) {
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