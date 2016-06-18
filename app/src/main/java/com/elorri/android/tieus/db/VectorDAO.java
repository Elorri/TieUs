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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 26/04/2016.
 * This class contains useful info for quering table Event
 */
public class VectorDAO {


    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.VectorTable.NAME +
            "(" + TieUsContract.VectorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TieUsContract.VectorTable.COLUMN_NAME + " TEXT NOT NULL, "
            + TieUsContract.VectorTable.COLUMN_DATA + " TEXT NOT NULL, "
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + " TEXT NOT NULL, "
            + "UNIQUE (" + TieUsContract.VectorTable.COLUMN_NAME + ") ON CONFLICT REPLACE,"
            + "UNIQUE (" + TieUsContract.VectorTable.COLUMN_DATA + ") ON CONFLICT REPLACE, "
            + "CONSTRAINT " + TieUsContract.VectorTable.MIMETYPE_CONSTRAINT + " check  ("
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + " = '"
            + TieUsContract.VectorTable.MIMETYPE_VALUE_PACKAGE + "' or ("
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + " = '"
            + TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE + "')))";


    public interface VectorQuery {

        int COL_ID = 0;
        int COL_VECTOR_NAME = 1;
        int COL_DATA = 2;
        int COL_MIMETYPE = 3;

        String SORT_ORDER = TieUsContract.VectorTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                TieUsContract.VectorTable._ID,
                TieUsContract.VectorTable.COLUMN_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_QUERY = {
                TieUsContract.VectorTable._ID,
                TieUsContract.VectorTable.COLUMN_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.VIEW_VECTOR_ITEM + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

    }

    public static ContentValues getContentValues(String name, String data, String mimetype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TieUsContract.VectorTable.COLUMN_NAME, name);
        contentValues.put(TieUsContract.VectorTable.COLUMN_DATA, data);
        contentValues.put(TieUsContract.VectorTable.COLUMN_MIMETYPE, mimetype);
        return contentValues;
    }


    public static Cursor getCursor(Context context, SQLiteDatabase db) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(MatrixCursors.getOneLineCursor(
                MatrixCursors.TitleQuery.PROJECTION,
                MatrixCursors.TitleQuery.VALUES,
                context.getResources().getString(R.string.select_vector)));
        cursors.add(db.query(TieUsContract.VectorTable.NAME,
                VectorQuery.PROJECTION_QUERY,
                null,
                null,
                null,
                null,
                VectorQuery.SORT_ORDER));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

}
