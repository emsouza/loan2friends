package br.com.eduardo.loan.item;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.validator.ItemValidator;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EActivity(R.layout.ac_item_add_edit)
public class ItemAddActivity extends FragmentActivity {

	protected ArrayAdapter<String> typeAdapter;

	@ViewById(R.id.ac_item_add_type_image)
	protected ImageView imageType;

	@ViewById(R.id.ac_item_add_title)
	protected EditText title;

	@ViewById(R.id.ac_item_add_description)
	protected EditText description;

	@ViewById(R.id.ac_item_add_type)
	protected Spinner type;

	@AfterViews
	void afterView() {
		type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				imageType.setImageResource(ItemTypeImage.typeACImage(arg2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		populateCombo();
		type.setAdapter(typeAdapter);
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

	@Click(R.id.buttonSave)
	void saveItem() {
		ItemDTO item = new ItemDTO();
		item.setTitle(title.getText().toString());
		item.setDescription(description.getText().toString());
		item.setStatus(Status.AVAILABLE.id());
		item.setType(Long.valueOf(type.getSelectedItemId()).intValue());
		if (ItemValidator.validaItem(this, item)) {
			ItemDAO db = new ItemDAO(this);
			db.insert(item);
			db.close();

			finish();
		}
	}

	@Override
	@Click(R.id.buttonCancel)
	public void finish() {
		super.finish();
	}
}