package com.example.lab4_20202396.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20202396.R;
import com.example.lab4_20202396.models.ForecastModel;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private final List<ForecastModel.ForecastDay> forecastDays;

    public ForecastAdapter(List<ForecastModel.ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ForecastModel.ForecastDay item = forecastDays.get(position);
        holder.tvDate.setText(item.date);
        holder.tvTemp.setText("Máx: " + item.day.maxTempC + "°C / Mín: " + item.day.minTempC + "°C");
        holder.tvCondition.setText(item.day.condition.text);
    }

    @Override
    public int getItemCount() {
        return forecastDays.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTemp, tvCondition;

        ViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvTemp = view.findViewById(R.id.tvTemp);
            tvCondition = view.findViewById(R.id.tvCondition);
        }
    }
}
