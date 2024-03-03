-- Создание таблицы product
CREATE TABLE IF NOT EXISTS `product`
(
    `product_id`             VARCHAR(36)   NOT NULL,
    `product_name`           VARCHAR(45)   NOT NULL,
    `product_characteristic` VARCHAR(45)   NOT NULL,
    `product_capacity_type`  VARCHAR(45)   NOT NULL,
    `warehouse_id`           INT,
    `production_id`          INT,
    `product_price`          DECIMAL(8, 2) NOT NULL
);

-- Создание таблицы production
CREATE TABLE IF NOT EXISTS `production`
(
    `production_id`             INT NOT NULL,
    `warehouse_id_raw_material` INT NOT NULL,
    `warehouse_id_product`      INT NOT NULL,
    `status_production`         VARCHAR(45)
);

-- Создание таблицы raw_material
CREATE TABLE IF NOT EXISTS `raw_material`
(
    `raw_material_id`           VARCHAR(36) NOT NULL,
    `raw_material_name`         VARCHAR(45) NOT NULL,
    `raw_material_warehouse_id` INT,
    `supplier_id`               INT         NOT NULL,
    `production_id`             INT
);

-- Создание таблицы sale
CREATE TABLE IF NOT EXISTS `sale`
(
    `sale_id`       VARCHAR(36) NOT NULL,
    `product_id`    VARCHAR(36) NOT NULL,
    `sale_date`     DATE        NOT NULL,
    `customer_name` VARCHAR(45) NOT NULL
);

-- Создание таблицы supplier
CREATE TABLE IF NOT EXISTS `supplier`
(
    `supplier_id`   INT NOT NULL,
    `supplier_name` VARCHAR(45)
);

-- Создание таблицы `warehouse`
CREATE TABLE IF NOT EXISTS `warehouse`
(
    `warehouse_id`   INT         NOT NULL,
    `warehouse_type` VARCHAR(45) NOT NULL
);
