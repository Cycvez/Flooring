# Flooring
The program is able to read and write Orders for a flooring Company

All orders are stored within an Orders folder. A new order file is created for each sales day. The file name is guaranteed to be unique for that day because the date is part of the file name. When creating new order files the file name should be in this format: Orders_MMDDYYYY.txt.

With this format, order file for Augusst 21st, 2017 would be named Orders_08212017.txt.

The order file should contain a header row. Data rows will contain the following fields and should be in this order:

OrderNumber – Integer
CustomerName – String
State – String
TaxRate – BigDecimal
ProductType – String
Area – BigDecimal
CostPerSquareFoot – BigDecimal
LaborCostPerSquareFoot – BigDecimal
MaterialCost – BigDecimal
LaborCost – BigDecimal
Tax – BigDecimal
Total – BigDecimal
Here is a sample line from an orders file:
