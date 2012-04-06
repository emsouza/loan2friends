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
import br.com.eduardo.loan.util.type.LoanStatusImage;

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
			TextView date = (TextView) v.findViewById(R.id.ac_loan_list_date);
			ImageView type = (ImageView) v.findViewById(R.id.ac_loan_list_icon);
			ImageView status = (ImageView) v.findViewById(R.id.ic_loan_status_icon);

			title.setText(getContext().getString(R.string.item_title) + " " + o.getTitle());
			name.setText(getContext().getString(R.string.friend_name) + " " + o.getName());
			Date loanDate = DateFormatUtil.formatToDate(o.getDate());
			if (o.getStatus() == 0) {
				date.setText(getContext().getString(R.string.date_return) + ": " + GUI_FORMAT.format(loanDate));
			} else {
				date.setText(getContext().getString(R.string.date_loan) + ": " + GUI_FORMAT.format(loanDate));
			}
			status.setImageResource(LoanStatusImage.statusImage(o.getStatus()));

			type.setImageResource(ItemTypeImage.typeGridImage(o.getType()));
		}
		return v;
	}

	public ArrayList<LoanView> getItems() {
		return items;
	}
}