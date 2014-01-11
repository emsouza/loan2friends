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
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.CurrencySymbol;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.type.Status;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public class ItemAdapter extends ArrayAdapter<ItemDTO> {

    private ArrayList<ItemDTO> items;

    public ItemAdapter(Context context, ArrayList<ItemDTO> items) {
        super(context, R.layout.ac_item_row, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.ac_item_row, null);
        }
        ItemDTO o = items.get(position);
        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.itemTitle);
            TextView bt = (TextView) v.findViewById(R.id.itemStatus);
            ImageView iv = (ImageView) v.findViewById(R.id.itemImage);

            if (o.getType() == 6) {
                tt.setText(CurrencySymbol.format(o.getTitle()));
            } else {
                tt.setText(o.getTitle());
            }

            if (o.getStatus() == Status.LENDED) {
                bt.setBackgroundResource(R.color.red);
            } else {
                bt.setBackgroundResource(R.color.green);
            }

            iv.setImageResource(ItemTypeImage.typeGridImage(o.getType()));
        }
        return v;
    }

    public ArrayList<ItemDTO> getItems() {
        return items;
    }
}