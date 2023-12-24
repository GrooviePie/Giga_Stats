package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.DB.DTO.SetDetails;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterSets extends RecyclerView.Adapter<AdapterSets.ViewHolder> {

    Context context;
    ArrayList<Integer> setCount;
    ArrayList<SetDetails> setDetails;
    HashMap<Integer, ArrayList<Integer>> setsPerExercise;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;

    public AdapterSets(Context context, HashMap<Integer, ArrayList<Integer>> setsPerExercise, HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise, int pos){
        this.context = context;
        this.setsPerExercise = setsPerExercise;
        this.setCount = setsPerExercise.get(pos);
        this.setDetailsPerExercise = setDetailsPerExercise;
        this.setDetails = setDetailsPerExercise.get(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_inner_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SetDetails setDetails = this.setDetails.get(position);

        holder.setCount.setText(String.valueOf(position + 1));
        holder.weightText.setText(String.valueOf(setDetails.getWeight()));
        holder.repText.setText(String.valueOf(setDetails.getReps()));

        holder.setsCheckBox.setOnCheckedChangeListener((view, isChecked) -> {
            if(isChecked){
                this.setDetails.get(position).setWeight(Integer.parseInt(holder.weightText.getText().toString()));
                this.setDetails.get(position).setReps(Integer.parseInt(holder.repText.getText().toString()));
            } else {
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return setCount.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView setCount;
        TextView weightText;
        TextView repText;
        CheckBox setsCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            setCount = itemView.findViewById(R.id.setCount);
            weightText = itemView.findViewById(R.id.weightText);
            repText = itemView.findViewById(R.id.repText);
            setsCheckBox = itemView.findViewById(R.id.checkbox1);
        }
    }
}
