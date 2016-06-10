package com.elorri.android.tieus.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Elorri on 09/06/2016.
 */
public class TieUsAuthenticatorService  extends Service {
    // Instance field that stores the authenticator object
    private TieUsAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new TieUsAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}