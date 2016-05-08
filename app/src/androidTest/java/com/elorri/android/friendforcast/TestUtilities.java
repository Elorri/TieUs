package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by Elorri on 07/05/2016.
 */
public class TestUtilities extends AndroidTestCase {
    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. ", valueCursor.moveToFirst());
        validateCurrentRecord(valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. ", idx == -1);
            String expectedValue = entry.getValue() != null ? entry.getValue().toString() : null;
            String cursorValue = valueCursor.getString(idx);
//            Log.e("FF", Thread.currentThread().getStackTrace()[2]
//                    + "columnName " + columnName
//                    + " expectedValue " + expectedValue
//                    + " cursorValue " + cursorValue);
            assertEquals("Error ", expectedValue, cursorValue);
        }
    }
}
