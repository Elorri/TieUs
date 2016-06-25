/*
 * The MIT License (MIT)

 Copyright (c) 2016 ETCHEMENDY ELORRI

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package com.elorri.android.capstone.tieus.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Elorri on 11/04/2016.
 * This class list column names of the different tables used to store data.
 * of the app
 */
public class TieUsContract {

    public static final String CONTENT_AUTHORITY = "com.elorri.android.capstone.tieus";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class WidgetData {
        // DATA_WIDGET  content://com.elorri.android.tieus/widget/
        static String PATH_WIDGET = "widget";

        public static Uri buildWidgetUri(long time) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_WIDGET)
                    .appendEncodedPath(String.valueOf(time))
                    .build();
        }

        static long getTimeFromUri(Uri uri) {
            return Long.valueOf(uri.getPathSegments().get(1));
        }


    }

    public static class MainData {
        // DATA_BOARD  content://com.elorri.android.tieus/main/
        static String PATH_MAIN = "main";

        public static Uri buildBoardUri(long time) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MAIN)
                    .appendEncodedPath(String.valueOf(time))
                    .build();
        }

        static long getTimeFromUri(Uri uri) {
            return Long.valueOf(uri.getPathSegments().get(1));
        }


    }

    public static class DetailData {
        //DATA_DETAIL content://com.elorri.android.tieus/detail/
        static String PATH_DETAIL = "detail";

        public static Uri buildDetailUri(long id) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_DETAIL)
                    .appendEncodedPath(String.valueOf(id))
                    .build();
        }

        public static String getContactIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }


    public static class AddActionData {
        static String PATH_ADD_ACTION = "add_action";


        // DATA_ADD_ACTION_SELECT_ACTION  content://com.elorri.android.tieus/add_action/
        public static final Uri URI_PAGE_SELECT_ACTION = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ADD_ACTION)
                .build();


        // DATA_ADD_ACTION_SELECT_VECTOR  content://com.elorri.android.tieus/add_action/15
        public static Uri buildSelectVectorUri(String actionId) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_ADD_ACTION)
                    .appendPath(actionId)
                    .build();
        }


        // DATA_ADD_ACTION_VALIDATE  content://com.elorri.android.tieus/add_action/15/3/56789796
        public static Uri buildValidateUri(String actionId, String vectorId,
                                           String timeStart) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_ADD_ACTION)
                    .appendPath(actionId)
                    .appendPath(vectorId)
                    .appendPath(timeStart)
                    .build();
        }


        static String getActionIdFromSelectVectorUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


        static String getActionIdFromSelectValidateUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        static String getVectorIdFromSelectValidateUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }


        static String getTimeStartIdFromSelectValidateUri(Uri uri) {
            return uri.getPathSegments().get(3);
        }
    }


    /**
     * Keep info about user contact list
     */
    public static class ContactTable implements BaseColumns {
        public static String PATH_CONTACT = "contact";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT).build();

        public static final String NAME = "contact";

        public static final String COLUMN_ANDROID_CONTACT_ID = "android_contact_id";
        public static final String COLUMN_ANDROID_CONTACT_LOOKUP_KEY = "android_contact_lookup_key";
        public static final String COLUMN_ANDROID_CONTACT_NAME = "contact_name";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_SATISFACTION = "satisfaction";
        public static final String COLUMN_RESPONSE_EXPECTED_TIME_LIMIT = "expected_response_time_limit";
        public static final String COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT = "increased_expected_response_time_limit";
        public static final String COLUMN_FREQUENCY_OF_CONTACT = "frequency_of_contact";
        public static final String COLUMN_LAST_SATISFACTION_DECREASED = "last_satisfaction_update";
        public static final String COLUMN_UNFOLLOWED = "unfollowed";
        public static final String COLUMN_SATISFACTION_UNKNOWN = "satisfaction_unknown";
        public static final String COLUMN_BACKGROUND_COLOR = "background_color";

        public static final String UNFOLLOWED_ON_VALUE = "1";
        public static final String UNFOLLOWED_OFF_VALUE = "0";
        public static final String UNFOLLOWED_CONSTRAINT = "unfollowed_ck";

        public static final String SATISFACTION_UNKNOWN_ON_VALUE = "1";
        public static final String SATISFACTION_UNKNOWN_OFF_VALUE = "0";
        public static final String SATISFACTION_UNKNOWN_CONSTRAINT = "satisfaction_unknown_ck";


        public static final String VIEW_PART = "part";
        public static final String VIEW_TOTAL = "total";
        public static final String VIEW_RATIO = "ratio";

        static Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    /**
     * Contains a list of predefined actions, that the user will be able to select, when planning
     * his next interaction.
     */
    public static class ActionTable implements BaseColumns {
        static String PATH_ACTION = "action";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTION).build();

        public static final String NAME = "action";
        public static final String COLUMN_TAG_TITLE_RESOURCE_ID = "title";
        public static final String COLUMN_NAME_RESOURCE_ID = "name";
        public static final String COLUMN_SORT_ORDER = "sort_order";

        //When joining a this table with another, we'll use VIEW_ACTION_ID instead of _ID in
        // query to help sqlite distinguish between ActionTable _ID and the other table _ID
        public static final String VIEW_ACTION_ID = "action_id";
        public static final String VIEW_ACTION_NAME_RESOURCE_ID = "action_name";

        static Uri buildActionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /**
     * Table
     */
    public static class EventTable implements BaseColumns {
        public static String PATH_EVENT = "event";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();

        public static final String NAME = "event";
        public static final String COLUMN_CONTACT_ID = "contact_id";
        public static final String COLUMN_ACTION_ID = "action_id";
        public static final String COLUMN_VECTOR_ID = "vector_id";
        public static final String COLUMN_TIME_START = "time_start";
        public static final String COLUMN_TIME_END = "time_end";
        public static final String VIEW_EVENT_ID = "event_id";
        public static final String VIEW_LAST_TIME_END = "last_time_end";


        public static Uri buildEventUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /**
     * Contains a list of vectors of communication, that the user will be able to select, when
     * planning his next interaction. Vectors of communication are :
     * - Social networks installed on the user phone.
     * - Small text messages
     * - Phone
     * - Real life meetings
     */
    public static class VectorTable implements BaseColumns {
        static String PATH_VECTOR = "vector";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VECTOR).build();

        public static final String NAME = "vector";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATA = "data";
        //This column will contain the ressourceId or the package name
        public static final String COLUMN_MIMETYPE = "mimetype";

        static final String VIEW_VECTOR_ID = "vector_id";
        public static final String VIEW_VECTOR_NAME = "vector_name";

        public static final String MIMETYPE_VALUE_RESSOURCE = "ressourceId";
        public static final String MIMETYPE_VALUE_PACKAGE = "package";
        public static final String MIMETYPE_CONSTRAINT = "mimetype_ck";


        static Uri buildVectorUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
