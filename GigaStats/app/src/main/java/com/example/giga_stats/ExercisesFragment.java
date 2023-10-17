package com.example.giga_stats;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.MANAGER.AppDatabase;
import com.example.giga_stats.adapter.ExerciseRoomAdapter;

import java.util.List;

public class ExercisesFragment extends Fragment {

    //TODO: Fenster für Hinzufügen einer Übung

    //TODO: Fenster für Bearbeiten einer Übung

    //TODO: Hardcoded Texte bearbeiten

    private ListView listView;

    private String[] context_menu_item;
    int index;
    private Context context;
    //private DBManager db;
    private AppDatabase appDatabase;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    ////________________LEBENSZYKLUS_____________________
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE EXERCISE - onCreate() in ExerciseFragment.java aufgerufen");
        //db = new DBManager(getContext());
        context = getContext();

        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "GS.db").fallbackToDestructiveMigration().build();

        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        // Initialisieren Sie die ListView, bevor Sie den Adapter setzen
        listView = new ListView(context);

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, context_menu_item);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Erstellen des Adapters in onCreate(): " + e.getMessage());
        }

        setHasOptionsMenu(true); // Optionsmenü erstellen
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "LIFE EXERCISE - onCreateView() in ExerciseFragment.java aufgerufen");
        View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);
        listView = rootView.findViewById(R.id.listView);
        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        try {
            //exercisesAuslesen();
            exerciseAuslesenRoom();
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }

        // Registrieren Sie den ListView für LongClick-Ereignisse
        registerForContextMenu(listView);

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

    //________________OPTIONSMENÜ_____________________
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in ExerciseFragment.java aufgerufen JUUUUUUUUUUUUUUUHUUUUUUUUUUUU");
        inflater.inflate(R.menu.menu_option_exercises, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in ExerciseFragment.java aufgerufen");
        if (itemId == R.id.option_menu_add_exercises) {
            Log.d("CHAD", "ADD Optionsmenü in ExercisesFragment gedrückt ");
            //TODO:  Aktion für "Hinzufügen" in the Toolbar innerhalb des Fragments ExerciseFragment

            return true;
        } else if (itemId == R.id.option_menu_tutorial_exercises) {
            Log.d("CHAD", "TUTORIAL Optionsmenü in ExercisesFragment gedrückt ");
            //TODO:  Aktion für "Tutorial" in the Toolbar innerhalb des Fragments ExerciseFragment

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
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Übungen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }
    //________________KONTEXTMENÜ_____________________

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("CHAD", "onCreateContextMenu() in ExercisesFragmente aufgerufen :)");

        MenuInflater inflater = getMenuInflater();
        if (inflater != null) {
            inflater.inflate(R.menu.menu_context_exercises, menu);

            index = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            MenuItem edit_context = menu.findItem(R.id.MENU_CONTEXT_EDIT_EXERCISES);
            MenuItem delete_context = menu.findItem(R.id.MENU_CONTEXT_DELETE_EXERCISES);

            edit_context.setTitle("Übung bearbeiten");
            delete_context.setTitle("Übung löschen");
        }
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        // Hier könnten Sie auch eine alternative Implementierung hinzufügen, wenn getActivity() null ist.
        return null;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.MENU_CONTEXT_EDIT_EXERCISES) {
            // Aktion für "Bearbeiten" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD","onContextItemSelected -> Übung bearbeiten gedrückt in ExercisesFragment XOXO");
            /*
            TODO: Parameter aus der App auslesen
            updateExercise(id, name, category, rep, weight);
            */
            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_EXERCISES) {
            // Aktion für "Löschen" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD","onContextItemSelected -> Übung löschen gedrückt in ExercisesFragment OXOX");
            /*
            TODO: Exercise ID auslesen
            deleteExercise();
            */
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //________________METHODEN_____________________

//    private void exercisesAuslesen() {
//        try {
//            Cursor cursor = db.selectAllExercises();
//            String[] from = new String[]{db.SPALTE_EXERCISES_IMG, db.SPALTE_EXERCISES_NAME, db.SPALTE_EXERCISES_CATEGORY, db.SPALTE_EXERCISES_REP, db.SPALTE_EXERCISES_WEIGHT};
//            int[] to = new int[]{R.id.img, R.id.name, R.id.category, R.id.rep, R.id.weight};
//
//            ExercisesAdapter adapter = new ExercisesAdapter(context, R.layout.exercises_list_layout, cursor, from, to, 0);
//            listView.setAdapter(adapter);
//        } catch (Exception e) {
//            Log.e("CHAD", "Fehler beim Auslesen der Daten : " + e.getMessage());
//        }
//    }

//    private void addExercise(String name, String category, int rep, int weight) {
//        db.insertExercise(name, category, rep, weight);
//        exercisesAuslesen();
//    }

//    private void deleteExercise(int id) {
//        db.deleteExercise(id);
//        exercisesAuslesen();
//    }

//    private void updateExercise(int id, String name, String category, int rep, int weight) {
//        db.updateExercise(id, name, category, rep, weight);
//    }

    private void exerciseAuslesenRoom() {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("CHAD","First Runnable");
                try {
                    final List<Exercise> exercises = appDatabase.exerciseDao().getAllExercises();
                    Log.d("CHAD", "Exercises: " + exercises.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("CHAD","Second Runnable");
                            ExerciseRoomAdapter adapter = new ExerciseRoomAdapter(context, R.layout.exercise_list_item, exercises);
                            listView.setAdapter(adapter);
                        }
                    });
                } catch (Exception e){
                    Log.e("CHAD", "Fehler bei dem Datanbankzugriff: " + e);
                }
            }
        });
        backgroundThread.start();
    }
}
