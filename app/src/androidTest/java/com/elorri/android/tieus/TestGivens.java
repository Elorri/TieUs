package com.elorri.android.tieus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.EventDAO;
import com.elorri.android.tieus.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 */

public class TestGivens extends AndroidTestCase {

    public static long now_19may2016at12h40m52s = 1463654452071l;
    public static long todayStart_19may2016at00h00m00s = 1463608800000l;
    public static long tomorrowStart_20may2016at00h00m00sStart = 1463695200000l;
    public static long _32daysAgo_17april2016at12h40m52s = 1460889652071l;
    public static long _31daysAgo_18april2016at12h40m52s = 1460976052071l;
    public static long _30daysAgo_19april2016at12h40m52s = 1461062452071l;
    public static long _26daysAgo_23april2016at12h40m52s = 1461408052071l;
    public static long _25daysAgo_24april2016at12h40m52s = 1461494452071l;
    public static long _4daysAgo_15may2016at12h40m52s = 1463308852071l;
    public static long _3daysAgo_16may2016at12h40m52s = 1463395252071l;
    public static long _2daysAgo_17may2016at12h40m52s = 1463481652071l;
    public static long _1dayAgo_18may2016at12h40m52s = 1463568052071l;
    public static long _in1second_19may2016at12h40m53s = 1463654453071l;
    public static long _in2seconds_19may2016at12h40m54s = 1463654454071l;
    public static long _in1day_20may2016at12h40m52s = 1463740852071l;
    public static long _in2days_21may2016at12h40m52s = 1463827252071l;
    public static long _in4days_23may2016at12h40m52s = 1464000052071l;

    public static final long _1day = 86400000l;
    public static final long _2days = 172800000l;
    public static final long _3days = 259200000l;
    public static final long _4days = 345600000l;
    public static final long _10days = 864000000l;
    public static final long _20days = 1728000000l;
    public static final long _23days = 1987200000l;
    public static final long _24days = 2073600000l;
    public static final long _30days = 2592000000l;
    public static final long _29days = 2505600000l;
    public static final long _50days = 4320000000l;

    Context aContext;

    public TestGivens(Context context) {
        aContext = context;
    }

    public TestGivens() {
    }

    @Override
    protected void setUp() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        aContext = mContext;
        deleteAllRecordsFromDB();
        super.setUp();
    }


//    @Override
//    protected void tearDown() throws Exception {
//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
//        deleteAllRecordsFromDB();
//        super.tearDown();
//    }


    public void deleteAllRecordsFromDB() {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        aContext.getContentResolver().delete(TieUsContract.ContactTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(TieUsContract.ActionTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(TieUsContract.VectorTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(TieUsContract.EventTable.CONTENT_URI, null, null);

    }


    public void test_I_have_a_preference_setDoneActionsAware_equals_to_false() {
        Status.setDoneActionsAware(aContext, false);
        assertEquals(false, Status.isDoneActionsAware(aContext));
    }

    public void test_I_have_a_preference_setDoneActionsAware_equals_to_true() {
        Status.setDoneActionsAware(aContext, true);
        assertEquals(true, Status.isDoneActionsAware(aContext));
    }
    public void test_I_have_a_preference_setDeleteActionsAware_equals_to_false() {
        Status.setDeleteActionsAware(aContext, false);
        assertEquals(false, Status.isDeleteActionsAware(aContext));
    }

    public void test_I_have_a_preference_setDeleteActionsAware_equals_to_true() {
        Status.setDeleteActionsAware(aContext, true);
        assertEquals(true, Status.isDeleteActionsAware(aContext));
    }

    public void test_fillActionTable() {
        aContext.getContentResolver().delete(TieUsContract.ActionTable.CONTENT_URI, null, null);

        String actionString = "\n"
                + "header |" +
                TieUsContract.ActionTable._ID + "|" +
                TieUsContract.ActionTable.COLUMN_TITLE_RESOURCE_ID + "|" +
                TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + "|" +
                TieUsContract.ActionTable.COLUMN_SORT_ORDER + "|\n"
                + "row |1|"+R.string.action_title1+"|"+R.string.action_name1+"|1|\n"
                + "row |2|"+R.string.action_title1+"|"+R.string.action_name2+"|2|\n"
                + "row |3|"+R.string.action_title2+"|"+R.string.action_name3+"|3|\n"
                + "row |4|"+R.string.action_title2+"|"+R.string.action_name4+"|4|\n"
                + "row |5|"+R.string.action_title2+"|"+R.string.action_name5+"|5|\n"
                + "row |6|"+R.string.action_title2+"|"+R.string.action_name6+"|6|\n"
                + "row |7|"+R.string.action_title3+"|"+R.string.action_name7+"|7|\n"
                + "row |8|"+R.string.action_title3+"|"+R.string.action_name8+"|8|\n"
                + "row |9|"+R.string.action_title3+"|"+R.string.action_name9+"|9|\n"
                + "row |10|"+R.string.action_title4+"|"+R.string.action_name10+"|10|\n"
                + "row |11|"+R.string.action_title4+"|"+R.string.action_name11+"|11|\n"
                + "row |12|"+R.string.action_title4+"|"+R.string.action_name12+"|12|\n";

        ContentValues[] actionValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(actionString));
        int insertCount = aContext.getContentResolver().bulkInsert(TieUsContract.ActionTable
                .CONTENT_URI, actionValues);
        assertEquals(12, insertCount);
        Cursor actionCursor = aContext.getContentResolver().query(TieUsContract.ActionTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + actionCursor);
        assertEquals(actionString, TestUtility.getCursorString(actionCursor));
    }

    public void test_fillVectorTable() {
        aContext.getContentResolver().delete(TieUsContract.VectorTable.CONTENT_URI, null, null);

        String vectorString = "\n"
                + "header |" +
                TieUsContract.VectorTable._ID + "|" +
                TieUsContract.VectorTable.COLUMN_NAME + "|" +
                TieUsContract.VectorTable.COLUMN_DATA + "|" +
                TieUsContract.VectorTable.COLUMN_MIMETYPE + "|\n"
                + "row |32|Gmail|com.google.android.gm|package|\n"
                + "row |33|Google plus|com.google.android.apps.plus|package|\n"
                + "row |34|Facebook|com.facebook.katana|package|\n"
                + "row |35|LinkedIn|com.linkedin.android|package|\n"
                + "row |36|Viadeo|com.viadeo.android|package|\n"
                + "row |37|Phone|com.android.phone|package|\n"
                + "row |38|Twitter|com.twitter.android|package|\n"
                + "row |39|Meeting|"+R.drawable.ic_meeting_24dp+"|ressourceId|\n";


        ContentValues[] vectorValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(vectorString));
        int insertCount = aContext.getContentResolver().bulkInsert(TieUsContract.VectorTable
                        .CONTENT_URI,
                vectorValues);
        assertEquals(8, insertCount);
        Cursor vectorCursor = aContext.getContentResolver().query(TieUsContract.VectorTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorCursor);
        assertEquals(vectorString, TestUtility.getCursorString(vectorCursor));
    }


    public void test_fillContactTable() {

        aContext.getContentResolver().delete(TieUsContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION)
                + "row |15|832|298i5.3552i264b0e968b8a42ff|Paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|Pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-4560696|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|Jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|Jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|Mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |20|837|298i5.3552i264b0e968b8a46fv|Denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|Émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-10177034|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|Mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|Françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|Emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |25|851|290i5.3552i264b0e968b8a42fk|Bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-18611|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|Paul_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|Pierre_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-4560696|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|Jacques_Unfolllowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|Jeanne_Unfolllowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|Mathieu_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|Denis_Unfolllowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|Émilie_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-10177034|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|Mélissa_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|Françoise_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|Emma_Unfolllowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |45|951|390i5.3552i264b0e968b8a42fk|Bernard_Unfolllowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|-18611|\n";

        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(TieUsContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(22, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                TieUsContract.ContactTable.CONTENT_URI,
                ContactDAO.ContactQuery.PROJECTION,
                null,
                null,
                null
        );

        assertEquals(22, contactCursor.getCount());

//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "expected : \n" + contactString);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(contactCursor));

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();

    }


    public void test_fillEventTable() {
        aContext.getContentResolver().delete(TieUsContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + TestUtility.getCursorHeaderString(EventDAO.EventQuery.PROJECTION)
                + "row |8|16|12|32|" + _4daysAgo_15may2016at12h40m52s + "|null|\n"
                + "row |9|17|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |10|18|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |11|19|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |12|20|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |13|21|12|32|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s + "|\n"
                + "row |14|22|12|32|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s + "|\n"
                + "row |15|23|12|32|" + _in1second_19may2016at12h40m53s + "|null|\n"
                + "row |16|24|12|32|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s + "|\n"
                + "row |17|25|12|32|" + tomorrowStart_20may2016at00h00m00sStart + "|null|\n"
                + "row |18|36|12|32|" + _4daysAgo_15may2016at12h40m52s + "|null|\n"
                + "row |19|37|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |20|38|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |21|39|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |22|40|12|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |23|41|12|32|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s + "|\n"
                + "row |24|42|12|32|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s + "|\n"
                + "row |25|43|12|32|" + _in1second_19may2016at12h40m53s + "|null|\n"
                + "row |26|44|12|32|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s + "|\n"
                + "row |27|45|12|32|" + _in4days_23may2016at12h40m52s + "|null|\n";

        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));
        int insertCount = aContext.getContentResolver().bulkInsert(
                TieUsContract.EventTable.CONTENT_URI,
                eventValues);
        assertEquals(20, insertCount);
        Cursor eventCursor = aContext.getContentResolver().query(TieUsContract.EventTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + eventString);
        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
    }
}
