package com.elorri.android.communication.data;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Elorri on 12/04/2016.
 * Wrapper class for Cursor that delegates all calls to the actual cursor object.
 * This class works well with adapter and allow it to know the type of view to inflate by calling
 * the getViewType method.
 */
public class ViewTypeCursor extends CursorWrapper {

    public ViewTypeCursor(Cursor cursor) {
        super(cursor);
    }
}
