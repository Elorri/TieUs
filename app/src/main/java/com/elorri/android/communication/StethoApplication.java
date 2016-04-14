package com.elorri.android.communication;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Elorri on 30/11/2015.
 */
public class StethoApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
