package com.example.giga_stats.listener;

import com.example.giga_stats.database.dto.SetDetails;

import java.util.ArrayList;
import java.util.HashMap;

public interface OnDataChangedListener {
    void onDataChanged(HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise);

}
