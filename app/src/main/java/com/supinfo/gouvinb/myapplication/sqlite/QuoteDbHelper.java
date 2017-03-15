package com.supinfo.gouvinb.myapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.supinfo.gouvinb.myapplication.model.Quote.QuoteEntry;

/**
 * Created by gouvinb on 15/03/2017.
 */

public class QuoteDbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "Quote.db";
  public static final int DATABASE_VERSION = 1;

  private static final String SQL_CREATE_ENTRIES =
      "CREATE TABLE " + QuoteEntry.TABLE_NAME + " (" +
          QuoteEntry._ID + " INTEGER PRIMARY KEY," +
          QuoteEntry.COLUMN_NAME_QUOTE + " TEXT," +
          QuoteEntry.COLUMN_NAME_RATING + " INTEGER," +
          QuoteEntry.COLUMN_NAME_DATE + " TEXT)";

  private static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + QuoteEntry.TABLE_NAME;


  public QuoteDbHelper(Context applicationContext) {
    super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}
