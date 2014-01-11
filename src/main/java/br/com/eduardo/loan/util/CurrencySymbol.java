package br.com.eduardo.loan.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public final class CurrencySymbol {

    public static String format(String amount) {
        BigDecimal parsed = new BigDecimal(amount).setScale(2,BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);                
        return NumberFormat.getCurrencyInstance().format(parsed);
    }
}