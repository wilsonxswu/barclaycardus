package com.bcus.inventory.mgmt.valueobject;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void getValue() throws Exception {
        Item item = new Item("Test", new BigDecimal("2.00"), new BigDecimal("2.70"));
        item.setQuantity(BigDecimal.valueOf(100));
        assertThat(item.getValue().toString(), is("200.00"));
    }

    @Test
    public void getProfit() throws Exception {
        Item item = new Item("Test", new BigDecimal("2.00"), new BigDecimal("2.70"));
        assertThat(item.getProfit().toString(), is("0.70"));
    }

}