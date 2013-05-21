package br.com.eduardo.loan.friend;

import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.FriendDAO;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.validator.FriendValidator;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EActivity(R.layout.ac_friend_edit)
public class FriendEditActivity extends FragmentActivity {

	@ViewById(R.id.ac_friend_edit_name)
	protected EditText name;

	@ViewById(R.id.ac_friend_edit_number)
	protected EditText number;

	@Bean
	protected FriendDAO friendDAO;

	@Extra
	protected Integer id;

	@AfterViews
	void afterView() {
		number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		Friend friend = friendDAO.find(id);
		name.setText(friend.getName());
		number.setText(friend.getPhone());
	}

	@Click(R.id.ac_friend_edit_save)
	void saveFriend() {
		Friend friend = new Friend();
		friend.setId(id);
		friend.setName(((EditText) this.findViewById(R.id.ac_friend_edit_name)).getText().toString());
		friend.setPhone(((EditText) this.findViewById(R.id.ac_friend_edit_number)).getText().toString());

		if (FriendValidator.validaFriend(this, friend)) {
			friendDAO.update(friend);
			finish();
		}
	}

	@Override
	@Click(R.id.ac_friend_edit_cancel)
	public void finish() {
		super.finish();
	}
}