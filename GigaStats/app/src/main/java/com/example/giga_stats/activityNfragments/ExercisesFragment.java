package com.example.giga_stats.activityNfragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.ExerciseRoomExpandableListAdapter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class ExercisesFragment extends Fragment {


    //TODO: Hardcoded Texte bearbeiten

    private ExpandableListView expandableListView;
    private String[] context_menu_item;
    int index;
    private Context context;
    private AppDatabase appDatabase;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    //=====================================================LEBENSZYKLUS==========================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE EXERCISE - onCreate() in ExerciseFragment.java aufgerufen");

        context = getContext();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();

        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        // Initialisieren Sie die, bevor Sie den Adapter setzen
        expandableListView = new ExpandableListView(getContext());

//        try {
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, context_menu_item);
//            expandableListView.setAdapter(adapter);
//        } catch (Exception e) {
//            Log.e("CHAD", "Fehler beim Erstellen des Adapters in onCreate(): " + e.getMessage());
//        }

        setHasOptionsMenu(true); // Optionsmenü erstellen
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "LIFE EXERCISE - onCreateView() in ExerciseFragment.java aufgerufen");
        View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);
        expandableListView = rootView.findViewById(R.id.expandableListView);
        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        try {
            updateExerciseList();
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
        Log.d("CHAD", "LIFE EXERCISES: onResume(): Das Fragment tritt in den Vordergrund");
        // Hier können Sie Aktualisierungen durchführen und Benutzerinteraktionen ermöglichen.
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CHAD", "LIFE EXERCISES: onPause(): Das Fragment wechselt in den Hintergrund");
        // Hier können Sie Aufgaben ausführen, wenn das Fragment in den Hintergrund wechselt.
    }

    //=====================================================OPTIONSMENÜ==========================================================================
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in ExerciseFragment.java aufgerufen JUUUUUUUUUUUUUUUHUUUUUUUUUUUU");
        inflater.inflate(R.menu.menu_option_exercises, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in ExerciseFragment.java aufgerufen");
        if (itemId == R.id.option_menu_add_exercises) {
            Log.d("CHAD", "ADD Optionsmenü in ExercisesFragment gedrückt");
            openAddExerciseDialog();
            return true;
        } else if (itemId == R.id.option_menu_tutorial_exercises) {
            Log.d("CHAD", "TUTORIAL Optionsmenü in ExercisesFragment gedrückt");
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Übungen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);

        }
    }

    //=====================================================KONTEXTMENÜ==========================================================================

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("CHAD", "onCreateContextMenu() in ExercisesFragmente aufgerufen :)");

        if (menuInfo instanceof ExpandableListView.ExpandableListContextMenuInfo) {
            ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

            index = ExpandableListView.getPackedPositionGroup(info.packedPosition);

            MenuInflater inflater = getMenuInflater();
            if (inflater != null) {
                inflater.inflate(R.menu.menu_context_exercises, menu);

                MenuItem edit_context = menu.findItem(R.id.MENU_CONTEXT_EDIT_EXERCISES);
                MenuItem delete_context = menu.findItem(R.id.MENU_CONTEXT_DELETE_EXERCISES);

                edit_context.setTitle("Übung bearbeiten");
                delete_context.setTitle("Übung löschen");
            }

        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "Item Id: " + itemId);
        int exercise_id = 0;

        if (index >= 0) {

            int id = (int) expandableListView.getExpandableListAdapter().getGroupId(index);

            AtomicReference<Exercise> selectedExercise = new AtomicReference<>();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> selectedExercise.set(appDatabase.exerciseDao().getExerciseById(id)));

            future.join();

            exercise_id = selectedExercise.get().getExercise_id();
            Log.d("CHAD", "Exercise Item mit der ID: " + exercise_id);
        }

        if (itemId == R.id.MENU_CONTEXT_EDIT_EXERCISES) {
            // Aktion für "Bearbeiten" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD", "onContextItemSelected -> Übung bearbeiten gedrückt in ExercisesFragment XOXO");

            openEditExerciseDialog(exercise_id);

            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_EXERCISES) {
            // Aktion für "Löschen" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD", "onContextItemSelected -> Übung löschen gedrückt in ExercisesFragment OXOX");

            openDeleteExerciseDialog(exercise_id);

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //=====================================================DIALOGE==========================================================================

    private void openAddExerciseDialog() {
        Log.d("CHAD", "openAddExerciseDialog() in ExercisesFragment aufgerufen");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Übung hinzufügen");

        // Erstellen Sie EditText-Felder für die Eingabe von Übungsdetails
        final EditText inputExerciseName = new EditText(requireContext());
        inputExerciseName.setHint("Übungsname");

        final EditText inputExerciseCategory = new EditText(requireContext());
        inputExerciseCategory.setHint("Kategorie");

        final EditText inputExerciseRep = new EditText(requireContext());
        inputExerciseRep.setHint("Wiederholungen");

        final EditText inputExerciseWeight = new EditText(requireContext());
        inputExerciseWeight.setHint("Gewicht");

        final EditText inputExerciseDesc = new EditText(requireContext());
        inputExerciseDesc.setHint("Kurzbeschreibung");

        // Fügen Sie die EditText-Felder zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputExerciseName);
        layout.addView(inputExerciseCategory);
        layout.addView(inputExerciseRep);
        layout.addView(inputExerciseWeight);
        layout.addView(inputExerciseDesc);
        builder.setView(layout);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            String name = inputExerciseName.getText().toString();
            String category = inputExerciseCategory.getText().toString();
            String repStr = inputExerciseRep.getText().toString();
            String weightStr = inputExerciseWeight.getText().toString();
            String desc = inputExerciseDesc.getText().toString();

            if (!name.isEmpty() && !category.isEmpty() && !repStr.isEmpty() && !weightStr.isEmpty()) {
                try {
                    int rep = Integer.parseInt(repStr);
                    int weight = Integer.parseInt(weightStr);

                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        appDatabase.exerciseDao().insertExercise(new Exercise(name, category, rep, weight, desc));
                        Log.e("CHAD", "Name: " + name + " Category: " + category + " rep: " + rep + " weight: " + weight);
                    });

                    future.thenRun(() -> {
                        dialog.dismiss();
                        updateExerciseList();
                    });
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Wiederholungen und Gewicht müssen ganze Zahlen sein.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void openEditExerciseDialog(int exercise_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Übung bearbeiten");

        // Erstellen Sie EditText-Felder für die Bearbeitung von Übungsdetails
        final EditText inputExerciseName = new EditText(requireContext());
        inputExerciseName.setHint("Übungsname");

        final EditText inputExerciseCategory = new EditText(requireContext());
        inputExerciseCategory.setHint("Kategorie");

        final EditText inputExerciseRep = new EditText(requireContext());
        inputExerciseRep.setHint("Wiederholungen");

        final EditText inputExerciseWeight = new EditText(requireContext());
        inputExerciseWeight.setHint("Gewicht");

        final EditText inputExerciseDesc = new EditText(requireContext());
        inputExerciseDesc.setHint("Kurzbeschreibung");

        // Fügen Sie die EditText-Felder zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputExerciseName);
        layout.addView(inputExerciseCategory);
        layout.addView(inputExerciseRep);
        layout.addView(inputExerciseWeight);
        layout.addView(inputExerciseDesc);
        builder.setView(layout);

        builder.setPositiveButton("Speichern", (dialog, which) -> {
            String newName = inputExerciseName.getText().toString();
            String newCategory = inputExerciseCategory.getText().toString();
            String newRepStr = inputExerciseRep.getText().toString();
            String newWeightStr = inputExerciseWeight.getText().toString();
            String newDesc = inputExerciseDesc.getText().toString();


            if (!newName.isEmpty() && !newCategory.isEmpty() && !newRepStr.isEmpty() && !newWeightStr.isEmpty()) {
                int newRep = Integer.parseInt(newRepStr);
                int newWeight = Integer.parseInt(newWeightStr);

                Exercise updatedExercise = new Exercise(newName, newCategory, newRep, newWeight, newDesc);
                updatedExercise.setExercise_id(exercise_id);

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> appDatabase.exerciseDao().updateExercise(updatedExercise));

                future.thenRun(() -> {
                    dialog.dismiss();
                    updateExerciseList();
                });
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void openDeleteExerciseDialog(int exercise_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Übung löschen");
        builder.setMessage("Möchten Sie diese Übung endgültig löschen?");

        builder.setPositiveButton("Ja", (dialog, which) -> {
            dialog.dismiss();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> appDatabase.exerciseDao().deleteExerciseById(exercise_id));

            future.thenRun(() -> updateExerciseList());
        });

        builder.setNegativeButton("Nein", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }


    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Übungen"
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

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    //=====================================================HILFSMETHODEN==========================================================================

    private void updateExerciseList() {
        LiveData<List<Exercise>> exercisesLiveData = appDatabase.exerciseDao().getAllExercises();

        exercisesLiveData.observe(requireActivity(), exercises -> {

            //ExerciseRoomAdapter adapter = new ExerciseRoomAdapter(context, R.layout.exercise_list_item, exercises);
            ExerciseRoomExpandableListAdapter adapter = new ExerciseRoomExpandableListAdapter(context, exercises);
            expandableListView.setAdapter(adapter);

        });
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }
}
