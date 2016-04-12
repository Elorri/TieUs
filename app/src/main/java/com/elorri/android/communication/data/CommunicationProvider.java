package com.elorri.android.communication.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elorri.android.communication.BoardAdapter;
import com.elorri.android.communication.Tools;
import com.elorri.android.communication.data.db.ContactActionEventDAO;

import java.util.ArrayList;

/**
 * Created by Elorri on 12/04/2016.
 */
public class CommunicationProvider extends ContentProvider {

    static final int PAGE_BOARD = 100; //will match content://com.elorri.android.communication/board/

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private CommunicationDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CommunicationContract.CONTENT_AUTHORITY, CommunicationContract.PATH_BOARD, PAGE_BOARD);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = CommunicationDbHelper.instance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e("MealPlanner", Thread.currentThread().getStackTrace()[2] + "thread " + Tools.thread());
        Cursor cursor = null;
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case PAGE_BOARD:
                ArrayList<Integer> viewTypes = new ArrayList<>();
                ArrayList<Cursor> cursors = new ArrayList();
                int cursorType = ContactActionEventDAO.UNMANAGED_PEOPLE;
                cursors.add(ContactActionEventDAO.getWrappedCursor(getContext(), cursorType, db, viewTypes));
                cursorType = ContactActionEventDAO.DELAY_PEOPLE;
                cursors.add(ContactActionEventDAO.getWrappedCursor(getContext(), cursorType, db, viewTypes));
                cursorType = ContactActionEventDAO.TODAY_PEOPLE;
                cursors.add(ContactActionEventDAO.getWrappedCursor(getContext(), cursorType, db, viewTypes));
                cursorType = ContactActionEventDAO.TODAY_DONE_PEOPLE;
                cursors.add(ContactActionEventDAO.getWrappedCursor(getContext(), cursorType, db, viewTypes));
                cursorType = ContactActionEventDAO.NEXT_PEOPLE;
                cursors.add(ContactActionEventDAO.getWrappedCursor(getContext(), cursorType, db, viewTypes));
                BoardAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
                cursor = new MergeCursor(Tools.convertToArrayCursors(cursors));
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
        return null;
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
