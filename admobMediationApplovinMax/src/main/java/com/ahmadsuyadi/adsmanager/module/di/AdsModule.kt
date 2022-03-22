package com.ahmadsuyadi.adsmanager.module.di

import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.admobMediation.AdmobMediationAds
import com.ahmadsuyadi.adsmanager.module.ads.applovin.ApplovinAds
import org.koin.dsl.module

val adsModule = module {
    single { ApplovinAds() }
    single { AdmobMediationAds() }
    single {
        AdsManager(get(), get())
    }
}