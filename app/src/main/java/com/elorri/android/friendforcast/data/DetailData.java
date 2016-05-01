package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;
import com.elorri.android.friendforcast.fragments.DetailAdapter;

import java.util.ArrayList;

/**
 * Created by Elorri on 16/04/2016.
 */
public class DetailData {

    public interface TitleQuery {
        int COL_TITLE = 0;
    }

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db, String contactId) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(CursorUtils.setViewType(ContactDAO.getCursor(contactId,
                ContactDAO.CONTACT_BY_ID, db), viewTypes, DetailAdapter.VIEW_EMOICON));
        cursors.add(CursorUtils.setViewType(db.query(FriendForecastContract.ContactVectorsTable.NAME,
                        ContactVectorsDAO.ContactVectorsQuery.PROJECTION,
                        ContactVectorsDAO.ContactVectorsQuery.SELECTION,
                        new String[]{contactId}, null, null, null),
                viewTypes, DetailAdapter.VIEW_VECTORS_OF_COMMUNICATION));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context,
                ContactActionVectorEventDAO.ACTION_BY_CONTACT_ID,
                db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

}
