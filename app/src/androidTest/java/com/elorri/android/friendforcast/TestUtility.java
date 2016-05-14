package com.elorri.android.friendforcast;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
import com.elorri.android.friendforcast.db.ViewTypes;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Elorri on 08/05/2016.
 */
public class TestUtility extends AndroidTestCase {

    MatrixCursor mCursor1;
    MatrixCursor mCursor2;
    MatrixCursor mEmptyCursor;
    Cursor mMergedCursor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String[] cursor1_columns = {"_id", "col1", "col2", ViewTypes.COLUMN_VIEWTYPE};
        mCursor1 = new MatrixCursor(cursor1_columns);
        mCursor1.addRow(new Object[]{1, "data1", null, 1});
        mCursor1.addRow(new Object[]{2, "data3", "data4", 1});

        String[] cursor2_columns = {"_id", "col1", "col2", ViewTypes.COLUMN_VIEWTYPE};
        mCursor2 = new MatrixCursor(cursor2_columns);
        mCursor2.addRow(new Object[]{1, "data1", "data2", 2});

        String[] emptyCursor_columns = {"_id", "col1", "col2"};
        mEmptyCursor = new MatrixCursor(emptyCursor_columns);

        mMergedCursor = new MergeCursor(new Cursor[]{mCursor1, mCursor2});

    }

    public static String getCursorHeaderString(Cursor cursor) {
        String header = "header |";
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            header = header + cursor.getColumnName(i) + "|";
        }
        return header + "\n";
    }

    public static String getCursorRowString(Cursor cursor) {
        String row = "row |";
        String cell = null;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            cell = cursor.getString(i) == null ? "null" : cursor.getString(i);
            row = row + cell + "|";
        }
        return row + "\n";
    }

    public static String getCursorString(Cursor cursor) {
        cursor.moveToPosition(-1);
        String cursorString = "\n";
        if (cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE) != -1) {
            if (cursor.moveToFirst()) {
                int viewType = cursor.getInt(cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE));
                cursorString = cursorString + getCursorHeaderString(cursor);
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    if (viewType != cursor.getInt(cursor.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE)))
                        cursorString = cursorString + getCursorHeaderString(cursor);
                    cursorString = cursorString + getCursorRowString(cursor);
                }
            }
        } else {
            cursorString = cursorString + getCursorHeaderString(cursor);
            while (cursor.moveToNext()) {
                cursorString = cursorString + getCursorRowString(cursor);
            }
        }
        return cursorString;
    }

    public static Cursor getCursorFromString(String cursorString) {
        String[] lines = cursorString.split("\\n");
        ArrayList<MatrixCursor> cursorsList = new ArrayList<>();
        MatrixCursor current = null;
        for (String line : lines) {
            if (line.length() > 5) {
                if (line.startsWith("header")) {
                    current = new MatrixCursor(line.substring(8).split("\\|"));
                    cursorsList.add(current);
                } else {
                    current.addRow(line.substring(5).split("\\|"));
                }
            }
        }
        return new MergeCursor(convertToCursorArray(cursorsList));
    }

    private static Cursor[] convertToCursorArray(ArrayList<MatrixCursor> cursorsList) {
        Cursor[] cursors = new Cursor[cursorsList.size()];
        for (int i = 0; i < cursorsList.size(); i++) {
            cursors[i] = cursorsList.get(i);
        }
        return cursors;
    }

    private static ContentValues fromCursorRowToContentValues(Cursor cursor) {
        ContentValues values = new ContentValues(cursor.getColumnCount());
        String value;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            value = cursor.getString(i).equals("null") ? null : cursor.getString(i);
            values.put(cursor.getColumnName(i), value);
        }
        return values;
    }


    public static ContentValues[] fromCursorToContentValues(Cursor cursor) {
        ContentValues[] values = new ContentValues[cursor.getCount()];
        cursor.moveToPosition(-1);
        int i = 0;
        while (cursor.moveToNext()) {
            values[i] = fromCursorRowToContentValues(cursor);
            i++;
        }
        return values;
    }

    public void test_getCursorHeaderString() {
        assertEquals("header |_id|col1|" + ViewTypes.COLUMN_VIEWTYPE + "|\n",
                getCursorHeaderString(mCursor1));
    }

    public void test_getCursorRowString() {
        mCursor1.moveToFirst();
        assertEquals("row |1|data1|null|1|\n", getCursorRowString(mCursor1));
    }


    public void test_getCursorString() {
        String expected = "\n"
                + "header |_id|col1|projection_type|\n"
                + "row |1|data1|1|\n"
                + "row |2|data3|1|\n"
                + "header |_id|col1|col2|projection_type|\n"
                + "row |1|data1|data2|2|\n";
        assertEquals(expected, getCursorString(mMergedCursor));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(mMergedCursor));
    }

    public void test_getCursorFromString() {
        String cursorString = "\n"
                + "header |_id|col1|projection_type|\n"
                + "row |1|data1|1|\n"
                + "row |2|data3|1|\n"
                + "header |_id|col1|col2|projection_type|\n"
                + "row |1|data1|data2|2|\n";
        Cursor cursor = getCursorFromString(cursorString);
        assertEquals(cursorString, getCursorString(cursor));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
    }


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


    public static String getCursorHeaderString(String[] projection) {
        String header = "header |";
        for (String title : projection) {
            header = header + title + "|";
        }
        return header + "\n";
    }


    public void test_ToolsGetCursorWithProperties() {

        String aTitle = "aTitle";
        String anEmptyCursorMessage = "anEmptyCursorMessage";


        String cursorEmptyCursorStringWithoutMessageWithoutTitle = "\nheader |_id|col1|col2|\n";
        Cursor cursor = Tools.addDisplayProperties(mEmptyCursor, false, aTitle, false,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithoutMessageWithoutTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mEmptyCursor, false, aTitle, false,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithoutMessageWithoutTitle" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithoutMessageWithoutTitle, getCursorString(cursor));


        String cursorEmptyCursorStringWithMessageWithoutTitle = "\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + anEmptyCursorMessage + "|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";
        cursor = Tools.addDisplayProperties(mEmptyCursor, false, aTitle, true,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithMessageWithoutTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mEmptyCursor, false, aTitle, true,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithMessageWithoutTitle" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithMessageWithoutTitle, getCursorString(cursor));


        String cursorEmptyCursorStringWithoutMessageWithTitle = "\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + aTitle + "|" + ViewTypes.VIEW_TITLE + "|\n";
        cursor = Tools.addDisplayProperties(mEmptyCursor, true, aTitle, false,
                anEmptyCursorMessage, true);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithoutMessageWithTitle" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithoutMessageWithTitle, getCursorString(cursor));


        String cursorEmptyCursorStringWithMessageWithTitle = "\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + aTitle + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE
                + "|\n"
                + "row |" + anEmptyCursorMessage + "|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";
        cursor = Tools.addDisplayProperties(mEmptyCursor, true, aTitle, true,
                anEmptyCursorMessage, true);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithMessageWithTitle" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithMessageWithTitle, getCursorString(cursor));


        String cursorEmptyCursorStringWithoutMessageWithTitleDisplayTitleIfListEmptyFalse = "\n"
                + "header |_id|col1|col2|\n";
        cursor = Tools.addDisplayProperties(mEmptyCursor, true, aTitle, false,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithoutMessageWithTitleDisplayTitleIfListEmptyFalse" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithoutMessageWithTitleDisplayTitleIfListEmptyFalse,
                getCursorString(cursor));


        String cursorEmptyCursorStringWithMessageWithTitleDisplayTitleIfListEmptyFalse = "\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE
                + "|\n"
                + "row |" + anEmptyCursorMessage + "|" + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";
        cursor = Tools.addDisplayProperties(mEmptyCursor, true, aTitle, true,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorEmptyCursorStringWithMessageWithTitleDisplayTitleIfListEmptyFalse" + getCursorString(cursor));
        assertEquals(cursorEmptyCursorStringWithMessageWithTitleDisplayTitleIfListEmptyFalse, getCursorString(cursor));


        String cursorFullCursorStringWithTitle = "\n"
                + "header |"
                + MatrixCursors.TitleQuery.COLUMN_TITLE + "|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |" + aTitle + "|" + ViewTypes.VIEW_TITLE + "|\n"
                + "header |_id|col1|col2|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |1|data1|data2|2|\n";
        cursor = Tools.addDisplayProperties(mCursor2, true, aTitle, true,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, true, aTitle, false,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, true, aTitle, true,
                anEmptyCursorMessage, false);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, true, aTitle, false,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorFullCursorStringWithTitle" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithTitle, getCursorString(cursor));


        String cursorFullCursorStringWithoutTitle = "\n"
                + "header |_id|col1|col2|" + ViewTypes.COLUMN_VIEWTYPE + "|\n"
                + "row |1|data1|data2|2|\n";

        cursor = Tools.addDisplayProperties(mCursor2, false, aTitle, true,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithoutTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, false, aTitle, false,
                anEmptyCursorMessage, true);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithoutTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, false, aTitle, true,
                anEmptyCursorMessage, false);
        //Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithoutTitle, getCursorString(cursor));

        cursor = Tools.addDisplayProperties(mCursor2, false, aTitle, false,
                anEmptyCursorMessage, false);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursorFullCursorStringWithoutTitle" + getCursorString(cursor));
        assertEquals(cursorFullCursorStringWithoutTitle, getCursorString(cursor));


    }

    public static void test_printSqlQueries() {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT"
                + ContactActionVectorEventDAO.JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "SELECT_UNMANAGED_PEOPLE"
                + ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT_UNMANAGED_PEOPLE);

    }

}
