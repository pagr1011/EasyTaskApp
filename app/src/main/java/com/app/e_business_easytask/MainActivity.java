package com.app.e_business_easytask;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.e_business_easytask.database.TaskDataSource;
import com.app.e_business_easytask.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Instanz der Datenbindungs-Klasse für die Aktivität
    private ActivityMainBinding binding;
    private TaskDataSource dataSource;
    private Validator validator;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_suche, R.id.navigation_inbox, R.id.navigation_profile, R.id.navigation_mytasks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        dataSource = new TaskDataSource(this);
        validator = new Validator(this);
    }


    //Erstellen eines Auftrags
    public void onCreateTaskClick(View view) {
        // Hier öffnet sich das Popup-Fenster
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_create_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create(); // Hier wird die Instanzvariable aktualisiert
        alertDialog.show();
    }

    public void onSearchFilterClick(View view) {
        // Hier öffnet sich das Popup-Fenster
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_filter_suche, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create(); // Hier wird die Instanzvariable aktualisiert
        alertDialog.show();
    }

    public void onConfirmClick(View view) {
        if (alertDialog != null) {
            // Inflated View erhalten
            View dialogView = alertDialog.findViewById(R.id.popup_create_task_layout);
            if (dialogView != null) {
                // Hier sind die Eingabedaten aus den EditText-Views
                Log.d("MainActivity", "Dialog view found");

                String serviceType = ((EditText) dialogView.findViewById(R.id.editTextServiceType)).getText().toString();
                String jobDetails = ((EditText) dialogView.findViewById(R.id.editTextJobDetails)).getText().toString();
                Log.d("MainActivity", "ServiceType: " + serviceType);
                Log.d("MainActivity", "JobDetails: " + jobDetails);

                // Datum
                DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                // Uhrzeit
                TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
                int hour;
                int minute;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                Log.d("MainActivity", "TimePicker: " + timePicker);

                // Datum separat speichern
                String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);

                // Uhrzeit separat speichern
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                Log.d("MainActivity", "formattedDate: " + formattedDate);
                Log.d("MainActivity", "formattedTime: " + formattedTime);

                // Ort
                String street = ((EditText) dialogView.findViewById(R.id.editTextStreet)).getText().toString();
                String houseNumber = ((EditText) dialogView.findViewById(R.id.editTextHouseNumber)).getText().toString();
                String zipCode = ((EditText) dialogView.findViewById(R.id.editTextZipCode)).getText().toString();
                Log.d("MainActivity", "street: " + street);
                Log.d("MainActivity", "house_number: " + houseNumber);
                Log.d("MainActivity", "zip_code: " + zipCode);

                // Dauer des Auftrags
                String durationText = ((EditText) dialogView.findViewById(R.id.editTextDuration)).getText().toString();
                int duration = Integer.parseInt(durationText);
                String durationUnit = ((Spinner) dialogView.findViewById(R.id.spinnerDurationUnit)).getSelectedItem().toString();
                Log.d("MainActivity", "duration: " + duration);
                Log.d("MainActivity", "duration_unit: " + durationUnit);

                // Budget
                double budget = Double.parseDouble(((EditText) dialogView.findViewById(R.id.editTextBudget)).getText().toString());
                Log.d("MainActivity", "budget: " + budget);

                if (validator.validateTask(serviceType, jobDetails, formattedDate, formattedTime,
                        street, houseNumber, zipCode, durationText, durationUnit, String.valueOf(budget))) {
                    // Daten in die Datenbank einfügen
                    dataSource.openWritableDB(); // DB öffnen
                    dataSource.insertTask(serviceType, jobDetails, formattedDate, formattedTime, street, houseNumber, zipCode, duration, durationUnit, budget);
                    dataSource.close(); // DB schließen

                    // Feedback an den Benutzer geben
                    Toast.makeText(this, "Auftrag erstellt!", Toast.LENGTH_SHORT).show();

                    alertDialog.dismiss();
                } else {
                    // Wenn die inflated view null ist dann soll der Text ausgegeben werden
                    Toast.makeText(this, "Dialog view not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onCancelClick(View view) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void onAddFilesClick(View view) {
        Toast.makeText(this, "Ihre ganzen Bilder wurden hinzugefügt!", Toast.LENGTH_SHORT).show();
    }
}


