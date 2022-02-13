package com.ahmadsuyadi.adsmanager.module.ads.tapdaq

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.IInitialize
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.tapdaq.sdk.STATUS
import com.tapdaq.sdk.TMBannerAdView
import com.tapdaq.sdk.Tapdaq
import com.tapdaq.sdk.adnetworks.TDMediatedNativeAd
import com.tapdaq.sdk.adnetworks.TDMediatedNativeAdOptions
import com.tapdaq.sdk.adnetworks.TMMediationNetworks
import com.tapdaq.sdk.common.TMAdError
import com.tapdaq.sdk.common.TMBannerAdSizes
import com.tapdaq.sdk.listeners.TMAdListener
import com.tapdaq.sdk.listeners.TMInitListener


class TapdaqAds : IAds {

    private lateinit var activity: Activity
    private lateinit var iInitialize: IInitialize

    override fun initialize(activity: Activity, iInitialize: IInitialize) {
        this.iInitialize = iInitialize
        this.activity = activity
        val config = Tapdaq.getInstance().config()
        config.registerTestDevices(TMMediationNetworks.AD_MOB, ConfigAds.testDevices)
        config.registerTestDevices(TMMediationNetworks.FACEBOOK, ConfigAds.testDevices);
        config.userSubjectToGdprStatus = STATUS.TRUE //GDPR declare if user is in EU
        config.consentStatus = STATUS.TRUE //GDPR consent must be obtained from the user
        config.ageRestrictedUserStatus =
            STATUS.FALSE //Is user subject to COPPA or GDPR age restrictions
        config.setAutoReloadAds(true)
        Tapdaq.getInstance()
            .initialize(
                activity,
                ConfigAds.tapdaqApplicationId,
                ConfigAds.tapdaqClientKey,
                config,
                initLister
            )
    }


    fun showNative(nativeAdLayout: NativeAdLayout) {
        val mAd = HashMap<String, TDMediatedNativeAd>()
        val options = TDMediatedNativeAdOptions() //optional param
        Tapdaq.getInstance().loadMediatedNativeAd(
            activity,
            "default",
            options,
            object : TMAdListener() {
                override fun didLoad(ad: TDMediatedNativeAd) {
                    // Provides the ad object full of data
                    mAd["default"] = ad
                    nativeAdLayout.clear()
                    nativeAdLayout.populate(mAd["default"])
                    mAd.remove("default")
                }

                override fun didFailToLoad(error: TMAdError?) {
                    super.didFailToLoad(error)
                }
            }
        )
    }

    override fun showBanner(bannerView: RelativeLayout) {
        val ad = TMBannerAdView(activity) // Create ad view
        ad.load(activity, TMBannerAdSizes.STANDARD, bannerListener)
        bannerView.removeAllViews()
        bannerView.addView(ad)
    }

    override fun showInterstitial() {
        if (Tapdaq.getInstance().isVideoReady(activity, "default")) {
            Tapdaq.getInstance().showVideo(activity, "default", intListener)
        } else {
            Tapdaq.getInstance().loadVideo(activity, "default", intListener)
        }
    }

    private val initLister = object : TMInitListener() {
        override fun didInitialise() {
            super.didInitialise()
            // Ads may now be requested
            Tapdaq.getInstance().loadVideo(activity, "default", intListener)
            iInitialize.onInitialize(true)
        }

        override fun didFailToInitialise(error: TMAdError?) {
            super.didFailToInitialise(error)
            //Tapdaq failed to initialise
            iInitialize.onInitialize(false)
        }
    }

    private val intListener = object : TMAdListener() {
        override fun didClose() {
            Tapdaq.getInstance().loadVideo(activity, "default", this)
        }
    }

    private val bannerListener = object : TMAdListener() {
        override fun didLoad() {
            // First banner loaded into view
        }

        override fun didFailToLoad(error: TMAdError) {
            // No banners available. View will stop refreshing
        }

        override fun didRefresh() {
            // Subequent banner loaded, this view will refresh every 30 seconds
        }

        override fun didFailToRefresh(error: TMAdError) {
            // Banner could not load, this view will attempt another refresh every 30 seconds
        }

        override fun didClick() {
            // User clicked on banner
        }
    }

}