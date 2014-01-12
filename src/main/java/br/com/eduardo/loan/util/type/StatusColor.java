package br.com.eduardo.loan.util.type;

import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class StatusColor {

    public static int statusImage(int status) {
        switch (status) {
            case Status.LENDED:
                return R.color.red;
            case Status.ARCHIVED:
                return R.color.gray_dark;
            default:
                return R.color.green;
        }
    }
}
