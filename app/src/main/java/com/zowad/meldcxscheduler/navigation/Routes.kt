package com.zowad.meldcxscheduler.navigation

import androidx.navigation3.runtime.NavKey
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.models.ApplicationData
import kotlinx.serialization.Serializable

@Serializable
data class HomeRoute(
    val selectedApplicationData: ApplicationData? = null,
    val selectedScheduleItem: ScheduleItem? = null,
) : NavKey

@Serializable data object AppListRoute : NavKey
@Serializable data object HistoryRoute : NavKey