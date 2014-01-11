package br.com.eduardo.loan.util.mail;

import android.content.Context;
import android.content.Intent;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 13/08/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class EmailUtil {

    public static void sendMail(Context context, String title, String message) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "loantofriends@emsouza.com.br" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.title_feature)));
    }

}