package backend.window.main.form.constant;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EnumProvider<T, E extends Suppliable<T>> {
    private final Map<T, E> map = new HashMap<>();
    private final E[] enumeration;

    public EnumProvider(E[] enumeration) {
        this.enumeration = enumeration;
    }

    @Nullable
    public E get(T code) {
        for (E i : enumeration)
            map.put(i.getCode(), i);
        return map.get(code);
    }
}
