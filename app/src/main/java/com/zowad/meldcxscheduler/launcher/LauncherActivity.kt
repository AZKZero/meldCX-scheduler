package com.zowad.meldcxscheduler.launcher

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.zowad.meldcxscheduler.db.ScheduleItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : ComponentActivity() {
    private val viewModel: LauncherViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        val scheduleItem = intent.getParcelableExtra("item") as? ScheduleItem
        if (scheduleItem != null) {
            Toast.makeText(this, "Processing Schedule", Toast.LENGTH_SHORT).show()
            viewModel.acknowledgeSchedule(scheduleItem)
        } else {
            Toast.makeText(this, "Schedule Error", Toast.LENGTH_SHORT).show()
            finishAndRemoveTask()
        }

        viewModel.onTargetScheduleAcknowledged().observe(this) {
            Toast.makeText(this, "Schedule Processed", Toast.LENGTH_SHORT).show()
            startActivity(
                packageManager.getLaunchIntentForPackage(it.schedulePackageName)
                    ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finishAndRemoveTask()
        }
    }
}