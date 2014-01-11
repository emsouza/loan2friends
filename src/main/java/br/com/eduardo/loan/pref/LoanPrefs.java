package br.com.eduardo.loan.pref;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Eduardo Matos de Souza <br>
 *         26/06/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@SharedPref(value=SharedPref.Scope.APPLICATION_DEFAULT)
public interface LoanPrefs {

    @DefaultString("MM/dd/yyyy")
    String dateFormat();

    @DefaultString("HH:mm")
    String timeFormat();
}