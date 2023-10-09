package com.example.giga_stats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.giga_stats.R;

public class DBManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "Giga_Stats.db";
    public static final int version = 1;
    public static final String TABELLE_EXERCISES = "exercises";
    public static final String SPALTE_EXERCISES_ID = "_id";
    public static final String SPALTE_EXERCISES_NAME = "name";
    public static final String SPALTE_EXERCISES_CATEGORY = "category";
    public static final String SPALTE_EXERCISES_INFO = "info";  // Gewicht oder Wiederholungen
    public static final String SPALTE_EXERCISES_IMG = "bild_res";

    public DBManager(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABELLE_EXERCISES + " (" +
                        SPALTE_EXERCISES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_EXERCISES_NAME + " TEXT," +
                        SPALTE_EXERCISES_CATEGORY + " TEXT," +
                        SPALTE_EXERCISES_INFO + " TEXT," +
                        SPALTE_EXERCISES_IMG + " TEXT" +
                        ")"
        );

        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 1', 'Kategorie 1', 'Informationen zur Übung 1', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 2', 'Kategorie 2', 'Informationen zur Übung 2', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 3', 'Kategorie 1', 'Informationen zur Übung 3', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 4', 'Kategorie 2', 'Informationen zur Übung 4', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 5', 'Kategorie 1', 'Informationen zur Übung 5', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 6', 'Kategorie 2', 'Informationen zur Übung 6', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 7', 'Kategorie 1', 'Informationen zur Übung 7', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 8', 'Kategorie 2', 'Informationen zur Übung 8', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 9', 'Kategorie 1', 'Informationen zur Übung 9', 'baseline_accessibility_new_24');");
        db.execSQL("INSERT INTO " + TABELLE_EXERCISES + " (" + SPALTE_EXERCISES_NAME + ", " + SPALTE_EXERCISES_CATEGORY + ", " + SPALTE_EXERCISES_INFO + ", " + SPALTE_EXERCISES_IMG + ") VALUES ('Übung 10', 'Kategorie 2', 'Informationen zur Übung 10', 'baseline_accessibility_new_24');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_EXERCISES);
        onCreate(db);
    }

    public void insertExercise(String name, String adresse, int bild_res) {

        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_EXERCISES_NAME, name);
        neueZeile.put(SPALTE_EXERCISES_CATEGORY, adresse);
        neueZeile.put(SPALTE_EXERCISES_IMG, bild_res);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_EXERCISES, null, neueZeile);

    }

    public void deleteExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = SPALTE_EXERCISES_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(id)};

        db.delete(TABELLE_EXERCISES, where, whereArg);
    }

    public Cursor selectAllExercises() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor meinZeiger = db.rawQuery("SELECT * FROM " + TABELLE_EXERCISES, null);
        meinZeiger.moveToFirst();
        if (meinZeiger != null) {
            Log.d("CHAD", "Anzahl der abgerufenen Übungen: " + meinZeiger.getCount());
        }
        return meinZeiger;
    }

    public Cursor selectExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor meinZeiger = db.rawQuery("SELECT * FROM " + TABELLE_EXERCISES + " WHERE " + SPALTE_EXERCISES_ID + "=" + id, null);
        meinZeiger.moveToFirst();
        return meinZeiger;
    }

//    public Cursor updateExercise(int id) {
//        Cursor cursor = selectExercise(id);
//        deleteExercise(id);
//
//        insertExercise(cursor.);
//        return
//    }
}
