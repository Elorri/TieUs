package com.elorri.android.friendforcast.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.data.FriendForecastContract;

/**
 * Created by Elorri on 11/04/2016.
 */
public class CursorTestFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private CursorTestAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private final int LOADER_ID=0;

    public CursorTestFragment() {
        // Required empty public constructor
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_cursor_test, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CursorTestAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        Uri uri = FriendForecastContract.VectorTable.CONTENT_URI;

        String[] PROJECTION=new String[]{FriendForecastContract.VectorTable.COLUMN_NAME,
                FriendForecastContract.VectorTable._ID+" as viewtype"};
        return new CursorLoader(getActivity(),
                uri,
                PROJECTION,
                null,
                null,
                FriendForecastContract.VectorTable._ID+" asc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2] + "");
        mRecyclerView.setAdapter(null);
    }
}
