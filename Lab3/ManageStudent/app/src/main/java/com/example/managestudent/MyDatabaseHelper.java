package com.example.managestudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Information.db";
    private static final String TABLE_NAME = "students";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_CLASS = "class";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_CLASS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getStudentById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{id}, null, null, null);
    }

    public void addStudent(Information student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, student.getId());
        cv.put(COLUMN_FULL_NAME, student.getName());
        cv.put(COLUMN_CLASS, student.getClassAc());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }


    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        db.close();
        return row > -1;
    }


    public void updateStudent(Information student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, student.getId());
        cv.put(COLUMN_FULL_NAME, student.getName());
        cv.put(COLUMN_CLASS, student.getClassAc());
        db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{student.getId()});
        db.close();
    }
}