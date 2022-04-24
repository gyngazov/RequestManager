package backend.window.main.form;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class Region {
    private static Region region;

    private final Map<Integer, String> map;

    private Region() {
        map = new HashMap<>();

        map.put(1, "Республика Адыгея");
        map.put(2, "Республика Башкортостан");
        map.put(3, "Республика Бурятия");
        map.put(4, "Республика Алтай");
        map.put(5, "Республика Дагестан");
        map.put(6, "Республика Ингушетия");
        map.put(7, "Кабардино-Балкарская Республика");
        map.put(8, "Республика Калмыкия");
        map.put(9, "Карачаево-Черкесская Республика");
        map.put(10, "Республика Карелия");
        map.put(11, "Республика Коми");
        map.put(12, "Республика Марий Эл");
        map.put(13, "Республика Мордовия");
        map.put(14, "Республика Саха (Якутия)");
        map.put(15, "Республика Северная Осетия - Алания");
        map.put(16, "Республика Татарстан"); //X
        map.put(17, "Республика Тыва");
        map.put(18, "Удмуртская Республика");
        map.put(19, "Республика Хакасия");
        map.put(20, "Чеченская Республика");
        map.put(21, "Чувашская Республика");
        map.put(22, "Алтайский край");
        map.put(23, "Краснодарский край");
        map.put(24, "Красноярский край");
        map.put(25, "Приморский край");
        map.put(26, "Ставропольский край");
        map.put(27, "Хабаровский край");
        map.put(28, "Амурская область");
        map.put(29, "Архангельская область");
        map.put(30, "Астраханская область");
        map.put(31, "Белгородская область");
        map.put(32, "Брянская область");
        map.put(33, "Владимирская область");
        map.put(34, "Волгоградская область");
        map.put(35, "Вологодская область");
        map.put(36, "Воронежская область");
        map.put(37, "Ивановская область");
        map.put(38, "Иркутская область");
        map.put(39, "Калининградская область");
        map.put(40, "Калужская область");
        map.put(41, "Камчатский край");
        map.put(42, "Кемеровская область");
        map.put(43, "Кировская область");
        map.put(44, "Костромская область");
        map.put(45, "Курганская область");
        map.put(46, "Курская область");
        map.put(47, "Ленинградская область");
        map.put(48, "Липецкая область");
        map.put(49, "Магаданская область");
        map.put(50, "Московская область");
        map.put(51, "Мурманская область");
        map.put(52, "Нижегородская область");
        map.put(53, "Новгородская область");
        map.put(54, "Новосибирская область");
        map.put(55, "Омская область");
        map.put(56, "Оренбургская область");
        map.put(57, "Орловская область");
        map.put(58, "Пензенская область");
        map.put(59, "Пермский край");
        map.put(60, "Псковская область");
        map.put(61, "Ростовская область");
        map.put(62, "Рязанская область");
        map.put(63, "Самарская область");
        map.put(64, "Саратовская область");
        map.put(65, "Сахалинская область");
        map.put(66, "Свердловская область");
        map.put(67, "Смоленская область");
        map.put(68, "Тамбовская область");
        map.put(69, "Тверская область");
        map.put(70, "Томская область");
        map.put(71, "Тульская область");
        map.put(72, "Тюменская область");
        map.put(73, "Ульяновская область");
        map.put(74, "Челябинская область");
        map.put(75, "Забайкальский край");
        map.put(76, "Ярославская область");
        map.put(77, "Москва");
        map.put(78, "Санкт-Петербург");
        map.put(79, "Еврейская автономная область");
        map.put(82, "Республика Крым");
        map.put(83, "Ненецкий автономный округ");
        map.put(86, "Ханты-Мансийский автономный округ - Югра");
        map.put(87, "Чукотский автономный округ");
        map.put(89, "Ямало-Ненецкий автономный округ");
        map.put(92, "Севастополь");
        map.put(99, "Байконур");
    }

    public String @NotNull [] getSortedListOfRegions() {
        String[] list = map.values().toArray(new String[0]);
        Arrays.sort(list);
        return list;
    }

    public String getRegionName(int code) {
        return map.get(code);
    }

    public int getRegionCode(@Nullable String regionName) {
        if (regionName != null && !regionName.isBlank()) {
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

            for (Map.Entry<Integer, String> entry : this.map.entrySet()) {
                if (entry.getValue().toUpperCase().matches(".*\\b" + list.get(0) + "\\b.*")) {
                    return entry.getKey();
                }
            }
        }

        return 0;
    }

    public static Region getInstance() {
        if (region == null) {
            return region = new Region();
        }
        return region;
    }
}
