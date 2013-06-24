package br.com.eduardo.loan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.eduardo.loan.action.HomeAction;
import br.com.eduardo.loan.friend.dialog.FriendSearchDialog;
import br.com.eduardo.loan.item.dialog.ItemSearchDialog;
import br.com.eduardo.loan.model.entity.FriendDTO;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.util.type.Status;
import br.com.emsouza.widget.bar.ActionBar;
import br.com.emsouza.widget.dateslider.DateSlider;
import br.com.emsouza.widget.dateslider.DefaultDateSlider;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EActivity(R.layout.ac_loan_add)
public class LoanAddActivity extends FragmentActivity {

	static final int DIALOG_FRIEND_ID = 0;

	static final int DIALOG_ITEM_ID = 1;

	@ViewById(R.id.actionBar)
	protected ActionBar actionBar;

	@ViewById(R.id.itemNameText)
	protected EditText itemName;

	@ViewById(R.id.friendNameText)
	protected EditText friendName;

	@ViewById(R.id.dateText)
	protected EditText date;

	protected ItemDTO item;

	protected FriendDTO friend;

	@AfterViews
	void afterView() {
		actionBar.setHomeAction(new HomeAction(this));
		actionBar.setDisplayHomeAsUpEnabled(true);

		ImageButton searchFriend = (ImageButton) findViewById(R.id.friendSearch);
		searchFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_FRIEND_ID);
			}
		});

		friendName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_FRIEND_ID);
			}
		});

		ImageButton searchItem = (ImageButton) findViewById(R.id.itemSearch);
		searchItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ITEM_ID);
			}
		});

		itemName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_ITEM_ID);
			}
		});

		Button save = (Button) findViewById(R.id.saveLoan);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveLoan();
			}
		});

		Button cancel = (Button) findViewById(R.id.cancelLoan);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Click(R.id.dateText)
	protected void dateClick() {
		DateSlider.OnDateSetListener dateListener = new DateSlider.OnDateSetListener() {
			@Override
			public void onDateSet(DateSlider view, Calendar selectedDate) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
				date.setText(format.format(selectedDate.getTime()));
			}
		};

		DefaultDateSlider slider = new DefaultDateSlider(this, getString(R.string.date_loan), dateListener, Calendar.getInstance());
		slider.show();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_FRIEND_ID:
				final FriendSearchDialog dialogFriend = new FriendSearchDialog(this);
				dialogFriend.populate();
				dialogFriend.setOnDismissListener(new OnDismissListener() {
					@Override
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
					@Override
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
		LoanDTO loan = new LoanDTO();
		loan.setIdFriend(friend != null ? friend.getId() : null);
		loan.setIdItem(item != null ? item.getId() : null);
		loan.setStatus(Status.LENDED.id());

		// Calendar cal = new GregorianCalendar();
		// cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
		// cal.set(Calendar.MONTH, date.getMonth());
		// cal.set(Calendar.YEAR, date.getYear());
		// cal.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
		// cal.set(Calendar.MINUTE, time.getCurrentMinute());
		//
		// loan.setLentDate(DateFormatUtil.formatToDB(cal.getTime()));
		//
		// if (LoanValidator.validaLoan(this, loan)) {
		// LoanDAO db = new LoanDAO(this);
		// db.insert(loan);
		// db.close();
		//
		// finish();
		// }
	}
}