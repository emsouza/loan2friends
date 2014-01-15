package br.com.eduardo.loan.friend;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.widget.EditText;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.dialog.ContactDialog;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.entity.ContactDTO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.ui.view.EditTextLookup;
import br.com.eduardo.loan.util.ContactFinder;
import br.com.eduardo.loan.util.validator.FriendValidator;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_friend_add)
public class FriendAddActivity extends SherlockActivity {

    @ViewById(R.id.contact_lookup)
    protected EditTextLookup name;

    @ViewById(R.id.ac_friend_add_number)
    protected EditText number;

    @Bean
    protected FriendDAO friendDAO;

    protected Long contactId = null;

    @AfterViews
    protected void afterView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name.setHint(R.string.label_name);
    }

    @Click(R.id.grlookupButton)
    void search() {
        if (ContactFinder.getContacts(this).size() > 0) {
            final ContactDialog dialog = new ContactDialog(this);
            dialog.populate();
            dialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface arg0) {
                    setData(dialog.getItemSelected());
                }
            });
            dialog.show();
        } else {
            Toast.makeText(this, getString(R.string.msg_no_contacs), Toast.LENGTH_SHORT).show();
        }
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
        friend.setName(name.getText());
        friend.setPhone(number.getText().toString());
        if (contactId != null) {
            friend.setContactId(contactId);
        }

        if (FriendValidator.validaFriend(this, friend)) {
            friendDAO.insert(friend);
            finish();
        }
    }

    @Click(R.id.buttonCancel)
    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }
}