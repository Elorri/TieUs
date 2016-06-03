package com.elorri.android.tieus.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.fragments.BoardFragment;


public class MainActivity extends AppCompatActivity {

    private ImageView mForecastImageView;
    private ImageView mForecastToolbarImageView;
    private SearchView mSearchView;
    private static final String QUERY = "query";
    private String mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        setContentView(R.layout.activity_main);

        final BoardFragment boardFragment = (BoardFragment)getSupportFragmentManager()
                .findFragmentByTag(getResources().getString(R.string.tag_fragment_board));

        Typeface courgette = Typeface.createFromAsset(getAssets(), "courgette-regular.ttf");
        final TextView titleTextView = (TextView) findViewById(R.id.title);
        if (titleTextView != null) {
            titleTextView.setTypeface(courgette);
        }
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
                boardFragment.setSearchString(mSearchView.getQuery().toString());
                boardFragment.restartLoader();
                return true;
            }

        });

        if (savedInstanceState != null && savedInstanceState.containsKey(QUERY)) {
            mQuery = savedInstanceState.getString(QUERY);
            mSearchView.setQuery(mQuery, true);
            boardFragment.setSearchString(mSearchView.getQuery().toString());
        }

    }


    public void onContactClicked(Uri uri) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.setData(uri);
        startActivity(intent);
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
        mForecastImageView.setBackgroundResource(forecastRessourceId);
    }

    public void setForecastToolbarImageView(int forecastRessourceId) {
        mForecastToolbarImageView.setBackgroundResource(forecastRessourceId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(QUERY, mSearchView.getQuery().toString());
        super.onSaveInstanceState(outState);
    }


}
