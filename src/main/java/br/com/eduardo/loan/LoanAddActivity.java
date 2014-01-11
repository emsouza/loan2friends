package br.com.eduardo.loan;

import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import br.com.eduardo.loan.friend.dialog.FriendSearchDialog;
import br.com.eduardo.loan.item.dialog.ItemSearchDialog;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.ui.view.EditTextLookup;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.validator.LoanValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_loan_add)
public class LoanAddActivity extends FragmentActivity {

    @ViewById(R.id.itemLookup)
    EditTextLookup itemLookup;

    @ViewById(R.id.friendLookup)
    EditTextLookup friendLookup;

    @ViewById(R.id.dateText)
    EditTextLookup dateText;

    @Bean
    LoanDAO loanDAO;

    @Bean
    DateFormatUtil dateTimeFormat;

    @Bean
    LoanValidator validator;

    ItemDTO item;

    FriendDTO friend;

    @AfterViews
    void afterView() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        itemLookup.disableTextComponent();
        itemLookup.setHint(R.string.hint_select_item);
        friendLookup.disableTextComponent();
        friendLookup.setHint(R.string.hint_select_friend);
        dateText.disableTextComponent();
        dateText.setText(dateTimeFormat.formatDateToScreen(Calendar.getInstance().getTime()));
    }

    @Click(R.id.buttonCancel)
    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }

    @Click(R.id.itemLookup)
    protected void findItem() {
        final ItemSearchDialog dialog = new ItemSearchDialog(this);
        dialog.populate();
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface di) {
                item = dialog.getItemSelected();
                if (item != null) {
                    itemLookup.setText(item.getTitle());
                }
            }
        });
        dialog.show();
    }

    @Click(R.id.friendLookup)
    protected void findFriend() {
        final FriendSearchDialog dialog = new FriendSearchDialog(this);
        dialog.populate();
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface di) {
                friend = dialog.getItemSelected();
                if (friend != null) {
                    friendLookup.setText(friend.getName());
                }
            }
        });
        dialog.show();
    }

    @Click(R.id.dateText)
    protected void dateClick() {
        Date date = dateTimeFormat.formatScreenToDate(dateText.getText());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                dateText.setText(dateTimeFormat.formatDateToScreen(calendar.getTime()));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, dateSet,year, month, day);
        dialog.show();
    }

    @Click(R.id.buttonSave)
    protected void save() {
        LoanDTO loan = new LoanDTO();
        loan.setIdFriend(friend != null ? friend.getId() : null);
        loan.setIdItem(item != null ? item.getId() : null);
        loan.setStatus(Status.LENDED);
        loan.setLentDate(dateTimeFormat.formatToDB(dateTimeFormat.formatScreenToDate(dateText.getText())));

        if (validator.validaLoan(this, loan)) {
            loanDAO.insert(loan);
            loanDAO.close();
            finish();
        }
    }
}