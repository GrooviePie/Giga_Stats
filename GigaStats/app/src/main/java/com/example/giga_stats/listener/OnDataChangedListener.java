package com.example.giga_stats.listener;

import com.example.giga_stats.database.dto.SetDetails;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ein Listener-Interface, das aufgerufen wird, wenn sich Daten ändern.
 */
public interface OnDataChangedListener {
    /**
     * Diese Methode wird aufgerufen, wenn sich die Daten ändern.
     *
     * @param setDetailsPerExercise Eine HashMap, die die geänderten Set-Details pro Übung enthält.
     *                              Der Schlüssel (Integer) repräsentiert die Übungs-ID,
     *                              und der Wert enthält die aktualisierten Set-Details.
     */
    void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise);

}
