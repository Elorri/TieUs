package com.elorri.android.friendforcast.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

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
            + FriendForecastContract.EventEntry.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventEntry.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.ActionEntry.NAME + "."
            + FriendForecastContract.ActionEntry.COLUMN_NAME + " as "
            + FriendForecastContract.ActionEntry.VIEW_ACTION_NAME + ", "
            + FriendForecastContract.EventEntry.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventEntry.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + " from (select "
            + FriendForecastContract.EventEntry.NAME + "."
            + FriendForecastContract.EventEntry._ID + " as "
            + FriendForecastContract.EventEntry.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventEntry.NAME + "."
            + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventEntry.NAME + "."
            + FriendForecastContract.EventEntry.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventEntry.NAME + "."
            + FriendForecastContract.EventEntry.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventEntry.NAME + "."
            + FriendForecastContract.EventEntry.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactEntry.NAME + "."
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactEntry.NAME + "."
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactEntry.NAME + "."
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactEntry.NAME + "."
            + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + " from "
            + FriendForecastContract.EventEntry.NAME + " inner join "
            + FriendForecastContract.ContactEntry.NAME + " on "
            + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + "="
            + FriendForecastContract.ContactEntry.NAME + "."
            + FriendForecastContract.ContactEntry._ID + ") as ec inner join "
            + FriendForecastContract.ActionEntry.NAME + " on ec."
            + FriendForecastContract.EventEntry.COLUMN_ACTION_ID + "="
            + FriendForecastContract.ActionEntry.NAME + "."
            + FriendForecastContract.ActionEntry._ID;

    public interface PeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
    }


    public interface TitleQuery {
        int COL_TITLE = 0;
    }


    public interface UnmanagedPeopleQuery extends PeopleQuery{

        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;


        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID
        };

        String SELECT_UNMANAGED_PEOPLE = "select "
                + FriendForecastContract.ContactEntry._ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                 + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + " from "
                + FriendForecastContract.ContactEntry.NAME + " except select "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ")";
    }

    public interface DelayPeopleQuery extends PeopleQuery{
        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;


        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendForecastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_DELAY_PEOPLE = "select "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + "< ? and "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayPeopleQuery extends PeopleQuery{

        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;


        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendForecastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_TODAY_PEOPLE = "select "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayDonePeopleQuery extends PeopleQuery{

        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_END = 6;


        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendForecastContract.EventEntry.COLUMN_TIME_END
        };

        String SELECT_TODAY_DONE_PEOPLE = "select "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END + " is not null";
    }

    public interface NextPeopleQuery extends PeopleQuery{


        int COL_CONTACT_NAME = 3;
        int COL_EMOICON_ID = 4;
        int COL_ACTION = 5;
        int COL_TIME_START = 6;



        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionEntry.VIEW_ACTION_NAME,
                FriendForecastContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_NEXT_PEOPLE = "select "
                + FriendForecastContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionEntry.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventEntry.COLUMN_TIME_START + " > ? and "
                + FriendForecastContract.EventEntry.COLUMN_TIME_END + " is null";
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
