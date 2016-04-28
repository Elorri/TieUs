package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.AddActionFragment;

import java.util.ArrayList;

/**
 * Created by Elorri on 26/04/2016.
 */
public class VectorDAO {

    public static final int ALL_VECTORS = 0;

    public static  final String CREATE = "CREATE TABLE "
            + FriendForecastContract.VectorTable.NAME +
            "(" + FriendForecastContract.VectorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.VectorTable.COLUMN_NAME + " TEXT NOT NULL, "
            + FriendForecastContract.VectorTable.COLUMN_LOGO_ID + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.VectorTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";



    public interface VectorQuery {

        int LOADER_ID = 0;

        int COL_ID = 0;
        int COL_VECTOR_NAME = 1;
        int COL_RESSOURCE_ID = 2;

        String SELECTION = FriendForecastContract.VectorTable._ID + "=?";

        String SORT_ORDER = FriendForecastContract.VectorTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                FriendForecastContract.VectorTable._ID,
                FriendForecastContract.VectorTable.COLUMN_NAME,
                FriendForecastContract.VectorTable.COLUMN_LOGO_ID
        };

    }


    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(AddActionFragment.AddActionAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(cursorType, db, viewTypes));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case ALL_VECTORS:
                return context.getResources().getString(R.string.select_vector);
            default:
                return null;
        }
    }

    public static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes) {

        switch (cursorType) {
            case ALL_VECTORS:
                return CursorUtils.setViewType(
                        getCursor(cursorType, db, null),
                        viewTypes, AddActionFragment.AddActionAdapter.VIEW_VECTOR_ITEM);
            default:
                return null;
        }
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String actionId) {
        switch (cursorType) {
            case ALL_VECTORS: {
                return db.query(FriendForecastContract.VectorTable.NAME,
                        VectorQuery.PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        VectorQuery.SORT_ORDER);
            }
            default:
                return null;
        }
    }
}
