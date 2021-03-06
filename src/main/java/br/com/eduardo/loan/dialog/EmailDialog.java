package br.com.eduardo.loan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.mail.EmailUtil;
import br.com.eduardo.loan.util.validator.MailValidator;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class EmailDialog extends Dialog {

    protected ItemDTO item;

    protected EditText title;

    protected EditText message;

    public EmailDialog(Context context) {
        super(context);
        setTitle(R.string.title_feature);
        setContentView(R.layout.dg_email);
        getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        title = (EditText) this.findViewById(R.id.mailTitle);
        message = (EditText) this.findViewById(R.id.mailDescription);

        Button send = (Button) findViewById(R.id.dg_mail_send);
        send.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        Button cancel = (Button) findViewById(R.id.dg_mail_cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected void sendMail() {
        final String sTitle = getContext().getString(R.string.title_feature) + " - " + title.getText().toString();
        final String sMessage = message.getText().toString();

        if (MailValidator.validaMessage(getContext(), sTitle, sMessage)) {
            EmailUtil.sendMail(getContext(), sTitle, sMessage);
            dismiss();
        }
    }
}