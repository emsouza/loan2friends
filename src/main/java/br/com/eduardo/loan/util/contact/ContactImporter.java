package br.com.eduardo.loan.util.contact;

import java.util.List;

import android.content.Context;
import br.com.eduardo.loan.model.FriendDAO;
import br.com.eduardo.loan.model.entity.ContactDTO;
import br.com.eduardo.loan.model.entity.FriendDTO;
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
        List<ContactDTO> list = ContactFinder.getContacts(context);
        FriendDAO db = new FriendDAO(context);
        for (ContactDTO c : list) {
            FriendDTO f = db.findByNumber(c.getNumber());
            if (f == null) {
                FriendDTO friend = new FriendDTO();
                friend.setName(c.getName());
                friend.setPhone(c.getNumber());
                friend.setContactId(c.getContactId());
                db.insert(friend);
                db.close();
            }
        }
    }
}
