package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.fragments.DetailAdapter;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 21/04/2016.
 */
public class ContactVectorsDAO {


    public static final int VECTORS_OF_COMMUNICATION = 0;



    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactVectorsTable.NAME +
            "(" + FriendForecastContract.ContactVectorsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactVectorsTable.COLUMN_CONTACT_ID + " TEXT NOT NULL,"
            + FriendForecastContract.ContactVectorsTable.COLUMN_VECTOR_ID + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ContactVectorsTable.COLUMN_CONTACT_ID + ", "
            + FriendForecastContract.ContactVectorsTable.COLUMN_VECTOR_ID + ") ON " +
            "CONFLICT REPLACE)";


    public interface ContactVectorsQuery {

        int COL_ID = 0;
        int COL_CONTACT_ID = 1;
        int COL_VECTOR_ID = 2;

        String SELECTION = FriendForecastContract.ContactVectorsTable.COLUMN_CONTACT_ID + "=?";

        String[] PROJECTION = {
                FriendForecastContract.ContactVectorsTable._ID,
                FriendForecastContract.ContactVectorsTable.COLUMN_CONTACT_ID,
                FriendForecastContract.ContactVectorsTable.COLUMN_VECTOR_ID
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
            case VECTORS_OF_COMMUNICATION:
                return context.getResources().getString(R.string.vectors_of_communication);
            default:
                return null;
        }
    }

    public static Cursor getCursorWithViewTypes(Context context, int cursorType, SQLiteDatabase db,
                                                 ArrayList<Integer> viewTypes, String contactId) {

        switch (cursorType) {
            case VECTORS_OF_COMMUNICATION:
                Cursor cursor=db.query(FriendForecastContract.ContactVectorsTable.NAME, ContactVectorsQuery
                                .PROJECTION, ContactVectorsQuery.SELECTION,
                        new String[]{contactId}, null, null, null);
                if(cursor.getCount()==0){
                    cursor=Tools.getOneLineCursor(context.getResources().getString(R.string
                            .no_vectors_of_communication));
                    cursor=CursorUtils.setViewType(cursor,
                            viewTypes, DetailAdapter.VIEW_EMPTY_CURSOR);
                }
                cursor=CursorUtils.setViewType(cursor,
                        viewTypes, DetailAdapter.VIEW_VECTORS_OF_COMMUNICATION);
                return cursor;
            default:
                return null;
        }
    }


    public static ContentValues getContentValues(String contactId, int vectorResourcesId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendForecastContract.ContactVectorsTable.COLUMN_CONTACT_ID,contactId);
        contentValues.put(FriendForecastContract.ContactVectorsTable.COLUMN_VECTOR_ID, vectorResourcesId);
        return contentValues;
    }


}
