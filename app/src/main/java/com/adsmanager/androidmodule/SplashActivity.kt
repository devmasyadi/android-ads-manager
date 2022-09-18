package com.adsmanager.androidmodule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.adsmanager.ads.AdsManagerOpenAd
import com.adsmanager.ads.NetworkOpenAd
import com.adsmanager.core.CallbackAds
import com.adsmanager.core.CallbackOpenAd
import org.koin.android.ext.android.inject

/**
 * Number of seconds to count down before showing the app open ad. This simulates the time needed
 * to load the app.
 */
private const val COUNTER_TIME = 2L

private const val LOG_TAG = "SplashActivity"

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val adsManagerOpenAd: AdsManagerOpenAd by inject()
    private var secondsRemaining: Long = 0L
    private val adUnitOpenAdId = "ca-app-pub-3940256099942544/3419835294"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ConfigAds.primaryOpenAdId = adUnitOpenAdId
        adsManagerOpenAd.loadAd(
            this,
            NetworkOpenAd.ADMOB,
            adUnitOpenAdId,
            null,
            null,
            null,
            null,
            object :
                CallbackAds() {
                override fun onAdFailedToLoad(error: String?) {
                    startMainActivity()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    createTimer(COUNTER_TIME)
                }
            })
    }

    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     *
     * @param seconds the number of seconds that the timer counts down from
     */
    private fun createTimer(seconds: Long) {

        val countDownTimer: CountDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000 + 1
            }

            override fun onFinish() {
                secondsRemaining = 0
                adsManagerOpenAd.showAdIfAvailable(
                    this@SplashActivity,
                    NetworkOpenAd.ADMOB,
                    adUnitOpenAdId,
                    null,
                    null,
                    null,
                    null,
                    object : CallbackOpenAd() {

                        override fun onAdFailedToLoad(error: String?) {
                            Log.e(LOG_TAG, "$error")
                            startMainActivity()
                        }

                        override fun onShowAdComplete() {
                            Log.e(LOG_TAG, "onShowAdComplete")
                            startMainActivity()
                        }

                    })

            }
        }
        countDownTimer.start()
    }

    /** Start the MainActivity. */
    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}