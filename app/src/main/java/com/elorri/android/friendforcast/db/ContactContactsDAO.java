package com.elorri.android.friendforcast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.DetailAdapter;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.CursorUtils;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 21/04/2016.
 */
public class ContactContactsDAO {

    public static final int LIKED_CONTACTS = 0;


    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactContactsTable.NAME +
            "(" + FriendForecastContract.ContactContactsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_1 + " TEXT NOT NULL,"
            + FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_2 + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_1 + ", "
            + FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_2 + ") ON " +
            "CONFLICT REPLACE)";


    public interface ContactContactsQuery {

        int COL_ID = 0;
        int COL_CONTACT_ID_1 = 1;
        int COL_CONTACT_ID_2 = 2;

        String SELECTION = FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_1 + "=?";

        String[] PROJECTION = {
                FriendForecastContract.ContactContactsTable._ID,
                FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_1,
                FriendForecastContract.ContactContactsTable.COLUMN_CONTACT_ID_2
        };

    }


    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes, String contactId) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(DetailAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes(context, cursorType, db, viewTypes, contactId));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case LIKED_CONTACTS:
                return context.getResources().getString(R.string.liked_contacts);
            default:
                return null;
        }
    }

    public static Cursor getCursorWithViewTypes(Context context, int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes, String contactId) {

        switch (cursorType) {
            case LIKED_CONTACTS:
                Cursor cursor = db.query(FriendForecastContract.ContactContactsTable.NAME, ContactContactsQuery
                                .PROJECTION, ContactContactsQuery.SELECTION,
                        new String[]{contactId}, null, null, null);

                if (cursor.getCount() == 0) {
                    cursor = Tools.getOneLineCursor(context.getResources().getString(R.string.no_liked_contacts));
                    cursor = CursorUtils.setViewType(cursor, viewTypes, DetailAdapter.VIEW_EMPTY_CURSOR);
                }
                cursor = CursorUtils.setViewType(cursor, viewTypes, DetailAdapter.VIEW_LIKED_CONTACTS);
                return cursor;
            default:
                return null;
        }
    }


}
