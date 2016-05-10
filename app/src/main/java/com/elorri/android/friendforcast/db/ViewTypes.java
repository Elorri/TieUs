package com.elorri.android.friendforcast.db;

/**
 * Created by Elorri on 06/05/2016.
 */
public class ViewTypes {
    public static final String COLUMN_VIEWTYPE = "viewtype";


    //TODO rename this
    public static final int VIEW_FORECAST = 0; //BoardAdapter ContactDAO.RATIO
    public static final int VIEW_TITLE = 1; //BoardAdapter DetailAdapter AddActionAdapter
    public static final int VIEW_UNMANAGED_PEOPLE = 2;//BoardAdapter
    public static final int VIEW_DELAY_PEOPLE = 3;//BoardAdapter
    public static final int VIEW_TODAY_PEOPLE = 4;//BoardAdapter
    public static final int VIEW_TODAY_DONE_PEOPLE = 5;//BoardAdapter
    public static final int VIEW_NEXT_PEOPLE = 6;//BoardAdapter
    public static final int VIEW_UNTRACKED_PEOPLE = 7;//BoardAdapter

    public static final int VIEW_CONTACT = 8; // DetailAdapter rename view_contact ?
    public static final int VIEW_ALL_ACTION = 9;// DetailAdapter
    public static final int VIEW_NEXT_ACTION = 10;// DetailAdapter
    public static final int VIEW_DONE_ACTION = 11;// DetailAdapter

    public static final int VIEW_EMPTY_CURSOR_MESSAGE = 12;// DetailAdapter
    public static final int VIEW_EDUCATE_MESSAGE = 13;// DetailAdapter
    public static final int VIEW_MESSAGE = 14;// DetailAdapter

    public static final int VIEW_ACTION = 15;
    public static final int VIEW_VECTOR_ITEM = 16;// AddActionAdapter
    public static final int VIEW_ACTION_RECAP_QUERY = 17;// AddActionAdapter
}
