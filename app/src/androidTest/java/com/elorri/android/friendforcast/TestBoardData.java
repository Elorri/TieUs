package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.elorri.android.friendforcast.data.FriendForecastContract;
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
        mTestResults=new TestResults();
        mTestGivens.deleteAllRecordsFromDB();
    }


    @Override
    protected void tearDown() throws Exception {
        mTestGivens.deleteAllRecordsFromDB();
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
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor2(mContext, cursor);
        cursor.close();

    }

    public void testFillInDelayFeedbackMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();

    }

    public void testUpdateMoodMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testSetUpAFreequencyOfContact() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testAskForFeedbackOrMoveToUntrackMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testApprochingDeadLineMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_DEAD_LINE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testNotePeopleWhoDecreasedMoodMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testTakeTimeForFeedbackMessage() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


//
//    public void test_getTopCursors() {
//        assertEquals(Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
//                BoardData.getTopCursors(mContext, Status.UPDATE_MOOD));
//    }


}
