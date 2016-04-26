package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.DetailAdapter;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactSocialNetworkDAO;
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
        //Look if the contact has social network registered registered
        Cursor cursor = db.query(FriendForecastContract.ContactSocialNetworkTable.NAME,
                ContactSocialNetworkDAO.ContactSocialNetworksQuery.PROJECTION,
                ContactSocialNetworkDAO.ContactSocialNetworksQuery.SELECTION,
                new String[]{contactId},
                null, null, null);

        boolean isSocialNetworkAddFeatureAware =
                ContactDAO.isUserAwareOfSocialNetworkAddFeature(db, contactId);

        //If this contact does not have any social networks registered, we ask the user to
        // register some at least one time. User will see this cursor only once.
        if (cursor.getCount() <= 0 && !isSocialNetworkAddFeatureAware)
            cursor = getSocialNetworkUnfilledCursor(context, db, contactId);
        else //If this contact have social networks registered or does not but the user is aware
        // of it, he can now add actions
            cursor = getReadyForActionCursor(context, db, contactId);
        return cursor;
    }


    private static Cursor getSocialNetworkUnfilledCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactVectorsDAO.getWrappedCursor(context, ContactVectorsDAO.VECTORS_OF_COMMUNICATION, db, viewTypes, contactId));
        cursors.add(ContactSocialNetworkDAO.getCursorWithViewTypes(context, ContactSocialNetworkDAO.SOCIAL_NETWORKS, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getReadyForActionCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursorWithViewTypes(ContactDAO.CONTACT_BY_ID, db, viewTypes, contactId));
        cursors.add(ContactVectorsDAO.getWrappedCursor(context, ContactVectorsDAO.VECTORS_OF_COMMUNICATION, db, viewTypes, contactId));
        cursors.add(ContactSocialNetworkDAO.getCursorWithViewTypes(context, ContactSocialNetworkDAO.SOCIAL_NETWORKS, db, viewTypes, contactId));
        cursors.add(ContactActionEventDAO.getCursorWithViewTypes(ContactActionEventDAO.ACTION_BY_CONTACT_ID, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }
}
