package com.example.giga_stats;

import com.example.giga_stats.DB.DTO.SetData;
import com.example.giga_stats.DB.DTO.SetDetails;

import java.util.ArrayList;
import java.util.HashMap;

public interface OnDataChangedListener {
    void onDataChanged(ArrayList<SetData> setDataPerWorkout);
}
