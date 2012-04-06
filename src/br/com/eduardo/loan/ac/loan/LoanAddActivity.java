package br.com.eduardo.loan.ac.loan;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.ac.loan.dialog.FriendSearchDialog;
import br.com.eduardo.loan.ac.loan.dialog.ItemSearchDialog;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.validator.LoanValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class LoanAddActivity extends Activity {

	static final int DIALOG_FRIEND_ID = 0;

	static final int DIALOG_ITEM_ID = 1;

	protected Item item;

	protected Friend friend;

	protected EditText itemName;

	protected EditText friendName;

	protected DatePicker date;

	protected TimePicker time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_loan_add);

		itemName = (EditText) this.findViewById(R.id.ac_loan_item_name);
		friendName = (EditText) this.findViewById(R.id.ac_loan_frient_name);
		date = (DatePicker) this.findViewById(R.id.ac_loan_add_date);
		time = (TimePicker) this.findViewById(R.id.ac_loan_add_time);

		time.setIs24HourView(true);

		ImageButton searchFriend = (ImageButton) findViewById(R.id.ac_loan_friend_search);
		searchFriend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_FRIEND_ID);
			}
		});

		ImageButton searchItem = (ImageButton) findViewById(R.id.ac_loan_item_search);
		searchItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_ITEM_ID);
			}
		});

		Button save = (Button) findViewById(R.id.ac_loan_add_save);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveLoan();
			}
		});

		Button cancel = (Button) findViewById(R.id.ac_loan_add_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_FRIEND_ID:
				final FriendSearchDialog dialogFriend = new FriendSearchDialog(this);
				dialogFriend.populate();
				dialogFriend.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface arg0) {
						friend = dialogFriend.getItemSelected();
						if (friend != null) {
							friendName.setText(friend.getName());
						}
					}
				});
				return dialogFriend;
			case DIALOG_ITEM_ID:
				final ItemSearchDialog dialogItem = new ItemSearchDialog(this);
				dialogItem.populate();
				dialogItem.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface arg0) {
						item = dialogItem.getItemSelected();
						if (item != null) {
							itemName.setText(item.getTitle());
						}
					}
				});
				return dialogItem;
			default:
				return super.onCreateDialog(id);
		}
	}

	protected void saveLoan() {
		Loan loan = new Loan();
		loan.setIdFriend(friend != null ? friend.getId() : null);
		loan.setIdItem(item != null ? item.getId() : null);
		loan.setStatus(Status.LENDED.id());

		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
		cal.set(Calendar.MONTH, date.getMonth());
		cal.set(Calendar.YEAR, date.getYear());
		cal.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
		cal.set(Calendar.MINUTE, time.getCurrentMinute());

		loan.setDate(DateFormatUtil.formatToDB(cal.getTime()));

		if (LoanValidator.validaLoan(this, loan)) {
			LoanDBManager db = new LoanDBManager(this);
			db.insert(loan);
			db.close();

			finish();
		}
	}
}