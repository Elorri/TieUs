package com.elorri.android.friendforcast.db;

import android.content.ContentValues;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class EventDAO {

    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.EventTable.NAME +
            "(" + FriendForecastContract.EventTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + " INTEGER NOT NULL,"
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + " INTEGER NOT NULL,"
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + " INTEGER NOT NULL,"
            + FriendForecastContract.EventTable.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + FriendForecastContract.EventTable.COLUMN_TIME_END + " INTEGER, "
            + "UNIQUE (" + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ") ON CONFLICT REPLACE)";


    public interface EventQuery {

        int COL_ID = 0;
        int COL_CONTACT_ID = 1;
        int COL_ACTION_ID = 2;
        int COL_VECTOR_ID = 2;
        int COL_TIME_START = 3;
        int COL_TIME_END = 4;

        String SELECTION = FriendForecastContract.ContactTable._ID + "=?";

        String[] PROJECTION = {
                FriendForecastContract.EventTable._ID,
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID,
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END
        };

    }


    public static ContentValues getContentValues(String contactId, String actionId, String
            vectorId, long date) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.EventTable.COLUMN_CONTACT_ID, contactId);
        values.put(FriendForecastContract.EventTable.COLUMN_ACTION_ID, actionId);
        values.put(FriendForecastContract.EventTable.COLUMN_VECTOR_ID, vectorId);
        values.put(FriendForecastContract.EventTable.COLUMN_TIME_START, date);
        return values;

    }
}
