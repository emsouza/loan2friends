package br.com.eduardo.loan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.friend.adapter.FriendDialogAdapter;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.entity.Friend;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FriendSearchDialog extends Dialog implements OnItemClickListener {

	protected Friend friend;

	public FriendSearchDialog(Context context) {
		super(context);
		setTitle(R.string.title_dialog_friend);
	}

	public void populate() {
		FriendDAO db = new FriendDAO(getContext());
		FriendDialogAdapter adapter = new FriendDialogAdapter(getContext(), R.layout.dg_friend_list, db.findAll());
		db.close();

		ListView listView = new ListView(getContext());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		setContentView(listView);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		friend = ((Friend) arg0.getItemAtPosition(arg2));
		dismiss();
	}

	public Friend getItemSelected() {
		return friend;
	}
}