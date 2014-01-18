package br.com.eduardo.loan.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author eduardo
 */
public class CompareDate {

    public static boolean compareDate(Date date) {

        Calendar loanDate = Calendar.getInstance();
        loanDate.setTime(date);

        Calendar now = Calendar.getInstance();

        if (loanDate.after(now))
            return false;
        else {
            return true;
        }
    }
}