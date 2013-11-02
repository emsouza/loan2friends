package br.com.eduardo.loan.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts.People;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza <br>
 *         EMS - 30/07/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@SuppressWarnings({ "deprecation", "static-access" })
public class ContactFinderVersion4 {

    public static Cursor getCursor(Context context) {
        String[] projection = new String[] { People.NAME, People.NUMBER };
        Uri contacts = People.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(contacts, projection, null, null, People.NAME + " ASC");
        return cursor;
    }

    public static Bitmap getPhoto(Context context, long contactId) {
        Uri uri = ContentUris.withAppendedId(People.CONTENT_URI, contactId);
        Bitmap bitmap = People.loadContactPhoto(context, uri, R.drawable.ic_friend, null);
        return bitmap;
    }
}