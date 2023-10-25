package com.example.giga_stats.activityNfragments;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.giga_stats.R;

public class RunningWorkoutFragment extends Fragment {

    //TODO: Hardcoded Texte bearbeiten

    private String[] context_menu_item;

    public RunningWorkoutFragment() {
        // Required empty public constructor
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
        //expandableListView = rootView.findViewById(R.id.expandableListView);
        context_menu_item = getResources().getStringArray(R.array.ContextMenuSets);

        try {
            updateSetList();
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Lesen der Daten: " + e.getMessage());
        }

        // Registrieren Sie den ListView für LongClick-Ereignisse
        //registerForContextMenu(expandableListView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CHAD", "LIFE SETS: onResume(): Das Fragment tritt in den Vordergrund");
        // Hier können Sie Aktualisierungen durchführen und Benutzerinteraktionen ermöglichen.
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
        Log.d("CHAD", "onCreateOptionsMenu() in RunningWorkoutsFragment.java aufgerufen UTZUTZUTZUTZ");
        inflater.inflate(R.menu.menu_option_runningworkouts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in RunnningWorkoutsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_add_runningworkouts) {
            openAddSetDialog();
            return true;
        } else if (itemId == R.id.option_menu_tutorial_runningworkouts ) {
            openTutorialDialog();
            return true;
        } else return super.onOptionsItemSelected(item);
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


    private void openAddSetDialog() {
        // Erstellen Sie einen AlertDialog für das Hinzufügen eines Sets
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Set hinzufügen");

        // Hier können Sie den Dialoginhalt anpassen
        // Zum Beispiel: Textfelder, Dropdowns, usw.
        // Hier ist ein einfaches Beispiel mit einer Nachricht und Schaltflächen:

        builder.setMessage("Fügen Sie hier Ihre Set-Details hinzu:");

        // Hinzufügen von Schaltflächen für Bestätigen und Abbrechen
        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Fügen Sie hier den Code zum Hinzufügen des Sets hinzu
                // Dies wird aufgerufen, wenn der "Hinzufügen" Button gedrückt wird
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dies wird aufgerufen, wenn der "Abbrechen" Button gedrückt wird
                dialog.dismiss(); // Schließen Sie den Dialog
            }
        });

        // Den Dialog anzeigen
        builder.create().show();
    }


    private void openEditSetDialog(int set_id){
        //TODO: EDIT bauen
    }

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


    private void updateSetList() {
        //TODO: Adapter bauen
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        return null;
    }

}