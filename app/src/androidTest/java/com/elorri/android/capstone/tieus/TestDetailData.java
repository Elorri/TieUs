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

import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 04/05/2016.
 * Test if cursor returned by ContentProvider uri  DATA_MAIN query is correct in different
 * scénarios (user knows how to delete an action, or user doesn't know "mTestGivens
 * .test_I_have_a_preference_...") .
 */
public class TestDetailData extends AndroidTestCase {
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

    public void testDetailNoActions() {
        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.DetailData.buildDetailUri(Long.valueOf(15)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE
                + "|\n"
                + "row |"+mContext.getResources().getString(R.string.select_action)+"|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_preference_setDoneActionsAware_equals_to_false();
        mTestGivens.test_I_have_a_preference_setDeleteActionsAware_equals_to_true();       

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.ConfirmMessageQuery.COLUMN_CONFIRM_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.how_to_done_action)+"|" + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |8|com.google.android.gm|package|Gmail|"+R.string.action_name12+"|" + TestGivens._4daysAgo_15may2016at12h40m52s + "|null|" + ViewTypes
                .VIEW_NEXT_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionEducatedUser() {
        mTestGivens.test_I_have_a_preference_setDoneActionsAware_equals_to_true();
        mTestGivens.test_I_have_a_preference_setDeleteActionsAware_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |8|com.google.android.gm|package|Gmail|"+R.string.action_name12+"|" + TestGivens._4daysAgo_15may2016at12h40m52s + "|null|" + ViewTypes
                .VIEW_NEXT_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));

        cursor.close();
    }


    public void testDetailDoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_preference_setDoneActionsAware_equals_to_false();
        mTestGivens.test_I_have_a_preference_setDeleteActionsAware_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE
                + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery
                        .PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE
                + "|-18611|" + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + "|\n"
                + "header |"
                + MatrixCursors.ConfirmMessageQuery.COLUMN_CONFIRM_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.how_to_done_action)+"|" + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.next_actions)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.select_action)+"|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.done_actions)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |9|com.google.android.gm|package|Gmail|"+R.string.action_name12+"|" +
                TestGivens._4daysAgo_15may2016at12h40m52s + "|"
                + TestGivens._3daysAgo_16may2016at12h40m52s + "|" + ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testDetailDoneActionEducatedUser() {
        mTestGivens.test_I_have_a_preference_setDoneActionsAware_equals_to_true();
        mTestGivens.test_I_have_a_preference_setDeleteActionsAware_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE
                + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery
                        .PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE
                + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|"
                + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.next_actions)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.select_action)+"|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.done_actions)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |9|com.google.android.gm|package|Gmail|"+R.string.action_name12+"|" + TestGivens
                ._4daysAgo_15may2016at12h40m52s + "|"
                + TestGivens._3daysAgo_16may2016at12h40m52s + "|" + ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


}
