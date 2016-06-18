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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elorri.android.tieus.db.ContactActionVectorEventDAO;
import com.elorri.android.tieus.extra.DateUtils;

/**
 * Created by Elorri on 12/04/2016.
 * This class allow for query, update, delete the differents tables used by the app to persist
 * the data.
 */
public class TieUsProvider extends ContentProvider {

    // Uris for getting data directly taken from a table
    private static final int TABLE_CONTACT = 500; //will match content://com.elorri.android.tieus/contact/
    private static final int TABLE_ACTION = 501; //will match content://com.elorri.android.tieus/action/
    private static final int TABLE_EVENT = 502; //will match content://com.elorri.android.tieus/event/
    private static final int TABLE_VECTOR = 503; //will match content://com.elorri.android.tieus/vector/

    // Uris for getting data that is a combinaison of data taken in different tables, and
    // organised in 1 cursor.
    private static final int DATA_WIDGET = 50; //content://com.elorri.android.tieus/widget/
    private static final int DATA_MAIN = 100; //content://com.elorri.android.tieus/main/
    private static final int DATA_DETAIL = 101; //content://com.elorri.android.tieus/detail/15
    private static final int DATA_ADD_ACTION_SELECT_ACTION = 102; //content://com.elorri.android.tieus/add_action/
    private static final int DATA_ADD_ACTION_SELECT_VECTOR = 103; //content://com.elorri.android.tieus/add_action/12
    private static final int DATA_ADD_ACTION_VALIDATE = 105; //content://com.elorri.android.tieus/add_action/12/15/1464472800000


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String LOG_TAG = TieUsProvider.class.getSimpleName();

    private TieUsDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.WidgetData.PATH_WIDGET + "/#", DATA_WIDGET);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.MainData.PATH_MAIN + "/#", DATA_MAIN);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.DetailData.PATH_DETAIL + "/#", DATA_DETAIL);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.AddActionData.PATH_ADD_ACTION, DATA_ADD_ACTION_SELECT_ACTION);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.AddActionData.PATH_ADD_ACTION + "/#", DATA_ADD_ACTION_SELECT_VECTOR);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY,
                TieUsContract.AddActionData.PATH_ADD_ACTION + "/#/#/#", DATA_ADD_ACTION_VALIDATE);

        matcher.addURI(TieUsContract.CONTENT_AUTHORITY, TieUsContract.ContactTable
                .PATH_CONTACT, TABLE_CONTACT);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY, TieUsContract.ActionTable
                .PATH_ACTION, TABLE_ACTION);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY, TieUsContract.EventTable
                .PATH_EVENT, TABLE_EVENT);
        matcher.addURI(TieUsContract.CONTENT_AUTHORITY, TieUsContract.VectorTable
                .PATH_VECTOR, TABLE_VECTOR);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new TieUsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case DATA_WIDGET: {
                Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_WIDGET uri " + uri);
                long now = TieUsContract.WidgetData.getTimeFromUri(uri);
                String todayStart = String.valueOf(DateUtils.setZeroDay(now));
                String tomorrowStart = String.valueOf(DateUtils.addDay(1, DateUtils.setZeroDay(now)));
                cursor = db.query("(" + ContactActionVectorEventDAO.TodayPeopleQuery.SELECT_WITH_VIEWTYPE + ")",
                        null,
                        null,
                        new String[]{todayStart, tomorrowStart},
                        null,
                        null,
                        null);
                break;
            }
            case DATA_MAIN: {
                Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_MAIN uri " + uri);
                long now = TieUsContract.MainData.getTimeFromUri(uri);
                cursor = com.elorri.android.tieus.data.MainData.getCursor(getContext(), db, now, selection, selectionArgs);
                break;
            }
            case DATA_DETAIL:
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_DETAIL uri " + uri);
                String contactId = TieUsContract.DetailData.getContactIdFromUri(uri);
                cursor = DetailData.getCursor(getContext(), db, contactId);
                break;
            case DATA_ADD_ACTION_SELECT_ACTION:
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_SELECT_ACTION uri " + uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_SELECT_ACTION,
                        null, null, null);
                break;
            case DATA_ADD_ACTION_SELECT_VECTOR: {
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_SELECT_VECTOR uri " + uri);
                String actionId = TieUsContract.AddActionData.getActionIdFromSelectVectorUri(uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_SELECT_VECTOR,
                        actionId, null, null);
            }
            break;
            case DATA_ADD_ACTION_VALIDATE: {
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_VALIDATE uri " + uri);
                String actionId = TieUsContract.AddActionData.getActionIdFromSelectValidateUri(uri);
                String vectorId = TieUsContract.AddActionData.getVectorIdFromSelectValidateUri(uri);
                String timeStart = TieUsContract.AddActionData.getTimeStartIdFromSelectValidateUri(uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_VALIDATE,
                        actionId, vectorId, timeStart);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursor.getCount()"
                        + cursor.getCount());
            }
            break;
            case TABLE_CONTACT:
                Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "TABLE_CONTACT uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        TieUsContract.ContactTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_ACTION:
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "TABLE_ACTION uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        TieUsContract.ActionTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_VECTOR:
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "TABLE_VECTOR uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        TieUsContract.VectorTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_EVENT:
                Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "TABLE_EVENT uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        TieUsContract.EventTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TABLE_CONTACT: {
                long _id = db.insert(TieUsContract.ContactTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = TieUsContract.ContactTable.buildContactUri(_id);
                    Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_CONTACT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_EVENT: {
                long _id = db.insert(TieUsContract.EventTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = TieUsContract.EventTable.buildEventUri(_id);
                    Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_EVENT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_ACTION: {
                long _id = db.insert(TieUsContract.ActionTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = TieUsContract.ActionTable.buildActionUri(_id);
                    Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_EVENT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_VECTOR: {
                long _id = db.insert(TieUsContract.VectorTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = TieUsContract.VectorTable.buildVectorUri(_id);
                    Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_VECTORS " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted but there is no null where clause here
        if (null == selection) selection = "1";
        switch (match) {
            case TABLE_CONTACT:
                rowsDeleted = db.delete(TieUsContract.ContactTable.NAME, selection, selectionArgs);
                break;
            case TABLE_VECTOR:
                rowsDeleted = db.delete(TieUsContract.VectorTable.NAME, selection, selectionArgs);
                break;
            case TABLE_EVENT:
                rowsDeleted = db.delete(TieUsContract.EventTable.NAME, selection, selectionArgs);
                break;
            case TABLE_ACTION:
                rowsDeleted = db.delete(TieUsContract.ActionTable.NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TABLE_CONTACT:
                rowsUpdated = db.update(TieUsContract.ContactTable.NAME, values,
                        selection,
                        selectionArgs);
                break;
            case TABLE_EVENT:
                rowsUpdated = db.update(TieUsContract.EventTable.NAME, values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case TABLE_CONTACT:
                returnCount = insertInBulk(TieUsContract.ContactTable.NAME, values);
                break;
            case TABLE_ACTION:
                returnCount = insertInBulk(TieUsContract.ActionTable.NAME, values);
                break;
            case TABLE_VECTOR:
                returnCount = insertInBulk(TieUsContract.VectorTable.NAME, values);
                break;
            case TABLE_EVENT:
                returnCount = insertInBulk(TieUsContract.EventTable.NAME, values);
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d("TieUs", returnCount + "rows inserted in " + uri);
        return returnCount;
    }

    private int insertInBulk(String tableName, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insertWithOnConflict(tableName, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
            return returnCount;
        } finally {
            db.endTransaction();
        }
    }
}
