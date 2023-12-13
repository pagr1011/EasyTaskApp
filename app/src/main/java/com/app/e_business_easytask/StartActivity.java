package com.app.e_business_easytask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {

    private MaterialButton benutzerButton, taskerButton, ueberspringenButton;
    private TextView anmeldenTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        benutzerButton = findViewById(R.id.benutzerButton);
        taskerButton = findViewById(R.id.taskerButton);
        ueberspringenButton = findViewById(R.id.ueberspringenButton);
        anmeldenTextView = findViewById(R.id.anmeldenTextView);

        benutzerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartActivity", "Als Benutzer registrieren.");
                startRegistrationActivity("Benutzer");
            }
        });

        taskerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartActivity", "Als Dienstleister registrieren.");
                startRegistrationActivity("Dienstleister");
            }
        });

        anmeldenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartActivity", "Benutzer hat bereits Account.");
                startLoginActivity();
            }
        });

        ueberspringenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("StartActivity", "Benutzer möchte noch keinen Account anlegen.");
                startMainActivity();
            }
        });
    }

    // Methode, um die Registrierungsaktivität zu starten
    private void startRegistrationActivity(String user_type) {
        Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
        intent.putExtra("user_type", user_type);
        startActivity(intent);
        finish();
    }

    // Methode, um die Loginaktivität zu starten
    private void startLoginActivity() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Methode, um die Hauptaktivität zu starten
    private void startMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}