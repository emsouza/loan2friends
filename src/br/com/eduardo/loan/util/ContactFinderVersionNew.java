package br.com.eduardo.loan.util;

import java.io.InputStream;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 30/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ContactFinderVersionNew {

	public static Cursor getCursor(Context context) {
		Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, Phone.DISPLAY_NAME + " ASC");
		return cursor;
	}

	public static Bitmap getPhoto(Context context, long contactId) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
		if (input == null) {
			return null;
		}
		return BitmapFactory.decodeStream(input);
	}
}
