package br.com.eduardo.loan.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.eduardo.loan.model.entity.FriendDTO;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

/**
 * @author Eduardo Matos de Souza<br>
 *         30/04/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EBean(scope = Scope.Singleton)
public class FriendDAO extends AbstractDAO {

    private static final String TABLE_NAME = "FRIEND";

    public static final String ID_COLUMN = "_id";

    public static final String NAME_COLUMN = "NA_NAME";

    public static final String PHONE_COLUMN = "NA_NUMBER";

    public static final String CONTACT_ID = "CONTACT_ID";

    public FriendDAO(Context context) {
        super(context);
    }

    public void insert(FriendDTO item) {
        ContentValues value = new ContentValues();
        value.put(NAME_COLUMN, item.getName());
        value.put(PHONE_COLUMN, item.getPhone());
        value.put(CONTACT_ID, item.getContactId());

        try {
            getWritableDatabase().insert(TABLE_NAME, null, value);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Erro ao inserir um amigo.", e);
        }
    }

    public void update(FriendDTO item) {
        String where = ID_COLUMN + " = " + item.getId();

        ContentValues value = new ContentValues();
        value.put(NAME_COLUMN, item.getName());
        value.put(PHONE_COLUMN, item.getPhone());

        try {
            getWritableDatabase().update(TABLE_NAME, value, where, null);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Erro ao atualizar um amigo.", e);
        }
    }

    public void delete(Integer id) {
        String where = ID_COLUMN + " = " + id;
        try {
            getWritableDatabase().delete(TABLE_NAME, where, null);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Erro ao deletar um amigo.", e);
        }
    }

    public FriendDTO find(Integer id) {
        FriendDTO item = new FriendDTO();
        String where = ID_COLUMN + " = " + id;
        Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setPhone(cursor.getString(2));
            item.setContactId(cursor.getLong(3));
            cursor.close();
            return item;
        }
        return null;
    }

    public FriendDTO findByNumber(String number) {
        FriendDTO item = null;
        String where = PHONE_COLUMN + " = '" + number + "'";
        Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                item = new FriendDTO();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setPhone(cursor.getString(2));
                item.setContactId(cursor.getLong(3));
            }
            cursor.close();
            getReadableDatabase().close();
            return (item != null ? item : null);
        }
        return null;
    }

    public ArrayList<FriendDTO> findAll() {
        ArrayList<FriendDTO> list = new ArrayList<FriendDTO>();
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                FriendDTO item = new FriendDTO();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setPhone(cursor.getString(2));
                item.setContactId(cursor.getLong(3));
                list.add(item);
            }
            cursor.close();
            getReadableDatabase().close();
        }
        return list;
    }
}