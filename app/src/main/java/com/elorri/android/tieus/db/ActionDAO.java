package com.elorri.android.tieus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {

    public static final int ALL_ACTIONS = 0;
    public static final int ACTION_BY_ID = 1;
    public static final int ACTIONS_TITLES = 2;

    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.ActionTable.NAME +
            "(" + TieUsContract.ActionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TieUsContract.ActionTable.COLUMN_TITLE + " TEXT NOT NULL, "
            + TieUsContract.ActionTable.COLUMN_NAME + " TEXT NOT NULL, "
            + TieUsContract.ActionTable.COLUMN_SORT_ORDER + " INTEGER NOT NULL, "
            + "UNIQUE (" + TieUsContract.ActionTable.COLUMN_TITLE + ", "
            + TieUsContract.ActionTable.COLUMN_NAME + ") ON CONFLICT REPLACE)";


    public static final String INSERT = "INSERT INTO "
            + TieUsContract.ActionTable.NAME
            + " ("
            + TieUsContract.ActionTable._ID + ", "
            + TieUsContract.ActionTable.COLUMN_TITLE + ", "
            + TieUsContract.ActionTable.COLUMN_NAME + ", "
            + TieUsContract.ActionTable.COLUMN_SORT_ORDER
            + ") " + "VALUES (?, ?, ?, ?)";

    public static Cursor getCursorActionsWithTitle(SQLiteDatabase db) {
        ArrayList<Cursor> cursors = new ArrayList();

        Cursor cursorTitles = db.query(true, TieUsContract.ActionTable.NAME,
                DistinctActionTitleQuery.PROJECTION,
                null, null, null, null, DistinctActionTitleQuery.SORT_ORDER, null);
        try {
            while (cursorTitles.moveToNext()) {
                String title = cursorTitles.getString(DistinctActionTitleQuery.COL_ACTION_TITLE);
                cursors.add(MatrixCursors.getOneLineCursor(
                        MatrixCursors.TitleQuery.PROJECTION,
                        MatrixCursors.TitleQuery.VALUES,
                        title));
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

        String SELECTION_BY_ACTION_ID = TieUsContract.ActionTable._ID + "=?";
        String SELECTION_BY_TITLE = TieUsContract.ActionTable.COLUMN_TITLE + "=?";

        String SORT_ORDER = TieUsContract.ActionTable.COLUMN_SORT_ORDER + " asc";

        String[] PROJECTION = {
                TieUsContract.ActionTable._ID,
                TieUsContract.ActionTable.COLUMN_TITLE,
                TieUsContract.ActionTable.COLUMN_NAME,
                TieUsContract.ActionTable.COLUMN_SORT_ORDER,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_QUERY = {
                TieUsContract.ActionTable._ID,
                TieUsContract.ActionTable.COLUMN_TITLE,
                TieUsContract.ActionTable.COLUMN_NAME,
                TieUsContract.ActionTable.COLUMN_SORT_ORDER,
                ViewTypes.VIEW_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

    }

    public interface DistinctActionTitleQuery {

        int COL_ACTION_TITLE = 0;

        String[] PROJECTION = {TieUsContract.ActionTable.COLUMN_TITLE};

        String SELECTION = TieUsContract.ActionTable._ID + "=?";

        String SORT_ORDER = TieUsContract.ActionTable.COLUMN_SORT_ORDER + " asc";

        String SELECT_TITLE = "select distinct"
                + TieUsContract.ActionTable.COLUMN_TITLE + " from "
                + TieUsContract.ActionTable.NAME;

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
                return db.query(TieUsContract.ActionTable.NAME,
                        ActionQuery.PROJECTION_QUERY,
                        null,
                        null,
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTION_BY_ID: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "QUERY ACTION_BY_ID");
                return db.query(TieUsContract.ActionTable.NAME,
                        ActionQuery.PROJECTION_QUERY,
                        ActionQuery.SELECTION_BY_ACTION_ID,
                        new String[]{actionId},
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTIONS_TITLES: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "QUERY ACTION_BY_ID");
                return db.query(TieUsContract.ActionTable.NAME,
                        ActionQuery.PROJECTION_QUERY,
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
