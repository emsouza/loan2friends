package br.com.eduardo.loan.item;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.item.adapter.ItemAdapter;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_item)
@OptionsMenu(R.menu.menu_item)
public class ItemActivity extends SherlockActivity {

    @ViewById(R.id.itemList)
    ListView listView;

    @ViewById(R.id.emptyList)
    TextView empty;

    @Bean
    ItemDAO itemDAO;

    @Bean
    LoanDAO loanDAO;

    @AfterViews
    void afterView() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setEmptyView(empty);
    }

    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }

    @OptionsItem(R.id.menu_add_item)
    void openAddItem() {
        Intent prefIntent = new Intent(this, ItemAddActivity_.class);
        this.startActivity(prefIntent);
    }

    @ItemLongClick(R.id.itemList)
    void itemLongClick(final int position) {
        final CharSequence[] items = { getString(R.string.option_edit), getString(R.string.option_delete),
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
            edit(item);
            break;
        case 2:
            askForDelete(item);
            break;
        default:
            break;
        }
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