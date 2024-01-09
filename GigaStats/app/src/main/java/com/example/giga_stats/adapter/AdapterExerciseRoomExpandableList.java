package com.example.giga_stats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.R;

import java.util.List;

/**
 * Der AdapterExerciseRoomExpandableList ist ein ExpandableListAdapter, der für die Anzeige von
 * Übungen in einer ExpandableListView in der Giga Stats-Anwendung verwendet wird.
 *
 * Diese Klasse stellt Methoden bereit, um die Gruppen- und Kind-Elemente für die Anzeige zu verwalten.
 * Sie erweitert die BaseExpandableListAdapter-Klasse.
 *
 * @version 1.0
 */
public class AdapterExerciseRoomExpandableList extends BaseExpandableListAdapter {

    private Context context;
    private List<Exercise> exercises;

    /**
     * Konstruktor für den AdapterExerciseRoomExpandableList.
     *
     * @param context   Der Kontext, in dem der Adapter erstellt wird.
     * @param exercises Eine Liste von Übungen, die im Adapter angezeigt werden sollen.
     */
    public AdapterExerciseRoomExpandableList(Context context, List<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    /**
     * Gibt die Anzahl der Gruppen (Übungen) im Adapter zurück.
     *
     * @return Die Anzahl der Gruppen im Adapter.
     */
    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    /**
     * Gibt die Anzahl der Kinder (Detailinformationen zu Übungen) für eine bestimmte Gruppe zurück.
     *
     * @param i Die Position der Gruppe in der Liste.
     * @return Die Anzahl der Kinder für die Gruppe an der Position i.
     */
    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    /**
     * Gibt die Gruppe (Übungsnamen) an einer bestimmten Position zurück.
     *
     * @param i Die Position der Gruppe in der Liste.
     * @return Das Objekt, das die Gruppeninformation repräsentiert (Übungsname).
     */
    @Override
    public Object getGroup(int i) {
        return exercises.get(i).getName();
    }

    /**
     * Gibt die Detailinformationen (Übungsbeschreibung) zu einem bestimmten Kind in einer Gruppe zurück.
     *
     * @param i  Die Position der Gruppe in der Liste.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return Das Objekt, das die Detailinformationen repräsentiert (Übungsbeschreibung).
     */
    @Override
    public Object getChild(int i, int i1) {
        return exercises.get(i).getDesc();
    }

    /**
     * Gibt die Gruppen-ID für eine bestimmte Gruppe zurück.
     *
     * @param i Die Position der Gruppe in der Liste.
     * @return Die Gruppen-ID für die Gruppe an der Position i.
     */
    @Override
    public long getGroupId(int i) {
        return exercises.get(i).getExercise_id();
    }

    /**
     * Gibt die ID für ein bestimmtes Kind in einer Gruppe zurück.
     *
     * @param i  Die Position der Gruppe in der Liste.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return Die ID für das Kind an der Position i1 in der Gruppe an der Position i.
     */
    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    /**
     * Gibt an, ob die IDs für Gruppen und Kinder stabil sind und sich nicht ändern.
     *
     * @return `true`, wenn die IDs stabil sind, andernfalls `false`.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Erstellt und gibt die Ansicht für die Gruppe zurück.
     *
     * @param i           Die Position der Gruppe in der Liste.
     * @param b           Gibt an, ob die Gruppe erweiterbar ist.
     * @param view        Die vorhandene Ansicht oder `null`, wenn eine neue Ansicht erstellt werden muss.
     * @param viewGroup   Die übergeordnete Ansicht, in die die Gruppenansicht eingefügt wird.
     * @return Die Ansicht für die Gruppe an der Position i.
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_group_exercise, null);
        }
        Exercise exercise = exercises.get(i);

        if (exercise != null) {
            TextView nameTextView = view.findViewById(R.id.nameExercise);
            TextView categoryTextView = view.findViewById(R.id.categoryExercise);
            TextView idTextView = view.findViewById(R.id.idExercise);

            idTextView.setText(String.valueOf(exercise.getExercise_id()));
            nameTextView.setText(exercise.getName());
            categoryTextView.setText(exercise.getCategory());
        }

        return view;
    }

    /**
     * Erstellt und gibt die Ansicht für das Kind (Detailinformationen zur Übung) zurück.
     *
     * @param i           Die Position der Gruppe in der Liste.
     * @param i1          Die Position des Kindes in der Gruppe.
     * @param b           Gibt an, ob das Kind ausgewählt ist.
     * @param view        Die vorhandene Ansicht oder `null`, wenn eine neue Ansicht erstellt werden muss.
     * @param viewGroup   Die übergeordnete Ansicht, in die die Kindansicht eingefügt wird.
     * @return Die Ansicht für das Kind an der Position i1 in der Gruppe an der Position i.
     */
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_exercise, null);
        }

        Exercise exercise = exercises.get(i);

        if(exercise != null) {
            TextView descTextView = view.findViewById(R.id.descExercise);
            TextView repTextView = view.findViewById(R.id.repExercise);
            TextView weightTextView = view.findViewById(R.id.weightExercise);

            repTextView.setText(String.valueOf(exercise.getRep()) + "x");
            weightTextView.setText(String.valueOf(exercise.getWeight()) + "kg");
            descTextView.setText(exercise.getDesc());
        }

        return view;
    }

    /**
     * Gibt an, ob das Kind (Detailinformationen zur Übung) auswählbar ist.
     *
     * @param i  Die Position der Gruppe in der Liste.
     * @param i1 Die Position des Kindes in der Gruppe.
     * @return `true`, wenn das Kind auswählbar ist, andernfalls `false`.
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
