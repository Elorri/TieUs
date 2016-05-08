package com.elorri.android.friendforcast.db;

/**
 * Created by Elorri on 06/05/2016.
 */
public class Projections {
    public static final String COLUMN_PROJECTION_TYPE = "projection_type";


    public interface MessageQuery {
       String COLUMN_MESSAGE = "_id";
        String[] PROJECTION = {
                MessageQuery.COLUMN_MESSAGE,
                Projections.VIEW_MESSAGE + " as " + Projections.COLUMN_PROJECTION_TYPE
        };
    }

    public interface EmptyCursorMessageQuery {
        String COLUMN_EMPTY_CURSOR = "_id";
        String[] PROJECTION = {
                EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR,
                Projections.COLUMN_PROJECTION_TYPE
        };

        String[] PROJECTION_QUERY = {
                EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR,
                Projections.VIEW_EMPTY_CURSOR_MESSAGE + " as " + Projections.COLUMN_PROJECTION_TYPE
        };
    }

    public interface EducateMessageQuery {
        String COLUMN_EDUCATE_MESSAGE = "_id";
        String[] PROJECTION = {
                EducateMessageQuery.COLUMN_EDUCATE_MESSAGE,
                Projections.VIEW_EDUCATE_MESSAGE + " as " + Projections.COLUMN_PROJECTION_TYPE
        };
    }



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
}
