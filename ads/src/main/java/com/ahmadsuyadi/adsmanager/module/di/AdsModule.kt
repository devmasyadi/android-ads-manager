package com.ahmadsuyadi.adsmanager.module.di

import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.admob.AdmobAds
import com.ahmadsuyadi.adsmanager.module.ads.applovin.AppLovin
import com.ahmadsuyadi.adsmanager.module.ads.unityAds.UnityAdsModule
import org.koin.dsl.module

val adsModule = module {
    single { AdmobAds() }
    single { UnityAdsModule() }
    single { AppLovin() }
    single {
        AdsManager(get(), get(), get())
    }
}