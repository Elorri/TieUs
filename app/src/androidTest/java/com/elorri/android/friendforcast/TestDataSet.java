package com.elorri.android.friendforcast;

import android.test.AndroidTestCase;

/**
 * Created by Elorri on 11/05/2016.
 */
public class TestDataSet extends AndroidTestCase {
    TestGivens mTestGivens;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();

        mTestGivens.test_fillContactTable();
        mTestGivens.test_fillActionTable();
        mTestGivens.test_fillVectorTable();
    }

    public void testBasicSet(){

    }
}
