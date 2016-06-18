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
package com.elorri.android.tieus.activities;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.TieUsApplication;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.extra.Tools;
import com.elorri.android.tieus.fragments.DetailFragment;
import com.elorri.android.tieus.fragments.MainFragment;
import com.elorri.android.tieus.sync.TieUsSyncAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Elorri on 11/04/2016.
 * First activity launched by the app.
 */
public class MainActivity extends AppCompatActivity {

    public static final int PORT = 1;
    public static final int LAND = 2;
    public static final int W600dp_PORT = 3;
    public static final int W700dp_LAND = 4;

    private ImageView mForecastImageView;
    private ImageView mForecastToolbarImageView;
    private SearchView mSearchView;
    private static final String QUERY = "query";
    private MainFragment mBoardFragment;
    private String DETAIL_FRAGMENT_TAG = "detail_fragment";
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("TieUs", Thread.currentThread().getStackTrace()[2]+"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag(getResources().getString(R.string.tag_fragment_board));

        setCommonViews();
        setOthersViews(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(QUERY)) {
            String query = savedInstanceState.getString(QUERY);
            mSearchView.setQuery(query, true);
            mBoardFragment.setSearchString(mSearchView.getQuery().toString());
        }

        //This will launch a synchronisation with the user device contacts at first install and
        // then every 3 days. Although our app doesn't grap data from
        // the network ( it grab data from the local android device) we use a SyncAdapter because
        // it's convenient for updating data at regular intervals. That means that without
        // internet on, sync won't work, because SyncAdapter synchronise only if internet is on.
        TieUsSyncAdapter.initializeSyncAdapter(this);

        if (!Status.getFirebaseStatsSent(this)) {
            Bundle bundle = new Bundle();
            bundle.putInt(FirebaseAnalytics.Param.VALUE, Status.getSyncStatsContactAdded(this));
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, this.getResources().getString(R.string.item_contact));
            ((TieUsApplication) getApplication()).getFirebaseAnalytics().logEvent(
                    this.getResources().getString(R.string.event_contact_added), bundle);

            bundle.putInt(FirebaseAnalytics.Param.VALUE, Status.getSyncStatsContactUpdated(this));
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, this.getResources().getString(R.string.item_contact));
            ((TieUsApplication) getApplication()).getFirebaseAnalytics().logEvent(
                    this.getResources().getString(R.string.event_contact_updated), bundle);

            bundle.putInt(FirebaseAnalytics.Param.VALUE, Status.getSyncStatsContactDeleted(this));
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, this.getResources().getString(R.string.item_contact));
            ((TieUsApplication) getApplication()).getFirebaseAnalytics().logEvent(
                    this.getResources().getString(R.string.event_contact_deleted), bundle);

            Status.setFirebaseStatsSent(this, true);
        }

    }

    private void setCommonViews() {
        Typeface courgette = Typeface.createFromAsset(getAssets(), "courgette-regular.ttf");
        final TextView titleTextView = (TextView) findViewById(R.id.title);
        if (titleTextView != null) {
            titleTextView.setTypeface(courgette);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mBoardFragment.setSearchString(mSearchView.getQuery().toString());
                mBoardFragment.restartLoader();
                return true;
            }

        });
    }

    private void setOthersViews(Bundle savedInstanceState) {
        switch (getResources().getInteger(R.integer.orientation)) {
            case PORT: {
                mForecastImageView = (ImageView) findViewById(R.id.forecast);
                mForecastToolbarImageView = (ImageView) findViewById(R.id.forecast_toolbar);

                AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
                if (appBarLayout != null) {
                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset <= 0) {
                                mForecastImageView.setVisibility(View.INVISIBLE);
                                mForecastToolbarImageView.setVisibility(View.VISIBLE);
                            } else {
                                mForecastImageView.setVisibility(View.VISIBLE);
                                mForecastToolbarImageView.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                break;
            }
            case LAND: {
                mForecastToolbarImageView = (ImageView) findViewById(R.id.forecast_toolbar);
                break;
            }
            case W600dp_PORT: {
                mForecastImageView = (ImageView) findViewById(R.id.forecast);
                break;
            }
            case W700dp_LAND: {
                mForecastImageView = (ImageView) findViewById(R.id.forecast);
                if (findViewById(R.id.detail_fragment_container) != null) {
                    mTwoPane = true;
                    if (savedInstanceState != null) {
                        Fragment detailFragment = getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
                        if (detailFragment == null) {
                            getSupportFragmentManager().beginTransaction().replace(
                                    R.id.detail_fragment_container,
                                    new DetailFragment(), DETAIL_FRAGMENT_TAG)
                                    .commit();
                        }
                    }
                } else {
                    mTwoPane = false;
                }
                break;
            }
        }


    }


    public void onContactClicked(Uri uri, View view) {
        if (mTwoPane) {

            findViewById(R.id.detail_fragment_container).setVisibility(View.VISIBLE);

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, uri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        view,
                        getResources().getString(R.string.keep))
                        .toBundle();
                Intent intent = new Intent(this, DetailActivity.class);
                intent.setData(uri);
                startActivity(intent, bundle);
            } else {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reset_help_messages) {
            Status.setDoneActionsAware(this, false);
            Status.setDeleteActionsAware(this, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setForecastImageView(int forecastRessourceId) {
        if (mForecastImageView != null) {
            mForecastImageView.setBackgroundResource(forecastRessourceId);
            mForecastImageView.setContentDescription(Tools.getForecastDescription(this, forecastRessourceId));
        }
    }

    public void setForecastToolbarImageView(int forecastRessourceId) {
        if (mForecastToolbarImageView != null) {
            mForecastToolbarImageView.setBackgroundResource(forecastRessourceId);
            mForecastToolbarImageView.setContentDescription(Tools.getForecastDescription
                    (this, forecastRessourceId));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY, mSearchView.getQuery().toString());
        super.onSaveInstanceState(outState);
    }


    public MainFragment getMainFragment() {
        return mBoardFragment;
    }
}
