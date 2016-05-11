package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.elorri.android.friendforcast.data.BoardData;
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


//    @Override
//    protected void tearDown() throws Exception {
//        mTestGivens.deleteAllRecordsFromDB();
//        super.tearDown();
//    }

    public void testNoContactRegisteredOnPhone() {
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor1(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveUnmanagedPeople_LastMessageIdx0() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor2(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveUnmanagedPeople_LastMessageIdx1() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveUnmanagedPeople_LastMessageIdx2() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveUnmanagedPeople_LastMessageIdx3() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveUnmanagedPeople_LastMessageIdx4() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveUnmanagedPeople_LastMessageIdx5() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveUnmanagedPeople_LastMessageIdx6() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveUnmanagedPeople_LastMessageIdx7() {
        mTestGivens.test_I_have_1_unmanaged_person_id_15();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }





    public void testWeHaveTakenActionForOnePeople_LastMessageIdx0() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor4(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveTakenActionForOnePeople_LastMessageIdx1() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor4(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveTakenActionForOnePeople_LastMessageIdx2() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveTakenActionForOnePeople_LastMessageIdx3() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveTakenActionForOnePeople_LastMessageIdx4() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveTakenActionForOnePeople_LastMessageIdx5() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveTakenActionForOnePeople_LastMessageIdx6() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveTakenActionForOnePeople_LastMessageIdx7() {
        mTestGivens.test_I_have_1_taken_action_people_with_id_16();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }




    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx0() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor5(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx1() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor5(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx2() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor5(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx3() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx4() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx5() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx6() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonThatNeedMoodUpdate_LastMessageIdx7() {
        mTestGivens.test_I_have_1_person_that_need_mood_update_with_id_17();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }




    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx0() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor6(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx1() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor6(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx2() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor6(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx3() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor6(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx4() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx5() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx6() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonThatNeedFrequency_LastMessageIdx7() {
        mTestGivens.test_I_have_1_person_that_need_frequency_update_with_id_18();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }






    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx0() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor7(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx1() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor7(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx2() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor7(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx3() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor7(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx4() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor7(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx5() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx6() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonWeHaveToAskedForFeedback_LastMessageIdx7() {
        mTestGivens.test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }



    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx0() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor2(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx1() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx2() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx3() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx4() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx5() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx6() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonApprochingDelay_LastMessageIdx7() {
        mTestGivens.test_I_have_1_person_approching_delay_with_id_20();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }






    public void testWeHaveOnePersonWhoDecreasedMood_LastMessageIdx0() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.MANAGE_UNMANAGED_PEOPLE);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor2(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx1() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.FILL_IN_DELAY_FEEDBACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();

    }

    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx2() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.UPDATE_MOOD);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx3() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.SET_UP_A_FREQUENCY_OF_CONTACT);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx4() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }

    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx5() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY);

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx6() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.NOTE_PEOPLE_WHO_CHANGED_MOOD_TODAY);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }


    public void testWeHaveOnePersonWhoChangedMood_LastMessageIdx7() {
        mTestGivens.test_I_have_1_person_who_changed_mood_with_id_21();
        Status.setLastMessageIdx(mContext, Status.TAKE_TIME_FOR_FEEDBACK);
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.BoardData.URI_PAGE_BOARD, null, null, null, null);
        mTestResults.test_assert_equals_BoardData_cursor3(mContext, cursor);
        cursor.close();
    }







    public void test_getTopCursors() {
        assertEquals(Status.ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK,
                BoardData.getTopCursors(mContext, Status.UPDATE_MOOD));
    }
}
