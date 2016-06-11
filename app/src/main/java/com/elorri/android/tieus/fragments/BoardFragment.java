package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.TextView;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.BoardData;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.sync.TieUsSyncAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Elorri on 11/04/2016.
 */
public abstract class BoardFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<Cursor>,
        BoardAdapter.Callback, SharedPreferences.OnSharedPreferenceChangeListener {

    //TODO put this in the sync adapter
    public static final String ACTION_DATA_UPDATED = TieUsContract.CONTENT_AUTHORITY + ".ACTION_DATA_UPDATED";

    private static final String SELECTED_KEY = "selected_position";

    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mSynchronising;
    private Integer mPosition;
    private String mSearchString;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mListState;
    private String LIST_STATE_KEY = "list_state_key";


    //For free flavor
    private AdView mAdBannerView = null;


    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO remove this when adding SyncAdapter
        //syncContacts();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSynchronising = (TextView) view.findViewById(R.id.synchronising);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        int choiceMode = (getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND) ?
//                AbsListView.CHOICE_MODE_SINGLE : AbsListView.CHOICE_MODE_NONE;
        int choiceMode = AbsListView.CHOICE_MODE_SINGLE;
        mAdapter = new BoardAdapter(null, this, choiceMode);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            Log.e("TieUs", Thread.currentThread().getStackTrace()[2] + "onRestoreInstanceState");
            mAdapter.onRestoreInstanceState(savedInstanceState);
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mLayoutManager.onRestoreInstanceState(mListState);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }


        mAdBannerView = (AdView) view.findViewById(R.id.adView);
        requestBannerAd();
        return view;
    }

    private void requestBannerAd() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdBannerView.loadAd(adRequest);
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.fragment_main, menu);
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sync) {
            TieUsSyncAdapter.syncImmediately(getContext());
            //TODO is it necessary ?
            //getLoaderManager().restartLoader(BoardData.LOADER_ID, null, BoardFragment.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        //getLoaderManager().initLoader(BoardData.LOADER_ID, null, this);
        getLoaderManager().restartLoader(BoardData.LOADER_ID, null, this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = TieUsContract.BoardData.buildBoardUri(System.currentTimeMillis());

        final String selection = TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " LIKE ? ";

        if (mSearchString != null && mSearchString.length() > 0) {
            mSearchString = "%" + mSearchString + "%";
            return new CursorLoader(
                    getActivity(),
                    uri,
                    null,
                    selection,
                    new String[]{mSearchString},
                    null
            );
        }
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        updateSynchronisingView();
        if (Status.getSyncStatus(getContext()).equals(Status.SYNC_DONE)) {
            mAdapter.swapCursor(data);
            updateWidget();
            mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {

                    if (mRecyclerView.getChildCount() > 0) {
                        // Since we know we're going to get items, we keep the listener around until
                        // we see Children.
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        int position = mAdapter.getSelectedItemPosition();
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mPosition " + mPosition);
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
                        if (position == RecyclerView.NO_POSITION) {
                            position = mPosition == null ? getFirstContactPosition(mAdapter) : mPosition;
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
                        }
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);

                        mRecyclerView.smoothScrollToPosition(position);

//                        mLayoutManager.scrollToPosition(position);
//                        mLayoutManager.scrollToPositionWithOffset(position, 20);
//                        mRecyclerView.scrollToPosition(position);

                        //this method findViewHolderForAdapterPosition will always return null if we
                        // call it after a swapCursor
                        //(because it always return null after a notifyDataSetChanged) that's why we
                        // call findViewHolderForAdapterPosition in the onPreDraw method

                        RecyclerView.ViewHolder vh = mRecyclerView.findViewHolderForAdapterPosition(position);

                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "vh " + vh);
                        if (null != vh) {
                            if (getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
                                mAdapter.selectView(vh);

                        }
                        mPosition = position;
                        return true;
                    }
                    return false;
                }
            });
        }
    }


    private int getFirstContactPosition(BoardAdapter mAdapter) {
        Cursor data = mAdapter.getCursor();
        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            if ((data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_UNMANAGED_PEOPLE)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_FILL_IN_DELAY_FEEDBACK)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_UPDATE_MOOD)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_ASK_FOR_FEEDBACK_OR_MOVE_TO_UNTRACK)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_DELAY)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_MOOD_TODAY)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_DELAY_PEOPLE)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_TODAY_PEOPLE)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_TODAY_DONE_PEOPLE)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_NEXT_PEOPLE)
                    ) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2] + "");
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onContactClicked(Uri uri, View v) {
        ((MainActivity) getActivity()).onContactClicked(uri, v);
    }


    @Override
    public void setForecast(int forecastRessourceId) {
        ((MainActivity) getActivity()).setForecastImageView(forecastRessourceId);
        ((MainActivity) getActivity()).setForecastToolbarImageView(forecastRessourceId);
    }

    @Override
    public void restartLoader() {
        getLoaderManager().restartLoader(BoardData.LOADER_ID, null, BoardFragment.this);
    }


    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }


    private void updateWidget() {
        Context context = getContext();
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
        //TODO see if we use this
        //mPosition = mRecyclerView.getVerticalScrollbarPosition();
        if (mPosition != null) {
            outState.putInt(SELECTED_KEY, mPosition);

            // When tablets rotate, the currently selected list item needs to be saved.
            mAdapter.onSaveInstanceState(outState);

            // Save list state
            mListState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(LIST_STATE_KEY, mListState);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sync_status_key))) {
            updateSynchronisingView();
            restartLoader();
        }
    }

    private void updateSynchronisingView() {
        if (mRecyclerView != null && mSynchronising != null) {
            if (Status.getSyncStatus(getContext()).equals(Status.SYNC_START)) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mSynchronising.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mSynchronising.setVisibility(View.INVISIBLE);
            }
        }
    }
}
