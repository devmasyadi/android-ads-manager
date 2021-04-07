package com.masyadi.udinusstudent.adsmanager.di

import com.masyadi.udinusstudent.adsmanager.AdsManager
import com.masyadi.udinusstudent.adsmanager.ads.admob.AdmobAds
import org.koin.dsl.module

val adsModule = module {
    single { AdmobAds() }
    single {
        AdsManager(get())
    }
}