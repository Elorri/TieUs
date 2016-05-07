package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.data.Projections;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {

    public static final int ALL_ACTIONS = 0;
    public static final int ACTION_BY_ID = 1;
    public static final int ACTIONS_TITLES = 2;

    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ActionTable.NAME +
            "(" + FriendForecastContract.ActionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ActionTable.COLUMN_TITLE + " TEXT NOT NULL, "
            + FriendForecastContract.ActionTable.COLUMN_NAME + " TEXT NOT NULL, "
            + FriendForecastContract.ActionTable.COLUMN_SORT_ORDER + " INTEGER NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ActionTable.COLUMN_TITLE + ", "
            + FriendForecastContract.ActionTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";


    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.ActionTable.NAME
            + " ("
            + FriendForecastContract.ActionTable._ID + ", "
            + FriendForecastContract.ActionTable.COLUMN_TITLE + ", "
            + FriendForecastContract.ActionTable.COLUMN_NAME + ", "
            + FriendForecastContract.ActionTable.COLUMN_SORT_ORDER
            + ") " + "VALUES (?, ?, ?, ?)";

    public static Cursor getCursorActionsWithTitle(SQLiteDatabase db) {
        ArrayList<Cursor> cursors = new ArrayList();

        Cursor cursorTitles = db.query(true, FriendForecastContract.ActionTable.NAME,
                DistinctActionTitleQuery.PROJECTION,
                null, null, null, null, DistinctActionTitleQuery.SORT_ORDER, null);
        try {
            while (cursorTitles.moveToNext()) {
                String title = cursorTitles.getString(DistinctActionTitleQuery.COL_ACTION_TITLE);
                cursors.add(Tools.getOneLineCursor(title, Projections.VIEW_TITLE));
                cursors.add(ActionDAO.getCursor(ActionDAO.ACTIONS_TITLES, db, null, title));
            }
        } finally {
            if (cursorTitles != null) cursorTitles.close();
        }
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    public interface ActionQuery {

        int COL_ID = 0;
        int COL_ACTION_TITLE = 1;
        int COL_ACTION_NAME = 2;
        int COL_ACTION_SORT_ORDER = 3;
        int COL_PROJECTION_TYPE = 4;

        String SELECTION_BY_ACTION_ID = FriendForecastContract.ActionTable._ID + "=?";
        String SELECTION_BY_TITLE = FriendForecastContract.ActionTable.COLUMN_TITLE + "=?";

        String SORT_ORDER = FriendForecastContract.ActionTable.COLUMN_SORT_ORDER + " asc";

        String[] PROJECTION = {
                FriendForecastContract.ActionTable._ID,
                FriendForecastContract.ActionTable.COLUMN_TITLE,
                FriendForecastContract.ActionTable.COLUMN_NAME,
                FriendForecastContract.ActionTable.COLUMN_SORT_ORDER,
                Projections.VIEW_ACTION + " as " + Projections.COLUMN_PROJECTION_TYPE
        };

    }

    public interface DistinctActionTitleQuery {

        int COL_ACTION_TITLE = 0;

        String[] PROJECTION = {FriendForecastContract.ActionTable.COLUMN_TITLE};

        String SELECTION = FriendForecastContract.ActionTable._ID + "=?";

        String SORT_ORDER = FriendForecastContract.ActionTable.COLUMN_SORT_ORDER + " asc";

        String SELECT_TITLE = "select distinct"
                + FriendForecastContract.ActionTable.COLUMN_TITLE + " from "
                + FriendForecastContract.ActionTable.NAME;

    }


    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case ALL_ACTIONS:
                return context.getResources().getString(R.string.select_action);
            default:
                return null;
        }
    }

    //TODO virer toutes les methodes getCursorWithViewTypes CursorUtils.setViewType est plus
    // explicite


    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String actionId, String
            title) {
        switch (cursorType) {
            case ALL_ACTIONS: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "QUERY ALL_ACTIONS");
                return db.query(FriendForecastContract.ActionTable.NAME,
                        ActionQuery.PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTION_BY_ID: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "QUERY ACTION_BY_ID");
                return db.query(FriendForecastContract.ActionTable.NAME,
                        ActionQuery.PROJECTION,
                        ActionQuery.SELECTION_BY_ACTION_ID,
                        new String[]{actionId},
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTIONS_TITLES: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "QUERY ACTION_BY_ID");
                return db.query(FriendForecastContract.ActionTable.NAME,
                        ActionQuery.PROJECTION,
                        ActionQuery.SELECTION_BY_TITLE,
                        new String[]{title},
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            default:
                return null;
        }
    }
}
