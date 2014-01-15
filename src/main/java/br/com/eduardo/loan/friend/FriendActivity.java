package br.com.eduardo.loan.friend;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.adapter.FriendAdapter;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.util.contact.ContactImporter;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_friend)
@OptionsMenu(R.menu.menu_friend)
public class FriendActivity extends SherlockActivity {

    @ViewById(R.id.friendList)
    ListView listView;

    @ViewById(R.id.emptyList)
    TextView empty;

    @Bean
    FriendDAO friendDAO;

    @Bean
    LoanDAO loanDAO;

    @AfterViews
    void afterInject() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setEmptyView(empty);
    }

    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }

    @OptionsItem(R.id.menu_friend_add)
    void addFriend() {
        Intent prefIntent = new Intent(this, FriendAddActivity_.class);
        this.startActivity(prefIntent);
    }

    @UiThread
    @OptionsItem(R.id.menu_friend_add_all)
    void addAllFriends() {
        ContactImporter.importContact(this);
        populate();
    }

    @ItemLongClick(R.id.friendList)
    void listItemLongClicked(final int position) {
        final CharSequence[] items = { getString(R.string.option_edit), getString(R.string.option_delete), getString(R.string.option_cancel) };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                FriendDTO f = (FriendDTO) listView.getAdapter().getItem(position);
                processMenu(item, f);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void processMenu(int key, FriendDTO item) {
        switch (key) {
        case 0:
            edit(item);
            break;
        case 1:
            askForDelete(item);
            break;
        default:
            break;
        }
    }

    private void edit(FriendDTO item) {
        Intent intent = new Intent(this, FriendEditActivity_.class);
        Bundle bun = new Bundle();
        bun.putInt("id", item.getId());
        intent.putExtras(bun);
        this.startActivity(intent);
    }

    private void askForDelete(final FriendDTO item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_delete)).setCancelable(false)
                .setPositiveButton(getString(R.string.button_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        delete(item);
                    }
                }).setNegativeButton(getString(R.string.button_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    protected void delete(FriendDTO item) {
        loanDAO.deleteFriend(item.getId());
        friendDAO.delete(item.getId());
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populate();
    }

    private void populate() {
        listView.setAdapter(new FriendAdapter(FriendActivity.this, friendDAO.findAll()));
    }
}