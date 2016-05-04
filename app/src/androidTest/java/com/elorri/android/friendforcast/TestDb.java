package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.database.MergeCursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 04/05/2016.
 */
public class TestDb extends AndroidTestCase {

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

    }
}
