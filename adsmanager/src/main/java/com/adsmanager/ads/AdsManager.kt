package com.adsmanager.ads

import android.app.Activity
import android.content.Context
import android.widget.RelativeLayout
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.IRewards
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.iadsmanager.SizeBanner
import com.adsmanager.core.iadsmanager.SizeNative

class AdsManager(
    private val handleAds: HandleAds
) : IAds {
    override fun initialize(
        context: Context,
        iInitialize: IInitialize,
        primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?,
    ) {
        handleAds.initialize(context, iInitialize, primaryAds)
        secondaryAds?.let { handleAds.initialize(context, iInitialize, it) }
        tertiaryAds?.let { handleAds.initialize(context, iInitialize, it) }
        iInitialize.onInitializationComplete()
    }

    override fun setTestDevices(
        activity: Activity, testDevices: List<String>, primaryAds: NetworkAds,
        secondaryAds: NetworkAds?,
        tertiaryAds: NetworkAds?
    ) {
        handleAds.setTestDevices(activity, testDevices, primaryAds)
        secondaryAds?.let { handleAds.setTestDevices(activity, testDevices, it) }
        tertiaryAds?.let { handleAds.setTestDevices(activity, testDevices, it) }
    }

    override fun loadGdpr(activity: Activity, childDirected: Boolean, primaryAds: NetworkAds) {
        handleAds.loadGdpr(activity, childDirected, primaryAds)
    }

    override fun showBanner(
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
    ) {
        handleAds.showBanner(
            activity,
            bannerView,
            sizeBanner,
            primaryAds,
            adUnitPrimaryId,
            object : CallbackAds() {
                override fun onAdFailedToLoad(error: String?) {
                    callbackAds?.onAdFailedToLoad(error)
                    secondaryAds?.let {
                        adUnitSecondaryId?.let {
                            handleAds.showBanner(
                                activity,
                                bannerView,
                                sizeBanner,
                                secondaryAds,
                                adUnitSecondaryId,
                                object : CallbackAds() {
                                    override fun onAdFailedToLoad(error: String?) {
                                        callbackAds?.onAdFailedToLoad(error)
                                        tertiaryAds?.let {
                                            adUnitTertiaryAdsId?.let {
                                                handleAds.showBanner(
                                                    activity,
                                                    bannerView,
                                                    sizeBanner,
                                                    tertiaryAds,
                                                    adUnitTertiaryAdsId,
                                                    callbackAds
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

    override fun loadInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?
    ) {
        handleAds.loadInterstitial(activity, primaryAds, adUnitPrimaryId)
        secondaryAds?.let {
            adUnitSecondaryId?.let {
                handleAds.loadInterstitial(
                    activity, secondaryAds,
                    adUnitSecondaryId
                )
            }
        }
        tertiaryAds?.let {
            adUnitTertiaryAdsId?.let {
                handleAds.loadInterstitial(
                    activity, tertiaryAds,
                    adUnitTertiaryAdsId
                )
            }
        }
    }

    override fun showInterstitial(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?
    ) {
        handleAds.showInterstitial(activity, primaryAds, adUnitPrimaryId, object : CallbackAds() {
            override fun onAdFailedToLoad(error: String?) {
                callbackAds?.onAdFailedToLoad(error)
                secondaryAds?.let {
                    adUnitSecondaryId?.let {
                        handleAds.showInterstitial(
                            activity,
                            secondaryAds,
                            adUnitSecondaryId,
                            object : CallbackAds() {
                                override fun onAdFailedToLoad(error: String?) {
                                    callbackAds?.onAdFailedToLoad(error)
                                    tertiaryAds?.let {
                                        adUnitTertiaryAdsId?.let {
                                            handleAds.showInterstitial(
                                                activity,
                                                tertiaryAds,
                                                adUnitTertiaryAdsId,
                                                callbackAds
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

    override fun showNativeAds(
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
    ) {
        handleAds.showNativeAds(
            activity,
            nativeView,
            sizeNative,
            primaryAds,
            adUnitPrimaryId,
            object : CallbackAds() {
                override fun onAdFailedToLoad(error: String?) {
                    callbackAds?.onAdFailedToLoad(error)
                    secondaryAds?.let {
                        adUnitSecondaryId?.let {
                            handleAds.showNativeAds(
                                activity,
                                nativeView,
                                sizeNative,
                                secondaryAds,
                                adUnitSecondaryId,
                                object : CallbackAds() {
                                    override fun onAdFailedToLoad(error: String?) {
                                        callbackAds?.onAdFailedToLoad(error)
                                        tertiaryAds?.let {
                                            adUnitTertiaryAdsId?.let {
                                                handleAds.showNativeAds(
                                                    activity,
                                                    nativeView,
                                                    sizeNative,
                                                    tertiaryAds,
                                                    adUnitTertiaryAdsId,
                                                    callbackAds
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

    override fun loadRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?
    ) {
        handleAds.loadRewards(activity, primaryAds, adUnitPrimaryId)
        secondaryAds?.let {
            adUnitSecondaryId?.let {
                handleAds.loadRewards(
                    activity, secondaryAds,
                    adUnitSecondaryId
                )
            }
        }
        tertiaryAds?.let {
            adUnitTertiaryAdsId?.let {
                handleAds.loadRewards(
                    activity, tertiaryAds,
                    adUnitTertiaryAdsId
                )
            }
        }
    }

    override fun showRewards(
        activity: Activity,
        primaryAds: NetworkAds,
        adUnitPrimaryId: String,
        secondaryAds: NetworkAds?,
        adUnitSecondaryId: String?,
        tertiaryAds: NetworkAds?,
        adUnitTertiaryAdsId: String?,
        callbackAds: CallbackAds?,
        iRewards: IRewards?
    ) {
        handleAds.showRewards(activity, primaryAds, adUnitPrimaryId, object : CallbackAds() {
            override fun onAdFailedToLoad(error: String?) {
                callbackAds?.onAdFailedToLoad(error)
                secondaryAds?.let {
                    adUnitSecondaryId?.let {
                        handleAds.showRewards(
                            activity,
                            secondaryAds,
                            adUnitSecondaryId,
                            object : CallbackAds() {
                                override fun onAdFailedToLoad(error: String?) {
                                    callbackAds?.onAdFailedToLoad(error)
                                    tertiaryAds?.let {
                                        adUnitTertiaryAdsId?.let {
                                            handleAds.showRewards(
                                                activity,
                                                tertiaryAds,
                                                adUnitTertiaryAdsId,
                                                callbackAds,
                                                iRewards
                                            )
                                        }
                                    }
                                }
                            },
                            iRewards
                        )
                    }
                }
            }
        }, iRewards)
    }

}