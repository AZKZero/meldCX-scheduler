package com.zowad.meldcxscheduler.receivers

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import com.zowad.meldcxscheduler.db.toScheduleLog
import com.zowad.meldcxscheduler.di.KoinStarter
import com.zowad.meldcxscheduler.launcher.LauncherActivity
import com.zowad.meldcxscheduler.source.ScheduleRepository
import com.zowad.meldcxscheduler.utils.KEY_SCHEDULE_ID
import com.zowad.meldcxscheduler.utils.NotificationUtils
import com.zowad.meldcxscheduler.utils.goAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import kotlin.jvm.java

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, data: Intent) {
        KoinStarter.startIfNeeded(context)
        Log.i(AlarmReceiver::class.java.simpleName, "onReceive: ALARM ${data.extras}")
        val scheduleRepository: ScheduleRepository by inject()
        val scheduleId = data.getIntExtra(KEY_SCHEDULE_ID, -1)
        if (scheduleId == -1) return

        val now = Calendar.getInstance().timeInMillis

        goAsync {
            scheduleRepository.getPendingSchedule(scheduleId)?.let { scheduleItem ->
                scheduleRepository.deleteScheduleItem(scheduleItem)
                scheduleRepository.saveScheduleLog(scheduleItem.toScheduleLog(now))
                withContext(Dispatchers.Main.immediate) {
                    Log.i(
                        AlarmReceiver::class.java.simpleName,
                        "LAUNCHING INTENT $scheduleItem"
                    )
                    try {
                        NotificationUtils.prepareAndPostAppLaunchNotification(context, scheduleItem)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}