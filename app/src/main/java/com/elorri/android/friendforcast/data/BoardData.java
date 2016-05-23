package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 13/04/2016.
 */
public abstract class BoardData {

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, long now) {

        //If there is no contact on phone tell it to the user
        Cursor cursor = context.getContentResolver().query(FriendForecastContract.ContactTable
                .CONTENT_URI, null, null, null, null);
        try {
            if (cursor.getCount() == 0) {
                return MatrixCursors.getOneLineCursor(
                        MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                        MatrixCursors.EmptyCursorMessageQuery.VALUES,
                        context.getResources().getString(R.string.no_contacts_on_phone));
            }
        } finally {
            cursor.close();
        }

        ArrayList<Cursor> cursors = new ArrayList();
        String todayStart = String.valueOf(DateUtils.setZeroDay(now));
        String tomorrowStart = String.valueOf(DateUtils.addDay(1, DateUtils.setZeroDay(now)));

        cursors.add(db.rawQuery(ContactDAO.RatioQuery.SELECT_WITH_VIEWTYPE, null));
        cursors.add(getTopCursors(context, db, Status.getLastMessageIdx(context), now));

        cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE, null);
        cursors.add(Tools.addDisplayProperties(cursor, true,
                context.getResources().getString(R.string.unmanaged_people, cursor.getCount()), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.DelayPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{todayStart});
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.delay), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.TodayPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{todayStart, tomorrowStart});
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.today), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT_WITH_VIEWTYPE,
                new String[]{todayStart, tomorrowStart});
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.done), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.NextPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{tomorrowStart});
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.next), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.UntrackedPeopleQuery.SELECT_WITH_VIEWTYPE, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.untracked), false, null, false));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getTopCursors(Context context, SQLiteDatabase db, int messageIdx, long now) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor;
        switch (messageIdx) {
            case Status.MANAGE_UNMANAGED_PEOPLE:
                cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE,
                        null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.manage_unmanaged_people_message,
                                    cursor.getCount())));
                } else return getTopCursors(context, db, Status.FILL_IN_DELAY_FEEDBACK, now);
                break;
            case Status.FILL_IN_DELAY_FEEDBACK:
                cursor = db.rawQuery(ContactActionVectorEventDAO
                        .PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT_WITH_VIEWTYPE, null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.fill_in_delay_feedback_message, cursor.getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string
                                    .fill_in_delay_feedback_title), false, null,
                            false));
                } else return getTopCursors(context, db, Status.UPDATE_MOOD, now);
                break;
            case Status.UPDATE_MOOD:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery
                                .SELECT_AFTER_BIND_WITH_VIEWTYPE, null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.update_mood_message, cursor
                                    .getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.mood_to_update), false, null,
                            false));
                } else return getTopCursors(context, db, Status.SET_UP_A_FREQUENCY_OF_CONTACT, now);
                break;
            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
                cursor = db.rawQuery(ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT_WITH_VIEWTYPE,
                        null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            context.getResources().getString(R.string.fill_up_frequency_message, cursor
                                    .getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.fill_up_frequency_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
                            now);
                break;
            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE,
                        null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            context.getResources().getString(R.string.ask_for_feedback_message, cursor
                                    .getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.ask_for_feedback_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.APPROCHING_DEAD_LINE, now);
                break;
            case Status.APPROCHING_DEAD_LINE:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                                .SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                                .SELECT_AFTER_BIND_WITH_VIEWTYPE, null);
                if (cursor.getCount() > 0) {
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            context.getResources().getString(R.string.nearby_decreased_mood_message, cursor
                                    .getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.nearby_decreased_mood_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status
                            .NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY, now);
                break;
            case Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY:

                db.update(FriendForecastContract.ContactTable.NAME,
                        Tools.getContentValues(
                                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                                String.valueOf(now)),
                        ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_BEFORE_BIND
                                + now
                                + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery
                                .UPDATE_AFTER_BIND, null);

                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT_WITH_VIEWTYPE,
                        new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(context))});
                if (cursor.getCount() > 0) {
                    //They decreased their mood, lets change their moodIcon
                    while (cursor.moveToNext()) {
                        int moodIcon = cursor.getInt(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.COL_MOOD_ID);
                        db.update(FriendForecastContract.ContactTable.NAME,
                                Tools.getContentValues(FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                                        String.valueOf(Tools.decreaseMood(moodIcon))),
                                FriendForecastContract.ContactTable._ID + "=?",
                                new String[]{cursor.getString(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.COL_ID)}
                        );
                    }

                    cursor.moveToPosition(-1);
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            context.getResources().getString(R.string.decreased_mood_message, cursor
                                    .getCount())));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.decreased_mood_title), false, null,
                            false));

                } else
                    return getTopCursors(context, db, Status.TAKE_TIME_FOR_FEEDBACK, now);
                break;
            case Status.TAKE_TIME_FOR_FEEDBACK:
                cursors.add(MatrixCursors.getOneLineCursor(
                        MatrixCursors.ConfirmMessageQuery.PROJECTION,
                        MatrixCursors.ConfirmMessageQuery.VALUES,
                        context.getResources().getString(R.string.take_time_for_feedback_message)));
                //when clicked on ok.
                //Status.setLastMessageIdxBg(context, Status.MANAGE_UNMANAGED_PEOPLE);
                break;
        }
        Status.setLastMessageIdxBg(context, messageIdx);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));

    }

    //TODO remove this function
//    public static int getTopCursors(Context context, int messageIdx) {
//
//        boolean manageCond = false;
//        boolean feedback = false;
//        boolean mood = false;
//        boolean frequency = false;
//        boolean askFor = true;
//
//        switch (messageIdx) {
//            case Status.MANAGE_UNMANAGED_PEOPLE:
//                if (manageCond) {
//                } else return getTopCursors(context, Status.FILL_IN_DELAY_FEEDBACK);
//                break;
//            case Status.FILL_IN_DELAY_FEEDBACK:
//                if (feedback) {
//                } else return getTopCursors(context, Status.UPDATE_MOOD);
//                break;
//            case Status.UPDATE_MOOD:
//                if (mood) {
//                } else return getTopCursors(context, Status.SET_UP_A_FREQUENCY_OF_CONTACT);
//                break;
//            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
//                if (frequency) {
//                } else return getTopCursors(context, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);
//                break;
//            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
//                if (askFor) {
//                } else
//                    return getTopCursors(context, Status.APPROCHING_DEAD_LINE);
//                break;
//            case Status.APPROCHING_DEAD_LINE:
//                //when clicked on ok.
//                //Status.setLastMessageIdxBg(context, Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY);
//                break;
//            case Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY:
//                //Status.setLastMessageIdxBg(context, Status.TAKE_TIME_FOR_FEEDBACK);
//                break;
//            case Status.TAKE_TIME_FOR_FEEDBACK:
//                //Status.setLastMessageIdxBg(context, Status.MANAGE_UNMANAGED_PEOPLE);
//                break;
//        }
//        Status.setLastMessageIdxBg(context, messageIdx);
//        return Status.getLastMessageIdx(context);
//
//    }

}