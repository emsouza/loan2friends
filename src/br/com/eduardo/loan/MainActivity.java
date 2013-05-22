package br.com.eduardo.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import br.com.eduardo.loan.action.HomeOptionsAction;
import br.com.eduardo.loan.adapter.LoanViewAdapter;
import br.com.eduardo.loan.db.LoanDAO;
import br.com.eduardo.loan.dialog.ChangeLog;
import br.com.eduardo.loan.dialog.FilterDialog;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.entity.LoanView;
import br.com.eduardo.loan.friend.FriendActivity_;
import br.com.eduardo.loan.item.ItemActivity_;
import br.com.eduardo.loan.settings.ConfigActivity;
import br.com.eduardo.loan.text.MenuStrings;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.Status;
import br.com.emsouza.widget.bar.ActionBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemLongClick;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza <br>
 *         10/08/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EActivity(R.layout.ac_main)
@OptionsMenu(R.menu.main_menu)
public class MainActivity extends FragmentActivity {

	@ViewById(R.id.actionBar)
	ActionBar actionBar;

	@ViewById(R.id.ac_main_list)
	protected ListView listView;

	@Bean
	protected LoanDAO loanDAO;

	protected ImageButton iButton;

	protected EditText sText;

	protected LoanViewAdapter adapter;

	protected List<String> status = new ArrayList<String>();

	@AfterViews
	void afterView() {
		ChangeLog cl = new ChangeLog(this);
		if (cl.firstRun()) {
			cl.getLogDialog().show();
		}
		actionBar.setHomeAction(new HomeOptionsAction(this));
		listView.setEmptyView(this.findViewById(R.id.loan_list_empty));
	}

	@Override
	protected void onResume() {
		super.onResume();
		listView.setAdapter(new LoanViewAdapter(MainActivity.this, loanDAO.findAll(status)));
	}

	@ItemLongClick(R.id.ac_main_list)
	void listItemLongClicked(final int position) {
		final LoanView loanView = (LoanView) listView.getAdapter().getItem(position);
		CharSequence[] items = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

		if (loanView.getStatus() == Status.LENDED.id()) {
			items = MenuStrings.getLentMenuStrings(MainActivity.this);
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					processLentMenu(item, loanView);
				}
			});
		} else if (loanView.getStatus() == Status.RETURNED.id()) {
			items = MenuStrings.getReturnedMenuStrings(MainActivity.this);
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					processReturnedMenu(item, loanView);
				}
			});
		} else if (loanView.getStatus() == Status.ARCHIVED.id()) {
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

	@OptionsItem(R.id.loanAdd)
	void openAddLoan() {
		Intent prefIntent = new Intent(this, LoanAddActivity_.class);
		this.startActivity(prefIntent);
	}

	@OptionsItem(R.id.filter)
	void openFilter() {
		final FilterDialog filterDialog = new FilterDialog(this, status);
		filterDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (filterDialog.isOperationComplete()) {
					status = filterDialog.updateStatus();
					onResume();
				}
			}
		});
		filterDialog.show();
	}

	@OptionsItem(R.id.friendsOpen)
	void openFriend() {
		Intent prefIntent = new Intent(this, FriendActivity_.class);
		this.startActivity(prefIntent);
	}

	@OptionsItem(R.id.itemsOpen)
	void openItem() {
		Intent prefIntent = new Intent(this, ItemActivity_.class);
		this.startActivity(prefIntent);
	}

	@OptionsItem(R.id.settings)
	void openSettings() {
		Intent settingsActivity = new Intent(getBaseContext(), ConfigActivity.class);
		startActivity(settingsActivity);
	}

	protected void processLentMenu(int key, LoanView item) {
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

	protected void processReturnedMenu(int key, LoanView item) {
		switch (key) {
			case 0:
				markArchived(item.getId());
				break;
			case 1:
				askForDelete(item);
				break;
			default:
				break;
		}
	}

	protected void processArchivedMenu(int key, LoanView item) {
		switch (key) {
			case 0:
				askForDelete(item);
				break;
			default:
				break;
		}
	}

	protected void markReturned(Integer id) {
		Loan loan = loanDAO.find(id);
		loan.setStatus(Status.RETURNED.id());
		loan.setReturnDate(DateFormatUtil.formatToDB(new Date()));
		loanDAO.update(loan);
		onResume();
	}

	protected void markArchived(Integer id) {
		loanDAO.archive(id);
		onResume();
	}

	protected void askForDelete(final LoanView item) {
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

	protected void delete(LoanView item) {
		loanDAO.delete(item.getId());
		onResume();
	}
}
