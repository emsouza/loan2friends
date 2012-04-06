package br.com.eduardo.loan.ac.item;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.manager.ItemDBManager;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.validator.ItemValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class ItemEditActivity extends Activity {

	protected ArrayAdapter<String> typeAdapter;

	protected ImageView imageType;

	protected EditText title;

	protected EditText description;

	protected Spinner type;

	protected Item item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_item_add_edit);

		imageType = (ImageView) this.findViewById(R.id.ac_item_add_type_image);
		title = (EditText) this.findViewById(R.id.ac_item_add_title);
		description = (EditText) this.findViewById(R.id.ac_item_add_description);
		type = (Spinner) this.findViewById(R.id.ac_item_add_type);

		type.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				imageType.setImageResource(ItemTypeImage.typeACImage(arg2));
			}

			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		Button save = (Button) findViewById(R.id.ac_item_add_save);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				update();
			}
		});

		Button cancel = (Button) findViewById(R.id.ac_item_add_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		populateCombo();
		type.setAdapter(typeAdapter);

		Bundle bun = getIntent().getExtras();
		if (null == bun) {
			return;
		}

		Integer id = bun.getInt("id");
		ItemDBManager db = new ItemDBManager(this);
		item = db.find(id);
		db.close();

		title.setText(item.getTitle());
		description.setText(item.getDescription());
		type.setSelection(item.getType());
		imageType.setImageResource(ItemTypeImage.typeACImage(item.getType()));
	}

	private void populateCombo() {
		typeAdapter.add(getString(R.string.type_default));
		typeAdapter.add(getString(R.string.type_cd));
		typeAdapter.add(getString(R.string.type_dvd));
		typeAdapter.add(getString(R.string.type_bluray));
		typeAdapter.add(getString(R.string.type_book));
		typeAdapter.add(getString(R.string.type_game));
		typeAdapter.add(getString(R.string.type_money));
	}

	protected void update() {
		item.setTitle(title.getText().toString());
		item.setDescription(description.getText().toString());
		item.setType(new Long(type.getSelectedItemId()).intValue());

		if (ItemValidator.validaItem(this, item)) {
			ItemDBManager db = new ItemDBManager(this);
			db.update(item);
			db.close();

			finish();
		}
	}
}