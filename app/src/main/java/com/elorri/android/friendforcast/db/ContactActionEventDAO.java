package com.elorri.android.friendforcast.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendCastContract;

/**
 * Created by Elorri on 12/04/2016.
 */
public class ContactActionEventDAO {

    public static final int UNMANAGED_PEOPLE = 0;
    public static final int DELAY_PEOPLE = 1;
    public static final int TODAY_PEOPLE = 2;
    public static final int TODAY_DONE_PEOPLE = 3;
    public static final int NEXT_PEOPLE = 4;


    private static final String JOINT_TABLE_CONTACT_ACTION_EVENT = "select "
            + FriendCastContract.EventEntry.VIEW_EVENT_ID + ", "
            + FriendCastContract.EventEntry.COLUMN_ACTION_ID + ", "
            + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + FriendCastContract.ActionEntry.NAME + "."
            + FriendCastContract.ActionEntry.COLUMN_NAME + " as "
            + FriendCastContract.ActionEntry.VIEW_ACTION_NAME + ", "
            + FriendCastContract.EventEntry.COLUMN_TIME_START + ", "
            + FriendCastContract.EventEntry.COLUMN_TIME_END + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + " from (select "
            + FriendCastContract.EventEntry.NAME + "."
            + FriendCastContract.EventEntry._ID + " as "
            + FriendCastContract.EventEntry.VIEW_EVENT_ID + ", "
            + FriendCastContract.EventEntry.NAME + "."
            + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + FriendCastContract.EventEntry.NAME + "."
            + FriendCastContract.EventEntry.COLUMN_ACTION_ID + ", "
            + FriendCastContract.EventEntry.NAME + "."
            + FriendCastContract.EventEntry.COLUMN_TIME_START + ", "
            + FriendCastContract.EventEntry.NAME + "."
            + FriendCastContract.EventEntry.COLUMN_TIME_END + ", "
            + FriendCastContract.ContactEntry.NAME + "."
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendCastContract.ContactEntry.NAME + "."
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendCastContract.ContactEntry.NAME + "."
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendCastContract.ContactEntry.NAME + "."
            + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + " from "
            + FriendCastContract.EventEntry.NAME + " inner join "
            + FriendCastContract.ContactEntry.NAME + " on "
            + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + "="
            + FriendCastContract.ContactEntry.NAME + "."
            + FriendCastContract.ContactEntry._ID + ") as ec inner join "
            + FriendCastContract.ActionEntry.NAME + " on ec."
            + FriendCastContract.EventEntry.COLUMN_ACTION_ID + "="
            + FriendCastContract.ActionEntry.NAME + "."
            + FriendCastContract.ActionEntry._ID;


    public interface TitleQuery {
        int COL_TITLE = 0;
    }


    public interface UnmanagedPeopleQuery {

        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;


        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendCastContract.ContactEntry.COLUMN_EMOICON_ID
        };

        String SELECT_UNMANAGED_PEOPLE = "select "
                + FriendCastContract.ContactEntry._ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                 + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + " from "
                + FriendCastContract.ContactEntry.NAME + " except select "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ")";
    }

    public interface DelayPeopleQuery {

        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;


        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendCastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendCastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendCastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_DELAY_PEOPLE = "select "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendCastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + "< ? and "
                + FriendCastContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayPeopleQuery {

        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;


        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendCastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendCastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendCastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_TODAY_PEOPLE = "select "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendCastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + FriendCastContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayDonePeopleQuery {


        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_END = 6;


        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendCastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendCastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendCastContract.EventEntry.COLUMN_TIME_END
        };

        String SELECT_TODAY_DONE_PEOPLE = "select "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendCastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendCastContract.EventEntry.COLUMN_TIME_END + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + FriendCastContract.EventEntry.COLUMN_TIME_END + " is not null";
    }

    public interface NextPeopleQuery {

        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;



        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendCastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendCastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendCastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_NEXT_PEOPLE = "select "
                + FriendCastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendCastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendCastContract.EventEntry.COLUMN_TIME_START + " > ? and "
                + FriendCastContract.EventEntry.COLUMN_TIME_END + " is null";
    }


    public static Cursor getCursor(int cursorType, SQLiteDatabase db) {
        switch (cursorType) {
            case UNMANAGED_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE);
                return db.rawQuery(UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE, null);
            }
            case DELAY_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + DelayPeopleQuery.SELECT_DELAY_PEOPLE);
                long now = System.currentTimeMillis();
                return db.rawQuery(DelayPeopleQuery.SELECT_DELAY_PEOPLE, new String[]{String
                        .valueOf(now)});
            }
            case TODAY_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + TodayPeopleQuery.SELECT_TODAY_PEOPLE);
                long todayStart = System.currentTimeMillis();
                long todayEnd = System.currentTimeMillis();
                return db.rawQuery(TodayPeopleQuery.SELECT_TODAY_PEOPLE, new String[]{String
                        .valueOf(todayStart),
                        String.valueOf(todayEnd)});
            }
            case TODAY_DONE_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + TodayDonePeopleQuery.SELECT_TODAY_DONE_PEOPLE);
                long todayStart = System.currentTimeMillis();
                long todayEnd = System.currentTimeMillis();
                return db.rawQuery(TodayDonePeopleQuery.SELECT_TODAY_DONE_PEOPLE, new
                        String[]{String.valueOf
                        (todayStart), String.valueOf(todayEnd)});
            }
            case NEXT_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + NextPeopleQuery.SELECT_NEXT_PEOPLE);
                long tomorrow = System.currentTimeMillis();
                return db.rawQuery(NextPeopleQuery.SELECT_NEXT_PEOPLE, new String[]{String
                        .valueOf(tomorrow)});
            }
            default:
                return null;
        }
    }

    public static String[] getCursorColumns(int cursorType) {
        switch (cursorType) {
            case ContactActionEventDAO.UNMANAGED_PEOPLE:
                return UnmanagedPeopleQuery.PROJECTION;
            case ContactActionEventDAO.DELAY_PEOPLE:
                return DelayPeopleQuery.PROJECTION;
            case ContactActionEventDAO.TODAY_PEOPLE:
                return TodayPeopleQuery.PROJECTION;
            case ContactActionEventDAO.TODAY_DONE_PEOPLE:
                return TodayDonePeopleQuery.PROJECTION;
            case ContactActionEventDAO.NEXT_PEOPLE:
                return NextPeopleQuery.PROJECTION;
            default:
                return null;
        }
    }
}
