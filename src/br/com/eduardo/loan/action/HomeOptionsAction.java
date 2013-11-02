package br.com.eduardo.loan.action;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.eduardo.loan.MainActivity_;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.FriendActivity_;
import br.com.eduardo.loan.item.ItemActivity_;
import br.com.emsouza.widget.bar.action.CustomAction;
import br.com.emsouza.widget.slidingmenu.SlidingMenu;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 20/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
public class HomeOptionsAction extends CustomAction {

    protected Activity activity;

    protected SlidingMenu menu;

    public HomeOptionsAction(Activity activity) {
        this.activity = activity;
        menu = new SlidingMenu(activity, SlidingMenu.SLIDING_CONTENT);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.setBackgroundColor(activity.getResources().getColor(R.color.gray));
        menu.setMenu(R.layout.ac_menu);

        menu.findViewById(R.id.menuHome).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                home();
            }
        });

        menu.findViewById(R.id.menuFriend).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                friend();
            }
        });

        menu.findViewById(R.id.menuItem).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                item();
            }
        });
    }

    @Override
    public int getDrawable() {
        return R.drawable.ic_action;
    }

    @Override
    public void performAction(View view) {
        menu.showMenu(true);
    }

    protected void home() {
        Intent intent = new Intent(activity, MainActivity_.class);
        activity.startActivity(intent);
    }

    protected void friend() {
        Intent intent = new Intent(activity, FriendActivity_.class);
        activity.startActivity(intent);
    }

    protected void item() {
        Intent intent = new Intent(activity, ItemActivity_.class);
        activity.startActivity(intent);
    }
}