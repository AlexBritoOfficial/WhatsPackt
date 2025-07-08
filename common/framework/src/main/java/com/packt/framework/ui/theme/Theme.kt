package com.packt.framework.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BlueButton,
    onPrimary = WhiteText,
    background = WhiteText,
    onBackground = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = BlueButton,
    onPrimary = WhiteText,
    background = WhiteText,
    onBackground = Color.Black
)

@Composable
fun WhatsPacktTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}