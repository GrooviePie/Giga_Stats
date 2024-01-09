package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExercises;
import com.example.giga_stats.R;

import java.util.List;

/**
 * Die Klasse AdapterWorkoutRoomExpandableList ist ein BaseExpandableListAdapter, der dazu dient, Workouts und zugehörige Übungen in einer ExpandableListView in der Giga Stats-Anwendung anzuzeigen.
 *
 * Dieser Adapter ermöglicht die Anzeige von Workouts und deren zugehörigen Übungen in einer ExpandableListView. Er erweitert BaseExpandableListAdapter und implementiert die notwendigen Methoden für die Verwaltung von Gruppen und Kindern in der ExpandableListView.
 *
 * @version 1.0
 */
public class AdapterWorkoutRoomExpandableList extends BaseExpandableListAdapter {
    private Context context;
    private List <WorkoutExercises> workoutExercises;

    /**
     * Konstruktor für den AdapterWorkoutRoomExpandableList.
     *
     * @param context         Der Kontext, in dem der Adapter erstellt wird.
     * @param workoutExercises Eine Liste von WorkoutExercises, die Workouts und zugehörige Übungen enthält.
     */
    public AdapterWorkoutRoomExpandableList(Context context, List <WorkoutExercises> workoutExercises) {
        this.context = context;
        this.workoutExercises = workoutExercises;
    }

    /**
     * Gibt die Anzahl der Gruppen (Workouts) in der ExpandableListView zurück.
     *
     * @return Die Anzahl der Gruppen.
     */
    @Override
    public int getGroupCount() {
        return workoutExercises.size();
    }

    /**
     * Gibt die Anzahl der Kinder (Übungen) in einer bestimmten Gruppe (Workout) zurück.
     *
     * @param i Die Position der Gruppe.
     * @return Die Anzahl der Kinder in der Gruppe.
     */
    @Override
    public int getChildrenCount(int i) {
        return workoutExercises.get(i).getExercises().size();
    }

    /**
     * Gibt die Gruppe (Workout) an einer bestimmten Position zurück.
     *
     * @param i Die Position der Gruppe.
     * @return Die Gruppe (Workout) an der angegebenen Position.
     */
    @Override
    public Object getGroup(int i) {
        return workoutExercises.get(i);
    }

    /**
     * Gibt das Kind (Übung) an einer bestimmten Position in einer Gruppe zurück.
     *
     * @param i Die Position der Gruppe.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return Das Kind (Übung) an der angegebenen Position.
     */
    @Override
    public Object getChild(int i, int i1) {
        return workoutExercises.get(i).getExercises();
    }

    /**
     * Gibt die Gruppen-ID (Workout-ID) an einer bestimmten Position zurück.
     *
     * @param i Die Position der Gruppe.
     * @return Die Gruppen-ID (Workout-ID) an der angegebenen Position.
     */
    @Override
    public long getGroupId(int i) {
        return workoutExercises.get(i).getWorkout().getWorkout_id();
    }

    /**
     * Gibt die Kinder-ID (Übungs-ID) an einer bestimmten Position in einer Gruppe zurück.
     *
     * @param i Die Position der Gruppe.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return Die Kinder-ID (Übungs-ID) an der angegebenen Position.
     */
    @Override
    public long getChildId(int i, int i1) {
        return workoutExercises.get(i).getExercises().get(i1).getExercise_id();
    }

    /**
     * Gibt an, ob die Gruppen- und Kinder-IDs stabil sind.
     *
     * @return true, wenn die IDs stabil sind, ansonsten false.
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Gibt die Ansicht für die Gruppe (Workout) an einer bestimmten Position zurück.
     *
     * @param i Die Position der Gruppe.
     * @param b    Gibt an, ob die Gruppe erweitert ist.
     * @param view   Die bestehende Ansicht.
     * @param viewGroup        Die übergeordnete Ansicht.
     * @return Die Ansicht für die Gruppe.
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_group_workout, null);
        }
        Workout workout = workoutExercises.get(i).getWorkout();

        if (workout != null) {
            TextView nameTextView = view.findViewById(R.id.nameWorkout);
            TextView idTextView = view.findViewById(R.id.idWorkout);

            idTextView.setText(String.valueOf(workout.getWorkout_id()));
            nameTextView.setText(workout.getName());
        }

        return view;
    }

    /**
     * Gibt die Ansicht für das Kind (Übung) an einer bestimmten Position in einer Gruppe zurück.
     *
     * @param i Die Position der Gruppe.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @param b    Gibt an, ob das Kind das letzte in der Gruppe ist.
     * @param view   Die bestehende Ansicht.
     * @param viewGroup        Die übergeordnete Ansicht.
     * @return Die Ansicht für das Kind.
     */
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_workout, null);
        }

        TextView exerciseNameTextView = view.findViewById(R.id.w);
        exerciseNameTextView.setText(workoutExercises.get(i).getExercises().get(i1).getName());

        return view;
    }

    /**
     * Gibt an, ob das Kind auswählbar ist.
     *
     * @param i Die Position der Gruppe.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return true, wenn das Kind auswählbar ist, ansonsten false.
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
