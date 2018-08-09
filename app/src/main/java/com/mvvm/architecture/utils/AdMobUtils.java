package com.mvvm.architecture.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.mvvm.architecture.BuildConfig;
import com.mvvm.architecture.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdMobUtils {
    public static final String ADS_NATIVE_DEFAULT = "ca-app-pub-3940256099942544/8135179316";
    public static final String ADS_BANNER_DEFAULT = "ca-app-pub-3940256099942544/6300978111";
    public static final String ADS_INTERSTITIAL_DEFAULT = "ca-app-pub-3940256099942544/1033173712";
    public static final String ADS_APP_ID_DEFAULT = "ca-app-pub-3940256099942544~3347511713";
    private static final String dtStart = "2018-10-01T09:27:37Z";
    private static final String dtStartDebug = "2017-10-01T09:27:37Z";

    /**
     * mục đích là để bắt đầu từ 1 thời gian nhất định(dtStart) mới bắt đầu chạy quảng cáo
     *
     * @return true  -> show
     * false -> hide
     */
    private static boolean isShowAdsByDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date;
            if (BuildConfig.DEBUG) {
                date = format.parse(dtStartDebug);
            } else {
                date = format.parse(dtStart);
            }
            Date currentDate = new Date();
            return date.before(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * banner ads
     *
     * @param bannerAds
     */
    public static void initBannerAds(final AdView bannerAds) {
        if (!isShowAdsByDate()) return;
        AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("8F1F546F7621C8AA2E97110B5EB7B8C7")
            .addTestDevice("2DFA0AFE7C0C9B7B32B869F3C5DEDA55")
            .addTestDevice("C74BBFDBAFA4820BA8C2953CCF73D736")
            .addTestDevice("DCC0AD0A2BADCE62CC3EAB97B323CD1D")
            .addTestDevice("91392D9E37D2DC52625BF1AFD53A20FE")
            .addTestDevice("1D09BC07C09695A254028B1C575E4451")
            .addTestDevice("775515D779B8C060E4EDB8EBCCF1A68A")
            .addTestDevice("99C6203E45343014CE61C62D9592D81")
            .addTestDevice("6BB3BA152CAE9E5DEDB18A75561F2B")
            .build();
        bannerAds.loadAd(adRequest);
        bannerAds.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                bannerAds.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                bannerAds.setVisibility(View.GONE);
            }
        });
    }

    /**
     * native ads
     *
     * @param nativeAd
     * @param adView
     */
    private static void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd,
                                                    UnifiedNativeAdView adView) {
        VideoController vc = nativeAd.getVideoController();
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                // TODO: 8/8/2018  
                super.onVideoEnd();
            }
        });
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        ImageView mainImageView = adView.findViewById(R.id.ad_image);
        if (vc.hasVideoContent()) {
            adView.setMediaView(mediaView);
            mainImageView.setVisibility(View.GONE);
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);
            List<NativeAd.Image> images = nativeAd.getImages();
            if (images.size() > 0) {
                mainImageView.setImageDrawable(images.get(0).getDrawable());
            }
        }
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    /**
     * native ads
     *
     * @param activity
     * @param frameLayout
     */
    public static void loadNativeAd(final Activity activity, final FrameLayout frameLayout) {
        if (!isShowAdsByDate()) return;
        String nativeAdId = activity.getString(R.string.native_ad_unit_id);
        if (!BuildConfig.DEBUG) {
            if (TextUtils.equals(nativeAdId, ADS_NATIVE_DEFAULT)) return;
        }
        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeAdId);
        frameLayout.setVisibility(View.GONE);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                    .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
                frameLayout.setVisibility(View.VISIBLE);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder()
            .setStartMuted(false)
            .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO: 8/8/2018
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    /**
     * interstitial ads
     *
     * @param context
     * @param interstitialId
     */
    public static void initInterstitialAds(Context context, String interstitialId) {
        if (!isShowAdsByDate()) return;
        if (!BuildConfig.DEBUG) {
            if (TextUtils.equals(interstitialId, ADS_INTERSTITIAL_DEFAULT)) return;
        }
        final InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
    }
}
