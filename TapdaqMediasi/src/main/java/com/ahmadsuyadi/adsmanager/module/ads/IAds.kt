package com.ahmadsuyadi.adsmanager.module.ads

import android.app.Activity
import android.widget.RelativeLayout

interface IAds {
    fun initialize(activity: Activity)
    fun showBanner(bannerView: RelativeLayout)
    fun showInterstitial()
}