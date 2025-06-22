package com.zowad.meldcxscheduler.source

import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.db.ScheduleLog
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getPendingSchedules(): Flow<List<ScheduleItem>>
    fun getLogs(): Flow<List<ScheduleLog>>
    suspend fun saveScheduleItem(scheduleItem: ScheduleItem)
    suspend fun deleteScheduleItem(scheduleItem: ScheduleItem)
    suspend fun saveScheduleLog(scheduleLog: ScheduleLog)
}