package com.example.lab4_20202396.fragments;

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
import com.example.lab4_20202396.adapters.SportsAdapter;
import com.example.lab4_20202396.models.SportsModel;
import com.example.lab4_20202396.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsFragment extends Fragment {

    private EditText etLocation;
    private Button btnGetSports;
    private RecyclerView rvSports;

    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);

        etLocation = view.findViewById(R.id.etLocation);
        btnGetSports = view.findViewById(R.id.btnGetSports);
        rvSports = view.findViewById(R.id.rvSports);
        rvSports.setLayoutManager(new LinearLayoutManager(getContext()));

        btnGetSports.setOnClickListener(v -> {
            String location = etLocation.getText().toString().trim();
            if (!location.isEmpty()) {
                getSports(location);
            } else {
                Toast.makeText(getContext(), "Escribe una ciudad o lugar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getSports(String location) {
        RetrofitClient.getApiService().getSports(API_KEY, location)
                .enqueue(new Callback<SportsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SportsModel> call, @NonNull Response<SportsModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            rvSports.setAdapter(new SportsAdapter(response.body().football));
                        } else {
                            Toast.makeText(getContext(), "Sin datos disponibles", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SportsModel> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}