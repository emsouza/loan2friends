package br.com.eduardo.loan.util.validator;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.Item;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ItemValidator {

	public static boolean validaItem(Context context, Item item) {
		if (item.getTitle() != null && item.getTitle().length() > 0) {
			return true;
		} else {
			Toast toast = Toast.makeText(context, R.string.item_name_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
	}
}
