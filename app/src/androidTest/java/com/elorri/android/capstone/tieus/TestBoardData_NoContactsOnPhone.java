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


import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.elorri.android.capstone.tieus.data.TieUsContract;
import com.elorri.android.capstone.tieus.db.MatrixCursors;
import com.elorri.android.capstone.tieus.db.ViewTypes;
import com.elorri.android.capstone.tieus.extra.Status;
import com.elorri.android.tieus.R;

/**
 * Created by Elorri on 20/05/2016.
 */
public class TestBoardData_NoContactsOnPhone extends AndroidTestCase {

    TestGivens mTestGivens;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestGivens = new TestGivens(mContext);
        mTestGivens.deleteAllRecordsFromDB();
    }


    @Override
    protected void tearDown() throws Exception {
        //mTestGivens.deleteAllRecordsFromDB();
        super.tearDown();

        Status.setLastMessageIdxBg(mContext, Status.MANAGE_UNSCHEDULED_PEOPLE);
    }

    public void testNoContactRegisteredOnPhone() {
        Cursor cursor = mContext.getContentResolver().query(
                TieUsContract.MainData.buildBoardUri(TestGivens.now_19may2016at12h40m52s), null, null, null, null);

        String cursorString = "\n"
                + "header |"
                + MatrixCursors.EmptyCursorMessageQuery.COLUMN_EMPTY_CURSOR + "|" + ViewTypes.COLUMN_VIEWTYPE +
                "|\n"
                + "row |"
                + mContext.getResources().getString(R.string.no_contacts_on_phone)
                + "|"
                + ViewTypes.VIEW_EMPTY_CURSOR_MESSAGE + "|\n";


        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "" + TestUtility.getCursorString(cursor));
        assertEquals(cursorString, TestUtility.getCursorString(cursor));
        cursor.close();

    }
}
