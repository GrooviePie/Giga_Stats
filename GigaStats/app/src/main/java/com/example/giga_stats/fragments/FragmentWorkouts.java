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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExerciseCrossRef;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterExerciseRoomRecyclerView;
import com.example.giga_stats.adapter.AdapterWorkoutRoomExpandableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Fragment für die Verwaltung von Workouts und Übungen.
 */

public class FragmentWorkouts extends Fragment implements AdapterExerciseRoomRecyclerView.OnItemClickListener {

    private ExpandableListView expandableListView;
    private RecyclerView recyclerView;
    private String[] context_menu_item;
    int index;
    private Context context;
    private AppDatabase appDatabase;
    private List<Exercise> selectedExercises = new ArrayList<>();
    private List<Integer> alrSelectedExercises = new ArrayList<>();

    /**
     * Standardkonstruktor für das Fragment.
     */
    public FragmentWorkouts() {
    }

    /**
     * Setzt die Referenz zur App-Datenbank.
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
     * @param savedInstanceState Die zuvor gespeicherten Daten des Fragments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE WORKOUTS: onCreate() in WorkoutsFragment.java aufgerufen");

        context = getContext();
        assert context != null;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();

        context_menu_item = getResources().getStringArray(R.array.ContextMenuWorkouts);

        expandableListView = new ExpandableListView(getContext());

        setHasOptionsMenu(true);
    }

    /**
     * Erstellt die Benutzeroberfläche des Fragments.
     *
     * @param inflater           Der LayoutInflater, der verwendet wird, um XML in eine View zu konvertieren.
     * @param container          Der Container, in den die fragment_layout-View eingefügt wird.
     * @param savedInstanceState Die zuvor gespeicherten Daten des Fragments.
     * @return Die erstellte View.
     */
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

        registerForContextMenu(expandableListView);

        return rootView;
    }

    /**
     * Wird aufgerufen, wenn das Fragment wieder in den Vordergrund tritt.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE WORKOUTS: onResume(): Das Fragment tritt in den Vordergrund");
    }

    /**
     * Wird aufgerufen, wenn das Fragment pausiert wird.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE WORKOUTS: onPause(): Das Fragment wechselt in den Hintergrund");
    }



    //=====================================================OPTIONSMENÜ==========================================================================

    /**
     * Erstellt das Optionsmenü des Fragments.
     *
     * @param menu     Das Optionsmenü.
     * @param inflater Der MenuInflater, der verwendet wird, um das Menü aufzublasen.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in WorkoutsFragment.java aufgerufen YEAHYEAHYEAH");
        inflater.inflate(R.menu.menu_option_workouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Behandelt die Auswahl einer Option im Optionsmenü.
     *
     * @param item Die ausgewählte Option im Menü.
     * @return true, wenn die Auswahl erfolgreich behandelt wurde, sonst false.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in WorkoutsFragment.java aufgerufen");
        if (itemId == R.id.option_menu_add_workouts) {
            Log.d("CHAD", "ADD Optionsmenü in WorkoutsFragment gedrückt");
            openAddWorkoutDialog();
            return true;
        } else if (itemId == R.id.option_menu_tutorial_workouts) {
            Log.d("CHAD", "TUTORIAL Optionsmenü in WorkoutsFragment gedrückt");
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Vorbereitet das Optionsmenü, bevor es angezeigt wird.
     *
     * @param menu Das Optionsmenü.
     */
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Workouts");

        }
    }



    //=====================================================KONTEXTMENÜ==========================================================================

    /**
     * Erstellt das Kontextmenü, wenn es angefordert wird.
     *
     * @param menu     Das Kontextmenü.
     * @param v        Die View, die das Kontextmenü ausgelöst hat.
     * @param menuInfo Zusätzliche Informationen über das ausgelöste Kontextmenü.
     */
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

    /**
     * Behandelt die Auswahl im Kontextmenü.
     *
     * @param item Die ausgewählte Option im Kontextmenü.
     * @return true, wenn die Auswahl erfolgreich behandelt wurde, sonst false.
     */
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
            Log.d("CHAD", "onContextItemSelected -> Übung bearbeiten gedrückt in WorkoutsFragment BLUB");

            openEditWorkoutsDialog(workout_id);

            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_WORKOUTS) {
            Log.d("CHAD", "onContextItemSelected -> Übung löschen gedrückt in WorkoutsFragment BLUB");

            openDeleteWorkoutsDialog(workout_id);

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }



    //=====================================================DIALOGE==========================================================================

    /**
     * Öffnet einen Dialog zum Hinzufügen eines neuen Workouts.
     */
    private void openAddWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Workout hinzufügen");
        builder.setCustomTitle(titleView);

        View dialogView = inflater.inflate(R.layout.dialog_layout_add_workout, null);
        builder.setView(dialogView);

        EditText editTextWorkoutName = dialogView.findViewById(R.id.editTextWorkoutName);

        readExercisesToAdd(dialogView);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            String workoutName = editTextWorkoutName.getText().toString();
            if(!workoutName.isEmpty()) {
                Workout newWorkout = new Workout(workoutName);

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    int workoutId = (int) appDatabase.workoutDao().insertWorkout(newWorkout);
                    for (Exercise exercise : selectedExercises) {
                        appDatabase.exerciseDao().insertCrossRef(new WorkoutExerciseCrossRef(workoutId, exercise.getExercise_id()));
                    }
                });

                future.thenRun(() -> {
                    selectedExercises.clear();
                    updateWorkoutsList();
                    dialog.dismiss();
                });
            } else {
            Toast.makeText(requireContext(), "Bitte füllen Sie das Namensfeld aus.", Toast.LENGTH_SHORT).show();
        }
        });


        builder.setNegativeButton("Abbrechen", (dialog, which) -> {
            selectedExercises.clear();
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);
    }

    /**
     * Öffnet einen Dialog zum Bearbeiten eines vorhandenen Workouts.
     *
     * @param workoutId Die ID des zu bearbeitenden Workouts.
     */
    private void openEditWorkoutsDialog(int workoutId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Workout bearbeiten");
        builder.setCustomTitle(titleView);

        View dialogView = inflater.inflate(R.layout.dialog_layout_edit_workout, null);
        builder.setView(dialogView);

        EditText editTextWorkoutName = dialogView.findViewById(R.id.editTextWorkoutName);

        AtomicReference<Workout> selectedWorkout = new AtomicReference<>();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> selectedWorkout.set(appDatabase.workoutDao().getWorkoutById(workoutId)));

        future1.join();
        int currentWorkoutId = selectedWorkout.get().getWorkout_id();
        String currentWorkoutName = selectedWorkout.get().getName();
        editTextWorkoutName.setText(currentWorkoutName);

        addSelectedExercises(currentWorkoutId);
        readExercisesToAdd(dialogView);

        builder.setPositiveButton("Speichern", (dialog, which) -> {
            String newWorkoutName = editTextWorkoutName.getText().toString();
            Workout newWorkout = new Workout(newWorkoutName);
            newWorkout.setWorkout_id(workoutId);

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                appDatabase.workoutDao().updateWorkout(newWorkout);
                appDatabase.workoutExerciseCrossRefDao().deleteRefsById(workoutId);

                for (Exercise exercise : selectedExercises) {
                    appDatabase.exerciseDao().insertCrossRef(new WorkoutExerciseCrossRef(workoutId, exercise.getExercise_id()));
                }
            });

            future.thenRun(() -> {
                selectedExercises.clear();
                updateWorkoutsList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> {
            selectedExercises.clear();
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);
    }

    /**
     * Öffnet einen Dialog zum Löschen eines vorhandenen Workouts.
     *
     * @param workoutId Die ID des zu löschenden Workouts.
     */
    private void openDeleteWorkoutsDialog(int workoutId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Workout löschen");

        View dialogView = inflater.inflate(R.layout.dialog_layout_delete_entity, null);
        builder.setView(dialogView);
        builder.setCustomTitle(titleView);

        TextView delDialogTextView = dialogView.findViewById(R.id.delDialogTextView);
        delDialogTextView.setText("Möchten Sie das Workout wirklich löschen?");

        builder.setPositiveButton("Ja", (dialog, which) -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                appDatabase.workoutDao().deleteWorkoutById(workoutId);
                appDatabase.workoutExerciseCrossRefDao().deleteRefsById(workoutId);
            });

            future.thenRun(() -> {
                updateWorkoutsList();
                dialog.dismiss();
            });
        });

        builder.setNegativeButton("Nein", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);
    }


    /**
     * Öffnet einen Dialog für das Tutorial des Workouts.
     */
    private void openTutorialDialog() {
        String textContent = "\nUm ein Workout zu erstellen, drücken Sie den \"+\"-Button.\n \n" + "Sie können dann dem Workout einen Namen geben und selbst erstellte Übungen hinzufügen.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Workout");
        builder.setCustomTitle(titleView);

        final TextView textView = new TextView(requireContext());
        textView.setText(textContent);
        textView.setPadding(16,16,16,16);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
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
     * Aktualisiert die Liste der Workouts in der Benutzeroberfläche.
     */
    private void updateWorkoutsList() {
        LiveData<List<WorkoutExercises>> workoutWithExercisesLiveData = appDatabase.workoutExerciseCrossRefDao().getWorkoutsWithExercises();

        workoutWithExercisesLiveData.observe(requireActivity(), workoutExercises -> {
            if (workoutExercises != null) {

                AdapterWorkoutRoomExpandableList adapter = new AdapterWorkoutRoomExpandableList(context, workoutExercises);
                expandableListView.setAdapter(adapter);
            }
        });
    }

    /**
     * Liest die verfügbaren Übungen aus der Datenbank und zeigt sie im Dialog an.
     *
     * @param dialogView Die View des Dialogs.
     */
    private void readExercisesToAdd(View dialogView) {
        Log.d("CHAD", "WorkoutFragment -- readExercisesToAdd() aufgerufen");

        recyclerView = dialogView.findViewById(R.id.recyclerViewWorkoutAdd);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        appDatabase.exerciseDao().getAllExercises().observe(requireActivity(), exerciseList -> {
            if (exerciseList != null) {
                AdapterExerciseRoomRecyclerView adapter = new AdapterExerciseRoomRecyclerView(context, exerciseList, selectedExercises, this);
                recyclerView.setAdapter(adapter);
                Log.d("CHAD", "Adapter der RecyclerView gesetzt");
            }
        });
    }

    /**
     * Fügt ausgewählte Übungen einem vorhandenen Workout hinzu.
     *
     * @param workout_id Die ID des Workouts, zu dem die Übungen hinzugefügt werden sollen.
     */
    private void addSelectedExercises(int workout_id) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            WorkoutExercises workoutExercises = appDatabase.workoutDao().getExercisesForWorkout(workout_id);
            for (Exercise e : workoutExercises.getExercises()) {
                this.alrSelectedExercises.add(e.getExercise_id());
            }
        });

        future.join();
        Log.d("CHAD", "alrSelectedExercises: " + alrSelectedExercises.toString());

    }

    /**
     * Gibt den MenuInflater zurück, der zum Aufblasen des Menüs verwendet wird.
     *
     * @return Der MenuInflater des übergeordneten Activity, wenn vorhanden, ansonsten null.
     */
    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }

    /**
     * Behandelt Klick-Ereignisse auf Übungen in der RecyclerView.
     *
     * @param exercise Die ausgewählte Übung.
     */
    @Override
    public void onItemClick(Exercise exercise) {
        if (!selectedExercises.contains(exercise)) {
            selectedExercises.add(exercise);
        } else {
            selectedExercises.remove(exercise);
        }

        Log.d("CHAD", "Exercise: " + exercise.getName() + "zu selectedExercises hinzugefügt");
    }
}