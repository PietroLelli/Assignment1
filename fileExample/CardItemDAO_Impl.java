package com.example.progettomobile_07_05.Database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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
public final class CardItemDAO_Impl implements CardItemDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CardItem> __insertionAdapterOfCardItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItem;

  public CardItemDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCardItem = new EntityInsertionAdapter<CardItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `item` (`item_id`,`item_image`,`item_name`,`item_price`,`item_description`,`item_position`,`email`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CardItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getImageResource() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageResource());
        }
        if (value.getProductName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProductName());
        }
        if (value.getProductPrice() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProductPrice());
        }
        if (value.getProductDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProductDescription());
        }
        if (value.getProductPosition() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getProductPosition());
        }
        if (value.getEmailUser() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getEmailUser());
        }
      }
    };
    this.__preparedStmtOfDeleteItem = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM item WHERE item_id = ?";
        return _query;
      }
    };
  }

  @Override
  public void addCardItem(final CardItem cardItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCardItem.insert(cardItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteItem(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItem.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteItem.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CardItem>> getCardItems() {
    final String _sql = "SELECT * FROM item ORDER BY item_id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"item"}, true, new Callable<List<CardItem>>() {
      @Override
      public List<CardItem> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "item_id");
            final int _cursorIndexOfImageResource = CursorUtil.getColumnIndexOrThrow(_cursor, "item_image");
            final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "item_name");
            final int _cursorIndexOfProductPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "item_price");
            final int _cursorIndexOfProductDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "item_description");
            final int _cursorIndexOfProductPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "item_position");
            final int _cursorIndexOfEmailUser = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
            final List<CardItem> _result = new ArrayList<CardItem>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final CardItem _item;
              final String _tmpImageResource;
              if (_cursor.isNull(_cursorIndexOfImageResource)) {
                _tmpImageResource = null;
              } else {
                _tmpImageResource = _cursor.getString(_cursorIndexOfImageResource);
              }
              final String _tmpProductName;
              if (_cursor.isNull(_cursorIndexOfProductName)) {
                _tmpProductName = null;
              } else {
                _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
              }
              final String _tmpProductPrice;
              if (_cursor.isNull(_cursorIndexOfProductPrice)) {
                _tmpProductPrice = null;
              } else {
                _tmpProductPrice = _cursor.getString(_cursorIndexOfProductPrice);
              }
              final String _tmpProductDescription;
              if (_cursor.isNull(_cursorIndexOfProductDescription)) {
                _tmpProductDescription = null;
              } else {
                _tmpProductDescription = _cursor.getString(_cursorIndexOfProductDescription);
              }
              final String _tmpProductPosition;
              if (_cursor.isNull(_cursorIndexOfProductPosition)) {
                _tmpProductPosition = null;
              } else {
                _tmpProductPosition = _cursor.getString(_cursorIndexOfProductPosition);
              }
              final String _tmpEmailUser;
              if (_cursor.isNull(_cursorIndexOfEmailUser)) {
                _tmpEmailUser = null;
              } else {
                _tmpEmailUser = _cursor.getString(_cursorIndexOfEmailUser);
              }
              _item = new CardItem(_tmpImageResource,_tmpProductName,_tmpProductPrice,_tmpProductDescription,_tmpProductPosition,_tmpEmailUser);
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              _item.setId(_tmpId);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
