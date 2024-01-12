package com.example.giga_stats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterRunningWorkout;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.manager.AppDatabase;

import java.util.List;

/**
 * Ein Fragment zur Anzeige und Verwaltung von laufenden Workouts.
 */
public class FragmentRunningWorkout extends Fragment {

    private String[] context_menu_item;
    private AppDatabase appDatabase;
    private Context context;
    private GridView gridView;

    /**
     * Standardkonstruktor für das FragmentRunningWorkout.
     */
    public FragmentRunningWorkout() {
    }

    /**
     * Setzt die Referenz zur App-Datenbank für das Fragment.
     *
     * @param appDatabase Die Referenz zur App-Datenbank.
     */
    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }



    //=====================================================LEBENSZYKLUS==========================================================================

    /**
     * Wird aufgerufen, wenn das Fragment erstellt wird.
     *
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in RunningWorkoutsFragment.java aufgerufen");
        setHasOptionsMenu(true);
    }

    /**
     * Wird aufgerufen, um die Benutzeroberfläche für das Fragment zu erstellen oder zu ändern.
     *
     * @param inflater           Der LayoutInflater zum Aufblasen des Layouts.
     * @param container          Die ViewGroup, in der das Fragment platziert wird.
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     * @return Die erstellte Benutzeroberfläche.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "LIFE SETS - onCreateView() in RunningWorkoutsFragment.java aufgerufen");
        View rootView = inflater.inflate(R.layout.fragment_running_workout, container, false);

        context_menu_item = getResources().getStringArray(R.array.ContextMenuSets);
        context = getContext();
        gridView = rootView.findViewById(R.id.runningWorkoutGridView);

        try {
            updateRunningWorkoutList();

        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }

        return rootView;
    }

    /**
     * Wird aufgerufen, wenn das Fragment wieder in den Vordergrund tritt.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE SETS: onResume(): Das Fragment tritt in den Vordergrund");
        updateRunningWorkoutList();
    }

    /**
     * Wird aufgerufen, wenn das Fragment in den Hintergrund wechselt.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE SETS: onPause(): Das Fragment wechselt in den Hintergrund");
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }


    //=====================================================OPTIONSMENÜ==========================================================================

    /**
     * Erstellt das Optionsmenü für das Fragment.
     *
     * @param menu     Das Menü, in dem die Elemente platziert werden sollen.
     * @param inflater Der Menüinflater, der verwendet wird, um das Menü aufzublasen.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in RunningWorkoutsFragment.java aufgerufen ZOOM ZOOM ZOOM");
        inflater.inflate(R.menu.menu_option_runningworkouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Behandelt die Auswahl von Elementen im Optionsmenü.
     *
     * @param item Das ausgewählte Menüelement.
     * @return true, wenn die Auswahl erfolgreich behandelt wurde, andernfalls false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in RunnningWorkoutsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_tutorial_runningworkouts) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Wird aufgerufen, um das Optionsmenü vorzubereiten.
     *
     * @param menu Das vorzubereitende Menü.
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle("Workout beginnen"); // Setzen Sie den Titel für die Toolbar
        }
    }



    //=====================================================KONTEXTMENÜ==========================================================================

    /**
     * Wird aufgerufen, um das Kontextmenü für ein Element im GridView zu erstellen.
     *
     * @param menu     Das zu erstellende Kontextmenü.
     * @param v        Die Ansicht, auf die das Kontextmenü angewendet wird.
     * @param menuInfo Zusätzliche Informationen über das ausgewählte Element.
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("CHAD", "onCreateContextMenu() in RunningFragmte aufgerufen :)");

        MenuInflater inflater = getMenuInflater();
        if (inflater != null) {
            inflater.inflate(R.menu.menu_context_runningworkouts, menu);

            MenuItem edit_context = menu.findItem(R.id.MENU_CONTEXT_EDIT_SET);
            MenuItem delete_context = menu.findItem(R.id.MENU_CONTEXT_DELETE_SET);

            edit_context.setTitle("Set bearbeiten");
            delete_context.setTitle("Set löschen");
        }

    }

    /**
     * Behandelt die Auswahl von Elementen im Kontextmenü.
     *
     * @param item Das ausgewählte Kontextmenüelement.
     * @return true, wenn die Auswahl erfolgreich behandelt wurde, andernfalls false.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return false;
    }



    //=====================================================DIALOGE==========================================================================

    /**
     * Öffnet einen Dialog mit einem Tutorial für das Fragment "Workout beginnen".
     */
    private void openTutorialDialog() {
      
        String textContent = "\nUm ein Workout zu starten, tippen Sie einfach auf das entsprechende Workout und anschließend auf \"Starten\". \n\nNun können Sie das Fenster vergrößern und Ihre Trainingsdaten eingeben. \n\nSie können dem Workout weitere Sätze hinzufügen, indem Sie auf den Button \"Set +\" tippen. \n\nWenn sie mit dem Workout fertig sind, tippen sie auf \"Beenden\".";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Workout starten");
        builder.setCustomTitle(titleView);

        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);
        textView.setPadding(16,16,16,16);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });

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



    //=====================================================HILFSMETHODEN==========================================================================

    /**
     * Aktualisiert die Liste der laufenden Workouts im GridView.
     */
    private void updateRunningWorkoutList() {
        LiveData<List<Workout>> runningWorkoutsLiveData = appDatabase.workoutDao().getAllWorkouts();

        runningWorkoutsLiveData.observe(getViewLifecycleOwner(), workoutsLiveData -> {
            if (workoutsLiveData != null) {
                Workout[] workoutArr = workoutsLiveData.toArray(new Workout[0]);

                AdapterRunningWorkout adapter = new AdapterRunningWorkout(context, workoutArr);
                gridView.setAdapter(adapter);
            }
        });
    }

    /**
     * Gibt den Menüinflater für das Fragment zurück.
     *
     * @return Der Menüinflater für das Fragment.
     */
    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }
}