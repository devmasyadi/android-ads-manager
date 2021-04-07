package com.masyadi.udinusstudent.adsmanager.ads

import android.app.Activity
import android.widget.RelativeLayout
import com.google.android.ads.nativetemplates.TemplateView

interface IAds {
    fun initialize(activity: Activity)
    fun showBanner(bannerView: RelativeLayout)
    fun showInterstitial()
    fun showNativeAds(nativeView: TemplateView)
}