package com.zowad.meldcxscheduler

import android.app.Application
import com.zowad.meldcxscheduler.di.KoinStarter
import com.zowad.meldcxscheduler.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoreApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinStarter.startIfNeeded(this)
    }
}