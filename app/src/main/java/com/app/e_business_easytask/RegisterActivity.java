package com.app.e_business_easytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.e_business_easytask.database.TaskDataSource;
import com.app.e_business_easytask.security.PasswordHasher;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText vornameEditText, nachnameEditText, emailEditText, passwortEditText, confirmPasswortEditText, telefonEditText, ortEditText, plzEditText, strasseEditText, nrEditText;
    private String user_type;
    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dataSource = new TaskDataSource(this);

        vornameEditText = findViewById(R.id.vornameEditText);
        nachnameEditText = findViewById(R.id.nachnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwortEditText = findViewById(R.id.passwortEditText);
        confirmPasswortEditText = findViewById(R.id.confirmPasswortEditText);
        telefonEditText = findViewById(R.id.telefonEditText);
        ortEditText = findViewById(R.id.ortEditText);
        plzEditText = findViewById(R.id.plzEditText);
        strasseEditText = findViewById(R.id.strasseEditText);
        nrEditText = findViewById(R.id.nrEditText);

        Intent intent = getIntent();
        if (intent != null) {
            // Extrahiert Daten aus dem Intent, wenn vorhanden
            String receivedData = intent.getStringExtra("user_type");
            if (receivedData != null) {
                user_type = receivedData;
            }
        }

        MaterialButton registrierenButton = findViewById(R.id.registrierenButton);
        registrierenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertBenutzer()) {
                    Log.d("RegisterActivity", "Formular abgeschickt");
                    startLoginActivity();
                } else {
                    Log.d("RegisterActivity", "Fehler im Formular");
                }
            }
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStartActivity();
            }
        });

        TextView anmeldenTextView = findViewById(R.id.anmeldenTextView);
        anmeldenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
    }

    private boolean insertBenutzer() {
        // Die folgenden Werte sollten aus den EditText-Widgets abgerufen werden
        String vorname = vornameEditText.getText().toString();
        String nachname = nachnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String passwort = passwortEditText.getText().toString();
        String confirmPasswort = confirmPasswortEditText.getText().toString();
        String telefon = telefonEditText.getText().toString();
        String ort = ortEditText.getText().toString();
        String plz = plzEditText.getText().toString();
        String strasse = strasseEditText.getText().toString();
        String nr = nrEditText.getText().toString();

        if (!passwort.equals(confirmPasswort)) {
            // Die Passwörter stimmen nicht überein, zeige eine Fehlermeldung an
            Toast.makeText(RegisterActivity.this, "Die Passwörter stimmen nicht überein", Toast.LENGTH_SHORT).show();
            return false;
        }

        String hashedPasswort = PasswordHasher.hashPassword(passwort);

        dataSource.openWritableDB();

        if (user_type.equals("Benutzer")) {

            Log.d("RegisterActivity", "Benutzer abspeichern. " + "Benutzer-Daten: "
                    + "vorname: " + vorname + " | nachname: " + nachname + " | email: " + email
                    + " | hashedPasswort: " + hashedPasswort + " | telefonnummer: " + telefon
                    + " | ort: " + ort + " | plz: " + plz + " | strasse: " + strasse
                    + " | nr: " + nr + " | user_type: " + user_type);

            dataSource.insertBenutzer(vorname, nachname, email, hashedPasswort, telefon, ort, plz, strasse, nr, user_type);

        } else if (user_type.equals("Dienstleister")) {

            Log.d("RegisterActivity", "Dienstleister abspeichern. " + "Benutzer-Daten: "
                    + "vorname: " + vorname + " | nachname: " + nachname + " | email: " + email
                    + " | hashedPasswort: " + hashedPasswort + " | telefonnummer: " + telefon
                    + " | ort: " + ort + " | plz: " + plz + " | strasse: " + strasse
                    + " | nr: " + nr + " | user_type: " + user_type);

            dataSource.insertDienstleister(vorname, nachname, email, hashedPasswort, telefon, ort, plz, strasse, nr, user_type, null);
        }

        dataSource.close();

        return true;
    }

    private void startStartActivity() {
        Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}