package com.vasily_sokolov.nucacola.entity;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Map;

public class Production {
    private RawMaterialWarehouse rawMaterialWarehouse;
    private Product product;
    private Map<Integer, Integer> quantityNeeded;
    private FinishedProductWarehouse finishedProductWarehouse;
}

