package com.bcus.inventory.mgmt;

import com.bcus.inventory.mgmt.executor.*;
import com.bcus.inventory.mgmt.executor.command.Command;
import com.bcus.inventory.mgmt.valueobject.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private Inventory inventory;
    private Map<String, CommandExecutor> commandExecutorMap;

    public InventoryManager() {
        inventory = new Inventory();
        commandExecutorMap = new HashMap<>();
        commandExecutorMap.put(Command.create.name(), new CreateCommandExecutor());
        commandExecutorMap.put(Command.delete.name(), new DeleteCommandExecutor());
        commandExecutorMap.put(Command.updateBuy.name(), new BuyCommandExecutor());
        commandExecutorMap.put(Command.updateSell.name(), new SellCommandExecutor());
        commandExecutorMap.put(Command.updateSellPrice.name(), new UpdateSellPriceCommandExecutor());
        commandExecutorMap.put(Command.report.name(), new ReportCommandExecutor());
    }

    public void manage(String commandStr) {
        try {
            if (commandStr.length() > 0) {
                String[] arguments = commandStr.split(" ");
                String command = arguments[0];
                if (commandExecutorMap.containsKey(command)) {
                    commandExecutorMap.get(command).execute(arguments, inventory);
                } else {
                    System.err.println("Unsupported command : " + command);
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred when executing command : [" + commandStr + "]. Reason : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
