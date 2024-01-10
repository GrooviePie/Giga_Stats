
package com.example.giga_stats.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Die Klasse Sets repräsentiert einen Satz in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Entity(foreignKeys = @ForeignKey(entity = Workout.class,
        parentColumns = "workout_id",
        childColumns = "workout_id",
        onDelete = ForeignKey.CASCADE))
public class Sets {
    @PrimaryKey(autoGenerate = true)
    private long set_id;
    private long workout_id;
    private long exercise_id;
    private String date;
    private int repetitions;
    private int weight;

    /**
     * Standard-Konstruktor für die Sets-Klasse.
     */
    public Sets() {
    }

    /**
     * Gibt die ID des Satzes zurück.
     *
     * @return Die ID des Satzes.
     */
    public long getSet_id() {
        return set_id;
    }

    /**
     * Setzt die ID des Satzes.
     *
     * @param set_id Die ID des Satzes.
     */
    public void setSet_id(long set_id) {
        this.set_id = set_id;
    }

    /**
     * Gibt die ID des zugehörigen Workouts zurück.
     *
     * @return Die ID des zugehörigen Workouts.
     */
    public long getWorkout_id() {
        return workout_id;
    }

    /**
     * Setzt die ID des zugehörigen Workouts.
     *
     * @param workout_id Die ID des zugehörigen Workouts.
     */
    public void setWorkout_id(long workout_id) {
        this.workout_id = workout_id;
    }

    /**
     * Gibt die ID der zugehörigen Übung zurück.
     *
     * @return Die ID der zugehörigen Übung.
     */
    public long getExercise_id() {
        return exercise_id;
    }

    /**
     * Setzt die ID der zugehörigen Übung.
     *
     * @param exercise_id Die ID der zugehörigen Übung.
     */
    public void setExercise_id(long exercise_id) {
        this.exercise_id = exercise_id;
    }

    /**
     * Gibt das Datum des Satzes zurück.
     *
     * @return Das Datum des Satzes.
     */
    public String getDate() {
        return date;
    }

    /**
     * Setzt das Datum des Satzes.
     *
     * @param date Das Datum des Satzes.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gibt die Anzahl der Wiederholungen zurück.
     *
     * @return Die Anzahl der Wiederholungen.
     */
    public int getRepetitions() {
        return repetitions;
    }

    /**
     * Setzt die Anzahl der Wiederholungen.
     *
     * @param repetitions Die Anzahl der Wiederholungen.
     */
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    /**
     * Gibt das Gewicht des Satzes zurück.
     *
     * @return Das Gewicht des Satzes.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Setzt das Gewicht des Satzes.
     *
     * @param weight Das Gewicht des Satzes.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gibt eine String-Repräsentation der Sets-Instanz zurück.
     *
     * @return Eine String-Repräsentation der Sets-Instanz.
     */
    @Override
    public String toString() {
        return "Sets{" +
                "set_id=" + getSet_id() +
                ", workout_id=" + getWorkout_id() +
                ", exercise_id=" + getExercise_id() +
                ", date='" + getDate() + '\'' +
                ", repetitions=" + getRepetitions() +
                ", weight=" + getWeight() +
                '}';
    }
}
