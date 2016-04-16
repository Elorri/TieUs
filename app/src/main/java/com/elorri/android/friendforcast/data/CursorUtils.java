package com.elorri.android.friendforcast.data;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Elorri on 14/04/2016.
 */
public class CursorUtils {




    public static Cursor setViewType(Cursor cursor, ArrayList<Integer> viewTypes, int viewType) {
        while (cursor.moveToNext()) {
            viewTypes.add(viewType);
        }
        return cursor;
    }






}
