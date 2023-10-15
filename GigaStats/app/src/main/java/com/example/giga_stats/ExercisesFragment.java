package com.example.giga_stats;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ExercisesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Define constant variables for resource IDs
    private static final int OPTION_MENU_EXERCISES_ADD_ID = R.id.optionmenu_exercises_add;
    private static final int OPTION_MENU_EXERCISES_EDIT_ID = R.id.optionmenu_exercises_edit;
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
        Log.d("CHAD", "onCreate() in ExerciseFragment.java aufgerufen"); // Hinzugefügte Log-Ausgabe
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Enable options menu
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_exercises_top_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
        Log.d("CHAD", "onCreateOptionsMenu() in ExerciseFragment.java aufgerufen"); // Hinzugefügte Log-Ausgabe
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in ExerciseFragment.java aufgerufen");
        if (itemId == OPTION_MENU_EXERCISES_ADD_ID) {

            // Action for "Hinzufügen"
            // Implementieren Sie Ihre Aktion hier
            return true;
        } else if (itemId == OPTION_MENU_EXERCISES_EDIT_ID) {
            // Action for "Editieren"
            // Implementieren Sie Ihre Aktion hier
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void auslesen() {
        db = new DBManager(getContext());
        Cursor cursor = db.selectAllExercises();
        String[] from = new String[]{db.SPALTE_EXERCISES_IMG, db.SPALTE_EXERCISES_NAME, db.SPALTE_EXERCISES_CATEGORY, db.SPALTE_EXERCISES_REP, db.SPALTE_EXERCISES_WEIGHT};
        int[] to = new int[]{R.id.img, R.id.name, R.id.category, R.id.rep, R.id.weight};

        ExercisesAdapter adapter = new ExercisesAdapter(getContext(), R.layout.exercises_list_layout, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }
}
