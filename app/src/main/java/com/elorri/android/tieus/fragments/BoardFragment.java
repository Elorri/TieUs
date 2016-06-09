package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.BoardData;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.ViewTypes;
import com.elorri.android.tieus.sync.TieUsSyncAdapter;

/**
 * Created by Elorri on 11/04/2016.
 */
public class BoardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BoardAdapter.Callback {

    //TODO put this in the sync adapter
    public static final String ACTION_DATA_UPDATED = TieUsContract.CONTENT_AUTHORITY + ".ACTION_DATA_UPDATED";

    private static final String SELECTED_KEY = "selected_position";

    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Integer mPosition;
    private String mSearchString;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mListState;
    private String LIST_STATE_KEY = "list_state_key";


    public BoardFragment() {
        // Required empty public constructor
        Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
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
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2]);
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        int choiceMode = (getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND) ?
//                AbsListView.CHOICE_MODE_SINGLE : AbsListView.CHOICE_MODE_NONE;
        int choiceMode = AbsListView.CHOICE_MODE_SINGLE;
        mAdapter = new BoardAdapter(null, this, choiceMode);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            mAdapter.onRestoreInstanceState(getContext(), savedInstanceState);
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return view;
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
        super.onResume();
        Log.e("FF", "" + Thread.currentThread().getStackTrace()[2] + "mPosition " + mPosition);
        //getLoaderManager().initLoader(BoardData.LOADER_ID, null, this);
        getLoaderManager().restartLoader(BoardData.LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2]);
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
        mAdapter.swapCursor(data);
        updateWidget();
        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (mRecyclerView.getChildCount() > 0) {
                    // Since we know we're going to get items, we keep the listener around until
                    // we see Children.
                    mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int position = mAdapter.getSelectedItemPosition(getContext());
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mPosition " + mPosition);
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
                    if (position == RecyclerView.NO_POSITION) {
                        position = mPosition == null ? getFirstContactPosition(mAdapter) : mPosition;
                        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
                    }
                    Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);

                    mRecyclerView.smoothScrollToPosition(position);


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
        //TODO see if we use this
        //mPosition = mRecyclerView.getVerticalScrollbarPosition();
        if (mPosition != null) {
            outState.putInt(SELECTED_KEY, mPosition);

            // When tablets rotate, the currently selected list item needs to be saved.
            mAdapter.onSaveInstanceState(getContext(), outState);

            // Save list state
            mListState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(LIST_STATE_KEY, mListState);
        }
        super.onSaveInstanceState(outState);
    }


}
