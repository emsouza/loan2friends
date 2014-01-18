package br.com.eduardo.loan.item;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.type.ItemFormatter;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.validator.ItemValidator;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EActivity(R.layout.ac_item_add_edit)
public class ItemEditActivity extends SherlockActivity {

    ArrayAdapter<String> typeAdapter;

    @ViewById(R.id.itemImage)
    ImageView imageType;

    @ViewById(R.id.itemLabel)
    TextView label;

    @ViewById(R.id.itemTitle)
    EditText title;

    @ViewById(R.id.itemDescription)
    EditText description;

    @ViewById(R.id.itemType)
    Spinner type;

    @Extra
    Integer id;

    @Bean
    ItemDAO itemDAO;

    @AfterViews
    void afterView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        populateCombo();

        ItemDTO item = itemDAO.find(id);
        type.setSelection(item.getType());
        title.setText(item.getTitle());
        description.setText(item.getDescription());
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
        type.setAdapter(typeAdapter);
    }

    @ItemSelect(R.id.itemType)
    public void processType(boolean selected, int position) {
        if (selected) {
            imageType.setImageResource(ItemTypeImage.typeACImage(position));
            if (((Long) type.getSelectedItemId()).intValue() == ItemFormatter.TYPE_MONEY) {
                label.setText(getString(R.string.label_amount));
                title.setHint(getString(R.string.label_amount));
                title.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
            } else {
                label.setText(getString(R.string.label_title));
                title.setHint(getString(R.string.label_title));
                title.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }
    }

    @Click(R.id.buttonSave)
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

    @Click(R.id.buttonCancel)
    @OptionsItem(android.R.id.home)
    void backHome() {
        finish();
    }
}