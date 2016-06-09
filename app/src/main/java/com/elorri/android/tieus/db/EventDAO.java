package com.elorri.android.tieus.db;

import android.content.ContentValues;

import com.elorri.android.tieus.data.TieUsContract;

/**
 * Created by Elorri on 11/04/2016.
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
        int COL_CONTACT_ID = 1;
        int COL_ACTION_ID = 2;
        int COL_VECTOR_ID = 2;
        int COL_TIME_START = 3;
        int COL_TIME_END = 4;

        String SELECTION = TieUsContract.ContactTable._ID + "=?";

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
