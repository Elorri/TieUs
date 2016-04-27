package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.fragments.DetailAdapter;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactSocialNetworkDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailData {
    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactVectorsDAO.getWrappedCursor(context, ContactVectorsDAO.VECTORS_OF_COMMUNICATION, db, viewTypes, contactId));
        cursors.add(ContactSocialNetworkDAO.getCursorWithViewTypes(context, ContactSocialNetworkDAO.SOCIAL_NETWORKS, db, viewTypes, contactId));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.ACTION_BY_CONTACT_ID,
                db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

}
