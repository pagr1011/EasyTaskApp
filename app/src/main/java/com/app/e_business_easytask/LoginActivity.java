package com.app.e_business_easytask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.e_business_easytask.database.TaskDataSource;
import com.app.e_business_easytask.entity.Benutzer;
import com.app.e_business_easytask.security.PasswordHasher;

public class LoginActivity extends AppCompatActivity {

    private Benutzer benutzer;
    private EditText emailEditText, passwortEditText;
    private Button anmeldenButton;
    private ImageView backButton;
    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataSource = new TaskDataSource(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwortEditText = findViewById(R.id.passwortEditText);
        anmeldenButton = findViewById(R.id.anmeldenButton);
        backButton = findViewById(R.id.backButton);

        anmeldenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity", "Anmeldeprozess gestartet");
                String email = emailEditText.getText().toString();
                String password = passwortEditText.getText().toString();
                // Wenn die Überprüfung erfolgreich ist, starte die MainActivity
                if (checkCredentials(email, password)) {
                    Log.d("LoginActivity", "Login erfolgreich");
                    startMainActivity(benutzer.getId());
                } else {
                    Log.d("LoginActivity", "Login fehlgeschlagen");
                    Toast.makeText(LoginActivity.this, "Login fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStartActivity();
            }
        });
    }

    private boolean checkCredentials(String email, String password) {
        Log.d("LoginActivity", "checkCredentials: Email = " + email + " Passwort = *****");
        String storedHashedPassword = getStoredPasswordFromDatabase(email);

        if (storedHashedPassword != null) {
            return PasswordHasher.checkPassword(password, storedHashedPassword);
        } else {
            Log.d("LoginActivity", "Kein Benutzer zu Email: " + email + " gefunden.");
            return false;
        }
    }

    private String getStoredPasswordFromDatabase(String email) {
        dataSource.openReadableDB();

        Benutzer dbUser = dataSource.getBenutzerByEmail(email);

        if (dbUser != null) {

            benutzer = dbUser;

            Log.d("LoginActivity", "Benutzer gefunden. " + "Benutzer-Daten:"
                    + " id: " + benutzer.getId()
                    + " | vorname: " + benutzer.getVorname()
                    + " | nachname: " + benutzer.getNachname()
                    + " | email: " + email
                    + " | hashedPasswort: " + benutzer.getPasswort()
                    + " | telefonnummer: " + benutzer.getTelefonnummer()
                    + " | adresse_id: " + benutzer.getAdresse_id()
                    + " | user_type: " + benutzer.getUser_type());

            dataSource.close();

            return benutzer.getPasswort();
        } else {
            return null;
        }
    }

    // Methode, um die Hauptaktivität zu starten
    private void startMainActivity(long id) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user_id", id);
        startActivity(intent);
        finish();
    }

    private void startStartActivity() {
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}