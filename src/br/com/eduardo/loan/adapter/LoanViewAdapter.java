package br.com.eduardo.loan.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.entity.LoanView;
import br.com.eduardo.loan.util.ConfigPreferences;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.ItemTypeImage;
import br.com.eduardo.loan.util.type.Status;
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

			GUI_FORMAT = new SimpleDateFormat(format);
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
			TextView title = (TextView) v.findViewById(R.id.ac_loan_list_title);
			TextView name = (TextView) v.findViewById(R.id.ac_loan_list_name);
			TextView lentDate = (TextView) v.findViewById(R.id.ac_loan_list_lent_date);
			TextView returnDate = (TextView) v.findViewById(R.id.ac_loan_list_return_date);
			ImageView type = (ImageView) v.findViewById(R.id.ac_loan_list_icon);
			ImageView status = (ImageView) v.findViewById(R.id.ic_loan_status_icon);

			title.setText(getContext().getString(R.string.item_title) + " " + o.getTitle());
			name.setText(getContext().getString(R.string.friend_name) + " " + o.getName());

			Date loanDate = DateFormatUtil.formatToDate(o.getLentDate());
			lentDate.setText(getContext().getString(R.string.date_loan) + " " + GUI_FORMAT.format(loanDate));

			if (o.getStatus() == Status.LENDED.id()) {
				long milis1 = loanDate.getTime();
				long milis2 = new Date().getTime();
				long diff = milis2 - milis1;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				returnDate.setText(getContext().getString(R.string.loan_days) + " " + diffDays + " " + getContext().getString(R.string.days));
			} else {
				Date rDate = DateFormatUtil.formatToDate(o.getReturnDate());
				returnDate.setText(getContext().getString(R.string.date_return) + " " + GUI_FORMAT.format(rDate));
			}
			status.setImageResource(StatusImage.statusImage(o.getStatus()));
			type.setImageResource(ItemTypeImage.typeGridImage(o.getType()));
		}
		return v;
	}

	public ArrayList<LoanView> getItems() {
		return items;
	}
}