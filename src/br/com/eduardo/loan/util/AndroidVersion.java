package br.com.eduardo.loan.util;

import android.os.Build;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 30/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class AndroidVersion {

	public static String getVersion() {
		return Build.VERSION.SDK;
	}
}
