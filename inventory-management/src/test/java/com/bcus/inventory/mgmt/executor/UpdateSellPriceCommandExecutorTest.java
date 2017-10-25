package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UpdateSellPriceCommandExecutorTest {
    private static final String ITEM_NAME = "Test1";
    private static final String COMMAND = "create";
    private CommandExecutor updateSellPriceCommandExecutor;
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        updateSellPriceCommandExecutor = new UpdateSellPriceCommandExecutor();
        inventory = mock(Inventory.class);
    }

    @Test
    public void testUpdateExistingItem() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(true);
        Item originalItem = mock(Item.class);
        BigDecimal costPrice = BigDecimal.valueOf(2.00d);
        when(originalItem.getCostPrice()).thenReturn(costPrice);
        when(originalItem.getQuantity()).thenReturn(BigDecimal.valueOf(100));
        when(inventory.getItem(ITEM_NAME)).thenReturn(originalItem);

        updateSellPriceCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "2.2"}, inventory);
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(inventory, times(1)).addItem(captor.capture());
        Item addedItem = captor.getValue();
        assertThat(addedItem.getName(), is(ITEM_NAME));
        assertThat(addedItem.getCostPrice(), is(costPrice));
        assertThat(addedItem.getSellingPrice().toString(), is("2.20"));
        assertThat(addedItem.getQuantity().toString(), is("100"));
    }

    @Test
    public void testUpdateItemWhichDoesNotExist() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(false);
        try {
            updateSellPriceCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, "2.2"}, inventory);
            fail();
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is("Invalid command : Test1 does not exists in inventory."));
            verify(inventory, never()).addItem(any(Item.class));
        }
    }
}