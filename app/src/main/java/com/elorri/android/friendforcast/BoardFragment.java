package com.elorri.android.friendforcast;

import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.friendforcast.data.BoardQuery;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.AndroidDAO;
import com.elorri.android.friendforcast.db.ContactDAO;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                } else {
                    mForecastImageView.setVisibility(View.VISIBLE);
                    mForecastToolbarImageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BoardAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_board, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sync) {
            syncContacts();
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    private void syncContacts() {
        SyncContactsTask syncContactsTask = new SyncContactsTask();
        syncContactsTask.execute();
    }


    private class SyncContactsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
            addOrUpdateAppContactsAccordingToAndroidContacts();
            removeAppContactsAccordingToAndroidContacts();
            return null;
        }

        /**
         * This function will remove the app database contacts in order to match the
         * device android database contacts
         */
        private void removeAppContactsAccordingToAndroidContacts() {
            //Query 1 : Select our app database contacts
            Cursor appCursor = getContext().getContentResolver().query(
                    FriendForecastContract.ContactTable.CONTENT_URI,
                    ContactDAO.ContactQuery.PROJECTION,
                    null,
                    null,
                    null
            );

            String appContactId;
            String androidContactId;
            String androidLookUpKey;
            Cursor androidCursor=null;
            try {
                while (appCursor.moveToNext()) {
                    appContactId = appCursor.getString(ContactDAO.ContactQuery.COL_ID);
                    androidContactId = appCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_ID);
                    androidLookUpKey = appCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_LOOKUP_KEY);
                    Log.d("Communication", Thread.currentThread().getStackTrace()[2] +
                            "" + androidContactId + " " + androidLookUpKey);

                    try {
                        //Query 2 : Select android database contacts and compare them with those given by
                        // the fisrt query
                        androidCursor = getContext().getContentResolver().query(
                                AndroidDAO.ContactQuery.CONTENT_URI,
                                AndroidDAO.ContactQuery.PROJECTION,
                                ContactsContract.Contacts._ID + "=? and "
                                        + ContactsContract.Contacts.LOOKUP_KEY + "=?",
                                new String[]{androidContactId, androidLookUpKey},
                                null
                        );

                        //The android database doesn't know this contact, it must have been
                        // removed, let's remove it from our app database
                        if (androidCursor.getCount() == 0) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "remove");
                            getContext().getContentResolver().delete(
                                    FriendForecastContract.ContactTable.CONTENT_URI,
                                    FriendForecastContract.ContactTable._ID+"=?",
                                    new String[]{appContactId});
                        }

                    } finally {
                        androidCursor.close();
                    }
                }
            } finally {
                appCursor.close();
            }


        }

        /**
         * This function will add or update the app database contacts in order to match the
         * device android database contacts
         */
        private void addOrUpdateAppContactsAccordingToAndroidContacts() {
            //Query 1 : Select android phone/tablet contacts
            Cursor androidCursor = getContext().getContentResolver().query(
                    AndroidDAO.ContactQuery.CONTENT_URI,
                    AndroidDAO.ContactQuery.PROJECTION,
                    AndroidDAO.ContactQuery.SELECTION,
                    null,
                    AndroidDAO.ContactQuery.SORT_ORDER
            );

            String androidContactId;
            String androidLookUpKey;
            Cursor localCursor=null;
            try {
                while (androidCursor.moveToNext()) {
                    androidContactId = androidCursor.getString(AndroidDAO.ContactQuery.COL_ID);
                    androidLookUpKey = androidCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY);
                    Log.d("Communication", Thread.currentThread().getStackTrace()[2] +
                            "" + androidContactId + " " + androidLookUpKey);

                    try {
                        //Query 2 : Select app stored contact and compare them with those given by
                        // the fisrt query
                        localCursor = getContext().getContentResolver().query(
                                FriendForecastContract.ContactTable.CONTENT_URI,
                                ContactDAO.ContactQuery.PROJECTION,
                                FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "=? and "
                                        + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "=?",
                                new String[]{androidContactId, androidLookUpKey},
                                null
                        );

                        //Our local database doesn't know this contact, let's add it up
                        if (localCursor.getCount() == 0) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "insert");
                            getContext().getContentResolver().insert(
                                    FriendForecastContract.ContactTable.CONTENT_URI,
                                    ContactDAO.getContentValues(androidCursor, R.drawable
                                            .ic_sentiment_neutral_black_48dp));
                        } else { //Our local database know this contact, but in case the contact
                            // name has been updated, we update the whole contact but keep our local
                            // data like the emoiconId
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "update");
                            localCursor.moveToFirst();
                            String contactId = localCursor.getString(ContactDAO.ContactQuery.COL_ID);
                            getContext().getContentResolver().update(
                                    FriendForecastContract.ContactTable.CONTENT_URI,
                                    ContactDAO.getContentValues(localCursor),
                                    FriendForecastContract.ContactTable._ID + "=?", new
                                            String[]{contactId});
                        }

                    } finally {
                        localCursor.close();
                    }
                }
            } finally {
                androidCursor.close();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLoaderManager().restartLoader(BoardQuery.LOADER_ID, null, BoardFragment.this);
        }
    }
}
