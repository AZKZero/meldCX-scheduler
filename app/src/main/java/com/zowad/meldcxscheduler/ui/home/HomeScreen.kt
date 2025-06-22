package com.zowad.meldcxscheduler.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zowad.meldcxscheduler.db.ScheduleItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showAddDialog by remember {
        mutableStateOf(true)
    }
    var showEditingDialog by remember {
        mutableStateOf(false)
    }
    var editingSchedule by remember {
        mutableStateOf<ScheduleItem?>(null)
    }

    when (uiState) {
        is HomeUiState.Loaded -> {
            Schedules(
                (uiState as HomeUiState.Loaded).pendingSchedules,
                onAddNewClicked = {},
                onEditClicked = {  },
                onDeleteClicked = {  }
            )
        }

        HomeUiState.Failed -> {}
        HomeUiState.Loading -> {}
        HomeUiState.Start -> {}
    }
}