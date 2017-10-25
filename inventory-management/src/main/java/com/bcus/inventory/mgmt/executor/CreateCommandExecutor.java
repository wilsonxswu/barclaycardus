package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;

import java.math.BigDecimal;

public class CreateCommandExecutor extends AbstractCommandExecutor {

    public void execute(String[] arguments, Inventory inventory) {
        validateArgNumber(arguments.length, 4, "create [itemName] [costPrice] [sellingPrice]");

        String itemName = arguments[1];
        if (!inventory.existItem(itemName)) {
            BigDecimal costPrice = parsePriceToBigDecimal(arguments[2]);
            BigDecimal sellingPrice = parsePriceToBigDecimal(arguments[3]);
            inventory.addItem(new Item(itemName, costPrice, sellingPrice));
        } else {
            System.out.println(itemName + " already exists in inventory, ignore this create command.");
        }
    }
}
