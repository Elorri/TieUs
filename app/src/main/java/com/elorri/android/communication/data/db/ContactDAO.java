package com.elorri.android.communication.data.db;

import com.elorri.android.communication.data.CommunicationContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ContactDAO {
    public static final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE "
            + CommunicationContract.ContactEntry.NAME +
            "(" + CommunicationContract.ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID1 + " TEXT,"
            + CommunicationContract.ContactEntry.COLUMN_GOOGLE_PLUS_ID2 + " INTEGER)";


}
