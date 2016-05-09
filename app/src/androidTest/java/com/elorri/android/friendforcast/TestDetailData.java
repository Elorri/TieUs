package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 04/05/2016.
 */
public class TestDetailData extends AndroidTestCase {
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
        mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();
    }

    public void testDetailNoActions() {
        mTestGivens.test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_15();
        mTestGivens.test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(15)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_16();
        mTestGivens.test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5();
        mTestGivens.test_I_have_a_table_vector_that_contains_at_least_1_row_with_vector_id_32();
        mTestGivens.test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_16_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();


        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        cursor.close();

    }


    public void testDetailUndoneActionEducatedUser() {
        mTestGivens.test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_16();
        mTestGivens.test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5();
        mTestGivens.test_I_have_a_table_vector_that_contains_at_least_1_row_with_vector_id_32();
        mTestGivens.test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_16_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailDoneActionUneducatedUser() {
    }

    public void testDetailDoneActionEducatedUser() {
    }


    public void testDetailDoneAndUndoneActionUneducatedUser() {
    }

    public void testDetailDoneAndUndoneActionEducatedUser() {
    }

}
