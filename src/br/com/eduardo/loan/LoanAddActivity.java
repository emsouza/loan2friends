package br.com.eduardo.loan;

import java.util.Calendar;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import br.com.eduardo.loan.action.HomeAction;
import br.com.eduardo.loan.friend.dialog.FriendSearchDialog;
import br.com.eduardo.loan.item.dialog.ItemSearchDialog;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.ui.view.DateView;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.validator.LoanValidator;
import br.com.emsouza.widget.bar.ActionBar;
import br.com.emsouza.widget.dateslider.DateSlider;
import br.com.emsouza.widget.dateslider.DefaultDateSlider;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EActivity(R.layout.ac_loan_add)
public class LoanAddActivity extends FragmentActivity {

    @ViewById(R.id.actionBar)
    protected ActionBar actionBar;

    @ViewById(R.id.itemNameText)
    protected EditText itemText;

    @ViewById(R.id.friendNameText)
    protected EditText friendText;

    @ViewById(R.id.dateText)
    protected DateView dateText;

    @Bean
    protected LoanDAO loanDAO;

    @Bean
    protected DateFormatUtil dateTimeFormat;

    @Bean
    protected LoanValidator validator;

    protected ItemDTO item;

    protected FriendDTO friend;

    @AfterViews
    void afterView() {
        actionBar.setHomeAction(new HomeAction(this));
        actionBar.setDisplayHomeAsUpEnabled(true);
        dateText.setDate(Calendar.getInstance());
    }

    @Click(R.id.friendNameText)
    protected void findFriend() {
        final FriendSearchDialog dialog = new FriendSearchDialog(this);
        dialog.populate();
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface di) {
                friend = dialog.getItemSelected();
                if (friend != null) {
                    friendText.setText(friend.getName());
                }
            }
        });
        dialog.show();
    }

    @Click(R.id.itemNameText)
    protected void findItem() {
        final ItemSearchDialog dialog = new ItemSearchDialog(this);
        dialog.populate();
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface di) {
                item = dialog.getItemSelected();
                if (item != null) {
                    itemText.setText(item.getTitle());
                }
            }
        });
        dialog.show();
    }

    @Click(R.id.dateText)
    protected void dateClick() {
        DateSlider.OnDateSetListener dateListener = new DateSlider.OnDateSetListener() {
            @Override
            public void onDateSet(DateSlider view, Calendar selectedDate) {
                dateText.setDate(selectedDate);
            }
        };
        DefaultDateSlider slider = new DefaultDateSlider(this, getString(R.string.date_loan), dateListener, Calendar.getInstance());
        slider.show();
    }

    @Click(R.id.buttonSave)
    protected void save() {
        LoanDTO loan = new LoanDTO();
        loan.setIdFriend(friend != null ? friend.getId() : null);
        loan.setIdItem(item != null ? item.getId() : null);
        loan.setStatus(Status.LENDED.id());
        loan.setLentDate(dateTimeFormat.formatToDB(dateText.getDate().getTime()));

        if (validator.validaLoan(this, loan)) {
            loanDAO.insert(loan);
            loanDAO.close();
            finish();
        }
    }

    @Click(R.id.buttonCancel)
    protected void cancel() {
        finish();
    }
}