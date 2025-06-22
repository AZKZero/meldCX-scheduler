package com.zowad.meldcxscheduler

import androidx.room.Room
import com.zowad.meldcxscheduler.db.AppDatabase
import com.zowad.meldcxscheduler.db.ScheduleDao
import com.zowad.meldcxscheduler.source.ScheduleRepository
import com.zowad.meldcxscheduler.source.ScheduleRepositoryImpl
import com.zowad.meldcxscheduler.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "database"
        ).build()
    }

    single<ScheduleDao> {
        val database = get<AppDatabase>()
        database.scheduleItemDao()
    }

    singleOf(::ScheduleRepositoryImpl) { bind<ScheduleRepository>() }

    viewModelOf(::HomeViewModel)
}