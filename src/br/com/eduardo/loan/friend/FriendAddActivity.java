package br.com.eduardo.loan.friend;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.dialog.ContactDialog;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.entity.ContactDTO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.util.validator.FriendValidator;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EActivity(R.layout.ac_friend_add)
public class FriendAddActivity extends FragmentActivity {

	@ViewById(R.id.ac_friend_add_name)
	protected EditText name;

	@ViewById(R.id.ac_friend_add_number)
	protected EditText number;

	@Bean
	protected FriendDAO friendDAO;

	protected Long contactId = null;

	protected static final int DIALOG_CONTACTS_ID = 0;

	@Click(R.id.ac_friend_add_search)
	void search() {
		final ContactDialog dialog = new ContactDialog(this);
		dialog.populate();
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface arg0) {
				setData(dialog.getItemSelected());
			}
		});
		dialog.show();
	}

	protected void setData(ContactDTO item) {
		if (item != null) {
			name.setText(item.getName());
			number.setText(item.getNumber() != null ? item.getNumber() : "");
			contactId = item.getContactId();
		}
	}

	@Click(R.id.buttonSave)
	void saveFriend() {
		FriendDTO friend = new FriendDTO();
		friend.setName(name.getText().toString());
		friend.setPhone(number.getText().toString());
		if (contactId != null) {
			friend.setContactId(contactId);
		}

		if (FriendValidator.validaFriend(this, friend)) {
			friendDAO.insert(friend);
			finish();
		}
	}

	@Override
	@Click(R.id.buttonCancel)
	public void finish() {
		super.finish();
	}
}