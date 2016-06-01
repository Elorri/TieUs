package com.elorri.android.tieus.db;

import com.elorri.android.tieus.data.FriendForecastContract;

/**
 * Created by Elorri on 12/04/2016.
 */
public class ContactActionVectorEventDAO {


    public static final String[] JOINT_TABLE_PROJECTION = new String[]{
            FriendForecastContract.EventTable.VIEW_EVENT_ID,
            FriendForecastContract.EventTable.COLUMN_ACTION_ID,
            FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
            FriendForecastContract.EventTable.COLUMN_VECTOR_ID,
            FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
            FriendForecastContract.EventTable.COLUMN_TIME_START,
            FriendForecastContract.EventTable.COLUMN_TIME_END,
            FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
            FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
            FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
            FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
            FriendForecastContract.ContactTable.COLUMN_MOOD,
            FriendForecastContract.VectorTable.VIEW_VECTOR_NAME,
            FriendForecastContract.VectorTable.COLUMN_DATA,
            FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
            FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
            FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
            FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
            FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
            FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
            FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
            FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR
    };


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
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
            + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from (select "
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
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
            + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from (select "
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
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
            + FriendForecastContract.ContactTable.NAME + "."
            + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
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


    public static final String JOINT_LAST_ACTION_EVENT = "select "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", max("
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ") as "
            + FriendForecastContract.EventTable.VIEW_LAST_TIME_END + " from ("
            + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") group by "
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID;


    public static final String JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT = "select "
            + FriendForecastContract.EventTable.VIEW_EVENT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_ACTION_ID + ", lac."
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.EventTable.COLUMN_VECTOR_ID + ", "
            + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
            + FriendForecastContract.VectorTable.VIEW_VECTOR_NAME + ", "
            + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
            + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
            + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
            + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
            + JOINT_LAST_ACTION_EVENT + ") lac inner join ("
            + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") e on lac."
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "= e."
            + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + " order by "
            + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " asc";


    public interface PeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_MOOD_ID = 5;
        int COL_FEEDBACK_EXPECTED_DELAY = 6;
        int COL_FEEDBACK_INCREASED_EXPECTED_DELAY = 7;
        int COL_FREQUENCY_OF_CONTACT = 8;
        int COL_LAST_MOOD_DECREASED = 9;
        int COL_UNTRACKED = 10;
        int COL_MOOD_UNKNOWN = 11;
        int COL_BACKGROUND_COLOR = 12;

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR
        };


        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                ViewTypes.COLUMN_VIEWTYPE
        };
    }


    public interface ManagedPeopleQuery extends PeopleQuery {

        String SELECT_MANAGED_PEOPLE = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }


    public interface UnmanagedPeopleQuery extends PeopleQuery {


        String SELECT = "select "
                + FriendForecastContract.ContactTable._ID + " as "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " = "
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " except "
                + ManagedPeopleQuery.SELECT_MANAGED_PEOPLE;

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UNMANAGED_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + UnmanagedPeopleQuery.SELECT + ")";
    }


    public interface PeopleThatNeedsToFillInDelayFeedbackQuery extends PeopleQuery {


        String SELECT = "select distinct "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is not null and "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " is null order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface PeopleThatNeedMoodUpdateQuery extends PeopleQuery {


        String SELECT_BEFORE_BIND = "select distinct "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where ";

        String SELECT_AFTER_BIND = " between ("
                + FriendForecastContract.EventTable.COLUMN_TIME_END + "+"
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ") and ("
                + FriendForecastContract.EventTable.COLUMN_TIME_END + "+"
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ") and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + "="
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + " and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UPDATE_MOOD + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }

    public interface PeopleThatNeedFrequencyQuery extends PeopleQuery {


        String SELECT = "select distinct "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " is not null and "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + "!= "
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + " and "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";


    }

    public interface AskForFeedbackQuery extends PeopleQuery {

        String SELECT_BEFORE_BIND = "select distinct "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + "= "
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + " and ";

        String SELECT_AFTER_BIND = " > ("
                + FriendForecastContract.EventTable.COLUMN_TIME_END + "+"
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ") and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";


        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }


    public interface PeopleApprochingFrequencyQuery extends PeopleQuery {


        String SELECT_BEFORE_BIND = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_LAST_ACTION_EVENT + ") inner join "
                + FriendForecastContract.ContactTable.NAME + " on "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "="
                + FriendForecastContract.ContactTable.NAME + "."
                + FriendForecastContract.ContactTable._ID + " where ";

        String SELECT_AFTER_BIND = " between ("
                + FriendForecastContract.EventTable.VIEW_LAST_TIME_END + " + "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "*(2/3)) and( "
                + FriendForecastContract.EventTable.VIEW_LAST_TIME_END + " + "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ") and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";


        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }

    public interface PeopleWhoDecreasedMoodQuery extends PeopleQuery {

        String UPDATE_BEFORE_BIND = ""
                + FriendForecastContract.ContactTable._ID + " in (select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + " from ("
                + JOINT_LAST_ACTION_EVENT + ") where ";

        String UPDATE_AFTER_BIND = " > ("
                + FriendForecastContract.EventTable.VIEW_LAST_TIME_END + " + "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "))";


        //This select works only if the update above have been applied before
        String SELECT = "select "
                + FriendForecastContract.ContactTable._ID + " as "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + " > ? and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface DelayPeopleQuery extends PeopleQuery {
        int COL_ACTION = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_DATA = 15;
        int COL_VECTOR_MIMETYPE = 16;

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };


        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + "< ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " = "
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_DELAY_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }

    public interface TodayPeopleQuery extends PeopleQuery {

        int COL_ACTION = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_DATA = 15;
        int COL_VECTOR_MIMETYPE = 16;

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " >= ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " < ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " = "
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_TODAY_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }

    public interface TodayDonePeopleQuery extends PeopleQuery {

        int COL_ACTION = 13;
        int COL_TIME_END = 14;
        int COL_VECTOR_DATA = 15;
        int COL_VECTOR_MIMETYPE = 16;

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_END,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is not null and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " >= ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " < ? order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " desc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_TODAY_DONE_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }

    public interface NextPeopleQuery extends PeopleQuery {

        int COL_ACTION = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_DATA = 15;
        int COL_VECTOR_MIMETYPE = 16;

        String[] PROJECTION = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                FriendForecastContract.ActionTable.VIEW_ACTION_NAME,
                FriendForecastContract.EventTable.COLUMN_TIME_START,
                FriendForecastContract.VectorTable.COLUMN_DATA,
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + FriendForecastContract.ActionTable.VIEW_ACTION_NAME + ", "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + ", "
                + FriendForecastContract.VectorTable.COLUMN_DATA + ", "
                + FriendForecastContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " > ? and "
                + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " = "
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " order by "
                + FriendForecastContract.EventTable.COLUMN_TIME_START + " asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_NEXT_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }


    public interface UntrackedPeopleQuery extends PeopleQuery {

        String SELECT = "select "
                + FriendForecastContract.ContactTable._ID + " as "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " = "
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + " order by lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UNTRACKED_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
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

    public interface PeopleElligibleForFillInDelayAloneUpdateQuery extends PeopleQuery {

        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT + ") union select "
                + FriendForecastContract.ContactTable._ID + " as "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " is not null and "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " except "
                + PeopleThatNeedFrequencyQuery.SELECT;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface PeopleElligibleForFrequencyUpdateQuery extends PeopleQuery {

        String SELECT = "select "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + PeopleThatNeedFrequencyQuery.SELECT + ") union select "
                + FriendForecastContract.ContactTable._ID + " as "
                + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + ", "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + ", "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + ", "
                + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + ", "
                + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is not null and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_FEEDBACK_FREQUENCY + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


}
