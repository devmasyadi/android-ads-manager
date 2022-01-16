package com.ahmadsuyadi.adsmanager.module.ads.applovin

import android.app.Activity
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.R
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAd
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import java.util.concurrent.TimeUnit

class AppLovin: IAds {

    private val TAG = AppLovin::class.java.simpleName
    private lateinit var activity: Activity

    override fun initialize(activity: Activity, gdpr: String?) {
        this.activity = activity
        AppLovinSdk.getInstance( activity ).mediationProvider = "max"
        AppLovinSdk.getInstance( activity ).initializeSdk {
            // AppLovin SDK is initialized, start loading ads
            Log.i(TAG, "AppLovin SDK is initialized, start loading ads")
            createInterstitialAd()
        }
    }

    override fun showBanner(bannerView: RelativeLayout) {
        createBannerAd(bannerView)
    }

    override fun showInterstitial() {
        if  ( interstitialAd.isReady )
        {
            interstitialAd.showAd()
        }
    }

    override fun showNativeAds(nativeView: RelativeLayout) {

    }

    private fun createBannerAd(bannerView: RelativeLayout)
    {
        val adView = MaxAdView(ConfigAds.appLovinBannerId, activity)
        adView.setListener(bannerListener)

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = 90

        adView.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        bannerView.removeAllViews()
        bannerView.addView(adView)

        // Load the ad
        adView.loadAd()
    }

    private val bannerListener = object : MaxAdViewAdListener {
        // MAX Ad Listener
        override fun onAdLoaded(maxAd: MaxAd) {}

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError) {}

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError) {}

        override fun onAdClicked(maxAd: MaxAd) {}

        override fun onAdExpanded(maxAd: MaxAd) {}

        override fun onAdCollapsed(maxAd: MaxAd) {}

        override fun onAdDisplayed(maxAd: MaxAd) { /* DO NOT USE - THIS IS RESERVED FOR FULLSCREEN ADS ONLY AND WILL BE REMOVED IN A FUTURE SDK RELEASE */ }

        override fun onAdHidden(maxAd: MaxAd) { /* DO NOT USE - THIS IS RESERVED FOR FULLSCREEN ADS ONLY AND WILL BE REMOVED IN A FUTURE SDK RELEASE */ }
    }

    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0

    private fun createInterstitialAd()
    {
        interstitialAd = MaxInterstitialAd( ConfigAds.applovinInterId, activity )
        interstitialAd.setListener(interstitialListener)

        // Load the first ad
        interstitialAd.loadAd()
    }

    private val interstitialListener = object : MaxAdListener {
        // MAX Ad Listener
        override fun onAdLoaded(maxAd: MaxAd)
        {
            // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

            // Reset retry attempt
            retryAttempt = 0.0
        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError)
        {
            // Interstitial ad failed to load
            // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

            retryAttempt++
            val delayMillis = TimeUnit.SECONDS.toMillis( Math.pow( 2.0, Math.min( 6.0, retryAttempt ) ).toLong() )

            Handler().postDelayed( { interstitialAd.loadAd() }, delayMillis )
        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError)
        {
            // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
            interstitialAd.loadAd()
        }

        override fun onAdDisplayed(maxAd: MaxAd) {}

        override fun onAdClicked(maxAd: MaxAd) {}

        override fun onAdHidden(maxAd: MaxAd)
        {
            // Interstitial ad is hidden. Pre-load the next ad
            interstitialAd.loadAd()
        }

    }


}