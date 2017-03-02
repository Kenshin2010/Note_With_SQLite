package com.android.manroid.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.android.manroid.model.NoteModel;
import com.android.manroid.sqlite.NoteSQliteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Manroid on 1/28/2017.
 */

public class NoteDataSource {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] allColumns = {NoteSQliteHelper.COLUMN_ID , NoteSQliteHelper.COLUMN_NOTE , NoteSQliteHelper.COLUMN_DATETIME};
    private Context context;


    public NoteDataSource(Context context) {
        this.context = context;
        sqLiteOpenHelper = new NoteSQliteHelper(context);
    }
    public void open() throws SQLiteException{
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close() throws SQLiteException{
        sqLiteOpenHelper.close();
    }
    public void addNewNote(String note){

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
        String datetime = sdf.format(date);
  //      String datetime = DateFormat.getDateTimeInstance().format(new Date());
        ContentValues values  = new ContentValues();
        values.put(NoteSQliteHelper.COLUMN_NOTE,note);
        values.put(NoteSQliteHelper.COLUMN_DATETIME,datetime);
        //insert
        sqLiteDatabase.insert(NoteSQliteHelper.TABLE_NAME,null,values);
        Toast.makeText(this.context,"Add new note success",Toast.LENGTH_SHORT).show();
    }
    public void deleteNote(NoteModel note){
        sqLiteDatabase.delete(
                NoteSQliteHelper.TABLE_NAME,
                NoteSQliteHelper.COLUMN_ID +
                        "=" +
                        note.getId(),null);
        Toast.makeText(this.context,"Delete note success",Toast.LENGTH_SHORT).show();
    }
    public ArrayList<NoteModel> getAllNotes(){
        ArrayList<NoteModel> arr = new ArrayList<NoteModel>();
        Cursor cursor = sqLiteDatabase.query(NoteSQliteHelper.TABLE_NAME,allColumns,null,null,null,null,null);
        if (cursor == null){
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            NoteModel model = cursorToModel(cursor);
            arr.add(model);
            cursor.moveToNext();
        }
        return arr;
    }

    private NoteModel cursorToModel(Cursor cursor) {
        NoteModel model  = new NoteModel();
        model.setId(cursor.getInt(0));
        model.setNote(cursor.getString(1));
        model.setDatetime(cursor.getString(2));
        return model;
    }
}
