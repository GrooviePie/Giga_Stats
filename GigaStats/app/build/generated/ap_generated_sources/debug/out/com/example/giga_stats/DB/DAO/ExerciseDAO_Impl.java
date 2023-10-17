package com.example.giga_stats.DB.DAO;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.giga_stats.DB.ENTITY.Exercise;
import com.example.giga_stats.DB.ENTITY.WorkoutExerciseSetCrossRef;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExerciseDAO_Impl implements ExerciseDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Exercise> __insertionAdapterOfExercise;

  private final EntityInsertionAdapter<WorkoutExerciseSetCrossRef> __insertionAdapterOfWorkoutExerciseSetCrossRef;

  private final EntityDeletionOrUpdateAdapter<Exercise> __deletionAdapterOfExercise;

  private final EntityDeletionOrUpdateAdapter<Exercise> __updateAdapterOfExercise;

  public ExerciseDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExercise = new EntityInsertionAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `exercises` (`exercise_id`,`name`,`category`,`rep`,`weight`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getExercise_id());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        stmt.bindLong(4, value.getRep());
        stmt.bindLong(5, value.getWeight());
      }
    };
    this.__insertionAdapterOfWorkoutExerciseSetCrossRef = new EntityInsertionAdapter<WorkoutExerciseSetCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `WorkoutExerciseSetCrossRef` (`workout_id`,`exercise_id`,`set_id`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WorkoutExerciseSetCrossRef value) {
        stmt.bindLong(1, value.workout_id);
        stmt.bindLong(2, value.exercise_id);
        stmt.bindLong(3, value.set_id);
      }
    };
    this.__deletionAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `exercises` WHERE `exercise_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getExercise_id());
      }
    };
    this.__updateAdapterOfExercise = new EntityDeletionOrUpdateAdapter<Exercise>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `exercises` SET `exercise_id` = ?,`name` = ?,`category` = ?,`rep` = ?,`weight` = ? WHERE `exercise_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Exercise value) {
        stmt.bindLong(1, value.getExercise_id());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        stmt.bindLong(4, value.getRep());
        stmt.bindLong(5, value.getWeight());
        stmt.bindLong(6, value.getExercise_id());
      }
    };
  }

  @Override
  public void insertExercise(final Exercise exercise) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExercise.insert(exercise);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertCrossRef(final WorkoutExerciseSetCrossRef crossRef) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWorkoutExerciseSetCrossRef.insert(crossRef);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteExercise(final Exercise exercise) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExercise.handle(exercise);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateExercise(final Exercise exercise) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExercise.handle(exercise);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Exercise> getAllExercises() {
    final String _sql = "SELECT * FROM exercises";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfRep = CursorUtil.getColumnIndexOrThrow(_cursor, "rep");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Exercise _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final int _tmpRep;
        _tmpRep = _cursor.getInt(_cursorIndexOfRep);
        final int _tmpWeight;
        _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
        _item = new Exercise(_tmpName,_tmpCategory,_tmpRep,_tmpWeight);
        final int _tmpExercise_id;
        _tmpExercise_id = _cursor.getInt(_cursorIndexOfExerciseId);
        _item.setExercise_id(_tmpExercise_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Exercise> getExerciseById(final int id) {
    final String _sql = "SELECT * FROM exercises WHERE exercise_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfRep = CursorUtil.getColumnIndexOrThrow(_cursor, "rep");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final List<Exercise> _result = new ArrayList<Exercise>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Exercise _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final int _tmpRep;
        _tmpRep = _cursor.getInt(_cursorIndexOfRep);
        final int _tmpWeight;
        _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
        _item = new Exercise(_tmpName,_tmpCategory,_tmpRep,_tmpWeight);
        final int _tmpExercise_id;
        _tmpExercise_id = _cursor.getInt(_cursorIndexOfExerciseId);
        _item.setExercise_id(_tmpExercise_id);
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
