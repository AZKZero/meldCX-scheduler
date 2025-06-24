package com.zowad.meldcxscheduler.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zowad.meldcxscheduler.db.ScheduleLog
import com.zowad.meldcxscheduler.ui.components.AppIcon
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun LogItem(modifier: Modifier, scheduleLog: ScheduleLog) {
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

        val fireDate = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.current.platformLocale)
            .format(Date(scheduleLog.scheduleFireTimeMillis))
        val targetDate = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.current.platformLocale)
        .format(Date(scheduleLog.scheduleTimeMillis))
        Row {
            Text(modifier = Modifier.weight(1f), text = scheduleLog.scheduleName)
        }
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppIcon(icon)
            Spacer(Modifier.width(12.dp))
            Text(modifier = Modifier.weight(1f), text = name.orEmpty())
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    textAlign = TextAlign.End,
                    text = "Scheduled $targetDate"
                )
                Text(
                    textAlign = TextAlign.End,
                    text = "Launched $fireDate"
                )
            }
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