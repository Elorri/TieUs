package com.elorri.android.friendforcast.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.db.ActionDAO;
import com.elorri.android.friendforcast.db.ActionVectorTemplatesDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.db.EventDAO;
import com.elorri.android.friendforcast.db.VectorDAO;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 11/04/2016.
 */
public class FriendForecastDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "friends.db";
    private final Context mContext;
    private static FriendForecastDbHelper instance = null;


    public FriendForecastDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);

        sqLiteDatabase.execSQL(ContactDAO.CREATE);
        sqLiteDatabase.execSQL(ActionDAO.CREATE);
        sqLiteDatabase.execSQL(EventDAO.CREATE);
        sqLiteDatabase.execSQL(VectorDAO.CREATE);
        sqLiteDatabase.execSQL(ContactVectorsDAO.CREATE);
        sqLiteDatabase.execSQL(ActionVectorTemplatesDAO.CREATE);


        insert(sqLiteDatabase, ActionDAO.INSERT, R.raw.actions);
        insert(sqLiteDatabase, ActionVectorTemplatesDAO.INSERT, R.raw.action_vector_templates);
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FriendForecastContract.ContactTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FriendForecastContract.ActionTable.NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FriendForecastContract.EventTable.NAME);
        onCreate(sqLiteDatabase);
    }


    protected void insert(SQLiteDatabase db, String queryInsert, int ressource_reference) {
        for (String line : Tools.loadTextFile(mContext, ressource_reference)) {
            db.execSQL(queryInsert, line.split("\\|"));
        }
    }


}