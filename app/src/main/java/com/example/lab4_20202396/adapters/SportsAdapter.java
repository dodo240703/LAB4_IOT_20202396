package com.example.lab4_20202396.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20202396.R;
import com.example.lab4_20202396.models.SportsModel;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

    private final List<SportsModel.Match> matches;

    public SportsAdapter(List<SportsModel.Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sports, parent, false);
        return new SportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
        SportsModel.Match match = matches.get(position);
        holder.tvTeams.setText(match.team1 + " vs " + match.team2);
        holder.tvTournament.setText("Torneo: " + match.tournament);
        holder.tvStadium.setText("Estadio: " + match.stadium);
        holder.tvDate.setText("Inicio: " + match.start);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class SportsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeams, tvTournament, tvStadium, tvDate;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeams = itemView.findViewById(R.id.tvTeams);
            tvTournament = itemView.findViewById(R.id.tvTournament);
            tvStadium = itemView.findViewById(R.id.tvStadium);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
