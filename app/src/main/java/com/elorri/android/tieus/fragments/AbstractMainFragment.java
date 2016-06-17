package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.TextView;

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.sync.TieUsSyncAdapter;


/**
 * Created by Elorri on 11/04/2016.
 */
public abstract class AbstractMainFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<Cursor>,
        MainAdapter.Callback, SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String ACTION_DATA_UPDATED = TieUsContract.CONTENT_AUTHORITY + ".ACTION_DATA_UPDATED";

    private static final String SELECTED_KEY = "selected_position";

    private MainAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mSynchronising;
    private Integer mPosition;
    private String mSearchString;
    private LinearLayoutManager mLayoutManager;


    public AbstractMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mAdapter = new MainAdapter(null, this, AbsListView.CHOICE_MODE_SINGLE);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState);
            if (savedInstanceState.containsKey(SELECTED_KEY)) {
                mPosition = savedInstanceState.getInt(SELECTED_KEY);
            }
        }

        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sync) {
            TieUsSyncAdapter.syncImmediately(getContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        //I had trouble with initLoader, so I use restartLoader here
        //getLoaderManager().initLoader(MainData.LOADER_ID, null, this);
        getLoaderManager().restartLoader(com.elorri.android.tieus.data.MainData.LOADER_ID, null, this);

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
        Uri uri = TieUsContract.MainData.buildBoardUri(System.currentTimeMillis());

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
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        updateSynchronisingView();
        if (Status.getSyncStatus(getContext()).equals(Status.SYNC_DONE)) {
            mAdapter.swapCursor(data);
            updateWidget();

            int position = mAdapter.getSelectedItemPosition();
            if (position == RecyclerView.NO_POSITION) {
                position = mPosition == null ? getFirstContactPosition(mAdapter) : mPosition;
            }
            mRecyclerView.smoothScrollToPosition(position);
            mPosition = position;
            mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (mRecyclerView.getChildCount() > 0) {
                        // Since we know we're going to get items, we keep the listener around until
                        // we see Children.
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        //this method findViewHolderForAdapterPosition will always return null if we
                        // call it after a swapCursor
                        //(because it always return null after a notifyDataSetChanged) that's why we
                        // call findViewHolderForAdapterPosition in the onPreDraw method
                        RecyclerView.ViewHolder vh = mRecyclerView.findViewHolderForAdapterPosition(mPosition);
                        if (null != vh) {
                            if (getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
                                mAdapter.selectView(vh);
                        } else {
                            // I had trouble trouble getting a ViewHolder not null for views that
                            // were first offscreens. See my post on forum :
                            // https://discussions.udacity.com/t/viewholder-null-and-scrolltoposition
                            // After several days of search i ended up restarting the loader. But
                            // I know it's not the best solution.
                            restartLoader();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }


    private int getFirstContactPosition(MainAdapter mAdapter) {
        Cursor data = mAdapter.getCursor();
        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            if ((data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_UNSCHEDULED_PEOPLE)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_FILL_IN_RESPONSE_TIME_LIMIT)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_UPDATE_SATISFACTION)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_SET_UP_A_FREQUENCY_OF_CONTACT)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_ASK_FOR_RESPONSE_OR_MOVE_TO_UNFOLLOWED)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_APPROCHING_END_OF_MOST_SUITABLE_CONTACT_TIME_LIMIT)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_NOTE_PEOPLE_WHO_DECREASED_SATISFACTION_TODAY)
                    || (data.getInt(data.getColumnIndex(ViewTypes.COLUMN_VIEWTYPE))
                    == ViewTypes.VIEW_DELAYED_PEOPLE)
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
        getLoaderManager().restartLoader(com.elorri.android.tieus.data.MainData.LOADER_ID, null, AbstractMainFragment.this);
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
        if (mPosition != null) {
            outState.putInt(SELECTED_KEY, mPosition);

            // When tablets rotate, the currently selected list item needs to be saved.
            mAdapter.onSaveInstanceState(outState);
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
