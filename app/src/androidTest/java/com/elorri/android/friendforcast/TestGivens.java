package com.elorri.android.friendforcast;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestGivens extends AndroidTestCase {

    public static final String CONTACT_ID_VALUE_15 = "15";
    public static final String CONTACT_ANDROID_CONTACT_ID_VALUE = "832";
    public static final String CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE = "298i5.3552i264b0e968b8a42ff";
    public static final String CONTACT_ANDROID_CONTACT_NAME_VALUE = "acticall";
    public static final String CONTACT_THUMBNAIL_VALUE = null;
    public static final String CONTACT_EMOICON_ID_VALUE = "2130837600";
    public static final String ACTION_ID_VALUE_5 = "5";
    public static final String ACTION_TITLE = "Give feedback";
    public static final String ACTION_NAME = "Thank you";
    public static final String ACTION_SORT_ORDER = "10";
    public static final String VECTOR_ID_VALUE_32 = "32";
    public static final String VECTOR_NAME = "Gmail";
    public static final String VECTOR_DATA = "com.google.android.gm";
    public static final String VECTOR_MIMETYPE = "package";
    public static final String EVENT_ID = "8";
    public static final String EVENT_CONTACT_ID_VALUE_15 = CONTACT_ID_VALUE_15;
    public static final String EVENT_ACTION_ID_VALUE_5 = ACTION_ID_VALUE_5;
    public static final String EVENT_VECTOR_ID_VALUE_32 = VECTOR_ID_VALUE_32;
    public static final String EVENT_TIMESTART = "1462226400000";
    public static final String EVENT_TIMEEND = "1462226400000";


    public static final String EDUCATE_MESSAGE = "Long press to complete or uncomplete action";
    public static final String EMPTY_DETAIL_ACTION_CURSOR_MESSAGE = "Add actions to start following up with this person";

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


    @Override
    protected void tearDown() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        deleteAllRecordsFromDB();
        super.tearDown();
    }


    public void deleteAllRecordsFromDB() {
        aContext.getContentResolver().delete(FriendForecastContract.ContactTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.EventTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.ActionTable.CONTENT_URI, null, null);
        aContext.getContentResolver().delete(FriendForecastContract.VectorTable.CONTENT_URI, null, null);
    }

    /**
     * Given I have a table contact that contain 1 row
     * with ContactTable._ID=CONTACT_ID_VALUE_15
     * and ContactTable.COLUMN_ANDROID_CONTACT_ID=CONTACT_ANDROID_CONTACT_ID_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY=CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_NAME=CONTACT_ANDROID_CONTACT_NAME_VALUE
     * and ContactTable.COLUMN_THUMBNAIL=CONTACT_THUMBNAIL_VALUE
     * and ContactTable.COLUMN_EMOICON_ID=CONTACT_EMOICON_ID_VALUE
     */
    public void test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_15() {
        test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_15(
                TestGivens.CONTACT_ID_VALUE_15,
                TestGivens.CONTACT_ANDROID_CONTACT_ID_VALUE,
                TestGivens.CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE,
                TestGivens.CONTACT_ANDROID_CONTACT_NAME_VALUE,
                TestGivens.CONTACT_THUMBNAIL_VALUE,
                TestGivens.CONTACT_EMOICON_ID_VALUE
        );
    }

    public void test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15() {
        test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15(
                TestGivens.CONTACT_ID_VALUE_15);
    }

    /**
     * Given that I have a table action with one row
     * ActionTable._ID=ACTION_ID_VALUE_5
     * ActionTable.COLUMN_TITLE=ACTION_TITLE
     * ActionTable.COLUMN_NAME=ACTION_NAME
     * ActionTable.COLUMN_SORT_ORDER=ACTION_SORT_ORDER
     */
    public void test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5() {
        test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5(
                ACTION_ID_VALUE_5,
                ACTION_TITLE,
                ACTION_NAME,
                ACTION_SORT_ORDER);
    }

    /**
     * Given I have a table vector with at least 1 row
     * VectorTable._ID=VECTOR_ID_VALUE_32
     * VectorTable.COLUMN_NAME=VECTOR_NAME
     * VectorTable.COLUMN_DATA=VECTOR_DATA
     * VectorTable.COLUMN_MIMETYPE=VECTOR_MIMETYPE
     */
    public void test_I_have_a_table_vector_that_contains_at_least_1_row() {
        test_I_have_a_table_vector_that_contains_at_least_1_row(
                VECTOR_ID_VALUE_32,
                VECTOR_NAME,
                VECTOR_DATA,
                VECTOR_MIMETYPE);
    }


    /**
     * Given I have a table event that contains at least 1 row
     * EventTable._ID = EVENT_ID
     * EventTable.COLUMN_CONTACT_ID =  EVENT_CONTACT_ID_VALUE_15
     * EventTable.COLUMN_ACTION_ID =  EVENT_ACTION_ID_VALUE_5
     * EventTable.COLUMN_VECTOR_ID = EVENT_VECTOR_ID
     * EventTable.COLUMN_TIME_START =  EVENT_TIMESTART
     */
    public void test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_15_action_id_5() {
        test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_15_action_id_5(
                EVENT_ID,
                EVENT_CONTACT_ID_VALUE_15,
                EVENT_ACTION_ID_VALUE_5,
                EVENT_VECTOR_ID_VALUE_32,
                EVENT_TIMESTART,
                EVENT_TIMEEND
        );
    }

    /**
     * Given a I have a preference getMarkActionFeatureStatus equals to false
     */
    public void test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false() {
        Status.setMarkActionFeatureStatus(aContext, false);
        assertEquals(false, Status.getMarkActionFeatureStatus(aContext));
    }


    public void test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_15(
            String contactIdValue15,
            String androidContactIdValue,
            String androidContactLookUpKeyValue,
            String androidContactNameValue,
            String thumnailValue,
            String emoiconIdValue) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");

        assertEquals(CONTACT_ID_VALUE_15, contactIdValue15);

        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ContactTable._ID, contactIdValue15);
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID, androidContactIdValue);
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY, androidContactLookUpKeyValue);
        values.put(FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME, androidContactNameValue);
        values.put(FriendForecastContract.ContactTable.COLUMN_THUMBNAIL, thumnailValue);
        values.put(FriendForecastContract.ContactTable.COLUMN_EMOICON_ID, emoiconIdValue);

        Uri contactUri = aContext.getContentResolver().insert(
                FriendForecastContract.ContactTable.CONTENT_URI, values);
        long contactId = ContentUris.parseId(contactUri);
        assertTrue(contactId != -1);

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ContactTable.CONTENT_URI,
                null,
                FriendForecastContract.ContactTable._ID + "=?",
                new String[]{String.valueOf(contactId)},
                null
        );

        TestUtilities.validateCursor(cursor, values);

        cursor.close();
    }


    public void test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15(String contactIdValue15) {
        assertEquals(CONTACT_ID_VALUE_15, contactIdValue15);
        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=?",
                new String[]{String.valueOf(contactIdValue15)},
                null
        );
        assertTrue(cursor.getCount() == 0);
        cursor.close();
    }

    public void test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5(
            String action_id_value_5,
            String action_title,
            String action_name,
            String action_sort_order) {

        assertEquals(ACTION_ID_VALUE_5, action_id_value_5);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");

        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.ActionTable._ID, action_id_value_5);
        values.put(FriendForecastContract.ActionTable.COLUMN_TITLE, action_title);
        values.put(FriendForecastContract.ActionTable.COLUMN_NAME, action_name);
        values.put(FriendForecastContract.ActionTable.COLUMN_SORT_ORDER, action_sort_order);


        Uri uri = aContext.getContentResolver().insert(
                FriendForecastContract.ActionTable.CONTENT_URI, values);
        long _id = ContentUris.parseId(uri);
        assertTrue(_id != -1);

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.ActionTable.CONTENT_URI,
                null,
                FriendForecastContract.ActionTable._ID + "=?",
                new String[]{String.valueOf(_id)},
                null
        );

        TestUtilities.validateCursor(cursor, values);

        cursor.close();
    }


    public void test_I_have_a_table_vector_that_contains_at_least_1_row(
            String vector_id,
            String vector_name,
            String vector_data,
            String vector_mimetype) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");

        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.VectorTable._ID, vector_id);
        values.put(FriendForecastContract.VectorTable.COLUMN_NAME, vector_name);
        values.put(FriendForecastContract.VectorTable.COLUMN_DATA, vector_data);
        values.put(FriendForecastContract.VectorTable.COLUMN_MIMETYPE, vector_mimetype);


        Uri uri = aContext.getContentResolver().insert(
                FriendForecastContract.VectorTable.CONTENT_URI, values);
        long _id = ContentUris.parseId(uri);
        assertTrue(_id != -1);

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.VectorTable.CONTENT_URI,
                null,
                FriendForecastContract.VectorTable._ID + "=?",
                new String[]{String.valueOf(_id)},
                null
        );

        TestUtilities.validateCursor(cursor, values);

        cursor.close();
    }


    public void test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_15_action_id_5(
            String event_id,
            String event_contact_id,
            String event_action_id,
            String event_vector_id,
            String event_timestart,
            String event_timeend) {

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");

        assertEquals(EVENT_CONTACT_ID_VALUE_15, event_contact_id);
        assertEquals(EVENT_ACTION_ID_VALUE_5, event_action_id);
        assertEquals(EVENT_VECTOR_ID_VALUE_32, event_vector_id);

        ContentValues values = new ContentValues();
        values.put(FriendForecastContract.EventTable._ID, event_id);
        values.put(FriendForecastContract.EventTable.COLUMN_CONTACT_ID, event_contact_id);
        values.put(FriendForecastContract.EventTable.COLUMN_ACTION_ID, event_action_id);
        values.put(FriendForecastContract.EventTable.COLUMN_VECTOR_ID, event_vector_id);
        values.put(FriendForecastContract.EventTable.COLUMN_TIME_START, event_timestart);
        values.put(FriendForecastContract.EventTable.COLUMN_TIME_END, event_timeend);


        Uri uri = aContext.getContentResolver().insert(
                FriendForecastContract.EventTable.CONTENT_URI, values);
        long _id = ContentUris.parseId(uri);
        assertTrue(_id != -1);

        Cursor cursor = aContext.getContentResolver().query(
                FriendForecastContract.EventTable.CONTENT_URI,
                null,
                FriendForecastContract.EventTable.COLUMN_ACTION_ID + "=?  and "
                        + FriendForecastContract.EventTable.COLUMN_CONTACT_ID + "=?",
                new String[]{EVENT_ACTION_ID_VALUE_5, EVENT_CONTACT_ID_VALUE_15},
                null
        );
        TestUtilities.validateCursor(cursor, values);
        cursor.close();
    }


}
