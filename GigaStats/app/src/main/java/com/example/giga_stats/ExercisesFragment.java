package com.example.giga_stats;

import android.database.Cursor;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExercisesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Define constant variables for resource IDs


    private ListView listView;
    private DBManager db;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    public static ExercisesFragment newInstance(String param1, String param2) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in ExerciseFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

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
        if (itemId == R.id.option_menu_exercises_add) {
            //TODO
            // Aktion für "Hinzufügen" in der Toolbar innerhalb des Fragments ExerciseFragment
            // Implementieren Sie Ihre Aktion hier
            return true;
        } else if (itemId == R.id.option_menu_exercises_edit) {
            //TODO
            // Aktion für "Editieren" in der Toolbar innerhalb des Fragments ExerciseFragment
            // Implementieren Sie Ihre Aktion hier
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHAD", "onCreateView() in ExerciseFragment.java aufgerufen"); // Hinzugefügte Log-Ausgabe
        View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        auslesen();

        return rootView;
    }






    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Übungen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setNavigationIcon(R.drawable.bottommenu_icon_exercise_24); // Setzen Sie ein Navigations-Icon, wenn benötigt

        }
    }




    private void auslesen() {
        db = new DBManager(getContext());
        Cursor cursor = db.selectAllExercises();
        String[] from = new String[]{db.SPALTE_EXERCISES_IMG, db.SPALTE_EXERCISES_NAME, db.SPALTE_EXERCISES_CATEGORY};
        int[] to = new int[]{R.id.img, R.id.name, R.id.category};

        ExercisesAdapter adapter = new ExercisesAdapter(getContext(), R.layout.exercises_list_layout, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }


}
