package br.com.eduardo.loan.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import br.com.eduardo.loan.model.entity.ContactDTO;

/**
 * @author Eduardo Matos de Souza<br>
 *         06/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ContactFinder {

	/**
	 * Busca os contatos do telefone e converte para uma lista.
	 * 
	 * @param context Contexto em que a classe foi invocada.
	 * @return Lista de <ContactItem> com as informações��es dos contatos.
	 */
	public static ArrayList<ContactDTO> getContacts(Context context) {
		ArrayList<ContactDTO> list = new ArrayList<ContactDTO>();

		Cursor cursor = null;

		if (AndroidVersion.getVersion().equalsIgnoreCase("4")) {
			cursor = ContactFinderVersion4.getCursor(context);
		} else {
			cursor = ContactFinderVersionNew.getCursor(context);
		}

		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ContactDTO item = new ContactDTO();
				item.setContactId(cursor.getLong(0));
				item.setName(cursor.getString(1));
				item.setNumber(cursor.getString(2));
				list.add(item);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * Busca as fotos dos contatos.
	 * 
	 * @param context Contexto em que a classe foi invocada.
	 * @return Lista de <ContactItem> com as informações��es dos contatos.
	 */
	public static Bitmap getPhotos(Context context, long contactId) {
		return ContactFinderVersionNew.getPhoto(context, contactId);
	}
}
