package br.com.eduardo.loan.action;

import android.content.Context;
import android.content.Intent;
import br.com.eduardo.loan.MainActivity;
import br.com.eduardo.loan.R;
import br.com.emsouza.widget.bar.action.IntentAction;

/**
 * @author Eduardo Matos de Souza <br>
 *         19/05/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ShareAction extends IntentAction {

	public ShareAction(Context ctx) {
		super(ctx, new Intent(ctx, MainActivity.class));
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_title_home_default;
	}
}
