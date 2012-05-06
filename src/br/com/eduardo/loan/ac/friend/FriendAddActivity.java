package br.com.eduardo.loan.ac.friend;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.ac.friend.dialog.ContactDialog;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.entity.Contact;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.validator.FriendValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendAddActivity extends Activity {

	protected EditText name;

	protected EditText number;

	protected Long contactId = null;

	protected static final int DIALOG_CONTACTS_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_friend_add);

		name = (EditText) this.findViewById(R.id.ac_friend_add_name);
		number = (EditText) this.findViewById(R.id.ac_friend_add_number);

		number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		ImageButton search = (ImageButton) findViewById(R.id.ac_friend_add_search);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_CONTACTS_ID);
			}
		});

		Button save = (Button) findViewById(R.id.ac_friend_add_save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveFriend();
			}
		});

		Button cancel = (Button) findViewById(R.id.ac_friend_add_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_CONTACTS_ID:
				final ContactDialog dialog = new ContactDialog(this);
				dialog.populate();
				dialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface arg0) {
						setData(dialog.getItemSelected());
					}
				});
				return dialog;
			default:
				return super.onCreateDialog(id);
		}
	}

	protected void setData(Contact item) {
		if (item != null) {
			EditText name = (EditText) this.findViewById(R.id.ac_friend_add_name);
			EditText number = (EditText) this.findViewById(R.id.ac_friend_add_number);
			if (name != null) {
				name.setText(item.getName());
			}
			if (number != null) {
				number.setText(item.getNumber() != null ? item.getNumber() : "");
			}
			contactId = item.getContactId();
		}
	}

	protected void saveFriend() {
		Friend friend = new Friend();
		friend.setName(((EditText) this.findViewById(R.id.ac_friend_add_name)).getText().toString());
		friend.setPhone(((EditText) this.findViewById(R.id.ac_friend_add_number)).getText().toString());
		if (contactId != null) {
			friend.setContactId(contactId);
		}

		if (FriendValidator.validaFriend(this, friend)) {
			FriendDBManager db = new FriendDBManager(this);
			db.insert(friend);
			db.close();

			finish();
		}
	}
}