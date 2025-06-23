package com.zowad.meldcxscheduler.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zowad.meldcxscheduler.db.ScheduleItem
import com.zowad.meldcxscheduler.models.ApplicationData
import com.zowad.meldcxscheduler.ui.dialogs.AddDialog
import com.zowad.meldcxscheduler.utils.setAlarm
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ScheduleExactAlarm")
@Composable
fun HomeScreen(
    selectedPackage: ApplicationData? = null,
    onSelectionCleared: () -> Unit,
    onAddNewClicked: () -> Unit,
    onEditClicked: (ScheduleItem) -> Unit,
    onDeleteClicked: (ScheduleItem) -> Unit,
    onHistoryClicked: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val insertedId by viewModel.insertedItem.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(insertedId) {
        insertedId?.let { it ->
            // Schedule the alarm
            context.setAlarm(
                timestamp = it.scheduleTimeMillis,
                scheduleId = it.id
            )
            // Reset insertedId so it doesn't re-trigger
            viewModel.clearInsertedId()
        }
    }

    var showEditingDialog by remember {
        mutableStateOf(false)
    }
    var editingSchedule by remember {
        mutableStateOf<ScheduleItem?>(null)
    }

    Column {
        when (uiState) {
            is HomeUiState.Loaded -> {
                Schedules(
                    Modifier.padding(horizontal = 8.dp).weight(1f),
                    (uiState as HomeUiState.Loaded).pendingSchedules,
                    onAddNewClicked = onAddNewClicked,
                    onEditClicked = onEditClicked,
                    onDeleteClicked = { viewModel.deleteSchedule(it) }
                )

                Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), onClick = {
                    onAddNewClicked()
                }) {
                    Text(text = "Add Another Schedule")
                }

                Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), onClick = {
                    onHistoryClicked()
                }) {
                    Text(text = "View History")
                }

                if (selectedPackage != null) {
                    AddDialog(app = selectedPackage, onConfirmed = {
                        onSelectionCleared()
                        viewModel.saveSchedule(it)
                    }, onClosed = {
                        onSelectionCleared()
                    })
                }
            }

            HomeUiState.Failed -> {}
            HomeUiState.Loading -> {}
            HomeUiState.Start -> {}
        }
    }

}