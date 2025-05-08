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
import com.example.lab4_20202396.adapters.LocationAdapter;
import com.example.lab4_20202396.models.LocationModel;
import com.example.lab4_20202396.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {

    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        etSearch = view.findViewById(R.id.etSearchLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.rvLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchLocations(query);
            } else {
                Toast.makeText(getContext(), "Escribe una ubicación", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void searchLocations(String query) {
        RetrofitClient.getApiService().searchLocations(API_KEY, query)
                .enqueue(new Callback<List<LocationModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<LocationModel> results = response.body();
                            recyclerView.setAdapter(new LocationAdapter(results));
                        } else {
                            Toast.makeText(getContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error al buscar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}