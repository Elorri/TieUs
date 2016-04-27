package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 26/04/2016.
 */
public class TemplateDAO {

    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.TemplateTable.NAME +
            "(" + FriendForecastContract.TemplateTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.TemplateTable.COLUMN_NAME + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.TemplateTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";



    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.TemplateTable.NAME
            + " ("
            + FriendForecastContract.TemplateTable._ID + ", "
            + FriendForecastContract.TemplateTable.COLUMN_NAME
            + ") " + "VALUES (?, ?)";

    public interface TemplateQuery {

        int LOADER_ID = 0;

        int COL_ID = 0;
        int COL_TEMPLATE_NAME = 1;

        String SELECTION = FriendForecastContract.TemplateTable._ID + "=?";

        String SORT_ORDER = FriendForecastContract.TemplateTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                FriendForecastContract.TemplateTable._ID,
                FriendForecastContract.TemplateTable.COLUMN_NAME
        };

    }
}
