package com.app.e_business_easytask.database;

import android.content.ContentValues;
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
        //insertMockData(db);
    }

    /*private void insertMockData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put("service_type", "Rasenmaehen");
        values.put("job_details", "Rasenmaehen am Sonntag");
        values.put("formattedDate", "2024-01-21");
        values.put("formattedTime", "11:01");
        values.put("street", "Musterstrasse");
        values.put("house_number", "1");
        values.put("zip_code", "12345");
        values.put("duration", 30);
        values.put("duration_unit", "Minutes");
        values.put("budget", 20.0);
        db.insert("Benutzer", null, values);

        values.clear();
        values.put("service_type", "Malerarbeiten");
        values.put("job_details", "Lattenzaun streichen");
        values.put("formattedDate", "2024-01-19");
        values.put("formattedTime", "12:00");
        values.put("street", "Haupstrasse");
        values.put("house_number", "2");
        values.put("zip_code", "12345");
        values.put("duration", 90);
        values.put("duration_unit", "Minutes");
        values.put("budget", 30.0);
        db.insert("Benutzer", null, values);

        values.clear();
        values.put("service_type", "Haushaltsreparaturen");
        values.put("job_details", "Waschbecken reparieren");
        values.put("formattedDate", "2024-01-20");
        values.put("formattedTime", "18:00");
        values.put("street", "Nebenstrasse");
        values.put("house_number", "3");
        values.put("zip_code", "12345");
        values.put("duration", 120);
        values.put("duration_unit", "Minutes");
        values.put("budget", 50.0);
        db.insert("Benutzer", null, values);
    }*/


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Adresse;");
        db.execSQL("DROP TABLE IF EXISTS Benutzer;");
        db.execSQL("DROP TABLE IF EXISTS Dienstleister;");
        db.execSQL("DROP TABLE IF EXISTS tasks;");
        onCreate(db);
    }
}
