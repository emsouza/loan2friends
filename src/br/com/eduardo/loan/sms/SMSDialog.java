package br.com.eduardo.loan.sms;

import java.text.SimpleDateFormat;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.db.manager.LoanDBManager;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.entity.LoanView;
import br.com.eduardo.loan.sms.util.SMSUtil;
import br.com.eduardo.loan.util.ConfigPreferences;
import br.com.eduardo.loan.util.DateFormatUtil;
import br.com.eduardo.loan.util.validator.SMSValidator;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 27/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class SMSDialog extends Dialog {

	protected EditText phoneNumber;

	protected EditText message;

	protected LoanView view;

	private SimpleDateFormat GUI_FORMAT;

	public SMSDialog(Context context, LoanView view) {
		super(context);
		this.view = view;

		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
			String format = prefs.getString(ConfigPreferences.DATE_FORMAT, "MM/dd/yyyy");
			format += " ";
			format += prefs.getString(ConfigPreferences.TIME_FORMAT, "HH:mm");

			GUI_FORMAT = new SimpleDateFormat(format);
		} catch (Exception e) {
			Log.e(SMSDialog.class.getName(), "Erro ao buscar o formato de data");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dg_sms);
		setTitle(R.string.title_sms);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		phoneNumber = (EditText) this.findViewById(R.id.dg_sms_phone_number);
		message = (EditText) this.findViewById(R.id.dg_sms_message);

		populate();

		Button send = (Button) findViewById(R.id.dg_sms_send);
		send.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				if (SMSValidator.validaMessage(getContext(), phoneNumber.getText().toString(), message.getText().toString())) {
					SMSUtil.sendSMS(phoneNumber.getText().toString(), message.getText().toString());
					dismiss();
				}
			}
		});

		Button cancel = (Button) findViewById(R.id.dg_sms_cancel);
		cancel.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void populate() {
		LoanDBManager dbLoan = new LoanDBManager(getContext());
		FriendDBManager dbFriend = new FriendDBManager(getContext());
		Loan loan = dbLoan.find(view.getId());
		Friend friend = dbFriend.find(loan.getIdFriend());

		dbLoan.close();
		dbFriend.close();

		phoneNumber.setText(friend.getPhone());

		String text = getContext().getString(R.string.sms_return) + "\n\n";
		text = text + getContext().getString(R.string.item_title) + " " + view.getTitle() + "\n";
		text = text + getContext().getString(R.string.date_loan) + " " + GUI_FORMAT.format(DateFormatUtil.formatToDate(view.getDate())) + "\n";

		message.setText(text);
	}
}
