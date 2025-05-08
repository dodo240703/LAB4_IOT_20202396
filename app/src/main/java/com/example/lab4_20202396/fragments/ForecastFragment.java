package com.example.lab4_20202396.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastFragment extends Fragment {

    private RecyclerView rvForecast;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        rvForecast = view.findViewById(R.id.rvForecast);
        rvForecast.setLayoutManager(new LinearLayoutManager(getContext()));

        String idLocation = getArguments() != null ? getArguments().getString("idLocation") : null;

        if (idLocation != null) {
            getForecast(idLocation);
        } else {
            Toast.makeText(getContext(), "ID no proporcionado", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void getForecast(String idLocation) {
        RetrofitClient.getApiService().getForecast("ec24b1c6dd8a4d528c1205500250305", "id:" + idLocation, 14)
                .enqueue(new Callback<ForecastModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ForecastModel> call, @NonNull Response<ForecastModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            rvForecast.setAdapter(new ForecastAdapter(response.body().forecast.forecastday));
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
}
