package com.helium4.theme.themes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helium4.theme.ThemeId
import com.helium4.theme.AnimationStyle
import com.helium4.theme.AppFonts
import com.helium4.theme.AppTheme
import com.helium4.theme.BackgroundPattern
import com.helium4.theme.ButtonStyle
import com.helium4.theme.DecorativeOverlay
import com.helium4.theme.IconStyle
import com.helium4.theme.SpacingDensity
import com.helium4.theme.SurfaceStyle

internal fun expressiveTheme(id: ThemeId, dark: Boolean): AppTheme? = when (id) {
    ThemeId.MEMPHIS -> memphisTheme(dark)
    ThemeId.RETRO_Y2K -> retroY2kTheme(dark)
    ThemeId.CYBERPUNK -> cyberpunkTheme(dark)
    ThemeId.AURORA_UI -> auroraUiTheme(dark)
    ThemeId.ORGANIC -> organicTheme(dark)
    ThemeId.MAXIMALISM -> maximalismTheme(dark)
    else -> null
}

/** Defining trait: 80s Memphis play — hot pink, cyan and yellow primaries scattered over geometric confetti. */
private fun memphisTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.MEMPHIS,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFFF5FA2) else Color(0xFFFF2D87),
    colorSecondary = if (dark) Color(0xFF4FE3E3) else Color(0xFF00BCD4),
    colorBackground = if (dark) Color(0xFF151833) else Color(0xFFFDF9EF),
    colorSurface = if (dark) Color(0xFF1F2447) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF2A3059) else Color(0xFFF2ECDA),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF7F5FF) else Color(0xFF17182B),
    colorOnSurface = if (dark) Color(0xFFF7F5FF) else Color(0xFF17182B),
    colorAccent = if (dark) Color(0xFFFFD84D) else Color(0xFFFFC400),
    colorBorder = if (dark) Color(0xFF39406E) else Color(0xFF17182B),
    colorError = if (dark) Color(0xFFFF7A6E) else Color(0xFFE53935),
    colorOutline = if (dark) Color(0xFF454D80) else Color(0xFF2B2C42),
    textPrimary = if (dark) Color(0xFFF7F5FF) else Color(0xFF17182B),
    textSecondary = if (dark) Color(0xFFB3B7DC) else Color(0xFF5C5E7A),
    headingFontFamily = AppFonts.Righteous,
    bodyFontFamily = AppFonts.Nunito,
    labelFontFamily = AppFonts.Nunito,
    headingFontSize = 28.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 14.dp,
    cornerRadiusLarge = 20.dp,
    buttonCornerRadius = 14.dp,
    cardElevation = 5.dp,
    borderWidth = 2.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.FILLED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundPattern = BackgroundPattern.GEOMETRIC,
    animationStyle = AnimationStyle.PLAYFUL,
    surfaceStyle = SurfaceStyle.HARD_SHADOW
)

/** Defining trait: Y2K chrome — silver-lilac surfaces, electric blue, sparkles and bubbly radii. */
private fun retroY2kTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.RETRO_Y2K,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF5FA8FF) else Color(0xFF0057FF),
    colorSecondary = if (dark) Color(0xFFC5A8F0) else Color(0xFF9B7BD8),
    colorBackground = if (dark) Color(0xFF1A1730) else Color(0xFFEFEEF6),
    colorSurface = if (dark) Color(0xFF262040) else Color(0xFFFDFCFF),
    colorSurfaceVariant = if (dark) Color(0xFF322A52) else Color(0xFFDCDAE8),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF0EEFC) else Color(0xFF1B1830),
    colorOnSurface = if (dark) Color(0xFFF0EEFC) else Color(0xFF1B1830),
    colorAccent = if (dark) Color(0xFF9FE8FF) else Color(0xFF00A8C8),
    colorBorder = if (dark) Color(0xFF3B3363) else Color(0xFFC7C4D8),
    colorError = if (dark) Color(0xFFFF7C9B) else Color(0xFFD62254),
    colorOutline = if (dark) Color(0xFF473D75) else Color(0xFFB3AFC8),
    textPrimary = if (dark) Color(0xFFF0EEFC) else Color(0xFF1B1830),
    textSecondary = if (dark) Color(0xFFACA6CF) else Color(0xFF5E5980),
    headingFontFamily = AppFonts.Orbitron,
    bodyFontFamily = AppFonts.Inter,
    labelFontFamily = AppFonts.Inter,
    headingFontSize = 26.sp,
    headingFontWeight = FontWeight.Bold,
    headingLetterSpacing = 1.sp,
    cornerRadiusSmall = 10.dp,
    cornerRadiusMedium = 16.dp,
    cornerRadiusLarge = 24.dp,
    buttonCornerRadius = 20.dp,
    cardElevation = 3.dp,
    backgroundGradient = if (dark) {
        listOf(Color(0xFF1A1730), Color(0xFF2A2350), Color(0xFF163046))
    } else {
        listOf(Color(0xFFEFEEF6), Color(0xFFDDE9F7), Color(0xFFE7DDF7))
    },
    glowColor = if (dark) Color(0xFF9FE8FF) else Color(0xFF00A8C8),
    buttonElevation = 2.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.BEVELED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundPattern = BackgroundPattern.GEOMETRIC,
    animationStyle = AnimationStyle.PLAYFUL,
    decorativeOverlay = DecorativeOverlay.STARS,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: neon-on-black — cyan and magenta over scanlines, hard 0dp edges, monospace body. */
private fun cyberpunkTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.CYBERPUNK,
    isDark = dark,
    colorPrimary = Color(0xFF00F0FF),
    colorSecondary = if (dark) Color(0xFFB14BFF) else Color(0xFF7A15C4),
    colorBackground = if (dark) Color(0xFF05060A) else Color(0xFFEDF3F5),
    colorSurface = if (dark) Color(0xFF0C0F17) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF141926) else Color(0xFFDCE6EA),
    colorOnPrimary = Color(0xFF05060A),
    colorOnBackground = if (dark) Color(0xFFE6FBFF) else Color(0xFF07131A),
    colorOnSurface = if (dark) Color(0xFFE6FBFF) else Color(0xFF07131A),
    colorAccent = Color(0xFFFF2E88),
    colorBorder = if (dark) Color(0xFF1E4A55) else Color(0xFFBCD2D9),
    colorError = Color(0xFFFF2E88),
    colorOutline = if (dark) Color(0xFF25606E) else Color(0xFFA8C2CB),
    textPrimary = if (dark) Color(0xFFE6FBFF) else Color(0xFF07131A),
    textSecondary = if (dark) Color(0xFF7FA9B5) else Color(0xFF456670),
    headingFontFamily = AppFonts.Orbitron,
    bodyFontFamily = AppFonts.SpaceMono,
    labelFontFamily = AppFonts.SpaceMono,
    headingFontSize = 26.sp,
    bodyFontSize = 15.sp,
    headingFontWeight = FontWeight.Bold,
    headingLetterSpacing = 2.sp,
    cornerRadiusSmall = 0.dp,
    cornerRadiusMedium = 2.dp,
    cornerRadiusLarge = 4.dp,
    buttonCornerRadius = 0.dp,
    cardElevation = 0.dp,
    glowColor = Color(0xFF00F0FF),
    buttonElevation = 0.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.OUTLINED,
    iconStyle = IconStyle.SHARP,
    spacingDensity = SpacingDensity.COMPACT,
    backgroundPattern = BackgroundPattern.SCANLINE,
    animationStyle = AnimationStyle.DRAMATIC,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: northern-lights depth — indigo and teal, soft organic wash, drifting stars. */
private fun auroraUiTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.AURORA_UI,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF6FE3C4) else Color(0xFF1E9E86),
    colorSecondary = if (dark) Color(0xFF8C9DFF) else Color(0xFF4657C9),
    colorBackground = if (dark) Color(0xFF0B1030) else Color(0xFFEFF3FB),
    colorSurface = if (dark) Color(0x38FFFFFF) else Color(0xB8FFFFFF),
    colorSurfaceVariant = if (dark) Color(0x24FFFFFF) else Color(0x8AFFFFFF),
    colorOnPrimary = Color(0xFF04231D),
    colorOnBackground = if (dark) Color(0xFFEAF0FF) else Color(0xFF0F1740),
    colorOnSurface = if (dark) Color(0xFFEAF0FF) else Color(0xFF0F1740),
    colorAccent = if (dark) Color(0xFFC08CFF) else Color(0xFF7B3FD4),
    colorBorder = if (dark) Color(0x4DFFFFFF) else Color(0x99FFFFFF),
    colorError = if (dark) Color(0xFFFF8199) else Color(0xFFD32F4E),
    colorOutline = if (dark) Color(0xFF334084) else Color(0xFFB7C4E1),
    textPrimary = if (dark) Color(0xFFEAF0FF) else Color(0xFF0F1740),
    textSecondary = if (dark) Color(0xFFA3AFD6) else Color(0xFF576594),
    headingFontFamily = AppFonts.SpaceGrotesk,
    bodyFontFamily = AppFonts.SpaceGrotesk,
    labelFontFamily = AppFonts.SpaceGrotesk,
    headingFontSize = 27.sp,
    headingFontWeight = FontWeight.SemiBold,
    cornerRadiusSmall = 12.dp,
    cornerRadiusMedium = 20.dp,
    cornerRadiusLarge = 28.dp,
    buttonCornerRadius = 22.dp,
    cardElevation = 2.dp,
    backgroundGradient = if (dark) {
        listOf(Color(0xFF0B1030), Color(0xFF17324F), Color(0xFF0E3B3A))
    } else {
        listOf(Color(0xFFEFF3FB), Color(0xFFDCE8FA), Color(0xFFD7F0EA))
    },
    glowColor = if (dark) Color(0xFF6FE3C4) else Color(0xFF1E9E86),
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundPattern = BackgroundPattern.ORGANIC,
    animationStyle = AnimationStyle.DRAMATIC,
    decorativeOverlay = DecorativeOverlay.STARS,
    surfaceStyle = SurfaceStyle.GLASS
)

/** Defining trait: nature-derived — sage, clay and sand, soft blob radii, drifting petals. */
private fun organicTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.ORGANIC,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF9CBFA0) else Color(0xFF5A7D5E),
    colorSecondary = if (dark) Color(0xFFD3A188) else Color(0xFFB0714F),
    colorBackground = if (dark) Color(0xFF1B201B) else Color(0xFFF5F1E6),
    colorSurface = if (dark) Color(0xFF242A24) else Color(0xFFFFFCF3),
    colorSurfaceVariant = if (dark) Color(0xFF2E352E) else Color(0xFFE7E1D0),
    colorOnPrimary = if (dark) Color(0xFF12190F) else Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFEDEFE6) else Color(0xFF25291F),
    colorOnSurface = if (dark) Color(0xFFEDEFE6) else Color(0xFF25291F),
    colorAccent = if (dark) Color(0xFFE0C489) else Color(0xFFB08D4A),
    colorBorder = if (dark) Color(0xFF3A423A) else Color(0xFFD9D2BE),
    colorError = if (dark) Color(0xFFD98A78) else Color(0xFFA34A32),
    colorOutline = if (dark) Color(0xFF444D44) else Color(0xFFC7BFA9),
    textPrimary = if (dark) Color(0xFFEDEFE6) else Color(0xFF25291F),
    textSecondary = if (dark) Color(0xFFAAB2A4) else Color(0xFF6A6F5D),
    headingFontFamily = AppFonts.Comfortaa,
    bodyFontFamily = AppFonts.Comfortaa,
    labelFontFamily = AppFonts.Comfortaa,
    headingFontSize = 25.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 12.dp,
    cornerRadiusMedium = 20.dp,
    cornerRadiusLarge = 28.dp,
    buttonCornerRadius = 24.dp,
    cardElevation = 1.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.COMFORTABLE,
    backgroundPattern = BackgroundPattern.ORGANIC,
    animationStyle = AnimationStyle.SUBTLE,
    decorativeOverlay = DecorativeOverlay.FLOWERS,
    surfaceStyle = SurfaceStyle.FLAT
)

/**
 * Defining trait: more is more, and it has to be *structurally* more.
 *
 * Saturated jewel tones and a display face were not enough - with one hairline border and
 * a 4dp shadow this rendered as the calmest theme in the Expressive family, which is the
 * opposite of the brief. So it layers: a three-stop gradient backdrop, the geometric
 * pattern at DRAMATIC strength over it, drifting stars above, a 3dp border and a deep
 * shadow. Loud by construction rather than by palette, and distinct from Brutalism and
 * Memphis, whose offset blocks are hard-edged and flat where these are blurred and deep.
 */
private fun maximalismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.MAXIMALISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFE0457B) else Color(0xFFB3005E),
    colorSecondary = if (dark) Color(0xFF3FBFAE) else Color(0xFF00796B),
    colorBackground = if (dark) Color(0xFF1B0F26) else Color(0xFFFFF6E8),
    colorSurface = if (dark) Color(0xFF2A1739) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF3A2150) else Color(0xFFF3E3C9),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFFCEFFF) else Color(0xFF231032),
    colorOnSurface = if (dark) Color(0xFFFCEFFF) else Color(0xFF231032),
    colorAccent = if (dark) Color(0xFFFFC93C) else Color(0xFFE09400),
    colorBorder = if (dark) Color(0xFF4A2C63) else Color(0xFFE2CBA6),
    colorError = if (dark) Color(0xFFFF6B6B) else Color(0xFFC62828),
    colorOutline = if (dark) Color(0xFF5A3777) else Color(0xFFD1B78D),
    textPrimary = if (dark) Color(0xFFFCEFFF) else Color(0xFF231032),
    textSecondary = if (dark) Color(0xFFC4A8D4) else Color(0xFF6A4A7C),
    headingFontFamily = AppFonts.Righteous,
    bodyFontFamily = AppFonts.Archivo,
    labelFontFamily = AppFonts.Archivo,
    headingFontSize = 30.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 16.dp,
    cornerRadiusLarge = 24.dp,
    buttonCornerRadius = 18.dp,
    cardElevation = 10.dp,
    buttonElevation = 4.dp,
    borderWidth = 3.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.FILLED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundGradient = if (dark) {
        listOf(Color(0xFF1B0F26), Color(0xFF44114F), Color(0xFF0E3340))
    } else {
        listOf(Color(0xFFFFEFC2), Color(0xFFFFC2DE), Color(0xFFC9B6FF))
    },
    glowColor = if (dark) Color(0xFFFFC93C) else Color(0xFFE09400),
    backgroundPattern = BackgroundPattern.GEOMETRIC,
    animationStyle = AnimationStyle.DRAMATIC,
    decorativeOverlay = DecorativeOverlay.STARS,
    surfaceStyle = SurfaceStyle.FLAT
)
