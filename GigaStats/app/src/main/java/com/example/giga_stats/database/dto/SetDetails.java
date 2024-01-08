/**
 * Die Klasse SetDetails repräsentiert die Details eines Sets in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
package com.example.giga_stats.database.dto;

import com.example.giga_stats.database.entities.Exercise;


public class SetDetails {
    private int setCount;
    private Exercise exercise;
    private int weight;
    private int reps;

    /**
     * Standardkonstruktor für SetDetails.
     */
    public SetDetails() {
    }

    /**
     * Gibt das Gewicht des Sets zurück.
     *
     * @return Das Gewicht des Sets.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Setzt das Gewicht des Sets.
     *
     * @param weight Das Gewicht des Sets.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gibt die Anzahl der Wiederholungen des Sets zurück.
     *
     * @return Die Anzahl der Wiederholungen des Sets.
     */
    public int getReps() {
        return reps;
    }

    /**
     * Setzt die Anzahl der Wiederholungen des Sets.
     *
     * @param reps Die Anzahl der Wiederholungen des Sets.
     */
    public void setReps(int reps) {
        this.reps = reps;
    }

    /**
     * Gibt die Set-Anzahl zurück.
     *
     * @return Die Set-Anzahl.
     */
    public int getSetCount() {
        return setCount;
    }

    /**
     * Setzt die Set-Anzahl.
     *
     * @param setCount Die Set-Anzahl.
     */
    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    /**
     * Gibt die zugehörige Übung (Exercise) zurück.
     *
     * @return Die zugehörige Übung (Exercise).
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Setzt die zugehörige Übung (Exercise).
     *
     * @param exercise Die zugehörige Übung (Exercise).
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Gibt eine String-Repräsentation der SetDetails-Instanz zurück.
     *
     * @return Eine String-Repräsentation der SetDetails-Instanz.
     */
    @Override
    public String toString() {
        if(getExercise() != null){
        return "SetDetails{" +
                "setCount=" + getSetCount() +
                ", exercise=" + getExercise().toString() +
                ", weight=" + getWeight() +
                ", reps=" + getReps() +
                '}';
        }
        return "Init Set";
    }
}
