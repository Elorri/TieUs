package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.Projections;

/**
 * Created by Elorri on 08/05/2016.
 */
public class TestDetailData_givens1 extends AndroidTestCase {

    TestGivens mTestGivens;


    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain at least 1 row
     * with ContactTable._ID=CONTACT_ID_VALUE_15
     * and ContactTable.COLUMN_ANDROID_CONTACT_ID=CONTACT_ANDROID_CONTACT_ID_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY=CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_NAME=CONTACT_ANDROID_CONTACT_NAME_VALUE
     * and ContactTable.COLUMN_THUMBNAIL=CONTACT_THUMBNAIL_VALUE
     * and ContactTable.COLUMN_EMOICON_ID=CONTACT_EMOICON_ID_VALUE
     * Given I have a table event that does NOT contains rows with EventTable.COLUMN_CONTACT_ID=CONTACT_ID_VALUE_15
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();

        mTestGivens.test_I_have_a_table_contact_that_contains_at_least_1_row_with_id_15(
                TestGivens.CONTACT_ID_VALUE_15,
                TestGivens.CONTACT_ANDROID_CONTACT_ID_VALUE,
                TestGivens.CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE,
                TestGivens.CONTACT_ANDROID_CONTACT_NAME_VALUE,
                TestGivens.CONTACT_THUMBNAIL_VALUE,
                TestGivens.CONTACT_EMOICON_ID_VALUE
        );
        mTestGivens.test_I_have_a_table_event_that_does_not_contain_row_with_contact_id_15(
                TestGivens.CONTACT_ID_VALUE_15);
    }


    @Override
    protected void tearDown() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();
    }



    /**
     * When I query the uri content://com.elorri.android.communication/detail/15
     * Then I should get a Cursor with 2 rows
     * and row 1 that contains 7 columns
     * with column 1 name =ContactDAO.ContactQuery.PROJECTION_QUERY[0]
     * and column 2 name = ContactDAO.ContactQuery.PROJECTION_QUERY[1]
     * and column 3 name = ContactDAO.ContactQuery.PROJECTION_QUERY[2]
     * and column 4 name = ContactDAO.ContactQuery.PROJECTION_QUERY[3]
     * and column 5 name = ContactDAO.ContactQuery.PROJECTION_QUERY[4]
     * and column 6 name = ContactDAO.ContactQuery.PROJECTION_QUERY[5]
     * and column 7 name = Projections.VIEW_CONTACT
     * and column 1 value = CONTACT_ID_VALUE_15
     * and column 2 value = CONTACT_ANDROID_CONTACT_ID_VALUE
     * and column 3 value = CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE
     * and column 4 value = CONTACT_ANDROID_CONTACT_NAME_VALUE
     * and column 5 value = CONTACT_THUMBNAIL_VALUE
     * and column 6 value = CONTACT_EMOICON_ID_VALUE
     * and column 7 value = Projections.VIEW_CONTACT
     * and row 2 that contains 2 columns
     * with column 1 name = Projections.EmptyCursorMessageQuery.PROJECTION_QUERY[0]
     * and column 2 name = Projections.EmptyCursorMessageQuery.PROJECTION_QUERY[1]
     * and column 1 value = TestGivens.EMPTY_DETAIL_ACTION_CURSOR_MESSAGE
     * and column 2 value = Projections.VIEW_EMPTY_CURSOR_MESSAGE
     */
    public void testDetailNoActions() {

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(TestGivens.CONTACT_ID_VALUE_15)),
                null,
                null,
                null,
                null
        );

        assertEquals("Then I should get a Cursor with 2 rows", 2, cursor.getCount());

        //row 1
        cursor.moveToNext();
        assertEquals("and row 1 that contains 7 columns",
                ContactDAO.ContactQuery.PROJECTION.length, cursor.getColumnCount());

        assertEquals("with column 1 name = ", ContactDAO.ContactQuery.PROJECTION[0], cursor.getColumnName(0));
        assertEquals("and column 2 name = ", ContactDAO.ContactQuery.PROJECTION[1], cursor.getColumnName(1));
        assertEquals("and column 3 name = ", ContactDAO.ContactQuery.PROJECTION[2], cursor.getColumnName(2));
        assertEquals("and column 4 name = ", ContactDAO.ContactQuery.PROJECTION[3], cursor.getColumnName(3));
        assertEquals("and column 5 name = ", ContactDAO.ContactQuery.PROJECTION[4], cursor.getColumnName(4));
        assertEquals("and column 6 name = ", ContactDAO.ContactQuery.PROJECTION[5], cursor.getColumnName(5));
        assertEquals("and column 7 name = ", ContactDAO.ContactQuery.PROJECTION[6], cursor.getColumnName(6));

        assertEquals("and column 1 value = ", TestGivens.CONTACT_ID_VALUE_15, cursor.getString(0));
        assertEquals("and column 2 value = ", TestGivens.CONTACT_ANDROID_CONTACT_ID_VALUE, cursor.getString(1));
        assertEquals("and column 3 value = ", TestGivens.CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE, cursor.getString(2));
        assertEquals("and column 4 value = ", TestGivens.CONTACT_ANDROID_CONTACT_NAME_VALUE, cursor.getString(3));
        assertEquals("and column 5 value = ", TestGivens.CONTACT_THUMBNAIL_VALUE, cursor.getString(4));
        assertEquals("and column 6 value = ", TestGivens.CONTACT_EMOICON_ID_VALUE, cursor.getString(5));
        assertEquals("and column 7 value = ", Projections.VIEW_CONTACT, cursor.getInt(6));


        //row 2
        cursor.moveToNext();
        assertEquals("and row 2 that contains 2 columns",
                Projections.EmptyCursorMessageQuery.PROJECTION.length, cursor.getColumnCount());

        assertEquals("with column 1 name = ", Projections.EmptyCursorMessageQuery.PROJECTION[0], cursor.getColumnName(0));
        assertEquals("and column 2 name = ", Projections.EmptyCursorMessageQuery.PROJECTION[1], cursor.getColumnName(1));
        assertEquals("and column 1 value = ", TestGivens.EMPTY_DETAIL_ACTION_CURSOR_MESSAGE, cursor.getString(0));
        assertEquals("and column 2 value = ", Projections.VIEW_EMPTY_CURSOR_MESSAGE, cursor.getInt(1));

        cursor.close();
    }


}
