package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;

public class AdapterStatistics extends RecyclerView.Adapter<AdapterStatistics.ViewHolder> {

    private Context context;
    private List<WorkoutExercises> workoutsWithExercises;
    private List<Exercise> exercises;
    private HashMap<Integer, Double> efficiencyPerExercise;
    private List<WorkoutEfficiencyPerExercise> workoutEfficiencies;
    private Workout workout;

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
        exercises = workoutsWithExercises.get(position).getExercises();
        workout = workoutsWithExercises.get(position).getWorkout();
        efficiencyPerExercise = workoutEfficiencies.get(position).getEfficiencyPerExercise();

        int strokeColor = ContextCompat.getColor(context, R.color.pastelGreen);
        String hexStrokeColor = String.format("#%06X", (0xFFFFFF & strokeColor));

        int fontColor = ContextCompat.getColor(context, R.color.textColorPrimary);
        String hexFontColor = String.format("#%06X", (0xFFFFFF & fontColor));

        List<DataEntry> dataEntries = new ArrayList<>();
        for (Exercise ex : exercises) {
            Double efficiency = efficiencyPerExercise.getOrDefault(ex.getExercise_id(), 0.0);
            dataEntries.add(new ValueDataEntry(ex.getName(), efficiency));
        }

        Set set = Set.instantiate();
        set.data(dataEntries);

        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

        Bar series = holder.cartesian.bar(seriesMapping);
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

        holder.updateChartData(dataEntries, workout.getName());
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return workoutsWithExercises.size();
    }

    public void updateData(List<WorkoutExercises> workoutsWithExercises, List<WorkoutEfficiencyPerExercise> workoutEfficiencies) {
        this.workoutsWithExercises = workoutsWithExercises;
        this.workoutEfficiencies = workoutEfficiencies;
        updateAdapter();
    }

    private void updateAdapter() {
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AnyChartView statisticsBarChart;
        Cartesian cartesian;

        public ViewHolder(View itemView) {
            super(itemView);
            statisticsBarChart = (AnyChartView) itemView.findViewById(R.id.statistics_anychart_barchart);
            initCartesian(itemView.getContext());
        }

        private void initCartesian(Context context) {
            int strokeColor = ContextCompat.getColor(context, R.color.pastelGreen);
            String hexStrokeColor = String.format("#%06X", (0xFFFFFF & strokeColor));

            int backgroundColor = ContextCompat.getColor(context, R.color.cardBackground);
            String hexBackgroundColor = String.format("#%06X", (0xFFFFFF & backgroundColor));

            int fontColor = ContextCompat.getColor(context, R.color.textColorPrimary);
            String hexFontColor = String.format("#%06X", (0xFFFFFF & fontColor));

            cartesian = AnyChart.bar();
            cartesian.animation(true);
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
        }

        public void updateChartData(List<DataEntry> dataEntries, String workoutName) {
            cartesian.data(dataEntries);
            cartesian.title(workoutName);
            statisticsBarChart.setChart(cartesian);
        }

        public void updateView(View view){
            statisticsBarChart = view.findViewById(R.id.statistics_anychart_barchart);
        }
    }
}

