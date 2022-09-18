package com.adsmanager.androidmodule

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.adsmanager.ads.AdsManager
import com.adsmanager.ads.AdsManagerOpenAd
import com.adsmanager.ads.NetworkAds
import com.adsmanager.ads.NetworkOpenAd
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.CallbackOpenAd
import com.adsmanager.core.IRewards
import com.adsmanager.core.RewardsItem
import com.adsmanager.core.iadsmanager.IInitialize
import com.adsmanager.core.iadsmanager.SizeBanner
import com.adsmanager.core.iadsmanager.SizeNative
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val adsManager: AdsManager by inject()
    private val adsManagerOpenAd: AdsManagerOpenAd by inject()

    private val primaryNetworkOpenAd = NetworkOpenAd.ADMOB

    private val primaryAds = NetworkAds.ADMOB
    private val secondaryAds = NetworkAds.FAN
    private val tertiaryAds = NetworkAds.APPLOVIN_MAX

    private val adUnitOpenAdId = "ca-app-pub-3940256099942544/3419835294"

    private val primaryBannerId = "ca-app-pub-3940256099942544/6300978111XXX"
    private val secondaryBannerId = "1363711600744576_1363713000744436"
    private val tertiaryBannerId = "62c9e910bbd85680"

    private val primaryInterstitialId = "ca-app-pub-3940256099942544/1033173712"
    private val secondaryInterstitialId = "1363711600744576_1508878896227845"
    private val tertiaryInterstitialId = "7263a762d1a5366b"

    private val primaryNativeId = "ca-app-pub-4764558539538067/9810032480XXX"
    private val secondaryNativeId = "1363711600744576_1508905202891881"
    private val tertiaryNativeId = "91294e31700550f5"

    private val primaryRewardsId = "ca-app-pub-3940256099942544/5224354917"
    private val secondaryRewardsId = "1363711600744576_1508879032894498"
    private val tertiaryRewardsId = "c11378688d2adfd1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adsManager.initialize(
            this,
            iInitialize = object : IInitialize {
                override fun onInitializationComplete() {
                    adsManager.loadGdpr(this@MainActivity, true, primaryAds)
                    adsManager.loadInterstitial(
                        this@MainActivity,
                        primaryAds,
                        primaryInterstitialId,
                        secondaryAds,
                        secondaryInterstitialId,
                        tertiaryAds,
                        tertiaryInterstitialId,
                    )
                    adsManager.loadRewards(
                        this@MainActivity,
                        primaryAds,
                        primaryBannerId,
                        secondaryAds,
                        secondaryBannerId,
                        tertiaryAds,
                        tertiaryBannerId,
                    )
                }
            },
            primaryAds = primaryAds,
            secondaryAds = secondaryAds,
            tertiaryAds = tertiaryAds,
        )

        adsManager.setTestDevices(
            this,
            listOf("ff85a790-04c7-4fbe-9ad5-e2a040685b69"),
            primaryAds,
            secondaryAds,
            tertiaryAds
        )

        findViewById<Button>(R.id.btnShowOpenApp).setOnClickListener {
            adsManagerOpenAd.showAdIfAvailable(
                this,
                primaryNetworkOpenAd,
                adUnitOpenAdId,
                null,
                null,
                null,
                null,
                object : CallbackOpenAd() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "openAd onAdFailedToLoad: $error")
                    }

                    override fun onAdLoaded() {
                        Log.e("HALLO", "openAd onAdLoaded")
                    }

                    override fun onShowAdComplete() {
                        Log.e("HALLO", "openAd onShowAdComplete")
                    }

                })
        }

        findViewById<Button>(R.id.btnShowBanner).setOnClickListener {
            val bannerView = findViewById<RelativeLayout>(R.id.bannerView)
            adsManager.showBanner(
                this,
                bannerView,
                SizeBanner.SMALL,
                primaryAds,
                primaryBannerId,
                secondaryAds,
                secondaryBannerId,
                tertiaryAds,
                tertiaryBannerId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "banner error: $error")
                    }
                })
        }

        findViewById<Button>(R.id.btnShowInterstitial).setOnClickListener {
            adsManager.showInterstitial(this,
                primaryAds,
                primaryInterstitialId,
                secondaryAds,
                secondaryInterstitialId,
                tertiaryAds,
                tertiaryInterstitialId, object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "interstitial error: $error")
                    }
                })
        }

        findViewById<Button>(R.id.btnShowRewards).setOnClickListener {
            adsManager.showRewards(this,
                primaryAds,
                primaryRewardsId,
                secondaryAds,
                secondaryRewardsId,
                tertiaryAds,
                tertiaryRewardsId, object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "rewards error: $error")
                    }
                },
                object : IRewards {
                    override fun onUserEarnedReward(rewardsItem: RewardsItem?) {
                        Log.i("HALLO", "onUserEarnedReward: $rewardsItem")
                    }

                }
            )
        }

        findViewById<Button>(R.id.btnSmallNative).setOnClickListener {
            val nativeView = findViewById<RelativeLayout>(R.id.nativeView)
            adsManager.showNativeAds(
                this,
                nativeView,
                SizeNative.SMALL,
                primaryAds,
                primaryNativeId,
                secondaryAds,
                secondaryNativeId,
                tertiaryAds,
                tertiaryNativeId,
                object : CallbackAds() {
                    override fun onAdFailedToLoad(error: String?) {
                        Log.e("HALLO", "Native error: $error")
                    }
                })
        }


    }


}