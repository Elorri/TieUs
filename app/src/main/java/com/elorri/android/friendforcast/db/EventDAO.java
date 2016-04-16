package com.elorri.android.friendforcast.db;

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
            + FriendForecastContract.EventTable.COLUMN_TIME_END + " INTEGER)";

        public static final String INSERT = "INSERT INTO "
                + FriendForecastContract.EventTable.NAME + " ("
                + FriendForecastContract.EventTable._ID + ", "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID+", "
                + FriendForecastContract.EventTable.COLUMN_ACTION_ID+", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START+", "
                + FriendForecastContract.EventTable.COLUMN_TIME_END+") "
                + "VALUES (?, ?, ?, ?, ?)";
}
