package com.zowad.meldcxscheduler.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var title by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                if (backStack.size > 1) {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            }
        )
    }) { innerPadding ->
        NavDisplay(
            modifier = modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is HomeRoute -> NavEntry(key) {
                        title = "Home"
                        HomeScreen(
                            selectedPackage = key.selectedApplicationData,
                            onSelectionCleared = {
                                backStack[backStack.lastIndex] =
                                    HomeRoute(selectedApplicationData = null)
                            },
                            onAddNewClicked = {
                                if (context.shouldAskForAlarmPermission()) {
                                    context.askForAlarmPermission()
                                    (context as? Activity)?.finish()
                                } else backStack.add(AppListRoute)
                            },
                            onEditClicked = {},
                            onDeleteClicked = {},
                            onHistoryClicked = { backStack.add(HistoryRoute) }
                        )
                    }

                    is HistoryRoute -> NavEntry(key) {
                        title = "History"
                        HistoryScreen()
                    }

                    is AppListRoute -> NavEntry(key) {
                        title = "App List"
                        AppListScreen {
                            backStack[backStack.lastIndex] = HomeRoute(selectedApplicationData = it)
                        }
                    }

                    else -> {
                        error("Unknown route: $key")
                    }
                }
            }
        )
    }
}
