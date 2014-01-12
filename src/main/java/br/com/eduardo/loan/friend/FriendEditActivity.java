package br.com.eduardo.loan.friend;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.util.validator.FriendValidator;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_friend_edit)
public class FriendEditActivity extends SherlockActivity {

    @ViewById(R.id.ac_friend_edit_name)
    EditText name;

    @ViewById(R.id.ac_friend_edit_number)
    EditText number;

    @Bean
    FriendDAO dao;

    @Extra
    Integer id;

    @AfterViews
    void afterView() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        FriendDTO friend = dao.find(id);
        name.setText(friend.getName());
        number.setText(friend.getPhone());
    }

    @Click(R.id.buttonSave)
    void saveFriend() {
        FriendDTO friend = new FriendDTO();
        friend.setId(id);
        friend.setName(name.getText().toString());
        friend.setPhone(number.getText().toString());

        if (FriendValidator.validaFriend(this, friend)) {
            dao.update(friend);
            finish();
        }
    }

    @Click(R.id.buttonCancel)
    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }
}