package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.BoardData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.Status;

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
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[5] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[6] + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul||2130837600|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));


        cursor.close();

    }


    public void test_getTopCursors() {
        assertEquals(Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
                BoardData.getTopCursors(mContext, Status.UPDATE_MOOD));
    }
}
