package com.app.e_business_easytask.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    // Task Tabelle
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
        // Überprüft, ob die Mock-Daten bereits eingefügt wurden
        if (isFirstRun(context)) {
            insertMockData();
            markFirstRunDone(context);
        }
    }

    private boolean isFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getBoolean("FirstRun", true);
    }

    private void markFirstRunDone(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstRun", false);
        editor.apply();
    }

    private void insertMockData() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();

            // Erstellt Tabellen in der richtigen Reihenfolge
            db.execSQL(CREATE_TABLE_ADRESSE);
            db.execSQL(CREATE_TABLE_BENUTZER);
            db.execSQL(CREATE_TABLE_DIENSTLEISTER);
            db.execSQL(CREATE_TABLE_TASKS);

            // Fügt Mock-Daten ein
            insertTask(db, "Cleaning", "Clean the house", "2024-01-17", "10:00 AM", "Sample Street", "123", "12345", 60, "minutes", 50.0);
            insertTask(db, "Gardening", "Water the plants", "2024-01-18", "02:00 PM", "Garden Street", "456", "56789", 45, "minutes", 30.0);

            db.setTransactionSuccessful();
            Log.d("DBHelper", "Mock data insertion completed.");
        } catch (Exception e) {
            Log.e("DBHelper", "Error inserting mock data into the database.", e);
        } finally {
            db.endTransaction();
        }
    }

    public void insertTask(SQLiteDatabase db, String serviceType, String jobDetails, String formattedDate, String formattedTime, String street, String house_number, String zip_code, Integer duration, String duration_unit, double budget) {
        ContentValues values = new ContentValues();
        values.put("service_type", serviceType);
        values.put("job_details", jobDetails);
        values.put("formattedDate", formattedDate);
        values.put("formattedTime", formattedTime);
        values.put("street", street);
        values.put("house_number", house_number);
        values.put("zip_code", zip_code);
        values.put("duration", duration);
        values.put("duration_unit", duration_unit);
        values.put("budget", budget);

        long result = db.insert("tasks", null, values);

        if (result == -1) {
            Log.e("DBHelper", "Error inserting task into the database.");
        } else {
            Log.d("DBHelper", "Task successfully inserted into the database. ID: " + result);
        }
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
