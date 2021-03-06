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
package com.elorri.android.capstone.tieus.db;

import com.elorri.android.capstone.tieus.data.TieUsContract;

/**
 * Created by Elorri on 12/04/2016.
 * This class joins tables event, action, contact and vector together in a view called
 * JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT and then question this view to answer  queries needed by
 * the app.
 */
public class ContactActionVectorEventDAO {


    public static final String[] JOINT_TABLE_PROJECTION = new String[]{
            TieUsContract.EventTable.VIEW_EVENT_ID,
            TieUsContract.EventTable.COLUMN_ACTION_ID,
            TieUsContract.EventTable.COLUMN_CONTACT_ID,
            TieUsContract.EventTable.COLUMN_VECTOR_ID,
            TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
            TieUsContract.EventTable.COLUMN_TIME_START,
            TieUsContract.EventTable.COLUMN_TIME_END,
            TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
            TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
            TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
            TieUsContract.ContactTable.COLUMN_THUMBNAIL,
            TieUsContract.ContactTable.COLUMN_SATISFACTION,
            TieUsContract.VectorTable.VIEW_VECTOR_NAME,
            TieUsContract.VectorTable.COLUMN_DATA,
            TieUsContract.VectorTable.COLUMN_MIMETYPE,
            TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
            TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
            TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
            TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
            TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
            TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
            TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR
    };


    public static final String JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT = "select "
            + TieUsContract.EventTable.VIEW_EVENT_ID + ", "
            + TieUsContract.EventTable.COLUMN_ACTION_ID + ", "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + ", "
            + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.EventTable.COLUMN_TIME_END + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
            + TieUsContract.VectorTable.COLUMN_NAME + " as "
            + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
            + TieUsContract.VectorTable.COLUMN_DATA + ", "
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
            + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
            + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from (select "
            + TieUsContract.EventTable.VIEW_EVENT_ID + ", "
            + TieUsContract.EventTable.COLUMN_ACTION_ID + ", "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + ", "
            + TieUsContract.ActionTable.NAME + "."
            + TieUsContract.ActionTable.COLUMN_NAME_RESOURCE_ID + " as "
            + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.EventTable.COLUMN_TIME_END + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
            + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
            + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
            + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from (select "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable._ID + " as "
            + TieUsContract.EventTable.VIEW_EVENT_ID + ", "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable.COLUMN_ACTION_ID + ", "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + ", "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.EventTable.NAME + "."
            + TieUsContract.EventTable.COLUMN_TIME_END + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
            + TieUsContract.EventTable.NAME + " inner join "
            + TieUsContract.ContactTable.NAME + " on "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + "="
            + TieUsContract.ContactTable.NAME + "."
            + TieUsContract.ContactTable._ID + ") as ec inner join "
            + TieUsContract.ActionTable.NAME + " on ec."
            + TieUsContract.EventTable.COLUMN_ACTION_ID + "="
            + TieUsContract.ActionTable.NAME + "."
            + TieUsContract.ActionTable._ID + ") as cae inner join "
            + TieUsContract.VectorTable.NAME + " on cae."
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + "="
            + TieUsContract.VectorTable.NAME + "."
            + TieUsContract.VectorTable._ID + " order by "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " asc";


    public static final String JOINT_LAST_ACTION_EVENT = "select "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", max("
            + TieUsContract.EventTable.COLUMN_TIME_END + ") as "
            + TieUsContract.EventTable.VIEW_LAST_TIME_END + " from ("
            + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") group by "
            + TieUsContract.EventTable.COLUMN_CONTACT_ID;


    public static final String JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT = "select "
            + TieUsContract.EventTable.VIEW_EVENT_ID + ", "
            + TieUsContract.EventTable.COLUMN_ACTION_ID + ", lac."
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
            + TieUsContract.EventTable.COLUMN_VECTOR_ID + ", "
            + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.EventTable.COLUMN_TIME_END + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
            + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
            + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
            + TieUsContract.VectorTable.COLUMN_DATA + ", "
            + TieUsContract.VectorTable.COLUMN_MIMETYPE + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
            + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
            + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
            + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
            + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
            + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
            + JOINT_LAST_ACTION_EVENT + ") lac inner join ("
            + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") e on lac."
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + "= e."
            + TieUsContract.EventTable.COLUMN_CONTACT_ID + " order by "
            + TieUsContract.EventTable.COLUMN_TIME_START + ", "
            + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " asc";


    public interface PeopleQuery {
        int COL_ID = 0;
        int COL_ANDROID_CONTACT_ID = 1;
        int COL_ANDROID_CONTACT_LOOKUP_KEY = 2;
        int COL_CONTACT_NAME = 3;
        int COL_THUMBNAIL = 4;
        int COL_SATISFACTION_ID = 5;
        int COL_EXPECTED_RESPONSE_TIME_LIMIT = 6;
        int COL_RESPONSE_INCREASED_EXPECTED_DELAY = 7;
        int COL_FREQUENCY_OF_CONTACT = 8;
        int COL_LAST_SATISFACTION_DECREASED = 9;
        int COL_UNFOLLOWED = 10;
        int COL_SATISFACTION_UNKNOWN = 11;
        int COL_BACKGROUND_COLOR = 12;

        String[] PROJECTION = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR
        };


        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                ViewTypes.COLUMN_VIEWTYPE
        };
    }


    public interface SchedulePeopleQuery extends PeopleQuery {

        String SELECT_MANAGED_PEOPLE = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is null order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";
    }


    public interface UnscheduledPeopleQuery extends PeopleQuery {


        String SELECT = "select "
                + TieUsContract.ContactTable._ID + " as "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " = "
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " except "
                + SchedulePeopleQuery.SELECT_MANAGED_PEOPLE;

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UNSCHEDULED_PEOPLE + " as "
                + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + UnscheduledPeopleQuery.SELECT + ")";
    }


    public interface PeopleThatNeedsToFillInTimeLimitResponseQuery extends PeopleQuery {


        String SELECT = "select distinct "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is not null and "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + " is null order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface PeopleThatNeedSatisfactionUpdateQuery extends PeopleQuery {


        String SELECT_BEFORE_BIND = "select distinct "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where ";

        String SELECT_AFTER_BIND = " between ("
                + TieUsContract.EventTable.COLUMN_TIME_END + "+"
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ") and ("
                + TieUsContract.EventTable.COLUMN_TIME_END + "+"
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ") and "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + "="
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + " and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UPDATE_SATISFACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }

    public interface PeopleThatNeedFrequencyQuery extends PeopleQuery {


        String SELECT = "select distinct "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + " is not null and "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + "!= "
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + " and "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";


    }

    public interface AskForResponseQuery extends PeopleQuery {

        String SELECT_BEFORE_BIND = "select distinct "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_TABLE_CONTACT_LAST_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + "= "
                + TieUsContract.ContactTable.SATISFACTION_UNKNOWN_ON_VALUE + " and ";

        String SELECT_AFTER_BIND = " > ("
                + TieUsContract.EventTable.COLUMN_TIME_END + "+"
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ") and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";


        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }


    public interface PeopleApprochingFrequencyQuery extends PeopleQuery {

        int COL_TIME_END_2THIRD_FREQ = 13;
        int COL_TIME_END_FREQ = 14;


        String SELECT_BEFORE_BIND = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + JOINT_LAST_ACTION_EVENT + ") inner join "
                + TieUsContract.ContactTable.NAME + " on "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + "="
                + TieUsContract.ContactTable.NAME + "."
                + TieUsContract.ContactTable._ID + " where ";

        String SELECT_AFTER_BIND = " between ("
                + TieUsContract.EventTable.VIEW_LAST_TIME_END + " + "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "*(2.0/3)) and( "
                + TieUsContract.EventTable.VIEW_LAST_TIME_END + " + "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ") and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";


        String SELECT_BEFORE_BIND_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT_BEFORE_BIND;

        String SELECT_AFTER_BIND_WITH_VIEWTYPE = SELECT_AFTER_BIND + ")";


    }

    public interface PeopleWhoDecreasedSatisfactionQuery extends PeopleQuery {

        String UPDATE_BEFORE_BIND = ""
                + TieUsContract.ContactTable._ID + " in (select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + " from ("
                + JOINT_LAST_ACTION_EVENT + ") where ";

        String UPDATE_AFTER_BIND = " > ("
                + TieUsContract.EventTable.VIEW_LAST_TIME_END + " + "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + "))";


        //This select works only if the update above have been applied before
        String SELECT = "select "
                + TieUsContract.ContactTable._ID + " as "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + " > ? and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface DelayedPeopleQuery extends PeopleQuery {
        int COL_ACTION_NAME_RESOURCE_ID = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_NAME = 15;
        int COL_VECTOR_DATA = 16;
        int COL_VECTOR_MIMETYPE = 17;

        String[] PROJECTION = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };


        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
                + TieUsContract.EventTable.COLUMN_TIME_START + ", "
                + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.EventTable.COLUMN_TIME_START + "< ? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " = "
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by "
                + TieUsContract.EventTable.COLUMN_TIME_START + " asc";


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_DELAYED_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }

    public interface TodayPeopleQuery extends PeopleQuery {

        int COL_ACTION_NAME_RESOURCE_ID = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_NAME = 15;
        int COL_VECTOR_DATA = 16;
        int COL_VECTOR_MIMETYPE = 17;

        String[] PROJECTION = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
                + TieUsContract.EventTable.COLUMN_TIME_START + ", "
                + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.EventTable.COLUMN_TIME_START + " >= ? and "
                + TieUsContract.EventTable.COLUMN_TIME_START + " < ? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " = "
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by "
                + TieUsContract.EventTable.COLUMN_TIME_START + " asc";


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_TODAY_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }

    public interface TodayDonePeopleQuery extends PeopleQuery {

        int COL_ACTION_NAME_RESOURCE_ID = 13;
        int COL_TIME_END = 14;
        int COL_VECTOR_NAME = 15;
        int COL_VECTOR_DATA = 16;
        int COL_VECTOR_MIMETYPE = 17;


        String[] PROJECTION = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_END,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_END,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
                + TieUsContract.EventTable.COLUMN_TIME_END + ", "
                + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is not null and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " >= ? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " < ? order by "
                + TieUsContract.EventTable.COLUMN_TIME_END + " desc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_TODAY_DONE_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }

    public interface NextPeopleQuery extends PeopleQuery {

        int COL_ACTION_NAME_RESOURCE_ID = 13;
        int COL_TIME_START = 14;
        int COL_VECTOR_NAME = 15;
        int COL_VECTOR_DATA = 16;
        int COL_VECTOR_MIMETYPE = 17;

        String[] PROJECTION = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE
        };

        String[] PROJECTION_WITH_VIEWTYPE = new String[]{
                TieUsContract.EventTable.COLUMN_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY,
                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME,
                TieUsContract.ContactTable.COLUMN_THUMBNAIL,
                TieUsContract.ContactTable.COLUMN_SATISFACTION,
                TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT,
                TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT,
                TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED,
                TieUsContract.ContactTable.COLUMN_UNFOLLOWED,
                TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN,
                TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + ", "
                + TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID + ", "
                + TieUsContract.EventTable.COLUMN_TIME_START + ", "
                + TieUsContract.VectorTable.VIEW_VECTOR_NAME + ", "
                + TieUsContract.VectorTable.COLUMN_DATA + ", "
                + TieUsContract.VectorTable.COLUMN_MIMETYPE + " from ("
                + JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT + ") where "
                + TieUsContract.EventTable.COLUMN_TIME_START + " >= ? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " = "
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " order by "
                + TieUsContract.EventTable.COLUMN_TIME_START + " asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_NEXT_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }


    public interface UnfollowedPeopleQuery extends PeopleQuery {

        String SELECT = "select "
                + TieUsContract.ContactTable._ID + " as "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + " = "
                + TieUsContract.ContactTable.UNFOLLOWED_ON_VALUE + " order by lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") asc";

        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_UNFOLLOWED_PEOPLE + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";
    }


    public interface VectorActionByContactIdQuery {
        int COL_EVENT_ID = 0;
        int COL_VECTOR_DATA = 1;
        int COL_VECTOR_MIMETYPE = 2;
        int COL_VECTOR_NAME = 3;
        int COL_ACTION_NAME_RESOURCE_ID = 4;
        int COL_TIME_START = 5;
        int COL_TIME_END = 6;
        int COL_PROJECTION_TYPE = 7;

        String SELECTION_ALL = TieUsContract.EventTable.COLUMN_CONTACT_ID + "=?";
        String SELECTION_UNDONE = TieUsContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is null";
        String SELECTION_DONE = TieUsContract.EventTable.COLUMN_CONTACT_ID + "=? and "
                + TieUsContract.EventTable.COLUMN_TIME_END + " is not null";

        String SORT_ORDER_UNDONE = TieUsContract.EventTable.COLUMN_TIME_START + " asc";
        String SORT_ORDER_DONE = TieUsContract.EventTable.COLUMN_TIME_END + " desc";

        String[] PROJECTION_ALL = {
                TieUsContract.EventTable.VIEW_EVENT_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_ALL_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_NEXT = {
                TieUsContract.EventTable.VIEW_EVENT_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_NEXT_QUERY = {
                TieUsContract.EventTable.VIEW_EVENT_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_NEXT_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_DONE = {
                TieUsContract.EventTable.VIEW_EVENT_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END,
                ViewTypes.COLUMN_VIEWTYPE
        };

        String[] PROJECTION_DONE_QUERY = {
                TieUsContract.EventTable.VIEW_EVENT_ID,
                TieUsContract.VectorTable.COLUMN_DATA,
                TieUsContract.VectorTable.COLUMN_MIMETYPE,
                TieUsContract.VectorTable.VIEW_VECTOR_NAME,
                TieUsContract.ActionTable.VIEW_ACTION_NAME_RESOURCE_ID,
                TieUsContract.EventTable.COLUMN_TIME_START,
                TieUsContract.EventTable.COLUMN_TIME_END,
                ViewTypes.VIEW_DONE_ACTION + " as " + ViewTypes.COLUMN_VIEWTYPE
        };
    }

    public interface PeopleElligibleForFillInTimeLimitAloneUpdateQuery extends PeopleQuery {

        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + PeopleThatNeedsToFillInTimeLimitResponseQuery.SELECT + ") union select "
                + TieUsContract.ContactTable._ID + " as "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + " is not null and "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE + " except "
                + PeopleThatNeedFrequencyQuery.SELECT;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


    public interface PeopleElligibleForFrequencyUpdateQuery extends PeopleQuery {

        String SELECT = "select "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from ("
                + PeopleThatNeedFrequencyQuery.SELECT + ") union select "
                + TieUsContract.ContactTable._ID + " as "
                + TieUsContract.EventTable.COLUMN_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + ", "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + ", lower("
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ") as "
                + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + ", "
                + TieUsContract.ContactTable.COLUMN_THUMBNAIL + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_RESPONSE_INCREASED_EXPECTED_TIME_LIMIT + ", "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + ", "
                + TieUsContract.ContactTable.COLUMN_LAST_SATISFACTION_DECREASED + ", "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + ", "
                + TieUsContract.ContactTable.COLUMN_SATISFACTION_UNKNOWN + ", "
                + TieUsContract.ContactTable.COLUMN_BACKGROUND_COLOR + " from "
                + TieUsContract.ContactTable.NAME + " where "
                + TieUsContract.ContactTable.COLUMN_FREQUENCY_OF_CONTACT + " is not null and "
                + TieUsContract.ContactTable.COLUMN_UNFOLLOWED + "="
                + TieUsContract.ContactTable.UNFOLLOWED_OFF_VALUE;


        String SELECT_WITH_VIEWTYPE = "select *, "
                + ViewTypes.VIEW_RESPONSE_FREQUENCY + " as " + ViewTypes.COLUMN_VIEWTYPE
                + " from (" + SELECT + ")";

    }


}
