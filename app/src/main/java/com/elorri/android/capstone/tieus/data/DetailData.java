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
import android.util.Log;

import com.elorri.android.capstone.tieus.R;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.capstone.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 * Class that will generate the combination of data needed to be displayed in detail screen and
 * give it in a form of a merged cursor.
 */
public class DetailData {

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor = null;

        //Query necessary to display the satisfactionIcon
        cursors.add(db.query(
                TieUsContract.ContactTable.NAME,
                ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE_QUERY,
                ContactDAO.ContactQuery.SELECTION,
                new String[]{contactId}, null, null, null));

        //If user is eligible for filling response time limit or if response time limit alone
        // already filled
        cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery
                        .SELECT_WITH_VIEWTYPE + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery
                        .PROJECTION_WITH_VIEWTYPE,
                TieUsContract.EventTable.COLUMN_CONTACT_ID + "=?",
                new String[]{contactId},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursors.add(cursor);
        }

        //If user eligible for filling frequency of contact or if frequency already known.
        cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery
                        .SELECT_WITH_VIEWTYPE + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery
                        .PROJECTION_WITH_VIEWTYPE,
                TieUsContract.EventTable.COLUMN_CONTACT_ID + "=?",
                new String[]{contactId},
                null, null, null);
        if (cursor.getCount() > 0) {
            cursors.add(cursor);
        }


        //if actions are registered and user does not know how to mark action as done. We add a row
        // on top to educate him.
        cursor = db.query("(" + ContactActionVectorEventDAO
                        .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_ALL,
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.SELECTION_ALL,
                new String[]{contactId}, null, null, null);
        if ((cursor.getCount() > 0) && (!Status.isDoneActionsAware(context))) {
            cursors.add(MatrixCursors.getOneLineCursor(
                    MatrixCursors.ConfirmMessageQuery.PROJECTION,
                    MatrixCursors.ConfirmMessageQuery.VALUES,
                    context.getResources().getString(R.string.how_to_done_action)));
        }
        if ((cursor.getCount() > 0) && (!Status.isDeleteActionsAware(context))) {
            cursors.add(MatrixCursors.getOneLineCursor(
                    MatrixCursors.ConfirmMessageQuery.PROJECTION,
                    MatrixCursors.ConfirmMessageQuery.VALUES,
                    context.getResources().getString(R.string.how_to_delete_action)));
        }
        cursors.add(getNextActionsWrappedCursor(context, context.getResources().getString(R.string.next_actions), db, contactId));
        cursors.add(getDoneActionsWrappedCursor(context, context.getResources().getString(R.string.done_actions), db, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsWrappedCursor(Context context, String title,
                                                      SQLiteDatabase db, String contactId) {
        //TODO close or not close this cursor
        Cursor doneActionCursor = db.query("(" + ContactActionVectorEventDAO
                        .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE_QUERY,
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.SELECTION_DONE,
                new String[]{contactId}, null, null, ContactActionVectorEventDAO.VectorActionByContactIdQuery
                        .SORT_ORDER_DONE);
        //if no actions done are registered, we display no title at all. No next action title and
        // no done action title. We only dispay the list of next actions.
        if (doneActionCursor.getCount() == 0) {
            return getNextActionsCursor(context, db, contactId);
        }

        //if some actions done are registered, we display the next action title.
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(MatrixCursors.getOneLineCursor(
                MatrixCursors.TitleQuery.PROJECTION,
                MatrixCursors.TitleQuery.VALUES,
                title
        ));
        cursors.add(getNextActionsCursor(context, db, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getDoneActionsWrappedCursor(Context context, String title, SQLiteDatabase db, String contactId) {
        Cursor doneActionCursor = db.query("(" + ContactActionVectorEventDAO
                        .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE_QUERY,
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.SELECTION_DONE,
                new String[]{contactId}, null, null, ContactActionVectorEventDAO.VectorActionByContactIdQuery
                        .SORT_ORDER_DONE);

        //if no actions done are registered, we display no title at all.
        if (doneActionCursor.getCount() == 0) {
            return doneActionCursor;
        }

        //if some actions done are registered, we display the done actions title.
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(MatrixCursors.getOneLineCursor(
                MatrixCursors.TitleQuery.PROJECTION,
                MatrixCursors.TitleQuery.VALUES,
                title
        ));
        cursors.add(doneActionCursor);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getNextActionsCursor(Context context, SQLiteDatabase db, String contactId) {

        Cursor nextActionsCursor = db.query("(" + ContactActionVectorEventDAO
                        .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT_QUERY,
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.SELECTION_UNDONE,
                new String[]{contactId}, null, null, ContactActionVectorEventDAO.VectorActionByContactIdQuery
                        .SORT_ORDER_UNDONE);

        //if no next actions are registered we display a message
        if (nextActionsCursor.getCount() == 0) {
            return MatrixCursors.getOneLineCursor(
                    MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                    MatrixCursors.EmptyCursorMessageQuery.VALUES,
                    context.getResources().getString(R.string.select_action));
        }

        //otherwise we display the actions
        return nextActionsCursor;
    }


}
