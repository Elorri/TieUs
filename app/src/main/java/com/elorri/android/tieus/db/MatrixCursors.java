/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.tieus.db;

import android.database.Cursor;
import android.database.MatrixCursor;

/**
 * Created by Elorri on 10/05/2016.
 * Useful class to create cursor without having to question db
 */
public class MatrixCursors {

    public static Cursor getOneLineCursor(String[] projection, String[] values, String firstValue) {
        MatrixCursor cursor = new MatrixCursor(projection);
        values[0]=firstValue;
        cursor.addRow(values);
        return cursor;
    }

    public interface EmptyCursorMessageQuery {
        int COL_MESSAGE=0;
        String COLUMN_EMPTY_CURSOR = "_id";

        String[] PROJECTION = {
                EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] VALUES = {
                null,
                String.valueOf(ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE)
        };
    }



    public interface ConfirmMessageQuery {
        int COL_MESSAGE=0;
        String COLUMN_CONFIRM_MESSAGE = "_id";
        String[] PROJECTION = {
                ConfirmMessageQuery.COLUMN_CONFIRM_MESSAGE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] VALUES = {
                null,
                String.valueOf(ViewTypes.VIEW_CONFIRM_MESSAGE)
        };
    }


    public interface TitleQuery {
        int COL_TITLE=0;
        String COLUMN_TITLE = "_id";
        String[] PROJECTION = {
                TitleQuery.COLUMN_TITLE,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] VALUES = {
                null,
                String.valueOf(ViewTypes.VIEW_TITLE)
        };
    }

    public interface MessageQuery {
        int COL_MESSAGE=0;
        String COLUMN_MESSAGE = "_id";
        String[] PROJECTION = {
                MessageQuery.COLUMN_MESSAGE,
                ViewTypes.COLUMN_VIEWTYPE
        };
        String[] VALUES = {
                null,
                String.valueOf(ViewTypes.VIEW_MESSAGE)
        };
    }



}
