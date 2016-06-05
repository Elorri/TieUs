package com.elorri.android.tieus.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.BoardData;
import com.elorri.android.tieus.data.FriendForecastContract;
import com.elorri.android.tieus.db.AndroidDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.VectorDAO;
import com.elorri.android.tieus.extra.Tools;

/**
 * Created by Elorri on 11/04/2016.
 */
public class BoardFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, BoardAdapter.Callback {

    //TODO put this in the sync adapter
    public static final String ACTION_DATA_UPDATED = FriendForecastContract.CONTENT_AUTHORITY + ".ACTION_DATA_UPDATED";

    private static final String SELECTED_KEY = "selected_position";

    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Integer mPosition;
    private String mSearchString;


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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BoardAdapter(null, this);
        mRecyclerView.setAdapter(mAdapter);

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
            syncContacts();
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
        Uri uri = FriendForecastContract.BoardData.buildBoardUri(System.currentTimeMillis());

        final String selection = FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_NAME + " LIKE ? ";

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
        int position = mAdapter.getSelectedItemPosition();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "mPosition " + mPosition);
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        if (position == RecyclerView.NO_POSITION) {
            position = mPosition == null ? 0 : mPosition;
            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
//            ((BoardAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position))
//                    .mView.performClick();

            if (getContext().getResources().getInteger(R.integer.orientation) == MainActivity.W700dp_LAND)
                mAdapter.performClickFirstContact();
        }
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "position " + position);
        mRecyclerView.smoothScrollToPosition(position);
        mPosition = position;

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("Communication", "" + Thread.currentThread().getStackTrace()[2] + "");
        mRecyclerView.setAdapter(null);
    }

    @Override
    public void onContactClicked(Uri uri) {
        ((MainActivity) getActivity()).onContactClicked(uri);
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


    private void syncContacts() {
        SyncContactsTask syncContactsTask = new SyncContactsTask();
        syncContactsTask.execute();
    }


    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }


    private class SyncContactsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("Communication", Thread.currentThread().getStackTrace()[2] + "");
            addOrUpdateAppContactsAccordingToAndroidContacts();
            removeAppContactsAccordingToAndroidContacts();
            addOrRemoveVectorsAccordingToAndroidAppsInstalled();
            updateWidget();
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
                    ContactDAO.ContactQuery.PROJECTION_QUERY,
                    null,
                    null,
                    null
            );

            String appContactId;
            String androidContactId;
            String androidLookUpKey;
            Cursor androidCursor = null;
            try {
                while (appCursor.moveToNext()) {
                    appContactId = appCursor.getString(ContactDAO.ContactQuery.COL_ID);
                    androidContactId = appCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_ID);
                    androidLookUpKey = appCursor.getString(ContactDAO.ContactQuery.COL_ANDROID_LOOKUP_KEY);
                    Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
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
                                    FriendForecastContract.ContactTable._ID + "=?",
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
            Cursor localCursor = null;
            try {
                if (androidCursor != null) {
                    while (androidCursor.moveToNext()) {
                        androidContactId = androidCursor.getString(AndroidDAO.ContactQuery.COL_ID);
                        androidLookUpKey = androidCursor.getString(AndroidDAO.ContactQuery.COL_LOOKUP_KEY);
                        Log.e("Communication", Thread.currentThread().getStackTrace()[2] +
                                "" + androidContactId + " " + androidLookUpKey);

                        try {
                            //Query 2 : Select app stored contact and compare them with those given by
                            // the fisrt query
                            localCursor = getContext().getContentResolver().query(
                                    FriendForecastContract.ContactTable.CONTENT_URI,
                                    ContactDAO.ContactQuery.PROJECTION_QUERY,
                                    FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "=? and "
                                            + FriendForecastContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "=?",
                                    new String[]{androidContactId, androidLookUpKey},
                                    null
                            );

                            //Our local database doesn't know this contact, let's add it up
                            if (localCursor.getCount() == 0) {
                                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "insert");
                                ContentValues values = ContactDAO.getContentValues(androidCursor,
                                        R.drawable.ic_sentiment_neutral_black_48dp,
                                        FriendForecastContract.ContactTable.UNTRACKED_OFF_VALUE,
                                        FriendForecastContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE,
                                        ColorGenerator.MATERIAL.getRandomColor());

                                getContext().getContentResolver().insert(
                                        FriendForecastContract.ContactTable.CONTENT_URI, values);
                            } else { //Our local database know this contact, but in case the contact
                                // name has been updated, we update the whole contact but keep our local
                                // data like the moodId
                                Log.e("FF", Thread.currentThread().getStackTrace()[2] + "update");
                                localCursor.moveToFirst();
                                String contactId = localCursor.getString(ContactDAO.ContactQuery.COL_ID);
                                getContext().getContentResolver().update(
                                        FriendForecastContract.ContactTable.CONTENT_URI,
                                        ContactDAO.getContentValues(androidCursor),
                                        FriendForecastContract.ContactTable._ID + "=?", new
                                                String[]{contactId});
                            }

                        } finally {
                            if (localCursor != null)
                                localCursor.close();
                        }
                    }
                }
            } finally {
                if (androidCursor != null)
                    androidCursor.close();
            }
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLoaderManager().restartLoader(BoardData.LOADER_ID, null, BoardFragment.this);
        }
    }

    private void updateWidget() {
        Context context = getContext();
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }

    private void addOrRemoveVectorsAccordingToAndroidAppsInstalled() {
        //first we delete all contacts vectors
        getContext().getContentResolver().delete(
                FriendForecastContract.VectorTable.CONTENT_URI,
                null,
                null);

        //We won't add it because we will use most popular email services
        //Add email vector
//        ContentValues emailVectorValues = VectorDAO.getContentValues(
//                getResources().getString(R.string.mail),
//                String.valueOf(R.drawable.ic_mail_outline_black_24dp),
//                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
//        getContext().getContentResolver().insert(
//                FriendForecastContract.VectorTable.CONTENT_URI,
//                emailVectorValues);

        //We won't put the phone because we will use the com.android.phone
        //Add phone vector
//        ContentValues phoneVectorValues = VectorDAO.getContentValues(
//                getResources().getString(R.string.phone),
//                String.valueOf(R.drawable.ic_phone_black_24dp),
//                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
//        getContext().getContentResolver().insert(
//                FriendForecastContract.VectorTable.CONTENT_URI,
//                phoneVectorValues);

        //Add sms vector
        ContentValues eventVectorValues = VectorDAO.getContentValues(
                getResources().getString(R.string.sms),
                String.valueOf(R.drawable.ic_textsms_black_24dp),
                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                FriendForecastContract.VectorTable.CONTENT_URI,
                eventVectorValues);


        //Add meeting vector
        eventVectorValues = VectorDAO.getContentValues(
                getResources().getString(R.string.meeting),
                String.valueOf(R.drawable.ic_meeting_24dp),
                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                FriendForecastContract.VectorTable.CONTENT_URI,
                eventVectorValues);


        //Add others vectors available on user phone
        String vectorName;
        String packageName;
        for (PackageInfo pi : Tools.getInstalledPackages(getActivity().getPackageManager())) {
            packageName = pi.applicationInfo.packageName;

            //check if this package is a social network that can be used as a vector of communication
            if (Tools.isPackageASocialNetworkVector(getContext(), packageName)) {
                vectorName = pi.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                ContentValues values = VectorDAO.getContentValues(vectorName, packageName,
                        FriendForecastContract.VectorTable.MIMETYPE_VALUE_PACKAGE);
                getContext().getContentResolver().insert(
                        FriendForecastContract.VectorTable.CONTENT_URI,
                        values);
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //TODO see if we use this
        //mPosition = mRecyclerView.getVerticalScrollbarPosition();
        outState.putInt(SELECTED_KEY, mPosition);
        super.onSaveInstanceState(outState);
    }

}
