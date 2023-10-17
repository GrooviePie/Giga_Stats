package com.example.giga_stats.DB.MANAGER;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.giga_stats.DB.DAO.ExerciseDAO;
import com.example.giga_stats.DB.DAO.SetsDAO;
import com.example.giga_stats.DB.DAO.WorkoutDAO;
import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.Sets;
import com.example.giga_stats.DB.ENTITY.Workout;
import com.example.giga_stats.DB.ENTITY.WorkoutExerciseSetCrossRef;

import java.util.concurrent.Executors;

@Database(entities = {Exercise.class, Workout.class, WorkoutExerciseSetCrossRef.class, Sets.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDAO exerciseDao();
    public abstract WorkoutDAO workoutDao();
    public abstract SetsDAO setDao();

    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "GS.db")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                ExerciseDAO exerciseDAO = instance.exerciseDao();
                exerciseDAO.insertExercise(new Exercise("Übung 1", "Kategorie 1", 10, 50));
                exerciseDAO.insertExercise(new Exercise("Übung 2", "Kategorie 2", 12, 60));
            });
        }
    };
}

