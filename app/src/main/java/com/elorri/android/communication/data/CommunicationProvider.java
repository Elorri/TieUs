package com.elorri.android.communication.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elorri.android.communication.extra.Tools;

/**
 * Created by Elorri on 12/04/2016.
 */
public class CommunicationProvider extends ContentProvider {

    static final int PAGE_BOARD = 100; //will match content://com.elorri.android.communication/board/

    static final int TABLE_CONTACT = 500; //will match content://com.elorri.android.communication/contact/

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private CommunicationDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CommunicationContract.CONTENT_AUTHORITY, CommunicationContract.PATH_BOARD, PAGE_BOARD);
        matcher.addURI(CommunicationContract.CONTENT_AUTHORITY, CommunicationContract.PATH_CONTACT, TABLE_CONTACT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CommunicationDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "thread " + Tools.thread());
        Cursor cursor = null;
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case PAGE_BOARD:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "PAGE_BOARD uri" + uri);
                cursor = PageBoardQuery.getCursor(getContext(), db);
                break;
            case TABLE_CONTACT:
                Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "TABLE_CONTACT uri" + uri);
                cursor = mOpenHelper.getReadableDatabase().query(
                        CommunicationContract.ContactEntry.NAME,
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
                long _id = db.insert(CommunicationContract.ContactEntry.NAME, null, values);
                if (_id > 0) {
                    returnUri = CommunicationContract.ContactEntry.buildContactUri(_id);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "insert _id TABLE_CONTACT " + _id);
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
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
