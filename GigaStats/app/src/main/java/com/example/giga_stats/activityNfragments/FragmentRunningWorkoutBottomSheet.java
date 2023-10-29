package com.example.giga_stats.activityNfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giga_stats.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentRunningWorkoutBottomSheet extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout_bottom_sheet_running_workout, container, false);
        return view;
    }
}
