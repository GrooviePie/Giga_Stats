package com.example.giga_stats.activityNfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterRunningWorkout;
import com.example.giga_stats.adapter.AdapterRunningWorkoutBottomSheet;

import java.util.ArrayList;
import java.util.List;

public class FragmentRunningWorkout extends Fragment {

    //TODO: Hardcoded Texte bearbeiten

    private String[] context_menu_item;

    private AppDatabase appDatabase;

    private Context context;

    private GridView gridView;

    private AdapterRunningWorkoutBottomSheet adapterBottomSheet;
    private ArrayList<String> exerciseNames;

    public FragmentRunningWorkout() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    //=====================================================LEBENSZYKLUS==========================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in RunningWorkoutsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "LIFE SETS - onCreateView() in RunningWorkoutsFragment.java aufgerufen");
        View rootView = inflater.inflate(R.layout.fragment_running_workout, container, false);

        context_menu_item = getResources().getStringArray(R.array.ContextMenuSets);
        context = getContext();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();
        gridView = rootView.findViewById(R.id.runningWorkoutGridView);

        exerciseNames = new ArrayList<>(); // Erstellen Sie eine leere Liste für die Übungsnamen
        adapterBottomSheet = new AdapterRunningWorkoutBottomSheet(context, exerciseNames);

        try {


            updateRunningWorkoutList();


        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE SETS: onResume(): Das Fragment tritt in den Vordergrund");
        updateRunningWorkoutList();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE SETS: onPause(): Das Fragment wechselt in den Hintergrund");
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }


    //=====================================================OPTIONSMENÜ==========================================================================

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in RunningWorkoutsFragment.java aufgerufen ZOOM ZOOM ZOOM");
        inflater.inflate(R.menu.menu_option_runningworkouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

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


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle("Workout beginnen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }


    //=====================================================KONTEXTMENÜ==========================================================================

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

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return false;
    }


    //=====================================================DIALOGE==========================================================================

    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Workout beginnen"
        String textContent = "Hier ist der Tutorial-Text, den du anzeigen möchtest.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Tutorial");

        // Erstellen Sie ein TextView, um den Textinhalt anzuzeigen
        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);

        // Fügen Sie das TextView zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textView);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Schließen Sie den Dialog
                dialog.dismiss();
            }
        });

        builder.create().show();
    }




    //=====================================================HILFSMETHODEN==========================================================================


    private void updateRunningWorkoutList() {
        LiveData<List<Workout>> runningWorkoutsLiveData = appDatabase.workoutDao().getAllWorkouts();

        runningWorkoutsLiveData.observe(getViewLifecycleOwner(), workoutExercises -> {
            if (workoutExercises != null) {
                Workout[] workouts = workoutExercises.toArray(new Workout[0]);

                AdapterRunningWorkout adapter = new AdapterRunningWorkout(context, workouts);
                gridView.setAdapter(adapter); // Aktualisieren Sie Ihre aktuelle GridView

                // Füllen Sie exerciseNames mit den Übungsnamen aus workouts (oder Ihren Daten)
                exerciseNames.clear();
                for (Workout workout : workouts) {
                    exerciseNames.add(workout.getName());
                }

                // Aktualisieren Sie den Adapter für das Bottom Sheet
                adapterBottomSheet.notifyDataSetChanged();
            }
        });
    }





    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }

    private boolean isValidInput(String repetitions, String weight) {
        // Hier können Sie Ihre Validierungslogik implementieren
        // Überprüfen Sie, ob die Eingaben gültig sind, z. B. ob sie numerisch sind und den Anforderungen entsprechen
        // Geben Sie true zurück, wenn die Eingaben gültig sind, andernfalls false
        // Zeigen Sie gegebenenfalls Fehlermeldungen an
        return true; // Beispiel: Immer als gültig markiert (ersetzen Sie dies mit Ihrer Validierungslogik)
    }

}