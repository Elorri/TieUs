package com.elorri.android.communication.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {

    public static final String CREATE = "CREATE TABLE "
            + CommunicationContract.ContactEntry.NAME +
            "(" + CommunicationContract.ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + " TEXT NOT NULL,"
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + " TEXT NOT NULL)";


    public static final String INSERT = "INSERT INTO "
            + CommunicationContract.ContactEntry.NAME + " ("
            + CommunicationContract.ContactEntry._ID + ", "
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + ", "
            + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ") "
            + "VALUES (?, ?, ?)";


    public interface ContactQuery {

        int COL_ID = 0;
        int COL_ANDROID_ID = 1;
        int COL_ANDROID_LOOKUP_KEY = 2;

        String[] PROJECTION = {
                CommunicationContract.ContactEntry._ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY
        };
    }

    public static ContentValues getContentValues(Cursor androidContactCursor) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID,
                    androidContactCursor.getString(AndroidDAO.ContactQuery.COL_ID));
            contentValues.put(CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                    androidContactCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
            return contentValues;

    }


}
