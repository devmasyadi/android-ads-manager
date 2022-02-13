package com.bismillahcoba.testtapdag

import android.app.Application
import com.ahmadsuyadi.adsmanager.module.di.adsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(
                adsModule
            ))
        }
    }
}