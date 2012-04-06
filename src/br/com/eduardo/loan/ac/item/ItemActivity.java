package br.com.eduardo.loan.ac.item;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.adapter.ItemAdapter;
import br.com.eduardo.loan.db.manager.ItemDBManager;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.entity.Item;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ItemActivity extends Activity implements OnItemLongClickListener, TextWatcher {

	protected ListView listView;

	protected ImageButton iButton;

	protected EditText sText;

	protected ItemAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_item_list);

		listView = (ListView) this.findViewById(R.id.ac_item_list_view);
		listView.setEmptyView(this.findViewById(R.id.item_list_empty));

		listView.setOnItemLongClickListener(this);

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.items_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_ac_item_add:
				openAddItem();
				return true;
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}

	protected void openAddItem() {
		Intent prefIntent = new Intent(this, ItemAddActivity.class);
		this.startActivity(prefIntent);
	}

	private void populate() {
		ItemDBManager db = new ItemDBManager(ItemActivity.this);
		adapter = new ItemAdapter(ItemActivity.this, db.findAll());
		db.close();
		listView.setAdapter(adapter);
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		final CharSequence[] items = { getString(R.string.option_detail), getString(R.string.option_edit), getString(R.string.option_delete),

		getString(R.string.option_cancel) };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Item f = (Item) listView.getAdapter().getItem(arg2);
				processMenu(item, f);
			}
		});

		AlertDialog alert = builder.create();

		alert.show();

		return true;
	}

	protected void processMenu(int key, Item item) {
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

	protected void detail(Item item) {
		Intent intent = new Intent(this, ItemDetailActivity.class);
		Bundle bun = new Bundle();
		bun.putInt("id", item.getId());
		intent.putExtras(bun);
		this.startActivity(intent);
	}

	protected void edit(Item item) {
		Intent intent = new Intent(this, ItemEditActivity.class);
		Bundle bun = new Bundle();
		bun.putInt("id", item.getId());
		intent.putExtras(bun);
		this.startActivity(intent);
	}

	protected void askForDelete(final Item item) {
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

	protected void delete(Item item) {
		LoanDBManager loanDB = new LoanDBManager(this);
		loanDB.deleteItem(item.getId());

		ItemDBManager db = new ItemDBManager(this);
		db.delete(item.getId());
		db.close();

		onResume();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sText.setVisibility(View.GONE);
		sText.setText("");
		populate();
	}

	public void afterTextChanged(Editable s) {}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int textlength = sText.getText().length();
		if (textlength >= 3) {
			ArrayList<Item> itemSort = new ArrayList<Item>();
			String text = sText.getText().toString().toUpperCase();
			if (adapter.getItems() != null) {
				for (Item item : adapter.getItems()) {
					if (item.getTitle().toUpperCase().contains(text)) {
						itemSort.add(item);
					}
				}
			}
			listView.setAdapter(new ItemAdapter(this, itemSort));
		} else {
			listView.setAdapter(adapter);
		}
	}
}