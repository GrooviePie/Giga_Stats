package com.example.giga_stats.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.SolidFill;
import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentStatistics extends Fragment {
    private AppDatabase appDatabase;

    //private boolean isFragmentInitialized = false; // Flagge, um zu überprüfen, ob das Fragment bereits initialisiert wurde

    public FragmentStatistics() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in StatisticsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {// Überprüfen, ob das Fragment bereits initialisiert wurde
        inflater.inflate(R.menu.menu_option_statistics, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in StatisticsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_tutorial_statistics) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Statistiken"); // Setzen Sie den Titel für die Toolbar
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.statistics_anychart_barchart);
        Cartesian cartesian = AnyChart.bar();

        cartesian.animation(true);
        cartesian.title("Workoutname");
        cartesian.title().fontColor("white");
        cartesian.background("#313233");

        List<DataEntry> dataEntries = new ArrayList<>();
        dataEntries.add(new ValueDataEntry("Übung 1", 99.12));
        dataEntries.add(new ValueDataEntry("Übung 2", 34.76));
        dataEntries.add(new ValueDataEntry("Übung 3", 52.67));

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
                .fontColor("#FFFFFF")
                .format("{%Value}");

        series.fill("transparent");
        series.stroke("#8AA6A3", 1, "solid", "round", "round");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Übungen");
        cartesian.yAxis(0).title("Effizienz");
        cartesian.xAxis(0).title().fontColor("#8AA6A3");
        cartesian.yAxis(0).title().fontColor("#8AA6A3");
        cartesian.xAxis(0).labels().fontColor("#EBEBEB");
        cartesian.yAxis(0).labels().fontColor("#EBEBEB");
        cartesian.yScale().maximum(100);

        anyChartView.setChart(cartesian);

        return view;
    }

    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Statistics"
        String textContent = "Hier werden in Zukunft die Statistiken der Benutzer angezeigt.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Statistik");
        builder.setCustomTitle(titleView);

        // Erstellen Sie ein TextView, um den Textinhalt anzuzeigen
        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        // Fügen Sie das TextView zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);
    }
}
