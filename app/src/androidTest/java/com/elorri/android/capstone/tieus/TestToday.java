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
package com.elorri.android.capstone.tieus;

import android.test.AndroidTestCase;

import com.elorri.android.capstone.tieus.extra.Status;

/**
 * Created by Elorri on 20/05/2016.
 * This class create a set of contact with actions registered in the near past and future.
 * The value of today, tomorrow etc table from TestGivens are updated each
 * time we run the test to reflect the real value of today, tomorrow ...
 * It is meant to be used as a demo. In case we want to play with app that already contains
 * contacts.
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

        Status.setDoneActionsAware(mContext, false);
        Status.setLastMessageIdxUI(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
        Status.setLastUserSatisfactionsConfirmAware(mContext, 0l);
    }
}
