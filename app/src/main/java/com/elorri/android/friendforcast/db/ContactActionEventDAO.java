package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.fragments.BoardAdapter;
import com.elorri.android.friendforcast.fragments.DetailAdapter;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Tools;

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
    public static final int ACTION_BY_CONTACT_ID = 5;
    public static final int SOCIAL_NETWORK = 6;
    public static final int UNTRACKED_PEOPLE = 7;


    private static final String JOINT_TABLE_CONTACT_ACTION_EVENT = "select "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.ActionTable.NAME + "."
            + FriendForecastContract.ActionTable.COLUMN_NAME + " as "
            + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from (select "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable._ID + " as "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from "
            + FriendForecastContract.EventTable.NAME + " inner join "
            + FriendForecastContract.ContactTable.NAME + " on "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "="
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable._ID + ") as ec inner join "
            + FriendForecastContract.ActionTable.NAME + " on ec."
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + "="
            + FriendForecastContract.ActionTable.NAME + "."
            + FriendForecastContract.ActionTable._ID + " order by "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " asc";

    public interface PeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_EMOICON_ID = 5;
    }


    public interface TitleQuery {
        int COL_TITLE = 0;
    }


    public interface UnmanagedPeopleQuery extends PeopleQuery {


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID
        };

        String SELECT_UNMANAGED_PEOPLE = "select "
                + FriendForecastContract.ContactTable._ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME+", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_social_network + " except select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }

    public interface SocialNetworkQuery extends PeopleQuery {


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID
        };

        String SELECT_SOCIAL_NETWORK = "select "
                + FriendForecastContract.ContactTable._ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME+", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " = "
                + R.drawable.ic_social_network + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }

    public interface UntrackedPeopleQuery extends PeopleQuery {


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID
        };

        String SELECT_UNTRACKED_PEOPLE = "select "
                + FriendForecastContract.ContactTable._ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME+", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " = "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }

    public interface DelayPeopleQuery extends PeopleQuery {
        int COL_ACTION = 6;
        int COL_TIME_START = 7;


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START
        };

        String SELECT_DELAY_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + "< ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START+" asc";
    }

    public interface TodayPeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_START = 7;


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START
        };

        String SELECT_TODAY_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START+" asc";
    }

    public interface TodayDonePeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_END = 7;


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_END
        };

        String SELECT_TODAY_DONE_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is not null order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START+" desc";
    }

    public interface NextPeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_START = 7;


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START
        };

        String SELECT_NEXT_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " > ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START+" asc";
    }


    public interface ActionByContactIdQuery {
        int COL_ACTION_NAME = 0;
        int COL_TIME_START = 1;

        String SELECTION = FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=?";

        String[] PROJECTION = {
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START
        };

        String SELECT_ACTION_BY_CONTACT_ID = "select "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " from ("
                + JOINT_TABLE_CONTACT_ACTION_EVENT + ") order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        return getCursor(cursorType, db, null);
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String contactId) {
        switch (cursorType) {
            case UNMANAGED_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE);
                return db.rawQuery(UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE, null);
            }
            case DELAY_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + DelayPeopleQuery.SELECT_DELAY_PEOPLE);
                long todayStart = DateUtils.todayStart();
                return db.rawQuery(DelayPeopleQuery.SELECT_DELAY_PEOPLE, new String[]{String
                        .valueOf(todayStart)});
            }
            case TODAY_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + TodayPeopleQuery.SELECT_TODAY_PEOPLE);
                long todayStart = DateUtils.todayStart();
                long todayEnd = DateUtils.tomorrowStart();
                return db.rawQuery(TodayPeopleQuery.SELECT_TODAY_PEOPLE, new String[]{String
                        .valueOf(todayStart),
                        String.valueOf(todayEnd)});
            }
            case TODAY_DONE_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + TodayDonePeopleQuery.SELECT_TODAY_DONE_PEOPLE);
                long todayStart = DateUtils.todayStart();
                long todayEnd = DateUtils.tomorrowStart();
                return db.rawQuery(TodayDonePeopleQuery.SELECT_TODAY_DONE_PEOPLE, new
                        String[]{String.valueOf
                        (todayStart), String.valueOf(todayEnd)});
            }
            case NEXT_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + NextPeopleQuery.SELECT_NEXT_PEOPLE);
                long tomorrow = DateUtils.tomorrowStart();
                return db.rawQuery(NextPeopleQuery.SELECT_NEXT_PEOPLE, new String[]{String
                        .valueOf(tomorrow)});
            }
            case SOCIAL_NETWORK: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + SocialNetworkQuery.SELECT_SOCIAL_NETWORK);
                return db.rawQuery(SocialNetworkQuery.SELECT_SOCIAL_NETWORK, null);
            }
            case UNTRACKED_PEOPLE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + UntrackedPeopleQuery.SELECT_UNTRACKED_PEOPLE);
                return db.rawQuery(UntrackedPeopleQuery.SELECT_UNTRACKED_PEOPLE, null);
            }
            case ACTION_BY_CONTACT_ID: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY " + ActionByContactIdQuery.SELECT_ACTION_BY_CONTACT_ID);
                //return db.rawQuery(ActionByContactIdQuery.SELECT_ACTION_BY_CONTACT_ID, null);
                return db.query("(" + JOINT_TABLE_CONTACT_ACTION_EVENT + ")", ActionByContactIdQuery.PROJECTION,
                        ActionByContactIdQuery.SELECTION,
                        new String[]{contactId}, null, null, null);
            }
            default:
                return null;
        }
    }


    public static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes) {
        return getCursorWithViewTypes(cursorType, db, viewTypes, null);
    }

    public static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes, String contactId) {

        switch (cursorType) {
            case UNMANAGED_PEOPLE:
                return CursorUtils.setViewType(
                        getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_UNMANAGED_PEOPLE);
            case DELAY_PEOPLE:
                return CursorUtils.setViewType(getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_DELAY_PEOPLE);
            case TODAY_PEOPLE:
                return CursorUtils.setViewType(getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_TODAY_PEOPLE);
            case TODAY_DONE_PEOPLE:
                return CursorUtils.setViewType(getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_TODAY_DONE_PEOPLE);
            case NEXT_PEOPLE:
                return CursorUtils.setViewType(getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_NEXT_PEOPLE);
            case SOCIAL_NETWORK:
                return CursorUtils.setViewType(
                        getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_SOCIAL_NETWORK);
            case UNTRACKED_PEOPLE:
                return CursorUtils.setViewType(
                        getCursor(cursorType, db),
                        viewTypes, BoardAdapter.VIEW_UNTRACKED_PEOPLE);
            case ACTION_BY_CONTACT_ID:
                return CursorUtils.setViewType(getCursor(cursorType, db,
                                contactId),
                        viewTypes, DetailAdapter.VIEW_ACTION);
            default:
                return null;
        }
    }


    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(BoardAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(cursorType, db, viewTypes));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes, String contactId) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(DetailAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(cursorType, db, viewTypes, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
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
            case SOCIAL_NETWORK:
                return context.getResources().getString(R.string.social_network);
            case UNTRACKED_PEOPLE:
                return context.getResources().getString(R.string.untracked);
            case ACTION_BY_CONTACT_ID:
                return context.getResources().getString(R.string.next_actions);
            default:
                return null;
        }
    }
}
