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
public class ContactSocialNetworkDAO {

    public static final int SOCIAL_NETWORKS = 0;


    public static final String CREATE = "CREATE TABLE "
            + FriendForecastContract.ContactSocialNetworkTable.NAME +
            "(" + FriendForecastContract.ContactSocialNetworkTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_1 + " TEXT NOT NULL,"
            + FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_2 + " TEXT NOT NULL, "
            + FriendForecastContract.ContactSocialNetworkTable.COLUMN_THUMBNAIL + " TEXT NOT NULL, "
            + "UNIQUE (" + FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_1 + ", "
            + FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_2 + ") ON " +
            "CONFLICT REPLACE)";


    public interface ContactSocialNetworksQuery {

        int COL_ID = 0;
        int COL_CONTACT_ID_1 = 1;
        int COL_CONTACT_ID_2 = 2;
        int COL_THUMBNAIL = 3;

        String SELECTION = FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_1 + "=?";

        String[] PROJECTION = {
                FriendForecastContract.ContactSocialNetworkTable._ID,
                FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_1,
                FriendForecastContract.ContactSocialNetworkTable.COLUMN_CONTACT_ID_2,
                FriendForecastContract.ContactSocialNetworkTable.COLUMN_THUMBNAIL
        };

    }


    //TODO remove that
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
            case SOCIAL_NETWORKS:
                return context.getResources().getString(R.string.liked_contacts);
            default:
                return null;
        }
    }

    public static Cursor getCursorWithViewTypes(Context context, int cursorType, SQLiteDatabase db,
                                                ArrayList<Integer> viewTypes, String contactId) {

        switch (cursorType) {
            case SOCIAL_NETWORKS:
                Cursor cursor = db.query(FriendForecastContract.ContactSocialNetworkTable.NAME, ContactSocialNetworksQuery
                                .PROJECTION, ContactSocialNetworksQuery.SELECTION,
                        new String[]{contactId}, null, null, null);
//TODO remove comments
//                if (cursor.getCount() == 0) {
//                    cursor = Tools.getOneLineCursor(context.getResources().getString(R.string.no_liked_contacts));
//                    cursor = CursorUtils.setViewType(cursor, viewTypes, DetailAdapter.VIEW_EMPTY_CURSOR);
//                }
                cursor = CursorUtils.setViewType(cursor, viewTypes, DetailAdapter.VIEW_SOCIAL_NETWORKS);
                return cursor;
            default:
                return null;
        }
    }

}
