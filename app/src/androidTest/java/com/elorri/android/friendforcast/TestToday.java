package com.elorri.android.friendforcast;

import android.test.AndroidTestCase;

import com.elorri.android.friendforcast.extra.Status;

/**
 * Created by Elorri on 20/05/2016.
 * This class create a set of contact with actions registered in the near past and future.
 * It is meant to be used as a demo.
 */
public class TestToday extends AndroidTestCase {

    TestGivens mTestGivens;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mTestGivens = new TestGivens(mContext);
        long now = System.currentTimeMillis();
        long diff = now - TestGivens.now_19may2016at12h40m52s;

        TestGivens.now_19may2016at12h40m52s = TestGivens.now_19may2016at12h40m52s + diff;
        TestGivens.todayStart_19may2016at00h00m00s = TestGivens.todayStart_19may2016at00h00m00s + diff;
        TestGivens.tomorrowStart_20may2016at00h00m00sStart = TestGivens.tomorrowStart_20may2016at00h00m00sStart + diff;
        TestGivens._32daysAgo_17april2016at12h40m52s = TestGivens._32daysAgo_17april2016at12h40m52s + diff;
        TestGivens._31daysAgo_18april2016at12h40m52s = TestGivens._31daysAgo_18april2016at12h40m52s + diff;
        TestGivens._30daysAgo_19april2016at12h40m52s = TestGivens._30daysAgo_19april2016at12h40m52s + diff;
        TestGivens._26daysAgo_23april2016at12h40m52s = TestGivens._26daysAgo_23april2016at12h40m52s + diff;
        TestGivens._25daysAgo_24april2016at12h40m52s = TestGivens._25daysAgo_24april2016at12h40m52s + diff;
        TestGivens._4daysAgo_15may2016at12h40m52s = TestGivens._4daysAgo_15may2016at12h40m52s + diff;
        TestGivens._3daysAgo_16may2016at12h40m52s = TestGivens._3daysAgo_16may2016at12h40m52s + diff;
        TestGivens._2daysAgo_17may2016at12h40m52s = TestGivens._2daysAgo_17may2016at12h40m52s + diff;
        TestGivens._1dayAgo_18may2016at12h40m52s = TestGivens._1dayAgo_18may2016at12h40m52s + diff;
        TestGivens._in1second_19may2016at12h40m53s = TestGivens._in1second_19may2016at12h40m53s + diff;
        TestGivens._in2seconds_19may2016at12h40m54s = TestGivens._in2seconds_19may2016at12h40m54s + diff;
        TestGivens._in1day_20may2016at12h40m52s = TestGivens._in1day_20may2016at12h40m52s + diff;
        TestGivens._in2days_21may2016at12h40m52s = TestGivens._in2days_21may2016at12h40m52s + diff;
        TestGivens._in4days_23may2016at12h40m52s = TestGivens._in4days_23may2016at12h40m52s + diff;


        mTestGivens.deleteAllRecordsFromDB();
        mTestGivens.test_fillContactTable();
        mTestGivens.test_fillActionTable();
        mTestGivens.test_fillVectorTable();
        mTestGivens.test_fillEventTable();

        Status.setMarkActionFeatureStatus(mContext, false);
        Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNMANAGED_PEOPLE);
        Status.setLastUserMoodsConfirmAware(mContext, 0l);
    }
}
