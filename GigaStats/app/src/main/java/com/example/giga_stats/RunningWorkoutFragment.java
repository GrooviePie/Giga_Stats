package com.example.giga_stats;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunningWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunningWorkoutFragment extends Fragment {

    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RunningWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RunningWorkoutFragment.
     */
    //  Rename and change types and number of parameters
    public static RunningWorkoutFragment newInstance(String param1, String param2) {
        RunningWorkoutFragment fragment = new RunningWorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in RunningWorkoutsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("CHAD", "onCreateOptionsMenu() in RunningWorkoutsFragment.java aufgerufen UTZUTZUTZUTZ");
        inflater.inflate(R.menu.menu_option_runningworkouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in RunnningWorkoutsFragment.java aufgerufen");
        if (itemId == R.id.option_menu_tutorial_runningworkouts) {
            //TODO
            // Tutorial für RunningWorkoutsFragment erstellen
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Workouts beginnen"); // Setzen Sie den Titel für die Toolbar
            toolbar.setNavigationIcon(R.drawable.bottommenu_icon_start_24); // Setzen Sie ein Navigations-Icon, wenn benötigt

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_running_workout, container, false);
    }
}