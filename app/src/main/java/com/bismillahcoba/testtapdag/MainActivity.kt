package com.bismillahcoba.testtapdag

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import com.bismillahcoba.testtapdag.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val adsManager: AdsManager by  inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ConfigAds.testDevices.add("CB4B9DBC445BCD662CF9794A73E65B13")
        ConfigAds.tapdaqApplicationId = "6204a24c86c31c4e07c1183e"
        ConfigAds.tapdaqClientKey = "6ea0953d-2d5b-43e2-a304-d9f61aeec26d"
        ConfigAds.intervalInt = 2
        adsManager.initialize(this)

        with(activityMainBinding) {
            btnShowInt.setOnClickListener {
                adsManager.showInterstitial()
            }
            btnShowBanner.setOnClickListener {
                adsManager.showBanner(viewBanner)
            }
            btnShowNative.setOnClickListener {
                adsManager.showNative(nativeAd)
            }
        }
    }


}