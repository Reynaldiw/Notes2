package com.reynaldiwijaya.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBNoteHelper extends SQLiteOpenHelper {

    //TODO 2.1 Membuat Table
    public static abstract class MyColumns implements BaseColumns {
        public static final String namaTabel = "Notes";
        public static final String id_judul = "ID_Judul";
        public static final String judul = "Judul";
        public static final String isi = "Isi";
    }

    //TODO 2.0 Membuat Variable Database dan Versinya
    private static final String DATABASE_NAME = "catatan.db";
    private static final int DATABASE_VERSION = 1;

    //TODO 1.1 Membuat Constructor DBHelper
    public DBNoteHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //TODO 1.0 Implement Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO 3.0 Menjalankan perintah untuk membuat table
       db.execSQL(SQL_CREATE_TABLE);
    }

    //TODO 2.2 Perintah untuk membuat table
    // CREATE TABLE Notes(ID_Judul INTEGER PRIMARY KEY AUTO INCREMENT, Judul TEXT NOT NULL, Isi TEXT NOT NULL)
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            MyColumns.namaTabel + "(" +
            MyColumns.id_judul + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyColumns.judul + " TEXT NOT NULL, " +
            MyColumns.isi + " TEXT NOT NULL)";

    //TODO 3.1 Membuat Perintah untuk Delete Table
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MyColumns.namaTabel;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);

    }
}
