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
 * Class that will generate the combination of data needed to be displayed in main screen and
 * give it in a form of a merged cursor.
 */
public abstract class MainData {

    public static final int LOADER_ID = 0;

    /**
     * Create the cursor that will be displayed on Main Screen. It is a MergeCursor that is
     * compose of 2 main parts:
     * - a top (displayed if necessary) cursor : that will gives appropriates advices to users
     * depending on the situation of their contact list
     * - a second (always displayed) cursor : that will display the contact list with priorities:
     * - first : people unfollowed (if there is some)
     * - second : people delayed (if there is some)
     * - third : today people (if there is some)
     * - fourth : next people (if there is some)
     * Note : if no contacts were found on the user phone, a one row cursor is returned with a
     * message advicing the user to add contacts on his phone.
     * @param context
     * @param db
     * @param now
     * @param selection
     * @param selectionArgs
     * @return a cursor
     */
    static Cursor getCursor(Context context, SQLiteDatabase db, long now, String
            selection, String[] selectionArgs) {
        boolean onlyUnfollowedPeople = false;

        //If our query uses selectionArgs we will add them to those we want
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
                    onlyUnfollowedPeople = true;
                else
                    onlyUnfollowedPeople =false;
            }
        } finally {
            cursor.close();
        }

        ArrayList<Cursor> cursors = new ArrayList();
        String todayStart = String.valueOf(DateUtils.setZeroDay(now));
        String tomorrowStart = String.valueOf(DateUtils.addDay(1, DateUtils.setZeroDay(now)));


        cursors.add(db.query("(" + ContactDAO.RatioQuery.SELECT_WITH_VIEWTYPE + ")", null, null,
                null, null, null, null));
        if (onlyUnfollowedPeople)
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
                context.getResources().getString(R.string.unscheduled_people), false, null, false));

        args = selectionArgs == null ? new String[]{todayStart} : new String[]{todayStart, selectionArgs[0]};
        cursor = db.query("(" + ContactActionVectorEventDAO.DelayedPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                null, selection, args, null, null, null);
        cursors.add(Tools.addDisplayProperties(cursor, true, context.getResources().getString(R.string.delayed), false, null, false));

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


    /**
     * The top Cursor is composed of 2 parts :
     * - A message advicing the user of taking actions with some contacts
     * - A cursor displaying the contact concerned
     * This method is organised so that only one message at a time is displayed to the user. Once
     * the conditions for displaying the message are no longer met (means the user have taken
     * action), we move to the next possible message and see if the conditions are met, if yes we
     * display it, if no we check the next message type, and so on until we have nothing to say
     * and return an empty top cursor.
     * @param context
     * @param db
     * @param messageIdx
     * @param now
     * @param selection
     * @param selectionArgs
     * @return
     */
    private static Cursor getTopCursors(Context context, SQLiteDatabase db, int messageIdx, long
            now, String selection, String[] selectionArgs) {
        ArrayList<Cursor> cursors = new ArrayList();
        Cursor cursor;
        String message = "";
        switch (messageIdx) {
            case Status.MANAGE_UNSCHEDULED_PEOPLE:
                cursor = db.query("(" + ContactActionVectorEventDAO.UnscheduledPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                        null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.manage_unscheduled_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.manage_unscheduled_contact_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                } else
                    return getTopCursors(context, db, Status.FILL_IN_RESPONSE_TIME_LIMIT, now, selection, selectionArgs);
                break;
            case Status.FILL_IN_RESPONSE_TIME_LIMIT:
                cursor = db.query("(" + ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.SELECT_WITH_VIEWTYPE + ")", null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.fill_in_time_limit_response_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.fill_in_time_limit_response_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string
                                    .fill_in_response_time_limit_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status.UPDATE_SATISFACTION, now, selection, selectionArgs);
                break;
            case Status.UPDATE_SATISFACTION:
                cursor = db.query("(" +
                        ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                        + now
                        + ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery
                        .SELECT_AFTER_BIND_WITH_VIEWTYPE + ")", null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.update_satisfaction_face_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.update_satisfaction_face_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.MessageQuery.PROJECTION,
                            MatrixCursors.MessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.satisfaction_to_update_title), false, null,
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
                    return getTopCursors(context, db, Status.ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED,
                            now, selection, selectionArgs);
                break;
            case Status.ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED:
                cursor = db.query("(" +
                                ContactActionVectorEventDAO.AskForResponseQuery.SELECT_BEFORE_BIND_WITH_VIEWTYPE
                                + now
                                + ContactActionVectorEventDAO.AskForResponseQuery.SELECT_AFTER_BIND_WITH_VIEWTYPE + ")",
                        null, selection, selectionArgs, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.ask_for_response_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.ask_for_response_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.ask_for_response_title), false, null,
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
                        message = context.getResources().getString(R.string.nearby_decreased_satisfaction_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.nearby_decreased_satisfaction_message,
                                cursor.getCount());
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.nearby_decreased_satisfaction_title), false, null,
                            false));
                } else
                    return getTopCursors(context, db, Status
                            .NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY, now, selection, selectionArgs);
                break;
            case Status.NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY:

                db.update(TieUsContract.ContactTable.NAME,
                        Tools.getContentValues(
                                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                                String.valueOf(now)),
                        ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.UPDATE_BEFORE_BIND
                                + now
                                + ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery
                                .UPDATE_AFTER_BIND, null);

                String[] args = selectionArgs == null ?
                        new String[]{String.valueOf(Status.getLastUserSatisfactionsConfirmAware(context))} :
                        new String[]{String.valueOf(Status.getLastUserSatisfactionsConfirmAware(context)), selectionArgs[0]};
                cursor = db.query("(" +
                        ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery
                                .SELECT_WITH_VIEWTYPE + ")", null, selection, args, null, null, null);

                if (cursor.getCount() > 0) {
                    //They decreased their satisfaction, lets change their satisfactionIcon
                    while (cursor.moveToNext()) {
                        int satisfactionIcon = cursor.getInt(ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.COL_SATISFACTION_ID);
                        db.update(TieUsContract.ContactTable.NAME,
                                Tools.getContentValues(TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                                        String.valueOf(Tools.decreaseSatisfaction(satisfactionIcon))),
                                TieUsContract.ContactTable._ID + "=?",
                                new String[]{cursor.getString(ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.COL_ID)}
                        );
                    }
                    if (cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        message = context.getResources().getString(R.string.decreased_satisfaction_person,
                                Tools.toProperCase(
                                        cursor.getString(ContactActionVectorEventDAO.PeopleQuery.COL_CONTACT_NAME)));
                        cursor.moveToPosition(-1);
                    } else
                        message = context.getResources().getString(R.string.decreased_satisfaction_message,
                                cursor.getCount());
                    cursor.moveToPosition(-1);
                    cursors.add(MatrixCursors.getOneLineCursor(
                            MatrixCursors.ConfirmMessageQuery.PROJECTION,
                            MatrixCursors.ConfirmMessageQuery.VALUES,
                            message));
                    cursors.add(Tools.addDisplayProperties(cursor, true,
                            context.getResources().getString(R.string.decreased_satisfaction_title), false, null,
                            false));

                } else
                    return getTopCursors(context, db, Status.NOTHING_TO_SAY, now, selection, selectionArgs);
                break;
            case Status.NOTHING_TO_SAY:
                Status.setLastMessageIdxBg(context, Status.MANAGE_UNSCHEDULED_PEOPLE);
                return null;
        }
        Status.setLastMessageIdxBg(context, messageIdx);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));

    }


}