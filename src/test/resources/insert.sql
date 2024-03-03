-- Вставка тестовых данных в таблицу product
INSERT INTO `product` (`product_id`,
                       `product_name`,
                       `product_characteristic`,
                       `product_capacity_type`,
                       `warehouse_id`,
                       `production_id`,
                       `product_price`)
VALUES ('10d83df1-7247-4a7e-af09-96d418317ec2', 'Nuca-Cola', 'SUGARY', 'BIG', 2, 1, 1.99),
       ('2eca2948-7eb2-407e-8666-a953a0b2ccd8', 'Nuca-Cola', 'SUGARY', 'MEDIUM', 2, 1, 1.49),
       ('78ef236c-4da2-4c03-8e81-70fe0823485e', 'Nuca-Cola', 'SUGARY', 'SMALL', 2, 1, 0.99),
       ('1c27bc9d-c00b-46c3-bf1c-393a39160168', 'Nuca-Cola zero', 'NOT_SUGARY', 'MEDIUM', 2, 1, 1.49),
       ('8bc6ca0f-f8b8-4714-85b4-8b05e6cc4680', 'Nuca-Cola zero', 'NOT_SUGARY', 'BIG', 2, 1, 1.99),
       ('96009103-dcfa-4c0f-977d-528863c5c44c', 'Nuca-Cola zero', 'NOT_SUGARY', 'SMALL', 2, 1, 0.99);

-- Вставка тестовых данных в таблицу production
INSERT INTO `production` (`production_id`, `warehouse_id_raw_material`, `warehouse_id_product`, `status_production`)
VALUES (1, 1, 2, 'PROCESS_STOP');

-- Вставка тестовых данных в таблицу raw_material
INSERT INTO `raw_material` (`raw_material_id`,
                            `raw_material_name`,
                            `raw_material_warehouse_id`,
                            `supplier_id`, `production_id`)
VALUES ('7d2000c1-8111-420c-a42a-e4eca5b50090', 'secret ingredient for  sugar drink', 1, 1, 1),
       ('c8d3fa37-02c8-4455-ae02-21835c436275', 'secret ingredient for not sugar drink', 1, 2, 1);

-- Вставка тестовых данных в таблицу sale
INSERT INTO `sale` (`sale_id`, `product_id`, `sale_date`, `customer_name`)
VALUES ('d092f5f9-c2b1-4fc3-bb8d-502ccd215c9f', '78ef236c-4da2-4c03-8e81-70fe0823485e', '2024-05-09', 'Supermarket 1'),
       ('b5310470-4943-4718-8899-2329a4dec393', '96009103-dcfa-4c0f-977d-528863c5c44c', '2024-05-09', 'Supermarket 1');

-- Вставка тестовых данных в таблицу supplier
INSERT INTO `supplier` (`supplier_id`, `supplier_name`)
VALUES (1, 'Mars'),
       (2, 'Jupiter');

-- Вставка тестовых данных в таблицу warehouse
INSERT INTO `warehouse` (`warehouse_id`, `warehouse_type`)
VALUES (1, 'RAW_MATERIAL_WAREHOUSE'),
       (2, 'FINISHED_PRODUCT_WAREHOUSE');
