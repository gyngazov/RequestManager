package backend.window.main.form.constant;

public enum ProductEnum implements Suppliable<Integer> {
    P3512(3512),
    P7904(7904),
    P3514(3514),
    P3513(3513);

    private final Integer code;

    ProductEnum(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
