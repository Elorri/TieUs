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
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.capstone.tieus.db.ContactDAO;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.capstone.tieus.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 * Test if cursor returned by ContentProvider uri  DATA_DETAIL query is correct in different
 * scénario (with a last message seen different "setLastMessageIdxBg") .
 */
public class TestBoardData extends AndroidTestCase {


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


    @Override
    protected void tearDown() throws Exception {
        //mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();

        Status.setLastMessageIdxBg(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
    }


    private static String getAlwaysDisplayedCursor(Context context) {
        return "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.unscheduled_people)
                + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnscheduledPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._1day + "|" + TestGivens._2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" +
                TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._3days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|"
                + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.delayed) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|"
                + "" + R.string.action_name12 + "|" + TestGivens._4daysAgo_15may2016at12h40m52s
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAYED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.today) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|"
                + "" + R.string.action_name12 + "|" + TestGivens._in1second_19may2016at12h40m53s
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.done) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|"
                + "" + R.string.action_name12 + "|" + TestGivens._in2seconds_19may2016at12h40m54s
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|"
                + "" + R.string.action_name12 + "|" + TestGivens._in2seconds_19may2016at12h40m54s
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.next) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|"
                + "" + R.string.action_name12 + "|" + TestGivens.tomorrowStart_20may2016at00h00m00sStart
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.unfollowed) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnfollowedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-18611|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._1day + "|" + TestGivens._2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611"
                + "|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._3days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n";
    }


    private static String getAlwaysDisplayedCursorSatisfactionMelissaUpdated(Context context) {
        return "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.unscheduled_people)
                + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnscheduledPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._1day + "|" + TestGivens._2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._3days + "|" +
                TestGivens._30days + "|" + TestGivens.now_19may2016at12h40m52s + "|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|" + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.delayed) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|-4560696|"
                + "" + R.string.action_name12 + "|" + TestGivens._4daysAgo_15may2016at12h40m52s + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAYED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.today) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|-1739917|"
                + "" + R.string.action_name12 + "|" + TestGivens._in1second_19may2016at12h40m53s + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.done) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|-4560696|"
                + "" + R.string.action_name12 + "|" + TestGivens._in1second_19may2016at12h40m53s
                + "|Gmail|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.next) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|-18611|"
                + "" + R.string.action_name12 + "|" + TestGivens.tomorrowStart_20may2016at00h00m00sStart + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + context.getResources().getString(R.string.unfollowed) + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnfollowedPeopleQuery
                .PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE
                + "|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._1day + "|" + TestGivens._2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_unfollowed|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._3days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11549705|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-1739917|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-4560696|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_unfollowed|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|" + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + "|\n";

    }


    public void testManagedUnmanagedPeopleMessage() {
        Status.setLastMessageIdxBg(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.manage_unscheduled_contact_message, 8)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + getAlwaysDisplayedCursor(mContext);


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void testFillInResponseTimeLimitMessage() {
        Status.setLastMessageIdxBg(mContext, Status.FILL_IN_RESPONSE_TIME_LIMIT);

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_in_time_limit_response_message, 2)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.fill_in_response_time_limit_title)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInTimeLimitResponseQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp
                + "|null|null|null|null|" + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-4560696|"
                + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|null|null|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|"
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-18611|"
                + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void testUpdateSatisfactionMessage() {
        Status.setLastMessageIdxBg(mContext, Status.UPDATE_SATISFACTION);

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.update_satisfaction_face_person, "Jeanne")
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"+mContext.getResources().getString(R.string.satisfaction_to_update_title)+"|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedSatisfactionUpdateQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-30107|" + ViewTypes.VIEW_UPDATE_SATISFACTION + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testSetUpAFrequencyOfContact() {
        Status.setLastMessageIdxBg(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_up_frequency_person, "Mathieu")
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.fill_up_frequency_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO
                .PeopleThatNeedFrequencyQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-11677471|" + ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testAskForResponseOrMoveToUnfollowedMessage() {
        Status.setLastMessageIdxBg(mContext, Status.ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED);


        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.ask_for_response_person, "Denis")
                + "|"
                + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.ask_for_response_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.AskForResponseQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_sentiment_satisfied_black_48dp + "|" + TestGivens._1day + "|" + TestGivens._2days + "|null|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + "|-6190977|" + ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testApprochingDeadLineMessage() {
        Status.setLastMessageIdxBg(mContext, Status.APPROCHING_DEAD_LINE);

        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.nearby_decreased_satisfaction_person, "Émilie")
                + "|"
                + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.nearby_decreased_satisfaction_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString
                (ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|" + TestGivens._2days + "|" + TestGivens._4days + "|" + TestGivens._30days + "|null|"
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + "|" + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_OFF_VALUE + "|-10177034|" + ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testNothingToSayMessage() {
        Status.setLastMessageIdxBg(mContext, Status.NOTHING_TO_SAY);
        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0.363636|" + ViewTypes.VIEW_FORECAST + "|\n"
                + getAlwaysDisplayedCursor(mContext);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }




}
