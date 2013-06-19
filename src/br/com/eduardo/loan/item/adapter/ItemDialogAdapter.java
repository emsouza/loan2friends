package br.com.eduardo.loan.item.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.Item;
import br.com.eduardo.loan.util.type.ItemTypeImage;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class ItemDialogAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> items;

	public ItemDialogAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.dg_item_list, null);
		}

		Item o = items.get(position);
		if (o != null) {
			TextView tt = (TextView) v.findViewById(R.id.dialog_item_name);
			tt.setText(o.getTitle());
			tt.setCompoundDrawablesWithIntrinsicBounds(ItemTypeImage.typeGridImage(o.getType()), 0, 0, 0);
		}
		return v;
	}
}