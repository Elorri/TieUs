package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elorri.android.friendforcast.data.BoardQuery;
import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class BoardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BoardAdapter.Callback {

    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbar;

    public BoardFragment() {
        // Required empty public constructor
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbar.setTitle("");
        final ImageView forecastToolbarImageView =
                (ImageView) view.findViewById(R.id.forecast_toolbar);


        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isCollapsed = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbar.setTitle(getResources().getString(R.string.app_name));
                    mCollapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
                    forecastToolbarImageView.setVisibility(View.VISIBLE);
                    isCollapsed = true;
                } else if (isCollapsed) {
                    mCollapsingToolbar.setTitle("");
                    isCollapsed = false;
                    forecastToolbarImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BoardAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        getLoaderManager().initLoader(BoardQuery.LOADER_ID, null, this);
        super.onResume();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        Uri uri = FriendForecastContract.BoardData.URI_PAGE_BOARD;
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2] + "data " + data
                .getCount());
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2] + "");
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onContactClicked(Uri uri, int avatarColor) {
        ((MainActivity) getActivity()).onContactClicked(uri, avatarColor);
    }


    @Override
    public void setForecast(int forecastRessourceId) {

    }
}
