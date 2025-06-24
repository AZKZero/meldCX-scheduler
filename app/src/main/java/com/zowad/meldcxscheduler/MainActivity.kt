package com.zowad.meldcxscheduler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import com.zowad.meldcxscheduler.navigation.Nav
import com.zowad.meldcxscheduler.ui.permission.EnsureNotificationPermission
import com.zowad.meldcxscheduler.ui.theme.MeldCXSchedulerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeldCXSchedulerTheme {
                EnsureNotificationPermission(
                    onPermissionGranted = {},
                    onPermissionDenied = {}
                ) {
                    Nav()
                }
            }
        }
    }
}