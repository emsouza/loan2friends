package br.com.eduardo.loan.util.type;

import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class LoanStatusImage {

	public static int statusImage(Integer status) {
		switch (status) {
			case 0:
				return R.drawable.ic_status_return;
			case 1:
				return R.drawable.ic_status_lent;
			default:
				return R.drawable.ic_icon;
		}
	}
}
