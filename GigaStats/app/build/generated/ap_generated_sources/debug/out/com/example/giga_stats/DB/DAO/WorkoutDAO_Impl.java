package com.example.giga_stats.DB.DAO;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.giga_stats.DB.ENTITY.Workout;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkoutDAO_Impl implements WorkoutDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Workout> __insertionAdapterOfWorkout;

  private final EntityDeletionOrUpdateAdapter<Workout> __deletionAdapterOfWorkout;

  private final EntityDeletionOrUpdateAdapter<Workout> __updateAdapterOfWorkout;

  public WorkoutDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkout = new EntityInsertionAdapter<Workout>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `workouts` (`workout_id`,`name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Workout value) {
        stmt.bindLong(1, value.workout_id);
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
      }
    };
    this.__deletionAdapterOfWorkout = new EntityDeletionOrUpdateAdapter<Workout>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `workouts` WHERE `workout_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Workout value) {
        stmt.bindLong(1, value.workout_id);
      }
    };
    this.__updateAdapterOfWorkout = new EntityDeletionOrUpdateAdapter<Workout>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `workouts` SET `workout_id` = ?,`name` = ? WHERE `workout_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Workout value) {
        stmt.bindLong(1, value.workout_id);
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.workout_id);
      }
    };
  }

  @Override
  public void insertWorkout(final Workout workout) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWorkout.insert(workout);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteWorkout(final Workout workout) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfWorkout.handle(workout);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateWorkout(final Workout workout) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfWorkout.handle(workout);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Workout> getAllWorkouts() {
    final String _sql = "SELECT * FROM workouts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workout_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Workout _item;
        final int _tmpWorkout_id;
        _tmpWorkout_id = _cursor.getInt(_cursorIndexOfWorkoutId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item = new Workout(_tmpWorkout_id,_tmpName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Workout> getWorkoutById(final int id) {
    final String _sql = "SELECT * FROM workouts WHERE workout_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workout_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final List<Workout> _result = new ArrayList<Workout>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Workout _item;
        final int _tmpWorkout_id;
        _tmpWorkout_id = _cursor.getInt(_cursorIndexOfWorkoutId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item = new Workout(_tmpWorkout_id,_tmpName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
