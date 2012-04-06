package br.com.eduardo.loan.ac.item;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.manager.ItemDBManager;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.util.type.ItemTypeImage;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 11/09/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class ItemDetailActivity extends Activity {

	protected ImageView imageType;

	protected Item item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_item_detail);

		imageType = (ImageView) this.findViewById(R.id.ac_item_detail_image);

		Bundle bun = getIntent().getExtras();
		if (null == bun) {
			return;
		}

		Integer id = bun.getInt("id");
		ItemDBManager dbItem = new ItemDBManager(this);
		item = dbItem.find(id);
		dbItem.close();

		EditText title = (EditText) findViewById(R.id.ac_item_detail_title);
		EditText description = (EditText) findViewById(R.id.ac_item_detail_description);

		title.setText(item.getTitle());

		if (item.getDescription() != null && item.getDescription().length() > 0) {
			description.setText(item.getDescription());
		} else {
			description.setText("...");
		}

		imageType.setImageResource(ItemTypeImage.typeACImage(item.getType()));
	}
}