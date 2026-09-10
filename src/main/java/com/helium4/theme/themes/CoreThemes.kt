package com.helium4.theme.themes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helium4.theme.ThemeId
import com.helium4.theme.AnimationStyle
import com.helium4.theme.AppFonts
import com.helium4.theme.AppTheme
import com.helium4.theme.ButtonStyle
import com.helium4.theme.IconStyle
import com.helium4.theme.SpacingDensity
import com.helium4.theme.SurfaceStyle

/** The four colors that make up the entire DEFAULT palette. Nothing else is permitted. */
private val Ink = Color(0xFF000000)
private val Paper = Color(0xFFFFFFFF)
private val ActionBlue = Color(0xFF0066FF)
private val DestructiveRed = Color(0xFFFF0000)

internal fun coreTheme(id: ThemeId, dark: Boolean): AppTheme? = when (id) {
    ThemeId.DEFAULT -> defaultTheme(dark)
    ThemeId.MIDNIGHT -> midnightTheme()
    ThemeId.FLAT -> flatTheme(dark)
    ThemeId.MATERIAL -> materialTheme(dark)
    ThemeId.DARK_UI -> darkUiTheme(dark)
    else -> null
}

// --- Midnight -------------------------------------------------------------
// Violet-on-near-black. Soft elevated surfaces, hairline dividers, one violet
// accent for every action and a rose for destructive ones.

private val MidnightBg = Color(0xFF0B0A10)
private val MidnightSurface = Color(0xFF171520)
private val MidnightSurfaceElevated = Color(0xFF231F32)
private val MidnightTextPrimary = Color(0xFFF8F8F9)
private val MidnightTextSecondary = Color(0xFFA3A1B5)
private val MidnightAction = Color(0xFF8B5CF6)
private val MidnightDestructive = Color(0xFFF43F5E)
private val MidnightDivider = Color(0xFF2A273A)

/**
 * Dark-only, and takes no `dark` parameter to say so in the signature.
 *
 * Midnight *is* violet-on-near-black - that is the design language the Phase 2 screens
 * are drawn against. It previously shipped a light counterpart with inverted neutrals,
 * which rendered as near-white and was almost indistinguishable from DEFAULT, so
 * "Midnight" in Light mode named one thing and showed another. It now ignores the app's
 * light/dark setting; every other theme still honours it.
 */
private fun midnightTheme(): AppTheme {
    val bg = MidnightBg
    val surface = MidnightSurface
    val elevated = MidnightSurfaceElevated
    val fg = MidnightTextPrimary
    val fgMuted = MidnightTextSecondary
    val divider = MidnightDivider

    return AppTheme(
        id = ThemeId.MIDNIGHT,
        isDark = true,
        colorPrimary = MidnightAction,
        colorSecondary = MidnightAction,
        colorBackground = bg,
        colorSurface = surface,
        colorSurfaceVariant = elevated,
        colorOnPrimary = Color.White,
        colorOnBackground = fg,
        colorOnSurface = fg,
        colorAccent = MidnightAction,
        colorBorder = divider,
        colorError = MidnightDestructive,
        colorOutline = divider,
        textPrimary = fg,
        textSecondary = fgMuted,
        statusBarColor = bg,
        navigationBarColor = bg,
        topAppBarColor = bg,
        topAppBarContentColor = fg,
        inputFieldBackground = bg,
        inputFieldBorderColor = divider,
        cardBackgroundColor = surface,
        cardBorderColor = divider,
        dividerColor = divider,
        snackbarBackground = elevated,
        dialogBackground = surface,
        chipBackgroundColor = elevated,
        chipTextColor = fgMuted,
        shimmerBaseColor = surface,
        shimmerHighlightColor = elevated,
        headingFontSize = 22.sp,
        bodyFontSize = 15.sp,
        labelFontSize = 13.sp,
        headingFontWeight = FontWeight.Medium,
        cornerRadiusSmall = 8.dp,
        cornerRadiusMedium = 16.dp,
        cornerRadiusLarge = 24.dp,
        buttonCornerRadius = 14.dp,
        surfaceStyle = SurfaceStyle.FLAT,
        buttonStyle = ButtonStyle.FILLED,
        spacingDensity = SpacingDensity.NORMAL,
        animationStyle = AnimationStyle.SUBTLE
    )
}

/**
 * The library's baseline design system, and the fallback theme: strictly two-tone.
 *
 * Every surface token is pure paper or pure ink; every text token is its inverse. There
 * are no greys and no alpha-dimmed values, because an alpha'd black composites to grey.
 * Consequences, all deliberate:
 *  - [AppTheme.textSecondary] equals [AppTheme.textPrimary]; hierarchy comes from size,
 *    weight and whitespace instead of color.
 *  - Surfaces carry no fill ([SurfaceStyle.BORDER_ONLY]) — a 1dp border is what makes a
 *    card read as a card.
 *  - Blue means "action", red means "destructive", and nothing else uses them. Since
 *    color alone can never carry meaning here, every use is paired with an icon or label.
 */
private fun defaultTheme(dark: Boolean): AppTheme {
    val bg = if (dark) Ink else Paper
    val fg = if (dark) Paper else Ink
    return AppTheme(
        id = ThemeId.DEFAULT,
        isDark = dark,
        colorPrimary = ActionBlue,
        colorSecondary = ActionBlue,
        colorBackground = bg,
        colorSurface = bg,
        colorSurfaceVariant = bg,
        colorOnPrimary = Paper,
        colorOnBackground = fg,
        colorOnSurface = fg,
        colorAccent = ActionBlue,
        colorBorder = fg,
        colorError = DestructiveRed,
        colorOutline = fg,
        textPrimary = fg,
        // Intentionally identical to textPrimary — see the class doc.
        textSecondary = fg,
        statusBarColor = bg,
        navigationBarColor = bg,
        topAppBarColor = bg,
        topAppBarContentColor = fg,
        bottomNavBackgroundColor = bg,
        bottomNavSelectedColor = ActionBlue,
        bottomNavUnselectedColor = fg,
        inputFieldBackground = bg,
        inputFieldBorderColor = fg,
        inputFieldTextColor = fg,
        cardBackgroundColor = bg,
        cardBorderColor = fg,
        dividerColor = fg,
        snackbarBackground = bg,
        snackbarTextColor = fg,
        dialogBackground = bg,
        // No grey available: loading states pulse between the two tones rather than
        // sweeping a grey gradient.
        shimmerBaseColor = bg,
        shimmerHighlightColor = fg,
        fabBackgroundColor = ActionBlue,
        fabIconColor = Paper,
        chipBackgroundColor = bg,
        chipTextColor = fg,
        switchThumbColor = ActionBlue,
        switchTrackColor = bg,
        checkboxColor = ActionBlue,
        radioButtonColor = ActionBlue,
        sliderActiveColor = ActionBlue,
        sliderInactiveColor = fg,
        progressIndicatorColor = ActionBlue,
        tooltipBackgroundColor = bg,
        tooltipTextColor = fg,
        // ActionBlue, not DestructiveRed: red is reserved for destructive actions in this
        // theme, and a count badge is not one.
        badgeBackgroundColor = ActionBlue,
        badgeTextColor = Paper,
        headingFontFamily = AppFonts.SystemSans,
        bodyFontFamily = AppFonts.SystemSans,
        labelFontFamily = AppFonts.SystemSans,
        headingFontSize = 26.sp,
        bodyFontSize = 16.sp,
        labelFontSize = 13.sp,
        headingFontWeight = FontWeight.Bold,
        cornerRadiusSmall = 4.dp,
        cornerRadiusMedium = 10.dp,
        cornerRadiusLarge = 16.dp,
        buttonCornerRadius = 10.dp,
        cardElevation = 0.dp,
        buttonElevation = 0.dp,
        borderWidth = 1.dp,
        buttonStyle = ButtonStyle.FILLED,
        iconStyle = IconStyle.OUTLINE,
        spacingDensity = SpacingDensity.NORMAL,
        animationStyle = AnimationStyle.SUBTLE,
        surfaceStyle = SurfaceStyle.BORDER_ONLY,
        allowsAlphaDimming = false
    )
}

/** Zero shadows, zero corner radius, saturated flat palette. */
private fun flatTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.FLAT,
    isDark = dark,
    colorPrimary = Color(0xFF2980B9),
    colorSecondary = Color(0xFF16A085),
    colorBackground = if (dark) Color(0xFF2C3E50) else Color(0xFFECF0F1),
    colorSurface = if (dark) Color(0xFF34495E) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF3D566E) else Color(0xFFDDE4E6),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFECF0F1) else Color(0xFF2C3E50),
    colorOnSurface = if (dark) Color(0xFFECF0F1) else Color(0xFF2C3E50),
    colorAccent = Color(0xFFE67E22),
    colorBorder = if (dark) Color(0xFF3D566E) else Color(0xFFBDC3C7),
    colorError = Color(0xFFC0392B),
    colorOutline = if (dark) Color(0xFF3D566E) else Color(0xFFBDC3C7),
    textPrimary = if (dark) Color(0xFFECF0F1) else Color(0xFF2C3E50),
    textSecondary = if (dark) Color(0xFFB2BEC3) else Color(0xFF7F8C8D),
    cornerRadiusSmall = 0.dp,
    cornerRadiusMedium = 0.dp,
    cornerRadiusLarge = 0.dp,
    buttonCornerRadius = 0.dp,
    cardElevation = 0.dp,
    // Flat design separates by colour block, never by outline.
    borderWidth = 0.dp,
    dividerThickness = 1.dp,
    buttonElevation = 0.dp,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/**
 * Standard Material 3 look. This is the one theme for which `AppThemeProvider` layers on
 * the platform's dynamic color (wallpaper-derived) on API 31+, in `toColorScheme`.
 */
private fun materialTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.MATERIAL,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFD0BCFF) else Color(0xFF6750A4),
    colorSecondary = if (dark) Color(0xFFCCC2DC) else Color(0xFF625B71),
    colorBackground = if (dark) Color(0xFF1C1B1F) else Color(0xFFFFFBFE),
    colorSurface = if (dark) Color(0xFF1C1B1F) else Color(0xFFFFFBFE),
    colorSurfaceVariant = if (dark) Color(0xFF49454F) else Color(0xFFE7E0EC),
    colorOnPrimary = if (dark) Color(0xFF381E72) else Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F),
    colorOnSurface = if (dark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F),
    colorAccent = if (dark) Color(0xFFEFB8C8) else Color(0xFF7D5260),
    colorBorder = if (dark) Color(0xFF49454F) else Color(0xFFCAC4D0),
    colorError = if (dark) Color(0xFFF2B8B5) else Color(0xFFB3261E),
    colorOutline = if (dark) Color(0xFF938F99) else Color(0xFF79747E),
    textPrimary = if (dark) Color(0xFFE6E1E5) else Color(0xFF1C1B1F),
    textSecondary = if (dark) Color(0xFFCAC4D0) else Color(0xFF49454F),
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 12.dp,
    cornerRadiusLarge = 28.dp,
    buttonCornerRadius = 20.dp,
    cardElevation = 1.dp,
    // Material separates by elevation; an outline on top of it is a different language.
    borderWidth = 0.dp,
    dividerThickness = 1.dp,
    buttonElevation = 1.dp,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/** OLED-friendly: true black grounds with cool grey surfaces. Always dark in feel. */
private fun darkUiTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.DARK_UI,
    isDark = dark,
    colorPrimary = Color(0xFF4C8DFF),
    colorSecondary = Color(0xFF8AB4F8),
    colorBackground = if (dark) Color(0xFF000000) else Color(0xFF12131A),
    colorSurface = if (dark) Color(0xFF0B0C10) else Color(0xFF1B1D26),
    colorSurfaceVariant = if (dark) Color(0xFF14161C) else Color(0xFF262933),
    colorOnPrimary = Color(0xFF00122E),
    colorOnBackground = Color(0xFFE8EAF0),
    colorOnSurface = Color(0xFFE8EAF0),
    colorAccent = Color(0xFF4C8DFF),
    colorBorder = Color(0xFF23262F),
    colorError = Color(0xFFFF5A5F),
    colorOutline = Color(0xFF2C3038),
    textPrimary = Color(0xFFE8EAF0),
    textSecondary = Color(0xFF9AA0AC),
    cornerRadiusSmall = 6.dp,
    cornerRadiusMedium = 12.dp,
    cornerRadiusLarge = 18.dp,
    buttonCornerRadius = 10.dp,
    cardElevation = 0.dp,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)
