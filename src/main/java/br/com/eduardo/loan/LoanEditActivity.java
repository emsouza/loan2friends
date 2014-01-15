package br.com.eduardo.loan;

import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.ui.view.EditTextLookup;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.validator.LoanValidator;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_loan_edit)
public class LoanEditActivity extends SherlockActivity {

    @ViewById(R.id.itemName)
    protected EditText itemLookup;

    @ViewById(R.id.friendName)
    protected EditText friendLookup;

    @ViewById(R.id.dateText)
    EditTextLookup dateText;

    @ViewById(R.id.loanStatus)
    Spinner status;

    DatePicker date;

    TimePicker time;

    @Bean
    LoanDAO loanDAO;

    @Bean
    ItemDAO itemDAO;

    @Bean
    FriendDAO friendDAO;

    @Bean
    DateFormatUtil dateTimeFormat;

    @Bean
    LoanValidator validator;

    @Extra
    Integer id;

    LoanDTO loan;

    ArrayAdapter<String> statusAdapter;

    @AfterViews
    void afterView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusAdapter.add(getString(R.string.status_lent));
        statusAdapter.add(getString(R.string.status_returned));
        statusAdapter.add(getString(R.string.status_achived));

        status.setAdapter(statusAdapter);

        loan = loanDAO.find(id);
        ItemDTO item = itemDAO.find(loan.getIdItem());
        FriendDTO friend = friendDAO.find(loan.getIdFriend());

        friendLookup.setText(friend.getName());
        itemLookup.setText(item.getTitle());

        Date loanDate = dateTimeFormat.formatToDate(loan.getLentDate());
        dateText.setText(dateTimeFormat.formatDateToScreen(loanDate));

        status.setSelection(loan.getStatus());
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
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(dateTimeFormat.formatDateToScreen(calendar.getTime()));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, dateSet, year, month, day);
        dialog.show();
    }

    @Click(R.id.buttonCancel)
    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }

    @Click(R.id.buttonSave)
    protected void update() {
        Integer statusNumber = new Long(status.getSelectedItemId()).intValue();
        Toast.makeText(LoanEditActivity.this, "Status = " + statusNumber, Toast.LENGTH_SHORT).show();

        Date loanDate = dateTimeFormat.formatScreenToDate(dateText.getText());
        loan.setStatus(++statusNumber);
        loan.setLentDate(dateTimeFormat.formatToDB(loanDate));
        loanDAO.update(loan);
        finish();
    }
}