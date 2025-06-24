package com.zowad.meldcxscheduler.utils

import android.content.BroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

const val ACTION_RECEIVE_ALARM_BROADCAST = "ACTION_RECEIVE_ALARM_BROADCAST"
const val KEY_SCHEDULE_ID = "KEY_SCHEDULE_ID"

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit,
) {
    val pendingResult = goAsync()
    CoroutineScope(Dispatchers.IO).launch(context) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            pendingResult.finish()
        }
    }
}
