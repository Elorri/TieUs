package com.elorri.android.communication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elorri.android.communication.R;
import com.elorri.android.communication.Tools;
import com.elorri.android.communication.data.db.ActionDAO;
import com.elorri.android.communication.data.db.ContactDAO;
import com.elorri.android.communication.data.db.EventDAO;

/**
 * Created by Elorri on 11/04/2016.
 */
public class CommunicationDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "communication.db";
    private final Context mContext;
    private static CommunicationDbHelper instance = null;


    public CommunicationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static CommunicationDbHelper instance(Context context) {
        if (instance == null) instance = new CommunicationDbHelper(context);
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);

        sqLiteDatabase.execSQL(ContactDAO.CREATE);
        sqLiteDatabase.execSQL(ActionDAO.CREATE);
        sqLiteDatabase.execSQL(EventDAO.CREATE);

        insert(sqLiteDatabase, ActionDAO.INSERT, R.raw.actions);
        Log.e("Communication", "Inserts done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CommunicationContract.ContactEntry.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CommunicationContract.ActionEntry.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CommunicationContract.EventEntry.NAME);
        onCreate(sqLiteDatabase);
    }


    protected void insert(SQLiteDatabase db, String queryInsert, int ressource_reference) {
        for (String line : Tools.loadTextFile(mContext, ressource_reference)) {
            db.execSQL(queryInsert, line.split("\\|"));
        }
    }


}