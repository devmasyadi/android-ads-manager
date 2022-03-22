package com.ahmadsuyadi.adsmanager.module.ads.applovin

import android.app.Activity
import android.os.Handler
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.R
import com.ahmadsuyadi.adsmanager.module.IInitialize
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.sdk.AppLovinSdk
import java.util.concurrent.TimeUnit
import kotlin.math.pow


class ApplovinAds: IAds {
    private lateinit var activity: Activity
    override fun initialize(activity: Activity, iInitialize: IInitialize) {
        this.activity = activity
        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance(this.activity).mediationProvider = "max"
        AppLovinSdk.initializeSdk(this.activity) {
            // AppLovin SDK is initialized, start loading ads
            createInterstitialAd()
            iInitialize.onInitialize(true)
        }
    }

    override fun showBanner(bannerView: RelativeLayout) {
        createBannerAd(bannerView)
    }

    override fun showInterstitial() {
        if  ( interstitialAd.isReady )
        {
            interstitialAd.showAd()
        } else
            createInterstitialAd()
    }

    private fun createBannerAd(bannerView: RelativeLayout)
    {
        val adView = MaxAdView(ConfigAds.appLovinBannerId, activity)
        adView.setListener(bannerListener)

        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT

        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = activity.resources.getDimensionPixelSize(R.dimen.banner_height)

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
            val delayMillis = TimeUnit.SECONDS.toMillis( 2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong() )

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

    private lateinit var nativeAdLoader: MaxNativeAdLoader
    private var nativeAd: MaxAd? = null

    fun showNative(nativeAdContainer: RelativeLayout)
    {
        nativeAdLoader = MaxNativeAdLoader( ConfigAds.applovinNativeId, activity )
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd?)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd )
                }

                // Save ad for cleanup.
                nativeAd = ad

                // Add ad view to view.
                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView( nativeAdView )
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            override fun onNativeAdClicked(ad: MaxAd)
            {
                // Optional click callback
            }
        })
        nativeAdLoader.loadAd()
    }
}