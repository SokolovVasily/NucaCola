package com.vasily_sokolov.nucacola.entity.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProductCharacteristic {
    SUGARY,
    NOT_SUGARY,
    SUGARY_EXTRA,
    NOT_SUGARY_EXTRA;

    public static List<String> getProductCharacteristicList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
