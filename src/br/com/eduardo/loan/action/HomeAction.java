package br.com.eduardo.loan.action;

import android.content.Context;
import android.content.Intent;
import br.com.eduardo.loan.MainActivity;
import br.com.eduardo.loan.R;
import br.com.emsouza.widget.ActionBar.IntentAction;

/**
 * @author Eduardo Matos de Souza <br>
 *         19/05/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class HomeAction extends IntentAction {

	public HomeAction(Context ctx) {
		super(ctx, new Intent(ctx, MainActivity.class), R.drawable.ic_title_home_default);
	}
}