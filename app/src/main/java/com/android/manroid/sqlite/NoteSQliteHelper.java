package com.android.manroid.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manroid on 1/28/2017.
 */

public class NoteSQliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mynotes.db";
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATETIME = "datetime";
    public static final int DATABSE_VERSION = 1;

    public static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + "( "
                                                                 + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement,"
                                                                 + COLUMN_NOTE + " text not null, "
                                                                 + COLUMN_DATETIME+ " text);";
        public NoteSQliteHelper (Context context){
        super(context, DATABASE_NAME, null , DATABSE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"  + TABLE_NAME);
        onCreate(db);
    }
}
