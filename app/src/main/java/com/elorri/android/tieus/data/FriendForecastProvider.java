package com.elorri.android.tieus.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elorri.android.tieus.extra.Tools;

/**
 * Created by Elorri on 12/04/2016.
 */
public class FriendForecastProvider extends ContentProvider {

    static final int DATA_BOARD = 100; //content://com.elorri.android.communication/board/
    static final int DATA_DETAIL = 101; //content://com.elorri.android.communication/detail/15
    static final int DATA_ADD_ACTION_SELECT_ACTION = 102; //content://com.elorri.android.communication/add_action/
    static final int DATA_ADD_ACTION_SELECT_VECTOR = 103; //content://com.elorri.android.communication/add_action/12
    static final int DATA_ADD_ACTION_VALIDATE = 105; //content://com.elorri.android.communication/add_action/12/15/1464472800000

    static final int TABLE_CONTACT = 500; //will match content://com.elorri.android.communication/contact/
    static final int TABLE_ACTION = 501; //will match content://com.elorri.android.communication/action/
    static final int TABLE_EVENT = 502; //will match content://com.elorri.android.communication/event/
    static final int TABLE_VECTOR = 503; //will match content://com.elorri.android.communication/vector/
    static final int TABLE_CONTACT_VECTORS = 504; //will match content://com.elorri.android.communication/contact_vectors/


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private FriendForecastDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY,
                FriendForecastContract.BoardData.PATH_BOARD + "/#", DATA_BOARD);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY,
                FriendForecastContract.DetailData.PATH_DETAIL + "/#", DATA_DETAIL);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY,
                FriendForecastContract.AddActionData.PATH_ADD_ACTION, DATA_ADD_ACTION_SELECT_ACTION);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY,
                FriendForecastContract.AddActionData.PATH_ADD_ACTION + "/#", DATA_ADD_ACTION_SELECT_VECTOR);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY,
                FriendForecastContract.AddActionData.PATH_ADD_ACTION + "/#/#/#", DATA_ADD_ACTION_VALIDATE);

        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY, FriendForecastContract.ContactTable
                .PATH_CONTACT, TABLE_CONTACT);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY, FriendForecastContract.ActionTable
                .PATH_ACTION, TABLE_ACTION);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY, FriendForecastContract.EventTable
                .PATH_EVENT, TABLE_EVENT);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY, FriendForecastContract.VectorTable
                .PATH_VECTOR, TABLE_VECTOR);
        matcher.addURI(FriendForecastContract.CONTENT_AUTHORITY, FriendForecastContract
                .ContactVectorsTable.PATH_CONTACT_VECTORS, TABLE_CONTACT_VECTORS);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FriendForecastDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "thread " + Tools
                .thread());
        Cursor cursor = null;
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case DATA_BOARD:
                Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "DATA_BOARD " +
                        "uri " + uri);
                long now=FriendForecastContract.BoardData.getTimeFromUri(uri);
                cursor = BoardData.getCursor(getContext(), db, now, selection, selectionArgs);
                break;
            case DATA_DETAIL:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "DATA_DETAIL uri " + uri);
                String contactId = FriendForecastContract.DetailData.getContactIdFromUri(uri);
                cursor = DetailData.getCursor(getContext(), db, contactId);
                break;
            case DATA_ADD_ACTION_SELECT_ACTION:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_SELECT_ACTION uri " + uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_SELECT_ACTION,
                        null, null,  null);
                break;
            case DATA_ADD_ACTION_SELECT_VECTOR: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_SELECT_VECTOR uri " + uri);
                String actionId = FriendForecastContract.AddActionData.getActionIdFromSelectVectorUri(uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_SELECT_VECTOR,
                        actionId, null,  null);
            }
            break;
            case DATA_ADD_ACTION_VALIDATE: {
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "DATA_ADD_ACTION_VALIDATE uri " + uri);
                String actionId = FriendForecastContract.AddActionData.getActionIdFromSelectValidateUri(uri);
                String vectorId = FriendForecastContract.AddActionData.getVectorIdFromSelectValidateUri(uri);
                String timeStart = FriendForecastContract.AddActionData.getTimeStartIdFromSelectValidateUri(uri);
                cursor = AddActionData.getCursor(getContext(), db, AddActionData.ACTION_VALIDATE,
                        actionId, vectorId, timeStart);
                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "cursor.getCount()"
                        +cursor.getCount());
            }
            break;
            case TABLE_CONTACT:
                Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "TABLE_CONTACT uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        FriendForecastContract.ContactTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_ACTION:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "TABLE_ACTION uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        FriendForecastContract.ActionTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_VECTOR:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "TABLE_VECTOR uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        FriendForecastContract.VectorTable.NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TABLE_EVENT:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "TABLE_EVENT uri " + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        FriendForecastContract.EventTable.NAME,
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
                long _id = db.insert(FriendForecastContract.ContactTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = FriendForecastContract.ContactTable.buildContactUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_CONTACT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_EVENT: {
                long _id = db.insert(FriendForecastContract.EventTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = FriendForecastContract.EventTable.buildEventUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_EVENT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_ACTION: {
                long _id = db.insert(FriendForecastContract.ActionTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = FriendForecastContract.ActionTable.buildActionUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_EVENT " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_VECTOR: {
                long _id = db.insert(FriendForecastContract.VectorTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = FriendForecastContract.VectorTable.buildVectorUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_VECTORS " + _id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TABLE_CONTACT_VECTORS: {
                long _id = db.insert(FriendForecastContract.ContactVectorsTable.NAME, null, values);
                if (_id > 0) {
                    returnUri = FriendForecastContract.ContactVectorsTable.buildContactVectorsUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_CONTACT_VECTORS " + _id);
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
                rowsDeleted = db.delete(FriendForecastContract.ContactTable.NAME, selection, selectionArgs);
                break;
            case TABLE_VECTOR:
                rowsDeleted = db.delete(FriendForecastContract.VectorTable.NAME, selection, selectionArgs);
                break;
            case TABLE_CONTACT_VECTORS:
                rowsDeleted = db.delete(FriendForecastContract.ContactVectorsTable.NAME, selection,
                        selectionArgs);
                break;
            case TABLE_EVENT:
                rowsDeleted = db.delete(FriendForecastContract.EventTable.NAME, selection, selectionArgs);
                break;
            case TABLE_ACTION:
                rowsDeleted = db.delete(FriendForecastContract.ActionTable.NAME, selection, selectionArgs);
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
                rowsUpdated = db.update(FriendForecastContract.ContactTable.NAME, values,
                        selection,
                        selectionArgs);
                break;
            case TABLE_EVENT:
                rowsUpdated = db.update(FriendForecastContract.EventTable.NAME, values,
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
        int returnCount = 0;
        switch (match) {
            case TABLE_CONTACT:
                returnCount = insertInBulk(FriendForecastContract.ContactTable.NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TABLE_ACTION:
                returnCount = insertInBulk(FriendForecastContract.ActionTable.NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TABLE_VECTOR:
                returnCount = insertInBulk(FriendForecastContract.VectorTable.NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TABLE_EVENT:
                returnCount = insertInBulk(FriendForecastContract.EventTable.NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private int insertInBulk(String tableName, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
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
