package com.app.e_business_easytask.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.e_business_easytask.Task;
import com.app.e_business_easytask.entity.*;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("Range")
public class TaskDataSource {

    private SQLiteDatabase database;
    private final DBHelper dbHelper;

    public TaskDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void openWritableDB() {
        database = dbHelper.getWritableDatabase();
    }
    public void open() {
        database = dbHelper.getWritableDatabase();
    }
    public void openReadableDB() {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertTask(String serviceType, String jobDetails, String formattedDate, String formattedTime, String street, String house_number, String zip_code, Integer duration, String duration_unit, double budget) {
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

        long result = database.insert("tasks", null, values);

        if (result == -1) {
            Log.e("TaskDataSource", "Fehler beim Einfügen des Auftrags in die Datenbank.");
        } else {
            Log.d("TaskDataSource", "Auftrag erfolgreich in die Datenbank eingefügt. ID: " + result);
        }
    }
    public void logAllTasks() {
        Cursor cursor = database.query("tasks", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int serviceTypeColumnIndex = cursor.getColumnIndex("service_type");
                Log.d("TaskDataSource", "Service Type Column Index: " + serviceTypeColumnIndex);
            }
            cursor.close();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = database.query("tasks", null, null, null, null, null, null);

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int serviceTypeColumnIndex = cursor.getColumnIndex("service_type");
            int jobDetailsColumnIndex = cursor.getColumnIndex("job_details");
            int formattedDateColumnIndex = cursor.getColumnIndex("formattedDate");
            int formattedTimeColumnIndex = cursor.getColumnIndex("formattedTime");
            int streetColumnIndex = cursor.getColumnIndex("street");
            int houseNumberColumnIndex = cursor.getColumnIndex("house_number");
            int zipCodeColumnIndex = cursor.getColumnIndex("zip_code");
            int durationColumnIndex = cursor.getColumnIndex("duration");
            int durationUnitColumnIndex = cursor.getColumnIndex("duration_unit");
            int budgetColumnIndex = cursor.getColumnIndex("budget");

            while (cursor.moveToNext()) {
                Task task = new Task();

                if (idColumnIndex >= 0) {
                    task.setId(cursor.getLong(idColumnIndex));
                }

                if (serviceTypeColumnIndex >= 0) {
                    task.setServiceType(cursor.getString(serviceTypeColumnIndex));
                }

                if (jobDetailsColumnIndex >= 0) {
                    task.setJobDetails(cursor.getString(jobDetailsColumnIndex));
                }

                if (formattedDateColumnIndex >= 0) {
                    task.setFormattedDate(cursor.getString(formattedDateColumnIndex));
                }

                if (formattedTimeColumnIndex >= 0) {
                    task.setFormattedTime(cursor.getString(formattedTimeColumnIndex));
                }

                if (streetColumnIndex >= 0) {
                    task.setStreet(cursor.getString(streetColumnIndex));
                }

                if (houseNumberColumnIndex >= 0) {
                    task.setHouseNumber(cursor.getString(houseNumberColumnIndex));
                }

                if (zipCodeColumnIndex >= 0) {
                    task.setZipCode(cursor.getString(zipCodeColumnIndex));
                }

                if (durationColumnIndex >= 0) {
                    task.setDuration(cursor.getInt(durationColumnIndex));
                }

                if (durationUnitColumnIndex >= 0) {
                    task.setDurationUnit(cursor.getString(durationUnitColumnIndex));
                }

                if (budgetColumnIndex >= 0) {
                    task.setBudget(cursor.getDouble(budgetColumnIndex));
                }

                taskList.add(task);
            }

            cursor.close();
        }

        return taskList;
    }


    public Long insertBenutzer(String vorname, String nachname, String email, String passwort, String telefonnummer, String ort, String plz, String strasse, String hausnummer, String user_type) {
        long adresse_id = insertAdresse(ort, plz, strasse, hausnummer);

        ContentValues values = new ContentValues();
        values.put("vorname", vorname);
        values.put("nachname", nachname);
        values.put("email", email);
        values.put("passwort", passwort);
        values.put("telefonnummer", telefonnummer);
        values.put("adresse_id", adresse_id);
        values.put("user_type", user_type);

        long result = -1;

        try {
            result = database.insert("Benutzer", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("RegisterActivity", "GangGang: " + e.getMessage());
            return result;
        } catch (Exception e) {
            Log.d("RegisterActivity", "GangGang: " + e.getMessage());
            return result;
        }


        if (result == -1) {
            Log.e("BenutzerDataSource", "Fehler beim Einfügen des Benutzers in die Datenbank.");
        } else {
            Log.d("BenutzerDataSource", "Benutzer erfolgreich in die Datenbank eingefügt. ID: " + result);
        }

        return result;
    }

    public Long insertDienstleister(String vorname, String nachname, String email, String passwort, String telefonnummer, String ort, String plz, String strasse, String hausnummer, String user_type, String verfuegbarkeit){

        long id = insertBenutzer(vorname, nachname, email, passwort, telefonnummer, ort, plz, strasse, hausnummer, user_type);

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("verfuegbarkeit", verfuegbarkeit);

        long result = -1;

        try {
            result = database.insert("Dienstleister", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("RegisterActivity", "Fehler: " + e.getMessage());
            return null;
        }
        if (result == -1) {
            Log.e("DienstleisterDataSource", "Fehler beim Einfügen des Dienstleisters in die Datenbank.");
            return null;
        } else {
            Log.d("DienstleisterDataSource", "Dienstleister erfolgreich in die Datenbank eingefügt. ID: " + result);
            return result;
        }
    }

    private Long insertAdresse(String ort, String plz, String strasse, String hausnummer) {
        ContentValues values = new ContentValues();
        values.put("ort", ort);
        values.put("plz", plz);
        values.put("strasse", strasse);
        values.put("nr", hausnummer);

        long result = database.insert("Adresse", null, values);

        if (result == -1) {
            Log.e("AdresseDataSource", "Fehler beim Einfügen der Adresse in die Datenbank.");
        } else {
            Log.d("AdresseDataSource", "Adresse erfolgreich in die Datenbank eingefügt. ID: " + result);
        }

        return result;
    }

    public Benutzer getBenutzerByEmail(String email) {
        Benutzer benutzer = null;

        // Query, um den Benutzer anhand der Email zu laden
        Cursor cursor = database.query(
                "Benutzer",
                new String[]{"id", "vorname", "nachname", "email", "passwort", "telefonnummer", "adresse_id", "user_type"},
                "email = ?",
                new String[]{email},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Benutzer gefunden
            long benutzerId = cursor.getLong(cursor.getColumnIndex("id"));
            String vorname = cursor.getString(cursor.getColumnIndex("vorname"));
            String nachname = cursor.getString(cursor.getColumnIndex("nachname"));
            String passwort = cursor.getString(cursor.getColumnIndex("passwort"));
            String telefonnummer = cursor.getString(cursor.getColumnIndex("telefonnummer"));
            long adresseId = cursor.getLong(cursor.getColumnIndex("adresse_id"));
            String userType = cursor.getString(cursor.getColumnIndex("user_type"));

            // Benutzer-Objekt erstellen
            benutzer = new Benutzer(benutzerId, vorname, nachname, email, passwort, telefonnummer, adresseId, null, userType);

            // Adresse anhand der adresse_id laden
            Adresse adresse = getAdresseById(adresseId);

            // Adresse zum Benutzer hinzufügen
            benutzer.setAdresse(adresse);
        }

        // Ressourcen freigeben
        if (cursor != null) {
            cursor.close();
        }

        return benutzer;
    }

    private Adresse getAdresseById(long adresseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Adresse adresse = null;

        // Query, um die Adresse anhand der ID zu laden
        Cursor cursor = db.query(
                "Adresse",
                new String[]{"id", "ort", "plz", "strasse", "nr"},
                "id = ?",
                new String[]{String.valueOf(adresseId)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Adresse gefunden
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String ort = cursor.getString(cursor.getColumnIndex("ort"));
            String plz = cursor.getString(cursor.getColumnIndex("plz"));
            String strasse = cursor.getString(cursor.getColumnIndex("strasse"));
            String nr = cursor.getString(cursor.getColumnIndex("nr"));

            // Adresse-Objekt erstellen
            adresse = new Adresse(id, ort, plz, strasse, nr);
        }

        // Ressourcen freigeben
        if (cursor != null) {
            cursor.close();
        }

        // Datenbankverbindung schließen
        db.close();

        return adresse;
    }
}
