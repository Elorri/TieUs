package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 * */

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
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.ActionTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.VectorTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

    }


    public void test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false() {
        Status.setMarkActionFeatureStatus(aContext, false);
        assertEquals(false, Status.getMarkActionFeatureStatus(aContext));
    }

    public void test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true() {
        Status.setMarkActionFeatureStatus(aContext, true);
        assertEquals(true, Status.getMarkActionFeatureStatus(aContext));
    }


    public void test_fillActionTable() {
        aContext.getContentResolver().delete(FriendForecastContract.ActionTable.CONTENT_URI, null, null);

        String actionString = "\n"
                + "header |" +
                FriendForecastContract.ActionTable._ID + "|" +
                FriendForecastContract.ActionTable.COLUMN_TITLE + "|" +
                FriendForecastContract.ActionTable.COLUMN_NAME + "|" +
                FriendForecastContract.ActionTable.COLUMN_SORT_ORDER + "|\n"
                + "row |5|Give feedback|Thank you|10|\n";

        ContentValues[] actionValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(actionString));
        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ActionTable
                .CONTENT_URI, actionValues);
        assertEquals(1, insertCount);
        Cursor actionCursor = aContext.getContentResolver().query(FriendForecastContract.ActionTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + actionCursor);
        assertEquals(actionString, TestUtility.getCursorString(actionCursor));
    }

    public void test_fillVectorTable() {
        aContext.getContentResolver().delete(FriendForecastContract.VectorTable.CONTENT_URI, null, null);

        String vectorString = "\n"
                + "header |" +
                FriendForecastContract.VectorTable._ID + "|" +
                FriendForecastContract.VectorTable.COLUMN_NAME + "|" +
                FriendForecastContract.VectorTable.COLUMN_DATA + "|" +
                FriendForecastContract.VectorTable.COLUMN_MIMETYPE + "|\n"
                + "row |32|Gmail|com.google.android.gm|package|\n";
        ContentValues[] vectorValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(vectorString));
        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.VectorTable
                        .CONTENT_URI,
                vectorValues);
        assertEquals(1, insertCount);
        Cursor vectorCursor = aContext.getContentResolver().query(FriendForecastContract.VectorTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + vectorCursor);
        assertEquals(vectorString, TestUtility.getCursorString(vectorCursor));
    }


    public void test_fillContactTable() {

        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION)
                + "row |15|832|298i5.3552i264b0e968b8a42ff|Paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|Pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|Jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|Jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|Mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |20|837|298i5.3552i264b0e968b8a46fv|Denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|Émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|Mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|Françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|Emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |25|851|290i5.3552i264b0e968b8a42fk|Bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|Paul_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|Pierre_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|Jacques_Untracked|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|Jeanne_Untracked|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|Mathieu_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|Denis_Untracked|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|Émilie_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|Mélissa_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|Françoise_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|Emma_Untracked|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_ON_VALUE + "|\n"
                + "row |45|951|390i5.3552i264b0e968b8a42fk|Bernard_Untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|"
                + FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE + "|\n";

        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(22, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
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
        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + TestUtility.getCursorHeaderString(EventDAO.EventQuery.PROJECTION)
                + "row |8|16|5|32|" + _4daysAgo_15may2016at12h40m52s + "|null|\n"
                + "row |9|17|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |10|18|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |11|19|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |12|20|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |13|21|5|32|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s + "|\n"
                + "row |14|22|5|32|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s + "|\n"
                + "row |15|23|5|32|" + _in1second_19may2016at12h40m53s + "|null|\n"
                + "row |16|24|5|32|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s + "|\n"
                + "row |17|25|5|32|" + _in4days_23may2016at12h40m52s + "|null|\n"
                + "row |18|36|5|32|" + _4daysAgo_15may2016at12h40m52s + "|null|\n"
                + "row |19|37|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |20|38|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |21|39|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |22|40|5|32|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s + "|\n"
                + "row |23|41|5|32|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s + "|\n"
                + "row |24|42|5|32|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s + "|\n"
                + "row |25|43|5|32|" + _in1second_19may2016at12h40m53s + "|null|\n"
                + "row |26|44|5|32|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s + "|\n"
                + "row |27|45|5|32|" + _in4days_23may2016at12h40m52s + "|null|\n";

        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));
        int insertCount = aContext.getContentResolver().bulkInsert(
                FriendForecastContract.EventTable.CONTENT_URI,
                eventValues);
        assertEquals(20, insertCount);
        Cursor eventCursor = aContext.getContentResolver().query(FriendForecastContract.EventTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + eventString);
        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
    }
}
