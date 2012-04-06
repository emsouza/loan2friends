package br.com.eduardo.loan.util.contact;

import java.util.List;

import android.content.Context;
import br.com.eduardo.loan.db.manager.FriendDBManager;
import br.com.eduardo.loan.entity.Contact;
import br.com.eduardo.loan.entity.Friend;
import br.com.eduardo.loan.util.ContactFinder;

/**
 * @author Eduardo Matos de Souza <br>
 *         23/12/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ContactImporter {

	/**
	 * Busca todos os contatos da Agenda e salva na base de dados.
	 * 
	 * @param context Contexto de Execução
	 */
	public static void importContact(Context context) {
		List<Contact> list = ContactFinder.getContacts(context);
		FriendDBManager db = new FriendDBManager(context);
		for (Contact c : list) {
			Friend f = db.findByNumber(c.getNumber());
			if (f == null) {
				Friend friend = new Friend();
				friend.setName(c.getName());
				friend.setPhone(c.getNumber());
				friend.setContactId(c.getContactId());
				db.insert(friend);
				db.close();
			}
		}
	}
}
