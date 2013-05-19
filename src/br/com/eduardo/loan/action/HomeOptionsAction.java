package br.com.eduardo.loan.action;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.FriendActivity;
import br.com.eduardo.loan.item.ItemActivity;
import br.com.eduardo.loan.loan.LoanAddActivity;
import br.com.eduardo.loan.settings.ConfigActivity;

import com.markupartist.android.widget.ActionBar.Action;

public class HomeOptionsAction implements Action {

	protected final Context ctx;

	protected final QuickAction quickAction;

	public HomeOptionsAction(final Context ctx) {
		this.ctx = ctx;
		this.quickAction = new QuickAction(this.ctx, QuickAction.VERTICAL);

		Resources res = ctx.getResources();

		ActionItem loan = new ActionItem(1, res.getString(R.string.menu_main_add), res.getDrawable(R.drawable.ic_menu_add));
		ActionItem filter = new ActionItem(2, res.getString(R.string.menu_main_filter), res.getDrawable(R.drawable.ic_menu_filter));
		ActionItem friends = new ActionItem(3, res.getString(R.string.menu_main_friends), res.getDrawable(R.drawable.ic_menu_friends));
		ActionItem items = new ActionItem(4, res.getString(R.string.menu_main_items), res.getDrawable(R.drawable.ic_menu_items));
		ActionItem settings = new ActionItem(5, res.getString(R.string.menu_main_settings), res.getDrawable(R.drawable.ic_menu_config));

		quickAction.addActionItem(loan);
		quickAction.addActionItem(filter);
		quickAction.addActionItem(friends);
		quickAction.addActionItem(items);
		quickAction.addActionItem(settings);

		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				switch (actionId) {
					case 1:
						startActivity(ctx, LoanAddActivity.class);
						break;
					case 2:

						break;
					case 3:
						startActivity(ctx, FriendActivity.class);
						break;
					case 4:
						startActivity(ctx, ItemActivity.class);
						break;
					case 5:
						startActivity(ctx, ConfigActivity.class);
						break;
					default:
						break;
				}
			}
		});
	}

	private void startActivity(Context ctx, Class<?> clazz) {
		Intent activity = new Intent(ctx.getApplicationContext(), clazz);
		ctx.startActivity(activity);
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_action;
	}

	@Override
	public void performAction(View view) {
		quickAction.show(view);
	}
}