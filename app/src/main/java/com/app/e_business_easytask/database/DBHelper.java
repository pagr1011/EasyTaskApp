package com.app.e_business_easytask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EasyTaskDB";
    private static final int DATABASE_VERSION = 2;

    // Benutzer-Tabelle
    private static final String CREATE_TABLE_BENUTZER = "CREATE TABLE IF NOT EXISTS Benutzer (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "vorname TEXT," +
            "nachname TEXT," +
            "email TEXT UNIQUE," +
            "telefonnummer TEXT," +
            "passwort TEXT," +
            "adresse_id INTEGER," +
            "user_type TEXT," +
            "FOREIGN KEY (adresse_id) REFERENCES Adresse(id)" +
            ");";

    // Dienstleister-Tabelle
    private static final String CREATE_TABLE_DIENSTLEISTER = "CREATE TABLE IF NOT EXISTS Dienstleister (" +
            "id INTEGER PRIMARY KEY," +
            "verfuegbarkeit TEXT," +
            "FOREIGN KEY (id) REFERENCES Benutzer(id)" +
            ");";

    // Adresse-Tabelle
    private static final String CREATE_TABLE_ADRESSE = "CREATE TABLE IF NOT EXISTS Adresse (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ort TEXT," +
            "plz TEXT," +
            "strasse TEXT," +
            "nr TEXT" +
            ");";

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE tasks (" +
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

    // Konstruktor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ADRESSE);
        db.execSQL(CREATE_TABLE_BENUTZER);
        db.execSQL(CREATE_TABLE_DIENSTLEISTER);
        db.execSQL(CREATE_TABLE_TASKS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Adresse;");
        db.execSQL("DROP TABLE IF EXISTS Benutzer;");
        db.execSQL("DROP TABLE IF EXISTS Dienstleister;");
        db.execSQL("DROP TABLE IF EXISTS tasks;");
        onCreate(db);
    }
}
