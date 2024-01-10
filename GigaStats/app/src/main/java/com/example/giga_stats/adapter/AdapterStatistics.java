package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.APIlib;
import com.anychart.AnyChartView;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.giga_stats.R;
import com.example.giga_stats.database.dto.WorkoutEfficiencyPerExercise;
import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;

import java.util.ArrayList;
import java.util.List;

/**
 * AdapterStatistics ist ein RecyclerView-Adapter, der für die Darstellung von Workout-Statistiken
 * zuständig ist, einschließlich Übungen und deren Effizienz. Dieser Adapter verwendet AnyChart,
 * um Daten in Form von Balkendiagrammen zu visualisieren.
 */
public class AdapterStatistics extends RecyclerView.Adapter<AdapterStatistics.ViewHolder> {

    private final Context context;
    private final List<WorkoutExercises> workoutsWithExercises;
    private final List<WorkoutEfficiencyPerExercise> workoutEfficiencies;

    /**
     * Konstruktor für die AdapterStatistics-Klasse.
     *
     * @param context               Der Kontext der Anwendung.
     * @param workoutsWithExercises Eine Liste von WorkoutExercises, die die Workouts und deren zugehörige Übungen darstellen.
     * @param workoutEfficiencies   Eine Liste von WorkoutEfficiencyPerExercise, die die Effizienz jeder Übung darstellt.
     */
    public AdapterStatistics(Context context, List<WorkoutExercises> workoutsWithExercises, List<WorkoutEfficiencyPerExercise> workoutEfficiencies){
        this.context = context;
        this.workoutsWithExercises = workoutsWithExercises;
        this.workoutEfficiencies = workoutEfficiencies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_statistics_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workoutsWithExercises.get(position).getWorkout();

        WorkoutExercises workoutExercises = workoutsWithExercises.get(position);
        WorkoutEfficiencyPerExercise workoutEfficiency = workoutEfficiencies.get(position);

        holder.bindData(workoutExercises, workoutEfficiency, context, workout.getName());
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return workoutsWithExercises.size();
    }

    /**
     * ViewHolder-Klasse für AdapterStatistics. Verwaltet die Darstellung eines einzelnen Elements in der RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AnyChartView statisticsBarChart;

        /**
         * Konstruktor für ViewHolder.
         *
         * @param itemView Die Ansicht des einzelnen Elements in der RecyclerView.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            statisticsBarChart = itemView.findViewById(R.id.statistics_anychart_barchart);
        }

        /**
         * Bindet Daten an den ViewHolder, um die Workout-Statistiken für jede Übung anzuzeigen.
         *
         * @param workoutExercises    Das WorkoutExercises-Objekt, das die Übungen für ein bestimmtes Workout enthält.
         * @param workoutEfficiency   Das WorkoutEfficiencyPerExercise-Objekt, das die Effizienzinformationen für jede Übung enthält.
         * @param context             Der Kontext der Anwendung.
         * @param workoutName         Der Name des Workouts.
         */
        public void bindData(WorkoutExercises workoutExercises, WorkoutEfficiencyPerExercise workoutEfficiency, Context context, String workoutName) {
            int strokeColor = ContextCompat.getColor(context, R.color.pastelGreen);
            String hexStrokeColor = String.format("#%06X", (0xFFFFFF & strokeColor));

            int backgroundColor = ContextCompat.getColor(context, R.color.cardBackground);
            String hexBackgroundColor = String.format("#%06X", (0xFFFFFF & backgroundColor));

            int fontColor = ContextCompat.getColor(context, R.color.textColorPrimary);
            String hexFontColor = String.format("#%06X", (0xFFFFFF & fontColor));

            APIlib.getInstance().setActiveAnyChartView(statisticsBarChart);
            Cartesian cartesian = AnyChart.bar();

            cartesian.title().fontColor(hexFontColor);
            cartesian.background(hexBackgroundColor);
            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);
            cartesian.xAxis(0).title("Übungen");
            cartesian.yAxis(0).title("Effizienz");
            cartesian.xAxis(0).title().fontColor(hexStrokeColor);
            cartesian.yAxis(0).title().fontColor(hexStrokeColor);
            cartesian.xAxis(0).labels().fontColor(hexFontColor);
            cartesian.yAxis(0).labels().fontColor(hexFontColor);
            cartesian.yScale().minimum(0);
            cartesian.yScale().maximum(100);
            cartesian.title(workoutName);

            List<DataEntry> dataEntries = new ArrayList<>();
            for (Exercise ex : workoutExercises.getExercises()) {
                Double efficiency = workoutEfficiency.getEfficiencyPerExercise().get(ex.getExercise_id());
                if (efficiency != null) {
                    dataEntries.add(new ValueDataEntry(ex.getName(), efficiency));
                }
            }

            Set set = Set.instantiate();
            set.data(dataEntries);
            Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

            Bar series = cartesian.bar(seriesMapping);
            series.name("Effizienz");
            series.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);

            series.labels(true);
            series.labels().position("center")
                    .fontColor(hexFontColor)
                    .format("{%Value}{decimalsCount:2}");

            series.fill("transparent");
            series.stroke(hexStrokeColor, 1, "solid", "round", "round");

            statisticsBarChart.setChart(cartesian);
        }
    }
}

