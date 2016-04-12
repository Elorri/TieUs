package com.elorri.android.communication.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Elorri on 11/04/2016.
 */
public class CommunicationContract {

    private static String PATH_BOARD="board";

    public static final String CONTENT_AUTHORITY = "com.elorri.android.communication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri URI_PAGE_BOARD = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOARD).build();



    public static class ContactEntry implements BaseColumns{
        public static final String NAME = "contact";
        public static final String COLUMN_GOOGLE_PLUS_ID1 = "google_plus_id1";
        public static final String COLUMN_GOOGLE_PLUS_ID2 = "google_plus_id2";
    }


    public static class ActionEntry implements BaseColumns{
        public static final String NAME= "action";
        public static final String COLUMN_NAME= "name";
    }


    public static class EventEntry implements BaseColumns{
        public static final String NAME = "event";
        public static final String COLUMN_ACTION_ID = "action_id";
        public static final String COLUMN_TIME_START = "time_start";
        public static final String COLUMN_TIME_END= "time_end";
    }

}
