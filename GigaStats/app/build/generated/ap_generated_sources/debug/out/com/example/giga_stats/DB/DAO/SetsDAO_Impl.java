package com.example.giga_stats.DB.DAO;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.giga_stats.DB.ENTITY.Sets;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SetsDAO_Impl implements SetsDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Sets> __insertionAdapterOfSets;

  public SetsDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSets = new EntityInsertionAdapter<Sets>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Sets` (`set_id`,`workout_id`,`exercise_id`,`date`,`repetitions`,`weight`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Sets value) {
        stmt.bindLong(1, value.set_id);
        stmt.bindLong(2, value.workout_id);
        stmt.bindLong(3, value.exercise_id);
        if (value.date == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.date);
        }
        stmt.bindLong(5, value.repetitions);
        stmt.bindLong(6, value.weight);
      }
    };
  }

  @Override
  public void insertSet(final Sets set) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSets.insert(set);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Sets>> getSetsForExerciseAndWorkout(final long workoutId,
      final long exerciseId) {
    final String _sql = "SELECT * FROM Sets WHERE workout_id = ? AND exercise_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workoutId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, exerciseId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Sets"}, false, new Callable<List<Sets>>() {
      @Override
      public List<Sets> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSetId = CursorUtil.getColumnIndexOrThrow(_cursor, "set_id");
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workout_id");
          final int _cursorIndexOfExerciseId = CursorUtil.getColumnIndexOrThrow(_cursor, "exercise_id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "repetitions");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final List<Sets> _result = new ArrayList<Sets>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Sets _item;
            _item = new Sets();
            _item.set_id = _cursor.getLong(_cursorIndexOfSetId);
            _item.workout_id = _cursor.getLong(_cursorIndexOfWorkoutId);
            _item.exercise_id = _cursor.getLong(_cursorIndexOfExerciseId);
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _item.date = null;
            } else {
              _item.date = _cursor.getString(_cursorIndexOfDate);
            }
            _item.repetitions = _cursor.getInt(_cursorIndexOfRepetitions);
            _item.weight = _cursor.getInt(_cursorIndexOfWeight);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
