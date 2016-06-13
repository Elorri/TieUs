package com.elorri.android.tieus.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.MatrixCursors;
import com.elorri.android.tieus.extra.DateUtils;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 13/04/2016.
 */
public abstract class BoardData {

    public static final int LOADER_ID = 0;
    private static boolean onlyUntrackedPeople = false;

    public static Cursor getCursor(Context context, SQLiteDatabase db, long now, String
            selection, String[] selectionArgs) {

        //If our query already uses bind arguments we will concatenate selectionArgs to them.
        String[] args;

        //If there is no contact on phone tell it to the user
        Cursor cursor = context.getContentResolver().query(TieUsContract.ContactTable
                .CONTENT_URI, null, null, null, null);
        try {
            int contactsCount = cursor.getCount();
            if (contactsCount == 0) {
                return MatrixCursors.getOneLineCursor(
                        MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                        MatrixCursors.EmptyCursorMessageQuery.VALUES,
                        context.getResources().getString(R.string.no_contacts_on_phone));
            } else {
                cursor = context.getContentResolver().query(TieUsContract.ContactTable
                        .CONTENT_URI, null, TieUsContract.ContactTable
                        .COLUMN_UNFOLLOWED + "=?", new String[]{TieUsContract.ContactTable
                        .UNFOLLOWED_ON_VALUE}, null);
                if (cursor.getCount() == contactsCount)
                    onlyUntrackedPeople = true;
                else
                    onlyUntrackedPeople=false;
            }
        } finally {
            cursor.close();
        }

        ArrayList<Cursor> cursors = new ArrayList();
        String todayStart = String.valueOf(DateUtils.setZeroDay(now));
        String tomorrowStart = String.valueOf(DateUtils.addDay(1, DateUtils.setZeroDay(now)));


        cursors.add(db.query("(" + ContactDAO.RatioQuery.SELECT_WITH_VIEWTYPE + ")", null, null,
                null, null, null, null));
        if (onlyUntrackedPeople)
            cursor = MatrixCursors.getOneLineCursor(
                    MatrixCursors.MessageQuery.PROJECTION,
                    MatrixCursors.MessageQuery.VALUES,
                    context.getResources().getString(R.string.no_people_to_follow));
        else
            cursor = getTopCursors(context, db, Status.getLastMessageIdx(context), now, selection,
                    selectionArgs);
        if (cursor != null)
            cursors.add(cursor);

        cursor = db.query("(" + ContactActionVectorEventDAO.UnscheduledPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                null, selection, selectionArgs, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true,
                context.getResources().getString(R.string.unscheduled_people, cursor.getCount()), false, null, false));

        args = selectionArgs == null ? new String[]{todayStart} : new String[]{todayStart, selectionArgs[0]};
        cursor = db.query("(" + ContactActionVectorEventDAO.DelayPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                null, selection, args, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.delay), false, null, false));

        args = selectionArgs == null ? new String[]{todayStart, tomorrowStart} : new String[]{todayStart,
                tomorrowStart, selectionArgs[0]};
        cursor = db.query("(" + ContactActionVectorEventDAO.TodayPeopleQuery.SELECT_WITH_VIEWTYPE + ")", null, selection, args, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.today), false, null, false));

        args = selectionArgs == null ? new String[]{todayStart, tomorrowStart} : new String[]{todayStart,
                tomorrowStart, selectionArgs[0]};
        cursor = db.query("(" + ContactActionVectorEventDAO.TodayDonePeopleQuery
                .SELECT_WITH_VIEWTYPE + ")", null, selection, args, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.done), false, null, false));

        args = selectionArgs == null ? new String[]{tomorrowStart} : new String[]{tomorrowStart, selectionArgs[0]};
        cursor = db.query("(" + ContactActionVectorEventDAO.NextPeopleQuery.SELECT_WITH_VIEWTYPE
                + ")", null, selection, args, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.next), false, null, false));

        cursor = db.query("(" + ContactActionVectorEventDAO.UnfollowedPeopleQuery.SELECT_WITH_VIEWTYPE + ")", null,
                selection, selectionArgs, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.unfollowed), false, null, false));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getTopCursors(Context context, SQLiteDatabase db, int messageIdx, long
            now, String selection, String[] selectionArgs) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor;
        String message = "";
        switch (messageIdx) {
            case Status.MANAGE_UNMANAGED_PEOPLE:
                cursor = db.query("(" + ContactActionVectorEventDAO.UnscheduledPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                        null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.manage_unmanaged_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.manage_unmanaged_people_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                } else
                    return getTopCursors(context, db, Status.FILL_IN_DELAY_FEEDBACK, now, selection, selectionArgs);
                break;
            case Status.FILL_IN_DELAY_FEEDBACK:
                cursor = db.query("(" + ContactActionVectorEventDAO
                        .PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT_WITH_VIEWTYPE + ")", null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.fill_in_delay_feedback_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.fill_in_delay_feedback_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string
                                    .fill_in_delay_feedback_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.UPDATE_MOOD, now, selection, selectionArgs);
                break;
            case Status.UPDATE_MOOD:
                cursor = db.query("(" +
                        ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now
                        + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery
                        .SELECT_AFTER_BIND_WITH_VIEWTYPE + ")", null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.update_mood_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.update_mood_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.mood_to_update_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.SET_UP_A_FREQUENCY_OF_CONTACT, now, selection, selectionArgs);
                break;
            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
                cursor = db.query("(" + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT_WITH_VIEWTYPE + ")",
                        null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.fill_up_frequency_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.fill_up_frequency_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.fill_up_frequency_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
                            now, selection, selectionArgs);
                break;
            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
                cursor = db.query("(" +
                                ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE + ")",
                        null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.ask_for_feedback_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.ask_for_feedback_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.ask_for_feedback_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.APPROCHING_DEAD_LINE, now, selection, selectionArgs);
                break;
            case Status.APPROCHING_DEAD_LINE:
                cursor = db.query("(" +
                        ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                                .SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                        .SELECT_AFTER_BIND_WITH_VIEWTYPE + ")", null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.nearby_decreased_mood_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.nearby_decreased_mood_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.nearby_decreased_mood_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status
                            .NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY, now, selection, selectionArgs);
                break;
            case Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY:

                db.update(TieUsContract.ContactTable.NAME,
                        Tools.getContentValues(
                                TieUsContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                                String.valueOf(now)),
                        ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_BEFORE_BIND
                                + now
                                + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery
                                .UPDATE_AFTER_BIND, null);

                String[] args = selectionArgs == null ?
                        new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(context))} :
                        new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(context)), selectionArgs[0]};
                cursor = db.query("(" +
                        ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery
                                .SELECT_WITH_VIEWTYPE + ")", null, selection, args, null, null, null);

                if (cursor.getCount() > 0) {
                    //They decreased their mood, lets change their moodIcon
                    while (cursor.moveToNext()) {
                        int moodIcon = cursor.getInt(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.COL_MOOD_ID);
                        db.update(TieUsContract.ContactTable.NAME,
                                Tools.getContentValues(TieUsContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                                        String.valueOf(Tools.decreaseMood(moodIcon))),
                                TieUsContract.ContactTable._ID + "=?",
                                new String[]{cursor.getString(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.COL_ID)}
                        );
                    }
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.decreased_mood_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.decreased_mood_message,
                                cursor.getCount());
                    cursor.moveToPosition(-1);
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.decreased_mood_title), false, null,
                            false));

                } else
                    return getTopCursors(context, db, Status.NOTHING_TO_SAY, now, selection, selectionArgs);
                break;
            case Status.NOTHING_TO_SAY:
//                cursors.add(MatrixCursors.getOneLineCursor(
//                        MatrixCursors.ConfirmMessageQuery.PROJECTION,
//                        MatrixCursors.ConfirmMessageQuery.VALUES,
//                        context.getResources().getString(R.string.take_time_for_feedback_message)));
                Status.setLastMessageIdxBg(context, Status.MANAGE_UNMANAGED_PEOPLE);
                //when clicked on ok.
                //Status.setLastMessageIdxBg(context, Status.MANAGE_UNMANAGED_PEOPLE);
                return null;
        }
        Status.setLastMessageIdxBg(context, messageIdx);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));

    }


}