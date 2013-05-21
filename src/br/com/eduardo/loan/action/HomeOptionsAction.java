package br.com.eduardo.loan.action;

import android.app.Activity;
import android.view.View;
import br.com.eduardo.loan.R;
import br.com.emsouza.widget.bar.action.CustomAction;
import br.com.emsouza.widget.slidingmenu.SlidingMenu;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 20/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
public class HomeOptionsAction extends CustomAction {

	SlidingMenu menu;

	public HomeOptionsAction(Activity activity) {
		menu = new SlidingMenu(activity, SlidingMenu.SLIDING_CONTENT);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setBackgroundColor(activity.getResources().getColor(R.color.background_window));
		menu.setMenu(R.layout.ac_menu);

		// menu.
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_action;
	}

	@Override
	public void performAction(View view) {
		menu.showMenu(true);
	}
}