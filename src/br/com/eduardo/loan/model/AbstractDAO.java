package br.com.eduardo.loan.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 20/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
public abstract class AbstractDAO extends SQLiteOpenHelper {

	private static final String DB_FILE_NAME = "loan_friends.db";

	public AbstractDAO(Context context) {
		super(context, DB_FILE_NAME, null, 9);// 7
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createFriend = "CREATE TABLE FRIEND ( _id INTEGER PRIMARY KEY AUTOINCREMENT, NA_NAME TEXT, NA_NUMBER TEXT, CONTACT_ID INTEGER );";
		String createItem = "CREATE TABLE ITEM ( _id INTEGER PRIMARY KEY AUTOINCREMENT, NA_TITLE TEXT, NA_DESC TEXT, TP_ITEM INTEGER DEFAULT 0 NOT NULL );";
		String createHistory = "CREATE TABLE LOAN_HISTORY ( _id INTEGER PRIMARY KEY AUTOINCREMENT, ID_FRIEND INTEGER NOT NULL, ID_ITEM INTEGER NOT NULL, DT_LENT TEXT NOT NULL, FG_STATUS INTEGER NOT NULL, DT_RETURN TEXT NULL);";
		String createLoanView = "CREATE VIEW LOAN_VIEW AS SELECT loan._id AS _id, friend.NA_NAME AS NA_NAME, loan.FG_STATUS AS FG_STATUS, loan.DT_LENT AS DT_LENT, friend.CONTACT_ID AS CONTACT_ID, DT_RETURN AS DT_RETURN FROM LOAN_HISTORY loan, ITEM item, FRIEND friend WHERE loan.id_friend = friend._ID AND loan.id_item = item._ID;";

		try {
			db.execSQL(createFriend);
			db.execSQL(createItem);
			db.execSQL(createHistory);
			db.execSQL(createLoanView);
		} catch (Exception e) {
			Log.e(AbstractDAO.class.getName(), "Erro ao criar banco de dados.", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropLoanView = "DROP VIEW LOAN_VIEW;";
		String createLoanView = "CREATE VIEW LOAN_VIEW AS SELECT loan._id AS _id, friend.NA_NAME AS NA_NAME, loan.FG_STATUS AS FG_STATUS, loan.DT_LENT AS DT_LENT, friend.CONTACT_ID AS CONTACT_ID, DT_RETURN AS DT_RETURN FROM LOAN_HISTORY loan, ITEM item, FRIEND friend WHERE loan.id_friend = friend._ID AND loan.id_item = item._ID;";

		try {
			db.execSQL(dropLoanView);
			db.execSQL(createLoanView);
		} catch (Exception e) {
			Log.e(AbstractDAO.class.getName(), "Erro ao atualizar base de dados.", e);
		}
	}
}