package br.com.eduardo.loan.util.validator;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza<br>
 *         13/08/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class MailValidator {

	public static boolean validaMessage(Context context, String title, String message) {
		if (title != null && title.length() > 0) {
			if (message != null && message.length() > 0) {
				return true;
			} else {
				Toast toast = Toast.makeText(context, R.string.message_error, Toast.LENGTH_SHORT);
				toast.show();
				return false;
			}
		} else {
			Toast toast = Toast.makeText(context, R.string.title_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
	}
}
