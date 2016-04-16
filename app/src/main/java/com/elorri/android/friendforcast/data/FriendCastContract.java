package com.elorri.android.friendforcast.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Elorri on 11/04/2016.
 */
public class FriendCastContract {

    //Pages paths
    public static String PATH_BOARD = "board";

    //Tables paths
    public static String PATH_CONTACT = "contact";

    public static final String CONTENT_AUTHORITY = "com.elorri.android.friendforcast";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri URI_PAGE_BOARD = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOARD).build();


    public static class ContactEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT).build();

        public static final String NAME = "contact";
        public static final String COLUMN_ANDROID_CONTACT_ID = "android_contact_id";
        public static final String COLUMN_ANDROID_CONTACT_LOOKUP_KEY = "android_contact_lookup_key";
        public static final String COLUMN_ANDROID_CONTACT_NAME = "contact_name";
        public static final String COLUMN_EMOICON_ID = "emoicon";

        public static Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    public static class ActionEntry implements BaseColumns {
        public static final String NAME = "action";
        public static final String COLUMN_NAME = "name";
        public static final String VIEW_ACTION_ID = "action_id";
        public static final String VIEW_ACTION_NAME = "action_name";
    }


    public static class EventEntry implements BaseColumns {
        public static final String NAME = "event";
        public static final String COLUMN_CONTACT_ID = "contact_id";
        public static final String COLUMN_ACTION_ID = "action_id";
        public static final String COLUMN_TIME_START = "time_start";
        public static final String COLUMN_TIME_END = "time_end";
        public static final String VIEW_EVENT_ID = "event_id";
    }

}
