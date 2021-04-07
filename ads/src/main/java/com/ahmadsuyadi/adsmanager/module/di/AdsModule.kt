package com.ahmadsuyadi.adsmanager.module.di

import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.admob.AdmobAds
import org.koin.dsl.module

val adsModule = module {
    single { AdmobAds() }
    single {
        AdsManager(get())
    }
}