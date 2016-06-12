package com.elorri.android.tieus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.db.ActionDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.EventDAO;
import com.elorri.android.tieus.db.VectorDAO;

/**
 * Created by Elorri on 11/04/2016.
 */
public class TieUsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "tieus.db";
    private final Context mContext;




    public TieUsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TieUs", "" + Thread.currentThread().getStackTrace()[2]);

        db.execSQL(ContactDAO.CREATE);
        db.execSQL(ActionDAO.CREATE);
        db.execSQL(EventDAO.CREATE);
        db.execSQL(VectorDAO.CREATE);

        bulkInsert(db, TieUsContract.ActionTable.NAME, getActionTableStartData());


     }

    private void bulkInsert(SQLiteDatabase db, String table, ContentValues[] contentValues) {
        for(ContentValues contentValue : contentValues){
            db.insert(table, null, contentValue);
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.ContactTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.ActionTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.EventTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TieUsContract.VectorTable.NAME);
        onCreate(sqLiteDatabase);
    }



    private ContentValues getActionTableLine(String title, String name, String sortOrder) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(TieUsContract.ActionTable.COLUMN_TITLE, title);
        contentValue.put(TieUsContract.ActionTable.COLUMN_NAME, name);
        contentValue.put(TieUsContract.ActionTable.COLUMN_SORT_ORDER, sortOrder);
        return contentValue;
    }

    public ContentValues[] getActionTableStartData() {
        ContentValues[] contentValues = new ContentValues[12];
        contentValues[0] = getActionTableLine(mContext.getResources().getString(R.string.action_title1),
                mContext.getResources().getString(R.string.action_name1),
                mContext.getResources().getString(R.string.action_sort_order1));
        contentValues[1] = getActionTableLine(mContext.getResources().getString(R.string.action_title2),
                mContext.getResources().getString(R.string.action_name2),
                mContext.getResources().getString(R.string.action_sort_order2));
        contentValues[2] = getActionTableLine(mContext.getResources().getString(R.string.action_title3),
                mContext.getResources().getString(R.string.action_name3),
                mContext.getResources().getString(R.string.action_sort_order3));
        contentValues[3] = getActionTableLine(mContext.getResources().getString(R.string.action_title4),
                mContext.getResources().getString(R.string.action_name4),
                mContext.getResources().getString(R.string.action_sort_order4));
        contentValues[4] = getActionTableLine(mContext.getResources().getString(R.string.action_title5),
                mContext.getResources().getString(R.string.action_name5),
                mContext.getResources().getString(R.string.action_sort_order5));
        contentValues[5] = getActionTableLine(mContext.getResources().getString(R.string.action_title6),
                mContext.getResources().getString(R.string.action_name6),
                mContext.getResources().getString(R.string.action_sort_order6));
        contentValues[6] = getActionTableLine(mContext.getResources().getString(R.string.action_title7),
                mContext.getResources().getString(R.string.action_name7),
                mContext.getResources().getString(R.string.action_sort_order7));
        contentValues[7] = getActionTableLine(mContext.getResources().getString(R.string.action_title8),
                mContext.getResources().getString(R.string.action_name8),
                mContext.getResources().getString(R.string.action_sort_order8));
        contentValues[8] = getActionTableLine(mContext.getResources().getString(R.string.action_title9),
                mContext.getResources().getString(R.string.action_name9),
                mContext.getResources().getString(R.string.action_sort_order9));
        contentValues[9] = getActionTableLine(mContext.getResources().getString(R.string.action_title10),
                mContext.getResources().getString(R.string.action_name10),
                mContext.getResources().getString(R.string.action_sort_order10));
        contentValues[10] = getActionTableLine(mContext.getResources().getString(R.string.action_title11),
                mContext.getResources().getString(R.string.action_name11),
                mContext.getResources().getString(R.string.action_sort_order11));
        contentValues[11] = getActionTableLine(mContext.getResources().getString(R.string.action_title12),
                mContext.getResources().getString(R.string.action_name12),
                mContext.getResources().getString(R.string.action_sort_order12));
        return contentValues;
    }



}