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

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 23/05/2016.
 * Very simple class, that will fill the table contact with 13 contacts, with no actions
 * registered on them. Useful for testing with contact list that is not yours.
 */
public class TestOnlyNewContacts extends AndroidTestCase {

    TestGivens mTestGivens;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestGivens = new TestGivens(mContext);

        mTestGivens.deleteAllRecordsFromDB();
        test_fillContactTable_only_new_contacts();
        mTestGivens.test_fillVectorTable();
        mTestGivens.test_fillActionTable();

        Status.setDoneActionsAware(mContext, false);
        Status.setDeleteActionsAware(mContext, false);
        Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
        Status.setLastUserSatisfactionsConfirmAware(mContext, 0l);
        Status.setSyncStatus(mContext, Status.SYNC_DONE);

    }

//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        mTestGivens.deleteAllRecordsFromDB();
//    }

    public void test_fillContactTable_only_new_contacts() {

        mContext.getContentResolver().delete(TieUsContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION)
                + "row |15|832|298i5.3552i264b0e968b8a42ff|Paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|1739917|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|Pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|Jacques|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|1739917|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|Jeanne|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|18611|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|Mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|1739917|\n"
                + "row |20|837|298i5.3552i264b0e968b8a46fv|Denis|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|18611|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|Émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|Mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|1739917|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|Françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|Emma|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|18611|\n"
                + "row |25|851|290i5.3552i264b0e968b8a42fk|Bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|6190977|\n"
                + "row |26|852|290i6.3552i264b0e968b8a42fk|TheBakery|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |27|853|290i7.3552i264b0e968b8a42fk|TheCostume|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|1739917|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = mContext.getContentResolver().bulkInsert(TieUsContract.ContactTable
                        .CONTENT_URI,
                contactValues);

        assertEquals(13, insertCount);

        Cursor contactCursor = mContext.getContentResolver().query(
                TieUsContract.ContactTable.CONTENT_URI,
                ContactDAO.ContactQuery.PROJECTION,
                null,
                null,
                null
        );

        assertEquals(13, contactCursor.getCount());
        assertEquals(13, insertCount);
        
        Log.e(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(contactCursor));

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();

    }


}
