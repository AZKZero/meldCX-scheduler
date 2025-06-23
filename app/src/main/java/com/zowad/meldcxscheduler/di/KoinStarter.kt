package com.zowad.meldcxscheduler.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

object KoinStarter {

    @Volatile
    private var started = false

    fun startIfNeeded(context: Context) {
        if (!started) {
            synchronized(this) {
                if (!started && GlobalContext.getOrNull() == null) {
                    startKoin {
                        androidContext(context.applicationContext)
                        modules(appModule)
                    }
                    started = true
                }
            }
        }
    }
}