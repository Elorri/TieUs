/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.capstone.tieus;


import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.capstone.tieus.data.AddActionData;
import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ActionDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.VectorDAO;
import com.elorri.android.capstone.tieus.db.ViewTypes;

/**
 * Created by Elorri on 07/05/2016.
 * Test if cursor returned by ContentProvider uris  DATA_ADD_ACTION_SELECT_ACTION and
 * DATA_ADD_ACTION_SELECT_VECTOR queries are correct.
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
                TieUsContract.AddActionData.URI_PAGE_SELECT_ACTION,
                null,
                null,
                null,
                null
        );

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |"+mContext.getResources().getString(R.string.action_title1)+"|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |1|"+R.string.action_title1+"|"+R.string.action_name1+"|1|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |2|"+R.string.action_title1+"|"+R.string.action_name2+"|2|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |"+mContext.getResources().getString(R.string.action_title2)+"|" +
                ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |3|"+R.string.action_title2+"|"+R.string.action_name3+"|3|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |4|"+R.string.action_title2+"|"+R.string.action_name4+"|4|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |5|"+R.string.action_title2+"|"+R.string.action_name5+"|5|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |6|"+R.string.action_title2+"|"+R.string.action_name6+"|6|" + ViewTypes
                .VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |"+mContext.getResources().getString(R.string.action_title3)+"|" +
                ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |7|"+R.string.action_title3+"|"+R.string.action_name7+"|7|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |8|"+R.string.action_title3+"|"+R.string.action_name8+"|8|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |9|"+R.string.action_title3+"|"+R.string.action_name9+"|9|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |"+mContext.getResources().getString(R.string.action_title4)+"|" +
                ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |10|"+R.string.action_title4+"|"+R.string.action_name10+"|10|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |11|"+R.string.action_title4+"|"+R.string.action_name11+"|11|" + ViewTypes.VIEW_ACTION + "|\n"
                + "row |12|"+R.string.action_title4+"|"+R.string.action_name12+"|12|" + ViewTypes.VIEW_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testSelectVectorUri() {
        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.AddActionData.buildSelectVectorUri(String.valueOf(12)),
                null,
                null,
                null,
                null
        );

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ActionDAO.ActionQuery.PROJECTION)
                + "row |12|"+R.string.action_title4+"|"+R.string.action_name12+"|12|" + ViewTypes.VIEW_ACTION + "|\n"
                + TestUtility.getCursorHeaderString(MatrixCursors.TitleQuery.PROJECTION)
                + "row |"+mContext.getResources().getString(R.string.select_vector)+"|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(VectorDAO.VectorQuery.PROJECTION)
                + "row |34|Facebook|com.facebook.katana|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |32|Gmail|com.google.android.gm|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |33|Google plus|com.google.android.apps.plus|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |35|LinkedIn|com.linkedin.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |39|Meeting|2130837636|ressourceId|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |37|Phone|com.android.phone|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |38|Twitter|com.twitter.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n"
                + "row |36|Viadeo|com.viadeo.android|package|" + ViewTypes.VIEW_VECTOR_ITEM + "|\n";


        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testValidateUri() {
        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.AddActionData.buildValidateUri(
                        String.valueOf(12),
                        String.valueOf(32),
                        String.valueOf(1462226400000l)),
                null,
                null,
                null,
                null
        );

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[0] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[1] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[2] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[3] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[4] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[5] + "|"
                + AddActionData.RecapQuery.SELECT_ACTION_RECAP_VALIDATE_PROJECTION[6] + "|\n"
                + "row |12|"+R.string.action_name12+"|32|com.google.android.gm|package|1462226400000|"
                + ViewTypes.VIEW_ACTION_RECAP_QUERY + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

}
