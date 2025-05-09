package com.example.lab4_20202396.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20202396.R;
import com.example.lab4_20202396.adapters.ForecastAdapter;
import com.example.lab4_20202396.models.ForecastModel;
import com.example.lab4_20202396.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastFragment extends Fragment implements SensorEventListener {

    private RecyclerView rvForecast;
    private ForecastAdapter adapter;
    private List<ForecastModel.ForecastDay> forecastDays;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    //Sensor
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float accelerationCurrentValue;
    private float accelerationLastValue;
    private final float SHAKE_THRESHOLD = 20.0f;

    //Búsqueda manual
    private EditText etManualId, etManualDays;
    private Button btnBuscarManual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        rvForecast = view.findViewById(R.id.rvForecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(getContext()));

        etManualId = view.findViewById(R.id.etManualId);
        etManualDays = view.findViewById(R.id.etManualDays);
        btnBuscarManual = view.findViewById(R.id.btnBuscarManual);

        // Sensor setup
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        accelerationCurrentValue = SensorManager.GRAVITY_EARTH;
        accelerationLastValue = SensorManager.GRAVITY_EARTH;

        String idLocation = getArguments() != null ? getArguments().getString("idLocation") : null;

        if (idLocation != null) {
            //Ocultar campos manuales si el ID viene desde LocationFragment
            etManualId.setVisibility(View.GONE);
            etManualDays.setVisibility(View.GONE);
            btnBuscarManual.setVisibility(View.GONE);
            getForecast(idLocation, 14);
        } else {
            //Mostrar búsqueda manual
            btnBuscarManual.setOnClickListener(v -> {
                String idInput = etManualId.getText().toString().trim();
                String daysInput = etManualDays.getText().toString().trim();

                if (idInput.isEmpty() || daysInput.isEmpty()) {
                    Toast.makeText(getContext(), "Completa ambos campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int days = Integer.parseInt(daysInput);
                if (days < 1 || days > 14) {
                    Toast.makeText(getContext(), "Días debe ser entre 1 y 14", Toast.LENGTH_SHORT).show();
                    return;
                }

                getForecast(idInput, days);
            });
        }

        return view;
    }

    private void getForecast(String idLocation, int days) {
        RetrofitClient.getApiService().getForecast(API_KEY, "id:" + idLocation, days)
                .enqueue(new Callback<ForecastModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ForecastModel> call, @NonNull Response<ForecastModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            forecastDays = response.body().forecast.forecastday;
                            adapter = new ForecastAdapter(forecastDays);
                            rvForecast.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "Error al cargar pronósticos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ForecastModel> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Fallo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Sensor logic
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerationLastValue = accelerationCurrentValue;
            accelerationCurrentValue = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = accelerationCurrentValue - accelerationLastValue;

            if (delta > SHAKE_THRESHOLD) {
                showDeleteConfirmationDialog();
            }
        }
    }

    private void showDeleteConfirmationDialog() {
        if (forecastDays == null || forecastDays.isEmpty()) return;

        new AlertDialog.Builder(getContext())
                .setTitle("¿Eliminar pronósticos?")
                .setMessage("Se detectó una sacudida. ¿Deseas eliminar la lista?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    forecastDays.clear();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
