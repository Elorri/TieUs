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
        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(15)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|2130837600|null|null|null|null|0|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE
                + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        long now = DateUtils.todayStart();

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|2130837600|null|null|null|null|0|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.ConfirmMessageQuery.COLUMN_CONFIRM_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Long press the action to mark it as complete or uncomplete|" + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |8|com.google.android.gm|package|Thank you|" + DateUtils.addDay(-4, now) + "|null|" + ViewTypes
                .VIEW_NEXT_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void testDetailUndoneActionEducatedUser() {
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(16)),
                null,
                null,
                null,
                null
        );

        long now = DateUtils.todayStart();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|2130837600|null|null|null|null|0|"
                + ViewTypes.VIEW_CONTACT + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |8|com.google.android.gm|package|Thank you|" + DateUtils.addDay(-4, now) + "|null|" + ViewTypes
                .VIEW_NEXT_ACTION + "|\n";

        assertEquals(cursorString, TestUtility.getCursorString(cursor));

        cursor.close();
    }


    public void testDetailDoneActionUneducatedUser() {
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        long now = DateUtils.todayStart();
        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|2130837603|null|null|null" +
                "|null|0|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.ConfirmMessageQuery.COLUMN_CONFIRM_MESSAGE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Long press the action to mark it as complete or uncomplete|" + ViewTypes.VIEW_CONFIRM_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |9|com.google.android.gm|package|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|" + ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void testDetailDoneActionEducatedUser() {
        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_true();

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(17)),
                null,
                null,
                null,
                null
        );
        long now = DateUtils.todayStart();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactDAO.ContactQuery.PROJECTION_WITH_VIEWTYPE)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|2130837603|null|null|null" +
                "|null|0|" + ViewTypes.VIEW_CONTACT + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Next actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Add actions to start following up with this person|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |Done actions|" + ViewTypes.VIEW_TITLE + "|\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT)
                + "row |9|com.google.android.gm|package|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|" + ViewTypes.VIEW_DONE_ACTION + "|\n";
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


}
