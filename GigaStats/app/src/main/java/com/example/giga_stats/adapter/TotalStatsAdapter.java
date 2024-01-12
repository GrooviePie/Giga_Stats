package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.R;
import com.example.giga_stats.database.dto.ExerciseTotalStats;

import java.util.List;
import java.util.Locale;

/**
 * Die Klasse TotalStatsAdapter ist ein RecyclerView-Adapter, der dazu dient, die Gesamtstatistiken für einzelne Übungen in der Giga Stats-Anwendung anzuzeigen.
 *
 * Dieser Adapter ermöglicht die Anzeige von Übungsstatistiken, einschließlich des Übungsnamens, des vorherigen Durchschnitts, des aktuellen Durchschnitts und der Effizienz, in einer RecyclerView. Er implementiert die notwendigen Methoden von RecyclerView.Adapter und verwendet eine benutzerdefinierte ViewHolder-Klasse für die einzelnen Listenelemente.
 *
 * @version 1.0
 */
public class TotalStatsAdapter extends RecyclerView.Adapter<TotalStatsAdapter.StatsViewHolder> {

    private List<ExerciseTotalStats> exerciseStatsList;
    private final Context context;

    /**
     * Konstruktor für den TotalStatsAdapter.
     *
     * @param exerciseStatsList Eine Liste von ExerciseTotalStats, die die Gesamtstatistiken für einzelne Übungen enthält.
     */
    public TotalStatsAdapter(Context context, List<ExerciseTotalStats> exerciseStatsList) {
        this.context = context;
        this.exerciseStatsList = exerciseStatsList;
    }

    /**
     * Erstellt und gibt einen ViewHolder zurück, der die Layoutinflation für jedes Element in der RecyclerView handhabt.
     *
     * @param parent   Die übergeordnete Ansicht, in die die neue Ansicht eingefügt wird.
     * @param viewType Der Ansichtstyp des neuen Ansichtselements.
     * @return Ein ViewHolder, der die Ansicht für jedes Element in der RecyclerView repräsentiert.
     */
    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stats_comparison, parent, false);
        return new StatsViewHolder(view);
    }

    /**
     * Bindet die Daten an die Ansichtselemente jedes Elements in der RecyclerView.
     *
     * @param holder   Der ViewHolder, der das aktuelle Ansichtselement repräsentiert.
     * @param position Die Position des aktuellen Elements in der RecyclerView-Liste.
     */
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

        if(stats.getEfficiency() >= 0){
            holder.efficiency.setTextColor(ContextCompat.getColor(context, R.color.pastelGreen));
        }else {
            holder.efficiency.setTextColor(ContextCompat.getColor(context, R.color.softRed));
        }
    }

    /**
     * Gibt die Anzahl der Elemente in der RecyclerView zurück.
     *
     * @return Die Anzahl der Elemente in der RecyclerView.
     */
    @Override
    public int getItemCount() {
        return exerciseStatsList.size();
    }

    /**
     * Die ViewHolder-Klasse repräsentiert die einzelnen Ansichtselemente in der RecyclerView.
     */
    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseName;
        private TextView previousAverage;
        private TextView currentAverage;
        private TextView efficiency;

        /**
         * Konstruktor für den StatsViewHolder.
         *
         * @param itemView Die Ansicht, die von diesem ViewHolder repräsentiert wird.
         */
        public StatsViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.statsExerciseName);
            previousAverage = itemView.findViewById(R.id.statsPrevAverage);
            currentAverage = itemView.findViewById(R.id.statsCurrentAverage);
            efficiency = itemView.findViewById(R.id.statsEfficiencyPercent);
        }
    }
}

