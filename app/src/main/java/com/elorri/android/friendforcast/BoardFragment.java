package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.friendforcast.data.BoardQuery;
import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class BoardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BoardAdapter.Callback {

    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ImageView mForecastImageView;
    private ImageView mForecastToolbarImageView;

    public BoardFragment() {
        // Required empty public constructor
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        Typeface courgette = Typeface.createFromAsset(getContext().getAssets(), "courgette-regular.ttf");
        final TextView titleTextView = (TextView) view.findViewById(R.id.title);
        titleTextView.setTypeface(courgette);
        mForecastImageView = (ImageView) view.findViewById(R.id.forecast);
        mForecastToolbarImageView = (ImageView) view.findViewById(R.id.forecast_toolbar);


        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
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
                } else  {
                    mForecastImageView.setVisibility(View.VISIBLE);
                    mForecastToolbarImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

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
        mForecastImageView.setBackgroundResource(forecastRessourceId);
        mForecastToolbarImageView.setBackgroundResource(forecastRessourceId);
    }
}
