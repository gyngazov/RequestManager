package backend.window.main.form;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Regions {
    private static final Map<Integer, String> LIST = new HashMap<>();

    static {
        LIST.put(1, "Республика Адыгея");
        LIST.put(2, "Республика Башкортостан");
        LIST.put(3, "Республика Бурятия");
        LIST.put(4, "Республика Алтай");
        LIST.put(5, "Республика Дагестан");
        LIST.put(6, "Республика Ингушетия");
        LIST.put(7, "Кабардино-Балкарская Республика");
        LIST.put(8, "Республика Калмыкия");
        LIST.put(9, "Карачаево-Черкесская Республика");
        LIST.put(10, "Республика Карелия");
        LIST.put(11, "Республика Коми");
        LIST.put(12, "Республика Марий Эл");
        LIST.put(13, "Республика Мордовия");
        LIST.put(14, "Республика Саха (Якутия)");
        LIST.put(15, "Республика Северная Осетия - Алания");
        LIST.put(16, "Республика Татарстан"); //X
        LIST.put(17, "Республика Тыва");
        LIST.put(18, "Удмуртская Республика");
        LIST.put(19, "Республика Хакасия");
        LIST.put(20, "Чеченская Республика");
        LIST.put(21, "Чувашская Республика");
        LIST.put(22, "Алтайский край");
        LIST.put(23, "Краснодарский край");
        LIST.put(24, "Красноярский край");
        LIST.put(25, "Приморский край");
        LIST.put(26, "Ставропольский край");
        LIST.put(27, "Хабаровский край");
        LIST.put(28, "Амурская область");
        LIST.put(29, "Архангельская область");
        LIST.put(30, "Астраханская область");
        LIST.put(31, "Белгородская область");
        LIST.put(32, "Брянская область");
        LIST.put(33, "Владимирская область");
        LIST.put(34, "Волгоградская область");
        LIST.put(35, "Вологодская область");
        LIST.put(36, "Воронежская область");
        LIST.put(37, "Ивановская область");
        LIST.put(38, "Иркутская область");
        LIST.put(39, "Калининградская область");
        LIST.put(40, "Калужская область");
        LIST.put(41, "Камчатский край");
        LIST.put(42, "Кемеровская область");
        LIST.put(43, "Кировская область");
        LIST.put(44, "Костромская область");
        LIST.put(45, "Курганская область");
        LIST.put(46, "Курская область");
        LIST.put(47, "Ленинградская область");
        LIST.put(48, "Липецкая область");
        LIST.put(49, "Магаданская область");
        LIST.put(50, "Московская область");
        LIST.put(51, "Мурманская область");
        LIST.put(52, "Нижегородская область");
        LIST.put(53, "Новгородская область");
        LIST.put(54, "Новосибирская область");
        LIST.put(55, "Омская область");
        LIST.put(56, "Оренбургская область");
        LIST.put(57, "Орловская область");
        LIST.put(58, "Пензенская область");
        LIST.put(59, "Пермский край");
        LIST.put(60, "Псковская область");
        LIST.put(61, "Ростовская область");
        LIST.put(62, "Рязанская область");
        LIST.put(63, "Самарская область");
        LIST.put(64, "Саратовская область");
        LIST.put(65, "Сахалинская область");
        LIST.put(66, "Свердловская область");
        LIST.put(67, "Смоленская область");
        LIST.put(68, "Тамбовская область");
        LIST.put(69, "Тверская область");
        LIST.put(70, "Томская область");
        LIST.put(71, "Тульская область");
        LIST.put(72, "Тюменская область");
        LIST.put(73, "Ульяновская область");
        LIST.put(74, "Челябинская область");
        LIST.put(75, "Забайкальский край");
        LIST.put(76, "Ярославская область");
        LIST.put(77, "Москва");
        LIST.put(78, "Санкт-Петербург");
        LIST.put(79, "Еврейская автономная область");
        LIST.put(82, "Республика Крым");
        LIST.put(83, "Ненецкий автономный округ");
        LIST.put(86, "Ханты-Мансийский автономный округ - Югра");
        LIST.put(87, "Чукотский автономный округ");
        LIST.put(89, "Ямало-Ненецкий автономный округ");
        LIST.put(92, "Севастополь");
        LIST.put(99, "Байконур");
    }

    private Regions() {
    }

    public static String @NotNull [] getSortedListOfRegions() {
        String[] list = LIST.values().toArray(new String[0]);
        Arrays.sort(list);
        return list;
    }

    public static String getRegionName(int code) {
        return LIST.get(code);
    }

    public static int getRegionCode(@Nullable String regionName) {
        if (regionName == null) return 0;

        List<String> list = new ArrayList<>(Arrays.asList(regionName
                .strip()
                .toUpperCase()
                .replaceAll("[^А-ЯЁ-]+", " ")
                .split(" ")));
        List<String> wordFilter = Arrays.asList(
                "Г", "ГОР", "ГОРОД",
                "ОБЛ", "ОБЛАСТЬ",
                "РЕСП", "РЕСПУБЛИКА",
                "КРАЙ",
                "АО", "А", "ОКР", "АВТОНОМНЫЙ", "ОКРУГ", "АВТОНОМНАЯ",
                "-"
        );
        list.removeAll(wordFilter);

        for (Map.Entry<Integer, String> entry : LIST.entrySet()) {
            if (entry.getValue().toUpperCase().matches(".*\\b" + list.get(0) + "\\b.*")) {
                return entry.getKey();
            }
        }

        return 0;
    }
}
