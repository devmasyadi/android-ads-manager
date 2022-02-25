package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.admobMediation.AdmobMediationAds
import com.ahmadsuyadi.adsmanager.module.ads.tapdaq.NativeAdLayout
import com.ahmadsuyadi.adsmanager.module.ads.tapdaq.TapdaqAds
import com.google.android.ads.nativetemplates.TemplateView

class AdsManager(
    private val tapdaqAds: TapdaqAds,
    private val admobMediationAds: AdmobMediationAds
) {
    fun initialize(activity: Activity, iInitialize: IInitialize) {
        if(ConfigAds.isShowAds)
            when(ConfigAds.modeAds) {
                4 -> tapdaqAds.initialize(activity, iInitialize)
                5 -> admobMediationAds.initialize(activity, iInitialize)
            }

    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds && ConfigAds.isShowBanner)
            when(ConfigAds.modeAds) {
                4 ->  tapdaqAds.showBanner(bannerView)
                5 -> admobMediationAds.showBanner(bannerView)
            }

    }

    fun showInterstitial() {
        ConfigAds.currentCountInt++
        if(ConfigAds.isShowAds && ConfigAds.currentCountInt % ConfigAds.intervalInt == 0) {
            when(ConfigAds.modeAds) {
                4 -> tapdaqAds.showInterstitial()
                5 -> admobMediationAds.showInterstitial()
            }
        }
    }

    fun showNative(nativeAdLayout: NativeAdLayout, nativeAdmob: TemplateView) {
        if(ConfigAds.isShowAds && ConfigAds.isShowNative)
            when(ConfigAds.modeAds) {
                4 -> tapdaqAds.showNative(nativeAdLayout)
                5 -> admobMediationAds.showNativeAdsAdmob(nativeAdmob)
            }

    }
}

interface IInitialize {
    fun onInitialize(isSuccess: Boolean)
}