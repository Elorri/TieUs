package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.friendforcast.data.FriendCastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {

    public static final String CREATE = "CREATE TABLE "
            + FriendCastContract.ContactEntry.NAME +
            "(" + FriendCastContract.ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL,"
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + " TEXT NOT NULL,"
            + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + " TEXT NOT NULL)";


    public static final String INSERT = "INSERT INTO "
            + FriendCastContract.ContactEntry.NAME + " ("
            + FriendCastContract.ContactEntry._ID + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME + ", "
            + FriendCastContract.ContactEntry.COLUMN_EMOICON_ID + ") "
            + "VALUES (?, ?, ?, ?, ?)";


    public interface ContactQuery {

        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;
        int COL_ANDROID_CONTACT_NAME = 3;

        String[] PROJECTION = {
                FriendCastContract.ContactEntry._ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME
        };
    }

    public static ContentValues getContentValuesInsert(Cursor androidContactCursor, int emoiconId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
        contentValues.put(FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
        contentValues.put(FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_NAME,
                androidContactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME));
        contentValues.put(FriendCastContract.ContactEntry.COLUMN_EMOICON_ID, emoiconId);
        return contentValues;

    }


}
