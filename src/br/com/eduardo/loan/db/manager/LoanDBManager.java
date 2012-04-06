package br.com.eduardo.loan.db.manager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.eduardo.loan.db.DBManager;
import br.com.eduardo.loan.entity.Loan;
import br.com.eduardo.loan.entity.LoanView;

/**
 * @author Eduardo Matos de Souza<br>
 *         07/05/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com </a>
 */
public class LoanDBManager extends DBManager {

	private static final String TABLE_NAME = "LOAN_HISTORY";

	private static final String TABLE_VIEW = "LOAN_VIEW";

	private static final String ID_COLUMN = "_id";

	private static final String ID_FRIEND = "ID_FRIEND";

	private static final String ID_ITEM = "ID_ITEM";

	private static final String DATE_COLUMN = "DT_DATE";

	private static final String STATUS_COLUMN = "FG_STATUS";

	public LoanDBManager(Context context) {
		super(context);
	}

	public void insert(Loan item) {
		ContentValues value = new ContentValues();
		value.put(ID_FRIEND, Integer.valueOf(item.getIdFriend()));
		value.put(ID_ITEM, Integer.valueOf(item.getIdItem()));
		value.put(STATUS_COLUMN, item.getStatus());
		value.put(DATE_COLUMN, item.getDate());

		try {
			getWritableDatabase().insert(TABLE_NAME, null, value);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao inserir um empréstimo.", e);
		}
	}

	public void update(Loan item) {
		String where = ID_COLUMN + " = " + item.getId();

		ContentValues value = new ContentValues();
		value.put(ID_FRIEND, Integer.valueOf(item.getIdFriend()));
		value.put(ID_ITEM, Integer.valueOf(item.getIdItem()));
		value.put(STATUS_COLUMN, item.getStatus());
		value.put(DATE_COLUMN, item.getDate());

		try {
			getWritableDatabase().update(TABLE_NAME, value, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao atualizar um registro de empréstimo.", e);
		}
	}

	public void delete(Integer id) {
		String where = ID_COLUMN + " = " + id;

		try {
			getWritableDatabase().delete(TABLE_NAME, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao deletar um registro de empréstimo.", e);
		}
	}

	public void deleteItem(Integer id) {
		String where = ID_ITEM + " = " + id;

		try {
			getWritableDatabase().delete(TABLE_NAME, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao deletar em préstimo pelo item.", e);
		}
	}

	public void deleteFriend(Integer id) {
		String where = ID_FRIEND + " = " + id;

		try {
			getWritableDatabase().delete(TABLE_NAME, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao deletar um empréstimo pelo amigo.", e);
		}
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

	public Loan find(Integer id) {
		Loan item = new Loan();
		String where = ID_COLUMN + " = " + id;
		Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			item.setId(cursor.getInt(0));
			item.setIdFriend(cursor.getInt(1));
			item.setIdItem(cursor.getInt(2));
			item.setDate(cursor.getString(3));
			item.setStatus(cursor.getInt(4));
			cursor.close();
			return item;
		}
		return null;
	}

	public LoanView findView(Integer id) {
		String orderBY = "DT_DATE DESC";
		String where = ID_COLUMN + " = " + id;
		Cursor cursor = getReadableDatabase().query(TABLE_VIEW, null, where, null, null, null, orderBY);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				LoanView item = new LoanView();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setTitle(cursor.getString(2));
				item.setStatus(cursor.getInt(3));
				item.setDate(cursor.getString(4));
				item.setType(cursor.getInt(5));
				cursor.close();
				return item;
			}
			cursor.close();
		}
		return null;
	}

	public ArrayList<LoanView> findAll() {
		String orderBY = "DT_DATE DESC";
		ArrayList<LoanView> list = new ArrayList<LoanView>();
		Cursor cursor = getReadableDatabase().query(TABLE_VIEW, null, null, null, null, null, orderBY);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				LoanView item = new LoanView();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setTitle(cursor.getString(2));
				item.setStatus(cursor.getInt(3));
				item.setDate(cursor.getString(4));
				item.setType(cursor.getInt(5));
				list.add(item);
			}
			cursor.close();
		}
		return list;
	}

	public LoanView findRandomView() {
		String orderBY = "RANDOM()";
		String where = "FG_STATUS = 1";
		Cursor cursor = getReadableDatabase().query(TABLE_VIEW, null, where, null, null, null, orderBY, "1");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				LoanView item = new LoanView();
				item.setId(cursor.getInt(0));
				item.setName(cursor.getString(1));
				item.setTitle(cursor.getString(2));
				item.setStatus(cursor.getInt(3));
				item.setDate(cursor.getString(4));
				item.setType(cursor.getInt(5));
				cursor.close();
				return item;
			}
			cursor.close();
		}
		return null;
	}
}