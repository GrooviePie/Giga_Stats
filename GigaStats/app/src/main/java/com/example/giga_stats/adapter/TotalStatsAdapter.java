package com.example.giga_stats.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.DTO.ExerciseTotalStats;
import com.example.giga_stats.R;

import java.util.List;
import java.util.Locale;

public class TotalStatsAdapter extends RecyclerView.Adapter<TotalStatsAdapter.StatsViewHolder> {

    private List<ExerciseTotalStats> exerciseStatsList;

    public TotalStatsAdapter(List<ExerciseTotalStats> exerciseStatsList) {
        this.exerciseStatsList = exerciseStatsList;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_comparison_list_item, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        ExerciseTotalStats stats = exerciseStatsList.get(position);

        holder.exerciseName.setText(stats.getExerciseName());
        holder.previousAverage.setText(String.format(Locale.getDefault(), "%.2f kg, %.2fx",
                stats.getPreviousAverage().getAverageWeight(),
                stats.getPreviousAverage().getAverageReps()));
        holder.currentAverage.setText(String.format(Locale.getDefault(), "%.2f kg, %.2fx",
                stats.getCurrentAverage().getAverageWeight(),
                stats.getCurrentAverage().getAverageReps()));
        holder.efficiency.setText(String.format(Locale.getDefault(), "%.2f%%", stats.getEfficiency()));
    }

    @Override
    public int getItemCount() {
        return exerciseStatsList.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseName;
        private TextView previousAverage;
        private TextView currentAverage;
        private TextView efficiency;

        public StatsViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.statsExerciseName);
            previousAverage = itemView.findViewById(R.id.statsPrevAverage);
            currentAverage = itemView.findViewById(R.id.statsCurrentAverage);
            efficiency = itemView.findViewById(R.id.statsEfficiencyPercent);
        }
    }
}

