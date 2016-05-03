package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.DetailAdapter;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailData {

    public interface SingleColumnQuery {
        int COL_SINGLE_COLUMN = 0;
    }

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(CursorUtils.setViewType(ContactDAO.getCursor(contactId,
                ContactDAO.CONTACT_BY_ID, db), viewTypes, DetailAdapter.VIEW_EMOICON));

        //if actions are registered and user does not know how to mark action as done. We add a row
        // on top to educate him.
        Cursor actionsCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.ACTION_BY_CONTACT_ID,
                db, contactId);
        if ((actionsCursor.getCount()>0)&&(!Status.getMarkActionFeatureStatus(context))) {
            cursors.add(CursorUtils.setViewType(
                    Tools.getOneLineCursor(context.getResources().getString(R.string.mark_action_as_done)),
                    viewTypes, DetailAdapter.VIEW_EDUCATE_USER));
        }
        cursors.add(getNextActionsWrappedCursor(context, context.getResources().getString(R.string.next_actions),
                db, viewTypes, contactId));
        cursors.add(getDoneActionsWrappedCursor(context, context.getResources().getString(R.string.done_actions),
                db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsWrappedCursor(Context context, String title, SQLiteDatabase db,
                                                      ArrayList<Integer> viewTypes, String contactId) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        Cursor doneActionCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.DONE_ACTION_BY_CONTACT_ID,
                db, contactId);
        //if no actions done are registered, we display no title at all. No next action title and
        // no done action title. We only dispay the list of next actions.
        if (doneActionCursor.getCount() == 0) {
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
            return getNextActionsCursor(context, db, viewTypes, contactId);
        }

        //if some actions done are registered, we display the next action title.
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(CursorUtils.setViewType(Tools.getOneLineCursor(title), viewTypes, DetailAdapter.VIEW_TITLE));
        cursors.add(getNextActionsCursor(context, db, viewTypes, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getDoneActionsWrappedCursor(Context context, String title, SQLiteDatabase db,
                                                      ArrayList<Integer> viewTypes, String contactId) {
        Cursor doneActionCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.DONE_ACTION_BY_CONTACT_ID,
                db, contactId);

        //if no actions done are registered, we display no title at all.
        if (doneActionCursor.getCount() == 0) {
            return CursorUtils.setViewType(doneActionCursor, viewTypes, DetailAdapter.VIEW_DONE_ACTION);
        }

        //if some actions done are registered, we display the done actions title.
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(CursorUtils.setViewType(Tools.getOneLineCursor(title), viewTypes, DetailAdapter.VIEW_TITLE));
        cursors.add(CursorUtils.setViewType(doneActionCursor, viewTypes, DetailAdapter.VIEW_DONE_ACTION));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsCursor(Context context, SQLiteDatabase db, ArrayList<Integer>
            viewTypes, String contactId) {

        Cursor nextActionsCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.NEXT_ACTION_BY_CONTACT_ID,
                db, contactId);

        //if no next actions are registered we display a message
        if (nextActionsCursor.getCount() == 0) {
            return CursorUtils.setViewType(
                    Tools.getOneLineCursor(context.getResources().getString(R.string.select_action)),
                    viewTypes,
                    DetailAdapter.VIEW_EMPTY_CURSOR);
        }

        //otherwise we display the actions
        return CursorUtils.setViewType(nextActionsCursor, viewTypes, DetailAdapter.VIEW_NEXT_ACTION);
    }


}
