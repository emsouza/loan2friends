package br.com.eduardo.loan.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.eduardo.loan.model.entity.Item;
import br.com.eduardo.loan.util.type.Status;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

/**
 * @author Eduardo Matos de Souza<br>
 *         EMS - 17/09/2011 <br>
 *         <a href="mailto:eduardo.souza@emsouza.com.br">eduardo.souza@emsouza.com.br</a>
 */
@EBean(scope = Scope.Singleton)
public class ItemDAO extends AbstractDAO {

	private static final String TABLE_NAME = "ITEM";

	private static final String ID_COLUMN = "_id";

	private static final String TITLE_COLUMN = "NA_TITLE";

	private static final String DESC_COLUMN = "NA_DESC";

	private static final String TYPE_COLUMN = "TP_ITEM";

	public ItemDAO(Context context) {
		super(context);
	}

	public void insert(Item item) {
		ContentValues value = new ContentValues();
		value.put(TITLE_COLUMN, item.getTitle());
		value.put(DESC_COLUMN, item.getDescription());
		value.put(TYPE_COLUMN, item.getType());

		try {
			getWritableDatabase().insert(TABLE_NAME, null, value);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao inserir um item.", e);
		}
	}

	public void update(Item item) {
		String where = ID_COLUMN + " = " + item.getId();
		ContentValues value = new ContentValues();
		value.put(TITLE_COLUMN, item.getTitle());
		value.put(DESC_COLUMN, item.getDescription());
		value.put(TYPE_COLUMN, item.getType());

		try {
			getWritableDatabase().update(TABLE_NAME, value, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao atualizar um item.", e);
		}
	}

	public void delete(Integer id) {
		String where = ID_COLUMN + " = " + id;

		try {
			getWritableDatabase().delete(TABLE_NAME, where, null);
		} catch (Exception e) {
			Log.e(getClass().getName(), "Erro ao deletar um item.", e);
		}
	}

	public Item find(Integer id) {
		Item item = new Item();
		String where = ID_COLUMN + " = " + id;
		Cursor cursor = getReadableDatabase().query(true, TABLE_NAME, null, where, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			item.setId(cursor.getInt(0));
			item.setTitle(cursor.getString(1));
			item.setDescription(cursor.getString(2));
			item.setType(cursor.getInt(3));
			cursor.close();
			return item;
		}
		return null;
	}

	private ArrayList<Item> findAllItens() {
		ArrayList<Item> list = new ArrayList<Item>();
		Cursor cursor = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Item item = new Item();
				item.setId(cursor.getInt(0));
				item.setTitle(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				item.setType(cursor.getInt(3));
				list.add(item);
			}
			cursor.close();
		}
		return list;
	}

	public ArrayList<Item> findAll() {
		ArrayList<Item> list = findAllItens();
		for (Item i : list) {
			Cursor cursor = getReadableDatabase().query("LOAN_HISTORY", new String[] { "FG_STATUS" }, "ID_ITEM = " + i.getId(), null, null, null,
					"DT_LENT DESC", "1");
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getInt(0) != Status.LENDED.id()) {
					i.setStatus(Status.AVAILABLE.id());
				} else {
					i.setStatus(cursor.getInt(0));
				}
			} else {
				i.setStatus(Status.AVAILABLE.id());
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	public ArrayList<Item> findAllAvailable() {
		ArrayList<Item> list = new ArrayList<Item>();
		for (Item i : findAllItens()) {
			Cursor cursor = getReadableDatabase().query("LOAN_HISTORY", new String[] { "FG_STATUS" }, "ID_ITEM = " + i.getId(), null, null, null,
					"DT_LENT DESC", "1");
			if (cursor != null && cursor.moveToFirst()) {
				Integer status = cursor.getInt(0);
				if (status == Status.RETURNED.id() || status == Status.ARCHIVED.id()) {
					list.add(i);
				}
			} else {
				list.add(i);
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}
}