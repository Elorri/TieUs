package com.elorri.android.friendforcast.db;

import android.content.ContentValues;
import android.content.Context;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;

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





    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case VECTORS_OF_COMMUNICATION:
                return context.getResources().getString(R.string.vectors_of_communication);
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
