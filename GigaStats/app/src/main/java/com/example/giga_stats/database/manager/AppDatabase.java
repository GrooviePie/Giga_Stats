/**
 * Die Klasse AppDatabase repräsentiert die Room-Datenbank der Giga Stats-Anwendung.
 * Sie enthält DAOs für die Entitäten Exercise, Workout, WorkoutExerciseCrossRef und Sets.
 * Die Datenbank wird durch die Annotation @Database definiert und erbt von RoomDatabase.
 *
 * @version 1.0
 */
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


@Database(entities = {Exercise.class, Workout.class, WorkoutExerciseCrossRef.class, Sets.class}, version = 11)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    /**
     * Gibt das DAO-Objekt für die Entität Exercise zurück.
     *
     * @return Das ExerciseDAO-Objekt.
     */
    public abstract ExerciseDAO exerciseDao();

    /**
     * Gibt das DAO-Objekt für die Entität Workout zurück.
     *
     * @return Das WorkoutDAO-Objekt.
     */
    public abstract WorkoutDAO workoutDao();

    /**
     * Gibt das DAO-Objekt für die Entität WorkoutExerciseCrossRef zurück.
     *
     * @return Das WorkoutExerciseCrossRefDAO-Objekt.
     */
    public abstract WorkoutExerciseCrossRefDAO workoutExerciseCrossRefDao();

    /**
     * Gibt das DAO-Objekt für die Entität Sets zurück.
     *
     * @return Das SetsDAO-Objekt.
     */
    public abstract SetsDAO setDao();

    /**
     * Gibt eine Instanz der Datenbank zurück. Wenn keine Instanz vorhanden ist, wird eine neue erstellt.
     *
     * @param context Der Anwendungskontext.
     * @return Die Datenbankinstanz.
     */
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

