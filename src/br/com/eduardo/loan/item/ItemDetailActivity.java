package br.com.eduardo.loan.item;

import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ImageView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.ItemDAO;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.util.type.ItemTypeImage;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 11/09/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EActivity(R.layout.ac_item_detail)
public class ItemDetailActivity extends FragmentActivity {

	@ViewById(R.id.ac_item_detail_title)
	protected EditText title;

	@ViewById(R.id.ac_item_detail_description)
	protected EditText description;

	@ViewById(R.id.ac_item_detail_image)
	protected ImageView imageType;

	@Bean
	protected ItemDAO itemDAO;

	@Extra
	protected Integer id;

	@AfterViews
	void afterView() {
		Item item = itemDAO.find(id);
		title.setText(item.getTitle());
		if (item.getDescription() != null && item.getDescription().length() > 0) {
			description.setText(item.getDescription());
		} else {
			description.setText("...");
		}
		imageType.setImageResource(ItemTypeImage.typeACImage(item.getType()));
	}
}