package com.elorri.android.friendforcast.data;

import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.DetailAdapter;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailQuery {
    public static final int LOADER_ID = 0;

    public static Cursor getCursor(SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactActionEventDAO.getCursorWithViewTypes(ContactActionEventDAO.ACTION_BY_CONTACT_ID, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }
}
