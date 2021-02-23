package com.lov.kontak.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lov.kontak.constant.ContactField;
import com.lov.kontak.model.Contact;

public class SQLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contact.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactField.TABLE_NAME + " (" +
                    ContactField.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactField.COLUMN_PHONE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactField.TABLE_NAME;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void create(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, contact.getName());
        values.put(ContactField.COLUMN_PHONE, contact.getPhone());

        long newRowId;
        newRowId = db.insert(
                ContactField.TABLE_NAME,
                null,
                values);
    }

    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                ContactField.COLUMN_ID,
                ContactField.COLUMN_NAME,
                ContactField.COLUMN_PHONE };

        String sortOrder =
                ContactField.COLUMN_NAME + " ASC";

        Cursor c = db.query(
                ContactField.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return c;
    }

    public void update(Contact contact){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactField.COLUMN_NAME, contact.getName());
        values.put(ContactField.COLUMN_PHONE, contact.getPhone());

        String selection = ContactField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(contact.getId()) };

        int count = db.update(
                ContactField.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void delete(int id){
        SQLiteDatabase db = getReadableDatabase();

        String selection = ContactField.COLUMN_ID + " LIKE ?";

        String[] selectionArgs = { String.valueOf(id) };

        db.delete(ContactField.TABLE_NAME, selection, selectionArgs);
    }
}
