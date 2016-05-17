package com.elorri.android.friendforcast;

import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;

/**
 * Created by Elorri on 10/05/2016.
 */
public class TestResults extends AndroidTestCase {


    public void test_assert_equals_BoardData_cursor1(Context context, Cursor cursor) {

    }

    public void test_assert_equals_BoardData_cursor2(Context mContext, Cursor cursor) {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.manage_unmanaged_people_message, 1) + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Unmanaged people|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION[5] + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|pierre||2130837600|" + ViewTypes
                .VIEW_UNMANAGED_PEOPLE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor3(Context mContext, Cursor cursor) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.take_time_for_feedback_message) + "|"
                + ViewTypes.VIEW_MESSAGE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor4(Context mContext, Cursor cursor) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_in_delay_feedback_message, 1) +
                "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_in_delay_feedback_title) + "|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[5] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION[6] + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fp|hector|null|2130837603|" + ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor5(Context mContext, Cursor cursor) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.update_mood_message, 1) +
                "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.mood_to_update) + "|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[5] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION[6] + "|\n"
                + "row |17|834|298i5.3552i264b0e868b8a42fp|paul|null|2130837603|" + ViewTypes.VIEW_UPDATE_MOOD + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor6(Context mContext, Cursor cursor) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_up_frequency_message, 1) +
                "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.fill_up_frequency_title) + "|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[5] + "|"
                + ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.PROJECTION[6] + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42op|jacques|null|2130837599|"
                + ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor7(Context mContext, Cursor cursor) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));


        String cursorString = "\n"
                + "header |"
                + ContactDAO.RatioQuery.PROJECTION[0] + "|"
                + ContactDAO.RatioQuery.PROJECTION[1] + "|\n"
                + "row |0|" + ViewTypes.VIEW_FORECAST + "|\n"
                + "header |"
                + MatrixCursors.MessageQuery.COLUMN_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.ask_for_feedback_message, 1) +
                "|"
                + ViewTypes.VIEW_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.ask_for_feedback_title) + "|" + ViewTypes
                .VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[0] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[1] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[2] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[3] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[4] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[5] + "|"
                + ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION[6] + "|\n"
                + "row |19|836|298i6.3552i264b0e968b8a42op|zorro|null|2130837603|"
                + ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
    }

    public void test_assert_equals_BoardData_cursor8(Context mContext, Cursor cursor) {
        //TODO don't forget to check that the mood decreased.
    }
}
