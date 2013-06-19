package br.com.eduardo.loan.util.validator;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.FriendDTO;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendValidator {

	public static boolean validaFriend(Context context, FriendDTO friend) {
		if (friend.getName() != null && friend.getName().length() > 0) {
			return true;
		} else {
			Toast toast = Toast.makeText(context, R.string.friend_name_error, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
	}
}
