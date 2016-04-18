package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {

    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ActionTable.NAME +
            "(" + FriendForecastContract.ActionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ActionTable.COLUMN_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ActionTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";



    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.ActionTable.NAME
            + " ("
            + FriendForecastContract.ActionTable._ID + ", "
            + FriendForecastContract.ActionTable.COLUMN_NAME
            + ") " + "VALUES (?, ?)";

    public interface ActionQuery {

        int COL_ID = 0;
        int COL_ACTION_NAME = 1;

        String SELECTION = FriendForecastContract.ActionTable._ID + "=?";

        String SORT_ORDER = FriendForecastContract.ActionTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                FriendForecastContract.ActionTable._ID,
                FriendForecastContract.ActionTable.COLUMN_NAME
        };

    }
}
