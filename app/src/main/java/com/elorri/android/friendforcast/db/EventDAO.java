package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendCastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class EventDAO {

    public static final String CREATE = "CREATE TABLE "
            + FriendCastContract.EventEntry.NAME +
            "(" + FriendCastContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + " INTEGER NOT NULL,"
            + FriendCastContract.EventEntry.COLUMN_ACTION_ID + " INTEGER NOT NULL,"
            + FriendCastContract.EventEntry.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + FriendCastContract.EventEntry.COLUMN_TIME_END + " INTEGER)";

        public static final String INSERT = "INSERT INTO "
                + FriendCastContract.EventEntry.NAME + " ("
                + FriendCastContract.EventEntry._ID + ", "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID+", "
                + FriendCastContract.EventEntry.COLUMN_ACTION_ID+", "
                + FriendCastContract.EventEntry.COLUMN_TIME_START+", "
                + FriendCastContract.EventEntry.COLUMN_TIME_END+") "
                + "VALUES (?, ?, ?, ?, ?)";
}
