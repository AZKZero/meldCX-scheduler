package com.zowad.meldcxscheduler.navigation

import androidx.navigation3.runtime.NavKey
import com.zowad.meldcxscheduler.models.ApplicationData
import kotlinx.serialization.Serializable

@Serializable data class HomeRoute(val selectedApplicationData: ApplicationData? = null) : NavKey
data object AppListRoute : NavKey
data object HistoryRoute : NavKey