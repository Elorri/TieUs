package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 13/04/2016.
 */
public abstract class BoardData {


    public interface TitleQuery {
        int COL_TITLE = 0;
    }

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db) {

        //If there is no contact on phone tell it to the user
        Cursor cursor = context.getContentResolver().query(FriendForecastContract.ContactTable
                .CONTENT_URI, null, null, null, null);
        try {
            if (cursor.getCount() == 0)
                return MatrixCursors.getOneLineCursor(
                        MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                        MatrixCursors.EmptyCursorMessageQuery.VALUES,
                        context.getResources().getString(R.string.no_contacts_on_phone));
        } finally {
            cursor.close();
        }


        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursor(db));
        cursors.add(getTopCursors(context, db, Status.getLastMessageIdx(context)));
        cursors.add(Tools.addDisplayProperties(
                ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.DELAY_PEOPLE, db),
                true, context.getResources().getString(R.string.delay), false, null, false));
        cursors.add(Tools.addDisplayProperties(
                ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.TODAY_PEOPLE, db),
                true, context.getResources().getString(R.string.today), false, null, false));
        cursors.add(Tools.addDisplayProperties(
                ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.TODAY_DONE_PEOPLE, db),
                true, context.getResources().getString(R.string.done), false, null, false));
        cursors.add(Tools.addDisplayProperties(
                ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.NEXT_PEOPLE, db),
                true, context.getResources().getString(R.string.next), false, null, false));
        cursors.add(Tools.addDisplayProperties(
                ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.UNTRACKED_PEOPLE, db),
                true, context.getResources().getString(R.string.untracked), false, null,
                false));

        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getTopCursors(Context context, SQLiteDatabase db, int messageIdx) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor;
        switch (messageIdx) {
            case Status.MANAGE_UNMANAGED_PEOPLE:
                cursor = ContactActionVectorEventDAO.getCursor(ContactActionVectorEventDAO.UNMANAGED_PEOPLE, db);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.manage_unmanaged_people)));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.unmanaged_people, cursor.getCount()), false,
                            null,
                            false));
                } else return getTopCursors(context, db, Status.FILL_IN_DELAY_FEEDBACK);
                break;
            case Status.FILL_IN_DELAY_FEEDBACK:
                String lastFillInDelay = String.valueOf(Status.getLastFillInDelayFeedbackChange(context));
                String now = String.valueOf(System.currentTimeMillis());
                cursor = db.query("(" + ContactActionVectorEventDAO
                                .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                        ContactActionVectorEventDAO.PeopleLastActedForQuery.PROJECTION_QUERY,
                        ContactActionVectorEventDAO.PeopleLastActedForQuery.SELECTION,
                        new String[]{lastFillInDelay, now}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.fill_in_delay_feedback, cursor.getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string
                                    .fill_in_delay_feedback_title), false, null,
                            false));
                } else return getTopCursors(context, db, Status.UPDATE_MOOD);
                break;
            case Status.UPDATE_MOOD:
                if (mood) {
                } else return getTopCursors(context, db, Status.SET_UP_A_FREQUENCY_OF_CONTACT);
                break;
            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
                if (frequency) {
                } else
                    return getTopCursors(context, db, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);
                break;
            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
                if (askFor) {
                } else
                    return getTopCursors(context, db, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);
                break;
            case Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY:
                //when clicked on ok.
                //Status.setLastMessageIdx(context, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
                break;
            case Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY:
                //Status.setLastMessageIdx(context, Status.TAKE_TIME_FOR_FEEDBACK);
                break;
            case Status.TAKE_TIME_FOR_FEEDBACK:
                //Status.setLastMessageIdx(context, Status.MANAGE_UNMANAGED_PEOPLE);
                break;
        }
        Status.setLastMessageIdx(context, messageIdx);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));

    }

    public static int getTopCursors(Context context, int messageIdx) {

        boolean manageCond = false;
        boolean feedback = false;
        boolean mood = false;
        boolean frequency = false;
        boolean askFor = true;

        switch (messageIdx) {
            case Status.MANAGE_UNMANAGED_PEOPLE:
                if (manageCond) {
                } else return getTopCursors(context, Status.FILL_IN_DELAY_FEEDBACK);
                break;
            case Status.FILL_IN_DELAY_FEEDBACK:
                if (feedback) {
                } else return getTopCursors(context, Status.UPDATE_MOOD);
                break;
            case Status.UPDATE_MOOD:
                if (mood) {
                } else return getTopCursors(context, Status.SET_UP_A_FREQUENCY_OF_CONTACT);
                break;
            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
                if (frequency) {
                } else return getTopCursors(context, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);
                break;
            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
                if (askFor) {
                } else
                    return getTopCursors(context, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);
                break;
            case Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY:
                //when clicked on ok.
                //Status.setLastMessageIdx(context, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
                break;
            case Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY:
                //Status.setLastMessageIdx(context, Status.TAKE_TIME_FOR_FEEDBACK);
                break;
            case Status.TAKE_TIME_FOR_FEEDBACK:
                //Status.setLastMessageIdx(context, Status.MANAGE_UNMANAGED_PEOPLE);
                break;
        }
        Status.setLastMessageIdx(context, messageIdx);
        return Status.getLastMessageIdx(context);

    }

}