package backend.window.main.form.constant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum GenderEnum implements Suppliable<String> {
    @Expose()
    @SerializedName(value = "M")
    M("Мужской", "M"),
    @Expose()
    @SerializedName(value = "F")
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
