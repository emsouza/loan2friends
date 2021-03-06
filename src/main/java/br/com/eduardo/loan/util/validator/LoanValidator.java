package br.com.eduardo.loan.util.validator;

import java.util.Date;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.util.CompareDate;
import br.com.eduardo.loan.util.DateFormatUtil;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EBean(scope = Scope.Singleton)
public class LoanValidator {

    @Bean
    protected DateFormatUtil dateTimeFormat;

    public boolean validaLoan(Context context, LoanDTO loan) {
        if (loan.getIdItem() == null || loan.getIdItem() <= 0) {
            Toast toast = Toast.makeText(context, R.string.msg_item_not_selected, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (loan.getIdFriend() == null || loan.getIdFriend() <= 0) {
            Toast toast = Toast.makeText(context, R.string.msg_friend_not_selected, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else {
            Date data = dateTimeFormat.formatToDate(loan.getLentDate());
            if (!CompareDate.compareDate(data)) {
                Toast toast = Toast.makeText(context, R.string.msg_date_time_error, Toast.LENGTH_SHORT);
                toast.show();
                return false;
            } else {
                return true;
            }
        }
    }
}
