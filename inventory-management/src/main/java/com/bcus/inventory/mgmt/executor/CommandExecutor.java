package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;

public interface CommandExecutor {

    void execute(String[] arguments, Inventory inventory);
}
