package br.com.eduardo.loan.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.LoanViewDTO;
import br.com.eduardo.loan.ui.view.TextViewGroup;
import br.com.eduardo.loan.util.ContactFinder;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.ItemFormatter;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.type.StatusColor;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public class LoanViewAdapter extends ArrayAdapter<LoanViewDTO> {

    private ArrayList<LoanViewDTO> items;

    private DateFormatUtil dateformat;

    public LoanViewAdapter(Context context, DateFormatUtil dateformat, ArrayList<LoanViewDTO> items) {
        super(context, R.layout.ac_main_row, items);
        this.dateformat = dateformat;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.ac_main_row, null);
        }
        LoanViewDTO o = items.get(position);
        if (o != null) {
            TextView title = (TextView) v.findViewById(R.id.loanTitle);
            TextViewGroup lentDate = (TextViewGroup) v.findViewById(R.id.lentDate);
            ImageView contactPhoto = (ImageView) v.findViewById(R.id.friendIcon);
            TextView status = (TextView) v.findViewById(R.id.loanStatus);

            title.setText(ItemFormatter.formatItemName(o.getType(), o.getName()));

            Date loanDate = dateformat.formatToDate(o.getLentDate());

            if (o.getStatus() == Status.LENDED) {
                lentDate.setValues(getContext().getString(R.string.label_date_loan), dateformat.formatDateToScreen(loanDate), true);
            } else {
                lentDate.setValues(getContext().getString(R.string.label_date_return), dateformat.formatDateToScreen(loanDate), true);
            }

            status.setBackgroundResource(StatusColor.statusImage(o.getStatus()));

            Bitmap b = ContactFinder.getPhotos(getContext(), o.getFriendId());
            if (b != null) {
                contactPhoto.setImageBitmap(b);
            } else {
                contactPhoto.setImageResource(R.drawable.grid_friend);
            }
        }
        return v;
    }

    public ArrayList<LoanViewDTO> getItems() {
        return items;
    }
}