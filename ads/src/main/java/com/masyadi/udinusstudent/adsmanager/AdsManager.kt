package com.masyadi.udinusstudent.adsmanager

import android.app.Activity
import android.widget.RelativeLayout
import com.google.android.ads.nativetemplates.TemplateView
import com.masyadi.udinusstudent.adsmanager.ads.ConfigAds
import com.masyadi.udinusstudent.adsmanager.ads.admob.AdmobAds

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
            if (isShowAds && currentCountInt % intervalInt == 0)
            admobAds.showInterstitial()
            currentCountInt++
        }

    }
    fun showNativeAds(nativeView: TemplateView) {
        admobAds.showNativeAds(nativeView)
    }
}