package com.elorri.android.friendforcast.fragments;

import android.content.ContentValues;
import android.content.pm.PackageInfo;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elorri.android.friendforcast.R;
import com.elorri.android.friendforcast.activities.MainActivity;
import com.elorri.android.friendforcast.data.BoardData;
import com.elorri.android.friendforcast.data.FriendForecastContract;
import com.elorri.android.friendforcast.db.AndroidDAO;
import com.elorri.android.friendforcast.db.ContactDAO;
import com.elorri.android.friendforcast.db.ContactVectorsDAO;
import com.elorri.android.friendforcast.db.VectorDAO;
import com.elorri.android.friendforcast.extra.Tools;

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
        //TODO remove this when adding SyncAdapter
        //syncContacts();
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


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.fragment_board, menu);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("Communication", Thread.currentThread().getStackTrace()[2] + "");
        getLoaderManager().initLoader(BoardData.LOADER_ID, null, this);
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
            addOrRemoveVectorsAccordingToAndroidAppsInstalled();
            addOrRemoveContactVectorsAccordingToAndroidContacts();

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
            Cursor androidCursor = null;
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
                        if (localCursor != null)
                            localCursor.close();
                    }
                }
            } finally {
                if (androidCursor != null)
                    androidCursor.close();
            }
        }

        /**
         * This function will add or update the app database contacts vectors of communication in
         * order to match the device android database contacts
         */
        private void addOrRemoveContactVectorsAccordingToAndroidContacts() {
            Log.e("ff", Thread.currentThread().getStackTrace()[2] + "");

            //first we delete all contacts vectors
            getContext().getContentResolver().delete(
                    FriendForecastContract.ContactVectorsTable.CONTENT_URI,
                    null,
                    null);

            //Query 1 : Select android contact ids
            Cursor cursorContacts = getContext().getContentResolver().query(
                    FriendForecastContract.ContactTable.CONTENT_URI,
                    ContactDAO.ContactQuery.PROJECTION,
                    null,
                    null,
                    null
            );
            while (cursorContacts.moveToNext()) {
                String contactId = cursorContacts.getString(ContactDAO.ContactQuery.COL_ID);
                String androidContactId = cursorContacts.getString(ContactDAO.ContactQuery.COL_ANDROID_ID);

                Uri contactUri = Uri.withAppendedPath(
                        ContactsContract.Contacts.CONTENT_URI, androidContactId).buildUpon().appendPath(
                        ContactsContract.Contacts.Entity.CONTENT_DIRECTORY).build();

                // Check if this contact has a phone number or email
                final String[] PROJECTION = {
                        ContactsContract.Contacts.Entity.RAW_CONTACT_ID,
                        ContactsContract.Contacts.Entity.DATA1,
                        ContactsContract.Contacts.Entity.DATA3,
                        ContactsContract.Contacts.Entity.MIMETYPE};
                String sortOrder = ContactsContract.Contacts.Entity.RAW_CONTACT_ID + " ASC";

                Cursor cursorDetails = getContext().getContentResolver().query(
                        contactUri,
                        PROJECTION,
                        null,
                        null,
                        sortOrder);
                String email = null;
                String phone = null;
                String eventDate = null;
                String eventLabel = null;
                int mimeIdx;
                int dataIdx;
                if (cursorDetails != null) {
                    mimeIdx = cursorDetails.getColumnIndex(ContactsContract.Contacts.Entity.MIMETYPE);
                    dataIdx = cursorDetails.getColumnIndex(ContactsContract.Contacts.Entity.DATA1);
                    while (cursorDetails.moveToNext()) {
                        if (cursorDetails.getString(mimeIdx).equalsIgnoreCase(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            email = cursorDetails.getString(dataIdx);
                        }
                        if (cursorDetails.getString(mimeIdx).equalsIgnoreCase(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            phone = cursorDetails.getString(dataIdx);
                        }
                        if (cursorDetails.getString(mimeIdx).equalsIgnoreCase(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) {
                            int dataIdx2 = cursorDetails.getColumnIndex(ContactsContract.CommonDataKinds.Event.LABEL);
                            //This works too :
                            //int dataIdx2 = cursorDetails.getColumnIndex(ContactsContract.Contacts.Entity.DATA3);
                            eventDate = cursorDetails.getString(dataIdx);
                            eventLabel = cursorDetails.getString(dataIdx2);
                        }

                        //Now we add any vectors found in the android contacts provider
                        if (email != null) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "email " + contactId + " " + email);
                            ContentValues emailVectorValues = ContactVectorsDAO.getContentValues
                                    (contactId, R.drawable.ic_mail_outline_black_24dp);

                            getContext().getContentResolver().insert(
                                    FriendForecastContract.ContactVectorsTable.CONTENT_URI,
                                    emailVectorValues);
                        }
                        if (phone != null) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "phone " + contactId + " " + phone);
                            ContentValues phoneVectorValues = ContactVectorsDAO.getContentValues
                                    (contactId, R.drawable.ic_phone_black_24dp);

                            getContext().getContentResolver().insert(
                                    FriendForecastContract.ContactVectorsTable.CONTENT_URI,
                                    phoneVectorValues);
                        }

                        //TODO convert eventDate from 2016-05-20 to long and only add future events
                        if (eventDate != null) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2]
                                    + "eventDate$ " + contactId + " " + eventDate + " " + eventLabel);
                            ContentValues phoneVectorValues = ContactVectorsDAO.getContentValues
                                    (contactId, R.drawable.ic_event_black_24dp);

                            getContext().getContentResolver().insert(
                                    FriendForecastContract.ContactVectorsTable.CONTENT_URI,
                                    phoneVectorValues);
                        }
                    }
                }
            }

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLoaderManager().restartLoader(BoardData.LOADER_ID, null, BoardFragment.this);
        }
    }

    private void addOrRemoveVectorsAccordingToAndroidAppsInstalled() {
        //first we delete all contacts vectors
        getContext().getContentResolver().delete(
                FriendForecastContract.VectorTable.CONTENT_URI,
                null,
                null);

        //Add email vector
        ContentValues emailVectorValues = VectorDAO.getContentValues(
                getResources().getString(R.string.mail),
                String.valueOf(R.drawable.ic_mail_outline_black_24dp),
                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                FriendForecastContract.VectorTable.CONTENT_URI,
                emailVectorValues);


        //Add phone vector
        ContentValues phoneVectorValues = VectorDAO.getContentValues(
                getResources().getString(R.string.phone),
                String.valueOf(R.drawable.ic_phone_black_24dp),
                FriendForecastContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                FriendForecastContract.VectorTable.CONTENT_URI,
                phoneVectorValues);

        //Add event vector
        ContentValues eventVectorValues = VectorDAO.getContentValues(
                getResources().getString(R.string.event),
                String.valueOf(R.drawable.ic_event_black_24dp),
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
            if (Tools.isPackageASocialNetworkVector(getContext(),packageName)) {
                vectorName = pi.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                ContentValues values = VectorDAO.getContentValues(vectorName, packageName,
                        FriendForecastContract.VectorTable.MIMETYPE_VALUE_PACKAGE);
                getContext().getContentResolver().insert(
                        FriendForecastContract.VectorTable.CONTENT_URI,
                        values);
            }
        }

    }
}
