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
    public static final int VIEW_FILL_IN_RESPONSE_TIME_LIMIT = 8;
    public static final int VIEW_UPDATE_SATISFACTION = 9;
    public static final int VIEW_SET_UP_A_FREQUENCY_OF_CONTACT = 10;
    public static final int VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED = 11;
    public static final int VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT = 12;
    public static final int VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY = 13;
    public static final int VIEW_RESPONSE_FREQUENCY = 24;
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
