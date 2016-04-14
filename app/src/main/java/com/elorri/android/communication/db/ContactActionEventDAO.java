package com.elorri.android.communication.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.communication.data.CommunicationContract;

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
            + CommunicationContract.EventEntry.VIEW_EVENT_ID + ", "
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID + ", "
            + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + CommunicationContract.ActionEntry.NAME + "."
            + CommunicationContract.ActionEntry.COLUMN_NAME + " as "
            + CommunicationContract.ActionEntry.VIEW_ACTION_NAME + ", "
            + CommunicationContract.EventEntry.COLUMN_TIME_START + ", "
            + CommunicationContract.EventEntry.COLUMN_TIME_END + ", "
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " from (select "
            + CommunicationContract.EventEntry.NAME + "."
            + CommunicationContract.EventEntry._ID + " as "
            + CommunicationContract.EventEntry.VIEW_EVENT_ID + ", "
            + CommunicationContract.EventEntry.NAME + "."
            + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
            + CommunicationContract.EventEntry.NAME + "."
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID + ", "
            + CommunicationContract.EventEntry.NAME + "."
            + CommunicationContract.EventEntry.COLUMN_TIME_START + ", "
            + CommunicationContract.EventEntry.NAME + "."
            + CommunicationContract.EventEntry.COLUMN_TIME_END + ", "
            + CommunicationContract.ContactEntry.NAME + "."
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + CommunicationContract.ContactEntry.NAME + "."
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " from "
            + CommunicationContract.EventEntry.NAME + " inner join "
            + CommunicationContract.ContactEntry.NAME + " on "
            + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + "="
            + CommunicationContract.ContactEntry.NAME + "."
            + CommunicationContract.ContactEntry._ID + ") as ec inner join "
            + CommunicationContract.ActionEntry.NAME + " on ec."
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID + "="
            + CommunicationContract.ActionEntry.NAME + "."
            + CommunicationContract.ActionEntry._ID;




    public interface UnmanagedPeopleQuery {

        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY
        };

        String SELECT_UNMANAGED_PEOPLE = "select "
                + CommunicationContract.ContactEntry._ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " from "
                + CommunicationContract.ContactEntry.NAME + " except select "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ")";
    }

    public interface DelayPeopleQuery {

        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                CommunicationContract.ActionEntry.VIEW_ACTION_NAME,
                CommunicationContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_DELAY_PEOPLE = "select "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + "< ? and "
                + CommunicationContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayPeopleQuery {
        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                CommunicationContract.ActionEntry.VIEW_ACTION_NAME,
                CommunicationContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_TODAY_PEOPLE = "select "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + CommunicationContract.EventEntry.COLUMN_TIME_END + " is null";
    }

    public interface TodayDonePeopleQuery {
        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                CommunicationContract.ActionEntry.VIEW_ACTION_NAME,
                CommunicationContract.EventEntry.COLUMN_TIME_END
        };

        String SELECT_TODAY_DONE_PEOPLE = "select "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + CommunicationContract.EventEntry.COLUMN_TIME_END + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + " between ? and ? and "
                + CommunicationContract.EventEntry.COLUMN_TIME_END + " is not null";
    }

    public interface NextPeopleQuery {

        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                CommunicationContract.ActionEntry.VIEW_ACTION_NAME,
                CommunicationContract.EventEntry.COLUMN_TIME_START
        };

        String SELECT_NEXT_PEOPLE = "select "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
                + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + CommunicationContract.EventEntry.COLUMN_TIME_START + " > ? and "
                + CommunicationContract.EventEntry.COLUMN_TIME_END + " is null";
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
