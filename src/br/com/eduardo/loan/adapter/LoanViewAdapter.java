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
import br.com.eduardo.loan.util.type.StatusImage;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class LoanViewAdapter extends ArrayAdapter<LoanViewDTO> {

	private ArrayList<LoanViewDTO> items;

	private DateFormatUtil dateformat;

	public LoanViewAdapter(Context context, DateFormatUtil dateformat, ArrayList<LoanViewDTO> items) {
		super(context, R.layout.ac_loan_list_row, items);
		this.dateformat = dateformat;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.ac_loan_list_row, null);
		}
		LoanViewDTO o = items.get(position);
		if (o != null) {
			TextView title = (TextView) v.findViewById(R.id.loanTitle);
			TextViewGroup lentDate = (TextViewGroup) v.findViewById(R.id.lentDate);
			ImageView contactPhoto = (ImageView) v.findViewById(R.id.friendIcon);
			ImageView status = (ImageView) v.findViewById(R.id.statusIcon);

			title.setText(o.getName());

			Date loanDate = dateformat.formatToDate(o.getLentDate());
			lentDate.setValues(getContext().getString(R.string.date_loan), dateformat.formatDateTimeToScreen(loanDate));

			status.setImageResource(StatusImage.statusImage(o.getStatus()));

			Bitmap b = ContactFinder.getPhotos(getContext(), o.getFriendId());
			if (b != null) {
				contactPhoto.setImageBitmap(b);
			} else {
				contactPhoto.setImageResource(R.drawable.ic_friend);
			}
		}
		return v;
	}

	public ArrayList<LoanViewDTO> getItems() {
		return items;
	}
}