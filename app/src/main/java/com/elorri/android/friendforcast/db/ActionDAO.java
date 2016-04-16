package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {




    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ActionTable.NAME +
            "(" + FriendForecastContract.ActionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ActionTable.COLUMN_NAME + " TEXT NOT NULL)";



    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.ActionTable.NAME
            + " ("
            + FriendForecastContract.ActionTable._ID + ", "
            + FriendForecastContract.ActionTable.COLUMN_NAME
            + ") " + "VALUES (?, ?)";


}
