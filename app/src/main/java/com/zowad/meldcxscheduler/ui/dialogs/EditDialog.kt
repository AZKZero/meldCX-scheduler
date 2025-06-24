@file:OptIn(ExperimentalMaterial3Api::class)

package com.zowad.meldcxscheduler.ui.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.ui.components.AppIcon
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(
    scheduleItem: ScheduleItem,
    onConfirmed: (ScheduleItem) -> Unit,
    onClosed: () -> Unit,
) {
    Dialog(onDismissRequest = { onClosed() }) {
        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.padding(
                    16.dp
                )
            ) {
                val packageManager = LocalContext.current.packageManager
                val (icon, appName) = with(
                    packageManager.getPackageInfo(
                        scheduleItem.schedulePackageName, 0
                    ).applicationInfo
                ) {
                    if (this == null) Pair(null, null)
                    else Pair(loadIcon(packageManager), loadLabel(packageManager).toString())
                }
                Column {
                    val timePickerState = rememberTimePickerState(
                        initialHour = scheduleItem.scheduleHour,
                        initialMinute = scheduleItem.scheduleMinute,
                        is24Hour = false
                    )

                    Text(scheduleItem.scheduleName)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    TimePicker(state = timePickerState, layoutType = TimePickerLayoutType.Vertical)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    Button(modifier = Modifier.fillMaxWidth(), onClick = {
                        val timestamp = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }.timeInMillis

                        val editedScheduleItem = scheduleItem.copy(
                            scheduleTimeMillis = timestamp,
                            scheduleHour = timePickerState.hour,
                            scheduleMinute = timePickerState.minute
                        )
                        Log.i(
                            "EditDialog",
                            "AddDialog: $editedScheduleItem ${timePickerState.hour} ${timePickerState.minute}"
                        )
                        onConfirmed(editedScheduleItem)
                    }) {
                        Text(text = "Confirm")
                    }
                }
                AppIcon(iconDrawable = icon)
            }

        }
    }
}