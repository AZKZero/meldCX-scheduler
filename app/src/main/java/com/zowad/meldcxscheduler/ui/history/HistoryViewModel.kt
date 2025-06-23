package com.zowad.meldcxscheduler.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zowad.meldcxscheduler.db.ScheduleLog
import com.zowad.meldcxscheduler.source.ScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {
    val uiState: StateFlow<HistoryUiState> =
        scheduleRepository
            .getLogs()
            .map<List<ScheduleLog>, HistoryUiState> { HistoryUiState.Loaded(it) }
            .catch { emit(HistoryUiState.Failed) }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HistoryUiState.Loading,
            )
}