package br.com.eduardo.loan.util.type;

import br.com.eduardo.loan.util.CurrencySymbol;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public abstract class ItemFormatter {

    public final static int TYPE_MONEY = 6;

    public static String formatItemName(Integer type, String name) {
        switch (type) {
        case TYPE_MONEY:
            return CurrencySymbol.format(name);
        default:
            return name;
        }
    }
}