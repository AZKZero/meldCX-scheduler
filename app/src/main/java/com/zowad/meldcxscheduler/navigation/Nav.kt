package com.zowad.meldcxscheduler.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zowad.meldcxscheduler.ui.applist.AppListScreen
import com.zowad.meldcxscheduler.ui.history.HistoryScreen
import com.zowad.meldcxscheduler.ui.home.HomeScreen
import com.zowad.meldcxscheduler.utils.askForAlarmPermission
import com.zowad.meldcxscheduler.utils.shouldAskForAlarmPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nav(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(HomeRoute())
    val context = LocalContext.current
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            Log.i("NAV", "Nav: $key ")
            when (key) {
                is HomeRoute -> NavEntry(key) {
                    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
                        TopAppBar(
                            title = { Text("Home") }
                        )
                    }) { innerPadding ->
                        HomeScreen(
                            modifier = modifier.padding(innerPadding),
                            selectedPackage = key.selectedApplicationData,
                            selectedScheduleItem = key.selectedScheduleItem,
                            onSelectionCleared = {
                                val lastItem = backStack.last()
                                if(lastItem is HomeRoute) {
                                    backStack[backStack.lastIndex] = lastItem.copy(selectedApplicationData = null)
                                } else {
                                    backStack[backStack.lastIndex] = HomeRoute(selectedApplicationData = null)
                                }
                            },
                            onSelectedScheduleCleared = {
                                val lastItem = backStack.last()
                                if(lastItem is HomeRoute) {
                                    backStack[backStack.lastIndex] = lastItem.copy(selectedScheduleItem = null)
                                } else {
                                    backStack[backStack.lastIndex] = HomeRoute(selectedScheduleItem = null)

                                }
                            },
                            onAddNewClicked = {
                                if (context.shouldAskForAlarmPermission()) {
                                    context.askForAlarmPermission()
                                    (context as? Activity)?.finish()
                                } else backStack.add(AppListRoute)
                            },
                            onEditClicked = {
                                Log.i("NAV", "Nav: $it $backStack")
                                val lastItem = backStack.last()
                                if(lastItem is HomeRoute) {
                                    backStack[backStack.lastIndex] = lastItem.copy(selectedScheduleItem = it)
                                } else {
                                    backStack[backStack.lastIndex] = HomeRoute(selectedScheduleItem = it)
                                }
                            },
                            onHistoryClicked = { backStack.add(HistoryRoute) }
                        )
                    }

                }

                is HistoryRoute -> NavEntry(key) {
                    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
                        TopAppBar(
                            title = { Text("History") },
                            navigationIcon = {
                                if (backStack.size > 1) {
                                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "back"
                                        )
                                    }
                                }
                            }
                        )
                    }) { innerPadding ->
                        HistoryScreen(
                            modifier = modifier.padding(innerPadding),
                        )
                    }
                }

                is AppListRoute -> NavEntry(key) {
                    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
                        TopAppBar(
                            title = { Text("App List") },
                            navigationIcon = {
                                if (backStack.size > 1) {
                                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "back"
                                        )
                                    }
                                }
                            }
                        )
                    }) { innerPadding ->
                        AppListScreen(
                            modifier = modifier.padding(innerPadding)
                        ) {
                            backStack.removeLastOrNull()
                            backStack[backStack.lastIndex] = HomeRoute(selectedApplicationData = it)
                        }
                    }
                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}
