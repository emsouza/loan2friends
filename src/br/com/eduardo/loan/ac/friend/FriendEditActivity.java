package br.com.eduardo.loan.ac.friend;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.validator.FriendValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendEditActivity extends Activity {

	protected EditText name;

	protected EditText number;

	private Friend friend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_friend_edit);

		name = (EditText) this.findViewById(R.id.ac_friend_edit_name);
		number = (EditText) this.findViewById(R.id.ac_friend_edit_number);

		number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		Bundle bun = getIntent().getExtras();
		if (null == bun)
			return;

		Integer id = bun.getInt("id");

		FriendDBManager db = new FriendDBManager(this);
		friend = db.find(id);
		db.close();

		EditText name = (EditText) this.findViewById(R.id.ac_friend_edit_name);
		EditText number = (EditText) this.findViewById(R.id.ac_friend_edit_number);

		name.setText(friend.getName());
		number.setText(friend.getPhone());

		Button save = (Button) findViewById(R.id.ac_friend_edit_save);
		save.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("synthetic-access")
			public void onClick(View v) {
				saveFriend();
			}
		});

		Button cancel = (Button) findViewById(R.id.ac_friend_edit_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void saveFriend() {
		friend.setName(((EditText) this.findViewById(R.id.ac_friend_edit_name)).getText().toString());
		friend.setPhone(((EditText) this.findViewById(R.id.ac_friend_edit_number)).getText().toString());

		if (FriendValidator.validaFriend(this, friend)) {
			FriendDBManager db = new FriendDBManager(this);
			db.update(friend);
			db.close();

			finish();
		}
	}
}