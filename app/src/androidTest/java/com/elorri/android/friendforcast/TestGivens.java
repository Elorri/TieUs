package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.data.FriendForecastDbHelper;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestGivens extends AndroidTestCase {

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


    public void test_I_have_a_table_contact_with_id_15() {
        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();


        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                FriendForecastContract.ContactTable._ID + "=?",
                new String[]{String.valueOf(15)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_contact_with_id_16() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                FriendForecastContract.ContactTable._ID + "=?",
                new String[]{String.valueOf(16)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_contact_with_id_17() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                FriendForecastContract.ContactTable._ID + "=?",
                new String[]{String.valueOf(17)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_contact_with_id_18() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                FriendForecastContract.ContactTable._ID + "=?",
                new String[]{String.valueOf(18)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }


    public void test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15() {
        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=?",
                new String[]{String.valueOf(15)},
                null
        );
        assertTrue(cursor.getCount() == 0);
        cursor.close();
    }

    public void test_I_have_a_table_action_with_id_5() {
        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ActionTable.CONTENT_URI,
                null,
                FriendForecastContract.ActionTable._ID + "=?",
                new String[]{String.valueOf(5)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }


    public void test_I_have_a_table_vector_with_id_32() {
        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.VectorTable.CONTENT_URI,
                null,
                FriendForecastContract.VectorTable._ID + "=?",
                new String[]{String.valueOf(32)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }


    public void test_I_have_a_table_event_with_contact_id_16_action_id_5_timeend_null() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "=?  and "
                        + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                        + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null ",
                new String[]{String.valueOf(5), String.valueOf(16)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_event_with_contact_id_17_action_id_5_timeend_not_null() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "=?  and "
                        + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                        + FriendForecastContract.EventTable.COLUMN_TIME_END + " not null ",
                new String[]{String.valueOf(5), String.valueOf(17)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_null() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "=?  and "
                        + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                        + FriendForecastContract.EventTable.COLUMN_TIME_END + " is null ",
                new String[]{String.valueOf(5), String.valueOf(18)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }

    public void test_I_have_a_table_event_with_contact_id_18_action_id_5_timeend_not_null() {

        test_fillContactTable();
        test_fillActionTable();
        test_fillVectorTable();
        test_fillEventTable();

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "=?  and "
                        + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                        + FriendForecastContract.EventTable.COLUMN_TIME_END + " not null ",
                new String[]{String.valueOf(5), String.valueOf(18)},
                null
        );
        assertTrue(cursor.getCount() > 0);
        cursor.close();
    }


    public void test_fillContactTable() {

        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|Paul||2130837600|\n"
                + "row |16|833|298i5.3552i264b0e968b8a46ff|Pierre||2130837600|\n"
                + "row |17|834|298i5.3552i264b0e968b8a47ff|Jacques||2130837600|\n"
                + "row |18|835|298i5.3552i264b0e968b8a49ff|Martin||2130837600|\n";

        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(4, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(4, contactCursor.getCount());

//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "expected : \n"
//                + contactString);
//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actual : \n"
//                + TestUtility.getCursorString(contactCursor));

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();
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
        assertEquals(vectorString, TestUtility.getCursorString(vectorCursor));
    }

    public void test_fillEventTable() {
        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |8|16|5|32|1462226400000|null|\n"
                + "row |9|17|5|32|1462226400000|1462791975441|\n"
                + "row |10|18|5|32|1462226400000|null|\n"
                + "row |11|18|5|32|1462226400050|1462791975441|\n";
        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));
        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);
        assertEquals(4, insertCount);
        Cursor eventCursor = aContext.getContentResolver().query(FriendForecastContract.EventTable
                .CONTENT_URI, null, null, null, null);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "expected : "
                + eventString);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actual : "
                + TestUtility.getCursorString(eventCursor));
        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
    }


    public void test_I_have_1_unmanaged_person_id_15() {
        test_I_have_1_contact_in_ContactTable();
        test_fillActionTable();
        test_fillVectorTable();


        SQLiteDatabase db = new FriendForecastDbHelper(aContext).getReadableDatabase();
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery
                .SELECT_UNMANAGED_PEOPLE, null);

        assertTrue(cursor.getCount() > 0);
    }

    public void test_I_have_1_taken_action_people_with_id_16() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fp|Hector|null|"
                + R.drawable.ic_social_network + "|null|null|null" + "|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |8|16|5|32|1462226400000|1462791975441|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();

    }

    public void test_I_have_1_contact_in_ContactTable() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|Pierre||2130837600|null|null|null|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "expected : \n"
//                + contactString);
//        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "actual : \n"
//                + TestUtility.getCursorString(contactCursor));

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();
    }

    public void test_I_have_1_person_that_need_mood_update_with_id_17() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |17|834|298i5.3552i264b0e868b8a42fp|Paul|null|"
                + R.drawable.ic_social_network + "|1462791980000|"
                + DateUtils.tomorrowStart()
                + "|null|null" + "|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |9|17|5|32|1462226400000|1462791975441|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();

    }


    public void test_I_have_1_person_that_need_frequency_update_with_id_18() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42op|Jacques|null|"
                + R.drawable.ic_sentiment_neutral_black_24dp + "|1462791980000|"
                + DateUtils.tomorrowStart()
                + "|null|null" + "|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |10|18|5|32|1462226400000|1462791975441|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();

    }


    public void test_I_have_1_person_we_have_to_ask_for_feedback_with_id_19() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |19|836|298i6.3552i264b0e968b8a42op|Zorro|null|"
                + R.drawable.ic_social_network + "|1462791980000|"
                + DateUtils.yesterdayStart()
                + "|null|null" + "|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |11|19|5|32|1462226400000|1462791975441|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();
    }

    public void test_I_have_1_person_approching_delay_with_id_20() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);
        long thereIs5Days = DateUtils.addDay(-5, DateUtils.todayStart());
        long thereIs4Days = DateUtils.addDay(-4, DateUtils.todayStart());
        long thereIs2Days = DateUtils.addDay(-2, DateUtils.todayStart());

        //to have lastMoodUpdate + freqOfContact = now
        // or better lastMoodUpdate + freqOfContact = now-1 (to make sure we are before the dead line)
        // we need freqOfContact=now-1-lastMoodUpdate
        long freqOfContact = System.currentTimeMillis() - 1 - thereIs2Days;

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_INCREASED_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |20|835|298i5.3552i264b0e968b8a42op|Superman|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + thereIs5Days + "|"
                + thereIs4Days + "|"
                + freqOfContact + "|"
                + thereIs2Days + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + contactString + " now " + "" + System.currentTimeMillis());

        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |12|20|5|32|1462226400000|null|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();

    }

    public void test_I_have_1_person_who_changed_mood_with_id_21() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);

        String contactString = "\n"
                + "header |" +
                FriendForecastContract.ContactTable._ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "|" +
                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + "|" +
                FriendForecastContract.ContactTable.COLUMN_THUMBNAIL + "|" +
                FriendForecastContract.ContactTable.COLUMN_MOOD + "|" +
                FriendForecastContract.ContactTable.COLUMN_FEEDBACK_EXPECTED_DELAY + "|" +
                FriendForecastContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "|" +
                FriendForecastContract.ContactTable.COLUMN_LAST_MOOD_UPDATE + "|\n"
                + "row |21|837|298i5.3552m264b0e968b8a42op|Superwoman|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|1462791980000|1814400000|1464606375441" +
                "|\n";


        ContentValues[] contactValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(contactString));

        int insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.ContactTable.CONTENT_URI,
                contactValues);

        assertEquals(1, insertCount);

        Cursor contactCursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, contactCursor.getCount());

        assertEquals(contactString, TestUtility.getCursorString(contactCursor));
        contactCursor.close();


        test_fillActionTable();
        test_fillVectorTable();

        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);

        String eventString = "\n"
                + "header |" +
                FriendForecastContract.EventTable._ID + "|" +
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_VECTOR_ID + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_START + "|" +
                FriendForecastContract.EventTable.COLUMN_TIME_END + "|\n"
                + "row |13|21|5|32|1462226400000|1462791975441|\n";


        ContentValues[] eventValues = TestUtility.fromCursorToContentValues(
                TestUtility.getCursorFromString(eventString));

        insertCount = aContext.getContentResolver().bulkInsert(FriendForecastContract.EventTable
                        .CONTENT_URI,
                eventValues);

        assertEquals(1, insertCount);

        Cursor eventCursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals(1, eventCursor.getCount());

        assertEquals(eventString, TestUtility.getCursorString(eventCursor));
        contactCursor.close();
    }
}
