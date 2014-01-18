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
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.util.ContactFinder;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public class FriendAdapter extends ArrayAdapter<FriendDTO> {

    private ArrayList<FriendDTO> items;

    public FriendAdapter(Context context, ArrayList<FriendDTO> items) {
        super(context, R.layout.ac_friend_row, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.ac_friend_row, null);
        }
        FriendDTO o = items.get(position);
        if (o != null) {
            ImageView img = (ImageView) v.findViewById(R.id.friendPhoto);
            TextView tt = (TextView) v.findViewById(R.id.friendName);
            TextView bt = (TextView) v.findViewById(R.id.friendNumber);
            tt.setText(o.getName());

            if (o.getPhone() != null && !o.getPhone().trim().isEmpty()) {
                bt.setText(String.format(getContext().getString(R.string.label_telephone_number), o.getPhone()));
            } else {
                bt.setVisibility(View.GONE);
            }

            Bitmap b = ContactFinder.getPhotos(getContext(), o.getContactId());
            if (b != null) {
                img.setImageBitmap(b);
            } else {
                img.setImageResource(R.drawable.grid_friend);
            }
        }
        return v;
    }

    public ArrayList<FriendDTO> getItems() {
        return items;
    }
}