package com.app.e_business_easytask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskDataSource {

    private SQLiteDatabase database;
    private final DBHelper dbHelper;

    public TaskDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertTask(String serviceType, String jobDetails, String formattedDate, String street, String house_number, String zip_code, Integer duration, String duration_unit, double budget) {
        ContentValues values = new ContentValues();
        values.put("service_type", serviceType);
        values.put("job_details", jobDetails);
        values.put("formattedDate", formattedDate);
        values.put("street", street);
        values.put("house_number", house_number);
        values.put("zip_code", zip_code);
        values.put("duration", duration);
        values.put("duration_unit", duration_unit);
        values.put("budget", budget);

        //database.insert("tasks", null, values);
        long result = database.insert("tasks", null, values);

        if (result == -1) {
            Log.e("TaskDataSource", "Fehler beim Einfügen des Auftrags in die Datenbank.");
        } else {
            Log.d("TaskDataSource", "Auftrag erfolgreich in die Datenbank eingefügt. ID: " + result);
        }
    }

}
