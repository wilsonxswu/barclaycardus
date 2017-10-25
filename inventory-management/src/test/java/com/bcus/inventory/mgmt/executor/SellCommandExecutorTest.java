package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class SellCommandExecutorTest {

    private static final String ITEM_NAME = "Test1";
    private static final String COMMAND = "updateSell";
    private CommandExecutor sellCommandExecutor;
    private Item item;
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        sellCommandExecutor = new SellCommandExecutor();

        item = new Item(ITEM_NAME, BigDecimal.valueOf(8.00d), BigDecimal.valueOf(10.00d));
        item.setQuantity(BigDecimal.valueOf(100));

        inventory = new Inventory();
        inventory.addItem(item);
        inventory.setTotalValue(BigDecimal.valueOf(1000.0d));
    }

    @Test
    public void testSellAnExistItem() {
        sellCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "10"}, inventory);
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(90)));
        assertThat(inventory.getProfit(), is(BigDecimal.valueOf(20.00d)));
        assertThat(inventory.getTotalValue(), is(BigDecimal.valueOf(920.00d)));
    }

    @Test
    public void testSellAnExistItemWithSellingQuantityMoreThanItemQuantity() {
        sellCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "105"}, inventory);
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(0)));
        assertThat(inventory.getProfit(), is(BigDecimal.valueOf(200.00d)));
        assertThat(inventory.getTotalValue(), is(BigDecimal.valueOf(200.00d)));
    }

    @Test
    public void testSellItemButItDoesNotExist() {
        Inventory inventory = new Inventory();
        try {
            sellCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "10"}, inventory);
            fail();
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is("Invalid command : Test1 does not exists in inventory."));
        }
    }

}