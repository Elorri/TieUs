package com.elorri.android.communication.data.db;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {


    public static  final String CREATE = "CREATE TABLE "
            + CommunicationContract.ActionEntry.NAME +
            "(" + CommunicationContract.ActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CommunicationContract.ActionEntry.COLUMN_NAME + " TEXT)";



    public static final String INSERT = "INSERT INTO "
            + CommunicationContract.ActionEntry.NAME
            + " ("
            + CommunicationContract.ActionEntry._ID + ", "
            + CommunicationContract.ActionEntry.COLUMN_NAME
            + ") " + "VALUES (?, ?)";
}
