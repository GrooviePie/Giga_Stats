package com.example.giga_stats.database.manager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.giga_stats.database.dao.ExerciseDAO;
import com.example.giga_stats.database.dao.SetsDAO;
import com.example.giga_stats.database.dao.WorkoutDAO;
import com.example.giga_stats.database.dao.WorkoutExerciseCrossRefDAO;
import com.example.giga_stats.database.entities.Exercise;
import com.example.giga_stats.database.entities.Sets;
import com.example.giga_stats.database.entities.Workout;
import com.example.giga_stats.database.entities.WorkoutExerciseCrossRef;

import java.util.concurrent.Executors;

@Database(entities = {Exercise.class, Workout.class, WorkoutExerciseCrossRef.class, Sets.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDAO exerciseDao();
    public abstract WorkoutDAO workoutDao();
    public abstract WorkoutExerciseCrossRefDAO workoutExerciseCrossRefDao();
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
            });
        }
    };
}

