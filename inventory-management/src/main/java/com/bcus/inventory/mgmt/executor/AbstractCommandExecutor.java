package com.bcus.inventory.mgmt.executor;


import java.math.BigDecimal;

abstract class AbstractCommandExecutor implements CommandExecutor {

    void validateArgNumber(int argumentNum, int expectNum, String usage) {
        if (argumentNum < expectNum) {
            throw new IllegalArgumentException("Argument is missing in command. Usage : " + usage);
        }
    }

    BigDecimal parsePriceToBigDecimal(String priceStr) {
        try {
            return new BigDecimal(priceStr).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Price value is not valid in command : " + priceStr);
        }
    }

    BigDecimal parseQuantityToBigDecimal(String quantityStr) {
        try {
            return new BigDecimal(quantityStr).setScale(0, BigDecimal.ROUND_DOWN);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Quantity value is not valid in command : " + quantityStr);
        }
    }
}
