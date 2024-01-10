package com.example.giga_stats.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.giga_stats.database.manager.AppDatabase;
import com.example.giga_stats.R;

/**
 * Ein Fragment zur Anzeige von Statistiken.
 */
public class FragmentStatistics extends Fragment {
    private AppDatabase appDatabase;

    //private boolean isFragmentInitialized = false; // Flagge, um zu überprüfen, ob das Fragment bereits initialisiert wurde

    /**
     * Leerer Standardkonstruktor für das FragmentStatistics.
     */
    public FragmentStatistics() {
        // Erforderlicher leerer Standardkonstruktor
    }

    /**
     * Setzt die Referenz zur App-Datenbank für das Fragment.
     *
     * @param appDatabase Die App-Datenbankreferenz.
     */
    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    /**
     * Wird aufgerufen, wenn das Fragment erstellt wird.
     *
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHAD", "onCreate() in StatisticsFragment.java aufgerufen");
        setHasOptionsMenu(true); // Damit wird onCreateOptionsMenu() im Fragment aufgerufen
    }

    /**
     * Erstellt das Optionsmenü für das Fragment.
     *
     * @param menu     Das Optionsmenü.
     * @param inflater Der MenuInflater zum Aufblasen des Menüs.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {// Überprüfen, ob das Fragment bereits initialisiert wurde
            inflater.inflate(R.menu.menu_option_statistics, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Wird aufgerufen, wenn ein Menüelement ausgewählt wird.
     *
     * @param item Das ausgewählte Menüelement.
     * @return True, wenn die Auswahl behandelt wurde, sonst false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d("CHAD", "onOptionsItemSelected() in StatisticsFragment.java aufgerufen");

        if (itemId == R.id.option_menu_tutorial_statistics) {
            openTutorialDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Vorbereitung des Optionsmenüs.
     *
     * @param menu Das Optionsmenü.
     */
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            // Konfigurieren Sie die Toolbar nach Bedarf
            toolbar.setTitle("Statistiken"); // Setzen Sie den Titel für die Toolbar
        }
    }

    /**
     * Erstellt und gibt die Benutzeroberfläche des Fragments zurück.
     *
     * @param inflater           Der LayoutInflater zum Aufblasen des Layouts.
     * @param container          Die ViewGroup, in der das Fragment platziert wird.
     * @param savedInstanceState Die gespeicherten Daten des Fragments.
     * @return Die erstellte Benutzeroberfläche.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    /**
     * Öffnet einen Dialog mit einem Tutorial für die Statistikansicht.
     */
    private void openTutorialDialog() {
        // Der Textinhalt, den du anzeigen möchtest
        //TODO: Tutorial schreiben für Fragment "Statistics"
        String textContent = "Hier werden in Zukunft die Statistiken der Benutzer angezeigt.";

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View titleView = inflater.inflate(R.layout.dialog_title, null);
        TextView titleTextView = titleView.findViewById(R.id.dialogTitle);
        titleTextView.setText("Tutorial Statistik");
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
}
