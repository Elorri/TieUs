package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.db.Projections;

/**
 * Created by Elorri on 08/05/2016.
 */
public class TestUtility extends AndroidTestCase {

    public static void printCursorHeader(Cursor cursor) {
        String header = "|";
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            header = header + cursor.getColumnName(i) + "|";
        }
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "header " + header);
    }

    public static void printCursorData(Cursor cursor) {
        String row = null;
        while (cursor.moveToNext()) {
            row = "|";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row = row + cursor.getString(i) + "|";
            }
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "row " + cursor.getPosition() + "" + row);
        }
    }

    public static void printMergedCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int viewType = cursor.getInt(cursor.getColumnIndex(Projections.COLUMN_PROJECTION_TYPE));
            printCursorHeader(cursor);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                if (viewType != cursor.getInt(cursor.getColumnIndex(Projections.COLUMN_PROJECTION_TYPE)))
                    printCursorHeader(cursor);
                printCursorData(cursor);
            }
        }
    }

}
