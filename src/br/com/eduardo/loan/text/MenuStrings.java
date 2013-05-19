package br.com.eduardo.loan.text;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza <br>
 *         06/04/2012 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class MenuStrings {

	public static CharSequence[] getLentMenuStrings(Context context) {
		List<CharSequence> list = new ArrayList<CharSequence>();
		list.add(context.getString(R.string.option_mark_returned));
		list.add(context.getString(R.string.option_delete));

		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
			// THIS PHONE HAS SMS FUNCTIONALITY
			list.add(context.getString(R.string.option_sms));
		}
		list.add(context.getString(R.string.option_cancel));

		return list.toArray(new CharSequence[list.size()]);
	}

	public static CharSequence[] getReturnedMenuStrings(Context context) {
		List<CharSequence> list = new ArrayList<CharSequence>();
		list.add(context.getString(R.string.option_mark_archive));
		list.add(context.getString(R.string.option_delete));
		list.add(context.getString(R.string.option_cancel));
		return list.toArray(new CharSequence[list.size()]);
	}

	public static CharSequence[] getArchivedMenuStrings(Context context) {
		List<CharSequence> list = new ArrayList<CharSequence>();
		list.add(context.getString(R.string.option_delete));
		list.add(context.getString(R.string.option_cancel));
		return list.toArray(new CharSequence[list.size()]);
	}
}
