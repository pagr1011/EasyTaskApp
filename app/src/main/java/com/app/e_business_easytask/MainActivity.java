package com.app.e_business_easytask;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.app.e_business_easytask.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    // Instanz der Datenbindungs-Klasse für die Aktivität
    private ActivityMainBinding binding;

    // Methode, die beim Erstellen der Aktivität aufgerufen wird
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Datenbindung initialisieren und Hauptansicht setzen
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bottom Navigation View durch ID finden
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Konfiguration der Top-Level-Ziele für die Aktionsleiste
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_suche, R.id.navigation_inbox, R.id.navigation_profile, R.id.navigation_mytasks)
                .build();

        // NavController erstellen und mit dem Fragment verbinden
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Aktionsleiste und Bottom Navigation View mit NavController verbinden
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
