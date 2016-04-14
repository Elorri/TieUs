package com.elorri.android.communication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;

import com.elorri.android.communication.data.CommunicationContract;
import com.elorri.android.communication.extra.CursorUtils;

/**
 * Created by Elorri on 13/04/2016.
 */
public class AndroidContactActionEventDAO {

    public interface TitleQuery {
        int COL_TITLE=0;
    }

    public interface UnmanagedPeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
    }

    public interface DelayPeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_ACTION = 3;
        int COL_TIME_START = 4;
        int COL_CONTACT_NAME = 5;
    }

    public interface TodayPeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_ACTION = 3;
        int COL_TIME_START = 4;
        int COL_CONTACT_NAME = 5;
    }

    public interface TodayDonePeopleQuery {

        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_ACTION = 3;
        int COL_TIME_END = 4;
        int COL_CONTACT_NAME = 5;
    }

    public interface NextPeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_ACTION = 3;
        int COL_TIME_START = 4;
        int COL_CONTACT_NAME = 5;
    }


    /**
     * This method return a CommunicationProvider cursor with additional columns taken from the
     * Android device Contact Provider
     *
     * @param cursorType
     * @param db
     * @return
     */
    public static Cursor getCursor(Context context, int cursorType, SQLiteDatabase db) {
        String[] cursor_columns = CursorUtils.addCursorColumnsNames(ContactActionEventDAO.getCursorColumns
                (cursorType), CommunicationContract.ContactEntry.VIEW_CONTACT_NAME);
        MatrixCursor cursor = new MatrixCursor(cursor_columns);
        Uri uri;
        Cursor contactCursor;
        String contactName = "Contact name unknown";
        Cursor appCursor = ContactActionEventDAO.getCursor(cursorType, db);
        while (appCursor.moveToNext()) {
            // Creates a contact lookup Uri from contact ID and lookup_key
            uri = ContactsContract.Contacts.getLookupUri(
                    appCursor.getLong(AndroidDAO.ContactQuery.COL_ID),
                    appCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY));
            contactCursor = context.getContentResolver().query(uri, AndroidDAO.ContactQuery.PROJECTION, null, null, null);
            if (contactCursor.moveToFirst())
                contactName = contactCursor.getString(AndroidDAO.ContactQuery.COL_CONTACT_NAME);
            cursor.addRow(CursorUtils.addCursorColumnsValues(appCursor, contactName));
        }
        return cursor;
    }
}
