package integration.com.bcus.inventory.mgmt;

import com.bcus.inventory.mgmt.InventoryManager;
import com.bcus.inventory.mgmt.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IntegrationTest {

    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private InventoryManager inventoryManager;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outStream));
        inventoryManager = new InventoryManager();
    }

    @Test
    public void executeMultipleCommandsContainOneReport() throws IOException {
        executeMultipleCommand("int_test_case_1_commands.txt", "int_test_case_1_report.txt");
    }

    @Test
    public void executeMultipleCommandsContainMultipleReports() throws IOException {
        executeMultipleCommand("int_test_case_2_commands.txt", "int_test_case_2_report.txt");
    }

    @Test
    public void executeMultipleCommandsContainsUpdateSellPrice() throws IOException {
        executeMultipleCommand("int_test_case_3_commands.txt", "int_test_case_3_report.txt");
    }

    private void executeMultipleCommand(String commandFile, String expectedReportFile) throws IOException {
        String resourcePath = "src/test/resources/";
        List<String> commands = FileUtil.readLines(resourcePath + commandFile);
        commands.forEach((command) -> inventoryManager.manage(command));
        assertThat(outStream.toString(), is(FileUtil.readFile(resourcePath + expectedReportFile)));
    }

}
