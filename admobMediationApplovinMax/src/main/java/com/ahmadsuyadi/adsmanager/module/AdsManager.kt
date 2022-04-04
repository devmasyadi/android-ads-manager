package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.admobMediation.AdmobMediationAds
import com.ahmadsuyadi.adsmanager.module.ads.applovin.ApplovinAds
import com.google.android.ads.nativetemplates.TemplateView

class AdsManager(
    private val applovinAds: ApplovinAds,
    private val admobMediationAds: AdmobMediationAds
) {
    fun initialize(activity: Activity, iInitialize: IInitialize) {
        if (ConfigAds.isShowAds)
            when (ConfigAds.modeAds) {
                3 -> applovinAds.initialize(activity, iInitialize)
                5 -> admobMediationAds.initialize(activity, iInitialize)
            }

    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds && ConfigAds.isShowBanner)
            when (ConfigAds.modeAds) {
                3 -> applovinAds.showBanner(bannerView)
                5 -> admobMediationAds.showBanner(bannerView)
            }

    }

    fun showInterstitial() {
        ConfigAds.currentCountInt++
        if (ConfigAds.isShowAds && ConfigAds.isShowInter && ConfigAds.currentCountInt % ConfigAds.intervalInt == 0) {
            when (ConfigAds.modeAds) {
                3 -> applovinAds.showInterstitial()
                5 -> admobMediationAds.showInterstitial()
            }
        }
    }

    fun showNative(nativeAdContainer: RelativeLayout, nativeAdmob: TemplateView) {
        if (ConfigAds.isShowAds && ConfigAds.isShowNative)
            when (ConfigAds.modeAds) {
                3 -> applovinAds.showNative(nativeAdContainer)
                5 -> admobMediationAds.showNativeAdsAdmob(nativeAdmob)
            }

    }
}

interface IInitialize {
    fun onInitialize(isSuccess: Boolean)
}