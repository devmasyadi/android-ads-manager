package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.admob.AdmobAds

class AdsManager(private val admobAds: AdmobAds) {
    fun initialize(activity: Activity) {
        if (ConfigAds.isShowAds)
            admobAds.initialize(activity)
    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds)
            admobAds.showBanner(bannerView)
    }

    fun showInterstitial() {
        with(ConfigAds) {
            if (isShowAds && currentCountInt % intervalInt == 0 && currentCountInt != 0)
                admobAds.showInterstitial()
            currentCountInt++
        }
    }
}