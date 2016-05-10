package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestBoardData extends AndroidTestCase {
    TestGivens mTestGivens;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();
    }


//    @Override
//    protected void tearDown() throws Exception {
//        mTestGivens.deleteAllRecordsFromDB();
//        super.tearDown();
//    }

    public void testNoContactRegisteredOnPhone() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD,
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.PROJECTION[0] + "|"
                + MatrixCursors.EmptyCursorMessageQuery.PROJECTION[1] + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.no_contacts_on_phone) + "|"
                + MatrixCursors.EmptyCursorMessageQuery.VALUES[1] + "|\n";


        assertEquals(cursorString, TestUtility.getCursorString(cursor));


        cursor.close();
    }


    public void testWeHaveUnmanagedPeople() {
        mTestGivens.test_I_have_1_unmanaged_people();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD,
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul||2130837600|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));


        cursor.close();

    }
}
