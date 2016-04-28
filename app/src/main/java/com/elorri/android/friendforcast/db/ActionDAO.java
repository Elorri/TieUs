package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.AddActionAdapter;

import java.util.ArrayList;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {

    public static final int ALL_ACTIONS = 0;
    public static final int ACTION_BY_ID = 1;

    public static final String CREATE = "CREATE TABLE "
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


    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(AddActionAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(cursorType, db, viewTypes, null));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
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
    public static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes, String actionId) {

        switch (cursorType) {
            case ALL_ACTIONS:
                return CursorUtils.setViewType(
                        getCursor(cursorType, db, null),
                        viewTypes, AddActionAdapter.VIEW_ACTION_ITEM);
            default:
                return null;
        }
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String actionId) {
        switch (cursorType) {
            case ALL_ACTIONS: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + ActionQuery.PROJECTION);
                return db.query(FriendForecastContract.ActionTable.NAME,
                        ActionQuery.PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTION_BY_ID: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + ActionQuery.PROJECTION);
                return db.query(FriendForecastContract.ActionTable.NAME,
                        ActionQuery.PROJECTION,
                        ActionQuery.SELECTION,
                        new String[]{actionId},
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            default:
                return null;
        }
    }
}
