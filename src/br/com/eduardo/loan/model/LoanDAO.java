package br.com.eduardo.loan.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.eduardo.loan.model.entity.LoanDTO;
import br.com.eduardo.loan.model.entity.LoanViewDTO;
import br.com.eduardo.loan.util.type.Status;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
@EBean(scope = Scope.Singleton)
public class LoanDAO extends AbstractDAO {

    private static final String TABLE_NAME = "LOAN_HISTORY";

    private static final String TABLE_VIEW = "LOAN_VIEW";

    private static final String ID_COLUMN = "_id";

    private static final String ID_FRIEND = "ID_FRIEND";

    private static final String ID_ITEM = "ID_ITEM";

    private static final String DATE_LENT_COLUMN = "DT_LENT";

    private static final String DATE_RETURN_COLUMN = "DT_RETURN";

    private static final String STATUS_COLUMN = "FG_STATUS";

    public LoanDAO(Context context) {
        super(context);
    }

    public void insert(LoanDTO item) {
        ContentValues value = new ContentValues();
        value.put(ID_FRIEND, Integer.valueOf(item.getIdFriend()));
        value.put(ID_ITEM, Integer.valueOf(item.getIdItem()));
        value.put(STATUS_COLUMN, item.getStatus());
        value.put(DATE_LENT_COLUMN, item.getLentDate());
        value.put(DATE_RETURN_COLUMN, item.getReturnDate());

        getWritableDatabase().insert(TABLE_NAME, null, value);
    }

    public void update(LoanDTO item) {
        String where = ID_COLUMN + " = " + item.getId();

        ContentValues value = new ContentValues();
        value.put(ID_FRIEND, Integer.valueOf(item.getIdFriend()));
        value.put(ID_ITEM, Integer.valueOf(item.getIdItem()));
        value.put(STATUS_COLUMN, item.getStatus());
        value.put(DATE_LENT_COLUMN, item.getLentDate());
        value.put(DATE_RETURN_COLUMN, item.getReturnDate());

        getWritableDatabase().update(TABLE_NAME, value, where, null);
    }

    public void archive(Integer id) {
        ContentValues value = new ContentValues();
        value.put(STATUS_COLUMN, Status.ARCHIVED.id());
        getWritableDatabase().update(TABLE_NAME, value, ID_COLUMN + "=?", new String[] { String.valueOf(id) });
    }

    public void delete(Integer id) {
        String where = ID_COLUMN + " = " + id;
        getWritableDatabase().delete(TABLE_NAME, where, null);
    }

    public void deleteItem(Integer id) {
        String where = ID_ITEM + " = " + id;

        getWritableDatabase().delete(TABLE_NAME, where, null);
    }

    public void deleteFriend(Integer id) {
        String where = ID_FRIEND + " = " + id;

        getWritableDatabase().delete(TABLE_NAME, where, null);
    }

    public Integer countItem(Integer id) {
        String where = ID_ITEM + " = " + id;
        Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
        if (cursor != null) {
            Integer count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public Integer countFriend(Integer id) {
        String where = ID_FRIEND + " = " + id;
        Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
        if (cursor != null) {
            Integer count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    public LoanDTO find(Integer id) {
        LoanDTO item = new LoanDTO();
        String where = ID_COLUMN + " = " + id;
        Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            item.setId(cursor.getInt(0));
            item.setIdFriend(cursor.getInt(1));
            item.setIdItem(cursor.getInt(2));
            item.setLentDate(cursor.getString(3));
            item.setStatus(cursor.getInt(4));
            item.setReturnDate(cursor.getString(5));
            cursor.close();
            return item;
        }
        return null;
    }

    public LoanViewDTO findView(Integer id) {
        String orderBY = "DT_LENT DESC";
        String where = ID_COLUMN + " = " + id;
        Cursor cursor = getReadableDatabase().query(TABLE_VIEW, null, where, null, null, null, orderBY);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LoanViewDTO item = new LoanViewDTO();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setStatus(cursor.getInt(2));
                item.setLentDate(cursor.getString(3));
                item.setFriendId(cursor.getInt(4));
                item.setReturnDate(cursor.getString(5));
                cursor.close();
                return item;
            }
            cursor.close();
        }
        return null;
    }

    public ArrayList<LoanViewDTO> findAll(List<String> status) {
        String orderBY = "DT_LENT DESC, FG_STATUS ASC";

        status = resetParams(status);

        String where = STATUS_COLUMN + " IN (" + makePlaceHolders(status.size()) + ")";

        ArrayList<LoanViewDTO> list = new ArrayList<LoanViewDTO>();
        Cursor cursor = getReadableDatabase().query(TABLE_VIEW, null, where, resetParams(status).toArray(new String[status.size()]), null, null,
                orderBY);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LoanViewDTO item = new LoanViewDTO();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setStatus(cursor.getInt(2));
                item.setLentDate(cursor.getString(3));
                item.setFriendId(cursor.getInt(4));
                item.setReturnDate(cursor.getString(5));
                list.add(item);
            }
            cursor.close();
        }
        return list;
    }

    List<String> resetParams(List<String> status) {
        if (status.size() <= 0) {
            status.add(String.valueOf(Status.LENDED.id()));
            status.add(String.valueOf(Status.RETURNED.id()));
        }
        return status;
    }

    String makePlaceHolders(int len) {
        StringBuilder sb = new StringBuilder(len * 2 - 1);
        sb.append("?");
        for (int i = 1; i < len; i++) {
            sb.append(",?");
        }
        return sb.toString();
    }
}