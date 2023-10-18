package com.example.giga_stats.DB.MANAGER;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.giga_stats.DB.DAO.ExerciseDAO;
import com.example.giga_stats.DB.DAO.ExerciseDAO_Impl;
import com.example.giga_stats.DB.DAO.SetsDAO;
import com.example.giga_stats.DB.DAO.SetsDAO_Impl;
import com.example.giga_stats.DB.DAO.WorkoutDAO;
import com.example.giga_stats.DB.DAO.WorkoutDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ExerciseDAO _exerciseDAO;

  private volatile WorkoutDAO _workoutDAO;

  private volatile SetsDAO _setsDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `exercises` (`exercise_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `category` TEXT, `rep` INTEGER NOT NULL, `weight` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `workouts` (`workout_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `WorkoutExerciseSetCrossRef` (`workout_id` INTEGER NOT NULL, `exercise_id` INTEGER NOT NULL, `set_id` INTEGER NOT NULL, PRIMARY KEY(`workout_id`, `exercise_id`, `set_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Sets` (`set_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workout_id` INTEGER NOT NULL, `exercise_id` INTEGER NOT NULL, `date` TEXT, `repetitions` INTEGER NOT NULL, `weight` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '65b9c2738dbe908779f78246cf4b7fed')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `exercises`");
        _db.execSQL("DROP TABLE IF EXISTS `workouts`");
        _db.execSQL("DROP TABLE IF EXISTS `WorkoutExerciseSetCrossRef`");
        _db.execSQL("DROP TABLE IF EXISTS `Sets`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsExercises = new HashMap<String, TableInfo.Column>(5);
        _columnsExercises.put("exercise_id", new TableInfo.Column("exercise_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("rep", new TableInfo.Column("rep", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("weight", new TableInfo.Column("weight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExercises = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExercises = new TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises);
        final TableInfo _existingExercises = TableInfo.read(_db, "exercises");
        if (! _infoExercises.equals(_existingExercises)) {
          return new RoomOpenHelper.ValidationResult(false, "exercises(com.example.giga_stats.DB.ENTITY.Exercise).\n"
                  + " Expected:\n" + _infoExercises + "\n"
                  + " Found:\n" + _existingExercises);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkouts = new HashMap<String, TableInfo.Column>(2);
        _columnsWorkouts.put("workout_id", new TableInfo.Column("workout_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkouts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkouts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkouts = new TableInfo("workouts", _columnsWorkouts, _foreignKeysWorkouts, _indicesWorkouts);
        final TableInfo _existingWorkouts = TableInfo.read(_db, "workouts");
        if (! _infoWorkouts.equals(_existingWorkouts)) {
          return new RoomOpenHelper.ValidationResult(false, "workouts(com.example.giga_stats.DB.ENTITY.Workout).\n"
                  + " Expected:\n" + _infoWorkouts + "\n"
                  + " Found:\n" + _existingWorkouts);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkoutExerciseSetCrossRef = new HashMap<String, TableInfo.Column>(3);
        _columnsWorkoutExerciseSetCrossRef.put("workout_id", new TableInfo.Column("workout_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutExerciseSetCrossRef.put("exercise_id", new TableInfo.Column("exercise_id", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutExerciseSetCrossRef.put("set_id", new TableInfo.Column("set_id", "INTEGER", true, 3, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkoutExerciseSetCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkoutExerciseSetCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutExerciseSetCrossRef = new TableInfo("WorkoutExerciseSetCrossRef", _columnsWorkoutExerciseSetCrossRef, _foreignKeysWorkoutExerciseSetCrossRef, _indicesWorkoutExerciseSetCrossRef);
        final TableInfo _existingWorkoutExerciseSetCrossRef = TableInfo.read(_db, "WorkoutExerciseSetCrossRef");
        if (! _infoWorkoutExerciseSetCrossRef.equals(_existingWorkoutExerciseSetCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "WorkoutExerciseSetCrossRef(com.example.giga_stats.DB.ENTITY.WorkoutExerciseSetCrossRef).\n"
                  + " Expected:\n" + _infoWorkoutExerciseSetCrossRef + "\n"
                  + " Found:\n" + _existingWorkoutExerciseSetCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsSets = new HashMap<String, TableInfo.Column>(6);
        _columnsSets.put("set_id", new TableInfo.Column("set_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSets.put("workout_id", new TableInfo.Column("workout_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSets.put("exercise_id", new TableInfo.Column("exercise_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSets.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSets.put("repetitions", new TableInfo.Column("repetitions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSets.put("weight", new TableInfo.Column("weight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSets = new TableInfo("Sets", _columnsSets, _foreignKeysSets, _indicesSets);
        final TableInfo _existingSets = TableInfo.read(_db, "Sets");
        if (! _infoSets.equals(_existingSets)) {
          return new RoomOpenHelper.ValidationResult(false, "Sets(com.example.giga_stats.DB.ENTITY.Sets).\n"
                  + " Expected:\n" + _infoSets + "\n"
                  + " Found:\n" + _existingSets);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "65b9c2738dbe908779f78246cf4b7fed", "bfe4419b27163f8d2e6e006e049f91fa");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "exercises","workouts","WorkoutExerciseSetCrossRef","Sets");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `exercises`");
      _db.execSQL("DELETE FROM `workouts`");
      _db.execSQL("DELETE FROM `WorkoutExerciseSetCrossRef`");
      _db.execSQL("DELETE FROM `Sets`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ExerciseDAO.class, ExerciseDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutDAO.class, WorkoutDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(SetsDAO.class, SetsDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ExerciseDAO exerciseDao() {
    if (_exerciseDAO != null) {
      return _exerciseDAO;
    } else {
      synchronized(this) {
        if(_exerciseDAO == null) {
          _exerciseDAO = new ExerciseDAO_Impl(this);
        }
        return _exerciseDAO;
      }
    }
  }

  @Override
  public WorkoutDAO workoutDao() {
    if (_workoutDAO != null) {
      return _workoutDAO;
    } else {
      synchronized(this) {
        if(_workoutDAO == null) {
          _workoutDAO = new WorkoutDAO_Impl(this);
        }
        return _workoutDAO;
      }
    }
  }

  @Override
  public SetsDAO setDao() {
    if (_setsDAO != null) {
      return _setsDAO;
    } else {
      synchronized(this) {
        if(_setsDAO == null) {
          _setsDAO = new SetsDAO_Impl(this);
        }
        return _setsDAO;
      }
    }
  }
}
