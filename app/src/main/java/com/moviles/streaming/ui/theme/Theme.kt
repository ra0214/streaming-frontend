package com.moviles.streaming.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = TwitchPurple,
    onPrimary = TwitchOnBackground,
    primaryContainer = TwitchPurpleDark,
    onPrimaryContainer = TwitchOnBackground,
    secondary = TwitchPurpleLight,
    onSecondary = TwitchBackground,
    background = TwitchBackground,
    onBackground = TwitchOnBackground,
    surface = TwitchSurface,
    onSurface = TwitchOnBackground,
    surfaceVariant = TwitchSurfaceVariant,
    onSurfaceVariant = TwitchGray,
    error = TwitchError,
    onError = TwitchOnBackground,
    outline = TwitchGray,
)

private val LightColorScheme = lightColorScheme(
    primary = TwitchPurple,
    onPrimary = TwitchOnBackground,
    primaryContainer = TwitchPurpleLight,
    secondary = TwitchPurpleDark,
    background = TwitchBackground,
    onBackground = TwitchOnBackground,
    surface = TwitchSurface,
    onSurface = TwitchOnBackground,
)

@Composable
fun StreamingTheme(
    darkTheme: Boolean = true, // Siempre oscuro por defecto
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
