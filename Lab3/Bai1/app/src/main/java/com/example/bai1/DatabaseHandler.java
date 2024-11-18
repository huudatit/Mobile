package com.example.bai1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactManager.db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_PH_NO + " TEXT" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
    }

    public Cursor getContactById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CONTACTS, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public boolean deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return row > -1;
    }

    public void resetContactsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM contacts");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name = 'contacts'"); // Đặt lại AUTOINCREMENT
        db.close();
    }
}
