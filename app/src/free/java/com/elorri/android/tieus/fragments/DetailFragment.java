package com.elorri.android.tieus.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elorri.android.tieus.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class DetailFragment extends AbstractDetailFragment {

    private InterstitialAd mInterstitialAd = null;
    private Context mContext;
    private String mMimetype;
    private String mVectorData;
    private String mVectorName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Load interstitial Ad.
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                DetailFragment.super.startVector(mContext, mMimetype, mVectorData, mVectorName);
            }
        });
        requestNewInterstitial();
        return view;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void startVector(Context context, String mimetype, String vectorData, String vectorName) {
        mContext = context;
        mMimetype = mimetype;
        mVectorData = vectorData;
        mVectorName = vectorName;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            super.startVector(context, mimetype, vectorData, vectorName);
        }
    }



}