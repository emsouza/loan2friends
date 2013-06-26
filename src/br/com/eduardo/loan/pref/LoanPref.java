package br.com.eduardo.loan.pref;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultString;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Eduardo Matos de Souza <br>
 *         26/06/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@SharedPref
public interface LoanPref {

	@DefaultString("MM/dd/yyyy")
	String dateFormat();

	@DefaultString("HH:mm")
	String timeFormat();
}
