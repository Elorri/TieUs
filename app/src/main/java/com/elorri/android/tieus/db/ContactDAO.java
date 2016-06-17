package com.elorri.android.tieus.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {


    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.ContactTable.NAME +
            "(" + TieUsContract.ContactTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL,"
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " TEXT NOT NULL,"
            + TieUsContract.ContactTable.COLUMN_THUMBNAIL + " TEXT,"
            + TieUsContract.ContactTable.COLUMN_SATISFACTION + " TEXT NOT NULL, "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + " INTEGER, "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + " INTEGER, "
            + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " INTEGER, "
            + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + " INTEGER, "
            + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " INTEGER NOT NULL, "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + " INTEGER NOT NULL, "
            + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " INTEGER NOT NULL, "
            + "UNIQUE (" + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ") ON CONFLICT REPLACE, "
            + "CONSTRAINT " + TieUsContract.ContactTable.UNFOLLOWED_CONSTRAINT + " check  (" +
            TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " between " +
            TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " AND "
            + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + ")"
            + "CONSTRAINT " + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_CONSTRAINT + " check  (" +
            TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + " between " +
            TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + " AND "
            + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "))";


    public interface ContactQuery {


        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_SATISFACTION_ID = 5;
        int COL_UNFOLLOWED = 10;
        int COL_SATISFACTION_UNKNOWN = 11;
        int COL_BACKGROUND_COLOR = 12;


        String SELECTION = TieUsContract.ContactTable._ID + "=?";


        String[] PROJECTION = {
                TieUsContract.ContactTable._ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR
        };

        //This projection won't be used in queries. It will only be used for checking the column
        // names easily. PROJECTION_QUERY and PROJECTION_QUERY should match.
        String[] PROJECTION_WITH_VIEWTYPE = {
                TieUsContract.ContactTable._ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                ViewTypes.COLUMN_VIEWTYPE
        };

        //This is the projection that will be used in queries.
        String[] PROJECTION_QUERY = {
                TieUsContract.ContactTable._ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR
        };


        String[] PROJECTION_WITH_VIEWTYPE_QUERY = {
                TieUsContract.ContactTable._ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                        + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                ViewTypes.VIEW_CONTACT + " as " + ViewTypes.COLUMN_VIEWTYPE
        };


    }

    public interface RatioQuery {

        int COL_RATIO = 0;

        String[] PROJECTION = new String[]{TieUsContract.ContactTable.VIEW_RATIO, ViewTypes.COLUMN_VIEWTYPE};

        String SELECT_WITH_VIEWTYPE = "select "
                + TieUsContract.ContactTable.VIEW_PART + "/("
                + TieUsContract.ContactTable.VIEW_TOTAL + "*1.0) as "
                + TieUsContract.ContactTable.VIEW_RATIO + ", "
                + ViewTypes.VIEW_FORECAST + " as "
                + ViewTypes.COLUMN_VIEWTYPE + " from (select count("
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ") as "
                + TieUsContract.ContactTable.VIEW_TOTAL + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + ") inner join (select" +
                " count("
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ") as "
                + TieUsContract.ContactTable.VIEW_PART + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + "="
                + R.drawable.ic_sentiment_satisfied_black_48dp + " and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + ")";
    }

    public static ContentValues getContentValues(Cursor androidContactCursor,
                                                 int satisfactionId,
                                                 String unfollowedValue,
                                                 String satisfactionUnknownValue,
                                                 int backgroundColorValue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_THUMBNAIL));
        contentValues.put(TieUsContract.ContactTable.COLUMN_SATISFACTION, satisfactionId);
        contentValues.put(TieUsContract.ContactTable.COLUMN_UNFOLLOWED, unfollowedValue);
        contentValues.put(TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN, satisfactionUnknownValue);
        contentValues.put(TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR, backgroundColorValue);
        return contentValues;
    }


    public static ContentValues getContentValues(Cursor androidContactCursor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_THUMBNAIL));
        return contentValues;
    }


}
