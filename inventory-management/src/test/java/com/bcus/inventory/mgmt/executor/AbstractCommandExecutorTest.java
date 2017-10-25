package com.bcus.inventory.mgmt.executor;

import com.bcus.inventory.mgmt.valueobject.Inventory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AbstractCommandExecutorTest {

    private AbstractCommandExecutor executor;

    @Before
    public void setUp() throws Exception {
        executor = new AbstractCommandExecutor() {
            @Override
            public void execute(String[] arguments, Inventory inventory) {
            }
        };
    }

    @Test
    public void testValidateArgNumber() throws Exception {
        String usage = "For Test Only";
        executor.validateArgNumber(3, 3, usage);
        executor.validateArgNumber(4, 3, usage);

        try {
            executor.validateArgNumber(3, 4, usage);
            fail();
        } catch (IllegalArgumentException ie) {
            assertThat(ie.getMessage(), is("Argument is missing in command. Usage : For Test Only"));
        }
    }

    @Test
    public void parseValidPriceString() throws Exception {
        BigDecimal price = executor.parsePriceToBigDecimal("1");
        assertThat(price.toString(), is("1.00"));

        price = executor.parsePriceToBigDecimal("1.11");
        assertThat(price.toString(), is("1.11"));

        price = executor.parsePriceToBigDecimal("100.123456");
        assertThat(price.toString(), is("100.12"));

        price = executor.parsePriceToBigDecimal("12.34567");
        assertThat(price.toString(), is("12.35"));
    }


    @Test
    public void testParseInvalidPriceString() {
        try {
            executor.parsePriceToBigDecimal("10.01ABC");
            fail();
        } catch (IllegalArgumentException ie) {
            assertThat(ie.getMessage(), is("Price value is not valid in command : 10.01ABC"));
        }
    }

    @Test
    public void parseValidQuantityString() throws Exception {
        BigDecimal price = executor.parseQuantityToBigDecimal("1");
        assertThat(price.toString(), is("1"));

        price = executor.parseQuantityToBigDecimal("1.11");
        assertThat(price.toString(), is("1"));

        price = executor.parseQuantityToBigDecimal("100.99");
        assertThat(price.toString(), is("100"));
    }


    @Test
    public void parseInvalidQuantityString() {
        try {
            executor.parseQuantityToBigDecimal("10X");
            fail();
        } catch (IllegalArgumentException ie) {
            assertThat(ie.getMessage(), is("Quantity value is not valid in command : 10X"));
        }
    }

}