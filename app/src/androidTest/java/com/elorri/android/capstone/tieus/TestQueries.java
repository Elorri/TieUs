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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.data.TieUsDbHelper;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.extra.DateUtils;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.capstone.tieus.extra.Tools;

import java.util.Locale;

/**
 * Created by Elorri on 13/05/2016.
 * This class test all the queries used in this app.
 */
public class TestQueries extends AndroidTestCase {


    TestGivens mTestGivens;
    SQLiteDatabase db;


    public static final long now_19may2016at12h40m52s = TestGivens.now_19may2016at12h40m52s;
    public static final long todayStart_19may2016at00h00m00s = TestGivens.todayStart_19may2016at00h00m00s;
    public static final long tomorrowStart_20may2016at00h00m00sStart = TestGivens.tomorrowStart_20may2016at00h00m00sStart;
    public static final long _32daysAgo_17april2016at12h40m52s = TestGivens._32daysAgo_17april2016at12h40m52s;
    public static final long _31daysAgo_18april2016at12h40m52s = TestGivens._31daysAgo_18april2016at12h40m52s;
    public static final long _30daysAgo_19april2016at12h40m52s = TestGivens._30daysAgo_19april2016at12h40m52s;
    public static final long _26daysAgo_23april2016at12h40m52s = TestGivens._26daysAgo_23april2016at12h40m52s;
    public static final long _25daysAgo_24april2016at12h40m52s = TestGivens._25daysAgo_24april2016at12h40m52s;
    public static final long _4daysAgo_15may2016at12h40m52s = TestGivens._4daysAgo_15may2016at12h40m52s;
    public static final long _3daysAgo_16may2016at12h40m52s = TestGivens._3daysAgo_16may2016at12h40m52s;
    public static final long _2daysAgo_17may2016at12h40m52s = TestGivens._2daysAgo_17may2016at12h40m52s;
    public static final long _1dayAgo_18may2016at12h40m52s = TestGivens._1dayAgo_18may2016at12h40m52s;
    public static final long _in1second_19may2016at12h40m53s = TestGivens._in1second_19may2016at12h40m53s;
    public static final long _in2seconds_19may2016at12h40m54s = TestGivens._in2seconds_19may2016at12h40m54s;
    public static final long _in1day_20may2016at12h40m52s = TestGivens._in1day_20may2016at12h40m52s;
    public static final long _in2days_21may2016at12h40m52s = TestGivens._in2days_21may2016at12h40m52s;
    public static final long _in4days_23may2016at12h40m52s = TestGivens._in4days_23may2016at12h40m52s;

    public static final long _1day = TestGivens._1day;
    public static final long _2days = TestGivens._2days;
    public static final long _3days = TestGivens._3days;
    public static final long _4days = TestGivens._4days;
    public static final long _10days = TestGivens._10days;
    public static final long _20days = TestGivens._20days;
    public static final long _23days = TestGivens._23days;
    public static final long _24days = TestGivens._24days;
    public static final long _30days = TestGivens._30days;
    public static final long _29days = TestGivens._29days;
    public static final long _50days = TestGivens._50days;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();

        mTestGivens.test_fillContactTable();
        mTestGivens.test_fillActionTable();
        mTestGivens.test_fillVectorTable();
        mTestGivens.test_fillEventTable();

        db = new TieUsDbHelper(mContext).getReadableDatabase();
        Status.setLastUserSatisfactionsConfirmAware(mContext, 0l);
    }

    public void test_joint_table_contact_action_vector_event() {
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT);
        Cursor cursor = db.query("(" + ContactActionVectorEventDAO
                        .JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ")",
                null, null, null, null, null, null);

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.JOINT_TABLE_PROJECTION)
                + "row |14|12|22|32|"+ R.string.action_name12+"|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s
                + "|839|298i5.3552i274b0e968b8a47fv|" + "Mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |24|12|42|32|"+R.string.action_name12+"|" + _32daysAgo_17april2016at12h40m52s + "|" + _31daysAgo_18april2016at12h40m52s
                + "|939|398i5.3552i274b0e968b8a47fv|Mélissa_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |13|12|21|32|"+R.string.action_name12+"|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s
                + "|838|298i5.3552i264b0e968b8a47fv|Émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n"
                + "row |23|12|41|32|"+R.string.action_name12+"|" + _26daysAgo_23april2016at12h40m52s + "|" + _25daysAgo_24april2016at12h40m52s
                + "|938|398i5.3552i264b0e968b8a47fv|Émilie_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n"
                + "row |12|12|20|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|837|298i5.3552i264b0e968b8a46fv|Denis|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |22|12|40|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|937|398i5.3552i264b0e968b8a46fv|Denis_Unfollowed|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |9|12|17|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|834|298i5.3552i264b0e968b8a42fl|Jacques|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |19|12|37|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|934|398i5.3552i264b0e968b8a42fl|Jacques_Unfollowed|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |10|12|18|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|835|298i5.3552i264b0e968b8a42fv|Jeanne|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |20|12|38|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|935|398i5.3552i264b0e968b8a42fv|Jeanne_Unfollowed|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |11|12|19|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|836|298i5.3552i264b0e968b8a42fd|Mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |21|12|39|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|" + _3daysAgo_16may2016at12h40m52s
                + "|936|398i5.3552i264b0e968b8a42fd|Mathieu_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |8|12|16|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|null" +
                "|833|298i5.3552i264b0e968b8a42fk|"
                + "Pierre|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|\n"
                + "row |18|12|36|32|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|null" +
                "|933|398i5.3552i264b0e968b8a42fk|"
                + "Pierre_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|\n"
                + "row |16|12|24|32|"+R.string.action_name12+"|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s
                + "|850|288i7.3552i264b0e968b8a42ff|"
                + "Emma|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |26|12|44|32|"+R.string.action_name12+"|" + _in1second_19may2016at12h40m53s + "|" + _in2seconds_19may2016at12h40m54s
                + "|950|398i9.3552i264b0e968b8a42ff|"
                + "Emma_Unfollowed|null|" + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |15|12|23|32|"+R.string.action_name12+"|" + _in1second_19may2016at12h40m53s + "|null|840|298i7.3552i264b0e968b8a42ff|"
                + "Françoise|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |25|12|43|32|"+R.string.action_name12+"|" + _in1second_19may2016at12h40m53s + "|null|940|398i7.3552i264b0e968b8a42ff|"
                + "Françoise_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |17|12|25|32|"+R.string.action_name12+"|" + tomorrowStart_20may2016at00h00m00sStart + "|null|851|290i5.3552i264b0e968b8a42fk|"
                + "Bernard|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|\n"
                + "row |27|12|45|32|"+R.string.action_name12+"|" + _in4days_23may2016at12h40m52s + "|null|951|390i5.3552i264b0e968b8a42fk|"
                + "Bernard_Unfollowed|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|\n";


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_scheduled_people() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.SchedulePeopleQuery.SELECT_MANAGED_PEOPLE, null);
        assertEquals(3, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.SchedulePeopleQuery.PROJECTION)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_unscheduled_people() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.UnscheduledPeopleQuery.SELECT, null);
        assertEquals(8, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnscheduledPeopleQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_needs_to_fill_in_time_limit_response() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.SELECT, null);
        assertEquals(2, cursor.getCount());

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.SELECT);

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_that_needs_satisfaction_update() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.SELECT_BEFORE_BIND
                        + now_19may2016at12h40m52s
                        + ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.SELECT_AFTER_BIND, null);
        assertEquals(1, cursor.getCount());

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" +
                ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.SELECT_BEFORE_BIND
                + now_19may2016at12h40m52s
                + ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.SELECT_AFTER_BIND);

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.PROJECTION)
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_set_up_a_frequency_of_contact() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT,
                null);
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.PROJECTION)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_be_asked_for_response_or_moved_to_unfollowed() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.AskForResponseQuery.SELECT_BEFORE_BIND
                        + now_19may2016at12h40m52s
                        + ContactActionVectorEventDAO.AskForResponseQuery.SELECT_AFTER_BIND,
                null);
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.AskForResponseQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n";


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_approching_end_of_most_suitable_contact_delay() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_BEFORE_BIND
                        + now_19may2016at12h40m52s + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                        .SELECT_AFTER_BIND, null);

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "now_19may2016at12h40m52s " + now_19may2016at12h40m52s + "- "
                + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_BEFORE_BIND
                + now_19may2016at12h40m52s + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_AFTER_BIND);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.PROJECTION)
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_who_decreased_satisfaction_today() {

        int updatedRows = db.update(TieUsContract.ContactTable.NAME,
                Tools.getContentValues(TieUsContract.ContactTable
                        .COLUMN_LAST_SATISFACTION_DECREASED, String.valueOf(now_19may2016at12h40m52s)),
                ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.UPDATE_BEFORE_BIND
                        + now_19may2016at12h40m52s
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.UPDATE_AFTER_BIND,
                null);

        //it is 2 because mélissa_unfollowed is also updated
        assertEquals(2, updatedRows);

        Cursor cursor = db.query("("
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.SELECT + ")",
                null,
                null,
                new String[]{String.valueOf(Status.getLastUserSatisfactionsConfirmAware(mContext))},
                null, null, null);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleWhoDecreasedSatisfactionQuery.PROJECTION)
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|"
                + now_19may2016at12h40m52s + "|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_should_already_be_contacted() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.DelayedPeopleQuery.SELECT,
                new String[]{String.valueOf(now_19may2016at12h40m52s)});

        Log.d(TestGivens.LOG_TAG, ContactActionVectorEventDAO.DelayedPeopleQuery.SELECT + " - "
                + String.valueOf(now_19may2016at12h40m52s));

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayedPeopleQuery.PROJECTION)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696"
                + "|"+R.string.action_name12+"|" + _4daysAgo_15may2016at12h40m52s + "|Gmail|com" +
                ".google.android.gm|package" + "|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_to_contact_today() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayPeopleQuery.SELECT,
                new String[]{String.valueOf(todayStart_19may2016at00h00m00s), String.valueOf(tomorrowStart_20may2016at00h00m00sStart)});

        Log.d(TestGivens.LOG_TAG, ContactActionVectorEventDAO.TodayPeopleQuery.SELECT + " - "
                + now_19may2016at12h40m52s + " - " + tomorrowStart_20may2016at00h00m00sStart);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917"
                + "|"+R.string.action_name12+"|" + _in1second_19may2016at12h40m53s + "|Gmail|com.google.android" +
                ".gm|package|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_contacted_today() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT,
                new String[]{String.valueOf(todayStart_19may2016at00h00m00s), String.valueOf(tomorrowStart_20may2016at00h00m00sStart)});

        Log.d(TestGivens.LOG_TAG, ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT + " - "
                + now_19may2016at12h40m52s + "" + tomorrowStart_20may2016at00h00m00sStart);

        assertEquals(2, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696"
                + "|"+R.string.action_name12+"|" + _in2seconds_19may2016at12h40m54s + "|Gmail|com" +
                ".google.android.gm|package|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696"
                + "|"+R.string.action_name12+"|" + _in2seconds_19may2016at12h40m54s + "|Gmail|com.google.android.gm|package|\n";


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_next_people_to_contact() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.NextPeopleQuery.SELECT,
                new String[]{String.valueOf(tomorrowStart_20may2016at00h00m00sStart)});

        Log.d(TestGivens.LOG_TAG, ContactActionVectorEventDAO.NextPeopleQuery.SELECT + " - "
                + tomorrowStart_20may2016at00h00m00sStart);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611"
                + "|"+R.string.action_name12+"|" + tomorrowStart_20may2016at00h00m00sStart +
                "|Gmail|com.google.android.gm|package|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_unfollowed_people() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.UnfollowedPeopleQuery.SELECT, null);

        assertEquals(11, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnfollowedPeopleQuery.PROJECTION)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n";

        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void test_people_eligible_for_updating_time_limit_response_only() {
        Cursor cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery.SELECT + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery.PROJECTION,
                null, null, null, null, null);
        assertEquals(4, cursor.getCount());


        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _1day + "|" + _2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|\n";


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_eligible_for_updating_frequency() {
        Cursor cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery.SELECT + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery.PROJECTION,
                null, null, null, null, null);
        assertEquals(3, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInTimeLimitAloneUpdateQuery.PROJECTION)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _4days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + _2days + "|" + _3days + "|" + _30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|\n";


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public static String getFriendlyDateTimeString(Context context, long date) {
        return DateUtils.fromLongToString(date, DateUtils.TIMESTAMP_FORMAT, Locale.getDefault());
    }

    public void test_print_dates() {
        long now_19may2016at12h40m52s = System.currentTimeMillis();
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "Now - "
                + getFriendlyDateTimeString(mContext, now_19may2016at12h40m52s) + " : " + now_19may2016at12h40m52s);
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "Today start - "
                + getFriendlyDateTimeString(mContext, DateUtils.setZeroDay(now_19may2016at12h40m52s))
                + " : " + DateUtils.setZeroDay(now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "Tomorrow start - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(1, DateUtils.setZeroDay(now_19may2016at12h40m52s)))
                + " : " + DateUtils.addDay(1, DateUtils.setZeroDay(now_19may2016at12h40m52s)));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 32 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-32, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-32, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 31 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-31, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-31, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 30 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-30, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-30, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 26 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-26, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-26, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 25 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-25, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-25, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 4 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-4, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-4, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 3 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-3, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-3, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[2] + "There is 2 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-2, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-2, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "There is 1 day - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-1, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(-1, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "In one second - "
                + getFriendlyDateTimeString(mContext, now_19may2016at12h40m52s + 1000) + " : " + (now_19may2016at12h40m52s + 1000));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "In 2 seconds - "
                + getFriendlyDateTimeString(mContext, now_19may2016at12h40m52s + 2000) + " : " + (now_19may2016at12h40m52s + 2000));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "In 1 day - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(1, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(1, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "In 2 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(2, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(2, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "In 4 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(4, now_19may2016at12h40m52s)) + " : "
                + DateUtils.addDay(4, now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_1days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(1, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(1, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_2days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(2, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(2, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_3days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(3, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(3, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_4days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(4, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(4, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_10days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(10, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(10, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_20days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(20, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(20, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_23days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(23, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(23, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_24days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(24, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(24, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_30days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(30, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(30, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_29days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(29, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(29, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));
        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "_50days - "
                + getFriendlyDateTimeString(mContext,
                DateUtils.addDay(50, now_19may2016at12h40m52s) - now_19may2016at12h40m52s) + " : "
                + (DateUtils.addDay(50, now_19may2016at12h40m52s) - now_19may2016at12h40m52s));


        Log.d(TestGivens.LOG_TAG, Thread.currentThread().getStackTrace()[1] + "DateUtils.nextYearStart() - "
                + DateUtils.fromLongToString(DateUtils.nextYearStart(), DateUtils
                .TIMESTAMP_FORMAT, Locale.getDefault()));
    }

}
