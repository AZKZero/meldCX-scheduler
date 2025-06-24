package com.zowad.meldcxscheduler.ui.applist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zowad.meldcxscheduler.models.ApplicationData
import com.zowad.meldcxscheduler.ui.components.AppIcon


@Composable
fun AppList(appList: List<ApplicationData>, onClick: (ApplicationData) -> Unit) {

    LazyColumn(Modifier.padding(16.dp)) {
        items(
            items = appList,
            key = {
                it.packageName
            },
        ) {
            AppItem(app = it, onClick = { onClick(it) })
        }
    }
}

@Composable
fun AppItem(app: ApplicationData, onClick: (ApplicationData) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(app)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        AppIcon(app.icon)
        Spacer(Modifier.width(12.dp))
        Text(text = app.name)
    }
}
