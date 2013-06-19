package br.com.eduardo.loan.item.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.Item;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.type.Status;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class ItemAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> items;

	public ItemAdapter(Context context, ArrayList<Item> items) {
		super(context, R.layout.ac_item_list_row, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.ac_item_list_row, null);
		}
		Item o = items.get(position);
		if (o != null) {
			TextView tt = (TextView) v.findViewById(R.id.ac_item_list_title);
			TextView bt = (TextView) v.findViewById(R.id.ac_item_list_status);
			ImageView iv = (ImageView) v.findViewById(R.id.ac_item_list_image_type);
			if (tt != null) {
				tt.setText(getContext().getString(R.string.item_title) + " " + o.getTitle());
			}
			if (bt != null) {
				String status = "";
				if (o.getStatus() == Status.AVAILABLE.id()) {
					status = getContext().getString(R.string.status_available);
				} else if (o.getStatus() == Status.LENDED.id()) {
					status = getContext().getString(R.string.status_lent);
				}
				bt.setText(getContext().getString(R.string.status) + " " + status);
			}
			if (iv != null) {
				iv.setImageResource(ItemTypeImage.typeGridImage(o.getType()));
			}
		}
		return v;
	}

	public ArrayList<Item> getItems() {
		return items;
	}
}