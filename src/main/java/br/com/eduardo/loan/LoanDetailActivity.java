package br.com.eduardo.loan;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.widget.EditText;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.ItemFormatter;
import br.com.eduardo.loan.util.validator.LoanValidator;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_loan_view)
public class LoanDetailActivity extends SherlockActivity {

    @ViewById(R.id.itemName)
    EditText itemLookup;

    @ViewById(R.id.friendName)
    EditText friendLookup;

    @ViewById(R.id.dateText)
    EditText dateText;

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

    @AfterViews
    void afterView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loan = loanDAO.find(id);
        ItemDTO item = itemDAO.find(loan.getIdItem());
        FriendDTO friend = friendDAO.find(loan.getIdFriend());

        friendLookup.setText(friend.getName());
        itemLookup.setText(ItemFormatter.formatItemName(item.getType(),  item.getTitle()));

        Date loanDate = dateTimeFormat.formatToDate(loan.getLentDate());
        dateText.setText(dateTimeFormat.formatDateToScreen(loanDate));
    }

    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }
}