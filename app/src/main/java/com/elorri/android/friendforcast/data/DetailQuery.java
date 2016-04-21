package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.BoardAdapter;
import com.elorri.android.friendforcast.DetailAdapter;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailQuery {
    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        //Look if the contact has actions registered
        Cursor cursor = db.query(FriendForecastContract.EventTable.NAME,
                EventDAO.EventQuery.PROJECTION,
                EventDAO.EventQuery.SELECTION,
                new String[]{contactId},
                null, null, null);

        //If this contact does not have any action registered, load the noActionsRegisteredCursor
        if (cursor.getCount() <= 0)
            cursor = getNoActionsRegisteredCursor();
        else //If this contact have actions registered, load the actionsRegisteredCursor
            cursor = getActionsRegisteredCursor();
        return cursor;
    }


    private static Cursor getNoActionsRegisteredCursor() {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getWrappedCursor(context, ContactDAO.VECTOR_OF_COMMUNICATION, db, viewTypes));
        cursors.add(ContactContactsDAO.getWrappedCursor(context, ContactContactsDAO.LIKED_CONTACTS, db, viewTypes));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getActionsRegisteredCursor() {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getWrappedCursor(context, ContactDAO.VECTOR_OF_COMMUNICATION, db, viewTypes));
        cursors.add(ContactContactsDAO.getWrappedCursor(context, ContactContactsDAO.LIKED_CONTACTS, db, viewTypes));
        cursors.add(ContactContactsDAO.getWrappedCursor(context, ContactContactsDAO.LIKED_CONTACTS, db, viewTypes));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }
}
