package br.com.eduardo.loan.friend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.action.HomeAction;
import br.com.eduardo.loan.adapter.FriendAdapter;
import br.com.eduardo.loan.db.FriendDAO;
import br.com.eduardo.loan.db.LoanDAO;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.contact.ContactImporter;
import br.com.emsouza.widget.bar.ActionBar;

import com.googlecode.androidannotations.annotations.AfterViews;
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
@EActivity(R.layout.ac_friend)
@OptionsMenu(R.menu.friend_menu)
public class FriendActivity extends FragmentActivity {

	@ViewById(R.id.actionBar)
	ActionBar actionBar;

	@ViewById(R.id.ac_friend_list_view)
	ListView listView;

	protected ImageButton iButton;

	protected FriendAdapter adapter;

	protected ProgressDialog dialog;

	@AfterViews
	void afterInject() {
		actionBar.setHomeAction(new HomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);
		listView.setEmptyView(this.findViewById(R.id.friend_list_empty));
	}

	@OptionsItem(R.id.menu_ac_friend_add)
	void openAddFriend() {
		Intent prefIntent = new Intent(this, FriendAddActivity_.class);
		this.startActivity(prefIntent);
	}

	@OptionsItem(R.id.menu_ac_friend_add_all)
	void importAll() {
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
		FriendDAO db = new FriendDAO(FriendActivity.this);
		adapter = new FriendAdapter(FriendActivity.this, db.findAll());
		listView.setAdapter(adapter);
		db.close();
	}

	@ItemLongClick(R.id.ac_friend_list_view)
	void listItemLongClicked(final int position) {
		final CharSequence[] items = { getString(R.string.option_edit), getString(R.string.option_delete), getString(R.string.option_cancel) };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				Friend f = (Friend) listView.getAdapter().getItem(position);
				processMenu(item, f);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
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
		Intent intent = new Intent(this, FriendEditActivity_.class);
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
		LoanDAO dbLoan = new LoanDAO(this);
		FriendDAO dbFriend = new FriendDAO(this);
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