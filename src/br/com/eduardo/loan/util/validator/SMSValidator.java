package br.com.eduardo.loan.util.validator;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class SMSValidator {

	public static boolean validaMessage(Context context, String phoneNumber, String message) {
		if (phoneNumber != null && phoneNumber.length() > 0) {
			if (message != null && message.length() > 0) {
				return true;
			} else {
				Toast toast = Toast.makeText(context, R.string.message_error, Toast.LENGTH_SHORT);
				toast.show();
				return false;
			}
		} else {
			Toast toast = Toast.makeText(context, R.string.phone_number_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
	}
}
