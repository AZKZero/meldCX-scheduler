package com.zowad.meldcxscheduler.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.ui.components.AppIcon

@Composable
@Preview
fun SchedulePreview() {
    Schedule (
        ScheduleItem(
            id = 0,
            scheduleName = "A",
            schedulePackageName = "",
            scheduleTimeMillis = 0,
            scheduleHour = 2,
            scheduleMinute = 20
        ), onEditClicked = {}, onDeleteClicked = {}
    )
}

@Composable
fun Schedule(scheduleItem: ScheduleItem, onEditClicked: (ScheduleItem) -> Unit, onDeleteClicked: (ScheduleItem) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 5.dp), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        val packageManager = LocalContext.current.packageManager
        val (icon, name) = with(packageManager.getPackageInfo(scheduleItem.schedulePackageName, 0).applicationInfo) {
            if (this == null) Pair(null, null)
            else Pair(loadIcon(packageManager),  loadLabel(packageManager).toString())
        }
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(text = scheduleItem.scheduleName)
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text(
                        text = "%02d:%02d".format(
                            scheduleItem.scheduleHour,
                            scheduleItem.scheduleMinute
                        ), fontSize = TextUnit(16f, TextUnitType.Sp)
                    )
                }
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            AppIcon(iconDrawable = icon)
            Row {
                Button(modifier = Modifier.weight(1f), onClick = { onEditClicked(scheduleItem) }) {
                    Text(text = "Edit")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Red), onClick = { onDeleteClicked(scheduleItem) }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
fun Schedules(
    modifier: Modifier = Modifier,
    scheduleItems: List<ScheduleItem>,
    onEditClicked: (ScheduleItem) -> Unit,
    onDeleteClicked: (ScheduleItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)) {
        items(scheduleItems) {
            Schedule(scheduleItem = it, onEditClicked = onEditClicked, onDeleteClicked = onDeleteClicked)
        }
    }
}