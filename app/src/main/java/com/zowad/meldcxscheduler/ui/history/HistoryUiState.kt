package com.zowad.meldcxscheduler.ui.history

import com.zowad.meldcxscheduler.db.ScheduleLog

sealed interface HistoryUiState {
    data object Loading : HistoryUiState
    data object Failed : HistoryUiState
    data object Start : HistoryUiState

    data class Loaded(
        val logs: List<ScheduleLog>,
    ) : HistoryUiState
}