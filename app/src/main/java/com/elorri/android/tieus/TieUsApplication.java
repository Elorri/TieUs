package com.elorri.android.tieus;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Elorri on 30/11/2015.
 */
public class TieUsApplication extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }
}
