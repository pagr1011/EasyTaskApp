package com.app.e_business_easytask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EasyTaskDB";
    private static final int DATABASE_VERSION = 1;

    // Konstruktor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Hier erstellen Sie Ihre Tabelle
        String createTableQuery = "CREATE TABLE tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "service_type TEXT," +
                "job_details TEXT," +
                "formattedDate TEXT," +       // Spalte für das Datum
                "formattedTime TEXT," +       // Spalte für die Uhrzeit
                "street TEXT," +     // Weitere Spalten für den Standort
                "house_number TEXT," +
                "zip_code TEXT," +
                "duration INTEGER," + // Annahme: Dauer in Minuten
                "duration_unit TEXT," +
                "budget REAL);";

        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Wenn Sie die Datenbank aktualisieren müssen, passen Sie dies hier an
    }
}
