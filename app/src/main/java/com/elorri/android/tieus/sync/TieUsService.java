package com.elorri.android.tieus.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Elorri on 09/06/2016.
 */
public class TieUsService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static TieUsSyncAdapter sTieUsSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sTieUsSyncAdapter == null) {
                sTieUsSyncAdapter = new TieUsSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sTieUsSyncAdapter.getSyncAdapterBinder();
    }
}