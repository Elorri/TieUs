package com.elorri.android.tieus.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        bulkInsert(db, ActionDAO.getStartData());

    }

    private void bulkInsert(SQLiteDatabase db, ContentValues[] startData) {
        Log.d("TieUs", "" + Thread.currentThread().getStackTrace()[2]);
        for (ContentValues values : startData) {
            db.insert(TieUsContract.ActionTable.NAME, null, values);
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
        Log.d("TieUs", "" + Thread.currentThread().getStackTrace()[2]);
    }


}