package com.elorri.android.tieus;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

/**
 * Created by Elorri on 30/11/2015.
 */
public class TieUsApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    public Tracker mTracker;
    public ContainerHolder mContainerHolder;
    public TagManager mTagManager;

    // Get the Tag Manager
    public TagManager getTagManager () {
        if (mTagManager == null) {
            // create the TagManager, save it in mTagManager
            mTagManager = TagManager.getInstance(this);
        }
        return mTagManager;
    }


    // Set the ContainerHolder
    public void setContainerHolder (ContainerHolder containerHolder) {
        mContainerHolder = containerHolder;
    }

    // Get the ContainerHolder
    public ContainerHolder getContainerHolder() {
        return mContainerHolder;
    }


    // Get the tracker associated with this app
    public void startTracking() {

        // Initialize an Analytics tracker using a Google Analytics property ID.
        // Does the Tracker already exist?
        // If not, create it

        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);

            // Get the config data for the tracker
            mTracker = ga.newTracker(R.xml.track_app);

            // Enable tracking of activities
            ga.enableAutoActivityReports(this);

            // Set the log level to verbose.
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }


    public Tracker getTracker() {
        // Make sure the tracker exists
        startTracking();

        // Then return the tracker
        return mTracker;
    }
}
