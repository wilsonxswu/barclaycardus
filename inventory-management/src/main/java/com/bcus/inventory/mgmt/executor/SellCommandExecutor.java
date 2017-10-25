package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;

import java.math.BigDecimal;

public class SellCommandExecutor extends AbstractCommandExecutor {

    public void execute(String[] arguments, Inventory inventory) {
        validateArgNumber(arguments.length, 3, "updateSell [itemName] [quantity]");

        String itemName = arguments[1];
        if (inventory.existItem(itemName)) {
            BigDecimal sellQuantity = parseQuantityToBigDecimal(arguments[2]);
            Item item = inventory.getItem(itemName);

            if (sellQuantity.compareTo(item.getQuantity()) > 0) {
                System.out.println("Quantity of " + itemName + " in inventory is " + item.getQuantity() + ". All will be sold out now.");
                sellQuantity = item.getQuantity();
                item.setQuantity(BigDecimal.ZERO);
            } else {
                item.setQuantity(item.getQuantity().subtract(sellQuantity));
            }

            BigDecimal itemsValue = item.getCostPrice().multiply(sellQuantity);
            inventory.setTotalValue(inventory.getTotalValue().subtract(itemsValue));

            BigDecimal itemsProfit = item.getProfit().multiply(sellQuantity);
            inventory.setProfit(inventory.getProfit().add(itemsProfit));
        } else {
            throw new IllegalArgumentException("Invalid command : " + itemName + " does not exists in inventory.");
        }
    }
}
