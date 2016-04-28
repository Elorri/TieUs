package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.fragments.BoardAdapter;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.CursorUtils;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 13/04/2016.
 */
public abstract class BoardData {

    public interface TitleQuery {
        int COL_TITLE = 0;
    }

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(CursorUtils.setViewType(ContactDAO.getCursor(ContactDAO.RATIO, db), viewTypes, BoardAdapter.VIEW_FORECAST));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.UNMANAGED_PEOPLE,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.DELAY_PEOPLE,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.TODAY_PEOPLE,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.TODAY_DONE_PEOPLE,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.NEXT_PEOPLE,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.SOCIAL_NETWORK,
                db, viewTypes));
        cursors.add(ContactActionEventDAO.getWrappedCursor(context, ContactActionEventDAO.UNTRACKED_PEOPLE,
                db, viewTypes));
        BoardAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


}