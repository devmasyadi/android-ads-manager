package com.ahmadsuyadi.adsmanager.module.di

import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.admobMediation.AdmobMediationAds
import com.ahmadsuyadi.adsmanager.module.ads.tapdaq.TapdaqAds
import org.koin.dsl.module

val adsModule = module {
    single { TapdaqAds() }
    single { AdmobMediationAds() }
    single {
        AdsManager(get(), get())
    }
}