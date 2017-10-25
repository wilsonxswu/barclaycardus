package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;

public class DeleteCommandExecutor extends AbstractCommandExecutor {

    public void execute(String[] arguments, Inventory inventory) {
        validateArgNumber(arguments.length, 2, "delete [itemName]");

        String itemName = arguments[1];
        if (inventory.existItem(itemName)) {
            Item item = inventory.getItem(itemName);
            inventory.setTotalValue(inventory.getTotalValue().subtract(item.getValue()));
            inventory.setProfit(inventory.getProfit().subtract(item.getValue()));
            inventory.deleteItem(itemName);
        } else {
            System.out.println(itemName + " does not exist in inventory, ignore this delete command.");
        }
    }
}
