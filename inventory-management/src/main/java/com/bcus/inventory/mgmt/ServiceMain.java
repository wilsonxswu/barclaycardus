package com.bcus.inventory.mgmt;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ServiceMain {

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        System.out.println("Inventory Management Service is ready.");

        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (scanner.hasNext()) {
            inventoryManager.manage(scanner.nextLine().trim());
        }
    }
}
