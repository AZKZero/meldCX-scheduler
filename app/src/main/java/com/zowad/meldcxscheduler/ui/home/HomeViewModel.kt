package com.zowad.meldcxscheduler.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.source.ScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        scheduleRepository
            .getPendingSchedules()
            .map<List<ScheduleItem>, HomeUiState> { HomeUiState.Loaded(it) }
            .catch { emit(HomeUiState.Failed) }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading,
            )
}