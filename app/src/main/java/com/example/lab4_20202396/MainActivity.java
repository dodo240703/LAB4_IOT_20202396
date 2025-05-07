package com.example.lab4_20202396;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4_20202396.R;
import com.example.lab4_20202396.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(view -> {
            if (NetworkUtils.isNetworkAvailable(this)) {
                // Si hay conexión, abre la AppActivity
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
            } else {
                // Si no hay conexión, muestra diálogo
                showNoInternetDialog();
            }
        });
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión")
                .setMessage("No tienes conexión a internet. ¿Deseas abrir la configuración?")
                .setCancelable(false)
                .setPositiveButton("Configuración", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}