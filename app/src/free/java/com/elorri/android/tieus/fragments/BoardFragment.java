package com.elorri.android.tieus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elorri.android.tieus.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Elorri on 11/06/2016.
 */
public class BoardFragment extends AbstractBoardFragment {

    private AdView mAdBannerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view=super.onCreateView(inflater, container, savedInstanceState);

        mAdBannerView = (AdView) view.findViewById(R.id.adView);
        requestBannerAd();
        return view;
    }

    private void requestBannerAd() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdBannerView.loadAd(adRequest);
    }
}
