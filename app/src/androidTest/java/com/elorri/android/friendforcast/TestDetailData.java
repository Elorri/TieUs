package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.database.MergeCursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 04/05/2016.
 */
public class TestDetailData extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
        super.setUp();
    }


    @Override
    protected void tearDown() throws Exception {
        Log.e("FF", Thread.currentThread().getStackTrace()[2]+"");
        super.tearDown();
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     * Given I have a table event that does NOT contains contact_id=15
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 2 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 that contains 2 columns
     *      with message="Add actions to start following up with this person"
     *      and projection_type=1 standing for Projections.VIEW_MESSAGE
     */
    public void testDetailNoActions() {
        TestGivens testGivens=new TestGivens();
        testGivens.test_I_have_a_table_contact_that_contains_1_row(mContext);
    }


    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 1 row
     *      with _id=8
     *      and contact_id=15
     *      and action_id=5
     *      and time_start=1462226400000
     *      and time_end=null
     * Given a Tools.getMarkActionFeatureStatus=false
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 3 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 should contains 2 columns
     *      with message="Long press to complete or uncomplete action"
     *      and projection_type=1 standing for Projections.EDUCATE_USER
     * and row 3 should contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=null
     *      and projection_type=10 standing for Projections.VIEW_NEXT_ACTION
     */
    public void testDetailUndoneActionUneducatedUser() {
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 1 row
     *      with _id=8
     *      and contact_id=15
     *      and action_id=5
     *      and time_start=1462226400000
     *      and time_end=null
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 2 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 row that contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=null
     *      and projection_type=10 standing for Projections.VIEW_NEXT_ACTION
     */
    public void testDetailUndoneActionEducatedUser() {
    }





    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 1 row
     *      with _id=8
     *      and contact_id=15
     *      and action_id=5
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=false
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 6 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 should contains 2 columns
     *      with message="Long press to complete or uncomplete action"
     *      and projection_type=1 standing for Projections.EDUCATE_USER
     * and row 3 should contains 2 columns
     *      with title="Next actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 4 that contains 2 columns
     *      with message="Add actions to start following up with this person"
     *      and projection_type=1 standing for Projections.VIEW_MESSAGE
     * and row 5 should contains 2 columns
     *      with title="Done actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 6 should contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     *      and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneActionUneducatedUser() {
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 1 row
     *      with _id=8
     *      and contact_id=15
     *      and action_id=5
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 5 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 should contains 2 columns
     *      with title="Next actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 3 that contains 2 columns
     *      with message="Add actions to start following up with this person"
     *      and projection_type=1 standing for Projections.VIEW_MESSAGE
     * and row 4 should contains 2 columns
     *      with title="Done actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 5 should contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     *      and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneActionEducatedUser() {
    }


    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 2 rows
     *      and row 1
     *          with _id=8
     *          and contact_id=15
     *          and action_id=5
     *          and time_start=1462226400000
     *          and time_end=null
     *      and row 2
     *          with _id=9
     *          and contact_id=15
     *          and action_id=5
     *          and time_start=1462226400000
     *          and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=false
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 6 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 should contains 2 columns
     *      with message="Long press to complete or uncomplete action"
     *      and projection_type=1 standing for Projections.EDUCATE_USER
     * and row 3 should contains 2 columns
     *      with title="Next actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 4 should contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=null
     *      and projection_type=11 standing for Projections.VIEW_NEXT_ACTION
     * and row 5 should contains 2 columns
     *      with title="Done actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 6 should contains 7 columns
     *      with event_id=9
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     *      and projection_type=11 standing for Projections.VIEW_DONE_ACTION
     */
    public void testDetailDoneAndUndoneActionUneducatedUser() {
    }

    /**
     * Given I have a Context mContext
     * Given I have a table contact that contain 1 row
     *      with _id=15
     *      android_contact_id=832
     *      android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      contact_name=acticall
     *      thumnail=null
     *      emoicon=2130837600
     * Given I have a table action
     *      that contain 1 row
     *      with _id=5
     *      and title="Give feedback"
     *      and name="Thank you"
     *      and sort_order=10
     * Given I have a table vector
     *      that contains 1 row
     *      with _id=32
     *      with name="Gmail"
     *      with data="com.google.android.gm"
     *      with mimetype="package"
     * Given I have a table event
     *      that contains 2 rows
     *      and row 1
     *          with _id=8
     *          and contact_id=15
     *          and action_id=5
     *          and time_start=1462226400000
     *          and time_end=null
     *      and row 2
     *          with _id=9
     *          and contact_id=15
     *          and action_id=5
     *          and time_start=1462226400000
     *          and time_end=1462269810423
     * Given a Tools.getMarkActionFeatureStatus=true
     * Given I have an uri = content://com.elorri.android.communication/detail/15
     * When I call mContext.getContentResolver().query(uri, ...)
     * Then I should get a Cursor
     * with 5 rows
     * and row 1 that contains 7 columns
     *      with _id=15
     *      and android_contact_id=832
     *      and android_contact_lookup_key=298i5.3552i264b0e968b8a42ff
     *      and contact_name=acticall
     *      and thumnail=null
     *      and emoicon=2130837600
     *      and projection_type=3 standing for Projections.VIEW_EMOICON
     * and row 2 should contains 2 columns
     *      with title="Next actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * with row 3 should contains 7 columns
     *      with event_id=8
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=null
     *      and projection_type=11 standing for Projections.VIEW_NEXT_ACTION
     * and row 4 should contains 2 columns
     *      with title="Done actions"
     *      and projection_type=1 standing for Projections.VIEW_TITLE
     * and row 5 should contains 7 columns
     *      with event_id=9
     *      and data="com.google.android.gm"
     *      and mimetype="package"
     *      and action_name="Thank you"
     *      and time_start=1462226400000
     *      and time_end=1462269810423
     *      and projection_type=11 standing for Projections.VIEW_DONE_ACTION
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

        cursors[0]=cursor1;
        cursors[1]=cursor2;
        Cursor mergeCursor=new MergeCursor(cursors);



        assertTrue(!cursor1.isClosed());
        assertTrue(!cursor2.isClosed());
        assertTrue(!mergeCursor.isClosed());


        while (mergeCursor.moveToNext()){

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
