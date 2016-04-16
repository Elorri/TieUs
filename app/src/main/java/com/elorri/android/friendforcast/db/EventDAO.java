package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class EventDAO {

    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.EventEntry.NAME +
            "(" + FriendForecastContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + " INTEGER NOT NULL,"
            + FriendForecastContract.EventEntry.COLUMN_ACTION_ID + " INTEGER NOT NULL,"
            + FriendForecastContract.EventEntry.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + FriendForecastContract.EventEntry.COLUMN_TIME_END + " INTEGER)";

        public static final String INSERT = "INSERT INTO "
                + FriendForecastContract.EventEntry.NAME + " ("
                + FriendForecastContract.EventEntry._ID + ", "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID+", "
                + FriendForecastContract.EventEntry.COLUMN_ACTION_ID+", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START+", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END+") "
                + "VALUES (?, ?, ?, ?, ?)";
}
