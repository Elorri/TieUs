package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 20/05/2016.
 */
public class TestBoardData_NoContactsOnPhone extends AndroidTestCase {

    TestGivens mTestGivens;

    public static final long now_19may2016at12h40m52s = TestGivens.now_19may2016at12h40m52s;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();
    }


    @Override
    protected void tearDown() throws Exception {
        //mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();

        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
    }

    public void testNoContactRegisteredOnPhone() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.buildBoardUri(now_19may2016at12h40m52s), null, null, null, null);

        String cursorString = "\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE +
                "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.no_contacts_on_phone)
                + "|"
                + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }
}
