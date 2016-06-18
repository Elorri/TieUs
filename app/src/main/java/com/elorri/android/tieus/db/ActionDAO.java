/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.tieus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 11/04/2016.
 * This class contains useful info for quering table Action
 */
public class ActionDAO {

    public static final int ACTION_BY_ID = 1;
    private static final int ACTIONS_TITLES = 2;

    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.ActionTable.NAME +
            "(" + TieUsContract.ActionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID + " INTEGER NOT NULL, "
            + TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + " INTEGER NOT NULL, "
            + TieUsContract.ActionTable.COLUMN_SORT_ORDER + " INTEGER NOT NULL, "
            + "UNIQUE (" + TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID + ", "
            + TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + ") ON CONFLICT REPLACE)";


    /**
     * This method return a cursor composed of a row with a tag name, followed rows reprensenting
     * the actions related to this tag, followed by another tag and its actions and so on
     * @param context
     * @param db
     * @return a MergeCursor
     */
    public static Cursor getCursorActionsWithTagName(Context context, SQLiteDatabase db) {
        ArrayList<Cursor> cursors = new ArrayList();

        //Get a list of distinct action tags titles
        Cursor cursorTitles = db.query(true, TieUsContract.ActionTable.NAME,
                DistinctActionTitleQuery.PROJECTION,
                null, null, null, null, DistinctActionTitleQuery.SORT_ORDER, null);
        try {
            //for each titles get the corresponding actions
            while (cursorTitles.moveToNext()) {
                int title = cursorTitles.getInt(DistinctActionTitleQuery.COL_ACTION_TITLE_RESOURCE_ID);
                cursors.add(MatrixCursors.getOneLineCursor(
                        MatrixCursors.TitleQuery.PROJECTION,
                        MatrixCursors.TitleQuery.VALUES,
                        context.getResources().getString(title)));
                cursors.add(ActionDAO.getCursor(ActionDAO.ACTIONS_TITLES, db, null, String
                        .valueOf(title)));
            }
        } finally {
            if (cursorTitles != null) cursorTitles.close();
        }
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    public interface ActionQuery {

        int COL_ID = 0;
        int COL_ACTION_NAME_RESOURCE_ID = 2;


        String SELECTION_BY_ACTION_ID = TieUsContract.ActionTable._ID + "=?";
        String SELECTION_BY_TITLE = TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID + "=?";

        String SORT_ORDER = TieUsContract.ActionTable.COLUMN_SORT_ORDER + " asc";

        String[] PROJECTION = {
                TieUsContract.ActionTable._ID,
                TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID,
                TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID,
                TieUsContract.ActionTable.COLUMN_SORT_ORDER,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_QUERY = {
                TieUsContract.ActionTable._ID,
                TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID,
                TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID,
                TieUsContract.ActionTable.COLUMN_SORT_ORDER,
                ViewTypes.VIEW_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

    }

    interface DistinctActionTitleQuery {

        int COL_ACTION_TITLE_RESOURCE_ID = 0;
        String[] PROJECTION = {TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID};
        String SORT_ORDER = TieUsContract.ActionTable.COLUMN_SORT_ORDER + " asc";

    }

        public static Cursor getCursor(int cursorType, SQLiteDatabase db, String actionId, String
            title) {
        switch (cursorType) {
            case ACTION_BY_ID: {
                return db.query(TieUsContract.ActionTable.NAME,
                        ActionQuery.PROJECTION_QUERY,
                        ActionQuery.SELECTION_BY_ACTION_ID,
                        new String[]{actionId},
                        null,
                        null,
                        ActionQuery.SORT_ORDER);
            }
            case ACTIONS_TITLES: {
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


    private static ContentValues getLine(int titleResource, int nameResource, int sortOrder) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(TieUsContract.ActionTable.COLUMN_TAG_TITLE_RESOURCE_ID, titleResource);
        contentValue.put(TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID, nameResource);
        contentValue.put(TieUsContract.ActionTable.COLUMN_SORT_ORDER, sortOrder);
        return contentValue;
    }

    public static ContentValues[] getStartData() {
        ContentValues[] contentValues = new ContentValues[12];
        contentValues[0] = getLine(R.string.action_title1, R.string.action_name1, 1);
        contentValues[1] = getLine(R.string.action_title1, R.string.action_name2, 2);
        contentValues[2] = getLine(R.string.action_title2, R.string.action_name3, 3);
        contentValues[3] = getLine(R.string.action_title2, R.string.action_name4, 4);
        contentValues[4] = getLine(R.string.action_title2, R.string.action_name5, 5);
        contentValues[5] = getLine(R.string.action_title2, R.string.action_name6, 6);
        contentValues[6] = getLine(R.string.action_title3, R.string.action_name7, 7);
        contentValues[7] = getLine(R.string.action_title3, R.string.action_name8, 8);
        contentValues[8] = getLine(R.string.action_title3, R.string.action_name9, 9);
        contentValues[9] = getLine(R.string.action_title4, R.string.action_name10, 10);
        contentValues[10] = getLine(R.string.action_title4, R.string.action_name11, 11);
        contentValues[11] = getLine(R.string.action_title4, R.string.action_name12, 12);
        return contentValues;
    }

}
