package com.elorri.android.tieus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.extra.Tools;

import java.util.ArrayList;

/**
 * Created by Elorri on 26/04/2016.
 */
public class VectorDAO {

    public static final int ALL_VECTORS = 0;

    public static final String CREATE = "CREATE TABLE "
            + TieUsContract.VectorTable.NAME +
            "(" + TieUsContract.VectorTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TieUsContract.VectorTable.COLUMN_NAME + " TEXT NOT NULL, "
            + TieUsContract.VectorTable.COLUMN_DATA + " TEXT NOT NULL, "
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + " TEXT NOT NULL, "
            + "UNIQUE (" + TieUsContract.VectorTable.COLUMN_NAME + ") ON CONFLICT REPLACE,"
            + "UNIQUE (" + TieUsContract.VectorTable.COLUMN_DATA + ") ON CONFLICT REPLACE)";


    public interface VectorQuery {

        int COL_ID = 0;
        int COL_VECTOR_NAME = 1;
        int COL_DATA = 2;
        int COL_MIMETYPE = 3;

        String SORT_ORDER = TieUsContract.VectorTable.COLUMN_NAME + " asc";

        String[] PROJECTION = {
                TieUsContract.VectorTable._ID,
                TieUsContract.VectorTable.COLUMN_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_QUERY = {
                TieUsContract.VectorTable._ID,
                TieUsContract.VectorTable.COLUMN_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.VIEW_VECTOR_ITEM + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

    }

    public static ContentValues getContentValues(String name, String data, String mimetype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TieUsContract.VectorTable.COLUMN_NAME, name);
        contentValues.put(TieUsContract.VectorTable.COLUMN_DATA, data);
        contentValues.put(TieUsContract.VectorTable.COLUMN_MIMETYPE, mimetype);
        return contentValues;
    }


    public static Cursor getCursor(Context context, SQLiteDatabase db) {
        ArrayList<Cursor> cursors = new ArrayList();
        cursors.add(MatrixCursors.getOneLineCursor(
                MatrixCursors.TitleQuery.PROJECTION,
                MatrixCursors.TitleQuery.VALUES,
                context.getResources().getString(R.string.select_vector)));
        cursors.add(db.query(TieUsContract.VectorTable.NAME,
                VectorQuery.PROJECTION_QUERY,
                null,
                null,
                null,
                null,
                VectorQuery.SORT_ORDER));
        return new MergeCursor(Tools.convertToArrayCursors(cursors));
    }

}
