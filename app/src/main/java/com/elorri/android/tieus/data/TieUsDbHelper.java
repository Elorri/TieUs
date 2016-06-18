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
package com.elorri.android.tieus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elorri.android.tieus.db.ActionDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.EventDAO;
import com.elorri.android.tieus.db.VectorDAO;
import com.elorri.android.tieus.extra.Status;

/**
 * Created by Elorri on 11/04/2016.
 * This class create the differents tables used to store data.
 * of the app
 */
public class TieUsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 5;

    static final String DATABASE_NAME = "tieus.db";
    private Context mContext;


    public TieUsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TieUs", "" + Thread.currentThread().getStackTrace()[2]);

        db.execSQL(ContactDAO.CREATE);
        db.execSQL(ActionDAO.CREATE);
        db.execSQL(EventDAO.CREATE);
        db.execSQL(VectorDAO.CREATE);

        bulkInsert(db, ActionDAO.getStartData());

    }

    private void bulkInsert(SQLiteDatabase db, ContentValues[] startData) {
        for (ContentValues values : startData) {
            db.insert(TieUsContract.ActionTable.NAME, null, values);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.ContactTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.ActionTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.EventTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.VectorTable.NAME);
        Status.setDoneActionsAware(mContext, Status.DONE_ACTION_AWARE_FALSE);
        Status.setDeleteActionsAware(mContext, Status.DELETE_ACTION_AWARE_FALSE);
        Status.setLastMessageIdxBg(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
        //Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
        Status.setLastUserSatisfactionsConfirmAware(mContext, 0);
        Status.setLastNotificationTimestamp(mContext, 0);
        Status.setSyncStatus(mContext, Status.SYNC_NO_INTERNET);
        Status.setSyncStatsContactAdded(mContext, 0);
        Status.setSyncStatsContactUpdated(mContext, 0);
        Status.setSyncStatsContactDeleted(mContext, 0);
        Status.setFirebaseStatsSent(mContext, false);
        onCreate(sqLiteDatabase);
    }


}