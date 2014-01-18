package br.com.eduardo.loan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.eduardo.loan.pref.LoanPrefs_;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EBean(scope = Scope.Singleton)
public class DateFormatUtil {

    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd HHmmss", Locale.getDefault());

    @Pref
    protected LoanPrefs_ prefs;

    public Date formatToDate(String date) {
        try {
            return DB_FORMAT.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }

    public String formatToDB(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar current = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, current.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, current.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, current.get(Calendar.SECOND));
        return DB_FORMAT.format(cal.getTime());
    }

    public String formatDateToScreen(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(prefs.dateFormat().get(), Locale.getDefault());
        return dateFormat.format(date);
    }

    public Date formatScreenToDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(prefs.dateFormat().get(), Locale.getDefault());
            return dateFormat.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }
}