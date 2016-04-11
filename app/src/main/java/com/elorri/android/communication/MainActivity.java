package com.elorri.android.communication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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
}
