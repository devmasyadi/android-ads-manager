package com.ahmadsuyadi.adsmanager.module.ads.admob

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.ahmadsuyadi.adsmanager.module.ads.IAds
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class AdmobAds : IAds {

    private val TAG = AdmobAds::class.java.simpleName

    private var mInterstitialAd: InterstitialAd? = null

    private lateinit var activity: Activity
    private lateinit var context: Context
    private lateinit var adRequest: AdRequest

    override fun initialize(activity: Activity) {
        this.activity = activity
        context = activity
        MobileAds.initialize(context) {
            Log.i(TAG, "initializationOnStatus: $it")
            MobileAds.setRequestConfiguration(
                    RequestConfiguration.Builder()
                            .setTestDeviceIds(ConfigAds.testDevices)
                            .build()
            )
            loadInt()
        }
    }

    private fun loadInt() {
        InterstitialAd.load(context, ConfigAds.admobInterId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }

    override fun showBanner(bannerView: RelativeLayout) {
        val adView = AdView(context)
        adView.adUnitId = ConfigAds.admobBannerId
        adView.adSize = adSize(bannerView)
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

    override fun showNativeAds(nativeView: TemplateView) {
        val adLoader = AdLoader.Builder(context, ConfigAds.admobNativeId)
                .forUnifiedNativeAd {
                    val background = ColorDrawable(ContextCompat.getColor(context, android.R.color.white))
                    val styles = NativeTemplateStyle.Builder().withMainBackgroundColor(background).build()
                    val template: TemplateView = nativeView
                    template.setStyles(styles)
                    template.setNativeAd(it)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
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