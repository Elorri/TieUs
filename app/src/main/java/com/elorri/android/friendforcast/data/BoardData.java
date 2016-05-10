package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.MatrixCursors;
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
        Cursor cursor = context.getContentResolver().query(FriendForecastContract.ContactTable
                        .CONTENT_URI,
                null,
                null,
                null,
                null);

        try {
            if (cursor.getCount() == 0)
                return MatrixCursors.getOneLineCursor(
                    MatrixCursors.EmptyCursorMessageQuery.PROJECTION,
                    MatrixCursors.EmptyCursorMessageQuery.VALUES,
                    context.getResources().getString(R.string.no_contacts_on_phone));

        } finally {
            cursor.close();
        }


        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(ContactDAO.getCursor(db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.UNMANAGED_PEOPLE, db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.DELAY_PEOPLE, db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.TODAY_PEOPLE, db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.TODAY_DONE_PEOPLE, db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.NEXT_PEOPLE, db));
        cursors.add(ContactActionVectorEventDAO.getWrappedCursor(context, ContactActionVectorEventDAO.UNTRACKED_PEOPLE, db));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


}