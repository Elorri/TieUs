package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 26/04/2016.
 */
public class VectorDAO {

    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.VectorTable.NAME +
            "(" + FriendForecastContract.VectorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.VectorTable.COLUMN_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.VectorTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";



    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.VectorTable.NAME
            + " ("
            + FriendForecastContract.VectorTable._ID + ", "
            + FriendForecastContract.VectorTable.COLUMN_NAME
            + ") " + "VALUES (?, ?)";

    public interface VectorQuery {

        int LOADER_ID = 0;

        int COL_ID = 0;
        int COL_VECTOR_NAME = 1;

        String SELECTION = FriendForecastContract.VectorTable._ID + "=?";

        String SORT_ORDER = FriendForecastContract.VectorTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                FriendForecastContract.VectorTable._ID,
                FriendForecastContract.VectorTable.COLUMN_NAME
        };

    }
}
