package com.zowad.meldcxscheduler.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = White,
    onBackground = Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = Black,
    onBackground = White,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MeldCXSchedulerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography/*.copy(
            displayLarge = Typography.displayLarge.copy(color = colorScheme.onSurface),
            displayMedium = Typography.displayMedium.copy(color = colorScheme.onSurface),
            displaySmall = Typography.displaySmall.copy(color = colorScheme.onSurface),
            headlineLarge = Typography.headlineLarge.copy(color = colorScheme.onSurface),
            headlineMedium = Typography.headlineMedium.copy(color = colorScheme.onSurface),
            headlineSmall = Typography.headlineSmall.copy(color = colorScheme.onSurface),
            titleLarge = Typography.titleLarge.copy(color = colorScheme.onSurface),
            titleMedium = Typography.titleMedium.copy(color = colorScheme.onSurface),
            titleSmall = Typography.titleSmall.copy(color = colorScheme.onSurface),
            bodyLarge = Typography.bodyLarge.copy(color = colorScheme.onSurface),
            bodyMedium = Typography.bodyMedium.copy(color = colorScheme.onSurface),
            bodySmall = Typography.bodySmall.copy(color = colorScheme.onSurface),
            labelLarge = Typography.labelLarge.copy(color = colorScheme.onSurface),
            labelMedium = Typography.labelMedium.copy(color = colorScheme.onSurface),
            labelSmall = Typography.labelSmall.copy(color = colorScheme.onSurface)
        )*/,
        content = content
    )
}