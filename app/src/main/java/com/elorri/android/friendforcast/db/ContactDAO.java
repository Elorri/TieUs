package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {


    public static final int CONTACT_BY_ID = 1;


    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactTable.NAME +
            "(" + FriendForecastContract.ContactTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " TEXT NOT NULL,"
            + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + " TEXT,"
            + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ") ON " +
            "CONFLICT REPLACE)";


    public interface ContactQuery {

        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_EMOICON_BY_ID = 5;
        int COL_PROJECTION_TYPE = 6;


        String SELECTION = FriendForecastContract.ContactTable._ID + "=?";

        //This projection won't be used in queries. It will only be used for checking the column
        // names easily. PROJECTION and PROJECTION_QUERY should match.
        String[] PROJECTION= {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                Projections.COLUMN_PROJECTION_TYPE
        };

        //This is the projection that will be used in queries.
        String[] PROJECTION_QUERY = {
                FriendForecastContract.ContactTable._ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                "lower(" + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                +FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL,
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                Projections.VIEW_CONTACT + " as " + Projections.COLUMN_PROJECTION_TYPE
        };


    }

    public interface RatioQuery {

        int COL_RATIO = 0;
        int COL_PROJECTION_TYPE = 1;

        String SELECT_RATIO_EMOICONE = "select "
                + FriendForecastContract.ContactTable.VIEW_PART + "/("
                + FriendForecastContract.ContactTable.VIEW_TOTAL + "*1.0) as "
                + FriendForecastContract.ContactTable.VIEW_RATIO + ", "
                + Projections.VIEW_FORECAST + " as "
                + Projections.COLUMN_PROJECTION_TYPE + " from (select count("
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ") as "
                + FriendForecastContract.ContactTable.VIEW_TOTAL + " from "
                + FriendForecastContract.ContactTable.NAME + ") inner join (select count("
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + ") as "
                + FriendForecastContract.ContactTable.VIEW_PART + " from "
                + FriendForecastContract.ContactTable.NAME + " where "
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "="
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
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_EMOICON_ID, emoiconId);
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
        contentValues.put(FriendForecastContract.ContactTable.COLUMN_EMOICON_ID,
                cursor.getString(ContactQuery.COL_EMOICON_BY_ID));
        return contentValues;
    }

    public static Cursor getCursor(SQLiteDatabase db) {
        return db.rawQuery(RatioQuery.SELECT_RATIO_EMOICONE, null);
    }


    public static Cursor getCursor(String contactId, int cursorType, SQLiteDatabase db) {
        switch (cursorType) {
            case CONTACT_BY_ID: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                        "QUERY CONTACT_BY_ID");
                return db.query(FriendForecastContract.ContactTable.NAME, ContactQuery.PROJECTION_QUERY, ContactQuery.SELECTION,
                        new String[]{contactId}, null, null, null);
            }
            default:
                return null;
        }
    }


}
