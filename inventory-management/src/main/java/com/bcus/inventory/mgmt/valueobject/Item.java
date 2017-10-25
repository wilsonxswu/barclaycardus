package com.bcus.inventory.mgmt.valueobject;

import java.math.BigDecimal;

public class Item {
    private String name;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal quantity;

    public Item(String name, BigDecimal costPrice, BigDecimal sellingPrice) {
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return costPrice.multiply(quantity);
    }

    public BigDecimal getProfit() {
        return sellingPrice.subtract(costPrice);
    }
}
