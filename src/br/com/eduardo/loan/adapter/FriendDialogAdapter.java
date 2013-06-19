package br.com.eduardo.loan.adapter;

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
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.ContactFinder;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendDialogAdapter extends ArrayAdapter<Friend> {

	private ArrayList<Friend> items;

	public FriendDialogAdapter(Context context, int textViewResourceId, ArrayList<Friend> items) {
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
			v = vi.inflate(R.layout.dg_friend_list, null);
		}
		Friend o = items.get(position);
		if (o != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.friendPhoto);
			TextView tt = (TextView) v.findViewById(R.id.dialog_friend_name);
			if (tt != null) {
				tt.setText(o.getName());
			}
			if (iv != null) {
				Bitmap b = ContactFinder.getPhotos(getContext(), o.getContactId());
				if (b != null) {
					iv.setImageBitmap(b);
				} else {
					iv.setImageResource(R.drawable.ic_friend);
				}
			}
		}
		return v;
	}
}