/**
 * Die Klasse SetAverage repräsentiert die durchschnittlichen Werte eines Sets in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
package com.example.giga_stats.database.dto;


public class SetAverage {
    double averageWeight;
    double averageReps;

    /**
     * Konstruktor für SetAverage mit Parametern.
     *
     * @param averageWeight Der durchschnittliche Gewichtswert.
     * @param averageReps   Der durchschnittliche Wiederholungswert.
     */
    public SetAverage(double averageWeight, double averageReps) {
        this.averageWeight = averageWeight;
        this.averageReps = averageReps;
    }

    /**
     * Standardkonstruktor für SetAverage.
     */
    public SetAverage() {
        averageWeight = 0.0;
        averageReps = 0.0;
    }

    /**
     * Gibt den durchschnittlichen Gewichtswert zurück.
     *
     * @return Der durchschnittliche Gewichtswert.
     */
    public double getAverageWeight() {
        return averageWeight;
    }

    /**
     * Setzt den durchschnittlichen Gewichtswert.
     *
     * @param averageWeight Der durchschnittliche Gewichtswert.
     */
    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    /**
     * Gibt den durchschnittlichen Wiederholungswert zurück.
     *
     * @return Der durchschnittliche Wiederholungswert.
     */
    public double getAverageReps() {
        return averageReps;
    }

    /**
     * Setzt den durchschnittlichen Wiederholungswert.
     *
     * @param averageReps Der durchschnittliche Wiederholungswert.
     */
    public void setAverageReps(double averageReps) {
        this.averageReps = averageReps;
    }

    /**
     * Gibt eine String-Repräsentation der SetAverage-Instanz zurück.
     *
     * @return Eine String-Repräsentation der SetAverage-Instanz.
     */
    @Override
    public String toString() {
        return "SetAverage{" +
                "averageWeight=" + averageWeight +
                ", averageReps=" + averageReps +
                '}';
    }
}
