package com.example.giga_stats.adapter;



import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giga_stats.DB.ENTITY.WorkoutExercises;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRunningWorkoutBottomSheet extends BaseAdapter {

    private Context context;
    private WorkoutExercises workoutWithExercises;
    private GridLayout gridLayout;
    private ArrayList<Integer> setCount = new ArrayList<>();

    public AdapterRunningWorkoutBottomSheet(Context context, WorkoutExercises workoutWithExercises) {
        this.context = context;
        this.workoutWithExercises = workoutWithExercises;
    }

    @Override
    public int getCount() {
        return workoutWithExercises.getExercises().size();
    }

    @Override
    public Object getItem(int position) {
        return workoutWithExercises.getExercises().get(position);
    }

    @Override
    public long getItemId(int position) {
        return workoutWithExercises.getWorkout().getWorkout_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bottom_sheet_list_item, null);
        }

        gridLayout = convertView.findViewById(R.id.gridLayoutBottomSheet);

        TextView nameExerciseBottomSheetTextView = convertView.findViewById(R.id.nameExerciseBottomSheet);
        nameExerciseBottomSheetTextView.setText(workoutWithExercises.getExercises().get(position).getName());

        ListView listView = convertView.findViewById(R.id.setListView);
        AdapterSets adapterSets = new AdapterSets(context, setCount);
        listView.setAdapter(adapterSets);

        Button addSetRowButton = convertView.findViewById(R.id.addNewRowButton);
        addSetRowButton.setOnClickListener(view -> {
            int size = setCount.size();
            setCount.add(size+1);
            adapterSets.notifyDataSetChanged();
        });

        return convertView;
    }
}

