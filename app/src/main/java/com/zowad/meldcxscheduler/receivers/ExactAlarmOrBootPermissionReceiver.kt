package com.zowad.meldcxscheduler.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.getSystemService
import com.zowad.meldcxscheduler.utils.setAlarm
import com.zowad.meldcxscheduler.di.KoinStarter
import com.zowad.meldcxscheduler.source.ScheduleRepository
import com.zowad.meldcxscheduler.utils.goAsync
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import kotlin.getValue

class ExactAlarmOrBootPermissionReceiver : BroadcastReceiver(), KoinComponent {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, p1: Intent) {
        KoinStarter.startIfNeeded(context)
        val scheduleRepository: ScheduleRepository by inject()

       val alarmManager: AlarmManager = context.getSystemService() ?: return
        val shouldRescheduleAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            false
        }
        if (shouldRescheduleAlarms) {
            val now = Calendar.getInstance().timeInMillis
            goAsync {
                scheduleRepository.getPendingSchedulesSynchronous(now).forEach {
                    context.setAlarm(
                        it.scheduleTimeMillis,
                        scheduleId = it.id
                    )
                }
            }
        }
    }
}