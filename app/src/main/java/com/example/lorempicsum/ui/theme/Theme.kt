package com.example.lorempicsum.ui.theme

import android.app.Activity
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceContainer = Color(0xFF3A3F3D),
    primaryContainer = Color(0xFF262626),
    onPrimaryContainer = Color(0xFFA9CCBC),
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

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

data class AppDimens(
    val paddingExtraSmall: Dp = 4.dp,
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val paddingExtraLarge: Dp = 32.dp,

    val cornerRadiusSmall: Dp = 4.dp,
    val cornerRadiusMedium: Dp = 10.dp,
    val cornerRadiusLarge: Dp = 16.dp,

    val spacerExtraSmall: Dp = 4.dp,
    val spacerSmall: Dp = 8.dp,
    val spacerMedium: Dp = 16.dp,
    val spacerLarge: Dp = 24.dp,
    val spacerExtraLarge: Dp = 32.dp,

    val iconSmall: Dp = 24.dp,
    val iconMedium: Dp = 32.dp,
    val iconLarge: Dp = 48.dp,

    val imageNotLoadedSize: Dp = 150.dp,
    val imageDefaultMaxSize: Dp = 500.dp,

    val dividerThickness: Dp = 1.dp
)

val LocalAppDimens = staticCompositionLocalOf {
    AppDimens()
}

@Composable
fun LoremPicsumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppDimens provides AppDimens()
    ) {
        content()
    }
    val colorScheme = DarkColorScheme
    //val colorScheme = when {
    //    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
    //        val context = LocalContext.current
    //        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    //    }

    //    darkTheme -> DarkColorScheme

    //    else -> LightColorScheme
    //}


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val MaterialTheme.dimens: AppDimens
    @Composable
    get() = LocalAppDimens.current