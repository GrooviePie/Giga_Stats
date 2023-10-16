package com.example.giga_stats;

import android.database.Cursor;
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

public class ExercisesFragment extends Fragment {

    //TODO: Fenster für Hinzufügen einer Übung

    //TODO: Fenster für Bearbeiten einer Übung

    //TODO: Hardcoded Texte bearbeiten

    private ListView listView;

    private String[] context_menu_item;
    private DBManager db;
    int index;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    ////________________LEBENSZYKLUS_____________________
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "LIFE EXERCISE - onCreate() in ExerciseFragment.java aufgerufen");

        context_menu_item = getResources().getStringArray(R.array.ContextMenuExercises);

        // Initialisieren Sie die ListView, bevor Sie den Adapter setzen
        listView = new ListView(getContext());

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, context_menu_item);
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
            auslesen();
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
            // TODO: Implementieren Sie die Bearbeiten-Logik
            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_EXERCISES) {
            // Aktion für "Löschen" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD","onContextItemSelected -> Übung löschen gedrückt in ExercisesFragment OXOX");
            // TODO: Implementieren Sie die Löschen-Logik
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //________________METHODEN_____________________

    private void auslesen() {
        try {
            db = new DBManager(getContext());
            Cursor cursor = db.selectAllExercises();
            String[] from = new String[]{db.SPALTE_EXERCISES_IMG, db.SPALTE_EXERCISES_NAME, db.SPALTE_EXERCISES_CATEGORY};
            int[] to = new int[]{R.id.img, R.id.name, R.id.category};

            ExercisesAdapter adapter = new ExercisesAdapter(getContext(), R.layout.exercises_list_layout, cursor, from, to, 0);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Auslesen der Daten : " + e.getMessage());
        }
    }
}
