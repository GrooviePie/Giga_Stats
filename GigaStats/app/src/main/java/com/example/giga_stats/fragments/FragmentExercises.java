package com.example.giga_stats.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.R;
import com.example.giga_stats.adapter.AdapterExerciseRoomExpandableList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class FragmentExercises extends Fragment {


    //TODO: Hardcoded Texte bearbeiten

    private ExpandableListView expandableListView;
    private String[] context_menu_item;
    int index;
    private Context context;
    private AppDatabase appDatabase;

    public FragmentExercises() {
        // Required empty public constructor
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    //=====================================================LEBENSZYKLUS==========================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE EXERCISE - onCreate() in ExerciseFragment.java aufgerufen");

        context = getContext();

        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        // Initialisieren Sie die, bevor Sie den Adapter setzen
        expandableListView = new ExpandableListView(getContext());

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


//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        Log.d("CHAD", "Item Id: " + itemId);
//
//        if (index >= 0) {
//
//            int id = (int) expandableListView.getExpandableListAdapter().getGroupId(index);
//
////            AtomicReference<Exercise> selectedExercise = new AtomicReference<>();
////            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> selectedExercise.set(appDatabase.exerciseDao().getExerciseById(id)));
////
////            future.join();
//
//            LiveData<Exercise> exerciseLiveData = appDatabase.exerciseDao().getExerciseById(id);
//
//            Observer<Exercise> exerciseObserver = new Observer<Exercise>() {
//                @Override
//                public void onChanged(Exercise existingExercise) {
//                    if (existingExercise != null) {
//                        int exercise_id = existingExercise.getExercise_id();
//                        Log.d("CHAD", "Exercise Item mit der ID: " + exercise_id);
//
//                        if (itemId == R.id.MENU_CONTEXT_EDIT_EXERCISES) {
//                            Log.d("CHAD", "onContextItemSelected -> Übung bearbeiten gedrückt");
//                            openEditExerciseDialog(exercise_id);
//                        } else if (itemId == R.id.MENU_CONTEXT_DELETE_EXERCISES) {
//                            Log.d("CHAD", "onContextItemSelected -> Übung löschen gedrückt");
//                            openDeleteExerciseDialog(exercise_id);
//                        }
//
//                        // Entfernen des Observers, um zu verhindern, dass der Dialog erneut geöffnet wird
//                        exerciseLiveData.removeObserver(this);
//                    }
//                }
//            };
//
//            // LiveData beobachten
//            exerciseLiveData.observe(this, exerciseObserver);
//            return true;
//        } else {
//            return super.onContextItemSelected(item);
//        }
//
//    }

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

        // Custom Titel aufblähen und setzen
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Übung hinzufügen");
        builder.setCustomTitle(titleView);

        // Inflate des benutzerdefinierten Layouts
        //LayoutInflater inflater2 = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout_create_exercise, null);
        builder.setView(dialogView);

        // Zugriff auf die Views
        final EditText inputExerciseName = dialogView.findViewById(R.id.inputExerciseName);
        final EditText inputExerciseRep = dialogView.findViewById(R.id.inputExerciseRep);
        final EditText inputExerciseWeight = dialogView.findViewById(R.id.inputExerciseWeight);
        final EditText inputExerciseDesc = dialogView.findViewById(R.id.inputExerciseDesc);
        final Spinner categorySpinner = dialogView.findViewById(R.id.categorySpinner);

        // Konfiguration des Spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.exercise_categories, R.layout.spinner_preview_item);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(categoryAdapter);


        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            String name = inputExerciseName.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
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

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        // Zugriff auf die Buttons und Anpassen des Stils
        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);
    }


    @SuppressLint("StaticFieldLeak")
    private void openEditExerciseDialog(int exercise_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Custom Titel aufblähen und setzen
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Übung bearbeiten");
        builder.setCustomTitle(titleView);

        // Inflate des benutzerdefinierten Layouts
        //LayoutInflater inflater2 = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout_create_exercise, null);
        builder.setView(dialogView);

        // Zugriff auf die Views
        final EditText inputExerciseName = dialogView.findViewById(R.id.inputExerciseName);
        final EditText inputExerciseRep = dialogView.findViewById(R.id.inputExerciseRep);
        final EditText inputExerciseWeight = dialogView.findViewById(R.id.inputExerciseWeight);
        final EditText inputExerciseDesc = dialogView.findViewById(R.id.inputExerciseDesc);
        final Spinner categorySpinner = dialogView.findViewById(R.id.categorySpinner);

        // Konfiguration des Spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.exercise_categories, R.layout.spinner_preview_item);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Abfrage der Übung aus der Datenbank in einem Hintergrund-Thread
        new AsyncTask<Integer, Void, Exercise>() {
            @Override
            protected Exercise doInBackground(Integer... params) {
                int exerciseId = params[0];
                return appDatabase.exerciseDao().getExerciseById(exerciseId);
            }

            @Override
            protected void onPostExecute(Exercise existingExercise) {
                if (existingExercise != null) {
                    // Setze die abgerufenen Werte in die Eingabefelder
                    inputExerciseName.setText(existingExercise.getName());
                    categorySpinner.setSelection(categoryAdapter.getPosition(existingExercise.getCategory()));

                    if (existingExercise.getRep() != 0) {
                        inputExerciseRep.setText(String.valueOf(existingExercise.getRep()));
                    }

                    if (existingExercise.getWeight() != 0) {
                        inputExerciseWeight.setText(String.valueOf(existingExercise.getWeight()));
                    }

                    inputExerciseDesc.setText(existingExercise.getDesc());
                }
            }
        }.execute(exercise_id);

//        LiveData<Exercise> exerciseLiveData = appDatabase.exerciseDao().getExerciseById(exercise_id);
//
//        // LiveData beobachten
//        exerciseLiveData.observe(this, existingExercise -> {
//            if (existingExercise != null) {
//                inputExerciseName.setText(existingExercise.getName());
//                categorySpinner.setSelection(categoryAdapter.getPosition(existingExercise.getCategory()));
//
//                if (existingExercise.getRep() != 0) {
//                    inputExerciseRep.setText(String.valueOf(existingExercise.getRep()));
//                }
//
//                if (existingExercise.getWeight() != 0) {
//                    inputExerciseWeight.setText(String.valueOf(existingExercise.getWeight()));
//                }
//
//                inputExerciseDesc.setText(existingExercise.getDesc());
//            }
//        });


        builder.setPositiveButton("Speichern", (dialog, which) -> {
            String newName = inputExerciseName.getText().toString();
            String newCategory = categorySpinner.getSelectedItem().toString();
            String newRepStr = inputExerciseRep.getText().toString();
            String newWeightStr = inputExerciseWeight.getText().toString();
            String newDesc = inputExerciseDesc.getText().toString();

            if (!newName.isEmpty() && !newCategory.isEmpty() && !newRepStr.isEmpty() && !newWeightStr.isEmpty()) {
                int newRep = Integer.parseInt(newRepStr);
                int newWeight = Integer.parseInt(newWeightStr);

                Exercise updatedExercise = new Exercise(newName, newCategory, newRep, newWeight, newDesc);
                updatedExercise.setExercise_id(exercise_id);

                // Datenbankaktualisierung kann auch in einem AsyncTask erfolgen
                new AsyncTask<Exercise, Void, Void>() {
                    @Override
                    protected Void doInBackground(Exercise... exercises) {
                        appDatabase.exerciseDao().updateExercise(exercises[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        dialog.dismiss();
                        updateExerciseList();
                    }
                }.execute(updatedExercise);
            } else {
            Toast.makeText(requireContext(), "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show();
        }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
        dialog.show();

        // Zugriff auf die Buttons und Anpassen des Stils
        int green = ContextCompat.getColor(requireContext(), R.color.pastelGreen);
        int red = ContextCompat.getColor(requireContext(), R.color.softRed);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextSize(16);
        positiveButton.setTextColor(green);

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(red);
        negativeButton.setTextSize(14);

    }


    private void openDeleteExerciseDialog(int exercise_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Übung löschen");

        View dialogView = inflater.inflate(R.layout.dialog_layout_delete_entity, null);
        builder.setView(dialogView);
        builder.setCustomTitle(titleView);

        TextView delDialogTextView = dialogView.findViewById(R.id.delDialogTextView);
        delDialogTextView.setText("Möchten Sie die Übung wirklich löschen?");

        builder.setPositiveButton("Ja", (dialog, which) -> {
            dialog.dismiss();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                boolean deletable = appDatabase.exerciseDao().canDeleteExercise(exercise_id);
                if(deletable){
                    appDatabase.exerciseDao().deleteExerciseById(exercise_id);
                } else {
                    showDeletionFailedMessage();
                }
            });

            future.thenRun(() -> updateExerciseList());
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


    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Übungen"
        String textContent = "Um eine Übung anzulegen drücken Sie den \"+\" Button. Anschließend weisen Sie der Übung eine Kategorie zu und geben Ihr einen Namen. Dann können sie die gewünschte Wiedeholungsanzahl und das Gewicht eingeben. Falls sie möchten können sie eine Kurzbeschreibung hinzufügen.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Übungen");
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

    //=====================================================HILFSMETHODEN==========================================================================

    private void updateExerciseList() {
        LiveData<List<Exercise>> exercisesLiveData = appDatabase.exerciseDao().getAllExercises();

        exercisesLiveData.observe(requireActivity(), exercises -> {

            AdapterExerciseRoomExpandableList adapter = new AdapterExerciseRoomExpandableList(context, exercises);
            expandableListView.setAdapter(adapter);

        });
    }

    private void showDeletionFailedMessage() {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), "Löschen nicht möglich: Übung gehört zu einem Workout.", Toast.LENGTH_LONG).show();
        });
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }
}
