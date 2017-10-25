package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;

import java.math.BigDecimal;

public class ReportCommandExecutor implements CommandExecutor {

    private static final String TITLE_FORMAT = "%-15s%s";
    private static final String ITEM_FORMAT = "%-15s%-15s%-15s%-15s%s";
    private static final String TOTAL_FORMAT = "%-60s%s";

    public void execute(String[] arguments, Inventory inventory) {
        System.out.format(TITLE_FORMAT, "", "INVENTORY REPORT").println();
        System.out.format(ITEM_FORMAT, "Item Name", "Bought At", "Sold At", "AvailableQty", "Value").println();
        System.out.format(ITEM_FORMAT, "---------", "---------", "-------", "------------", "-------").println();

        inventory.getAllItems()
                .forEach(item -> System.out.format(ITEM_FORMAT, item.getName(), item.getCostPrice(), item.getSellingPrice(), item.getQuantity(), item.getValue()).println());

        System.out.println("--------------------------------------------------------------------");
        System.out.format(TOTAL_FORMAT, "Total value", inventory.getTotalValue()).println();
        System.out.format(TOTAL_FORMAT, "Profit since previous report", inventory.getProfit()).println();
        System.out.println();

        inventory.setProfit(BigDecimal.ZERO);
    }

}
