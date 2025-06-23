package com.zowad.meldcxscheduler.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.zowad.meldcxscheduler.R

@Composable
fun AppIcon(iconDrawable: Drawable? = null) {
    Image(
        painter = rememberAsyncImagePainter(
            iconDrawable,
            fallback = painterResource(id = R.drawable.ic_launcher_foreground)
        ),
        contentDescription = "App Icon",
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
    )
}