package com.elorri.android.friendforcast.data;

/**
 * Created by Elorri on 06/05/2016.
 */
public class Projections {
    public static final String COLUMN_PROJECTION_TYPE = "projection_type";


    //TODO rename this
    public static final int VIEW_FORECAST = 0; //BoardAdapter ContactDAO.RATIO
    public static final int VIEW_TITLE = 1; //BoardAdapter DetailAdapter AddActionAdapter
    public static final int VIEW_UNMANAGED_PEOPLE = 2;//BoardAdapter
    public static final int VIEW_DELAY_PEOPLE = 3;//BoardAdapter
    public static final int VIEW_TODAY_PEOPLE = 4;//BoardAdapter
    public static final int VIEW_TODAY_DONE_PEOPLE = 5;//BoardAdapter
    public static final int VIEW_NEXT_PEOPLE = 6;//BoardAdapter
    public static final int VIEW_UNTRACKED_PEOPLE = 7;//BoardAdapter

    public static final int VIEW_EMOICON = 8; // DetailAdapter rename view_contact ?
    public static final int VIEW_ALL_ACTION = 9;// DetailAdapter
    public static final int VIEW_NEXT_ACTION = 10;// DetailAdapter
    public static final int VIEW_DONE_ACTION = 11;// DetailAdapter
    public static final int VIEW_EMPTY_CURSOR = 12;// DetailAdapter
    public static final int VIEW_EDUCATE_USER = 13;// DetailAdapter

    public static final int VIEW_ACTION = 14;
    public static final int VIEW_VECTOR_ITEM = 15;// AddActionAdapter
}
