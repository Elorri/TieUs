package com.elorri.android.friendforcast;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestGivens extends AndroidTestCase {


    @Override
    protected void setUp() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        deleteAllRecordsFromDB();
        super.setUp();
    }


    @Override
    protected void tearDown() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        deleteAllRecordsFromDB();
        super.tearDown();
    }


    public void deleteAllRecordsFromDB() {
        mContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);
    }


    /**
     * Given I have a table contact that contain 1 row
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     */
    public  void test_I_have_a_table_contact_that_contains_1_row(Context aContext) {
        Context context = mContext == null ? aContext : mContext;
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");

        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, 15);
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID, 832);
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY, "298i5.3552i264b0e968b8a42ff");
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME, "acticall");
        values.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL, (String) null);
        values.put(FriendForecastContract.ContactTable.COLUMN_EMOICON_ID, 2130837600);

        Uri contactUri = context.getContentResolver().insert(
                FriendForecastContract.ContactTable.CONTENT_URI, values);
        long contactId = ContentUris.parseId(contactUri);
        assertTrue(contactId != -1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = context.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestUtilities.validateCursor(cursor, values);

    }


}
