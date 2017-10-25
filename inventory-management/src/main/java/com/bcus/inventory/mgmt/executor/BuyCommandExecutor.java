package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;

import java.math.BigDecimal;

public class BuyCommandExecutor extends AbstractCommandExecutor {

    public void execute(String[] arguments, Inventory inventory) {
        validateArgNumber(arguments.length, 3, "updateBuy [itemName] [quantity]");

        String itemName = arguments[1];
        if (inventory.existItem(itemName)) {
            BigDecimal buyQuantity = parseQuantityToBigDecimal(arguments[2]);
            Item item = inventory.getItem(itemName);
            item.setQuantity(item.getQuantity().add(buyQuantity));

            BigDecimal itemsValue = item.getCostPrice().multiply(buyQuantity);
            inventory.setTotalValue(inventory.getTotalValue().add(itemsValue));
        } else {
            throw new IllegalArgumentException("Invalid command : " + itemName + " does not exists in inventory.");
        }
    }
}
