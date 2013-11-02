package br.com.eduardo.loan.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.action.HomeAction;
import br.com.eduardo.loan.item.adapter.ItemAdapter;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.emsouza.widget.bar.ActionBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemLongClick;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EActivity(R.layout.ac_item)
@OptionsMenu(R.menu.items_menu)
public class ItemActivity extends FragmentActivity {

    @ViewById(R.id.actionBar)
    protected ActionBar actionBar;

    @ViewById(R.id.ac_item_list_view)
    protected ListView listView;

    @Bean
    protected ItemDAO itemDAO;

    @Bean
    protected LoanDAO loanDAO;

    protected ItemAdapter adapter;

    @AfterViews
    void afterView() {
        actionBar.setHomeAction(new HomeAction(this));
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView.setEmptyView(this.findViewById(R.id.item_list_empty));
    }

    @OptionsItem(R.id.menu_ac_item_add)
    void openAddItem() {
        Intent prefIntent = new Intent(this, ItemAddActivity_.class);
        this.startActivity(prefIntent);
    }

    @ItemLongClick(R.id.ac_item_list_view)
    void itemLongClick(final int position) {
        final CharSequence[] items = { getString(R.string.option_detail), getString(R.string.option_edit), getString(R.string.option_delete),
                getString(R.string.option_cancel) };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                ItemDTO f = (ItemDTO) listView.getAdapter().getItem(position);
                processMenu(item, f);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void processMenu(int key, ItemDTO item) {
        switch (key) {
            case 0:
                detail(item);
                break;
            case 1:
                edit(item);
                break;
            case 2:
                askForDelete(item);
                break;
            default:
                break;
        }
    }

    protected void detail(ItemDTO item) {
        Intent intent = new Intent(this, ItemDetailActivity_.class);
        Bundle bun = new Bundle();
        bun.putInt("id", item.getId());
        intent.putExtras(bun);
        this.startActivity(intent);
    }

    protected void edit(ItemDTO item) {
        Intent intent = new Intent(this, ItemEditActivity_.class);
        Bundle bun = new Bundle();
        bun.putInt("id", item.getId());
        intent.putExtras(bun);
        this.startActivity(intent);
    }

    protected void askForDelete(final ItemDTO item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete)).setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        delete(item);
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    protected void delete(ItemDTO item) {
        loanDAO.deleteItem(item.getId());
        itemDAO.delete(item.getId());
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(new ItemAdapter(ItemActivity.this, itemDAO.findAll()));
    }
}