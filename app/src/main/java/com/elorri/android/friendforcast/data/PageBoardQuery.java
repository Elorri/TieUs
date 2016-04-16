package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.friendforcast.BoardAdapter;
import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ContactActionEventDAO;
import com.elorri.android.friendforcast.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 13/04/2016.
 */
public abstract class PageBoardQuery {

    public static final int LOADER_ID = 0;

    public static Cursor getCursor(Context context, SQLiteDatabase db) {
        ArrayList<Integer> viewTypes = new ArrayList<>();
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(getWrappedCursor(context, ContactActionEventDAO.UNMANAGED_PEOPLE, db, viewTypes));
        cursors.add(getWrappedCursor(context, ContactActionEventDAO.DELAY_PEOPLE, db, viewTypes));
        cursors.add(getWrappedCursor(context, ContactActionEventDAO.TODAY_PEOPLE, db, viewTypes));
        cursors.add(getWrappedCursor(context, ContactActionEventDAO.TODAY_DONE_PEOPLE, db, viewTypes));
        cursors.add(getWrappedCursor(context, ContactActionEventDAO.NEXT_PEOPLE, db, viewTypes));
        BoardAdapter.viewTypes = Tools.convertToArrayViewTypes(viewTypes);
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

    public static Cursor getWrappedCursor(Context context, int cursorType, SQLiteDatabase db,
                                          ArrayList<Integer> viewTypes) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(Tools.getOneLineCursor(getCursorTitle(context, cursorType)));
        viewTypes.add(BoardAdapter.VIEW_TITLE);
        cursors.add(getCursorWithViewTypes( cursorType, db, viewTypes));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }


    private static Cursor getCursorWithViewTypes(int cursorType, SQLiteDatabase db, ArrayList<Integer> viewTypes) {

        switch (cursorType) {
            case ContactActionEventDAO.UNMANAGED_PEOPLE:
                return setViewType(
                        ContactActionEventDAO.getCursor( cursorType, db),
                        viewTypes, BoardAdapter.VIEW_UNMANAGED_PEOPLE);
            case ContactActionEventDAO.DELAY_PEOPLE:
                return setViewType(ContactActionEventDAO.getCursor( cursorType, db),
                        viewTypes, BoardAdapter.VIEW_DELAY_PEOPLE);
            case ContactActionEventDAO.TODAY_PEOPLE:
                return setViewType(ContactActionEventDAO.getCursor( cursorType, db),
                        viewTypes, BoardAdapter.VIEW_TODAY_PEOPLE);
            case ContactActionEventDAO.TODAY_DONE_PEOPLE:
                return setViewType(ContactActionEventDAO.getCursor( cursorType, db),
                        viewTypes, BoardAdapter.VIEW_TODAY_DONE_PEOPLE);
            case ContactActionEventDAO.NEXT_PEOPLE:
                return setViewType(ContactActionEventDAO.getCursor( cursorType, db),
                        viewTypes, BoardAdapter.VIEW_NEXT_PEOPLE);
            default:
                return null;
        }
    }

    private static Cursor setViewType(Cursor cursor, ArrayList<Integer> viewTypes, int viewType) {
        while (cursor.moveToNext()) {
            viewTypes.add(viewType);
        }
        return cursor;
    }


    private static String getCursorTitle(Context context, int cursorType) {
        switch (cursorType) {
            case ContactActionEventDAO.UNMANAGED_PEOPLE:
                return context.getResources().getString(R.string.unmanaged_people);
            case ContactActionEventDAO.DELAY_PEOPLE:
                return context.getResources().getString(R.string.delay);
            case ContactActionEventDAO.TODAY_PEOPLE:
                return context.getResources().getString(R.string.today);
            case ContactActionEventDAO.TODAY_DONE_PEOPLE:
                return context.getResources().getString(R.string.done);
            case ContactActionEventDAO.NEXT_PEOPLE:
                return context.getResources().getString(R.string.next);
            default:
                return null;
        }
    }


}