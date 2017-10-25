package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class BuyCommandExecutorTest {

    private static final String ITEM_NAME = "Test1";
    private static final String COMMAND = "updateBuy";
    private CommandExecutor buyCommandExecutor;

    @Before
    public void setUp() throws Exception {
        buyCommandExecutor = new BuyCommandExecutor();
    }

    @Test
    public void testBuyAnExistItem() {
        Inventory inventory = new Inventory();
        Item item = new Item(ITEM_NAME, BigDecimal.valueOf(2.00d), BigDecimal.valueOf(3.00d));
        inventory.addItem(item);
        buyCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "10"}, inventory);
        assertThat(item.getQuantity(), is(BigDecimal.valueOf(10)));
        assertThat(inventory.getTotalValue(), is(BigDecimal.valueOf(20.00d)));
    }

    @Test
    public void testBuyItemButItDoesNotExist() {
        Inventory inventory = new Inventory();
        try {
            buyCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "10"}, inventory);
            fail();
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is("Invalid command : Test1 does not exists in inventory."));
        }
    }
}