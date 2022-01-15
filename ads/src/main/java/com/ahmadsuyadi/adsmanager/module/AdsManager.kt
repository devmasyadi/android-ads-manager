package com.ahmadsuyadi.adsmanager.module

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.admob.AdmobAds
import com.ahmadsuyadi.adsmanager.module.ads.applovin.AppLovin
import com.ahmadsuyadi.adsmanager.module.ads.unityAds.UnityAdsModule
import com.google.android.ads.nativetemplates.TemplateView

class AdsManager(
    private val admobAds: AdmobAds,
    private val unityAds: UnityAdsModule,
    private val appLovin: AppLovin
) {
    fun initialize(activity: Activity, gdpr: String?) {
        if (ConfigAds.isShowAds) {
            when(ConfigAds.modeAds) {
                1 -> admobAds.initialize(activity, gdpr)
                2 -> unityAds.initialize(activity, gdpr)
                3 -> appLovin.initialize(activity, gdpr)
            }
        }
    }

    fun showBanner(bannerView: RelativeLayout) {
        if (ConfigAds.isShowAds && ConfigAds.isShowBanner)
            when (ConfigAds.modeAds) {
                1 -> admobAds.showBanner(bannerView)
                2 -> unityAds.showBanner(bannerView)
                3 -> appLovin.showBanner(bannerView)
            }
    }

    fun showInterstitial(useInterval: Boolean? = true) {
        with(ConfigAds) {
            currentCountInt++
            if (isShowAds && currentCountInt % intervalInt == 0 && useInterval == true && isShowInter)
                when (modeAds) {
                    1 -> admobAds.showInterstitial()
                    2 -> unityAds.showInterstitial()
                    3 -> appLovin.showInterstitial()
                }
            else
                when (modeAds) {
                    1 -> admobAds.showInterstitial()
                    2 -> unityAds.showInterstitial()
                    3 -> appLovin.showInterstitial()
                }
        }
    }

    fun showNativeAds(nativeView: RelativeLayout, nativeAdmob: TemplateView) {
        if (ConfigAds.isShowAds && ConfigAds.isShowNative)
            when (ConfigAds.modeAds) {
                1 -> admobAds.showNativeAdsAdmob(nativeView, nativeAdmob)
                2 -> unityAds.showNativeAds(nativeView)
                3 -> appLovin.showNativeAds(nativeView)
            }
    }
}