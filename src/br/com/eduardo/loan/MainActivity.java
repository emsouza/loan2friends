package br.com.eduardo.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import br.com.eduardo.loan.ac.config.ConfigActivity;
import br.com.eduardo.loan.ac.friend.FriendActivity;
import br.com.eduardo.loan.ac.item.ItemActivity;
import br.com.eduardo.loan.ac.loan.LoanAddActivity;
import br.com.eduardo.loan.ac.loan.dialog.FilterDialog;
import br.com.eduardo.loan.adapter.LoanViewAdapter;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.dialog.ChangeLog;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.entity.LoanView;
import br.com.eduardo.loan.sms.SMSDialog;
import br.com.eduardo.loan.text.MenuStrings;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.Status;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 10/08/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class MainActivity extends Activity implements TextWatcher {

	static final int DIALOG_CHANGELOG_ID = 0;

	protected ListView listView;

	protected ImageButton iButton;

	protected EditText sText;

	protected LoanViewAdapter adapter;

	protected List<String> status = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_loan_list);

		status.add(String.valueOf(Status.LENDED.id()));
		status.add(String.valueOf(Status.RETURNED.id()));

		listView = (ListView) this.findViewById(R.id.ac_loan_list_view);
		listView.setEmptyView(this.findViewById(R.id.loan_list_empty));

		iButton = (ImageButton) this.findViewById(R.id.title_search);
		sText = (EditText) this.findViewById(R.id.search_text);

		iButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (sText.getVisibility() == View.GONE) {
					sText.setVisibility(View.VISIBLE);
					sText.requestFocus();
				} else {
					sText.setVisibility(View.GONE);
					sText.setText("");
				}
			}
		});

		sText.addTextChangedListener(this);

		ChangeLog cl = new ChangeLog(this);
		if (cl.firstRun()) {
			cl.getLogDialog().show();
		}

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final LoanView loanView = (LoanView) listView.getAdapter().getItem(arg2);
				CharSequence[] items = null;
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				if (loanView.getStatus() == Status.LENDED.id()) {
					items = MenuStrings.getLentMenuStrings(MainActivity.this);
					builder.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							processLentMenu(item, loanView);
						}
					});
				} else if (loanView.getStatus() == Status.RETURNED.id()) {
					items = MenuStrings.getReturnedMenuStrings(MainActivity.this);
					builder.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							processReturnedMenu(item, loanView);
						}
					});
				} else if (loanView.getStatus() == Status.ARCHIVED.id()) {
					items = MenuStrings.getArchivedMenuStrings(MainActivity.this);
					builder.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							processArchivedMenu(item, loanView);
						}
					});
				}
				AlertDialog alert = builder.create();
				alert.show();

				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		sText.setVisibility(View.GONE);
		sText.setText("");
		populate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_main_add:
				openAddLoan();
				return true;
			case R.id.menu_main_filter:
				openFilter();
				return true;
			case R.id.menu_main_friends:
				openFriend();
				return true;
			case R.id.menu_main_items:
				openItem();
				return true;
			case R.id.menu_main_settings:
				openSettings();
				return true;
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}

	protected void openAddLoan() {
		Intent prefIntent = new Intent(this, LoanAddActivity.class);
		this.startActivity(prefIntent);
	}

	protected void openFilter() {
		final FilterDialog filterDialog = new FilterDialog(this, status);
		filterDialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				if (filterDialog.isOperationComplete()) {
					status = filterDialog.updateStatus();
					onResume();
				}
			}
		});
		filterDialog.show();
	}

	protected void openFriend() {
		Intent prefIntent = new Intent(this, FriendActivity.class);
		this.startActivity(prefIntent);
	}

	protected void openItem() {
		Intent prefIntent = new Intent(this, ItemActivity.class);
		this.startActivity(prefIntent);
	}

	protected void openSettings() {
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
			case 2:
				if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
					smsSend(item);
				}
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
		LoanDBManager db = new LoanDBManager(this);
		Loan loan = db.find(id);
		loan.setStatus(Status.RETURNED.id());
		loan.setReturnDate(DateFormatUtil.formatToDB(new Date()));
		db.update(loan);
		db.close();
		onResume();
	}

	protected void markArchived(Integer id) {
		LoanDBManager db = new LoanDBManager(this);
		Loan loan = db.find(id);
		loan.setStatus(Status.ARCHIVED.id());
		db.update(loan);
		db.close();
		onResume();
	}

	protected void smsSend(LoanView item) {
		SMSDialog dialog = new SMSDialog(this, item);
		dialog.show();
	}

	protected void askForDelete(final LoanView item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.delete)).setCancelable(false)
				.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						delete(item);
					}
				}).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.create().show();
	}

	protected void delete(LoanView item) {
		LoanDBManager db = new LoanDBManager(this);
		db.delete(item.getId());
		db.close();
		onResume();
	}

	protected void populate() {
		LoanDBManager db = new LoanDBManager(MainActivity.this);
		adapter = new LoanViewAdapter(MainActivity.this, db.findAll(status));
		db.close();
		listView.setAdapter(adapter);
	}

	public void afterTextChanged(Editable s) {}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int textlength = sText.getText().length();
		if (textlength >= 3) {
			ArrayList<LoanView> loanSort = new ArrayList<LoanView>();
			String text = sText.getText().toString().toUpperCase();
			if (adapter.getItems() != null) {
				for (LoanView lv : adapter.getItems()) {
					if (lv.getTitle().toUpperCase().contains(text) || lv.getName().toUpperCase().contains(text)) {
						loanSort.add(lv);
					}
				}
			}
			listView.setAdapter(new LoanViewAdapter(this, loanSort));
		} else {
			listView.setAdapter(adapter);
		}
	}
}