package br.com.eduardo.loan;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.com.eduardo.loan.adapter.LoanViewAdapter;
import br.com.eduardo.loan.dialog.ChangeLog;
import br.com.eduardo.loan.dialog.FilterView_;
import br.com.eduardo.loan.friend.FriendActivity_;
import br.com.eduardo.loan.item.ItemActivity_;
import br.com.eduardo.loan.model.LoanDAO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.model.entity.LoanViewDTO;
import br.com.eduardo.loan.settings.SettingsActivity;
import br.com.eduardo.loan.ui.dialog.CustomDialog;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.text.MenuStrings;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.type.StatusParam;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * @author Eduardo Matos de Souza <br>
 *         10/08/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends SherlockFragmentActivity {

    @ViewById(R.id.mainList)
    ListView listView;

    @ViewById(R.id.emptyList)
    TextView empty;

    @Bean
    DateFormatUtil dateTimeFormat;

    @Bean
    LoanDAO loanDAO;

    @AfterViews
    void afterView() {
        ChangeLog cl = new ChangeLog(this);
        if (cl.firstRun()) {
            cl.getLogDialog().show();
        }
        listView.setEmptyView(empty);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populate();
    }

    private void populate() {
        listView.setAdapter(new LoanViewAdapter(MainActivity.this, dateTimeFormat, loanDAO.findAll(StatusParam.INSTANCE.getStatus())));        
    }

    @ItemLongClick(R.id.mainList)
    void listItemLongClicked(final int position) {
        final LoanViewDTO loanView = (LoanViewDTO) listView.getAdapter().getItem(position);
        CharSequence[] items = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        if (loanView.getStatus() == Status.LENDED) {
            items = MenuStrings.getLentMenuStrings(MainActivity.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    processLentMenu(item, loanView);
                }
            });
        } else if (loanView.getStatus() == Status.RETURNED) {
            items = MenuStrings.getReturnedMenuStrings(MainActivity.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    processReturnedMenu(item, loanView);
                }
            });
        } else if (loanView.getStatus() == Status.ARCHIVED) {
            items = MenuStrings.getArchivedMenuStrings(MainActivity.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    processArchivedMenu(item, loanView);
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OptionsItem(R.id.menu_add)
    void openAddLoan() {
        Intent prefIntent = new Intent(this, LoanAddActivity_.class);
        this.startActivity(prefIntent);
    }

    @OptionsItem(R.id.menu_filter)
    void openFilter() {
        CustomDialog dialog = new CustomDialog(new FilterView_(this), R.string.title_filter);
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dg) {
                    populate();
            }
        });
        dialog.show();
    }

    @OptionsItem(R.id.menu_friends)
    void openFriend() {
        Intent prefIntent = new Intent(this, FriendActivity_.class);
        this.startActivity(prefIntent);
    }

    @OptionsItem(R.id.menu_items)
    void openItem() {
        Intent prefIntent = new Intent(this, ItemActivity_.class);
        this.startActivity(prefIntent);
    }

    @OptionsItem(R.id.menu_settings)
    void openSettings() {
        Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
        startActivity(settingsActivity);
    }

    protected void processLentMenu(int key, LoanViewDTO item) {
        switch (key) {
        case 0:
            markReturned(item.getId());
            break;
        case 1:
            askForDelete(item);
            break;
        default:
            break;
        }
    }

    protected void processReturnedMenu(int key, LoanViewDTO item) {
        switch (key) {
        case 0:
            edit(item);
            break;
        case 1:
            markArchived(item.getId());
            break;
        case 2:
            askForDelete(item);
            break;
        default:
            break;
        }
    }

    protected void processArchivedMenu(int key, LoanViewDTO item) {
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

    void edit(LoanViewDTO item) {
        Intent intent = new Intent(this, LoanEditActivity_.class);
        Bundle bun = new Bundle();
        bun.putInt("id", item.getId());
        intent.putExtras(bun);
        this.startActivity(intent);
    }

    protected void markReturned(Integer id) {
        LoanDTO loan = loanDAO.find(id);
        loan.setStatus(Status.RETURNED);
        loan.setReturnDate(dateTimeFormat.formatToDB(new Date()));
        loanDAO.update(loan);
        onResume();
    }

    protected void markArchived(Integer id) {
        loanDAO.archive(id);
        onResume();
    }

    protected void askForDelete(final LoanViewDTO item) {
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

    protected void delete(LoanViewDTO item) {
        loanDAO.delete(item.getId());
        onResume();
    }
}