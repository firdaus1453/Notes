package me.firdaus1453.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by firdaus1453 on 1/15/2019.
 */
public class DBNote extends SQLiteOpenHelper {

    public static abstract class MyColumns implements BaseColumns {
        public static final String namaTabel = "Notes";
        public static final String id_judul = "ID_Judul";
        public static final String judul = "Judul";
        public static final String isi = "Isi";
    }

    private static final String namaDatabase = "catatan.db";
    private static final int versiDatabase = 1;

    public DBNote(Context context) {
        super(context, namaDatabase, null, versiDatabase);
    }

    // Query untuk membuat table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MyColumns.namaTabel +
            "(" + MyColumns.id_judul + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MyColumns.judul + " TEXT NOT NULL, "
            + MyColumns.isi + " TEXT NOT NULL)";

    // Untuk menghapus table
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MyColumns.namaTabel;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
