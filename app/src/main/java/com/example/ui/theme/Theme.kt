package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = QuranGoldPrimary,
    onPrimary = Color(0xFF1E1700),
    primaryContainer = QuranGoldContainer,
    onPrimaryContainer = QuranGoldLight,
    secondary = QuranGreenLight,
    onSecondary = Color(0xFF003824),
    secondaryContainer = Color(0xFF0E382A),
    onSecondaryContainer = Color(0xFFA5F0D2),
    tertiary = Color(0xFF81D4FA),
    onTertiary = Color(0xFF003549),
    background = QuranEmeraldDark,
    onBackground = QuranTextPrimary,
    surface = QuranEmeraldSurface,
    onSurface = QuranTextPrimary,
    surfaceVariant = QuranEmeraldCard,
    onSurfaceVariant = QuranTextSecondary,
    outline = QuranEmeraldBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = QuranTypography,
        content = content
    )
}
