package br.com.eduardo.loan.sms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 27/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class SMSUtil {

	public static void sendSMS(Context context, String phoneNumber, String message) {
		try {
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
			intent.putExtra("sms_body", message);
			context.startActivity(intent);
		} catch (Exception e) {
			Log.e(SMSUtil.class.getName(), "Erro ao enviar SMS.", e);
		}
	}
}
