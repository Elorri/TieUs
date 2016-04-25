package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.DetailAdapter;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactContactsDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailQuery {
    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        //Look if the contact has liked contacts registered
        Cursor cursor = db.query(FriendForecastContract.ContactContactsTable.NAME,
                ContactContactsDAO.ContactContactsQuery.PROJECTION,
                ContactContactsDAO.ContactContactsQuery.SELECTION,
                new String[]{contactId},
                null, null, null);

        //If this contact does not have any action registered, load the noActionsRegisteredCursor
        if (cursor.getCount() <= 0)
            cursor = getNoLikedContactsRegisteredCursor(context, db, contactId);
        else //If this contact have liked contacts registered, load the liked contactsRegisteredCursor
            cursor = getLikedContactsRegisteredCursor(context, db, contactId);

        return cursor;
    }


    private static Cursor getNoLikedContactsRegisteredCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactVectorsDAO.getWrappedCursor(context, ContactVectorsDAO.VECTORS_OF_COMMUNICATION, db, viewTypes, contactId));
        cursors.add(ContactContactsDAO.getWrappedCursor(context, ContactContactsDAO.LIKED_CONTACTS, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getLikedContactsRegisteredCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactVectorsDAO.getWrappedCursor(context, ContactVectorsDAO.VECTORS_OF_COMMUNICATION, db, viewTypes, contactId));
        cursors.add(ContactContactsDAO.getWrappedCursor(context, ContactContactsDAO.LIKED_CONTACTS, db, viewTypes, contactId));
        cursors.add(ContactActionEventDAO.getCursorWithViewTypes(ContactActionEventDAO.ACTION_BY_CONTACT_ID, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }
}
