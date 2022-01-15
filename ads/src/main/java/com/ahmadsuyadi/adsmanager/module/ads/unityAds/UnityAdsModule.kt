package com.ahmadsuyadi.adsmanager.module.ads.unityAds

import android.app.Activity
import android.util.Log
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions

import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds.*
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView.IListener
import com.unity3d.services.banners.UnityBannerSize





class UnityAdsModule: IAds, IUnityAdsInitializationListener {

    private val TAG = UnityAdsModule::class.java.simpleName
    private lateinit var activity: Activity
    private lateinit var bannerView: BannerView

    override fun initialize(activity: Activity, gdpr: String?) {
        this.activity = activity
        initialize(activity, ConfigAds.unitAdsID, ConfigAds.isTestMode, this);
        bannerView = BannerView(activity, "Banner_Android", UnityBannerSize(320, 50))
        bannerView.listener = bannerListener
    }

    override fun showBanner(bannerView: RelativeLayout) {
        loadBannerAd(this.bannerView, bannerView)
    }

    override fun showInterstitial() {
        displayInterstitialAd()
    }

    override fun showNativeAds(nativeView: RelativeLayout) {

    }

    override fun onInitializationComplete() {
        Log.i(TAG, "onInitializationComplete")

    }

    override fun onInitializationFailed(p0: UnityAds.UnityAdsInitializationError?, p1: String?) {
        Log.e(TAG, "onInitializationFailed: $p0, $p1")
    }

    private fun displayInterstitialAd() {
        load("Interstitial_Android", loadListener)
    }

    private val loadListener: IUnityAdsLoadListener = object : IUnityAdsLoadListener {
        override fun onUnityAdsAdLoaded(placementId: String) {
            show(
                activity,
                "Interstitial_Android",
                UnityAdsShowOptions(),
                showListener
            )
        }

        override fun onUnityAdsFailedToLoad(
            placementId: String,
            error: UnityAdsLoadError,
            message: String
        ) {
            Log.e(
                "UnityAdsExample",
                "Unity Ads failed to load ad for $placementId with error: [$error] $message"
            )
        }
    }

    private val showListener: IUnityAdsShowListener = object : IUnityAdsShowListener {
        override fun onUnityAdsShowFailure(
            placementId: String,
            error: UnityAdsShowError,
            message: String
        ) {
            Log.e(
                TAG,
                "Unity Ads failed to show ad for $placementId with error: [$error] $message"
            )
        }

        override fun onUnityAdsShowStart(placementId: String) {
            Log.v(TAG, "onUnityAdsShowStart: $placementId")
        }

        override fun onUnityAdsShowClick(placementId: String) {
            Log.v(TAG, "onUnityAdsShowClick: $placementId")
        }

        override fun onUnityAdsShowComplete(
            placementId: String,
            state: UnityAdsShowCompletionState
        ) {
            Log.v(TAG, "onUnityAdsShowComplete: $placementId")
        }
    }

    private fun loadBannerAd(bannerView: BannerView, bannerLayout: RelativeLayout) {
        // Request a banner ad:
        bannerView.load()
        // Associate the banner view object with the banner view:
        bannerLayout.addView(bannerView)
    }

    // Listener for banner events:
    private val bannerListener: IListener = object : IListener {
        override fun onBannerLoaded(bannerAdView: BannerView) {
            // Called when the banner is loaded.
            Log.v("UnityAdsExample", "onBannerLoaded: " + bannerAdView.placementId)
        }

        override fun onBannerFailedToLoad(bannerAdView: BannerView, errorInfo: BannerErrorInfo) {
            Log.e(
                "UnityAdsExample",
                "Unity Ads failed to load banner for " + bannerAdView.placementId + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage
            )
            // Note that the BannerErrorInfo object can indicate a no fill (see API documentation).
        }

        override fun onBannerClick(bannerAdView: BannerView) {
            // Called when a banner is clicked.
            Log.v("UnityAdsExample", "onBannerClick: " + bannerAdView.placementId)
        }

        override fun onBannerLeftApplication(bannerAdView: BannerView) {
            // Called when the banner links out of the application.
            Log.v("UnityAdsExample", "onBannerLeftApplication: " + bannerAdView.placementId)
        }
    }


}