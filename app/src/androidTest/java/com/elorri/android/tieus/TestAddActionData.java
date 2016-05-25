package com.elorri.android.tieus;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.tieus.data.AddActionData;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.ActionDAO;
import com.elorri.android.tieus.db.MatrixCursors;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.db.VectorDAO;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestAddActionData extends AndroidTestCase {

    TestGivens mTestGivens;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();
        mTestGivens.test_fillContactTable();
        mTestGivens.test_fillActionTable();
        mTestGivens.test_fillVectorTable();
        mTestGivens.test_fillEventTable();
    }


//    @Override
//    protected void tearDown() throws Exception {
//        mTestGivens.deleteAllRecordsFromDB();
//        super.tearDown();
//    }


    public void testSelectActionUri() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.AddActionData.URI_PAGE_SELECT_ACTION,
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Give feedback|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ActionDAO.ActionQuery.PROJECTION[0] + "|"
                + ActionDAO.ActionQuery.PROJECTION[1] + "|"
                + ActionDAO.ActionQuery.PROJECTION[2] + "|"
                + ActionDAO.ActionQuery.PROJECTION[3] + "|"
                + ActionDAO.ActionQuery.PROJECTION[4] + "|\n"
                + "row |5|Give feedback|Thank you|10|" + ViewTypes.VIEW_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testSelectVectorUri() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.AddActionData.buildSelectVectorUri(String.valueOf(5)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + ActionDAO.ActionQuery.PROJECTION[0] + "|"
                + ActionDAO.ActionQuery.PROJECTION[1] + "|"
                + ActionDAO.ActionQuery.PROJECTION[2] + "|"
                + ActionDAO.ActionQuery.PROJECTION[3] + "|"
                + ActionDAO.ActionQuery.PROJECTION[4] + "|\n"
                + "row |5|Give feedback|Thank you|10|" + ViewTypes.VIEW_ACTION + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Select a communication vector|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + VectorDAO.VectorQuery.PROJECTION[0] + "|"
                + VectorDAO.VectorQuery.PROJECTION[1] + "|"
                + VectorDAO.VectorQuery.PROJECTION[2] + "|"
                + VectorDAO.VectorQuery.PROJECTION[3] + "|"
                + VectorDAO.VectorQuery.PROJECTION[4] + "|\n"
                + "row |32|Gmail|com.google.android.gm|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testValidateUri() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.AddActionData.buildValidateUri(
                        String.valueOf(5),
                        String.valueOf(32),
                        String.valueOf(1462226400000l)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[0] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[1] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[2] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[3] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[4] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[5] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[6] + "|\n"
                + "row |5|Thank you|32|com.google.android.gm|package|1462226400000|"
                + ViewTypes.VIEW_ACTION_RECAP_QUERY + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

}
