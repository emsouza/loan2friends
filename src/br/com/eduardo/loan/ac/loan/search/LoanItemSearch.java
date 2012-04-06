package br.com.eduardo.loan.ac.loan.search;

import java.util.ArrayList;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import br.com.eduardo.loan.adapter.LoanViewAdapter;
import br.com.eduardo.loan.entity.LoanView;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 07/09/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class LoanItemSearch implements TextWatcher {

	protected Context context;

	protected EditText editText;

	protected ListView listView;

	protected ArrayList<LoanView> loans;

	public LoanItemSearch(Context context, EditText editText, ListView listView) {
		this.context = context;
		this.editText = editText;
		this.listView = listView;
		this.loans = ((LoanViewAdapter) listView.getAdapter()).getItems();
	}

	public void afterTextChanged(Editable s) {}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		int textlength = editText.getText().length();
		if (textlength >= 3) {
			ArrayList<LoanView> loansSort = new ArrayList<LoanView>();
			String text = editText.getText().toString().toUpperCase();
			for (LoanView lv : loans) {
				if (lv.getTitle().toUpperCase().contains(text) || lv.getName().toUpperCase().contains(text)) {
					loansSort.add(lv);
				}
			}
			listView.setAdapter(new LoanViewAdapter(context, loansSort));
		} else {
			listView.setAdapter(new LoanViewAdapter(context, loans));
		}
	}
}