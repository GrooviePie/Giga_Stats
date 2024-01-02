package com.example.giga_stats.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giga_stats.database.dto.SetAverage;
import com.example.giga_stats.database.dto.SetDetails;
import com.example.giga_stats.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterSets extends RecyclerView.Adapter<AdapterSets.ViewHolder> {

    private Context context;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;
    private ArrayList<SetDetails> setDetails;
    private HashMap<Integer, SetAverage> setAveragePerExercise;
    private SetAverage setAverage;


    public AdapterSets(Context context, HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise, HashMap<Integer, SetAverage> setAveragePerExercise, int exercise_id){
        this.context = context;
        this.setDetailsPerExercise = setDetailsPerExercise;
        this.setDetails = setDetailsPerExercise.get(exercise_id);
        this.setAveragePerExercise = setAveragePerExercise;
        this.setAverage = setAveragePerExercise.get(exercise_id);
        notifyDataSetChanged();
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
        SetAverage setAverage1 = this.setAverage;
        holder.getSetCount().setText(String.valueOf(position + 1));
        holder.getWeightText().setText(String.valueOf(setDetails.getWeight()));
        holder.getRepText().setText(String.valueOf(setDetails.getReps()));
        String averageWeight = String.format("%.2f", setAverage1.getAverageWeight());
        String averageReps = String.format("%.2f", setAverage1.getAverageReps());
        holder.getPrevText().setText(averageWeight + "kg, " + averageReps + "x ");

        TextWatcher weightWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int newWeight = Integer.parseInt(charSequence.toString());
                    setDetails.setWeight(newWeight);
                } catch (NumberFormatException e) {
                    Log.d("CHAD", "AdapterSets weightWatcher exception: " + e);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        holder.addWeightTextWatcher(weightWatcher);

        TextWatcher repWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int newRep = Integer.parseInt(charSequence.toString());
                    setDetails.setReps(newRep);
                } catch (NumberFormatException e) {
                    Log.d("CHAD", "AdapterSets repWatcher exception: " + e);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        holder.addRepTextWatcher(repWatcher);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clearTextWatchers();
    }

    @Override
    public int getItemCount() {
        return setDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView setCount;
        private TextView weightText;
        private TextView repText;
        private TextView prevText;
        private TextWatcher weightTextWatcher;
        private TextWatcher repTextWatcher;

        public ViewHolder(View itemView) {
            super(itemView);
            setSetCount(itemView.findViewById(R.id.setCount));
            setWeightText(itemView.findViewById(R.id.weightText));
            setRepText(itemView.findViewById(R.id.repText));
            setPrevText(itemView.findViewById(R.id.previousText));
        }

        public void addWeightTextWatcher(TextWatcher watcher) {
            if (getWeightTextWatcher() != null) {
                getWeightText().removeTextChangedListener(getWeightTextWatcher());
            }
            setWeightTextWatcher(watcher);
            getWeightText().addTextChangedListener(watcher);
        }

        public void addRepTextWatcher(TextWatcher watcher) {
            if (getRepTextWatcher() != null) {
                getRepText().removeTextChangedListener(getRepTextWatcher());
            }
            setRepTextWatcher(watcher);
            getRepText().addTextChangedListener(watcher);
        }

        public void clearTextWatchers() {
            if (getWeightTextWatcher() != null) {
                getWeightText().removeTextChangedListener(getWeightTextWatcher());
            }
            if (getRepTextWatcher() != null) {
                getRepText().removeTextChangedListener(getRepTextWatcher());
            }
        }

        private TextView getSetCount() {
            return setCount;
        }

        private void setSetCount(TextView setCount) {
            this.setCount = setCount;
        }

        private TextView getWeightText() {
            return weightText;
        }

        private void setWeightText(TextView weightText) {
            this.weightText = weightText;
        }

        private TextView getRepText() {
            return repText;
        }

        private void setRepText(TextView repText) {
            this.repText = repText;
        }

        private TextWatcher getWeightTextWatcher() {
            return weightTextWatcher;
        }

        private void setWeightTextWatcher(TextWatcher weightTextWatcher) {
            this.weightTextWatcher = weightTextWatcher;
        }

        private TextWatcher getRepTextWatcher() {
            return repTextWatcher;
        }

        private void setRepTextWatcher(TextWatcher repTextWatcher) {
            this.repTextWatcher = repTextWatcher;
        }

        public TextView getPrevText() {
            return prevText;
        }

        public void setPrevText(TextView prevText) {
            this.prevText = prevText;
        }
    }
}
