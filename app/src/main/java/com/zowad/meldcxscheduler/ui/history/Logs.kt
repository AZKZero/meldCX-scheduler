package com.zowad.meldcxscheduler.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.zowad.meldcxscheduler.db.ScheduleLog
import com.zowad.meldcxscheduler.ui.components.AppIcon
import java.util.Calendar

@Composable
fun LogItem(modifier: Modifier, scheduleLog: ScheduleLog) {
    val then = Calendar.getInstance().apply { timeInMillis = scheduleLog.scheduleFireTimeMillis }
    Column(modifier = modifier.padding(10.dp)) {
        val packageManager = LocalContext.current.packageManager

        val (icon, name) = with(
            packageManager.getPackageInfo(
                scheduleLog.schedulePackageName,
                0
            ).applicationInfo
        ) {
            if (this == null) Pair(null, null)
            else Pair(loadIcon(packageManager), loadLabel(packageManager).toString())
        }
        Row {
            Text(modifier = Modifier.weight(1f), text = scheduleLog.scheduleName)
            Text(
                text = "%02d:%02d".format(
                    then.get(Calendar.HOUR_OF_DAY),
                    then.get(Calendar.MINUTE)
                )
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppIcon(icon)
            Text(name.orEmpty())
        }
    }
}

@Composable
fun LogList(logList: List<ScheduleLog>) {

    LazyColumn(Modifier.padding(16.dp)) {
        items(
            items = logList,
            key = {
                it.id
            },
        ) {
            LogItem(modifier = Modifier.fillMaxWidth(), scheduleLog = it)
        }
    }
}