package com.elorri.android.communication.db;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class EventDAO {

    public static final String CREATE = "CREATE TABLE "
            + CommunicationContract.EventEntry.NAME +
            "(" + CommunicationContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + CommunicationContract.EventEntry.COLUMN_CONTACT_ID + " INTEGER NOT NULL,"
            + CommunicationContract.EventEntry.COLUMN_ACTION_ID + " INTEGER NOT NULL,"
            + CommunicationContract.EventEntry.COLUMN_TIME_START + " INTEGER NOT NULL,"
            + CommunicationContract.EventEntry.COLUMN_TIME_END + " INTEGER)";

        public static final String INSERT = "INSERT INTO "
                + CommunicationContract.EventEntry.NAME + " ("
                + CommunicationContract.EventEntry._ID + ", "
                + CommunicationContract.EventEntry.COLUMN_CONTACT_ID+", "
                + CommunicationContract.EventEntry.COLUMN_ACTION_ID+", "
                + CommunicationContract.EventEntry.COLUMN_TIME_START+", "
                + CommunicationContract.EventEntry.COLUMN_TIME_END+") "
                + "VALUES (?, ?, ?, ?, ?)";
}
