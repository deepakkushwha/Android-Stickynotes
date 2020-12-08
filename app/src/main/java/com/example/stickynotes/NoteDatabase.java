package com.example.stickynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Note.db";
    public static final String TABLE_NAME = "Notes_table";

    //Columns  Name

    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    public NoteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//Create table table_name();

        String query = ("create table " + TABLE_NAME + "(ID integer primary key ,title text,content text,date text,time text )");
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(db);

    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_TIME, note.getTime());
        c.put(KEY_DATE, note.getDate());
        long ID = db.insert(TABLE_NAME, null, c);
        Log.d("INSERTED", "ID->" + ID);
        return ID;

    }

    //******************Editcode

    public Note getnote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE
                , KEY_TIME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(cursor.getLong(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return note;
    }

    public List<Note> getnotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note node = new Note();
                node.setID(cursor.getLong(0));
                node.setTitle(cursor.getString(1));
                node.setContent(cursor.getString(2));
                node.setDate(cursor.getString(3));
                node.setTime(cursor.getString(4));
                allNotes.add(node);

            } while (cursor.moveToNext());
        }
        return allNotes;
    }
    public int editNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_TIME, note.getTime());
        c.put(KEY_DATE, note.getDate());
        return db.update(TABLE_NAME, c, KEY_ID + "=?", new String[]{String.valueOf(note.getID())});
    }
    void deletenote(long id){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
                db.close();
    }
}
