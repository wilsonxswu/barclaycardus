package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;

import java.math.BigDecimal;

public class UpdateSellPriceCommandExecutor extends AbstractCommandExecutor {

    public void execute(String[] arguments, Inventory inventory) {
        validateArgNumber(arguments.length, 3, "updateSellPrice [itemName] [newSellPrice]");

        String itemName = arguments[1];
        if (inventory.existItem(itemName)) {
            Item originalItem = inventory.getItem(itemName);
            BigDecimal sellingPrice = parsePriceToBigDecimal(arguments[2]);
            BigDecimal costPrice = originalItem.getCostPrice();

            Item updateItem = new Item(itemName, costPrice, sellingPrice);
            updateItem.setQuantity(originalItem.getQuantity());
            inventory.addItem(updateItem);
        } else {
            throw new IllegalArgumentException("Invalid command : " + itemName + " does not exists in inventory.");
        }
    }
}
