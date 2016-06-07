package com.elorri.android.tieus.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {


    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactTable.NAME +
            "(" + FriendForecastContract.ContactTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + " TEXT,"
            + FriendForecastContract.ContactTable.COLUMN_MOOD + " TEXT NOT NULL, "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + " INTEGER, "
            + FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + " INTEGER, "
            + FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " INTEGER, "
            + FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED + " INTEGER, "
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " INTEGER NOT NULL, "
            + FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + " INTEGER NOT NULL, "
            + FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR + " INTEGER NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ") ON CONFLICT REPLACE, "
            + "CONSTRAINT " + FriendForecastContract.ContactTable.UNTRACKED_CONSTRAINT + " check  (" +
            FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " between " +
            FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " AND "
            + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + ")"
            + "CONSTRAINT " + FriendForecastContract.ContactTable.MOOD_UNKNOWN_CONSTRAINT + " check  (" +
            FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN + " between " +
            FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + " AND "
            + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "))";


    public interface ContactQuery {


        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_MOOD_ID = 5;
        int COL_UNTRACKED = 10;
        int COL_MOOD_UNKNOWN = 11;
        int COL_BACKGROUND_COLOR = 12;


        String SELECTION = FriendForecastContract.ContactTable._ID + "=?";


        String[] PROJECTION = {
                FriendForecastContract.ContactTable._ID,
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

        //This projection won't be used in queries. It will only be used for checking the column
        // names easily. PROJECTION_QUERY and PROJECTION_QUERY should match.
        String[] PROJECTION_WITH_VIEWTYPE = {
                FriendForecastContract.ContactTable._ID,
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

        //This is the projection that will be used in queries.
        String[] PROJECTION_QUERY = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
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


        String[] PROJECTION_WITH_VIEWTYPE_QUERY = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_MOOD,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY,
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED,
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED,
                FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN,
                FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                ViewTypes.VIEW_CONTACT + " as " + ViewTypes.COLUMN_VIEWTYPE
        };


    }

    public interface RatioQuery {

        int COL_RATIO = 0;
        int COL_PROJECTION_TYPE = 1;

        String[] PROJECTION = new String[]{FriendForecastContract.ContactTable.VIEW_RATIO, ViewTypes.COLUMN_VIEWTYPE};

        String SELECT_WITH_VIEWTYPE = "select "
                + FriendForecastContract.ContactTable.VIEW_PART + "/("
                + FriendForecastContract.ContactTable.VIEW_TOTAL + "*1.0) as "
                + FriendForecastContract.ContactTable.VIEW_RATIO + ", "
                + ViewTypes.VIEW_FORECAST + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from (select count("
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ") as "
                + FriendForecastContract.ContactTable.VIEW_TOTAL + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + ") inner join (select" +
                " count("
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ") as "
                + FriendForecastContract.ContactTable.VIEW_PART + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + "="
                + R.drawable.ic_sentiment_satisfied_black_48dp + " and "
                + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + "="
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + ")";
    }

    public static ContentValues getContentValues(Cursor androidContactCursor,
                                                 int moodId,
                                                 String untrackedValue,
                                                 String moodUnknownValue,
                                                 int backgroundColorValue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_THUMBNAIL));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_MOOD, moodId);
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_UNTRACKED, untrackedValue);
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_MOOD_UNKNOWN, moodUnknownValue);
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_BACKGROUND_COLOR, backgroundColorValue);
        return contentValues;
    }


    public static ContentValues getContentValues(Cursor androidContactCursor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_THUMBNAIL));
        return contentValues;
    }


}
