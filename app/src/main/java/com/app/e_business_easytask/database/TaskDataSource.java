package com.app.e_business_easytask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.e_business_easytask.Task;

import java.util.ArrayList;
import java.util.List;

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

}
