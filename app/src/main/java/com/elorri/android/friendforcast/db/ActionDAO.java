package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {


    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ActionEntry.NAME +
            "(" + FriendForecastContract.ActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ActionEntry.COLUMN_NAME + " TEXT NOT NULL)";



    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.ActionEntry.NAME
            + " ("
            + FriendForecastContract.ActionEntry._ID + ", "
            + FriendForecastContract.ActionEntry.COLUMN_NAME
            + ") " + "VALUES (?, ?)";
}
