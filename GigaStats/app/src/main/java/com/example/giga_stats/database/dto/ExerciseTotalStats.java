package com.example.giga_stats.database.dto;

/**
 * Die Klasse ExerciseTotalStats repräsentiert die gesamten Statistiken einer Übung in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
public class ExerciseTotalStats {

    private String exerciseName;
    private SetAverage previousAverage;
    private SetAverage currentAverage;
    private double efficiency;

    /**
     * Standardkonstruktor für ExerciseTotalStats.
     */
    public ExerciseTotalStats() {
    }

    /**
     * Konstruktor für ExerciseTotalStats mit Parametern.
     *
     * @param exerciseName     Der Name der Übung.
     * @param previousAverage Die durchschnittlichen Werte vorheriger Sätze.
     * @param currentAverage  Die aktuellen durchschnittlichen Werte.
     * @param efficiency      Die Effizienz der Übung.
     */
    public ExerciseTotalStats(String exerciseName, SetAverage previousAverage, SetAverage currentAverage, double efficiency) {
        this.exerciseName = exerciseName;
        this.previousAverage = previousAverage;
        this.currentAverage = currentAverage;
        this.efficiency = efficiency;
    }

    /**
     * Gibt den Namen der Übung zurück.
     *
     * @return Der Name der Übung.
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Setzt den Namen der Übung.
     *
     * @param exerciseName Der Name der Übung.
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * Gibt die durchschnittlichen Werte vorheriger Sätze zurück.
     *
     * @return Die durchschnittlichen Werte vorheriger Sätze.
     */
    public SetAverage getPreviousAverage() {
        return previousAverage;
    }

    /**
     * Setzt die durchschnittlichen Werte vorheriger Sätze.
     *
     * @param previousAverage Die durchschnittlichen Werte vorheriger Sätze.
     */
    public void setPreviousAverage(SetAverage previousAverage) {
        this.previousAverage = previousAverage;
    }

    /**
     * Gibt die aktuellen durchschnittlichen Werte zurück.
     *
     * @return Die aktuellen durchschnittlichen Werte.
     */
    public SetAverage getCurrentAverage() {
        return currentAverage;
    }

    /**
     * Setzt die aktuellen durchschnittlichen Werte.
     *
     * @param currentAverage Die aktuellen durchschnittlichen Werte.
     */
    public void setCurrentAverage(SetAverage currentAverage) {
        this.currentAverage = currentAverage;
    }

    /**
     * Gibt die Effizienz der Übung zurück.
     *
     * @return Die Effizienz der Übung.
     */
    public double getEfficiency() {
        return efficiency;
    }

    /**
     * Setzt die Effizienz der Übung.
     *
     * @param efficiency Die Effizienz der Übung.
     */
    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }
}
