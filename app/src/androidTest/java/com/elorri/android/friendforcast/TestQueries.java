package com.elorri.android.friendforcast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.data.FriendForecastDbHelper;
import com.elorri.android.friendforcast.db.ContactActionVectorEventDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.extra.DateUtils;
import com.elorri.android.friendforcast.extra.Status;
import com.elorri.android.friendforcast.extra.Tools;

/**
 * Created by Elorri on 13/05/2016.
 */
public class TestQueries extends AndroidTestCase {


    TestGivens mTestGivens;
    SQLiteDatabase db;

    public static final long 	now	_19may2016at12h40m52s	=	1463654452071	l;
    public static final long 	todayStart	_19may2016at00h00m00s	=	1463608800000	l;
    public static final long 	tomorrowStart	_20may2016at00h00m00s	=	1463695200000	l;
    public static final long 	_32daysAgo	_17april2016at12h40m52s	=	1460889652071	l;
    public static final long 	_31daysAgo	_18april2016at12h40m52s	=	1460976052071	l;
    public static final long 	_30daysAgo	_19april2016at12h40m52s	=	1461062452071	l;
    public static final long 	_26daysAgo	_23april2016at12h40m52s	=	1461408052071	l;
    public static final long 	_25daysAgo	_24april2016at12h40m52s	=	1461494452071	l;
    public static final long 	_4daysAgo	_15may2016at12h40m52s	=	1463308852071	l;
    public static final long 	_3daysAgo	_16may2016at12h40m52s	=	1463395252071	l;
    public static final long 	_2daysAgo	_17may2016at12h40m52s	=	1463481652071	l;
    public static final long 	_1dayAgo	_18may2016at12h40m52s	=	1463568052071	l;
    public static final long 	_in2second	_19may2016at12h40m53s	=	'1463654453071	l;
    public static final long 	_in1second	_19may2016at12h40m54s	=	'1463654454071	l;
    public static final long 	_in1day	_20may2016at12h40m52s	=	1463740852071	l;
    public static final long 	_in2days	_21may2016at12h40m52s	=	1463827252071	l;
    public static final long 	_in3days	_22may2016at12h40m52s	=	1464000052071	l;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();

        mTestGivens.test_fillContactTable();
        mTestGivens.test_fillActionTable();
        mTestGivens.test_fillVectorTable();
        mTestGivens.test_fillEventTable();

        db = new FriendForecastDbHelper(mContext).getReadableDatabase();
    }

    public void test_joint_table_contact_action_vector_event() {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + ""
                + ContactActionVectorEventDAO.JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT);
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.JOINT_TABLE_CONTACT_ACTION_VECTOR_EVENT, null);

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.JOINT_TABLE_PROJECTION)
                + "row |14|5|22|32|Thank you|1400000000000|1400000010000|839|298i5.3552i274b0e968b8a47fv|"
                + "Mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|300000|290000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |13|5|21|32|Thank you|1400000060000|1400000070000|838|298i5.3552i264b0e968b8a47fv|"
                + "Émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|300000|230000|240000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |12|5|20|32|Thank you|1400000280000|1400000290000|837|298i5.3552i264b0e968b8a46fv|"
                + "Denis|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|10000|20000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |9|5|17|32|Thank you|1400000280000|1400000290000|834|298i5.3552i264b0e968b8a42fl|"
                + "Jacques|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |10|5|18|32|Thank you|1400000280000|1400000290000|835|298i5.3552i264b0e968b8a42fv|"
                + "Jeanne|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|10000|50000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |11|5|19|32|Thank you|1400000280000|1400000290000|836|298i5.3552i264b0e968b8a42fd|"
                + "Mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|null|10000|50000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |8|5|16|32|Thank you|1400000280000|null|833|298i5.3552i264b0e968b8a42fk|"
                + "Pierre|null|2130837600|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |16|5|24|32|Thank you|1400000321000|1400000322000|850|288i7.3552i264b0e968b8a42ff|"
                + "Emma|null|" + R.drawable.ic_social_network + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |15|5|23|32|Thank you|1400000321000|null|840|298i7.3552i264b0e968b8a42ff|"
                + "Françoise|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |17|5|25|32|Thank you|1400000360000|null|851|290i5.3552i264b0e968b8a42fk|"
                + "Bernard|null|2130837600|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_managed_people() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.ManagedPeopleQuery
                .SELECT_MANAGED_PEOPLE, null);
        assertEquals(3, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.ManagedPeopleQuery.PROJECTION)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_unmanaged_people() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT, null);
        assertEquals(8, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_social_network + "|10000|" + (20000)
                + "|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_social_network + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_social_network + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|290000|300000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|230000|240000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_needs_to_fill_in_delay_feedback() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT, null);
        assertEquals(2, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|"
                + R.drawable.ic_social_network + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_that_needs_mood_update() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT,
                new String[]{String.valueOf(now), String.valueOf(R.drawable.ic_social_network)});
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.PROJECTION)
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_social_network + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_set_up_a_frequency_of_contact() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT,
                null);
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_be_asked_for_feedback_or_moved_to_untrack() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT,
                new String[]{String.valueOf(R.drawable.ic_social_network), String.valueOf(now)});
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_social_network + "|10000|20000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_approching_end_of_most_suitable_contact_delay() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_BEFORE_BIND
                        + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery
                        .SELECT_AFTER_BIND, null);

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "now " + now + "- "
                + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_BEFORE_BIND
                + now + ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.SELECT_AFTER_BIND);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleApprochingFrequencyQuery.PROJECTION)
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|230000|240000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_who_decreased_mood_today() {

        int updatedRows = db.update(FriendForecastContract.ContactTable.NAME,
                ContactDAO.getContentValues(now),
                ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_BEFORE_BIND
                        + now
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery
                        .UPDATE_AFTER_BIND, null);

        assertEquals(1, updatedRows);

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT,
                new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(mContext))});

        Log.e("FF", ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT + " - "
                + String.valueOf(Status.getLastUserMoodsConfirmAware(mContext)));

        assertEquals(1, cursor.getCount());

        cursor.moveToFirst();
        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.PROJECTION)
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|290000|300000|300000|"
                + cursor.getString(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.COL_LAST_MOOD_DECREASED) + "|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";

        cursor.moveToPosition(-1);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_should_already_be_contacted() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.DelayPeopleQuery.SELECT,
                new String[]{String.valueOf(now)});

        Log.e("FF", ContactActionVectorEventDAO.DelayPeopleQuery.SELECT + " - "
                + String.valueOf(now));

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|Thank you|1400000280000|com.google.android.gm|package" + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_to_contact_today() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayPeopleQuery.SELECT,
                new String[]{String.valueOf(todayStart), String.valueOf(todayEnd)});

        Log.e("FF", ContactActionVectorEventDAO.TodayPeopleQuery.SELECT + " - "
                + now + "" + todayEnd);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|1400000321000|com.google.android.gm|package|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_contacted_today() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT,
                new String[]{String.valueOf(todayStart), String.valueOf(todayEnd)});

        Log.e("FF", ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT + " - "
                + now + "" + todayEnd);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|1400000321000|com.google.android.gm|package|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_next_people_to_contact() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.NextPeopleQuery.SELECT,
                new String[]{String.valueOf(tomorrow)});

        Log.e("FF", ContactActionVectorEventDAO.NextPeopleQuery.SELECT + " - "
                + tomorrow);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.NextPeopleQuery.PROJECTION)
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|1400000360000|com.google.android.gm|package|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_untracked_people() {

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.UntrackedPeopleQuery.SELECT, null);

        assertEquals(11, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UntrackedPeopleQuery.PROJECTION)
                + "row |45|951|390i5.3552i264b0e968b8a42fk|bernard_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network + "|10000|" + (20000)
                + "|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network + "|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network + "|10000|" + (50000)
                + "|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|290000|300000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|230000|240000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_ON_VALUE + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


    public void test_people_eligible_for_updating_feedback_delay_only() {
        Cursor cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFillInDelayAloneUpdateQuery.SELECT + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFillInDelayAloneUpdateQuery.PROJECTION,
                null, null, null, null, null);
        assertEquals(5, cursor.getCount());


        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInDelayAloneUpdateQuery.PROJECTION)
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_social_network + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|10000|50000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_social_network + "|10000|20000|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network
                + "|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_eligible_for_updating_frequency() {
        Cursor cursor = db.query(
                "(" + ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery.SELECT + ")",
                ContactActionVectorEventDAO.PeopleElligibleForFrequencyUpdateQuery.PROJECTION,
                null, null, null, null, null);
        assertEquals(3, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(
                ContactActionVectorEventDAO.PeopleElligibleForFillInDelayAloneUpdateQuery.PROJECTION)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|10000|" + (50000)
                + "|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|230000|240000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|290000|300000|300000|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public static String getFriendlyDateTimeString(Context context, long date) {
        return DateUtils.fromLongToString(date, DateUtils.TIMESTAMP_FORMAT, Tools
                .getMostSuitableLocale());
    }

    public void test_print_dates() {
        long now = System.currentTimeMillis();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "Now - "
                + getFriendlyDateTimeString(mContext, now) + " : " + now);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "Today start - "
                + getFriendlyDateTimeString(mContext, DateUtils.setZeroDay(now))
                + " : " + DateUtils.setZeroDay(now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "Tomorrow start - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(1, DateUtils.setZeroDay(now)))
                + " : " + DateUtils.addDay(1, DateUtils.setZeroDay(now)));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 32 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-32, now)) + " : "
                + DateUtils.addDay(-32, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 31 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-31, now)) + " : "
                + DateUtils.addDay(-31, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 30 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-30, now)) + " : "
                + DateUtils.addDay(-30, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 26 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-26, now)) + " : "
                + DateUtils.addDay(-26, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 25 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-25, now)) + " : "
                + DateUtils.addDay(-25, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 4 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-4, now)) + " : "
                + DateUtils.addDay(-4, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 3 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-3, now)) + " : "
                + DateUtils.addDay(-3, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "There is 2 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-2, now)) + " : "
                + DateUtils.addDay(-2, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "There is 1 day - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(-1, now)) + " : "
                + DateUtils.addDay(-1, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "In one second - "
                + getFriendlyDateTimeString(mContext, now + 1000) + " : " + (now + 1000));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "In 2 seconds - "
                + getFriendlyDateTimeString(mContext, now + 2000) + " : " + (now + 2000));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "In 1 day - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(1, now)) + " : "
                + DateUtils.addDay(1, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "In 2 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(2, now)) + " : "
                + DateUtils.addDay(2, now));
        Log.e("FF", Thread.currentThread().getStackTrace()[1] + "In 4 days - "
                + getFriendlyDateTimeString(mContext, DateUtils.addDay(4, now)) + " : "
                + DateUtils.addDay(4, now));

    }


}
