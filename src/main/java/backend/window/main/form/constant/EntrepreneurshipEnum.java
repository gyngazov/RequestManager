package backend.window.main.form.constant;

import com.google.gson.annotations.SerializedName;

public enum EntrepreneurshipEnum implements Suppliable<Integer> {
    @SerializedName(value = "3")
    JURIDICAL_PERSON("Юридическое лицо", 3),
    @SerializedName(value = "2")
    SOLE_PROPRIETOR("Индивидуальный пред.", 2),
    @SerializedName(value = "1")
    NATURAL_PERSON("Физическое лицо", 1);

    private final String title;
    private final Integer code;

    EntrepreneurshipEnum(String title, Integer code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return title;
    }
}
