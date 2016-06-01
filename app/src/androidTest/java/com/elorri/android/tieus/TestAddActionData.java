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
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |Introduce|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |1|Introduce|Get to know who they are and what they are looking for|1|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |2|Introduce|Tell who you are and what you can do|2|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |Inform|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |3|Inform|Inform of an event|3|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |4|Inform|Give expertise|4|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |5|Inform|Inform of an existing tool|5|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |6|Inform|Inform of a connection you have that may help|6|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |Make an offer|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |7|Make an offer|Make an offer of work|7|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |8|Make an offer|Make an offer of money|8|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |9|Make an offer|Offer a present|9|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |Give feedback|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |10|Give feedback|Upvote their competence|10|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |11|Give feedback|Encourage|11|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |12|Give feedback|Thanks|12|" + ViewTypes.VIEW_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testSelectVectorUri() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.AddActionData.buildSelectVectorUri(String.valueOf(12)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |12|Give feedback|Thanks|12|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |Select a vector|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(VectorDAO.VectorQuery.PROJECTION)
                + "row |34|Facebook|com.facebook.katana|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |32|Gmail|com.google.android.gm|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |33|Google plus|com.google.android.apps.plus|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |35|LinkedIn|com.linkedin.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |39|Meeting|2130837594|ressourceId|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |37|Phone|com.android.phone|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |38|Twitter|com.twitter.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |36|Viadeo|com.viadeo.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n";


        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testValidateUri() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.AddActionData.buildValidateUri(
                        String.valueOf(12),
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
                + "row |12|Thanks|32|com.google.android.gm|package|1462226400000|"
                + ViewTypes.VIEW_ACTION_RECAP_QUERY + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

}
