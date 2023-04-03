package com.example.progettomobile_07_05.Database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class UserDAO_Impl implements UserDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  public UserDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `user` (`email`,`password`,`name`,`surname`,`telephone_number`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getEmail() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEmail());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPassword());
        }
        if (value.getNameUser() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNameUser());
        }
        if (value.getSurnameUser() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSurnameUser());
        }
        if (value.getTelephoneNumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTelephoneNumber());
        }
      }
    };
  }

  @Override
  public void addUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<User>> getUsers() {
    final String _sql = "SELECT * FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"user"}, true, new Callable<List<User>>() {
      @Override
      public List<User> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
          try {
            final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
            final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
            final int _cursorIndexOfNameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfSurnameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
            final int _cursorIndexOfTelephoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone_number");
            final List<User> _result = new ArrayList<User>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final User _item;
              final String _tmpEmail;
              if (_cursor.isNull(_cursorIndexOfEmail)) {
                _tmpEmail = null;
              } else {
                _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
              }
              final String _tmpPassword;
              if (_cursor.isNull(_cursorIndexOfPassword)) {
                _tmpPassword = null;
              } else {
                _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
              }
              final String _tmpNameUser;
              if (_cursor.isNull(_cursorIndexOfNameUser)) {
                _tmpNameUser = null;
              } else {
                _tmpNameUser = _cursor.getString(_cursorIndexOfNameUser);
              }
              final String _tmpSurnameUser;
              if (_cursor.isNull(_cursorIndexOfSurnameUser)) {
                _tmpSurnameUser = null;
              } else {
                _tmpSurnameUser = _cursor.getString(_cursorIndexOfSurnameUser);
              }
              final String _tmpTelephoneNumber;
              if (_cursor.isNull(_cursorIndexOfTelephoneNumber)) {
                _tmpTelephoneNumber = null;
              } else {
                _tmpTelephoneNumber = _cursor.getString(_cursorIndexOfTelephoneNumber);
              }
              _item = new User(_tmpEmail,_tmpPassword,_tmpNameUser,_tmpSurnameUser,_tmpTelephoneNumber);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<User> getUserForLogin(final String email, final String password) {
    final String _sql = "SELECT * FROM user WHERE email LIKE ? AND password LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"user"}, false, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfNameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurnameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfTelephoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone_number");
          final User _result;
          if(_cursor.moveToFirst()) {
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpPassword;
            if (_cursor.isNull(_cursorIndexOfPassword)) {
              _tmpPassword = null;
            } else {
              _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            }
            final String _tmpNameUser;
            if (_cursor.isNull(_cursorIndexOfNameUser)) {
              _tmpNameUser = null;
            } else {
              _tmpNameUser = _cursor.getString(_cursorIndexOfNameUser);
            }
            final String _tmpSurnameUser;
            if (_cursor.isNull(_cursorIndexOfSurnameUser)) {
              _tmpSurnameUser = null;
            } else {
              _tmpSurnameUser = _cursor.getString(_cursorIndexOfSurnameUser);
            }
            final String _tmpTelephoneNumber;
            if (_cursor.isNull(_cursorIndexOfTelephoneNumber)) {
              _tmpTelephoneNumber = null;
            } else {
              _tmpTelephoneNumber = _cursor.getString(_cursorIndexOfTelephoneNumber);
            }
            _result = new User(_tmpEmail,_tmpPassword,_tmpNameUser,_tmpSurnameUser,_tmpTelephoneNumber);
          } else {
            _result = null;
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

  @Override
  public User getUserEmail(final String email) {
    final String _sql = "SELECT * FROM user WHERE email LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
      try {
        final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
        final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
        final int _cursorIndexOfNameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
        final int _cursorIndexOfSurnameUser = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
        final int _cursorIndexOfTelephoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone_number");
        final User _result;
        if(_cursor.moveToFirst()) {
          final String _tmpEmail;
          if (_cursor.isNull(_cursorIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
          }
          final String _tmpPassword;
          if (_cursor.isNull(_cursorIndexOfPassword)) {
            _tmpPassword = null;
          } else {
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
          }
          final String _tmpNameUser;
          if (_cursor.isNull(_cursorIndexOfNameUser)) {
            _tmpNameUser = null;
          } else {
            _tmpNameUser = _cursor.getString(_cursorIndexOfNameUser);
          }
          final String _tmpSurnameUser;
          if (_cursor.isNull(_cursorIndexOfSurnameUser)) {
            _tmpSurnameUser = null;
          } else {
            _tmpSurnameUser = _cursor.getString(_cursorIndexOfSurnameUser);
          }
          final String _tmpTelephoneNumber;
          if (_cursor.isNull(_cursorIndexOfTelephoneNumber)) {
            _tmpTelephoneNumber = null;
          } else {
            _tmpTelephoneNumber = _cursor.getString(_cursorIndexOfTelephoneNumber);
          }
          _result = new User(_tmpEmail,_tmpPassword,_tmpNameUser,_tmpSurnameUser,_tmpTelephoneNumber);
        } else {
          _result = null;
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
