package com.elorri.android.communication.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.communication.BoardAdapter;
import com.elorri.android.communication.R;
import com.elorri.android.communication.Tools;
import com.elorri.android.communication.data.CommunicationContract;

import java.util.ArrayList;

/**
 * Created by Elorri on 12/04/2016.
 */
public class ContactActionEventDAO {

    public static final int UNMANAGED_PEOPLE = 0;
    public static final int DELAY_PEOPLE = 1;
    public static final int TODAY_PEOPLE = 2;
    public static final int TODAY_DONE_PEOPLE = 3;
    public static final int NEXT_PEOPLE = 4;

    private static final String JOINT_TABLE_CONTACT_ACTION_EVENT = "SELECT * from "
            + CommunicationContract.ActionEntry.NAME + " inner join (select * from"
            + CommunicationContract.EventEntry.NAME + " inner join "
            + CommunicationContract.ContactEntry.NAME + " on "
            + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + "="
            + CommunicationContract.ContactEntry._ID + ") as ec on "
            + CommunicationContract.ActionEntry._ID + "= ec."
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID;

    private static String SELECT_UNMANAGED_PEOPLE="select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from "
            +CommunicationContract.ContactEntry.NAME+" minus select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from ("
            +JOINT_TABLE_CONTACT_ACTION_EVENT+")";

    private static String SELECT_DELAY_PEOPLE="select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from ("
            +JOINT_TABLE_CONTACT_ACTION_EVENT+") where "
            +CommunicationContract.EventEntry.COLUMN_TIME_START+"< ? and "
            +CommunicationContract.EventEntry.COLUMN_TIME_END+" is null";


    private static String SELECT_TODAY_PEOPLE="select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from ("
            +JOINT_TABLE_CONTACT_ACTION_EVENT+") where "
            +CommunicationContract.EventEntry.COLUMN_TIME_START+" between ? and ? and "
            +CommunicationContract.EventEntry.COLUMN_TIME_END+" is null";

    private static String SELECT_TODAY_DONE_PEOPLE="select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from ("
            +JOINT_TABLE_CONTACT_ACTION_EVENT+") where "
            +CommunicationContract.EventEntry.COLUMN_TIME_START+" between ? and ? and "
            +CommunicationContract.EventEntry.COLUMN_TIME_END+" is not null";


    private static String SELECT_NEXT_PEOPLE="select "
            +CommunicationContract.ContactEntry._ID+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1+", "
            +CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2+" from ("
            +JOINT_TABLE_CONTACT_ACTION_EVENT+") where "
            +CommunicationContract.EventEntry.COLUMN_TIME_START+" > ? and "
            +CommunicationContract.EventEntry.COLUMN_TIME_END+" is null";


    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(BoardAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(cursorType, db, viewTypes));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db, ArrayList<Integer> viewTypes) {

        switch (cursorType) {
            case UNMANAGED_PEOPLE:
                return setViewType(getCursor(cursorType, db), viewTypes, BoardAdapter.VIEW_UNMANAGED_PEOPLE);
            case DELAY_PEOPLE:
                return setViewType(getCursor(cursorType, db), viewTypes, BoardAdapter
                        .VIEW_UNDONE_PEOPLE);
            case TODAY_PEOPLE:
                return setViewType(getCursor(cursorType, db), viewTypes, BoardAdapter.VIEW_UNDONE_PEOPLE);
            case TODAY_DONE_PEOPLE:
                return setViewType(getCursor(cursorType, db), viewTypes, BoardAdapter.VIEW_DONE_PEOPLE);
            case NEXT_PEOPLE:
                return setViewType(getCursor(cursorType, db), viewTypes, BoardAdapter.VIEW_UNDONE_PEOPLE);
            default:
                return null;
        }

    }

    private static Cursor setViewType(Cursor cursor, ArrayList<Integer> viewTypes, int viewType) {
        while (cursor.moveToNext()) {
                viewTypes.add(viewType);
        }
        return cursor;
    }


    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case UNMANAGED_PEOPLE:
                return context.getResources().getString(R.string.unmanaged_people);
            case DELAY_PEOPLE:
                return context.getResources().getString(R.string.delay);
            case TODAY_PEOPLE:
                return context.getResources().getString(R.string.today);
            case TODAY_DONE_PEOPLE:
                return context.getResources().getString(R.string.done);
            case NEXT_PEOPLE:
                return context.getResources().getString(R.string.next);
            default:
                return null;
        }
    }

    private static Cursor getCursor(int cursorType, SQLiteDatabase db) {
        switch (cursorType) {
            case UNMANAGED_PEOPLE:{
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SELECT_UNMANAGED_PEOPLE);
                return db.rawQuery(SELECT_UNMANAGED_PEOPLE, null);}
            case DELAY_PEOPLE:{
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SELECT_DELAY_PEOPLE);
                long now = System.currentTimeMillis();
                return db.rawQuery(SELECT_DELAY_PEOPLE, new String[]{String.valueOf(now)});}
            case TODAY_PEOPLE:{
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SELECT_TODAY_PEOPLE);
                long todayStart = System.currentTimeMillis();
                long todayEnd = System.currentTimeMillis();
                return db.rawQuery(SELECT_TODAY_PEOPLE, new String[]{String.valueOf(todayStart),
                        String.valueOf(todayEnd)});}
            case TODAY_DONE_PEOPLE:{
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SELECT_TODAY_DONE_PEOPLE);
                long todayStart = System.currentTimeMillis();
                long todayEnd = System.currentTimeMillis();
                return db.rawQuery(SELECT_TODAY_DONE_PEOPLE, new String[]{String.valueOf
                        (todayStart), String.valueOf(todayEnd)});}
            case NEXT_PEOPLE:{
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SELECT_NEXT_PEOPLE);
                long tomorrow = System.currentTimeMillis();
                return db.rawQuery(SELECT_NEXT_PEOPLE, new String[]{String.valueOf(tomorrow)});}
            default:
                return null;
        }
    }
}
