package com.example.passfort.designSystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = active_state_dark,
    secondary = inactive_state_dark,
    surface = border_stroke_dark,
    background = background_dark,
    outline = outline_dark,
    inversePrimary = inverse,
    primaryContainer = red,
    secondaryContainer = yellow,
)

private val LightColorScheme = lightColorScheme(
    primary = active_state_light,
    secondary = inactive_state_light,
    surface = border_stroke_light,
    background = background_light,
    outline = outline_light,
    inversePrimary = inverse,
    primaryContainer = red,
    secondaryContainer = yellow,

    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

enum class ChosenTheme {
    AUTO,
    LIGHT,
    DARK,
}

@Composable
fun PassFortTheme(
    darkTheme: ChosenTheme = ChosenTheme.AUTO,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }*/

    val chosenColors = when (darkTheme) {
        ChosenTheme.DARK -> DarkColorScheme
        ChosenTheme.LIGHT -> LightColorScheme
        ChosenTheme.AUTO -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = chosenColors,
        typography = Typography,
        content = content
    )
}