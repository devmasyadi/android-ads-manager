package com.ahmadsuyadi.adsmanager.module.ads

object ConfigAds {
    var isShowAds = true
    var isShowBanner = true
    var isShowInter = true
    var isShowNative = true
    var intervalInt = 2
    var currentCountInt = 0
    var admobBannerId = ""
    var admobInterId = ""
    var admobNativeId = ""
    var unitAdsID = ""
    var applovinInterId = ""
    var appLovinBannerId = ""
    var isTestMode = true
    //1 Admob, 2 UnityAds, 3 AppLovin
    var modeAds = 1
    var testDevices = ArrayList<String>()
}