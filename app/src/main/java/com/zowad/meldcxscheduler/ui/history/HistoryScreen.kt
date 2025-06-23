package com.zowad.meldcxscheduler.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is HistoryUiState.Loaded -> {
                LogList(logList = (uiState as HistoryUiState.Loaded).logs)
            }

            HistoryUiState.Failed -> {}
            HistoryUiState.Loading -> {}
            HistoryUiState.Start -> {}
        }
    }
}