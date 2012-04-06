package br.com.eduardo.loan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class DateFormatUtil {

	private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd HHmm");

	public static Date formatToDate(String date) {
		try {
			return DB_FORMAT.parse(date);
		} catch (Exception e) {
			Log.e(DateFormatUtil.class.getName(), "Erro ao formatar data.");
			return new Date();
		}
	}

	public static String formatToDB(Date date) {
		return DB_FORMAT.format(date);
	}
}
