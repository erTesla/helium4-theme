package com.helium4.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Provides a theme to `@Preview` composables.
 *
 * [AppThemeProvider] resolves the theme from parameters the host app supplies (usually
 * from its own persistence layer), which a preview has no way to reach — so previews
 * resolve a theme directly instead. This is what makes the component library inspectable
 * across all 23 themes without an emulator.
 *
 * Like [AppThemeProvider], this installs **both** [LocalAppTheme] and [MaterialTheme].
 * Providing only [LocalAppTheme] would drop [toTypography], which is the single largest
 * difference between the typographic themes — a preview without it renders every theme
 * in the same type scale.
 *
 * It deliberately does *not* paint the backdrop, background pattern, or decorative
 * Dynamic color is never applied in a preview: it needs a live [android.content.Context]
 * wallpaper palette, which the preview renderer does not have.
 */
@Composable
public fun PreviewAppTheme(
    themeId: ThemeId = ThemeId.DEFAULT,
    dark: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = ThemeRegistry.resolve(themeId, dark)
    CompositionLocalProvider(LocalAppTheme provides theme) {
        MaterialTheme(
            colorScheme = theme.toColorScheme(dynamicAllowed = false),
            typography = theme.toTypography(),
            content = content
        )
    }
}
