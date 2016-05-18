package com.elorri.android.friendforcast;

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

/**
 * Created by Elorri on 13/05/2016.
 */
public class TestQueries extends AndroidTestCase {

    TestGivens mTestGivens;
    SQLiteDatabase db;

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

        long now = DateUtils.todayStart();
        long in25days = DateUtils.addDay(25, now);
        long moreThanTwoThirdOf30days = in25days - now;

        long in30days = DateUtils.addDay(30, now);
        long frequency30days = in30days - now;


        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.JOINT_TABLE_PROJECTION)
                + "row |14|5|22|32|Thank you|" + (now - frequency30days) + "|"
                + (now - frequency30days) + "|839|298i5.3552i274b0e968b8a47fv|"
                + "Mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + frequency30days + "|"
                + DateUtils.addDay(-2, now) + "|" + DateUtils.addDay(-1, now) + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |13|5|21|32|Thank you|" + DateUtils.addDay(-1, (now - moreThanTwoThirdOf30days)) + "|"
                + (now - moreThanTwoThirdOf30days) + "|838|298i5.3552i264b0e968b8a47fv|"
                + "Émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|" + frequency30days + "|" + DateUtils.addDay(-2, now)
                + "|" + DateUtils.addDay(-1, now) + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |12|5|20|32|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|837|298i5.3552i264b0e968b8a46fv|"
                + "Denis|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|" + DateUtils.addDay(-2, now)
                + "|" + DateUtils.addDay(-1, now) + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |9|5|17|32|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|834|298i5.3552i264b0e968b8a42fl|"
                + "Jacques|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |10|5|18|32|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|835|298i5.3552i264b0e968b8a42fv|"
                + "Jeanne|null|" + R.drawable.ic_social_network
                + "|Gmail|com.google.android.gm|package|null|" + DateUtils.addDay(-2, now)
                + "|" + DateUtils.addDay(+2, now) + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |11|5|19|32|Thank you|" + DateUtils.addDay(-4, now) + "|"
                + DateUtils.addDay(-3, now) + "|836|298i5.3552i264b0e968b8a42fd|"
                + "Mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp
                + "|Gmail|com.google.android.gm|package|null|" + DateUtils.addDay(-2, now)
                + "|" + DateUtils.addDay(+2, now) + "|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |8|5|16|32|Thank you|" + DateUtils.addDay(-4, now) + "|null|833|298i5.3552i264b0e968b8a42fk|"
                + "Pierre|null|2130837600|Gmail|com.google.android.gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |16|5|24|32|Thank you|" + (DateUtils.todayStart() + 1000) + "|" + (DateUtils.todayStart() + 2000) + "|850|"
                + "288i7.3552i264b0e968b8a42ff|"
                + "Emma|null|" + R.drawable.ic_social_network + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|"
                + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |15|5|23|32|Thank you|" + (DateUtils.todayStart() + 1000) + "|null|840|"
                + "298i7.3552i264b0e968b8a42ff|"
                + "Françoise|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|Gmail|com.google.android" +
                ".gm|package|null|null|null|null|" + FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE + "|\n"
                + "row |17|5|25|32|Thank you|" + DateUtils.addDay(4, now) + "|null|851|"
                + "290i5.3552i264b0e968b8a42fk|"
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
                + "row |25|851|290i5.3552i264b0e968b8a42fk|bernard|null|2130837600|\n"
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|2130837600|\n"
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|2130837600|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_unmanaged_people() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.UnmanagedPeopleQuery.SELECT, null);
        assertEquals(8, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.UnmanagedPeopleQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|" + R.drawable.ic_social_network + "|\n"
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network + "|\n"
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|" + R.drawable.ic_social_network + "|\n"
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |15|832|298i5.3552i264b0e968b8a42ff|paul|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |21|838|298i5.3552i264b0e968b8a47fv|émilie|null|" + R.drawable.ic_sentiment_neutral_black_48dp + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_needs_to_fill_in_delay_feedback() {
        Cursor cursor = db.rawQuery(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.SELECT, null);
        assertEquals(2, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|" + R.drawable.ic_social_network + "|\n"
                + "row |17|834|298i5.3552i264b0e968b8a42fl|jacques|null|" + R.drawable.ic_social_network + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_that_needs_mood_update() {
        long now = System.currentTimeMillis();
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedMoodUpdateQuery.SELECT,
                new String[]{String.valueOf(now), String.valueOf(R.drawable.ic_social_network)});
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION)
                + "row |18|835|298i5.3552i264b0e968b8a42fv|jeanne|null|"
                + R.drawable.ic_social_network + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_set_up_a_frequency_of_contact() {
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleThatNeedFrequencyQuery.SELECT,
                new String[]{String.valueOf(R.drawable.ic_social_network)});
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleThatNeedsToFillInDelayFeedbackQuery.PROJECTION)
                + "row |19|836|298i5.3552i264b0e968b8a42fd|mathieu|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_need_to_be_asked_for_feedback_or_moved_to_untrack() {
        long now = System.currentTimeMillis();
        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.AskForFeedbackQuery.SELECT,
                new String[]{String.valueOf(R.drawable.ic_social_network), String.valueOf(now)});
        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.AskForFeedbackQuery.PROJECTION)
                + "row |20|837|298i5.3552i264b0e968b8a46fv|denis|null|"
                + R.drawable.ic_social_network + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }

    public void test_people_approching_end_of_most_suitable_contact_delay() {
        long now = System.currentTimeMillis();

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
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_who_decreased_mood_today() {
        long now = System.currentTimeMillis();

        db.update(FriendForecastContract.ContactTable.NAME,
                ContactDAO.getContentValues(now),
                ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.UPDATE_BEFORE_BIND
                        + now
                        + ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery
                        .UPDATE_AFTER_BIND, null);

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT,
                new String[]{String.valueOf(Status.getLastUserMoodsConfirmAware(mContext))});

        Log.e("FF", ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.SELECT + " - "
                + String.valueOf(Status.getLastUserMoodsConfirmAware(mContext)));

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.PeopleWhoDecreasedMoodQuery.PROJECTION)
                + "row |22|839|298i5.3552i274b0e968b8a47fv|mélissa|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_that_should_already_be_contacted() {
        long now = DateUtils.todayStart();

        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.DelayPeopleQuery.SELECT,
                new String[]{String.valueOf(now)});

        Log.e("FF", ContactActionVectorEventDAO.DelayPeopleQuery.SELECT + " - "
                + String.valueOf(now));

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.DelayPeopleQuery.PROJECTION)
                + "row |16|833|298i5.3552i264b0e968b8a42fk|pierre|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + DateUtils.addDay(-4, now) + "|com.google.android.gm|package"
                + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_to_contact_today() {
        String todayStart = String.valueOf(DateUtils.todayStart());
        String todayEnd = String.valueOf(DateUtils.tomorrowStart());


        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayPeopleQuery.SELECT,
                new String[]{todayStart, todayEnd});

        Log.e("FF", ContactActionVectorEventDAO.TodayPeopleQuery.SELECT + " - "
                + todayStart + "" + todayEnd);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayPeopleQuery.PROJECTION)
                + "row |23|840|298i7.3552i264b0e968b8a42ff|françoise|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_people_contacted_today() {
        String todayStart = String.valueOf(DateUtils.todayStart());
        String todayEnd = String.valueOf(DateUtils.tomorrowStart());


        Cursor cursor = db.rawQuery(
                ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT,
                new String[]{todayStart, todayEnd});

        Log.e("FF", ContactActionVectorEventDAO.TodayDonePeopleQuery.SELECT + " - "
                + todayStart + "" + todayEnd);

        assertEquals(1, cursor.getCount());

        String cursorString = "\n"
                + TestUtility.getCursorHeaderString(ContactActionVectorEventDAO.TodayDonePeopleQuery.PROJECTION)
                + "row |24|850|288i7.3552i264b0e968b8a42ff|emma|null|"
                + R.drawable.ic_social_network + "|"
                + "Thank you|" + (DateUtils.todayStart() + 1000) + "|com.google.android.gm|package|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }

    public void test_next_people_to_contact() {
        long tomorrow = DateUtils.tomorrowStart();
        long now = DateUtils.todayStart();


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
                + "Thank you|" + DateUtils.addDay(4, now) + "|com.google.android.gm|package|\n";

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
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |40|937|398i5.3552i264b0e968b8a46fv|denis_untracked|null|"
                + R.drawable.ic_social_network + "|\n"
                + "row |44|950|398i9.3552i264b0e968b8a42ff|emma_untracked|null|"
                + R.drawable.ic_social_network + "|\n"
                + "row |43|940|398i7.3552i264b0e968b8a42ff|françoise_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |37|934|398i5.3552i264b0e968b8a42fl|jacques_untracked|null|"
                + R.drawable.ic_social_network + "|\n"
                + "row |38|935|398i5.3552i264b0e968b8a42fv|jeanne_untracked|null|"
                + R.drawable.ic_social_network + "|\n"
                + "row |39|936|398i5.3552i264b0e968b8a42fd|mathieu_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |42|939|398i5.3552i274b0e968b8a47fv|mélissa_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |35|932|398i5.3552i264b0e968b8a42ff|paul_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |36|933|398i5.3552i264b0e968b8a42fk|pierre_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n"
                + "row |41|938|398i5.3552i264b0e968b8a47fv|émilie_untracked|null|"
                + R.drawable.ic_sentiment_neutral_black_48dp + "|\n";

        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));

        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();
    }


}
