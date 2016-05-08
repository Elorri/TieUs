package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.Projections;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;

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
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursor(contactId, ContactDAO.CONTACT_BY_ID, db));

        //if actions are registered and user does not know how to mark action as done. We add a row
        // on top to educate him.
        Cursor actionsCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.ACTION_BY_CONTACT_ID,
                db, contactId);
        if ((actionsCursor.getCount() > 0) && (!Status.getMarkActionFeatureStatus(context))) {
            cursors.add(Tools.getOneLineCursor(context.getResources().getString(R.string.mark_action_as_done),
                    Projections.VIEW_EDUCATE_MESSAGE));
        }
        cursors.add(getNextActionsWrappedCursor(context, context.getResources().getString(R.string.next_actions), db, contactId));
        cursors.add(getDoneActionsWrappedCursor(context, context.getResources().getString(R.string.done_actions), db, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsWrappedCursor(Context context, String title,
                                                      SQLiteDatabase db, String contactId) {
        //TODO close or not close this cursor
        Cursor doneActionCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.DONE_ACTION_BY_CONTACT_ID,
                db, contactId);
        //if no actions done are registered, we display no title at all. No next action title and
        // no done action title. We only dispay the list of next actions.
        if (doneActionCursor.getCount() == 0) {
            return getNextActionsCursor(context, db, contactId);
        }

        //if some actions done are registered, we display the next action title.
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(title, Projections.VIEW_TITLE));
        cursors.add(getNextActionsCursor(context, db, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getDoneActionsWrappedCursor(Context context, String title, SQLiteDatabase db, String contactId) {
        Cursor doneActionCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.DONE_ACTION_BY_CONTACT_ID,
                db, contactId);

        //if no actions done are registered, we display no title at all.
        if (doneActionCursor.getCount() == 0) {
            return doneActionCursor;
        }

        //if some actions done are registered, we display the done actions title.
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(title, Projections.VIEW_TITLE));
        cursors.add(doneActionCursor);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsCursor(Context context, SQLiteDatabase db, String contactId) {

        Cursor nextActionsCursor = ContactActionVectorEventDAO.getCursor(
                ContactActionVectorEventDAO.NEXT_ACTION_BY_CONTACT_ID,
                db, contactId);

        //if no next actions are registered we display a message
        if (nextActionsCursor.getCount() == 0) {
            return Tools.getOneLineCursor(context.getResources().getString(R.string.select_action),
                    Projections.VIEW_EMPTY_CURSOR_MESSAGE);
        }

        //otherwise we display the actions
        return nextActionsCursor;
    }


}
