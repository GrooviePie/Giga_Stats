package com.example.giga_stats.activityNfragments;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.ENTITY.WorkoutExerciseSetCrossRef;
import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.ExerciseRoomRecyclerViewAdapter;
import com.example.giga_stats.adapter.WorkoutRoomExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class WorkoutsFragment extends Fragment implements ExerciseRoomRecyclerViewAdapter.OnItemClickListener {

    //TODO: Fenster für Hinzufügen eines Workouts
    //TODO: Fenster für Bearbeiten eines Workouts

    //TODO: Hardcoded Texte bearbeiten

    private ExpandableListView expandableListView;
    private RecyclerView recyclerView;
    private String[] context_menu_item;
    int index;
    private Context context;
    private AppDatabase appDatabase;
    private List<Exercise> selectedAddExercises = new ArrayList<>();

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    //=====================================================LEBENSZYKLUS==========================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE WORKOUTS: onCreate() in WorkoutsFragment.java aufgerufen");

        context = getContext();
        assert context != null;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();

        context_menu_item = getResources().getStringArray(R.array.ContextMenuWorkouts);

        // Initialisieren Sie die, bevor Sie den Adapter setzen
        expandableListView = new ExpandableListView(getContext());

        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "LIFE WORKOUTS - onCreateView() in WorkoutsFragment.java aufgerufen");
        View rootView = inflater.inflate(R.layout.fragment_workouts, container, false);
        expandableListView = rootView.findViewById(R.id.expandableListViewWorkouts);
        context_menu_item = getResources().getStringArray(R.array.ContextMenuWorkouts);

        try {
            updateWorkoutsList();
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }

        // Registrieren Sie den ListView für LongClick-Ereignisse
        registerForContextMenu(expandableListView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE WORKOUTS: onResume(): Das Fragment tritt in den Vordergrund");
        // Hier können Sie Aktualisierungen durchführen und Benutzerinteraktionen ermöglichen.
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE WORKOUTS: onPause(): Das Fragment wechselt in den Hintergrund");
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }

    //=====================================================OPTIONSMENÜ==========================================================================
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in WorkoutsFragment.java aufgerufen YEAHYEAHYEAH");
        inflater.inflate(R.menu.menu_option_workouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in WorkoutsFragment.java aufgerufen");
        if (itemId == R.id.option_menu_add_workouts) {
            Log.d("CHAD", "ADD Optionsmenü in WorkoutsFragment gedrückt");
            openAddWorkoutDialog();
            return true;
        } else if (itemId == R.id.option_menu_tutorial_workouts) {
            Log.d("CHAD", "TUTORIAL Optionsmenü in WorkoutsFragment gedrückt");
            openTutorialDialog();//Tutorialdialog wird geöffnet
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Workouts"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);

        }
    }

    //=====================================================KONTEXTMENÜ==========================================================================

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("CHAD", "onCreateContextMenu() in WorkoutsFragment aufgerufen");

        if (menuInfo instanceof ExpandableListView.ExpandableListContextMenuInfo) {
            ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

            index = ExpandableListView.getPackedPositionGroup(info.packedPosition);

            MenuInflater inflater = getMenuInflater();
            if (inflater != null) {
                inflater.inflate(R.menu.menu_context_workouts, menu);

                MenuItem edit_context = menu.findItem(R.id.MENU_CONTEXT_EDIT_WORKOUTS);
                MenuItem delete_context = menu.findItem(R.id.MENU_CONTEXT_DELETE_WORKOUTS);

                edit_context.setTitle("Workout bearbeiten");
                delete_context.setTitle("Workout löschen");
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "Item Id: " + itemId);
        int workout_id = 0;

        if (index >= 0) {

            int id = (int) expandableListView.getExpandableListAdapter().getGroupId(index);

            AtomicReference<Workout> selectedWorkout = new AtomicReference<>();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> selectedWorkout.set(appDatabase.workoutDao().getWorkoutById(id)));

            future.join();

            workout_id = selectedWorkout.get().getWorkout_id();
            Log.d("CHAD", "Workout Item mit der ID: " + workout_id);
        }

        if (itemId == R.id.MENU_CONTEXT_EDIT_WORKOUTS) {
            // Aktion für "Bearbeiten" im Kontextmenü innerhalb des Fragments WorkoutsFragment
            Log.d("CHAD", "onContextItemSelected -> Übung bearbeiten gedrückt in WorkoutsFragment BLUB");

            openEditWorkoutsDialog(workout_id);

            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_WORKOUTS) {
            // Aktion für "Löschen" im Kontextmenü innerhalb des Fragments WorkoutsFragment
            Log.d("CHAD", "onContextItemSelected -> Übung löschen gedrückt in WorkoutsFragment BLUB");

            openDeleteWorkoutsDialog(workout_id);

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //=====================================================DIALOGE==========================================================================

    private void openAddWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Workout");

        View dialogView = getLayoutInflater().inflate(R.layout.add_workout_dialog_layout, null);
        builder.setView(dialogView);

        EditText editTextWorkoutName = dialogView.findViewById(R.id.editTextWorkoutName);

        readExercisesToAdd(dialogView);

        builder.setPositiveButton("Save Workout", (dialog, which) -> {
            String workoutName = editTextWorkoutName.getText().toString();
            Workout newWorkout = new Workout(workoutName);

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                int workoutId = (int) appDatabase.workoutDao().insertWorkout(newWorkout);
                for(Exercise e : selectedAddExercises){
                    appDatabase.exerciseDao().insertCrossRef(new WorkoutExerciseSetCrossRef(workoutId, e.getExercise_id()));
                }
            });

            future.thenRun(()-> {
                selectedAddExercises.clear();
                updateWorkoutsList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            selectedAddExercises.clear();
            dialog.dismiss();
        });

        builder.create().show();
    }


    private void openEditWorkoutsDialog(int workoutId) {
        //TODO: Edit Dialog Logik schreiben
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Workout");
        View dialogView = getLayoutInflater().inflate(R.layout.edit_workout_dialog_layout, null);
        builder.setView(dialogView);

        EditText editTextWorkoutName = dialogView.findViewById(R.id.editTextWorkoutName);

        readExercisesToAdd(dialogView);

        builder.setPositiveButton("Save Workout", (dialog, which) -> {
            String newWorkoutName = editTextWorkoutName.getText().toString();
            Workout newWorkout = new Workout(newWorkoutName);
            newWorkout.setWorkout_id(workoutId);

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                appDatabase.workoutDao().updateWorkout(newWorkout);
                appDatabase.workoutExerciseCrossRefDao().deleteRefsById(workoutId);

                for(Exercise e : selectedAddExercises){
                    appDatabase.exerciseDao().insertCrossRef(new WorkoutExerciseSetCrossRef(workoutId, e.getExercise_id()));
                }
            });

            future.thenRun(()-> {
                selectedAddExercises.clear();
                updateWorkoutsList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            selectedAddExercises.clear();
            dialog.dismiss();
        });

        builder.create().show();
    }

    private void openDeleteWorkoutsDialog(int workoutId) {
        //TODO: Delete Dialog schreiben
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Workout");
        builder.setMessage("Do you really want to delete the workout?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                appDatabase.workoutDao().deleteWorkoutById(workoutId);
                appDatabase.workoutExerciseCrossRefDao().deleteRefsById(workoutId);
            });

            future.thenRun(() -> {
                updateWorkoutsList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }


    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Workouts"
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

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Schließen Sie den Dialog
            dialog.dismiss();
        });

        builder.create().show();
    }

    //=====================================================HILFSMETHODEN==========================================================================

    private void updateWorkoutsList() {
        LiveData<List<WorkoutExercises>> workoutWithExercisesLiveData = appDatabase.workoutExerciseCrossRefDao().getWorkoutsWithExercises();

        workoutWithExercisesLiveData.observe(requireActivity(), workoutExercises -> {
            if (workoutExercises != null) {

                WorkoutRoomExpandableListAdapter adapter = new WorkoutRoomExpandableListAdapter(context, workoutExercises);
                expandableListView.setAdapter(adapter);
            }
        });
    }

    private  void readExercisesToAdd(View dialogView){
        Log.d("CHAD","WorkoutFragment -- readExercisesToAdd() aufgerufen");

        recyclerView = dialogView.findViewById(R.id.recyclerViewWorkoutAdd);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        List<Exercise> exercises = new ArrayList<>();

        appDatabase.exerciseDao().getAllExercises().observe(requireActivity(), exerciseList -> {
            if (exerciseList != null) {
                for(Exercise e : exerciseList) {
                    exercises.add(e);
                }
                Log.d("CHAD", "Exercises: " + exercises);
                ExerciseRoomRecyclerViewAdapter adapter = new ExerciseRoomRecyclerViewAdapter(context, exercises, this);
                recyclerView.setAdapter(adapter);
                Log.d("CHAD", "Adapter der RecyclerView gesetzt");
            }
        });
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }

    @Override
    public void onItemClick(Exercise exercise) {
        selectedAddExercises.add(exercise);
        Log.d("CHAD", "Exercise: " + exercise.getName().toString() + "zu selectedExercises hinzugefügt");
    }
}