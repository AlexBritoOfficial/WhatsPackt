package com.packt.whatspackt.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RedBackground = Color(0xFFFF0000)
val WhiteText = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = RedBackground,
    onPrimary = WhiteText,
    background = RedBackground,
    onBackground = WhiteText
)

private val LightColorScheme = lightColorScheme(
    primary = RedBackground,
    onPrimary = WhiteText,
    background = RedBackground,
    onBackground = WhiteText
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