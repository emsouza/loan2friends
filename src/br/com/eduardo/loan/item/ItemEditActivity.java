package br.com.eduardo.loan.item;

import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.validator.ItemValidator;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ItemSelect;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EActivity(R.layout.ac_item_add_edit)
public class ItemEditActivity extends FragmentActivity {

	protected ArrayAdapter<String> typeAdapter;

	@ViewById(R.id.ac_item_add_type_image)
	protected ImageView imageType;

	@ViewById(R.id.ac_item_add_title)
	protected EditText title;

	@ViewById(R.id.ac_item_add_description)
	protected EditText description;

	@ViewById(R.id.ac_item_add_type)
	protected Spinner type;

	@Extra
	protected Integer id;

	@Bean
	protected ItemDAO itemDAO;

	@AfterViews
	void afterView() {
		typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		populateCombo();
		type.setAdapter(typeAdapter);

		ItemDTO item = itemDAO.find(id);

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

	@ItemSelect(R.id.ac_item_add_type)
	void itemSelect(boolean selected, int position) {
		if (selected) {
			imageType.setImageResource(ItemTypeImage.typeACImage(position));
		}
	}

	@Click(R.id.ac_item_add_save)
	void saveItem() {
		ItemDTO item = itemDAO.find(id);
		item.setTitle(title.getText().toString());
		item.setDescription(description.getText().toString());
		item.setType(Long.valueOf(type.getSelectedItemId()).intValue());

		if (ItemValidator.validaItem(this, item)) {
			itemDAO.update(item);
			finish();
		}
	}

	@Override
	@Click(R.id.ac_item_add_cancel)
	public void finish() {
		super.finish();
	}
}