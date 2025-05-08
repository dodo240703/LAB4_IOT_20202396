package com.example.lab4_20202396.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4_20202396.R;
import com.example.lab4_20202396.models.LocationModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private final List<LocationModel> locations;

    public LocationAdapter(List<LocationModel> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationModel location = locations.get(position);
        holder.tvLocation.setText(location.getName() + ", " + location.getRegion() + " (" + location.getCountry() + ")");

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("idLocation", location.getId());

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.forecastFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }
    }
}
