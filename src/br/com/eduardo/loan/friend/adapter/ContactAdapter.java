package br.com.eduardo.loan.friend.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.ContactDTO;
import br.com.eduardo.loan.util.ContactFinder;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ContactAdapter extends ArrayAdapter<ContactDTO> {

	private ArrayList<ContactDTO> items;

	public ContactAdapter(Context context, int textViewResourceId, ArrayList<ContactDTO> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.dg_contact_list, null);
		}
		ContactDTO o = items.get(position);
		if (o != null) {
			ImageView img = (ImageView) v.findViewById(R.id.friendPhoto);
			TextView tt = (TextView) v.findViewById(R.id.dialog_contact_name);
			TextView bt = (TextView) v.findViewById(R.id.dialog_contact_number);
			if (tt != null) {
				tt.setText(o.getName());
			}
			if (bt != null) {
				bt.setText(o.getNumber() != null ? o.getNumber() : "");
			}
			Bitmap b = ContactFinder.getPhotos(getContext(), o.getContactId());
			if (b != null) {
				img.setImageBitmap(b);
			} else {
				img.setImageResource(R.drawable.ic_friend);
			}
		}
		return v;
	}
}