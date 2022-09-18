package com.adsmanager.ads

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.IRewards
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.iadsmanager.SizeBanner
import com.adsmanager.core.iadsmanager.SizeNative

interface IAds {

    fun initialize(
        context: Context,
        iInitialize: IInitialize,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
    )

    fun setTestDevices(
        activity: Activity, testDevices: List<String>, primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?
    ) {

    }

    fun loadGdpr(activity: Activity, childDirected: Boolean, primaryAds: NetworkAds)

    fun showBanner(
        activity: Activity,
        bannerView: RelativeLayout,
        sizeBanner: SizeBanner,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?
    )

    fun loadInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
    )

    fun showInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?
    )

    fun showNativeAds(
        activity: Activity,
        nativeView: RelativeLayout,
        sizeNative: SizeNative,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?
    )

    fun loadRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
    )

    fun showRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?,
        iRewards: IRewards?
    )
}

enum class NetworkAds {
    ADMOB,
    FAN,
    APPLOVIN_MAX,
    APPLOVIN_DISCOVERY,
}
