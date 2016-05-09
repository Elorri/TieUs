package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 09/05/2016.
 */
public class TestProvider extends AndroidTestCase {

    TestGivens mTestGivens;

    @Override
    protected void setUp() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();
        super.setUp();
    }


//    @Override
//    protected void tearDown() throws Exception {
//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
//        deleteAllRecordsFromDB();
//        super.tearDown();
//    }

    public void testContactBulkInsert() {

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|acticall||2130837600|\n";

        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = mContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1,insertCount);

        Cursor contactCursor = mContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(contactCursor.getCount(), 1);

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();
    }
}
