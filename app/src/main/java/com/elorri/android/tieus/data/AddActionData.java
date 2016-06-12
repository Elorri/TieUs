package com.elorri.android.tieus.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.tieus.db.ActionDAO;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.db.VectorDAO;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 27/04/2016.
 */
public class AddActionData {

    public static final int LOADER_ID = 0;

    public static final int ACTION_SELECT_ACTION = 0;
    public static final int ACTION_SELECT_VECTOR = 1;
    public static final int ACTION_VALIDATE = 3;


    public static Cursor getCursor(Context context, SQLiteDatabase db,
                                   int cursorType, String actionId,
                                   String vectorId, String timeStart) {
        ArrayList<Cursor> cursors = new ArrayList();
        switch (cursorType) {
            case ACTION_SELECT_ACTION:
                cursors.add(ActionDAO.getCursorActionsWithTitle(context, db));
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                break;

            case ACTION_SELECT_VECTOR:
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                cursors.add(getActionRecapCursor(ACTION_SELECT_VECTOR, db, actionId, null, null));
                cursors.add(VectorDAO.getWrappedCursor(context, VectorDAO.ALL_VECTORS, db));
                break;

            case ACTION_VALIDATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actionId " + actionId + "vectorId " + vectorId
                        + "time " + timeStart);
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
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
                return ActionDAO.getCursor(ActionDAO.ACTION_BY_ID, db, actionId, null);
            case ACTION_VALIDATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + RecapQuery
                        .SELECT_ACTION_RECAP_VALIDATE);
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
