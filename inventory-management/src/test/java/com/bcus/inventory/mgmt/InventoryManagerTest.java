package com.bcus.inventory.mgmt;

import com.bcus.inventory.mgmt.executor.CommandExecutor;
import com.bcus.inventory.mgmt.valueobject.Inventory;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class InventoryManagerTest {

    private ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private InventoryManager inventoryManager;
    private CommandExecutor commandExecutor;

    @Before
    public void setUp() throws Exception {
        System.setErr(new PrintStream(errStream));
        inventoryManager = new InventoryManager();

        Map<String, CommandExecutor> executorMap = new HashMap<>();
        commandExecutor = mock(CommandExecutor.class);
        executorMap.put("create", commandExecutor);
        Field commandExecutorMap = InventoryManager.class.getDeclaredField("commandExecutorMap");
        commandExecutorMap.setAccessible(true);
        commandExecutorMap.set(inventoryManager, executorMap);
    }

    @Test
    public void testValidCommand() throws NoSuchFieldException, IllegalAccessException {
        inventoryManager.manage("create TestItem 10 20");
        verify(commandExecutor, times(1)).execute(isA(String[].class), isA(Inventory.class));
        assertThat(errStream.toString(), is(""));
    }

    @Test
    public void testInvalidCommand() {
        inventoryManager.manage("updateCostPrice TestItem 15");
        verify(commandExecutor, never()).execute(isA(String[].class), isA(Inventory.class));
        assertThat(errStream.toString(), CoreMatchers.startsWith("Unsupported command : updateCostPrice"));
    }

    @Test
    public void testExceptionCapturedDuringExecution() {
        doThrow(new RuntimeException("For Test Only")).when(commandExecutor).execute(isA(String[].class), isA(Inventory.class));

        inventoryManager.manage("create TestItem 10 20");

        verify(commandExecutor, times(1)).execute(isA(String[].class), isA(Inventory.class));
        assertThat(errStream.toString(), CoreMatchers.startsWith("Error occurred when executing command : [create TestItem 10 20]. Reason : For Test Only"));

    }
}