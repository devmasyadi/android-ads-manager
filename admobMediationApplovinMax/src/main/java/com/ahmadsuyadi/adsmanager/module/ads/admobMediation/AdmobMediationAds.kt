package com.ahmadsuyadi.adsmanager.module.ads.admobMediation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.IInitialize
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobMediationAds : IAds {

    private lateinit var adRequest: AdRequest
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var activity: Activity
    private lateinit var context: Context

    override fun initialize(activity: Activity, iInitialize: IInitialize) {
        this.activity = activity
        context = activity
        MobileAds.initialize(activity) { initializationStatus ->
            val statusMap =
                initializationStatus.adapterStatusMap
            for (adapterClass in statusMap.keys) {
                val status = statusMap[adapterClass]
                Log.d(
                    "AdmobMediationAds", String.format(
                        "Adapter name: %s, Description: %s, Latency: %d",
                        adapterClass, status!!.description, status.latency
                    )
                )
            }

            iInitialize.onInitialize(true)
            // Start loading ads here...
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                    .setTestDeviceIds(ConfigAds.testDevices)
                    .build()
            )
            val extras = Bundle()
            adRequest =
                AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
            loadInt()
        }

    }

    private fun loadInt() {
        InterstitialAd.load(
            context,
            ConfigAds.admobInterId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    @SuppressLint("MissingPermission")
    override fun showBanner(bannerView: RelativeLayout) {
        val adView = AdView(context)
        adView.adUnitId = ConfigAds.admobBannerId
        adView.adSize = adSize(bannerView)
        bannerView.removeAllViews()
        bannerView.addView(adView)
        adView.loadAd(adRequest)
    }

    override fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    loadInt()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {

                }
            }
            mInterstitialAd?.show(activity)
        } else
            loadInt()
    }

    @SuppressLint("MissingPermission")
    fun showNativeAdsAdmob(template: TemplateView) {
        val adLoader = AdLoader.Builder(
            context,
            ConfigAds.admobNativeId
        )
            .forNativeAd { nativeAd ->
                val styles =
                    NativeTemplateStyle.Builder()
                        .build()
                template.setStyles(styles)
                template.setNativeAd(nativeAd)
            }
            .build()
        adLoader.loadAd(adRequest)
    }

    private fun adSize(bannerView: RelativeLayout): AdSize {
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = bannerView.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }
}