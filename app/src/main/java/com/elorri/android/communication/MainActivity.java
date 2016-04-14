package com.elorri.android.communication;

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

import com.elorri.android.communication.data.CommunicationContract;
import com.elorri.android.communication.db.AndroidDAO;
import com.elorri.android.communication.db.ContactDAO;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final int NUM_PAGES = 2;
    private static final int BOARD_FRAGMENT = 0;
    private static final int CONTACTS_FRAGMENT = 1;
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
        mTabs[CONTACTS_FRAGMENT] = new ContactsFragment();

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

                    localCursor = getApplicationContext().getContentResolver().query(
                            CommunicationContract.ContactEntry.CONTENT_URI,
                            ContactDAO.ContactQuery.PROJECTION,
                            CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_ID + "=? and "
                                    + CommunicationContract.ContactEntry.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "=?",
                            new String[]{androidContactId, androidLookUpKey},
                            null
                    );

                    try {
                        if (localCursor.getCount() == 0) {
                            getApplicationContext().getContentResolver().insert(
                                    CommunicationContract.ContactEntry.CONTENT_URI,
                                    ContactDAO.getContentValues(androidCursor));
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
