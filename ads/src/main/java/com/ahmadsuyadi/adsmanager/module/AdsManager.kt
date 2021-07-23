package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.admob.AdmobAds

class AdsManager(private val admobAds: AdmobAds) {
    fun initialize(activity: Activity, gdpr: String?) {
        if (ConfigAds.isShowAds)
            admobAds.initialize(activity, gdpr)
    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds)
            admobAds.showBanner(bannerView)
    }

    fun showInterstitial(useInterval: Boolean? = true) {
        with(ConfigAds) {
            currentCountInt++
            if (isShowAds && currentCountInt % intervalInt == 0 && useInterval == true)
                admobAds.showInterstitial()
            else
                admobAds.showInterstitial()
        }
    }
}