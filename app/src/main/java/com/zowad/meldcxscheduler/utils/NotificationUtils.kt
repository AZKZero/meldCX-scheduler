package com.zowad.meldcxscheduler.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.zowad.meldcxscheduler.R
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.launcher.LauncherActivity

object NotificationUtils {

    const val SCHEDULE_LAUNCH_CHANNEL_ID = "schedule_launch_notifications"
    const val SCHEDULE_LAUNCH_CHANNEL_NAME = "MeldCX Scheduler"
    const val SCHEDULE_LAUNCH_CHANNEL_DESC =
        "Notifications for when a scheduled app is ready to launch."

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val launchChannel = NotificationChannel(
                SCHEDULE_LAUNCH_CHANNEL_ID,
                SCHEDULE_LAUNCH_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = SCHEDULE_LAUNCH_CHANNEL_DESC
                enableLights(true)
                lightColor = android.graphics.Color.CYAN
                enableVibration(true)
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(launchChannel)

        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun prepareAndPostAppLaunchNotification(context: Context, scheduleItem: ScheduleItem) {
        val packageName = scheduleItem.schedulePackageName
        val scheduleItemId = scheduleItem.id

        if (packageName.isEmpty()) {
            return
        }

        val launchIntent = Intent(context, LauncherActivity::class.java).putExtra("item", scheduleItem)

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntentFlags =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            scheduleItemId,
            launchIntent,
            pendingIntentFlags
        )


        val notificationTitle = "Scheduled Application: ${scheduleItem.scheduleName}"
        val notificationContent = "Tap to launch '$packageName'."

        val builder =
            NotificationCompat.Builder(context, SCHEDULE_LAUNCH_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(activityPendingIntent)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        try {
            NotificationManagerCompat.from(context).notify(scheduleItemId, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}