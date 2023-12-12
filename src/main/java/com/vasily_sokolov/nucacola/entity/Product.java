package com.vasily_sokolov.nucacola.entity;

import com.vasily_sokolov.nucacola.enums.ProductCapacityType;
import com.vasily_sokolov.nucacola.enums.ProductCharacteristic;

public class Product {
    private int productId;
    private String productName;
    private ProductCharacteristic characteristic;
    private ProductCapacityType capacityType;
    private FinishedProductWarehouse warehouse;
    private double price;
}
