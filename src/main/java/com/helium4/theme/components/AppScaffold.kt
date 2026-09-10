package com.helium4.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.helium4.theme.BackgroundPatternLayer
import com.helium4.theme.LocalAppTheme

/**
 * A screen container: the theme's background pattern under a transparent Material 3
 * [Scaffold].
 *
 * **It deliberately does not paint [com.helium4.theme.AppTheme.colorBackground].**
 * `AppThemeProvider` already painted the backdrop, and on a gradient theme repainting the
 * flat background color flattens the gradient back out. Set [paintBackground] to `true`
 * only when hosting this outside an `AppThemeProvider`.
 *
 * **Edge-to-edge:** with `enableEdgeToEdge()`, [Scaffold] reports the system-bar insets
 * through the [PaddingValues] it hands to [content] — but only content that actually
 * consumes them is safe. If you pass a [topBar], it is drawn under the status bar unless
 * the bar itself applies `Modifier.windowInsetsPadding(WindowInsets.statusBars)`;
 * [AppTopAppBar] does not do this for you, because a bar that should bleed under the
 * status bar is an equally valid design. Either wrap it, or override
 * [contentWindowInsets].
 */
@Composable
public fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    paintBackground: Boolean = false,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val theme = LocalAppTheme.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .then(if (paintBackground) Modifier.background(theme.colorBackground) else Modifier)
    ) {
        BackgroundPatternLayer(
            pattern = theme.backgroundPattern,
            theme = theme,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            containerColor = Color.Transparent,
            contentColor = theme.colorOnBackground,
            contentWindowInsets = contentWindowInsets,
            content = content
        )
    }
}
