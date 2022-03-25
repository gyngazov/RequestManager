package backend.window.main.form.constant;

import com.google.gson.annotations.SerializedName;

public enum IdentificationKindEnum implements Suppliable<Integer> {
    @SerializedName(value = "0")
    PERSONALLY("Лично", 0),
    @SerializedName(value = "1")
    CURRENT_CEP("По действующей КЭП", 1),
    @SerializedName(value = "2")
    INTERNATIONAL_PASSPORT("Загранпаспорт с электронным носителем", 2),
    @SerializedName(value = "3")
    ESIA_EBS("ЕСИА/ЕБС", 3);

    private final String title;
    private final Integer code;

    IdentificationKindEnum(String title, Integer code) {
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
