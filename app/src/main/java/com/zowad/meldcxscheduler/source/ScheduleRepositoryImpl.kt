package com.zowad.meldcxscheduler.source

import com.zowad.meldcxscheduler.db.ScheduleDao
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.db.ScheduleLog
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(private val scheduleDao: ScheduleDao) : ScheduleRepository{
    override fun getPendingSchedules(): Flow<List<ScheduleItem>> {
        return scheduleDao.getAll()
    }

    override fun getLogs(): Flow<List<ScheduleLog>> {
        return scheduleDao.getLogs()
    }

    override suspend fun saveScheduleItem(scheduleItem: ScheduleItem) {
        scheduleDao.saveSchedule(scheduleItem)
    }

    override suspend fun deleteScheduleItem(scheduleItem: ScheduleItem) {
        scheduleDao.deleteSchedule(scheduleItem)
    }

    override suspend fun saveScheduleLog(scheduleLog: ScheduleLog) {
        scheduleDao.saveScheduleLog(scheduleLog)
    }

}