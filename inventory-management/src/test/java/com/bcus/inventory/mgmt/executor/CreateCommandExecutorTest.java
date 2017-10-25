package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CreateCommandExecutorTest {

    private static final String ITEM_NAME = "Test1";
    private static final String COMMAND = "create";
    private static final String COST_PRICE = "1.10";
    private static final String SELLING_PRICE = "2.20";
    private CommandExecutor createCommandExecutor;
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        createCommandExecutor = new CreateCommandExecutor();
        inventory = mock(Inventory.class);
    }

    @Test
    public void testCreateNewItem() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(false);
        createCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, COST_PRICE, SELLING_PRICE}, inventory);
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(inventory, times(1)).addItem(captor.capture());
        Item addedItem = captor.getValue();
        assertThat(addedItem.getName(), is(ITEM_NAME));
        assertThat(addedItem.getCostPrice().toString(), is(COST_PRICE));
        assertThat(addedItem.getSellingPrice().toString(), is(SELLING_PRICE));
        assertThat(addedItem.getQuantity(), is(BigDecimal.ZERO));
    }

    @Test
    public void testCreateItemWhichAlreadyExists() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(true);
        createCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME, COST_PRICE, SELLING_PRICE}, inventory);
        verify(inventory, never()).addItem(any(Item.class));
    }
}