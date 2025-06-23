package com.zowad.meldcxscheduler.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ThrottledButton(
    modifier: Modifier = Modifier,
    throttleDurationMs: Long = 1000,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Button(
        onClick = {
            val now = System.currentTimeMillis()
            if (now - lastClickTime >= throttleDurationMs) {
                lastClickTime = now
                onClick()
            }
        },
        modifier = modifier
    ) {
        content
    }
}