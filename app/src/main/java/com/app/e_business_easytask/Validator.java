package com.app.e_business_easytask;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Validator {

    private final Context context;

    public Validator(Context context) {
        this.context = context;
    }

    public boolean validateTask(String serviceType, String jobDetails, String formattedDate, String formattedTime,
                                String street, String houseNumber, String zipCode,
                                String duration, String durationUnit, String budget) {

        if (TextUtils.isEmpty(serviceType) || TextUtils.isEmpty(jobDetails) ||
                TextUtils.isEmpty(formattedDate) || TextUtils.isEmpty(formattedTime) ||
                TextUtils.isEmpty(street) || TextUtils.isEmpty(houseNumber) ||
                TextUtils.isEmpty(zipCode) || TextUtils.isEmpty(duration) ||
                TextUtils.isEmpty(durationUnit) || TextUtils.isEmpty(budget)) {
            showToast("Bitte füllen Sie alle Felder aus.");
            return false;
        }

        // Datum und Uhrzeit validieren
        if (!isFutureDateTime(formattedDate, formattedTime)) {
            showToast("Datum und Uhrzeit dürfen nicht in der Vergangenheit liegen.");
            return false;
        }

        // Postleitzahl validieren
        if (!isValidZipCode(zipCode)) {
            showToast("Die Postleitzahl muss genau 5 Ziffern enthalten.");
            return false;
        }
        return true;
    }

    private boolean isFutureDateTime(String formattedDate, String formattedTime) {
        try {
            String dateTimeString = formattedDate + " " + formattedTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date inputDate = format.parse(dateTimeString);

            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            return inputDate != null && inputDate.after(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidZipCode(String zipCode) {
        return zipCode.length() == 5 && TextUtils.isDigitsOnly(zipCode);
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
