package com.example.giga_stats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "Giga_Stats.db";
    public static final int version = 4;
    public static final String TABELLE_EXERCISES = "exercises";
    public static final String SPALTE_EXERCISES_ID = "_id";
    public static final String SPALTE_EXERCISES_NAME = "name";
    public static final String SPALTE_EXERCISES_CATEGORY = "category";
    public static final String SPALTE_EXERCISES_REP = "rep";
    public static final String SPALTE_EXERCISES_WEIGHT = "weight";
    public static final String SPALTE_EXERCISES_IMG = "img";

    public static final String TABELLE_WORKOUTS = "workouts";
    public static final String SPALTE_WORKOUTS_ID = "_id";
    public static final String SPALTE_WORKOUTS_NAME = "name";

    public static final String TABELLE_WORKOUT_EXERCISES = "workout_exercises";
    public static final String SPALTE_WE_WORKOUT_ID = "workout_id";
    public static final String SPALTE_WE_EXERCISE_ID = "exercise_id";

    public static final String TABELLE_SETS = "sets";
    public static final String SPALTE_SETS_ID = "_id";
    public static final String SPALTE_SETS_REP = "rep";
    public static final String SPALTE_SETS_WEIGHT = "weight";
    public static final String SPALTE_SETS_WORKOUT = "workout_id";
    public static final String SPALTE_SETS_EXERCISE = "exercise_id";
    public static final String SPALTE_SETS_DATE = "date";

    ArrayList<String> categories = new ArrayList<>();

    public DBManager(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        categories.add("Arme");
        categories.add("Beine");
        categories.add("Brust");
        categories.add("Rücken");

        db.execSQL("PRAGMA foreign_keys = ON;");

        db.execSQL(
                "CREATE TABLE " + TABELLE_EXERCISES + " (" +
                        SPALTE_EXERCISES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_EXERCISES_NAME + " TEXT," +
                        SPALTE_EXERCISES_CATEGORY + " TEXT," +
                        SPALTE_EXERCISES_REP + " INTEGER," +
                        SPALTE_EXERCISES_WEIGHT + " INTEGER," +
                        SPALTE_EXERCISES_IMG + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + TABELLE_WORKOUTS + " (" +
                        SPALTE_WORKOUTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_WORKOUTS_NAME + " TEXT)"
        );

        db.execSQL(
                "CREATE TABLE " + TABELLE_WORKOUT_EXERCISES + " (" +
                        SPALTE_WE_WORKOUT_ID + " INTEGER," +
                        SPALTE_WE_EXERCISE_ID + " INTEGER," +
                        " FOREIGN KEY (" + SPALTE_WE_WORKOUT_ID + ") " +
                        "REFERENCES " + TABELLE_WORKOUTS + "(" + SPALTE_WORKOUTS_ID + ") ON DELETE CASCADE," +
                        " FOREIGN KEY (" + SPALTE_WE_EXERCISE_ID + ") " +
                        "REFERENCES " + TABELLE_EXERCISES + "(" + SPALTE_EXERCISES_ID + ") ON DELETE CASCADE)"
        );

        db.execSQL(
                "CREATE TABLE " + TABELLE_SETS + " (" +
                        SPALTE_SETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_SETS_WORKOUT + " INTEGER, " +
                        SPALTE_SETS_EXERCISE + " INTEGER, " +
                        SPALTE_SETS_REP + " INTEGER, " +
                        SPALTE_SETS_WEIGHT + "INTEGER, " +
                        SPALTE_SETS_DATE + " TEXT," +
                        " FOREIGN KEY (" + SPALTE_SETS_WORKOUT +
                        ") REFERENCES " + TABELLE_WORKOUTS + "(" + SPALTE_WORKOUTS_ID + ") ON DELETE CASCADE," +
                        " FOREIGN KEY (" + SPALTE_SETS_EXERCISE +
                        ") REFERENCES " + TABELLE_EXERCISES + "(" + SPALTE_EXERCISES_ID + ") ON DELETE CASCADE)"
        );

        // Beispiel 1: Einfügen einer Übung mit SQL-Insert-Anweisung
        String insertExerciseSQL1 = "INSERT INTO exercises (name, category, rep, weight, img) VALUES ('Bankdrücken', 'Brust', 10, 100, 'baseline_accessibility_new_24');";
        db.execSQL(insertExerciseSQL1);

        // Beispiel 2: Einfügen einer weiteren Übung
        String insertExerciseSQL2 = "INSERT INTO exercises (name, category, rep, weight, img) VALUES ('Kniebeugen', 'Beine', 8, 150, 'baseline_accessibility_new_24');";
        db.execSQL(insertExerciseSQL2);

        // Beispiel 3: Einfügen einer dritten Übung
        String insertExerciseSQL3 = "INSERT INTO exercises (name, category, rep, weight, img) VALUES ('Bizepscurls', 'Arme', 12, 20, 'baseline_accessibility_new_24');";
        db.execSQL(insertExerciseSQL3);

        // Beispiel 4: Einfügen einer vierten Übung
        String insertExerciseSQL4 = "INSERT INTO exercises (name, category, rep, weight, img) VALUES ('Klimmzüge', 'Rücken', 8, 80, 'baseline_accessibility_new_24');";
        db.execSQL(insertExerciseSQL4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i < i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EXERCISES);
            db.execSQL("DROP TABLE IF EXISTS " + TABELLE_WORKOUTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABELLE_SETS);
            onCreate(db);
        }
    }

    //=====================================================EXERCISES QUERIES=========================================================================
    public void insertExercise(String name, String category, int rep, int weight) {

        ContentValues newRow = new ContentValues();
        newRow.put(SPALTE_EXERCISES_NAME, name);
        newRow.put(SPALTE_EXERCISES_CATEGORY, category);
        newRow.put(SPALTE_EXERCISES_REP, rep);
        newRow.put(SPALTE_EXERCISES_WEIGHT, weight);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_EXERCISES, null, newRow);

    }

    public void deleteExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = SPALTE_EXERCISES_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};

        db.delete(TABELLE_EXERCISES, where, whereArg);
    }

    public Cursor selectAllExercises() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_EXERCISES, null);
        cursor.moveToFirst();
        if (cursor != null) {
            Log.d("CHAD", "Anzahl der abgerufenen Übungen: " + cursor.getCount());
        }
        return cursor;
    }

    public Cursor selectExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_EXERCISES + " WHERE " + SPALTE_EXERCISES_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateExercise(int id, String name, String category, int rep, int weight) {
        deleteExercise(id);
        insertExercise(name, category, rep, weight);
    }

    //=====================================================WORKOUTS QUERIES==========================================================================

    public void insertWorkout(String name) {

        ContentValues newRow = new ContentValues();
        newRow.put(SPALTE_WORKOUTS_NAME, name);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_WORKOUTS, null, newRow);

    }

    public void deleteWorkout(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = SPALTE_WORKOUTS_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};

        db.delete(TABELLE_WORKOUTS, where, whereArg);
    }

    public Cursor selectAllWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_WORKOUTS, null);
        cursor.moveToFirst();
        if (cursor != null) {
            Log.d("CHAD", "Anzahl der abgerufenen Übungen: " + cursor.getCount());
        }
        return cursor;
    }

    public Cursor selectWorkout(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_WORKOUTS + " WHERE " + SPALTE_WORKOUTS_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateWorkout(int id, String name) {
        deleteWorkout(id);
        insertWorkout(name);
    }

    //=====================================================WORKOUT_EXERCISES QUERIES=================================================================

    public void insertWorkoutExercise(int workout_id, int exercise_id) {
        ContentValues newRow = new ContentValues();
        newRow.put(SPALTE_WE_WORKOUT_ID, workout_id);
        newRow.put(SPALTE_WE_EXERCISE_ID, exercise_id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_WORKOUTS, null, newRow);
    }


    //=====================================================SETS QUERIES==============================================================================

    public void insertSet(int weight, int rep, int workout_id, int exercise_id, String date) {

        ContentValues newRow = new ContentValues();
        newRow.put(SPALTE_SETS_WORKOUT, workout_id);
        newRow.put(SPALTE_SETS_EXERCISE, exercise_id);
        newRow.put(SPALTE_SETS_WEIGHT, weight);
        newRow.put(SPALTE_SETS_REP, rep);
        newRow.put(SPALTE_SETS_DATE, date);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_SETS, null, newRow);

    }

    public void deleteSet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = SPALTE_SETS_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};

        db.delete(TABELLE_SETS, where, whereArg);
    }

    public Cursor selectAllSets() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_SETS, null);
        cursor.moveToFirst();
        if (cursor != null) {
            Log.d("CHAD", "Anzahl der abgerufenen Übungen: " + cursor.getCount());
        }
        return cursor;
    }

    public Cursor selectFullSet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELLE_SETS + " WHERE " + SPALTE_SETS_ID + "=" + id, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateSet(int id, String name) {
        deleteWorkout(id);
        insertWorkout(name);
    }
}
