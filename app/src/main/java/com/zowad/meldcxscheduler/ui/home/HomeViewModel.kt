package com.zowad.meldcxscheduler.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.source.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _insertedItem = MutableStateFlow<ScheduleItem?>(null)
    val insertedItem: StateFlow<ScheduleItem?> = _insertedItem

    fun saveSchedule(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val item = scheduleRepository.saveScheduleItem(scheduleItem)
                _insertedItem.value = item
            }
        }
    }

    fun deleteSchedule(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { scheduleRepository.deleteScheduleItem(scheduleItem) }
        }
    }

    fun clearInsertedId() {
        _insertedItem.value = null
    }
}