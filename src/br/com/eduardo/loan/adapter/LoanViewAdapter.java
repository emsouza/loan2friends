package br.com.eduardo.loan.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.group.TextViewGroup;
import br.com.eduardo.loan.model.entity.LoanView;
import br.com.eduardo.loan.util.ConfigPreferences;
import br.com.eduardo.loan.util.ContactFinder;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.StatusImage;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class LoanViewAdapter extends ArrayAdapter<LoanView> {

	private SimpleDateFormat GUI_FORMAT;

	private ArrayList<LoanView> items;

	public LoanViewAdapter(Context context, ArrayList<LoanView> items) {
		super(context, R.layout.ac_loan_list_row, items);
		this.items = items;

		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
			String format = prefs.getString(ConfigPreferences.DATE_FORMAT, "MM/dd/yyyy");
			format += " ";
			format += prefs.getString(ConfigPreferences.TIME_FORMAT, "HH:mm");

			GUI_FORMAT = new SimpleDateFormat(format, Locale.getDefault());
		} catch (Exception e) {
			Log.e(LoanViewAdapter.class.getName(), "Erro ao buscar o formato de data");
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.ac_loan_list_row, null);
		}
		LoanView o = items.get(position);
		if (o != null) {
			TextView title = (TextView) v.findViewById(R.id.loanTitle);
			TextViewGroup lentDate = (TextViewGroup) v.findViewById(R.id.lentDate);
			ImageView contactPhoto = (ImageView) v.findViewById(R.id.friendIcon);
			ImageView status = (ImageView) v.findViewById(R.id.statusIcon);

			title.setText(o.getName());

			Date loanDate = DateFormatUtil.formatToDate(o.getLentDate());
			lentDate.setValues(getContext().getString(R.string.date_loan), GUI_FORMAT.format(loanDate));

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

	public ArrayList<LoanView> getItems() {
		return items;
	}
}