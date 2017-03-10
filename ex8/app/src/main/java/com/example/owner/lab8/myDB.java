package com.example.owner.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "mytable";

    public myDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE if not exists " +
                TABLE_NAME +
                " (_id INTEGER PRIMARY KEY, name TEXT, birth TEXT, gift TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birth", birth);
        cv.put("gift", gift);
        db.insert(TABLE_NAME, null, cv);
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{name});
    }

    public void update(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birth", birth);
        cv.put("gift", gift);
        db.update(TABLE_NAME, cv, "name=?", new String[]{name});
    }

    public boolean findName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{"name"},
                "name=?", new String[]{name}, null, null, null);
        if (cursor == null || cursor.getCount() == 0 || !cursor.moveToFirst()) return false;
        else return true;
    }

    public Cursor getCursor() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{"_id", "name", "birth", "gift"},
                null, null, null, null, null);
        return cursor;
    }
}
