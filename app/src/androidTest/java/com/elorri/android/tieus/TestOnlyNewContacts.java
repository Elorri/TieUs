package com.elorri.android.tieus;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.extra.Status;

/**
 * Created by Elorri on 23/05/2016.
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
        Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
        Status.setLastUserMoodsConfirmAware(mContext, 0l);

    }

//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        mTestGivens.deleteAllRecordsFromDB();
//    }

    public void test_fillContactTable_only_new_contacts() {

        mContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION)
//                + "row |15|832|298i5.3552i264b0e968b8a42ff|Paul|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |16|833|298i5.3552i264b0e968b8a42fk|Pierre|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |17|834|298i5.3552i264b0e968b8a42fl|Jacques|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |18|835|298i5.3552i264b0e968b8a42fv|Jeanne|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |19|836|298i5.3552i264b0e968b8a42fd|Mathieu|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |20|837|298i5.3552i264b0e968b8a46fv|Denis|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
//                + "row |21|838|298i5.3552i264b0e968b8a47fv|Émilie|null|"
//                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
//                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
//                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"

                + "row |22|839|298i5.3552i274b0e968b8a47fv|Mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|1739917|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|Françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|Emma|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|18611|\n"
                + "row |25|851|290i5.3552i264b0e968b8a42fk|Bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|6190977|\n"
                + "row |26|852|290i6.3552i264b0e968b8a42fk|TheBakery|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|4560696|\n"
                + "row |27|853|290i7.3552i264b0e968b8a42fk|TheCostume|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|1739917|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = mContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable
                        .CONTENT_URI,
                contactValues);

        assertEquals(6, insertCount);

        Cursor contactCursor = mContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                ContactDAO.ContactQuery.PROJECTION,
                null,
                null,
                null
        );

        assertEquals(6, contactCursor.getCount());

//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "expected : \n" + contactString);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(contactCursor));

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();

    }


}
