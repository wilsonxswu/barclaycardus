package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.util.FileUtil;
import com.bcus.inventory.mgmt.valueobject.Inventory;
import com.bcus.inventory.mgmt.valueobject.Item;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReportCommandExecutorTest {

    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private ReportCommandExecutor executor;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outStream));
        executor = new ReportCommandExecutor();
    }

    @Test
    public void testGetReportForEmptyInventory() throws Exception {
        executor.execute(null, new Inventory());
        assertThat(outStream.toString(), is(FileUtil.readFile("src/test/resources/unit_test_report_case_1.txt")));
    }

    @Test
    public void testGetReportForInventoryWithItems() throws Exception {
        Inventory inventory = new Inventory();
        Item itemA = new Item("ItemA", BigDecimal.valueOf(2.15d), BigDecimal.valueOf(5.55d));
        itemA.setQuantity(BigDecimal.valueOf(10));
        inventory.addItem(itemA);
        Item itemB = new Item("ItemB", BigDecimal.valueOf(1024.15d), BigDecimal.valueOf(3333.33d));
        itemB.setQuantity(BigDecimal.valueOf(26));
        inventory.addItem(itemB);

        inventory.setTotalValue(BigDecimal.valueOf(26649.4d));
        inventory.setProfit(BigDecimal.valueOf(10000.0d));
        executor.execute(null, inventory);
        assertThat(outStream.toString(), is(FileUtil.readFile("src/test/resources/unit_test_report_case_2.txt")));
        assertThat(inventory.getProfit(), is(BigDecimal.ZERO));
    }
}