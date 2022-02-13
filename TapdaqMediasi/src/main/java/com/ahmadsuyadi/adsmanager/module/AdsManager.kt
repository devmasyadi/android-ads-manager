package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.tapdaq.NativeAdLayout
import com.ahmadsuyadi.adsmanager.module.ads.tapdaq.TapdaqAds

class AdsManager(
    private val tapdaqAds: TapdaqAds
) {
    fun initialize(activity: Activity, iInitialize: IInitialize) {
        if(ConfigAds.isShowAds)
            tapdaqAds.initialize(activity, iInitialize)
    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds && ConfigAds.isShowBanner)
            tapdaqAds.showBanner(bannerView)
    }

    fun showInterstitial() {
        ConfigAds.currentCountInt++
        if(ConfigAds.isShowAds && ConfigAds.currentCountInt % ConfigAds.intervalInt == 0) tapdaqAds.showInterstitial()
    }

    fun showNative(nativeAdLayout: NativeAdLayout) {
        if(ConfigAds.isShowAds && ConfigAds.isShowNative) tapdaqAds.showNative(nativeAdLayout)
    }
}

interface IInitialize {
    fun onInitialize(isSuccess: Boolean)
}