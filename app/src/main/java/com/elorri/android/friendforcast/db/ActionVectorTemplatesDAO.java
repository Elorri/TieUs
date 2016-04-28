package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.AddActionAdapter;

import java.util.ArrayList;

/**
 * Created by Elorri on 26/04/2016.
 */
public class ActionVectorTemplatesDAO {

    public static final int TEMPLATE_BY_ACTION_ID_VECTOR_ID = 0;

    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ActionVectorTemplatesTable.NAME +
            "(" + FriendForecastContract.ActionVectorTemplatesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_ACTION_ID + " TEXT NOT NULL,"
            + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VECTOR_ID + " TEXT NOT NULL, "
            + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VECTOR_ID + ", "
            + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE + ") ON CONFLICT REPLACE)";


    public interface ActionVectorTemplatesQuery {

        int LOADER_ID = 0;

        int COL_ID = 0;
        int COL_ACTION_ID = 1;
        int COL_VECTOR_ID = 2;
        int COL_VALUE = 3;


        String SELECTION = FriendForecastContract.ActionVectorTemplatesTable.COLUMN_ACTION_ID +
                "=? and " + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VECTOR_ID + "=?";

        String SORT_ORDER = FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE + " asc";

        String[] PROJECTION = {
                FriendForecastContract.ActionVectorTemplatesTable._ID,
                FriendForecastContract.ActionVectorTemplatesTable.COLUMN_ACTION_ID,
                FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VECTOR_ID,
                FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE
        };
    }


    //TODO remove all getWrappedCursor, getCursorTitle, getCursorWithViewTypes and only keep
    //TODO getOneLineCursor, Cursor.setViewType
    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db, ArrayList<Integer> viewTypes, String actionId, String vectorId) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(AddActionAdapter.VIEW_TITLE);
        cursors.add(CursorUtils.setViewType(
                getCursor(cursorType, db, actionId, vectorId),
                viewTypes, AddActionAdapter.VIEW_TEMPLATE_ITEM));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case TEMPLATE_BY_ACTION_ID_VECTOR_ID:
                return context.getResources().getString(R.string.select_template);
            default:
                return null;
        }
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String actionId, String
            vectorId) {
        switch (cursorType) {
            case TEMPLATE_BY_ACTION_ID_VECTOR_ID: {
                return db.query(FriendForecastContract.VectorTable.NAME,
                        ActionVectorTemplatesQuery.PROJECTION,
                        ActionVectorTemplatesQuery.SELECTION,
                        new String[]{actionId, vectorId},
                        null,
                        null,
                        ActionVectorTemplatesQuery.SORT_ORDER);
            }
            default:
                return null;
        }
    }
}
