package br.com.eduardo.loan.sms.util;

import java.util.ArrayList;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 27/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class SMSUtil {

	public static void sendSMS(String phoneNumber, String message) {
		try {
			SmsManager sms = SmsManager.getDefault();
			ArrayList<String> smstext = sms.divideMessage(message);
			sms.sendMultipartTextMessage(phoneNumber, null, smstext, null, null);
		} catch (Exception e) {
			Log.e(SMSUtil.class.getName(), "Erro ao enviar SMS.", e);
		}
	}
}
