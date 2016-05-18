package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestBoardData extends AndroidTestCase {
    TestGivens mTestGivens;
    TestResults mTestResults;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestResults = new TestResults();
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

        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
    }

    public void testNoContactRegisteredOnPhone() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor1(mContext, cursor);
        cursor.close();
    }


    public void testManagedUnmanagedPeopleMessage() {
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.manage_unmanaged_people_message, 8)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void testFillInDelayFeedbackMessage() {
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_in_delay_feedback_message, 2)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |People recently act for|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO
                .PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void testUpdateMoodMessage() {
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);

        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.update_mood_message, 1)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Moods to update|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO
                .PeopleThatNeedMoodUpdateQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UPDATE_MOOD + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testSetUpAFrequencyOfContact() {
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_up_frequency_message, 1)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.fill_up_frequency_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO
                .PeopleThatNeedFrequencyQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testAskForFeedbackOrMoveToUntrackMessage() {
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.ask_for_feedback_message, 1)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.ask_for_feedback_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO
                .AskForFeedbackQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testApprochingDeadLineMessage() {
        Status.setLastMessageIdx(mContext, Status.APPROCHING_DEAD_LINE);

        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.nearby_decreased_mood_message, 1)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.nearby_decreased_mood_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString
                (ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testNotePeopleWhoDecreasedMoodMessage() {
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY);
        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.decreased_mood_message, 1)
                + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getString(R.string.decreased_mood_title) + "|"
                + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString
                (ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testTakeTimeForFeedbackMessage() {
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        long todayStart = DateUtils.todayStart();
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.take_time_for_feedback_message, 1)
                + "|"
                + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNMANAGED_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Delay|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_DELAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done today|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_TODAY_DONE_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(4, todayStart) + "|com.google.android.gm|package"
                + "|" + ViewTypes.VIEW_NEXT_PEOPLE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Untracked|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|" + ViewTypes.VIEW_UNTRACKED_PEOPLE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }


//
//    public void test_getTopCursors() {
//        assertEquals(Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
//                BoardData.getTopCursors(mContext, Status.UPDATE_MOOD));
//    }


}
