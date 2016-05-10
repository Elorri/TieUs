package com.elorri.android.friendforcast.db;

import android.database.Cursor;
import android.database.MatrixCursor;

/**
 * Created by Elorri on 10/05/2016.
 */
public class MatrixCursors {


    public static Cursor getOneLineCursor(String[] projection, String[] values) {
        MatrixCursor cursor = new MatrixCursor(projection);
        cursor.addRow(values);
        return cursor;
    }

    public static Cursor getOneLineCursor(String[] projection, String[] values, String firstValue) {
        MatrixCursor cursor = new MatrixCursor(projection);
        values[0]=firstValue;
        cursor.addRow(values);
        return cursor;
    }

    public interface EmptyCursorMessageQuery {
        String COLUMN_EMPTY_CURSOR = "_id";

        String[] PROJECTION = {
                EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] VALUES = {
                "an empty cursor",
                String.valueOf(ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE)
        };
    }

    public interface EducateMessageQuery {
        String COLUMN_EDUCATE_MESSAGE = "_id";
        String[] PROJECTION = {
                EducateMessageQuery.COLUMN_EDUCATE_MESSAGE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] VALUES = {
                "an educationnal message",
                String.valueOf(ViewTypes.VIEW_EDUCATE_MESSAGE)
        };
    }


    public interface TitleQuery {
        String COLUMN_TITLE = "_id";
        String[] PROJECTION = {
                TitleQuery.COLUMN_TITLE,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] VALUES = {
                "a title",
                String.valueOf(ViewTypes.VIEW_TITLE)
        };
    }

    public interface MessageQuery {
        String COLUMN_MESSAGE = "_id";
        String[] PROJECTION = {
                MessageQuery.COLUMN_MESSAGE,
                ViewTypes.VIEW_MESSAGE + " as " + ViewTypes.COLUMN_VIEWTYPE
        };
    }
}
