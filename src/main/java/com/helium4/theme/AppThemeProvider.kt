package com.helium4.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

/**
 * Root composable of the theme system.
 *
 * Takes the desired [ThemeId] and [DarkMode] as plain parameters, so the host app is free
 * to source them from DataStore, Room, a ViewModel, or a hardcoded constant — this module
 * has no opinion and no dependency on any DI or persistence library.
 *
 * It resolves the theme, publishes it via [LocalAppTheme], mirrors it into [MaterialTheme]
 * so raw M3 components still look right, syncs the system bars, and sandwiches the content
 * between the background pattern and the decorative overlay.
 *
 * ### Constraints
 *
 * This is a **window-root** composable, not a general-purpose subtree wrapper:
 *
 * - It fills the available space ([Modifier.fillMaxSize]) and paints the page backdrop,
 *   so nesting it inside a laid-out subtree will stretch and repaint that subtree.
 * - [ApplySystemBars] needs the composition's context to be an [Activity]. In a dialog
 *   window, a `ComposeView` inside a `Service`, or any non-activity context it silently
 *   does nothing; everything else still themes correctly.
 *
 * To theme a subtree, a dialog, or a preview surface, use [PreviewAppTheme] instead — it
 * installs the same [LocalAppTheme] and [MaterialTheme] without the layout or window
 * side effects.
 */
@Composable
public fun AppThemeProvider(
    themeId: ThemeId = ThemeId.DEFAULT,
    darkMode: DarkMode = DarkMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val dark = when (darkMode) {
        DarkMode.SYSTEM -> systemDark
        DarkMode.LIGHT -> false
        DarkMode.DARK -> true
    }

    val theme = ThemeRegistry.resolve(themeId, dark).animated()

    ApplySystemBars(theme)

    CompositionLocalProvider(LocalAppTheme provides theme) {
        MaterialTheme(
            colorScheme = theme.toColorScheme(dynamicAllowed = theme.id == ThemeId.MATERIAL),
            typography = theme.toTypography()
        ) {
            Box(Modifier.fillMaxSize().then(theme.backdrop())) {
                GlassBloomLayer(theme)
                BackgroundPatternLayer(theme.backgroundPattern, theme)
                content()
                DecorativeOverlayLayer(theme.decorativeOverlay, theme.animationStyle, theme)
            }
        }
    }
}

/**
 * The page backdrop: a drifting gradient when the theme defines one, a flat fill
 * otherwise.
 *
 * Only a DRAMATIC theme actually animates. One very long transition drives the drift, and
 * every other theme resolves to a constant, so no animation is left running for the 21
 * themes that do not want one.
 */
@Composable
private fun AppTheme.backdrop(): Modifier {
    val gradient = backgroundGradient ?: return Modifier.background(colorBackground)

    val drifts = animationStyle == AnimationStyle.DRAMATIC
    val shift by if (drifts) {
        rememberInfiniteTransition(label = "backdropGradient").animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = GRADIENT_DRIFT_MILLIS, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "backdropShift"
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    return Modifier.background(gradientBrush(gradient, shift))
}

/** Slides the gradient's endpoints so the colours drift without the fill leaving the screen. */
private fun gradientBrush(colors: List<Color>, shift: Float): Brush {
    if (shift == 0f) return Brush.linearGradient(colors)
    val d = (shift - 0.5f) * 600f
    return Brush.linearGradient(
        colors = colors,
        start = Offset(d, 0f),
        end = Offset(1000f + d, 1400f)
    )
}

/**
 * Large, heavily overlapping accent circles, blurred to a haze.
 *
 * Frosted glass needs something out-of-focus *behind* it. This is the honest way to get
 * that: blur a backdrop layer, never the surface itself — blurring the surface blurs the
 * text sitting on it. Below API 31 `Modifier.blur` is a no-op and the plain translucency
 * is the fallback, which is why the blooms stay subtle enough to survive unblurred.
 *
 * Deliberately not animated: blurring a full-screen layer every frame is real GPU work,
 * and the gradient underneath already carries the motion.
 */
@Composable
private fun GlassBloomLayer(theme: AppTheme) {
    if (!theme.isTranslucentSurface) return
    Box(
        Modifier
            .fillMaxSize()
            .clearAndSetSemantics { }
            .blur(GLASS_BLOOM_BLUR)
            .drawBehind { drawGlassBlooms(theme) }
    )
}

private fun DrawScope.drawGlassBlooms(theme: AppTheme) {
    val unit = maxOf(size.width, size.height)
    listOf(
        Triple(0.20f, 0.18f, theme.colorAccent),
        Triple(0.85f, 0.35f, theme.colorPrimary),
        Triple(0.45f, 0.85f, theme.colorSecondary)
    ).forEachIndexed { i, (x, y, color) ->
        drawCircle(
            color = color.copy(alpha = 0.45f),
            radius = unit * (0.30f + 0.06f * i),
            center = Offset(x * size.width, y * size.height)
        )
    }
}

private const val GRADIENT_DRIFT_MILLIS = 22_000
private val GLASS_BLOOM_BLUR = 56.dp

/**
 * Cross-fades the color tokens so switching themes reads as a transition rather than a
 * snap. Skipped entirely when the theme opts out of animation (Brutalism), where an
 * instant switch is the point.
 */
@Composable
private fun AppTheme.animated(): AppTheme {
    if (animationStyle == AnimationStyle.NONE) return this

    return copy(
        colorPrimary = anim(colorPrimary, "primary"),
        colorBackground = anim(colorBackground, "background"),
        colorSurface = anim(colorSurface, "surface"),
        colorSurfaceVariant = anim(colorSurfaceVariant, "surfaceVariant"),
        colorOnBackground = anim(colorOnBackground, "onBackground"),
        colorOnSurface = anim(colorOnSurface, "onSurface"),
        colorBorder = anim(colorBorder, "border"),
        textPrimary = anim(textPrimary, "textPrimary"),
        textSecondary = anim(textSecondary, "textSecondary"),
        cardBackgroundColor = anim(cardBackgroundColor, "cardBackground"),
        cardBorderColor = anim(cardBorderColor, "cardBorder"),
        topAppBarColor = anim(topAppBarColor, "topAppBar"),
        topAppBarContentColor = anim(topAppBarContentColor, "topAppBarContent")
    )
}

private const val THEME_CROSSFADE_MILLIS = 320

@Composable
private fun anim(color: Color, label: String): Color =
    animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = THEME_CROSSFADE_MILLIS),
        label = label
    ).value

/**
 * Sets the system-bar **icon brightness**, so bar icons stay legible on both light and
 * dark themes. This is the load-bearing half and it works on every API level.
 *
 * The bar *colors* are a best-effort legacy fallback. `Window.statusBarColor` and
 * `Window.navigationBarColor` are deprecated and are **no-ops from API 35 onward**, where
 * the platform enforces edge-to-edge and the bars are always transparent. On API 35+ the
 * bars therefore show whatever the theme's backdrop paints behind them, which is why
 * `statusBarColor`/`navigationBarColor` in a theme definition should be kept consistent
 * with `colorBackground` rather than treated as an independent knob.
 *
 * Does nothing when the composition's context is not an [Activity] (dialog windows,
 * `ComposeView` in a service, layout previews).
 */
@Composable
private fun ApplySystemBars(theme: AppTheme) {
    val view = LocalView.current
    if (view.isInEditMode) return

    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        if (Build.VERSION.SDK_INT < 35) {
            @Suppress("DEPRECATION")
            window.statusBarColor = theme.statusBarColor.toArgb()
            @Suppress("DEPRECATION")
            window.navigationBarColor = theme.navigationBarColor.toArgb()
        }
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = !theme.isDark
            isAppearanceLightNavigationBars = !theme.isDark
        }
    }
}

/**
 * Mirrors the tokens into an M3 [androidx.compose.material3.ColorScheme] so any component
 * not yet swapped for an `App*` wrapper still picks up the theme.
 */
@Composable
internal fun AppTheme.toColorScheme(dynamicAllowed: Boolean) = when {
    dynamicAllowed && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    isDark -> darkColorScheme(
        primary = colorPrimary,
        onPrimary = colorOnPrimary,
        secondary = colorSecondary,
        background = colorBackground,
        onBackground = colorOnBackground,
        surface = colorSurface,
        onSurface = colorOnSurface,
        surfaceVariant = colorSurfaceVariant,
        outline = colorOutline,
        error = colorError
    )

    else -> lightColorScheme(
        primary = colorPrimary,
        onPrimary = colorOnPrimary,
        secondary = colorSecondary,
        background = colorBackground,
        onBackground = colorOnBackground,
        surface = colorSurface,
        onSurface = colorOnSurface,
        surfaceVariant = colorSurfaceVariant,
        outline = colorOutline,
        error = colorError
    )
}

/** M3 typography built from the theme's font tokens, replacing the generated static one. */
internal fun AppTheme.toTypography(): Typography {
    val base = Typography()
    return base.copy(
        headlineLarge = base.headlineLarge.copy(
            fontFamily = headingFontFamily,
            fontWeight = headingFontWeight,
            letterSpacing = headingLetterSpacing
        ),
        headlineMedium = base.headlineMedium.copy(
            fontFamily = headingFontFamily,
            fontSize = headingFontSize,
            fontWeight = headingFontWeight,
            letterSpacing = headingLetterSpacing
        ),
        titleLarge = base.titleLarge.copy(
            fontFamily = headingFontFamily,
            fontWeight = headingFontWeight
        ),
        titleMedium = base.titleMedium.copy(fontFamily = headingFontFamily),
        bodyLarge = base.bodyLarge.copy(fontFamily = bodyFontFamily, fontSize = bodyFontSize),
        bodyMedium = base.bodyMedium.copy(fontFamily = bodyFontFamily),
        labelLarge = base.labelLarge.copy(fontFamily = labelFontFamily, fontSize = labelFontSize),
        labelMedium = base.labelMedium.copy(fontFamily = labelFontFamily),
        labelSmall = base.labelSmall.copy(fontFamily = labelFontFamily)
    )
}
