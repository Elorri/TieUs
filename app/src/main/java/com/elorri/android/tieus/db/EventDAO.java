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

import com.elorri.android.tieus.data.TieUsContract;

/**
 * Created by Elorri on 11/04/2016.
 * This class contains useful info for quering table Event
 */
public class EventDAO {

    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.EventTable.NAME +
            "(" + TieUsContract.EventTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + " INTEGER NOT NULL,"
            + TieUsContract.EventTable.COLUMN_ACTION_ID + " INTEGER NOT NULL,"
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + " INTEGER NOT NULL,"
            + TieUsContract.EventTable.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + TieUsContract.EventTable.COLUMN_TIME_END + " INTEGER, "
            + "FOREIGN KEY (" + TieUsContract.EventTable.COLUMN_CONTACT_ID + ") "
            + "REFERENCES " + TieUsContract.ContactTable.NAME
            + "(" + TieUsContract.ContactTable._ID + "), "
            + "FOREIGN KEY (" + TieUsContract.EventTable.COLUMN_ACTION_ID + ") "
            + "REFERENCES " + TieUsContract.ActionTable.NAME
            + "(" + TieUsContract.ActionTable._ID + "), "
            + "FOREIGN KEY (" + TieUsContract.EventTable.COLUMN_VECTOR_ID + ") "
            + "REFERENCES " + TieUsContract.VectorTable.NAME
            + "(" + TieUsContract.VectorTable._ID + "), "
            + "UNIQUE (" + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
            + TieUsContract.EventTable.COLUMN_ACTION_ID + ", "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.EventTable.COLUMN_TIME_END + ") ON CONFLICT REPLACE)";


    public interface EventQuery {

        int COL_ID = 0;

        String[] PROJECTION = {
                TieUsContract.EventTable._ID,
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.EventTable.COLUMN_ACTION_ID,
                TieUsContract.EventTable.COLUMN_VECTOR_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END
        };

    }


    public static ContentValues getContentValues(String contactId, String actionId, String
            vectorId, long date) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.EventTable.COLUMN_CONTACT_ID, contactId);
        values.put(TieUsContract.EventTable.COLUMN_ACTION_ID, actionId);
        values.put(TieUsContract.EventTable.COLUMN_VECTOR_ID, vectorId);
        values.put(TieUsContract.EventTable.COLUMN_TIME_START, date);
        return values;
    }

    public static ContentValues getContentValues(Long timeEnd) {
        ContentValues values = new ContentValues();
        values.put(TieUsContract.EventTable.COLUMN_TIME_END, timeEnd);
        return values;
    }
}
