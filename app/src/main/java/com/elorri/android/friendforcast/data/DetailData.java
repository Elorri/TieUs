package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
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
        cursors.add(getActionsCursor(context, db, viewTypes, contactId));
        DetailAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    private static Cursor getActionsCursor(Context context, SQLiteDatabase db, ArrayList<Integer>
            viewTypes, String contactId) {
        Cursor cursor = ContactActionVectorEventDAO.getCursorWithViewTypes(
                ContactActionVectorEventDAO.ACTION_BY_CONTACT_ID,
                db, viewTypes, contactId);
        if (cursor.getCount() == 0)
            cursor = CursorUtils.setViewType(
                    Tools.getOneLineCursor(context.getResources().getString(R.string.select_action)),
                    viewTypes,
                    DetailAdapter.VIEW_EMPTY_CURSOR);
        return cursor;
    }

}
