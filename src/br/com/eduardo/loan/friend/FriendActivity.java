package br.com.eduardo.loan.friend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.action.HomeAction;
import br.com.eduardo.loan.adapter.FriendAdapter;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.contact.ContactImporter;

import com.markupartist.android.widget.ActionBar;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendActivity extends FragmentActivity implements OnItemLongClickListener {

	protected ActionBar actionBar;

	protected ListView listView;

	protected ImageButton iButton;

	protected FriendAdapter adapter;

	protected ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_friend_list);

		actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setHomeAction(new HomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);

		listView = (ListView) this.findViewById(R.id.ac_friend_list_view);
		listView.setEmptyView(this.findViewById(R.id.friend_list_empty));

		listView.setOnItemLongClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.friend_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_ac_friend_add:
				openAddFriend();
				return true;
			case R.id.menu_ac_friend_add_all:
				importAll();
				return true;
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}

	private void openAddFriend() {
		Intent prefIntent = new Intent(this, FriendAddActivity.class);
		this.startActivity(prefIntent);
	}

	private void importAll() {
		dialog = new ProgressDialog(this) {
			@Override
			public void onBackPressed() {
				// Não faz nada
			}
		};
		dialog.setMessage(getText(R.string.wait));
		dialog.setIndeterminate(true);
		dialog.show();

		new ImportAll().execute();
	}

	protected void populate() {
		FriendDBManager db = new FriendDBManager(FriendActivity.this);
		adapter = new FriendAdapter(FriendActivity.this, db.findAll());
		listView.setAdapter(adapter);
		db.close();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		final CharSequence[] items = { getString(R.string.option_edit), getString(R.string.option_delete), getString(R.string.option_cancel) };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				Friend f = (Friend) listView.getAdapter().getItem(arg2);
				processMenu(item, f);
			}
		});

		AlertDialog alert = builder.create();

		alert.show();

		return true;
	}

	protected void processMenu(int key, Friend item) {
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

	private void edit(Friend item) {
		Intent intent = new Intent(this, FriendEditActivity.class);
		Bundle bun = new Bundle();
		bun.putInt("id", item.getId());
		intent.putExtras(bun);
		this.startActivity(intent);
	}

	private void askForDelete(final Friend item) {
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

	protected void delete(Friend item) {
		LoanDBManager dbLoan = new LoanDBManager(this);
		FriendDBManager dbFriend = new FriendDBManager(this);
		dbLoan.deleteFriend(item.getId());
		dbFriend.delete(item.getId());
		dbLoan.close();
		dbFriend.close();
		onResume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populate();
	}

	/**
	 * Utilizado para importar todos os Contatos da Agenda sem travar a Tela principal.
	 * 
	 * @author Eduardo Matos de Souza <br>
	 *         23/12/2011 <br>
	 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
	 */
	protected class ImportAll extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ContactImporter.importContact(FriendActivity.this);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			FriendActivity.this.populate();
			dialog.dismiss();
			dialog = null;
		}
	}
}