package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;

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
            + FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " INTEGER, "
            + "UNIQUE (" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ") ON CONFLICT REPLACE, " +
            "CONSTRAINT " + FriendForecastContract.ContactTable.UNTRACKED_CONSTRAINT + " check  (" +
            FriendForecastContract.ContactTable.COLUMN_UNTRACKED + " between " +
            FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + " AND "
            + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "))";


    public interface ContactQuery {


        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_EMOICON_BY_ID = 5;
        int COL_PROJECTION_TYPE = 6;


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
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED
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
                FriendForecastContract.ContactTable.COLUMN_UNTRACKED
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
                + FriendForecastContract.ContactTable.NAME + ") inner join (select count("
                + FriendForecastContract.ContactTable.COLUMN_MOOD + ") as "
                + FriendForecastContract.ContactTable.VIEW_PART + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_MOOD + "="
                + R.drawable.ic_sentiment_satisfied_black_48dp + ")";
    }

    public static ContentValues getContentValues(Cursor androidContactCursor, int emoiconId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_THUMBNAIL));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_MOOD, emoiconId);
        return contentValues;
    }


    public static ContentValues getContentValues(Cursor cursor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactTable._ID,
                cursor.getString(ContactQuery.COL_ID));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                cursor.getString(ContactQuery.COL_ANDROID_ID));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                cursor.getString(ContactQuery.COL_ANDROID_LOOKUP_KEY));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                cursor.getString(ContactQuery.COL_ANDROID_CONTACT_NAME));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                cursor.getString(ContactQuery.COL_THUMBNAIL));
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_MOOD,
                cursor.getString(ContactQuery.COL_EMOICON_BY_ID));
        return contentValues;
    }

    public static ContentValues getContentValues(String  moodIconId) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable.COLUMN_MOOD, moodIconId);
        return values;
    }

    public static ContentValues getContentValues(long  lastMoodDecreased) {
        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_DECREASED, lastMoodDecreased);
        return values;
    }




}
