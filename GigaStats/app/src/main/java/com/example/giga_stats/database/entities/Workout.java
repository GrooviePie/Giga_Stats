package com.example.giga_stats.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Die Klasse Workout repräsentiert ein Workout in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Entity(tableName = "workouts")
public class Workout {

    /**
     * Standard-Konstruktor für die Workout-Klasse.
     *
     * @param name Der Name des Workouts.
     */
    public Workout(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    public int workout_id;

    @ColumnInfo(name = "name")
    String name;

    /**
     * Gibt die ID des Workouts zurück.
     *
     * @return Die ID des Workouts.
     */
    public int getWorkout_id() {
        return workout_id;
    }

    /**
     * Setzt die ID des Workouts.
     *
     * @param workout_id Die ID des Workouts.
     */
    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    /**
     * Gibt den Namen des Workouts zurück.
     *
     * @return Der Name des Workouts.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Workouts.
     *
     * @param name Der Name des Workouts.
     */
    public void setName(String name) {
        this.name = name;
    }
}
