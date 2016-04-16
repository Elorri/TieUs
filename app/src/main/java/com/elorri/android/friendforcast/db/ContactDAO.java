package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {

    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactEntry.NAME +
            "(" + FriendForecastContract.ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL,"
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + " TEXT NOT NULL,"
            + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + " TEXT NOT NULL)";


    public static final String INSERT = "INSERT INTO "
            + FriendForecastContract.ContactEntry.NAME + " ("
            + FriendForecastContract.ContactEntry._ID + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID + ") "
            + "VALUES (?, ?, ?, ?, ?)";


    public interface ContactQuery {

        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;

        String[] PROJECTION = {
                FriendForecastContract.ContactEntry._ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME
        };
    }

    public static ContentValues getContentValuesInsert(Cursor androidContactCursor, int emoiconId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(FriendForecastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(FriendForecastContract.ContactEntry.COLUMN_EMOICON_ID, emoiconId);
        return contentValues;

    }


}
