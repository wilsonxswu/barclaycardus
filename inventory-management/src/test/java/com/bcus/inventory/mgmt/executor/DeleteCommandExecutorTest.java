package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DeleteCommandExecutorTest {
    private static final String ITEM_NAME = "Test1";
    private static final String COMMAND = "delete";
    private CommandExecutor deleteCommandExecutor;
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        deleteCommandExecutor = new DeleteCommandExecutor();
        inventory = mock(Inventory.class);
    }

    @Test
    public void testDeleteItem() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(true);
        Item item = mock(Item.class);
        when(item.getValue()).thenReturn(BigDecimal.valueOf(1000.0d));
        when(inventory.getItem(ITEM_NAME)).thenReturn(item);
        when(inventory.getProfit()).thenReturn(BigDecimal.valueOf(9000.0d));
        when(inventory.getTotalValue()).thenReturn(BigDecimal.valueOf(18000.0d));

        deleteCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME}, inventory);
        verify(inventory, times(1)).deleteItem(eq(ITEM_NAME));
        verify(inventory, times(1)).setProfit(eq(BigDecimal.valueOf(8000.0d)));
        verify(inventory, times(1)).setTotalValue(eq(BigDecimal.valueOf(17000.0d)));
    }

    @Test
    public void testDeleteItemButItDoesNotExist() {
        when(inventory.existItem(ITEM_NAME)).thenReturn(false);
        deleteCommandExecutor.execute(new String[]{COMMAND, ITEM_NAME}, inventory);
        verify(inventory, never()).deleteItem(any(String.class));
        verify(inventory, never()).setProfit(any(BigDecimal.class));
    }

}