package com.derevenetz.oleg.christmastree;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.core.content.FileProvider;

import org.qtproject.qt5.android.bindings.QtActivity;

import com.google.ads.mediation.admob.AdMobAdapter;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

public class TreeActivity extends QtActivity
{
    private static final long AD_RELOAD_ON_FAILURE_DELAY = 60000;

    private boolean           showPersonalizedAds        = false;
    private AdView            bannerView                 = null;
    private InterstitialAd    interstitial               = null;

    private static native void interstitialActiveUpdated(boolean active);
    private static native void bannerViewHeightUpdated(int height);

    @Override
    public void onResume()
    {
        super.onResume();

        if (bannerView != null) {
            bannerView.resume();
        }
    }

    @Override
    public void onPause()
    {
        if (bannerView != null) {
            bannerView.pause();
        }

        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        if (bannerView != null) {
            bannerView.destroy();

            bannerView = null;
        }

        super.onDestroy();
    }

    public int getScreenDpi()
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        return metrics.densityDpi;
    }

    public void shareImage(String image_path)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(image_path)));

            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_image_chooser_title)));
        } catch (Exception ex) {
            Log.e("TreeActivity", "shareImage() : " + ex.toString());
        }
    }

    public void initAds(String interstitial_unit_id)
    {
        final String  f_interstitial_unit_id = interstitial_unit_id;
        final Context f_context              = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                MobileAds.setRequestConfiguration(MobileAds.getRequestConfiguration()
                                                           .toBuilder().setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                                                                       .build());

                MobileAds.initialize(f_context);

                interstitial = new InterstitialAd(f_context);

                interstitial.setAdUnitId(f_interstitial_unit_id);

                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdOpened()
                    {
                        interstitialActiveUpdated(true);
                    }

                    @Override
                    public void onAdClosed()
                    {
                        interstitialActiveUpdated(false);

                        if (interstitial != null) {
                            AdRequest.Builder builder = new AdRequest.Builder();

                            if (showPersonalizedAds) {
                                interstitial.loadAd(builder.build());
                            } else {
                                Bundle extras = new Bundle();

                                extras.putString("npa", "1");

                                interstitial.loadAd(builder.addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                                           .build());
                            }
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode)
                    {
                        if (interstitial != null) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    if (interstitial != null) {
                                        AdRequest.Builder builder = new AdRequest.Builder();

                                        if (showPersonalizedAds) {
                                            interstitial.loadAd(builder.build());
                                        } else {
                                            Bundle extras = new Bundle();

                                            extras.putString("npa", "1");

                                            interstitial.loadAd(builder.addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                                                       .build());
                                        }
                                    }
                                }
                            }, AD_RELOAD_ON_FAILURE_DELAY);
                        }
                    }
                });

                AdRequest.Builder builder = new AdRequest.Builder();

                if (showPersonalizedAds) {
                    interstitial.loadAd(builder.build());
                } else {
                    Bundle extras = new Bundle();

                    extras.putString("npa", "1");

                    interstitial.loadAd(builder.addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                               .build());
                }
            }
        });
    }

    public void setAdsPersonalization(boolean personalized)
    {
        final boolean f_personalized = personalized;

        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                showPersonalizedAds = f_personalized;
            }
        });
    }

    public void showBannerView(String unit_id)
    {
        final String  f_unit_id = unit_id;
        final Context f_context = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);

                if (view instanceof ViewGroup) {
                    ViewGroup view_group = (ViewGroup)view;

                    if (bannerView != null) {
                        view_group.removeView(bannerView);

                        bannerView.destroy();

                        bannerViewHeightUpdated(0);

                        bannerView = null;
                    }

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                                                                   FrameLayout.LayoutParams.WRAP_CONTENT,
                                                                                   Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                    bannerView = new AdView(f_context);

                    bannerView.setAdSize(AdSize.SMART_BANNER);
                    bannerView.setAdUnitId(f_unit_id);
                    bannerView.setLayoutParams(params);
                    bannerView.setVisibility(View.GONE);

                    bannerView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded()
                        {
                            if (bannerView != null) {
                                bannerView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode)
                        {
                            if (bannerView != null) {
                                bannerView.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    bannerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout()
                        {
                            if (bannerView != null) {
                                int height = bannerView.getHeight();

                                if (height > 0) {
                                    bannerViewHeightUpdated(height);
                                } else {
                                    bannerViewHeightUpdated(0);
                                }
                            }
                        }
                    });

                    view_group.addView(bannerView);

                    AdRequest.Builder builder = new AdRequest.Builder();

                    if (showPersonalizedAds) {
                        bannerView.loadAd(builder.build());
                    } else {
                        Bundle extras = new Bundle();

                        extras.putString("npa", "1");

                        bannerView.loadAd(builder.addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                                 .build());
                    }
                }
            }
        });
    }

    public void hideBannerView()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);

                if (view instanceof ViewGroup) {
                    ViewGroup view_group = (ViewGroup)view;

                    if (bannerView != null) {
                        view_group.removeView(bannerView);

                        bannerView.destroy();

                        bannerViewHeightUpdated(0);

                        bannerView = null;
                    }
                }
            }
        });
    }

    public void showInterstitial()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                if (interstitial != null && interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });
    }

    public boolean getInterstitialIsLoaded()
    {
        FutureTask<Boolean> task = new FutureTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call()
            {
                return Boolean.valueOf(interstitial != null && interstitial.isLoaded());
            }
        });

        runOnUiThread(task);

        boolean result = false;

        try {
            result = task.get().booleanValue();
        } catch (Exception ex) {
            Log.e("TreeActivity", "getInterstitialIsLoaded() : " + ex.toString());
        }

        return result;
    }
}
