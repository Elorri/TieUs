package com.elorri.android.communication.data.db;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {


    public static  final String SQL_CREATE_ACTION_TABLE = "CREATE TABLE "
            + CommunicationContract.ActionEntry.NAME +
            "(" + CommunicationContract.ActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CommunicationContract.ActionEntry.COLUMN_NAME + " TEXT)";



    public static final String QUERY_INSERT = "INSERT INTO "
            + CommunicationContract.ActionEntry.NAME
            + " ("
            + CommunicationContract.ActionEntry._ID + ", "
            + CommunicationContract.ActionEntry.COLUMN_NAME
            + ") " + "VALUES (?, ?)";
}
