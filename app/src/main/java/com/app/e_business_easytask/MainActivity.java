package com.app.e_business_easytask;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.e_business_easytask.database.TaskDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.e_business_easytask.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Instanz der Datenbindungs-Klasse für die Aktivität
    private ActivityMainBinding binding;
    private TaskDataSource dataSource;
    private AlertDialog alertDialog;

    // Methode, die beim Erstellen der Aktivität aufgerufen wird
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
    }


    //Erstellen eines Auftrags (Grigorios)
    public void onCreateTaskClick(View view) {
        // Hier öffne das Popup-Fenster
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_create_task, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create(); // Hier wird die Instanzvariable aktualisiert
        alertDialog.show();
    }

    public void onConfirmClick(View view) {
        if (alertDialog != null) {
            // Get the inflated view from the dialog
            View dialogView = alertDialog.findViewById(R.id.popup_create_task_layout);
            if (dialogView != null) {
                // Hier sidn die eingegebenen Daten aus den EditText-Views

                String serviceType = ((EditText) findViewById(R.id.editTextServiceType)).getText().toString();
                String jobDetails = ((EditText) findViewById(R.id.editTextJobDetails)).getText().toString();

                //Datum
                DatePicker datePicker = findViewById(R.id.datePicker);
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                //Uhrzeit
                TimePicker timePicker = findViewById(R.id.timePicker);
                int hour;
                int minute;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    // Für Versionen vor Android 6.0 (API-Level 23)
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                // Datum und Uhrzeit Formatierung
                String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d %02d:%02d", year, month + 1, day, hour, minute);

                //Ort
                String street = ((EditText) findViewById(R.id.editTextStreet)).getText().toString();
                String house_number = ((EditText) findViewById(R.id.editTextHouseNumber)).getText().toString();
                String zip_code = ((EditText) findViewById(R.id.editTextZipCode)).getText().toString();

                // Dauer des Auftrags
                String durationText = ((EditText) findViewById(R.id.editTextDuration)).getText().toString();
                int duration = Integer.parseInt(durationText);
                String duration_unit = ((Spinner) findViewById(R.id.spinnerDurationUnit)).getSelectedItem().toString();

                //budget
                double budget = Double.parseDouble(((EditText) findViewById(R.id.editTextBudget)).getText().toString());

                // Daten in die Datenbank einfügen
                dataSource.open(); // DB öffnen
                dataSource.insertTask(serviceType, jobDetails, formattedDate, street, house_number, zip_code, duration, duration_unit, budget);
                dataSource.close(); // DB schließen

                //Feedback an den Benutzer geben
                Toast.makeText(this, "Auftrag gespeichert!", Toast.LENGTH_SHORT).show();

                alertDialog.dismiss();
            } else {
                // Handle the case where the inflated view is null
                Toast.makeText(this, "Dialog view not found", Toast.LENGTH_SHORT).show();
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
            dataSource.close(); // Beispiel: Schließe die Datenbankverbindung
        }
    }

}


