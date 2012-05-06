package br.com.eduardo.loan.ac.friend.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.adapter.ContactAdapter;
import br.com.eduardo.loan.entity.Contact;
import br.com.eduardo.loan.util.ContactFinder;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ContactDialog extends Dialog implements OnItemClickListener {

	protected Contact itemSelected;

	protected ListView listView;

	public ContactDialog(Context context) {
		super(context);
		setTitle(R.string.title_dialog_contact);
		listView = new ListView(getContext());
		listView.setDrawSelectorOnTop(true);
		listView.setOnItemClickListener(this);
	}

	public void populate() {
		try {
			ArrayList<Contact> list = ContactFinder.getContacts(getContext());
			ContactAdapter adapter = new ContactAdapter(getContext(), R.layout.dg_contact_list, list);
			listView.setAdapter(adapter);
			setContentView(listView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		itemSelected = (Contact) arg0.getItemAtPosition(arg2);
		dismiss();
	}

	public Contact getItemSelected() {
		return itemSelected;
	}
}
