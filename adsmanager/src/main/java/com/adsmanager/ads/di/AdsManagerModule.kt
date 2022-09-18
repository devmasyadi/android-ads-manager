package com.adsmanager.ads.di

import com.adsmanager.admob.AdmobAds
import com.adsmanager.admob.AdmobOpenAd
import com.adsmanager.ads.AdsManager
import com.adsmanager.ads.AdsManagerOpenAd
import com.adsmanager.ads.HandleAds
import com.adsmanager.applovin.ApplovinDiscoveryAds
import com.adsmanager.applovin.ApplovinMaxAds
import com.adsmanager.fan.FanAds
import org.koin.dsl.module

val adsManagerModule = module {
    single { AdmobAds() }
    single { FanAds() }
    single { ApplovinMaxAds() }
    single { ApplovinDiscoveryAds() }
    single { HandleAds(get(), get(), get(), get()) }
    single { AdsManager(get()) }
    single { AdmobOpenAd() }
    single { AdsManagerOpenAd(get()) }
}