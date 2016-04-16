package com.elorri.android.friendforcast.db;

import com.elorri.android.friendforcast.data.FriendCastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class ActionDAO {


    public static  final String CREATE = "CREATE TABLE "
            + FriendCastContract.ActionEntry.NAME +
            "(" + FriendCastContract.ActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FriendCastContract.ActionEntry.COLUMN_NAME + " TEXT NOT NULL)";



    public static final String INSERT = "INSERT INTO "
            + FriendCastContract.ActionEntry.NAME
            + " ("
            + FriendCastContract.ActionEntry._ID + ", "
            + FriendCastContract.ActionEntry.COLUMN_NAME
            + ") " + "VALUES (?, ?)";
}
