package com.zowad.meldcxscheduler.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.db.toScheduleLog
import com.zowad.meldcxscheduler.source.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {
    private val targetSchedule = MutableLiveData<ScheduleItem>()
    fun onTargetScheduleAcknowledged() = targetSchedule


    fun acknowledgeSchedule(scheduleItem: ScheduleItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepository.deleteScheduleItem(scheduleItem)
                targetSchedule.postValue(scheduleItem)
                val now = System.currentTimeMillis()
                scheduleRepository.saveScheduleLog(scheduleItem.toScheduleLog(now))
            }
        }
    }
}