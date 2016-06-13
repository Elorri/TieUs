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
import com.elorri.android.tieus.fragments.BoardFragment;
import com.elorri.android.tieus.fragments.DetailFragment;


public class MainActivity extends AppCompatActivity {

    public static final int PORT = 1;
    public static final int LAND = 2;
    public static final int W600dp_PORT = 3;
    public static final int W700dp_LAND = 4;

    private ImageView mForecastImageView;
    private ImageView mForecastToolbarImageView;
    private SearchView mSearchView;
    private static final String QUERY = "query";
    private String mQuery;
    private BoardFragment mBoardFragment;
    private String DETAIL_FRAGMENT_TAG = "detail_fragment";
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        setContentView(R.layout.activity_main);

        mBoardFragment = (BoardFragment) getSupportFragmentManager()
                .findFragmentByTag(getResources().getString(R.string.tag_fragment_board));

        setCommonViews();
        setOthersViews(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(QUERY)) {
            mQuery = savedInstanceState.getString(QUERY);
            mSearchView.setQuery(mQuery, true);
            mBoardFragment.setSearchString(mSearchView.getQuery().toString());
        }

        //This will launch a synchronisation with the user device contacts at first install and
        // then every 3 days.
        //TieUsSyncAdapter.initializeSyncAdapter(this);

        // Make sure that Analytics tracking has started
        ((TieUsApplication) getApplication()).startTracking();
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
                }
                else {
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


    public BoardFragment getMainFragment() {
        return mBoardFragment;
    }
}
