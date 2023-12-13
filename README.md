# Beverage Production Factory - Nuka-Cola

## Project Overview

This project embodies a beverage production factory specialized in crafting Nuka-Cola. The scheme involves a supplier 
delivering raw materials for subsequent processing and beverage production at the plant's raw material warehouse. 
The necessary quantity of raw material (a batch) is then moved to the production area. Following confirmation of the 
arrival of the required ingredients for production, the production cycle initiates. This cycle results in the 
manufacturing of the planned quantity of beverages through the pouring of the drink into corresponding containers. 
The resulting products are then transported to the warehouse for finished goods, from where they will be sold through 
the sales department.

This project is designed to streamline the production process, manage raw material procurement, and facilitate the sale
of the manufactured beverages.
## Database Structure

### Supplier

- Supplier ID (`supplier_id`): Unique identifier for the supplier (int)
- Supplier Name (`supplier_name`): Name of the supplier (varchar)
- Raw Material ID (`raw_material_id`): Identifier for the raw material (int)
- Quantity Supplied (`quantity_supplied`): Quantity supplied (int)
- Delivery Time (`delivery_time`): Time of delivery (varchar)

### RawMaterial

- Raw Material ID (`raw_material_id`): Identifier for the raw material (int)
- Raw Material Name (`raw_material_name`): Name of the raw material (varchar)

### RawMaterialWarehouse

- Warehouse ID (`warehouse_id`): Unique identifier for the warehouse (int)
- Warehouse Type (`warehouse_type`): Type of warehouse (varchar)
- Raw Material ID (`raw_material_id`): Identifier for the raw material (int)
- Quantity Raw Material (`quantity_raw_material`): Quantity of raw material (int)
- Supplier ID (`supplier_id`): Supplier identifier (int)

### Production

- Production ID (`production_id`): Identifier for the production (int)
- Raw Material Warehouse ID (`rawmaterial_warehouse_id`): Identifier for the raw material warehouse (int)
- Raw Material ID (`raw_material_id`): Identifier for the raw material (int)
- Product ID (`product_id`): Identifier for the product (int)
- Quantity Needed (`quantity_needed`): Required quantity (int)
- Finished Warehouse ID (`finished_warehouse_id`): Identifier for the finished product warehouse (int)
- Status (`status`): Production status (varchar)

### FinishedProductWarehouse

- Warehouse ID (`warehouse_id`): Unique identifier for the finished product warehouse (int)
- Warehouse Type (`warehouse_type`): Type of warehouse (varchar)
- Quantity Finished Product (`quantity_finished_product`): Quantity of finished product (int)

### Product

- Product ID (`product_id`): Unique identifier for the product (int)
- Product Name (`product_name`): Name of the product (varchar)
- Characteristics (`characteristics`): Product characteristics (varchar)
- Capacity Type (`capacity_type`): Type of capacity (varchar)
- Finished Warehouse ID (`finished_warehouse_id`): Identifier for the finished product warehouse (int)
- Price (`price`): Price of the product (decimal)

### Sale

- Sale ID (`sale_id`): Unique identifier for the sale (int)
- Product ID (`product_id`): Identifier for the product (int)
- Quantity Sold (`quantity_sold`): Quantity sold (int)
- Sale Date (`sale_date`): Date of sale (datetime)
- Customer Name (`customer_name`): Name of the customer (varchar)
- Finished Warehouse ID (`finished_warehouse_id`): Identifier for the finished product warehouse (int)

This table structure is designed to manage production, track warehouse status, and handle product sales.