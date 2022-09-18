package com.adsmanager.ads

import android.app.Activity
import com.adsmanager.admob.AdmobOpenAd
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.CallbackOpenAd

class AdsManagerOpenAd(
    private val admobOpenAd: AdmobOpenAd
) {

    private var currentActivity: Activity? = null

    fun setCurrentActivity(activity: Activity) {
        this.currentActivity = activity
        admobOpenAd.currentActivity = currentActivity
    }

    fun getCurrentActivity() = currentActivity

    fun isShowingAd(primaryNetworkOpenAd: NetworkOpenAd): Boolean {
        return handleIsShowing(primaryNetworkOpenAd)
    }

    fun loadAd(
        activity: Activity,
        primaryNetworkOpenAd: NetworkOpenAd,
        primaryOpenAdUnitId: String,
        secondaryNetworkOpenAd: NetworkOpenAd?,
        secondaryOpenAdUnitId: String?,
        tertiaryNetworkOpenAd: NetworkOpenAd?,
        tertiaryOpenAdUnitId: String?,
        callbackAds: CallbackAds?
    ) {
        handleLoad(activity, primaryOpenAdUnitId, primaryNetworkOpenAd, callbackAds)
        secondaryOpenAdUnitId?.let { secondaryNetworkOpenAd?.let {
            handleLoad(activity, secondaryOpenAdUnitId, secondaryNetworkOpenAd, callbackAds)
        } }
        tertiaryOpenAdUnitId?.let { tertiaryNetworkOpenAd?.let {
            handleLoad(activity, tertiaryOpenAdUnitId, tertiaryNetworkOpenAd, callbackAds)
        } }
    }

    fun showAdIfAvailable(
        activity: Activity,
        primaryNetworkOpenAd: NetworkOpenAd,
        primaryOpenAdUnitId: String,
        secondaryNetworkOpenAd: NetworkOpenAd?,
        secondaryOpenAdUnitId: String?,
        tertiaryNetworkOpenAd: NetworkOpenAd?,
        tertiaryOpenAdUnitId: String?,
        callbackOpenAd: CallbackOpenAd?
    ) {
        handleShow(activity, primaryOpenAdUnitId, primaryNetworkOpenAd, object : CallbackOpenAd() {
            override fun onShowAdComplete() {
                if (secondaryNetworkOpenAd == null) {
                    callbackOpenAd?.onShowAdComplete()
                }
            }

            override fun onAdFailedToLoad(error: String?) {
                callbackOpenAd?.onAdFailedToLoad(error)
                secondaryOpenAdUnitId?.let {
                    secondaryNetworkOpenAd?.let {
                        handleShow(
                            activity,
                            secondaryOpenAdUnitId,
                            secondaryNetworkOpenAd,
                            object : CallbackOpenAd() {
                                override fun onShowAdComplete() {
                                    if (tertiaryNetworkOpenAd == null) {
                                        callbackOpenAd?.onShowAdComplete()
                                    }
                                }

                                override fun onAdFailedToLoad(error: String?) {
                                    callbackOpenAd?.onAdFailedToLoad(error)
                                    tertiaryOpenAdUnitId?.let {
                                        tertiaryNetworkOpenAd?.let {
                                            handleShow(
                                                activity,
                                                tertiaryOpenAdUnitId,
                                                tertiaryNetworkOpenAd,
                                                callbackOpenAd
                                            )
                                        }
                                    }
                                }
                            })
                    }
                }
            }
        })
    }


    private fun handleLoad(
        activity: Activity,
        adUnitId: String,
        networkOpenAd: NetworkOpenAd,
        callbackAds: CallbackAds?
    ) {
        when (networkOpenAd) {
            NetworkOpenAd.ADMOB -> admobOpenAd.loadAd(activity, adUnitId, callbackAds)
        }
    }

    private fun handleShow(
        activity: Activity,
        adUnitId: String,
        networkOpenAd: NetworkOpenAd,
        callbackOpenAd: CallbackOpenAd?
    ) {
        when (networkOpenAd) {
            NetworkOpenAd.ADMOB -> admobOpenAd.showAdIfAvailable(activity, adUnitId, callbackOpenAd)
        }
    }

    private fun handleIsShowing(networkOpenAd: NetworkOpenAd): Boolean {
        return when (networkOpenAd) {
            NetworkOpenAd.ADMOB -> admobOpenAd.isShowingAd
        }
    }
}

enum class NetworkOpenAd {
    ADMOB
}