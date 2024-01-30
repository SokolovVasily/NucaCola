package com.vasily_sokolov.nucacola.entity.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProductCapacityType {
    SMALL,
    MEDIUM,
    BIG;
    public static List<String> getProductCapacityTypeList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
