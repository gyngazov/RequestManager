package backend.window.main.filter.constant;

import backend.window.main.form.constant.Suppliable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum StatusEnum implements Suppliable<Integer> {
    @Expose(serialize = false, deserialize = false)
    WITHOUT_STATUS("Без статуса", null),
    @SerializedName(value = "1")
    SENDING_DOCUMENTS("Отправка документов", 1),
    @SerializedName(value = "21")
    MODERATION("На модерации", 21),
    @SerializedName(value = "2")
    GENERATING_REQUEST("Генерация запроса", 2),
    @SerializedName(value = "3")
    ISSUE("Выпуск", 3),
    @SerializedName(value = "4")
    READY("Готово", 4);

    private final String title;
    private final Integer code;

    StatusEnum(String title, Integer code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return title;
    }
}
