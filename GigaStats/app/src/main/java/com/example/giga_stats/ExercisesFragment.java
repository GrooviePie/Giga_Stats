package com.example.giga_stats;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class ExercisesFragment extends Fragment {


    //TODO: Fenster für Bearbeiten einer Übung

    //TODO: Hardcoded Texte bearbeiten

    private ListView listView;

    private String[] context_menu_item;
    private DBManager db;
    int index;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    //=====================================================LEBENSZYKLUS==========================================================================
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

    //=====================================================OPTIONSMENÜ==========================================================================
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
    public void onPrepareOptionsMenu(Menu menu) {
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.MENU_CONTEXT_EDIT_EXERCISES) {
            // Aktion für "Bearbeiten" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD","onContextItemSelected -> Übung bearbeiten gedrückt in ExercisesFragment XOXO");

            openEditExerciseDialog();

            return true;
        } else if (itemId == R.id.MENU_CONTEXT_DELETE_EXERCISES) {
            // Aktion für "Löschen" im Kontextmenü innerhalb des Fragments ExerciseFragment
            Log.d("CHAD","onContextItemSelected -> Übung löschen gedrückt in ExercisesFragment OXOX");

            openDeleteExerciseDialog();

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //=====================================================DIALOGE==========================================================================

    //TODO -> Anpassen nach Jens DB Erweiterung
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

        // Fügen Sie die EditText-Felder zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputExerciseName);
        layout.addView(inputExerciseCategory);
        layout.addView(inputExerciseRep);
        layout.addView(inputExerciseWeight);
        builder.setView(layout);

        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Datentypen anpassen damit richtig in der DB gespeichert
                String name = inputExerciseName.getText().toString();
                String category = inputExerciseCategory.getText().toString();
                String repStr = inputExerciseRep.getText().toString();
                String weightStr = inputExerciseWeight.getText().toString();

                if (!name.isEmpty() && !category.isEmpty() && !repStr.isEmpty() && !weightStr.isEmpty()) {
                    int rep = Integer.parseInt(repStr);
                    int weight = Integer.parseInt(weightStr);

                    //TODO: Kommentar aufheben um das einfügen in DB ermöglichen
                    //db.insertExercise(name, category, repStr, weightStr);

                    // Aktualisieren Sie den Cursor, um die Daten aus der Datenbank abzurufen
                    Cursor updatedCursor = db.selectAllExercises();
                    ExercisesAdapter adapter = (ExercisesAdapter) listView.getAdapter();
                    adapter.changeCursor(updatedCursor);
                    adapter.notifyDataSetChanged();

                    // Schließen Sie den Dialog
                    dialog.dismiss();
                } else {
                    // Behandeln Sie den Fall, in dem die Eingaben leer sind oder nicht gültig sind
                    // Zeigen Sie eine Nachricht an oder ergreifen Sie andere Maßnahmen
                }
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Schließen Sie den Dialog
                dialog.dismiss();
            }
        });

        builder.create().show();
    }




    //TODO: Anpassen, genau wie oben :)
    private void openEditExerciseDialog() {
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

        // Fügen Sie die EditText-Felder zum Dialog hinzu
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputExerciseName);
        layout.addView(inputExerciseCategory);
        layout.addView(inputExerciseRep);
        layout.addView(inputExerciseWeight);
        builder.setView(layout);

        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = inputExerciseName.getText().toString();
                String newCategory = inputExerciseCategory.getText().toString();
                String newRepStr = inputExerciseRep.getText().toString();
                String newWeightStr = inputExerciseWeight.getText().toString();

                if (!newName.isEmpty() && !newCategory.isEmpty() && !newRepStr.isEmpty() && !newWeightStr.isEmpty()) {
                    int newRep = Integer.parseInt(newRepStr);
                    int newWeight = Integer.parseInt(newWeightStr);

                    //TODO: Kommentar anpassen
                    //int exerciseId = 1; // Hier sollte die tatsächliche Übungs-ID stehen
                    //db.updateExercise(exerciseId, newName, newCategory, newRepStr, newWeightStr);

                    // Aktualisieren Sie die Ansicht oder den Adapter, um die Änderungen widerzuspiegeln

                    dialog.dismiss();
                } else {
                    // Behandeln Sie den Fall, in dem die Eingaben leer sind oder nicht gültig sind
                    // Zeigen Sie eine Nachricht an oder ergreifen Sie andere Maßnahmen
                }
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Schließen Sie den Dialog
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    private void openDeleteExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Übung löschen");
        builder.setMessage("Möchten Sie diese Übung endgültig löschen?");

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Logik für Löschen implementieren
                // Fügen Sie hier die Logik zum Löschen der Übung ein
                // Sie müssen die zu löschende Übung aus der Datenbank anhand der Auswahl des Benutzers ermitteln
                // Beachten Sie, dass Sie die Übungsdaten in der Methode auslesen müssen
                // und sicherstellen, dass Sie die richtige Übung löschen.

                // Beispiel:
                // int exerciseId = 1; // Hier sollte die tatsächliche Übungs-ID stehen
                // db.deleteExercise(exerciseId);

                // Aktualisieren Sie die Ansicht oder den Adapter, um die Änderungen widerzuspiegeln

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }









    //=====================================================HILFSMETHODEN==========================================================================

    private void auslesen() {
        try {
            db = new DBManager(getContext());
            Cursor cursor = db.selectAllExercises();
            String[] from = new String[]{db.SPALTE_EXERCISES_IMG, db.SPALTE_EXERCISES_NAME, db.SPALTE_EXERCISES_CATEGORY, db.SPALTE_EXERCISES_REP, db.SPALTE_EXERCISES_WEIGHT};
            int[] to = new int[]{R.id.img, R.id.name, R.id.category, R.id.rep, R.id.weight};

            ExercisesAdapter adapter = new ExercisesAdapter(getContext(), R.layout.exercises_list_layout, cursor, from, to, 0);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("CHAD", "Fehler beim Auslesen der Daten : " + e.getMessage());
        }
    }

    private MenuInflater getMenuInflater() {
        if (getActivity() != null) {
            return getActivity().getMenuInflater();
        }
        // Hier könnten Sie auch eine alternative Implementierung hinzufügen, wenn getActivity() null ist.
        return null;
    }
}
