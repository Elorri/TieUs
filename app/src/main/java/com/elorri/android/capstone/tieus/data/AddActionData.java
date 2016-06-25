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
package com.elorri.android.capstone.tieus.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.capstone.tieus.db.ActionDAO;
import com.elorri.android.capstone.tieus.db.VectorDAO;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.capstone.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 27/04/2016.
 */
public class AddActionData {

    public static final int LOADER_ID = 0;

    static final int ACTION_SELECT_ACTION = 0;
    static final int ACTION_SELECT_VECTOR = 1;
    static final int ACTION_VALIDATE = 3;


    static Cursor getCursor(Context context, SQLiteDatabase db,
                            int cursorType, String actionId,
                            String vectorId, String timeStart) {
        ArrayList<Cursor> cursors = new ArrayList();
        switch (cursorType) {
            case ACTION_SELECT_ACTION:
                cursors.add(ActionDAO.getCursorActionsWithTagName(context, db));
                break;

            case ACTION_SELECT_VECTOR:
                cursors.add(getActionRecapCursor(ACTION_SELECT_VECTOR, db, actionId, null, null));
                cursors.add(VectorDAO.getCursor(context,db));
                break;

            case ACTION_VALIDATE:
                cursors.add(getActionRecapCursor(ACTION_VALIDATE, db, actionId, vectorId, timeStart));
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getActionRecapCursor(int actionSelectVector, SQLiteDatabase db,
                                               String actionId, String vectorId, String timeStart) {
        switch (actionSelectVector) {
            case ACTION_SELECT_VECTOR:
                return ActionDAO.getCursor(ActionDAO.ACTION_BY_ID, db, actionId, null);
            case ACTION_VALIDATE:
                return db.rawQuery(RecapQuery.SELECT_ACTION_RECAP_VALIDATE, new
                        String[]{timeStart, actionId, vectorId});
        }
        return null;
    }

    public interface RecapQuery {


        String SELECT_ACTION = "select "
                + TieUsContract.ActionTable._ID + " as "
                + TieUsContract.ActionTable.VIEW_ACTION_ID + ", "
                + TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + " from "
                + TieUsContract.ActionTable.NAME + " where "
                + TieUsContract.ActionTable._ID + "=?";


        String SELECT_VECTOR = "select "
                + TieUsContract.VectorTable._ID + " as "
                + TieUsContract.VectorTable.VIEW_VECTOR_ID + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + " from "
                + TieUsContract.VectorTable.NAME + " where "
                + TieUsContract.VectorTable._ID + "=?";


        String SELECT_ACTION_RECAP_VALIDATE = "select "
                + TieUsContract.ActionTable.VIEW_ACTION_ID + ", "
                + TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + ", "
                + TieUsContract.VectorTable.VIEW_VECTOR_ID + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + ", ? as "
                + TieUsContract.EventTable.COLUMN_TIME_START + ", "
                + ViewTypes.VIEW_ACTION_RECAP_QUERY + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + SELECT_ACTION + ") inner join (" + SELECT_VECTOR + ")";

        String[] SELECT_ACTION_RECAP_VALIDATE_PROJECTION = new String[]{
                TieUsContract.ActionTable.VIEW_ACTION_ID,
                TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID,
                TieUsContract.VectorTable.VIEW_VECTOR_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.EventTable.COLUMN_TIME_START,
                ViewTypes.COLUMN_VIEWTYPE};

    }


}
