package br.com.eduardo.loan.util.validator;

import java.util.Date;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.util.CompareDate;
import br.com.eduardo.loan.util.DateFormatUtil;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class LoanValidator {

	public static boolean validaLoan(Context context, Loan loan) {
		if (loan.getIdItem() == null || loan.getIdItem() <= 0) {
			Toast toast = Toast.makeText(context, R.string.iditem_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		} else if (loan.getIdFriend() == null || loan.getIdFriend() <= 0) {
			Toast toast = Toast.makeText(context, R.string.idfriend_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		} else {
			Date data = DateFormatUtil.formatToDate(loan.getLentDate());
			if (!CompareDate.compareDate(data)) {
				Toast toast = Toast.makeText(context, R.string.date_time_error, Toast.LENGTH_SHORT);
				toast.show();
				return false;
			} else {
				return true;
			}
		}
	}
}
