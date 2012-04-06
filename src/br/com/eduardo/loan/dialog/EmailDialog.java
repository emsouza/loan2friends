package br.com.eduardo.loan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.entity.Item;
import br.com.eduardo.loan.util.mail.EmailUtil;
import br.com.eduardo.loan.util.validator.MailValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class EmailDialog extends Dialog {

	protected Item item;

	protected EditText title;

	protected EditText message;

	public EmailDialog(Context context) {
		super(context);
		setTitle(R.string.feature_request);
		setContentView(R.layout.dg_email);
		getWindow().setLayout(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		title = (EditText) this.findViewById(R.id.dg_mail_title);
		message = (EditText) this.findViewById(R.id.dg_mail_description);

		Button send = (Button) findViewById(R.id.dg_mail_send);
		send.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				sendMail();
			}
		});

		Button cancel = (Button) findViewById(R.id.dg_mail_cancel);
		cancel.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	protected void sendMail() {
		final String sTitle = getContext().getString(R.string.feature_request) + " - " + title.getText().toString();
		final String sMessage = message.getText().toString();

		if (MailValidator.validaMessage(getContext(), sTitle, sMessage)) {
			EmailUtil.sendMail(getContext(), sTitle, sMessage);
			dismiss();
		}
	}
}