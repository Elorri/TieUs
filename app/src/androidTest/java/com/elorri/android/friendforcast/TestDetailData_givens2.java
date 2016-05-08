package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.database.MergeCursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.Projections;

/**
 * Created by Elorri on 04/05/2016.
 */
public class TestDetailData_givens2 extends AndroidTestCase {
    TestGivens mTestGivens;

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with ContactTable._ID=CONTACT_ID_VALUE_15
     * and ContactTable.COLUMN_ANDROID_CONTACT_ID=CONTACT_ANDROID_CONTACT_ID_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY=CONTACT_ANDROID_CONTACT_LOOKUP_KEY_VALUE
     * and ContactTable.COLUMN_ANDROID_CONTACT_NAME=CONTACT_ANDROID_CONTACT_NAME_VALUE
     * and ContactTable.COLUMN_THUMBNAIL=CONTACT_THUMBNAIL_VALUE
     * and ContactTable.COLUMN_EMOICON_ID=CONTACT_EMOICON_ID_VALUE
     * Given that I have a table action with one row
     * ActionTable._ID=ACTION_ID_VALUE_5
     * ActionTable.COLUMN_TITLE=ACTION_TITLE
     * ActionTable.COLUMN_NAME=ACTION_NAME
     * ActionTable.COLUMN_SORT_ORDER=ACTION_SORT_ORDER
     * Given I have a table vector with at least 1 row
     * VectorTable._ID=VECTOR_ID_VALUE_32
     * VectorTable.COLUMN_NAME=VECTOR_NAME
     * VectorTable.COLUMN_DATA=VECTOR_DATA
     * VectorTable.COLUMN_MIMETYPE=VECTOR_MIMETYPE
     * Given I have a table event that contains at least 1 row
     * EventTable._ID = EVENT_ID
     * EventTable.COLUMN_CONTACT_ID =  EVENT_CONTACT_ID_VALUE_15
     * EventTable.COLUMN_ACTION_ID =  EVENT_ACTION_ID_VALUE_5
     * EventTable.COLUMN_VECTOR_ID = EVENT_VECTOR_ID
     * EventTable.COLUMN_TIME_START =  EVENT_TIMESTART
     * Given a I have a preference getMarkActionFeatureStatus equals to false
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        super.setUp();

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

        mTestGivens.test_I_have_a_table_action_that_contains_at_least_1_row_with_action_id_5(
                TestGivens.ACTION_ID_VALUE_5,
                TestGivens.ACTION_TITLE,
                TestGivens.ACTION_NAME,
                TestGivens.ACTION_SORT_ORDER
        );

        mTestGivens.test_I_have_a_table_vector_that_contains_at_least_1_row(
                TestGivens.VECTOR_ID_VALUE_32,
                TestGivens.VECTOR_NAME,
                TestGivens.VECTOR_DATA,
                TestGivens.VECTOR_MIMETYPE
        );

        mTestGivens.test_I_have_a_table_event_that_contains_at_least_1_row_with_contact_id_15_action_id_5(
                TestGivens.EVENT_ID,
                TestGivens.EVENT_CONTACT_ID_VALUE_15,
                TestGivens.EVENT_ACTION_ID_VALUE_5,
                TestGivens.EVENT_VECTOR_ID_VALUE_32,
                TestGivens.EVENT_TIMESTART,
                TestGivens.EVENT_TIMEEND
        );

        mTestGivens.test_I_have_a_preference_getMarkActionFeatureStatus_equals_to_false();
    }


    @Override
    protected void tearDown() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();
    }



    /**
     * When I query the uri content://com.elorri.android.communication/detail/15
     * Then I should get a Cursor with 3 rows
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
     * with column 1 name = Projections.EducateMessageQuery.PROJECTION_QUERY[0]
     * and column 2 name = Projections.EducateMessageQuery.PROJECTION_QUERY[1]
     * and column 1 value = TestGivens.EDUCATE_MESSAGE
     * and column 2 value = Projections.VIEW_EDUCATE_MESSAGE

     * with column 1 name =ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0]
     * and column 2 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1]
     * and column 3 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2]
     * and column 4 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3]
     * and column 5 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4]
     * and column 6 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5]
     * and column 7 name = ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6]
     * and column 1 value = TestGivens.EVENT_ID
     * and column 2 value = TestGivens.VECTOR_DATA
     * and column 3 value = TestGivens.VECTOR_MIMETYPE
     * and column 4 value = TestGivens.ACTION_NAME
     * and column 5 value = TestGivens.EVENT_TIMESTART
     * and column 6 value = TestGivens.EVENT_TIMEEND
     * and column 7 value = Projections.VIEW_NEXT_ACTION
     */
    public void testDetailUndoneActionUneducatedUser() {

        Cursor cursor = mContext.getContentResolver().query(
                FriendForecastContract.DetailData.buildDetailUri(Long.valueOf(TestGivens.CONTACT_ID_VALUE_15)),
                null,
                null,
                null,
                null
        );

        TestUtility.printMergedCursor(cursor);

        assertEquals("Then I should get a Cursor with 3 rows", 3, cursor.getCount());

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
                Projections.EducateMessageQuery.PROJECTION.length, cursor.getColumnCount());

        assertEquals("with column 1 name = ", Projections.EducateMessageQuery.PROJECTION[0], cursor.getColumnName(0));
        assertEquals("and column 2 name = ", Projections.EducateMessageQuery.PROJECTION[1], cursor.getColumnName(1));
        assertEquals("and column 1 value = ", TestGivens.EDUCATE_MESSAGE, cursor.getString(0));
        assertEquals("and column 2 value = ", Projections.VIEW_EDUCATE_MESSAGE, cursor.getInt(1));



        //row 3
        cursor.moveToNext();
        assertEquals("and row 1 that contains 7 columns",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT.length,
                cursor.getColumnCount());

        assertEquals("with column 1 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[0], cursor.getColumnName(0));
        assertEquals("and column 2 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[1], cursor.getColumnName(1));
        assertEquals("and column 3 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[2], cursor.getColumnName(2));
        assertEquals("and column 4 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[3], cursor.getColumnName(3));
        assertEquals("and column 5 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[4], cursor.getColumnName(4));
        assertEquals("and column 6 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[5], cursor.getColumnName(5));
        assertEquals("and column 7 name = ",
                ContactActionVectorEventDAO.VectorActionByContactIdQuery.PROJECTION_NEXT[6], cursor.getColumnName(6));

        assertEquals("and column 1 value = ", TestGivens.EVENT_ID, cursor.getString(0));
        assertEquals("and column 2 value = ", TestGivens.VECTOR_DATA, cursor.getString(1));
        assertEquals("and column 3 value = ", TestGivens.VECTOR_MIMETYPE, cursor.getString(2));
        assertEquals("and column 4 value = ", TestGivens.ACTION_NAME, cursor.getString(3));
        assertEquals("and column 5 value = ", TestGivens.EVENT_TIMESTART, cursor.getString(4));
        assertEquals("and column 6 value = ", TestGivens.EVENT_TIMEEND, cursor.getString(5));
        assertEquals("and column 7 value = ", Projections.VIEW_NEXT_ACTION, cursor.getInt(6));

        cursor.close();

    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with _id=15
     * android_contact_id=832
     * android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * contact_name=acticall
     * thumnail=null
     * emoicon=2130837600
     * Given I have a table action
     * that contain 1 row
     * with _id=5
     * and title="Give feedback"
     * and name="Thank you"
     * and sort_order=10
     * Given I have a table vector
     * that contains 1 row
     * with _id=32
     * with name="Gmail"
     * with data="com.google.android.gm"
     * with mimetype="package"
     * Given I have a table event
     * that contains 1 row
     * with _id=8
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=null
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 2 rows
     * and row 1 that contains 7 columns
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     * and projection_type=3 standing for Projections.VIEW_CONTACT
     * and row 2 row that contains 7 columns
     * with event_id=8
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=null
     * and projection_type=10 standing for Projections.VIEW_NEXT_ACTION
     */
    public void testDetailUndoneActionEducatedUser() {
    }


    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with _id=15
     * android_contact_id=832
     * android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * contact_name=acticall
     * thumnail=null
     * emoicon=2130837600
     * Given I have a table action
     * that contain 1 row
     * with _id=5
     * and title="Give feedback"
     * and name="Thank you"
     * and sort_order=10
     * Given I have a table vector
     * that contains 1 row
     * with _id=32
     * with name="Gmail"
     * with data="com.google.android.gm"
     * with mimetype="package"
     * Given I have a table event
     * that contains 1 row
     * with _id=8
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=false
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 6 rows
     * and row 1 that contains 7 columns
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     * and projection_type=3 standing for Projections.VIEW_CONTACT
     * and row 2 should contains 2 columns
     * with message="Long press to complete or uncomplete action"
     * and projection_type=1 standing for Projections.EDUCATE_USER
     * and row 3 should contains 2 columns
     * with title="Next actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 4 that contains 2 columns
     * with message="Add actions to start following up with this person"
     * and projection_type=1 standing for Projections.VIEW_MESSAGE
     * and row 5 should contains 2 columns
     * with title="Done actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 6 should contains 7 columns
     * with event_id=8
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=1462269810423
     * and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneActionUneducatedUser() {
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with _id=15
     * android_contact_id=832
     * android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * contact_name=acticall
     * thumnail=null
     * emoicon=2130837600
     * Given I have a table action
     * that contain 1 row
     * with _id=5
     * and title="Give feedback"
     * and name="Thank you"
     * and sort_order=10
     * Given I have a table vector
     * that contains 1 row
     * with _id=32
     * with name="Gmail"
     * with data="com.google.android.gm"
     * with mimetype="package"
     * Given I have a table event
     * that contains 1 row
     * with _id=8
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 5 rows
     * and row 1 that contains 7 columns
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     * and projection_type=3 standing for Projections.VIEW_CONTACT
     * and row 2 should contains 2 columns
     * with title="Next actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 3 that contains 2 columns
     * with message="Add actions to start following up with this person"
     * and projection_type=1 standing for Projections.VIEW_MESSAGE
     * and row 4 should contains 2 columns
     * with title="Done actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 5 should contains 7 columns
     * with event_id=8
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=1462269810423
     * and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneActionEducatedUser() {
    }


    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with _id=15
     * android_contact_id=832
     * android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * contact_name=acticall
     * thumnail=null
     * emoicon=2130837600
     * Given I have a table action
     * that contain 1 row
     * with _id=5
     * and title="Give feedback"
     * and name="Thank you"
     * and sort_order=10
     * Given I have a table vector
     * that contains 1 row
     * with _id=32
     * with name="Gmail"
     * with data="com.google.android.gm"
     * with mimetype="package"
     * Given I have a table event
     * that contains 2 rows
     * and row 1
     * with _id=8
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=null
     * and row 2
     * with _id=9
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=false
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 6 rows
     * and row 1 that contains 7 columns
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     * and projection_type=3 standing for Projections.VIEW_CONTACT
     * and row 2 should contains 2 columns
     * with message="Long press to complete or uncomplete action"
     * and projection_type=1 standing for Projections.EDUCATE_USER
     * and row 3 should contains 2 columns
     * with title="Next actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 4 should contains 7 columns
     * with event_id=8
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=null
     * and projection_type=11 standing for Projections.VIEW_NEXT_ACTION
     * and row 5 should contains 2 columns
     * with title="Done actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 6 should contains 7 columns
     * with event_id=9
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=1462269810423
     * and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneAndUndoneActionUneducatedUser() {
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     * with _id=15
     * android_contact_id=832
     * android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * contact_name=acticall
     * thumnail=null
     * emoicon=2130837600
     * Given I have a table action
     * that contain 1 row
     * with _id=5
     * and title="Give feedback"
     * and name="Thank you"
     * and sort_order=10
     * Given I have a table vector
     * that contains 1 row
     * with _id=32
     * with name="Gmail"
     * with data="com.google.android.gm"
     * with mimetype="package"
     * Given I have a table event
     * that contains 2 rows
     * and row 1
     * with _id=8
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=null
     * and row 2
     * with _id=9
     * and contact_id=15
     * and action_id=5
     * and time_start=1462226400000
     * and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 5 rows
     * and row 1 that contains 7 columns
     * with _id=15
     * and android_contact_id=832
     * and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     * and contact_name=acticall
     * and thumnail=null
     * and emoicon=2130837600
     * and projection_type=3 standing for Projections.VIEW_CONTACT
     * and row 2 should contains 2 columns
     * with title="Next actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 3 should contains 7 columns
     * with event_id=8
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=null
     * and projection_type=11 standing for Projections.VIEW_NEXT_ACTION
     * and row 4 should contains 2 columns
     * with title="Done actions"
     * and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 5 should contains 7 columns
     * with event_id=9
     * and data="com.google.android.gm"
     * and mimetype="package"
     * and action_name="Thank you"
     * and time_start=1462226400000
     * and time_end=1462269810423
     * and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneAndUndoneActionEducatedUser() {
    }


    public void testCheckOpenCursors() {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "");
        Cursor[] cursors = new Cursor[2];
        Cursor cursor1 = mContext.getContentResolver().query(FriendForecastContract.VectorTable.CONTENT_URI,
                null, null, null, null);
        assertTrue(!cursor1.isClosed());
        cursor1.moveToFirst();
        assertTrue(!cursor1.isClosed());
        Cursor cursor2 = mContext.getContentResolver().query(FriendForecastContract.ActionTable
                        .CONTENT_URI,
                null, null, null, null);
        assertTrue(!cursor2.isClosed());
        cursor2.moveToFirst();
        assertTrue(!cursor2.isClosed());

        cursors[0] = cursor1;
        cursors[1] = cursor2;
        Cursor mergeCursor = new MergeCursor(cursors);


        assertTrue(!cursor1.isClosed());
        assertTrue(!cursor2.isClosed());
        assertTrue(!mergeCursor.isClosed());


        while (mergeCursor.moveToNext()) {

        }

        mergeCursor.close();
        assertTrue(mergeCursor.isClosed());
        assertTrue(cursor1.isClosed());
        assertTrue(cursor2.isClosed());

        //conclusion: closing the merge cursor close the cursors it is made off. All cursors
        // should be closed except those which are part of a merge cursor.
        // Cursor used in a loader don't explicitly call close. The close method is called by the
        // loader. After that the onReset method is called and it is good practice to call
        // swapCursor(null) to release ressources.

    }
}
