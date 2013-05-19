package br.com.eduardo.loan.action;

import android.content.Context;
import android.content.Intent;
import br.com.eduardo.loan.MainActivity;
import br.com.eduardo.loan.R;

import com.markupartist.android.widget.ActionBar.IntentAction;

public class HomeAction extends IntentAction {

	public HomeAction(Context ctx) {
		super(ctx, new Intent(ctx, MainActivity.class), R.drawable.ic_title_home_default);
	}
}
