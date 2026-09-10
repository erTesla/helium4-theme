package com.helium4.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * The active theme.
 *
 * `staticCompositionLocalOf` rather than `compositionLocalOf`: theme changes are rare and
 * should retrigger the whole subtree, which is exactly what we want when re-theming live.
 */
public val LocalAppTheme: ProvidableCompositionLocal<AppTheme> = staticCompositionLocalOf {
    error("LocalAppTheme not provided — wrap content in AppThemeProvider (or PreviewAppTheme in a @Preview)")
}
