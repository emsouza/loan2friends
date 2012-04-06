package br.com.eduardo.loan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

	private static final String DB_FILE_NAME = "loan_friends.db";

	public DBManager(Context context) {
		super(context, DB_FILE_NAME, null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createFriend = "CREATE TABLE FRIEND ( _id INTEGER PRIMARY KEY AUTOINCREMENT, NA_NAME TEXT, NA_NUMBER TEXT, CONTACT_ID INTEGER );";
		String createItem = "CREATE TABLE ITEM ( _id INTEGER PRIMARY KEY AUTOINCREMENT, NA_TITLE TEXT, NA_DESC TEXT, TP_ITEM INTEGER DEFAULT 0 NOT NULL );";
		String createHistory = "CREATE TABLE LOAN_HISTORY ( _id INTEGER PRIMARY KEY AUTOINCREMENT, ID_FRIEND INTEGER NOT NULL, ID_ITEM INTEGER NOT NULL, DT_DATE TEXT NOT NULL, FG_STATUS INTEGER NOT NULL);";
		String createLoanView = "CREATE VIEW LOAN_VIEW AS SELECT loan._id AS _id, friend.NA_NAME AS NA_NAME, item.NA_TITLE AS NA_TITLE, loan.FG_STATUS AS FG_STATUS, loan.DT_DATE AS DT_DATE, item.TP_ITEM AS TP_ITEM FROM LOAN_HISTORY loan, ITEM item, FRIEND friend WHERE loan.id_friend = friend._ID AND loan.id_item = item._ID;";

		try {
			db.execSQL(createFriend);
			db.execSQL(createItem);
			db.execSQL(createHistory);
			db.execSQL(createLoanView);
		} catch (Exception e) {
			Log.e(DBManager.class.getName(), "Erro ao criar banco de dados.", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String alter = "ALTER TABLE FRIEND ADD CONTACT_ID INTEGER;";

		try {
			db.execSQL(alter);
		} catch (Exception e) {
			Log.e(DBManager.class.getName(), "Erro ao atualizar base de dados.", e);
		}
	}
}