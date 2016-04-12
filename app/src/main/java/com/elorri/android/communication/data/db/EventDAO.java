package com.elorri.android.communication.data.db;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class EventDAO {

    public static final String SQL_CREATE_EVENT_TABLE = "CREATE TABLE "
            + CommunicationContract.EventEntry.NAME +
            "(" + CommunicationContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID + " INTEGER,"
            + CommunicationContract.EventEntry.COLUMN_TIME_START + " INTEGER,"
            + CommunicationContract.EventEntry.COLUMN_TIME_END + " INTEGER)";
}
