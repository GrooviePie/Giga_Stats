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

/**
 * Der AdapterSets ist ein RecyclerView-Adapter, der für die Anzeige von Sets im Bottom Sheet in der Giga Stats-Anwendung verwendet wird.
 *
 * Diese Klasse ermöglicht die Anzeige von Set-Details, darunter Set-Nummer, Gewicht, Wiederholungen und Durchschnittswerte.
 * Sie implementiert die notwendigen Methoden von RecyclerView.Adapter und verwendet eine benutzerdefinierte ViewHolder-Klasse für die einzelnen Listenelemente.
 *
 * @version 1.0
 */
public class AdapterSets extends RecyclerView.Adapter<AdapterSets.ViewHolder> {

    private Context context;
    private HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise;
    private ArrayList<SetDetails> setDetails;
    private HashMap<Integer, SetAverage> setAveragePerExercise;
    private SetAverage setAverage;

    /**
     * Konstruktor für den AdapterSets.
     *
     * @param context              Der Kontext, in dem der Adapter erstellt wird.
     * @param setDetailsPerExercise HashMap, das SetDetails für jede Übung enthält.
     * @param setAveragePerExercise HashMap, das SetDurchschnittswerte für jede Übung enthält.
     * @param exercise_id          Die ID der Übung, für die Sets angezeigt werden sollen.
     */
    public AdapterSets(Context context, HashMap<Integer, ArrayList<SetDetails>> setDetailsPerExercise, HashMap<Integer, SetAverage> setAveragePerExercise, int exercise_id){
        this.context = context;
        this.setDetailsPerExercise = setDetailsPerExercise;
        this.setDetails = setDetailsPerExercise.get(exercise_id);
        this.setAveragePerExercise = setAveragePerExercise;
        this.setAverage = setAveragePerExercise.get(exercise_id);
        notifyDataSetChanged();
    }

    /**
     * Erstellt und gibt einen ViewHolder zurück, der die Layoutinflation für jedes Element in der RecyclerView handhabt.
     *
     * @param parent   Die übergeordnete Ansicht, in die die neue Ansicht eingefügt wird.
     * @param viewType Der Ansichtstyp des neuen Ansichtselements.
     * @return Ein ViewHolder, der die Ansicht für jedes Element in der RecyclerView repräsentiert.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_inner_list_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Bindet die Daten an die Ansichtselemente jedes Elements in der RecyclerView.
     *
     * @param holder   Der ViewHolder, der das aktuelle Ansichtselement repräsentiert.
     * @param position Die Position des aktuellen Elements in der RecyclerView-Liste.
     */
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

    /**
     * Wird aufgerufen, wenn der ViewHolder recycelt wird, um TextWatcher zu entfernen.
     *
     * @param holder Der ViewHolder, der recycelt wird.
     */
    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clearTextWatchers();
    }

    /**
     * Gibt die Anzahl der Elemente in der RecyclerView zurück.
     *
     * @return Die Anzahl der Elemente in der RecyclerView.
     */
    @Override
    public int getItemCount() {
        return setDetails.size();
    }

    /**
     * Die ViewHolder-Klasse repräsentiert die einzelnen Ansichtselemente in der RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView setCount;
        private TextView weightText;
        private TextView repText;
        private TextView prevText;
        private TextWatcher weightTextWatcher;
        private TextWatcher repTextWatcher;

        /**
         * Konstruktor für den ViewHolder.
         *
         * @param itemView Die Ansicht, die von diesem ViewHolder repräsentiert wird.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            setSetCount(itemView.findViewById(R.id.setCount));
            setWeightText(itemView.findViewById(R.id.weightText));
            setRepText(itemView.findViewById(R.id.repText));
            setPrevText(itemView.findViewById(R.id.previousText));
        }

        /**
         * Fügt einen TextWatcher für das Gewicht hinzu.
         *
         * @param watcher Der TextWatcher für das Gewicht.
         */
        public void addWeightTextWatcher(TextWatcher watcher) {
            if (getWeightTextWatcher() != null) {
                getWeightText().removeTextChangedListener(getWeightTextWatcher());
            }
            setWeightTextWatcher(watcher);
            getWeightText().addTextChangedListener(watcher);
        }

        /**
         * Fügt einen TextWatcher für die Wiederholungen hinzu.
         *
         * @param watcher Der TextWatcher für die Wiederholungen.
         */
        public void addRepTextWatcher(TextWatcher watcher) {
            if (getRepTextWatcher() != null) {
                getRepText().removeTextChangedListener(getRepTextWatcher());
            }
            setRepTextWatcher(watcher);
            getRepText().addTextChangedListener(watcher);
        }

        /**
         * Löscht alle TextWatcher.
         */
        public void clearTextWatchers() {
            if (getWeightTextWatcher() != null) {
                getWeightText().removeTextChangedListener(getWeightTextWatcher());
            }
            if (getRepTextWatcher() != null) {
                getRepText().removeTextChangedListener(getRepTextWatcher());
            }
        }

        /**
         * Getter für setCount.
         *
         * @return Der TextView, der die Set-Nummer repräsentiert.
         */
        private TextView getSetCount() {
            return setCount;
        }

        /**
         * Setter für setCount.
         *
         * @param setCount Der TextView, der die Set-Nummer repräsentiert.
         */
        private void setSetCount(TextView setCount) {
            this.setCount = setCount;
        }

        /**
         * Getter für weightText.
         *
         * @return Der TextView, der das Gewicht repräsentiert.
         */
        private TextView getWeightText() {
            return weightText;
        }

        /**
         * Setter für weightText.
         *
         * @param weightText Der TextView, der das Gewicht repräsentiert.
         */
        private void setWeightText(TextView weightText) {
            this.weightText = weightText;
        }

        /**
         * Getter für repText.
         *
         * @return Der TextView, der die Wiederholungen repräsentiert.
         */
        private TextView getRepText() {
            return repText;
        }

        /**
         * Setter für repText.
         *
         * @param repText Der TextView, der die Wiederholungen repräsentiert.
         */
        private void setRepText(TextView repText) {
            this.repText = repText;
        }

        /**
         * Getter für weightTextWatcher.
         *
         * @return Der TextWatcher für das Gewicht.
         */
        private TextWatcher getWeightTextWatcher() {
            return weightTextWatcher;
        }

        /**
         * Setter für weightTextWatcher.
         *
         * @param weightTextWatcher Der TextWatcher für das Gewicht.
         */
        private void setWeightTextWatcher(TextWatcher weightTextWatcher) {
            this.weightTextWatcher = weightTextWatcher;
        }

        /**
         * Getter für repTextWatcher.
         *
         * @return Der TextWatcher für die Wiederholungen.
         */
        private TextWatcher getRepTextWatcher() {
            return repTextWatcher;
        }

        /**
         * Setter für repTextWatcher.
         *
         * @param repTextWatcher Der TextWatcher für die Wiederholungen.
         */
        private void setRepTextWatcher(TextWatcher repTextWatcher) {
            this.repTextWatcher = repTextWatcher;
        }

        /**
         * Getter für prevText.
         *
         * @return Der TextView, der die vorherigen Durchschnittswerte repräsentiert.
         */
        public TextView getPrevText() {
            return prevText;
        }

        /**
         * Setter für prevText.
         *
         * @param prevText Der TextView, der die vorherigen Durchschnittswerte repräsentiert.
         */
        public void setPrevText(TextView prevText) {
            this.prevText = prevText;
        }
    }
}
