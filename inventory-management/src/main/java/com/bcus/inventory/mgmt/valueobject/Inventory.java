package com.bcus.inventory.mgmt.valueobject;

import java.math.BigDecimal;
import java.util.*;

public class Inventory {

    private Map<String, Item> itemMap;
    private BigDecimal totalValue;
    private BigDecimal profit;

    public Inventory() {
        this.itemMap = new TreeMap<>();
        this.totalValue = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
    }

    public Item getItem(String name) {
        return itemMap.get(name);
    }

    public Collection<Item> getAllItems() {
        return itemMap.values();
    }

    public void addItem(Item item) {
        itemMap.put(item.getName(), item);
    }

    public void deleteItem(String name) {
        itemMap.remove(name);
    }

    public boolean existItem(String name) {
        return itemMap.containsKey(name);
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

}
