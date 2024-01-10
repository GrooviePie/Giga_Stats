package com.example.giga_stats.adapter;

import static java.security.AccessController.getContext;

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
import com.example.giga_stats.database.dto.SetAverage;
import com.example.giga_stats.database.dto.WorkoutAveragesPerExercise;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_statistics_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        exercises = workoutsWithExercises.get(position).getExercises();
        workout = workoutsWithExercises.get(position).getWorkout();
        efficiencyPerExercise = workoutEfficiencies.get(position).getEfficiencyPerExercise();

        int strokeColor = ContextCompat.getColor(context, R.color.pastelGreen);
        String hexStrokeColor = String.format("#%06X", (0xFFFFFF & strokeColor));

        int backgroundColor = ContextCompat.getColor(context, R.color.cardBackground);
        String hexBackgroundColor = String.format("#%06X", (0xFFFFFF & backgroundColor));

        int fontColor = ContextCompat.getColor(context, R.color.textColorPrimary);
        String hexFontColor = String.format("#%06X", (0xFFFFFF & fontColor));



        Cartesian cartesian = AnyChart.bar();

        cartesian.animation(true);
        cartesian.title(workout.getName());
        cartesian.title().fontColor(hexFontColor);
        cartesian.background(hexBackgroundColor);

        List<DataEntry> dataEntries = new ArrayList<>();
        for (Exercise ex : exercises) {
            dataEntries.add(new ValueDataEntry(ex.getName(), efficiencyPerExercise.get(ex.getExercise_id())));
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

        holder.statisticsBarChart.setChart(cartesian);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return workoutsWithExercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AnyChartView statisticsBarChart;

        public ViewHolder(View itemView) {
            super(itemView);
            statisticsBarChart = (AnyChartView) itemView.findViewById(R.id.statistics_anychart_barchart);
        }
    }
}

