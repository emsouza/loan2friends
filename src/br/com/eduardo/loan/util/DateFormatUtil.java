package br.com.eduardo.loan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.eduardo.loan.pref.LoanPref_;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.googlecode.androidannotations.api.Scope;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EBean(scope = Scope.Singleton)
public class DateFormatUtil {

    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd HHmm", Locale.getDefault());

    private static SimpleDateFormat DATE_FORMAT, DATE_TIME_FORMAT;

    @Pref
    protected LoanPref_ prefs;

    @AfterInject
    public void init() {
        DATE_FORMAT = new SimpleDateFormat(prefs.dateFormat().get(), Locale.getDefault());
        DATE_TIME_FORMAT = new SimpleDateFormat(prefs.dateFormat().get() + " " + prefs.timeFormat().get(), Locale.getDefault());
    }

    public Date formatToDate(String date) {
        try {
            return DB_FORMAT.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }

    public String formatToDB(Date date) {
        return DB_FORMAT.format(date);
    }

    public String formatDateToScreen(Date date) {
        return DATE_FORMAT.format(date);
    }

    public String formatDateTimeToScreen(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }
}
