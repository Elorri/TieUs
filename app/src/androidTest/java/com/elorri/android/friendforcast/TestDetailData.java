package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;

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


//    @Override
//    protected void tearDown() throws Exception {
//        mTestGivens.deleteAllRecordsFromDB();
//        super.tearDown();
//    }

    public void testDetailNoActions() {
        mTestGivens.test_I_have_a_table_contact_with_id_15();
        mTestGivens.test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(15)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE
                + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_16();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens.test_I_have_a_table_event_with_contact_id_16_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();


        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a46ff|pierre||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EducateMessageQuery.COLUMN_EDUCATE_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Long press the action to mark it as complete or uncomplete|" + ViewTypes.VIEW_EDUCATE_MESSAGE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6] + "|\n"
                + "row |8|com.google.android.gm|package|Thank you|1462226400000|null|" + ViewTypes.VIEW_NEXT_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }


    public void testDetailUndoneActionEducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_16();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens.test_I_have_a_table_event_with_contact_id_16_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a46ff|pierre||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6] + "|\n"
                + "row |8|com.google.android.gm|package|Thank you|1462226400000|null|" + ViewTypes.VIEW_NEXT_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));

        cursor.close();
    }


    public void testDetailDoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_17();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens
                .test_I_have_a_table_event_with_contact_id_17_action_id_5_timeend_not_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a47ff|jacques||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EducateMessageQuery.COLUMN_EDUCATE_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Long press the action to mark it as complete or uncomplete|" + ViewTypes.VIEW_EDUCATE_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[6] + "|\n"
                + "row |9|com.google.android.gm|package|Thank you|1462226400000|1462791975441|" +
                ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testDetailDoneActionEducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_17();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens
                .test_I_have_a_table_event_with_contact_id_17_action_id_5_timeend_not_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a47ff|jacques||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[6] + "|\n"
                + "row |9|com.google.android.gm|package|Thank you|1462226400000|1462791975441|" + ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailDoneAndUndoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_18();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens.test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_not_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(18)),
                null,
                null,
                null,
                null
        );


        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a49ff|martin||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EducateMessageQuery.COLUMN_EDUCATE_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Long press the action to mark it as complete or uncomplete|" + ViewTypes.VIEW_EDUCATE_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6] + "|\n"
                + "row |10|com.google.android.gm|package|Thank you|1462226400000|null|" + ViewTypes.VIEW_NEXT_ACTION + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[6] + "|\n"
                + "row |11|com.google.android.gm|package|Thank you|1462226400050|1462791975441|" +
                ViewTypes.VIEW_DONE_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testDetailDoneAndUndoneActionEducatedUser() {
        mTestGivens.test_I_have_a_table_contact_with_id_18();
        mTestGivens.test_I_have_a_table_action_with_id_5();
        mTestGivens.test_I_have_a_table_vector_with_id_32();
        mTestGivens.test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_null();
        mTestGivens.test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_not_null();
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(18)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + "header |"
                + FriendForecastContract.ContactTable._ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|"
                + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|"
                + FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|"
                + FriendForecastContract.ContactTable.COLUMN_EMOICON_ID + "|"
                + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a49ff|martin||2130837600|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6] + "|\n"
                + "row |10|com.google.android.gm|package|Thank you|1462226400000|null|" + ViewTypes.VIEW_NEXT_ACTION + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[0] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[1] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[2] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[3] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[4] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[5] + "|"
                + ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_DONE[6] + "|\n"
                + "row |11|com.google.android.gm|package|Thank you|1462226400050|1462791975441|" +
                ViewTypes.VIEW_DONE_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));


        cursor.close();
    }

}
