package com.example.giga_stats.DB.ENTITY;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public Exercise(String name, String category, @Nullable int rep, @Nullable int weight, @Nullable String desc) {
        this.name = name;
        this.category = category;
        this.rep = rep;
        this.weight = weight;
        this.desc = desc;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getRep() {
        return rep;
    }

    public int getWeight() {
        return weight;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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
