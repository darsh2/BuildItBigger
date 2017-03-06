package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.logger.DebugLog;

/**
 * Created by darshan on 5/3/17.
 */

public class MainActivityFragment extends MainActivityFragmentCommon {
    private InterstitialAd interstitialAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DebugLog.logMethod();
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        instructions = (TextView) view.findViewById(R.id.instructions_text_view);
        button = (Button) view.findViewById(R.id.button_tell_joke);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLog.logMethod();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    getJoke();
                }
            }
        });

        initAdViews(view);

        return view;
    }

    private void initAdViews(View view) {
        DebugLog.logMethod();
        initBannerAd(view);
        initInterstitialAd();
    }

    private void initBannerAd(View view) {
        DebugLog.logMethod();
        AdView adView = (AdView) view.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);
    }

    private void initInterstitialAd() {
        DebugLog.logMethod();
        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                DebugLog.logMethod();
                requestNewInterstitial();
                getJoke();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        DebugLog.logMethod();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        interstitialAd.loadAd(adRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugLog.logMethod();
        interstitialAd.setAdListener(null);
    }
}
