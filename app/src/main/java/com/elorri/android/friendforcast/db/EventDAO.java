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
            + FriendForecastContract.EventTable.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + FriendForecastContract.EventTable.COLUMN_TIME_END + " INTEGER, "
            + "UNIQUE (" + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ") ON CONFLICT REPLACE)";

    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.EventTable.NAME + " ("
            + FriendForecastContract.EventTable._ID + ", "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ") "
            + "VALUES (?, ?, ?, ?, ?)";


    public static ContentValues getContentValues(String contactId, String actionId, long date) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.EventTable.COLUMN_CONTACT_ID, contactId);
        values.put(FriendForecastContract.EventTable.COLUMN_ACTION_ID, actionId);
        values.put(FriendForecastContract.EventTable.COLUMN_TIME_START, date);
        return values;

    }
}
