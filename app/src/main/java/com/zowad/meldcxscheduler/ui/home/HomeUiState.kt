package com.zowad.meldcxscheduler.ui.home

import com.zowad.meldcxscheduler.db.ScheduleItem

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data object Failed: HomeUiState
    data object Start: HomeUiState

    data class Loaded(
        val pendingSchedules: List<ScheduleItem>
    ):HomeUiState
}