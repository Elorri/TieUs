package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.db.ActionDAO;
import com.elorri.android.friendforcast.db.ActionVectorTemplatesDAO;
import com.elorri.android.friendforcast.db.VectorDAO;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.AddActionAdapter;

import java.util.ArrayList;

/**
 * Created by Elorri on 27/04/2016.
 */
public class AddActionData {

    public interface TitleQuery {
        int COL_TITLE = 0;
    }

    public static final int LOADER_ID = 0;

    public static final int ACTION_SELECT_ACTION = 0;
    public static final int ACTION_SELECT_VECTOR = 1;
    public static final int ACTION_SELECT_TEMPLATE = 2;
    public static final int ACTION_VALIDATE = 3;


    public static Cursor getCursor(Context context, SQLiteDatabase db,
                                   int cursorType, String actionId,
                                   String vectorId, String templateId, String timeStart) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        switch (cursorType) {
            case ACTION_SELECT_ACTION:
                cursors.add(CursorUtils.setViewType(
                        ActionDAO.getCursor(ActionDAO.ALL_ACTIONS, db, null),
                        viewTypes, AddActionAdapter.VIEW_ACTION_ITEM));
                Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
                break;

            case ACTION_SELECT_VECTOR:
                Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
                cursors.add(getActionRecapCursor(ACTION_SELECT_VECTOR, db, viewTypes, actionId, null, null, null));
                cursors.add(VectorDAO.getWrappedCursor(context, VectorDAO.ALL_VECTORS, db,
                        viewTypes));
                break;

            case ACTION_SELECT_TEMPLATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actionId " + actionId + "vectorId " + vectorId);
                cursors.add(getActionRecapCursor(ACTION_SELECT_TEMPLATE, db, viewTypes, actionId, vectorId, null, null));
                cursors.add(ActionVectorTemplatesDAO.getWrappedCursor(context,
                        ActionVectorTemplatesDAO.TEMPLATE_BY_ACTION_ID_VECTOR_ID, db, viewTypes,actionId, vectorId));
                break;
            case ACTION_VALIDATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actionId " + actionId + "vectorId " + vectorId
                        + "templateId " + templateId + "timeStart " + timeStart);
                cursors.add(getActionRecapCursor(ACTION_VALIDATE, db, viewTypes, actionId, vectorId, templateId, timeStart));
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        AddActionAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getActionRecapCursor(int actionSelectVector, SQLiteDatabase db,
                                               ArrayList<Integer> viewTypes,
                                               String actionId, String vectorId,
                                               String templateId, String timeStart) {
        switch (actionSelectVector) {
            case ACTION_SELECT_VECTOR:
                Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
                return CursorUtils.setViewType(
                        ActionDAO.getCursor(ActionDAO.ACTION_BY_ID, db, actionId),
                        viewTypes, AddActionAdapter.VIEW_ACTION_RECAP);
            case ACTION_SELECT_TEMPLATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
                return CursorUtils.setViewType(
                        db.rawQuery(RecapQuery.SELECT_ACTION_RECAP_TEMPLATE,
                                new String[]{actionId, vectorId}),
                        viewTypes, AddActionAdapter.VIEW_ACTION_RECAP);
            case ACTION_VALIDATE:
                Log.e("FF", Thread.currentThread().getStackTrace()[2]+""+RecapQuery
                        .SELECT_ACTION_RECAP_VALIDATE);
                return CursorUtils.setViewType(
                        db.rawQuery(RecapQuery.SELECT_ACTION_RECAP_VALIDATE, new
                                String[]{timeStart, templateId, actionId, vectorId }),
                        viewTypes, AddActionAdapter.VIEW_ACTION_RECAP);
        }
        return null;
    }

    public interface RecapQuery {


        String SELECT_ACTION = "select "
                + FriendForecastContract.ActionTable._ID + " as "
                + FriendForecastContract.ActionTable.VIEW_ACTION_ID + ", "
                + FriendForecastContract.ActionTable.COLUMN_NAME + " from "
                + FriendForecastContract.ActionTable.NAME + " where "
                + FriendForecastContract.ActionTable._ID + "=?";


        String SELECT_VECTOR = "select "
                + FriendForecastContract.VectorTable._ID + " as "
                + FriendForecastContract.VectorTable.VIEW_VECTOR_ID + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + " from "
                + FriendForecastContract.VectorTable.NAME + " where "
                + FriendForecastContract.VectorTable._ID + "=?";

        String SELECT_TEMPLATE = "select "
                + FriendForecastContract.ActionVectorTemplatesTable._ID + ", "
                + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE + " from "
                + FriendForecastContract.ActionVectorTemplatesTable.NAME + " where "
                + FriendForecastContract.ActionVectorTemplatesTable._ID + "=?";

        String SELECT_ACTION_RECAP_TEMPLATE = "select "
                + FriendForecastContract.ActionTable.VIEW_ACTION_ID + ", "
                + FriendForecastContract.ActionTable.COLUMN_NAME + ", "
                + FriendForecastContract.VectorTable.VIEW_VECTOR_ID + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + " from ("
                + SELECT_ACTION + ") inner join (" + SELECT_VECTOR + ")";

        String SELECT_ACTION_RECAP_VALIDATE = "select "
                + FriendForecastContract.ActionTable.VIEW_ACTION_ID + ", "
                + FriendForecastContract.ActionTable.COLUMN_NAME + ", "
                + FriendForecastContract.VectorTable.VIEW_VECTOR_ID + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.ActionVectorTemplatesTable._ID + ", "
                + FriendForecastContract.ActionVectorTemplatesTable.COLUMN_VALUE + ",? as "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " from ("
                + SELECT_TEMPLATE + ") inner join ("
                + SELECT_ACTION_RECAP_TEMPLATE + ")";


    }


}
