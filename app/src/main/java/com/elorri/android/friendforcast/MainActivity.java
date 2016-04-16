package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.elorri.android.friendforcast.data.FriendCastContract;
import com.elorri.android.friendforcast.db.AndroidDAO;
import com.elorri.android.friendforcast.db.ContactDAO;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final int NUM_PAGES = 1;
    private static final int BOARD_FRAGMENT = 0;
    private Fragment[] mTabs = new Fragment[NUM_PAGES];
    private ViewPager mViewPager;
    public PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabs[BOARD_FRAGMENT] = new BoardFragment();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);


        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.accent));

        syncContacts();
    }

    private void syncContacts() {
        SyncContactsTask syncContactsTask = new SyncContactsTask();
        syncContactsTask.execute();
    }

    public class PageAdapter extends FragmentStatePagerAdapter {

        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        public PageAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<>();
        }


        @Override
        public Fragment getItem(int i) {
            return mTabs[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return getString(R.string.board_title);
            else if (position == 1) return getString(R.string.contacts_title);
            return getString(R.string.board_title);
        }
    }

    private class SyncContactsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
            Cursor androidCursor = getApplicationContext().getContentResolver().query(
                    AndroidDAO.ContactQuery.CONTENT_URI,
                    AndroidDAO.ContactQuery.PROJECTION,
                    AndroidDAO.ContactQuery.SELECTION,
                    null,
                    AndroidDAO.ContactQuery.SORT_ORDER
            );

            String androidContactId;
            String androidLookUpKey;
            Cursor localCursor;
            try {
                while (androidCursor.moveToNext()) {
                    androidContactId = androidCursor.getString(AndroidDAO.ContactQuery.COL_ID);
                    androidLookUpKey = androidCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY);
                              Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                            ""+androidContactId+" "+androidLookUpKey);

                    localCursor = getApplicationContext().getContentResolver().query(
                            FriendCastContract.ContactEntry.CONTENT_URI,
                            ContactDAO.ContactQuery.PROJECTION,
                            FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + "=? and "
                                    + FriendCastContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "=?",
                            new String[]{androidContactId, androidLookUpKey},
                            null
                    );
                    try {
                        if (localCursor.getCount() == 0) {
                            Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
                            getApplicationContext().getContentResolver().insert(
                                    FriendCastContract.ContactEntry.CONTENT_URI,
                                    ContactDAO.getContentValuesInsert(androidCursor, R.drawable.ic_sentiment_neutral_black_24dp));
                        }
                    } finally {
                        localCursor.close();
                    }
                }
            } finally {
                androidCursor.close();
            }
            return null;
        }
    }
}
