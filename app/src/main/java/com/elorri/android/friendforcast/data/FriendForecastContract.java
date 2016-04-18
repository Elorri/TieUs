package com.elorri.android.friendforcast.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Elorri on 11/04/2016.
 */
public class FriendForecastContract {

    public static final String CONTENT_AUTHORITY = "com.elorri.android.friendforcast";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class BoardData{
        // DATA_BOARD  content://com.elorri.android.communication/board/
        public static String PATH_BOARD = "board";
        public static final Uri URI_PAGE_BOARD = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOARD).build();
    }

    public static class DetailData{
        //DATA_DETAIL content://com.elorri.android.communication/detail/
        public static String PATH_DETAIL = "detail";

        public static Uri buildDetailUri(long id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_DETAIL)
                    .appendEncodedPath(String.valueOf(id))
                    .build();
        }

        public static String getContactIdFromUri(Uri uri) {
            Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "" + "contactId" +
                    uri.getPathSegments().get(1));
            return uri.getPathSegments().get(1);
        }
    }



    public static class ContactTable implements BaseColumns {
        public static String PATH_CONTACT = "contact";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT).build();

        public static final String NAME = "contact";
        public static final String COLUMN_ANDROID_CONTACT_ID = "android_contact_id";
        public static final String COLUMN_ANDROID_CONTACT_LOOKUP_KEY = "android_contact_lookup_key";
        public static final String COLUMN_ANDROID_CONTACT_NAME = "contact_name";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_EMOICON_ID = "emoicon";

        public static Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    public static class ActionTable implements BaseColumns {
        public static String PATH_ACTION = "action";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTION).build();

        public static final String NAME = "action";
        public static final String COLUMN_NAME = "name";
        public static final String VIEW_ACTION_NAME = "action_name";
    }


    public static class EventTable implements BaseColumns {
        public static String PATH_EVENT = "event";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();

        public static final String NAME = "event";
        public static final String COLUMN_CONTACT_ID = "contact_id";
        public static final String COLUMN_ACTION_ID = "action_id";
        public static final String COLUMN_TIME_START = "time_start";
        public static final String COLUMN_TIME_END = "time_end";
        public static final String VIEW_EVENT_ID = "event_id";

        public static Uri buildEventUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
