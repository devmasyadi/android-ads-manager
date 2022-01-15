package com.ahmadsuyadi.adsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import com.ahmadsuyadi.adsmanager.module.AdsManager
import com.ahmadsuyadi.adsmanager.module.ads.ConfigAds
import kotlinx.android.synthetic.main.native_ads.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val adsManager: AdsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShowBanner = findViewById<Button>(R.id.btnShowBanner)
        val btnShowInter = findViewById<Button>(R.id.btnShowInter)
        val btnShowNative = findViewById<Button>(R.id.btnShowNative)
        val rlBanner = findViewById<RelativeLayout>(R.id.rlBanner)
        val rlNativeAds = findViewById<RelativeLayout>(R.id.rlNativeView)

        ConfigAds.admobBannerId = "ca-app-pub-3940256099942544/6300978111"
        ConfigAds.admobInterId = "ca-app-pub-3940256099942544/1033173712"
        ConfigAds.admobNativeId = "ca-app-pub-3940256099942544/2247696110"
        ConfigAds.unitAdsID = "4501269"
        ConfigAds.modeAds = 2
        ConfigAds.isTestMode = true
        ConfigAds.applovinInterId = "c03749d599e3418c"
        ConfigAds.appLovinBannerId = "8fe21f94479ef6d5"

        adsManager.initialize(this, "1")

        btnShowBanner.setOnClickListener {
            adsManager.showBanner(rlBanner)
        }

        btnShowInter.setOnClickListener {
            adsManager.showInterstitial(true)
        }

        btnShowNative.setOnClickListener {
            adsManager.showNativeAds(rlNativeAds, my_template)
        }
    }
}