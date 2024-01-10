package com.example.giga_stats.database.entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Die Klasse Exercise repräsentiert eine Übung in der Giga Stats-Anwendung.
 *
 * @version 1.0
 */
@Entity(tableName = "exercises")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    int exercise_id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "rep")
    @Nullable
    int rep;

    @ColumnInfo(name = "weight")
    @Nullable
    int weight;

    @ColumnInfo(name = "desc")
    String desc;

    /**
     * Konstruktor für die Exercise-Klasse.
     *
     * @param name     Der Name der Übung.
     * @param category Die Kategorie, zu der die Übung gehört.
     * @param rep      Die Anzahl der Wiederholungen (kann null sein).
     * @param weight   Das Gewicht (kann null sein).
     * @param desc     Eine Beschreibung der Übung.
     */
    public Exercise(String name, String category, @Nullable int rep, @Nullable int weight, @Nullable String desc) {
        this.name = name;
        this.category = category;
        this.rep = rep;
        this.weight = weight;
        this.desc = desc;
    }

    /**
     * Gibt die ID der Übung zurück.
     *
     * @return Die ID der Übung.
     */
    public int getExercise_id() {
        return exercise_id;
    }

    /**
     * Gibt den Namen der Übung zurück.
     *
     * @return Der Name der Übung.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Kategorie der Übung zurück.
     *
     * @return Die Kategorie der Übung.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gibt die Anzahl der Wiederholungen zurück (kann null sein).
     *
     * @return Die Anzahl der Wiederholungen.
     */
    public int getRep() {
        return rep;
    }

    /**
     * Gibt das Gewicht der Übung zurück (kann null sein).
     *
     * @return Das Gewicht der Übung.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Setzt die ID der Übung.
     *
     * @param exercise_id Die ID der Übung.
     */
    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    /**
     * Setzt den Namen der Übung.
     *
     * @param name Der Name der Übung.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setzt die Kategorie der Übung.
     *
     * @param category Die Kategorie der Übung.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Setzt die Anzahl der Wiederholungen.
     *
     * @param rep Die Anzahl der Wiederholungen.
     */
    public void setRep(int rep) {
        this.rep = rep;
    }

    /**
     * Setzt das Gewicht der Übung.
     *
     * @param weight Das Gewicht der Übung.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gibt die Beschreibung der Übung zurück.
     *
     * @return Die Beschreibung der Übung.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setzt die Beschreibung der Übung.
     *
     * @param desc Die Beschreibung der Übung.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Gibt eine String-Repräsentation der Exercise-Instanz zurück.
     *
     * @return Eine String-Repräsentation der Exercise-Instanz.
     */
    @Override
    public String toString() {
        return "Exercise{" +
                "exercise_id=" + exercise_id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", rep=" + rep +
                ", weight=" + weight +
                ", desc='" + desc + '\'' +
                '}';
    }
}
