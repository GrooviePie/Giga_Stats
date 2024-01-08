/**
 * Der AdapterRunningWorkout ist ein BaseAdapter, der für die Anzeige von laufenden Workouts
 * in einer ListView in der Giga Stats-Anwendung verwendet wird.
 *
 * Diese Klasse ermöglicht die Anzeige von Workouts und öffnet ein Bottom Sheet, wenn ein Element
 * in der Liste angeklickt wird.
 *
 * @version 1.0
 */

package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giga_stats.R;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.fragments.FragmentRunningWorkoutBottomSheet;


public class AdapterRunningWorkout extends BaseAdapter {

    Context context;
    Workout[] workouts;

    /**
     * Konstruktor für den AdapterRunningWorkout.
     *
     * @param context  Der Kontext, in dem der Adapter erstellt wird.
     * @param workouts Ein Array von laufenden Workouts, die im Adapter angezeigt werden sollen.
     */
    public AdapterRunningWorkout(Context context, Workout[] workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    /**
     * Gibt die Anzahl der Elemente im Adapter zurück.
     *
     * @return Die Anzahl der Elemente im Adapter.
     */
    @Override
    public int getCount() {
        return workouts.length;
    }

    /**
     * Gibt das Element an einer bestimmten Position im Adapter zurück.
     *
     * @param position Die Position des Elements in der Liste.
     * @return Das Workout-Objekt an der Position.
     */
    @Override
    public Object getItem(int position) {
        return workouts[position];
    }

    /**
     * Gibt die ID des Elements an einer bestimmten Position im Adapter zurück.
     *
     * @param position Die Position des Elements in der Liste.
     * @return Die ID des Workouts an der Position.
     */
    @Override
    public long getItemId(int position) {
        return workouts[position].workout_id;
    }

    /**
     * Erstellt und gibt die Ansicht für ein Element in der Liste zurück.
     *
     * @param position    Die Position des Elements in der Liste.
     * @param view        Die vorhandene Ansicht oder `null`, wenn eine neue Ansicht erstellt werden muss.
     * @param viewGroup   Die übergeordnete Ansicht, in die die Ansicht des Elements eingefügt wird.
     * @return Die Ansicht für das Element an der Position.
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_running_workout, null);
        }

        LinearLayout runningTile = view.findViewById(R.id.runningTile);
        TextView runningNameInsert = view.findViewById(R.id.runningNameInsert);
        TextView runningWorkoutId = view.findViewById(R.id.runningWorkoutId);
        //TextView bottomSheetExerciseNameTextView = view.findViewById(R.id.bottomSheetExerciseNameTextView);

        runningNameInsert.setText(workouts[position].getName());
        runningWorkoutId.setText(String.valueOf(workouts[position].getWorkout_id()));
        //bottomSheetExerciseNameTextView.setText(workouts[position].getName());

        // Füge den Klicklistener zum runningTile hinzu
        runningTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Klick-Callback-Methode, um das Bottom Sheet zu öffnen
                openBottomSheetDialog(workouts[position]);
            }
        });

        return view;
    }

    /**
     * Öffnet das Bottom Sheet, um weitere Details zum Workout anzuzeigen.
     *
     * @param workout Das Workout-Objekt, für das das Bottom Sheet geöffnet werden soll.
     */
    private void openBottomSheetDialog(Workout workout) {
        FragmentRunningWorkoutBottomSheet bottomSheetFragment = new FragmentRunningWorkoutBottomSheet(workout);
        bottomSheetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}



