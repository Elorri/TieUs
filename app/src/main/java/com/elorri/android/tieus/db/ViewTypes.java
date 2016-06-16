package com.elorri.android.tieus.db;

/**
 * Created by Elorri on 06/05/2016.
 * Useful class that link ContentProvider results to Adapters. We design the provider so that every
 * rows of cursors it create for display on screen is terminated with a viewtype column.
 * This viewtype is then read by the Adapter to inflate the proper xml layout file
 */
public class ViewTypes {
    public static final String COLUMN_VIEWTYPE = "viewtype";

    public static final int VIEW_FORECAST = 0;
    public static final int VIEW_TITLE = 1;
    public static final int VIEW_UNSCHEDULED_PEOPLE = 2;
    public static final int VIEW_DELAYED_PEOPLE = 3;
    public static final int VIEW_TODAY_PEOPLE = 4;
    public static final int VIEW_TODAY_DONE_PEOPLE = 5;
    public static final int VIEW_NEXT_PEOPLE = 6;
    public static final int VIEW_UNFOLLOWED_PEOPLE = 7;
    public static final int VIEW_FILL_IN_DELAY_FEEDBACK = 8;
    public static final int VIEW_UPDATE_MOOD = 9;
    public static final int VIEW_SET_UP_A_FREQUENCY_OF_CONTACT = 10;
    public static final int VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNFOLLOWED = 11;
    public static final int VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT = 12;
    public static final int VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY = 13;
    public static final int VIEW_FEEDBACK_FREQUENCY = 24;
    public static final int VIEW_CONTACT = 14;
    public static final int VIEW_ALL_ACTION = 15;
    public static final int VIEW_NEXT_ACTION = 16;
    public static final int VIEW_DONE_ACTION = 17;
    public static final int VIEW_EMPTY_CURSOR_MESSAGE = 18;
    public static final int VIEW_CONFIRM_MESSAGE = 19;
    public static final int VIEW_MESSAGE = 20;
    public static final int VIEW_ACTION = 21;
    public static final int VIEW_VECTOR_ITEM = 22;
    public static final int VIEW_ACTION_RECAP_QUERY = 23;


}
