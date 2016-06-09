package com.elorri.android.tieus.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elorri.android.tieus.R;
import com.elorri.android.tieus.activities.MainActivity;
import com.elorri.android.tieus.data.TieUsContract;
import com.elorri.android.tieus.db.AndroidDAO;
import com.elorri.android.tieus.db.ContactDAO;
import com.elorri.android.tieus.db.VectorDAO;
import com.elorri.android.tieus.extra.Status;
import com.elorri.android.tieus.extra.Tools;

/**
 * Created by Elorri on 09/06/2016.
 */

public class TieUsSyncAdapter extends AbstractThreadedSyncAdapter {

    // Interval at which to sync with the Tmdb network, in seconds.
    // 60 seconds (1 minute) * 60 * 24 * 3= 24 hours - will sync once every 3 days
    public static final int SYNC_INTERVAL = 60 * 60 * 24 * 3;
   // public static final int SYNC_INTERVAL = 60; //for testing

    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final long _3DAYS_IN_MILLIS = 1000 * 60 * 60 * 24 * 3;
    private static final int TIE_US_NOTIFICATION_ID = 3004;


    public TieUsSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        syncContacts();
        notifyUserSyncDone();
    }


    private void syncContacts() {
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "will start sync");
        addOrUpdateAppContactsAccordingToAndroidContacts();
        removeAppContactsAccordingToAndroidContacts();
        addOrRemoveVectorsAccordingToAndroidAppsInstalled();
        Log.e("FF", Thread.currentThread().getStackTrace()[2] + "sync done");
    }


    /**
     * This function will remove the app database contacts in order to match the
     * device android database contacts
     */
    private void removeAppContactsAccordingToAndroidContacts() {
        //Query 1 : Select our app database contacts
        Cursor appCursor = getContext().getContentResolver().query(
                TieUsContract.ContactTable.CONTENT_URI,
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
                                TieUsContract.ContactTable.CONTENT_URI,
                                TieUsContract.ContactTable._ID + "=?",
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
                                TieUsContract.ContactTable.CONTENT_URI,
                                ContactDAO.ContactQuery.PROJECTION_QUERY,
                                TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_ID + "=? and "
                                        + TieUsContract.ContactTable.COLUMN_ANDROID_CONTACT_LOOKUP_KEY + "=?",
                                new String[]{androidContactId, androidLookUpKey},
                                null
                        );

                        //Our local database doesn't know this contact, let's add it up
                        if (localCursor.getCount() == 0) {
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "insert");
                            ContentValues values = ContactDAO.getContentValues(androidCursor,
                                    R.drawable.ic_sentiment_neutral_black_48dp,
                                    TieUsContract.ContactTable.UNTRACKED_ON_VALUE,
                                    TieUsContract.ContactTable.MOOD_UNKNOWN_OFF_VALUE,
                                    ColorGenerator.MATERIAL.getRandomColor());

                            getContext().getContentResolver().insert(
                                    TieUsContract.ContactTable.CONTENT_URI, values);
                        } else { //Our local database know this contact, but in case the contact
                            // name has been updated, we update the whole contact but keep our local
                            // data like the moodId
                            Log.e("FF", Thread.currentThread().getStackTrace()[2] + "update");
                            localCursor.moveToFirst();
                            String contactId = localCursor.getString(ContactDAO.ContactQuery.COL_ID);
                            getContext().getContentResolver().update(
                                    TieUsContract.ContactTable.CONTENT_URI,
                                    ContactDAO.getContentValues(androidCursor),
                                    TieUsContract.ContactTable._ID + "=?", new
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

    private void addOrRemoveVectorsAccordingToAndroidAppsInstalled() {
        //first we delete all contacts vectors
        getContext().getContentResolver().delete(
                TieUsContract.VectorTable.CONTENT_URI,
                null,
                null);

        //We won't add it because we will use most popular email services
        //Add email vector
//        ContentValues emailVectorValues = VectorDAO.getContentValues(
//                getResources().getString(R.string.mail),
//                String.valueOf(R.drawable.ic_mail_outline_black_24dp),
//                TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
//        getContext().getContentResolver().insert(
//                TieUsContract.VectorTable.CONTENT_URI,
//                emailVectorValues);

        //We won't put the phone because we will use the com.android.phone
        //Add phone vector
//        ContentValues phoneVectorValues = VectorDAO.getContentValues(
//                getResources().getString(R.string.phone),
//                String.valueOf(R.drawable.ic_phone_black_24dp),
//                TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
//        getContext().getContentResolver().insert(
//                TieUsContract.VectorTable.CONTENT_URI,
//                phoneVectorValues);

        //Add sms vector
        ContentValues eventVectorValues = VectorDAO.getContentValues(
                getContext().getResources().getString(R.string.sms),
                String.valueOf(R.drawable.ic_textsms_black_24dp),
                TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                TieUsContract.VectorTable.CONTENT_URI,
                eventVectorValues);


        //Add meeting vector
        eventVectorValues = VectorDAO.getContentValues(
                getContext().getResources().getString(R.string.meeting),
                String.valueOf(R.drawable.ic_meeting_24dp),
                TieUsContract.VectorTable.MIMETYPE_VALUE_RESSOURCE);
        getContext().getContentResolver().insert(
                TieUsContract.VectorTable.CONTENT_URI,
                eventVectorValues);


        //Add others vectors available on user phone
        String vectorName;
        String packageName;
        for (PackageInfo pi : Tools.getInstalledPackages(getContext().getPackageManager())) {
            packageName = pi.applicationInfo.packageName;

            //check if this package is a social network that can be used as a vector of communication
            if (Tools.isPackageASocialNetworkVector(getContext(), packageName)) {
                vectorName = pi.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                ContentValues values = VectorDAO.getContentValues(vectorName, packageName,
                        TieUsContract.VectorTable.MIMETYPE_VALUE_PACKAGE);
                getContext().getContentResolver().insert(
                        TieUsContract.VectorTable.CONTENT_URI,
                        values);
            }
        }

    }


    private void notifyUserSyncDone() {
        Context context = getContext();
        //checking the last update and notify if it' the first of the day
        long lastSync = Status.getLastNotificationTimestamp(getContext());

        if (System.currentTimeMillis() - lastSync >= _3DAYS_IN_MILLIS) {
            // Last sync was more than 1 day ago, let's send a notification with the TieUs
            // for today

            // NotificationCompatBuilder is a very convenient way to build backward-compatible
            // notifications.  Just throw in some data.
            Resources resources = context.getResources();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getContext())
                            .setColor(resources.getColor(R.color.primaryDark))
//                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(context
                                    .getResources(), R.mipmap.ic_launcher))
                            .setContentTitle(context.getString(R.string.app_name))
                            .setContentText(context.getString(R.string.sync_contact_done));

            // Make something interesting happen when the user clicks on the notification.
            // In this case, opening the app is sufficient.
            Intent resultIntent = new Intent(context, MainActivity.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

            // To allows use to  update the notification later on.
            mNotificationManager.notify(TIE_US_NOTIFICATION_ID, mBuilder.build());

            //refreshing last sync
            Status.setLastNotificationTimestamp(getContext(), System.currentTimeMillis());
        }
    }


    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
             /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        TieUsSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }


    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        syncDB(context);
    }


    private static void syncDB(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority),
                bundle);
    }
}
