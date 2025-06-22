package com.zowad.meldcxscheduler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

@Composable
fun Nav() {
    val backStack = remember { mutableStateListOf<Any>(HomeRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is HomeRoute -> NavEntry(key) {

                }

                is HistoryRoute -> NavEntry(key) {

                }

                is AppListRoute -> NavEntry(key) {

                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}
