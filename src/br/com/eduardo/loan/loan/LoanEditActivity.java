package br.com.eduardo.loan.loan;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.db.manager.ItemDBManager;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.util.DateFormatUtil;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class LoanEditActivity extends FragmentActivity {

	private Loan loan;

	private Item item;

	private Friend friend;

	protected Spinner status;

	protected DatePicker date;

	protected TimePicker time;

	private ArrayAdapter<String> statusAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_loan_edit);

		statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		status = (Spinner) this.findViewById(R.id.ac_loan_status);
		date = (DatePicker) this.findViewById(R.id.ac_loan_add_date);
		time = (TimePicker) this.findViewById(R.id.ac_loan_add_time);

		statusAdapter.add(getString(R.string.status_available));
		statusAdapter.add(getString(R.string.status_lent));

		status.setAdapter(statusAdapter);
		time.setIs24HourView(true);

		Bundle bun = getIntent().getExtras();
		if (null == bun) {
			return;
		}

		Integer id = bun.getInt("id");

		LoanDBManager dbLoan = new LoanDBManager(this);
		loan = dbLoan.find(id);
		dbLoan.close();

		ItemDBManager dbItem = new ItemDBManager(this);
		item = dbItem.find(loan.getIdItem());
		dbItem.close();

		FriendDBManager dbFriend = new FriendDBManager(this);
		friend = dbFriend.find(loan.getIdFriend());
		dbFriend.close();

		EditText friendName = (EditText) this.findViewById(R.id.ac_loan_frient_name);
		EditText itemName = (EditText) this.findViewById(R.id.ac_loan_item_name);

		friendName.setText(friend.getName());
		itemName.setText(item.getTitle());

		status.setSelection(loan.getStatus());

		Calendar cal = Calendar.getInstance();
		cal.setTime(DateFormatUtil.formatToDate(loan.getLentDate()));

		date.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

		time.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		time.setCurrentMinute(cal.get(Calendar.MINUTE));

		Button save = (Button) findViewById(R.id.ac_loan_edit_save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				update();
				finish();
			}
		});

		Button cancel = (Button) findViewById(R.id.ac_loan_edit_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void update() {
		Integer statusNumber = new Long(status.getSelectedItemId()).intValue();
		loan.setStatus(statusNumber);
		LoanDBManager db = new LoanDBManager(this);
		db.update(loan);
		db.close();
	}
}