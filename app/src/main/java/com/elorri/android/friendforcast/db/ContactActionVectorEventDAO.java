package com.elorri.android.friendforcast.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.DateUtils;

/**
 * Created by Elorri on 12/04/2016.
 */
public class ContactActionVectorEventDAO {

    public static final int UNMANAGED_PEOPLE = 0;
    public static final int DELAY_PEOPLE = 1;
    public static final int TODAY_PEOPLE = 2;
    public static final int TODAY_DONE_PEOPLE = 3;
    public static final int NEXT_PEOPLE = 4;
    public static final int NEXT_ACTION_BY_CONTACT_ID = 5;
    public static final int DONE_ACTION_BY_CONTACT_ID = 6;
    public static final int ACTION_BY_CONTACT_ID = 7;
    public static final int UNTRACKED_PEOPLE = 8;


    public static final String JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT = "select "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + ", "
            + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
            + FriendForecastContract.VectorTable.COLUMN_NAME + " as "
            + FriendForecastContract.VectorTable.VIEW_VECTOR_NAME + ", "
            + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
            + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + " from (select "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + ", "
            + FriendForecastContract.ActionTable.NAME + "."
            + FriendForecastContract.ActionTable.COLUMN_NAME + " as "
            + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + " from (select "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable._ID + " as "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", "
            + FriendForecastContract.EventTable.NAME + "."
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + ", "
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
            + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + " from "
            + FriendForecastContract.EventTable.NAME + " inner join "
            + FriendForecastContract.ContactTable.NAME + " on "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "="
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable._ID + ") as ec inner join "
            + FriendForecastContract.ActionTable.NAME + " on ec."
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + "="
            + FriendForecastContract.ActionTable.NAME + "."
            + FriendForecastContract.ActionTable._ID + ") as cae inner join "
            + FriendForecastContract.VectorTable.NAME + " on cae."
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "="
            + FriendForecastContract.VectorTable.NAME + "."
            + FriendForecastContract.VectorTable._ID + " order by "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " asc";


    public interface PeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_EMOICON_ID = 5;


        String[] PROJECTION = new String[]{
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };
    }


    public interface UnmanagedPeopleQuery extends PeopleQuery {


        String SELECT_UNMANAGED_PEOPLE = "select "
                + FriendForecastContract.ContactTable._ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + ViewTypes.VIEW_UNMANAGED_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_social_network + " except select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + ViewTypes.VIEW_UNMANAGED_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }


    public interface PeopleLastActedForQuery {


        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };


        String[] PROJECTION_QUERY = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + " as " + ViewTypes.COLUMN_VIEWTYPE};

        String SELECTION_ALL = FriendForecastContract.ContactTable.COLUMN_MOOD + "=? and "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " is null";


    }


    public interface UpdateMoodQuery {

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_QUERY = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.VIEW_UPDATE_MOOD + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECTION_ONLY_DELAYED =
                FriendForecastContract.ContactTable.COLUMN_MOOD + "=? and "
                        + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "<? and "
                        + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ">? ";


    }

    public interface FrequencyQuery {
        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] PROJECTION_QUERY = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECTION_ONLY_NO_FREQUENCY =
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " is not null and "
                        + FriendForecastContract.ContactTable.COLUMN_MOOD + "!= ? and "
                        + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is null";


    }

    public interface AskForFeedbackQuery {
        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] PROJECTION_QUERY = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECTION_NOT_ANSWERING_PEOPLE =
                FriendForecastContract.ContactTable.COLUMN_MOOD + "= ? and "
                        + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + "<?";


    }

    //TODO add ApprochingLastDelayQuery again
//    public interface ApprochingLastDelayQuery {
//        String[] PROJECTION = new String[]{
//                FriendForecastContract.ContactTable._ID,
//                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
//                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
//                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
//                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
//                FriendForecastContract.ContactTable.COLUMN_MOOD,
//                ViewTypes.COLUMN_VIEWTYPE
//        };
//        String[] PROJECTION_QUERY = new String[]{
//                FriendForecastContract.ContactTable._ID,
//                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
//                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
//                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
//                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
//                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
//                FriendForecastContract.ContactTable.COLUMN_MOOD,
//                ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY + " as " + ViewTypes.COLUMN_VIEWTYPE
//        };
//
//        //TODO replace with a select minus. Select all contacts nearby their deadline and remove
//        // those in table event that have a finished action in the same delay.
//        String SELECTION_NEARBY_DECREASED_MOOD = " ? between "
//                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + " and "
//                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "+("
//                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "*(2/3))";
//    }

    public interface PeopleWhoChangedMoodQuery {
        int COL_ID=0;
        int COL_MOOD=5;

        String[] PROJECTION = new String[]{
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] PROJECTION_QUERY = new String[]{
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                ViewTypes.VIEW_NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY + " as " + ViewTypes.COLUMN_VIEWTYPE
        };


        String SELECTION_DECREASED_MOOD =
                FriendForecastContract.EventTable.COLUMN_TIME_END + " between "
                        + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + " and "
                        + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "+"
                        + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT;
    }


    public interface UntrackedPeopleQuery extends PeopleQuery {


        String SELECT_UNTRACKED_PEOPLE = "select "
                + FriendForecastContract.ContactTable._ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + ViewTypes.VIEW_UNTRACKED_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " = "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }

    public interface DelayPeopleQuery extends PeopleQuery {
        int COL_ACTION = 6;
        int COL_TIME_START = 7;
        int COL_VECTOR_DATA = 8;
        int COL_VECTOR_MIMETYPE = 9;


        String SELECT_DELAY_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
                + ViewTypes.VIEW_DELAY_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + "< ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";
    }

    public interface TodayPeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_START = 7;
        int COL_VECTOR_DATA = 8;
        int COL_VECTOR_MIMETYPE = 9;


        String SELECT_TODAY_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
                + ViewTypes.VIEW_TODAY_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";
    }

    public interface TodayDonePeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_END = 7;
        int COL_VECTOR_DATA = 8;
        int COL_VECTOR_MIMETYPE = 9;


        String SELECT_TODAY_DONE_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
                + ViewTypes.VIEW_TODAY_DONE_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " between ? and ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is not null order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " desc";
    }

    public interface NextPeopleQuery extends PeopleQuery {

        int COL_ACTION = 6;
        int COL_TIME_START = 7;
        int COL_VECTOR_DATA = 8;
        int COL_VECTOR_MIMETYPE = 9;


        String SELECT_NEXT_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "), "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
                + ViewTypes.VIEW_NEXT_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " > ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_do_not_disturb_alt_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + " != "
                + R.drawable.ic_social_network + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";
    }


    public interface VectorActionByContactIdQuery {
        int COL_EVENT_ID = 0;
        int COL_VECTOR_LOGO_ID = 1;
        int COL_VECTOR_MIMETYPE = 2;
        int COL_ACTION_NAME = 3;
        int COL_TIME_START = 4;
        int COL_TIME_END = 5;
        int COL_PROJECTION_TYPE = 6;

        String SELECTION_ALL = FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=?";
        String SELECTION_UNDONE = FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null";
        String SELECTION_DONE = FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is not null";

        String SORT_ORDER_UNDONE = FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";
        String SORT_ORDER_DONE = FriendForecastContract.EventTable.COLUMN_TIME_END + " desc";

        String[] PROJECTION_ALL = {
                FriendForecastContract.EventTable.VIEW_EVENT_ID,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_ALL_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_NEXT = {
                FriendForecastContract.EventTable.VIEW_EVENT_ID,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_NEXT_QUERY = {
                FriendForecastContract.EventTable.VIEW_EVENT_ID,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_NEXT_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_DONE = {
                FriendForecastContract.EventTable.VIEW_EVENT_ID,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_DONE_QUERY = {
                FriendForecastContract.EventTable.VIEW_EVENT_ID,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_DONE_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };


    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db) {
        return getCursor(cursorType, db, null);
    }

    public static Cursor getCursor(int cursorType, SQLiteDatabase db, String contactId) {
        switch (cursorType) {
            case UNMANAGED_PEOPLE: {
                return db.rawQuery(UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE, null);
            }
            case DELAY_PEOPLE: {
                long todayStart = DateUtils.todayStart();
                return db.rawQuery(DelayPeopleQuery.SELECT_DELAY_PEOPLE, new String[]{String
                        .valueOf(todayStart)});
            }
            case TODAY_PEOPLE: {
                long todayStart = DateUtils.todayStart();
                long todayEnd = DateUtils.tomorrowStart();
                return db.rawQuery(TodayPeopleQuery.SELECT_TODAY_PEOPLE, new String[]{String
                        .valueOf(todayStart),
                        String.valueOf(todayEnd)});
            }
            case TODAY_DONE_PEOPLE: {
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
            case UNTRACKED_PEOPLE: {
                return db.rawQuery(UntrackedPeopleQuery.SELECT_UNTRACKED_PEOPLE, null);
            }
            case ACTION_BY_CONTACT_ID: {
                Cursor cursor = db.query("(" + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                        VectorActionByContactIdQuery.PROJECTION_ALL,
                        VectorActionByContactIdQuery.SELECTION_ALL,
                        new String[]{contactId}, null, null, null);
                return cursor;
            }
            case NEXT_ACTION_BY_CONTACT_ID: {
                Cursor cursor = db.query("(" + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                        VectorActionByContactIdQuery.PROJECTION_NEXT_QUERY,
                        VectorActionByContactIdQuery.SELECTION_UNDONE,
                        new String[]{contactId}, null, null, VectorActionByContactIdQuery
                                .SORT_ORDER_UNDONE);
                return cursor;
            }
            case DONE_ACTION_BY_CONTACT_ID: {
                Cursor cursor = db.query("(" + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                        VectorActionByContactIdQuery.PROJECTION_DONE_QUERY,
                        VectorActionByContactIdQuery.SELECTION_DONE,
                        new String[]{contactId}, null, null, VectorActionByContactIdQuery
                                .SORT_ORDER_DONE);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + cursor.getCount());
                return cursor;
            }
            default:
                return null;
        }
    }


}
