package com.elorri.android.tieus.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactDAO.RatioQuery.SELECT_WITH_VIEWTYPE);

        cursor = getTopCursors(context, db, Status.getLastMessageIdx(context), now);
        if (cursor != null)
            cursors.add(cursor);

        cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE);
        cursors.add(Tools.addDisplayProperties(cursor, true,
                context.getResources().getString(R.string.unmanaged_people, cursor.getCount()), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.DelayPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{todayStart});
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.DelayPeopleQuery.SELECT_WITH_VIEWTYPE);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.delay), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.TodayPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{todayStart, tomorrowStart});
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.TodayPeopleQuery.SELECT_WITH_VIEWTYPE);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.today), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT_WITH_VIEWTYPE,
                new String[]{todayStart, tomorrowStart});
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT_WITH_VIEWTYPE + " - "
                + todayStart + " - " + tomorrowStart);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.done), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.NextPeopleQuery.SELECT_WITH_VIEWTYPE, new String[]{tomorrowStart});
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.NextPeopleQuery.SELECT_WITH_VIEWTYPE);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.next), false, null, false));

        cursor = db.rawQuery(ContactActionVectorEventDAO.UntrackedPeopleQuery.SELECT_WITH_VIEWTYPE, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.UntrackedPeopleQuery.SELECT_WITH_VIEWTYPE);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.untracked), false, null, false));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getTopCursors(Context context, SQLiteDatabase db, int messageIdx, long now) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor;
        String message = "";
        switch (messageIdx) {
            case Status.MANAGE_UNMANAGED_PEOPLE:
                cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE,
                        null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_WITH_VIEWTYPE);
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
                } else return getTopCursors(context, db, Status.FILL_IN_DELAY_FEEDBACK, now);
                break;
            case Status.FILL_IN_DELAY_FEEDBACK:
                cursor = db.rawQuery(ContactActionVectorEventDAO
                        .PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT_WITH_VIEWTYPE, null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT_WITH_VIEWTYPE);
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
                } else return getTopCursors(context, db, Status.UPDATE_MOOD, now);
                break;
            case Status.UPDATE_MOOD:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery
                                .SELECT_AFTER_BIND_WITH_VIEWTYPE, null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now
                        + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE);
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
                            context.getResources().getString(R.string.mood_to_update), false, null,
                            false));
                } else return getTopCursors(context, db, Status.SET_UP_A_FREQUENCY_OF_CONTACT, now);
                break;
            case Status.SET_UP_A_FREQUENCY_OF_CONTACT:
                cursor = db.rawQuery(ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT_WITH_VIEWTYPE,
                        null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT_WITH_VIEWTYPE);
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
                            now);
                break;
            case Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE,
                        null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now
                        + ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE);
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
                    return getTopCursors(context, db, Status.APPROCHING_DEAD_LINE, now);
                break;
            case Status.APPROCHING_DEAD_LINE:
                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                                .SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                                .SELECT_AFTER_BIND_WITH_VIEWTYPE, null);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                        .SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                        .SELECT_AFTER_BIND_WITH_VIEWTYPE);
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

                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_BEFORE_BIND
                        + now
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_AFTER_BIND);

                cursor = db.rawQuery(
                        ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT_WITH_VIEWTYPE,
                        new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(context))});
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT_WITH_VIEWTYPE);
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
                    return getTopCursors(context, db, Status.NOTHING_TO_SAY, now);
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