package com.ahmadsuyadi.adsmanager.module.ads

import android.app.Activity
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.IInitialize

interface IAds {
    fun initialize(activity: Activity, iInitialize: IInitialize)
    fun showBanner(bannerView: RelativeLayout)
    fun showInterstitial()
}