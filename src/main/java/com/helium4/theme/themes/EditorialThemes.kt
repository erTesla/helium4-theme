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
import com.helium4.theme.IconStyle
import com.helium4.theme.SpacingDensity
import com.helium4.theme.SurfaceStyle

internal fun editorialTheme(id: ThemeId, dark: Boolean): AppTheme? = when (id) {
    ThemeId.MINIMALISM -> minimalismTheme(dark)
    ThemeId.SWISS_STYLE -> swissStyleTheme(dark)
    ThemeId.TYPOGRAPHIC -> typographicTheme(dark)
    ThemeId.BENTO_GRID -> bentoGridTheme(dark)
    ThemeId.BRUTALISM -> brutalismTheme(dark)
    else -> null
}

/** Defining trait: restraint — near-monochrome, tiny radii, generous whitespace, outlined controls. */
private fun minimalismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.MINIMALISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFE8E8E8) else Color(0xFF1A1A1A),
    colorSecondary = if (dark) Color(0xFFB5B5B5) else Color(0xFF4D4D4D),
    colorBackground = if (dark) Color(0xFF121212) else Color(0xFFFAFAFA),
    colorSurface = if (dark) Color(0xFF1A1A1A) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF242424) else Color(0xFFF0F0F0),
    colorOnPrimary = if (dark) Color(0xFF121212) else Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFEDEDED) else Color(0xFF1A1A1A),
    colorOnSurface = if (dark) Color(0xFFEDEDED) else Color(0xFF1A1A1A),
    colorAccent = if (dark) Color(0xFFEDEDED) else Color(0xFF1A1A1A),
    colorBorder = if (dark) Color(0xFF303030) else Color(0xFFE2E2E2),
    colorError = if (dark) Color(0xFFE08A8A) else Color(0xFFB33A3A),
    colorOutline = if (dark) Color(0xFF3A3A3A) else Color(0xFFD6D6D6),
    textPrimary = if (dark) Color(0xFFEDEDED) else Color(0xFF1A1A1A),
    textSecondary = if (dark) Color(0xFF9A9A9A) else Color(0xFF6E6E6E),
    headingFontFamily = AppFonts.Inter,
    bodyFontFamily = AppFonts.Inter,
    labelFontFamily = AppFonts.Inter,
    headingFontSize = 22.sp,
    bodyFontSize = 16.sp,
    headingFontWeight = FontWeight.Medium,
    cornerRadiusSmall = 2.dp,
    cornerRadiusMedium = 4.dp,
    cornerRadiusLarge = 4.dp,
    buttonCornerRadius = 4.dp,
    cardElevation = 0.dp,
    // A minimal rule is thinner than a border, which is the entire point of the theme.
    dividerThickness = 0.5.dp,
    buttonElevation = 0.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.OUTLINED,
    iconStyle = IconStyle.OUTLINE,
    spacingDensity = SpacingDensity.COMFORTABLE,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: International Typographic Style — strict grid, square corners, one red, tight bold headings. */
private fun swissStyleTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.SWISS_STYLE,
    isDark = dark,
    colorPrimary = Color(0xFFE30613),
    colorSecondary = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorBackground = if (dark) Color(0xFF000000) else Color(0xFFFFFFFF),
    colorSurface = if (dark) Color(0xFF0D0D0D) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF1A1A1A) else Color(0xFFF2F2F2),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorOnSurface = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorAccent = Color(0xFFE30613),
    colorBorder = if (dark) Color(0xFF2B2B2B) else Color(0xFFDCDCDC),
    colorError = Color(0xFFE30613),
    colorOutline = if (dark) Color(0xFF3A3A3A) else Color(0xFFC9C9C9),
    textPrimary = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    textSecondary = if (dark) Color(0xFFA8A8A8) else Color(0xFF5C5C5C),
    headingFontFamily = AppFonts.Inter,
    bodyFontFamily = AppFonts.Inter,
    labelFontFamily = AppFonts.Inter,
    headingFontSize = 30.sp,
    bodyFontSize = 15.sp,
    labelFontSize = 12.sp,
    headingFontWeight = FontWeight.Bold,
    headingLetterSpacing = (-1).sp,
    cornerRadiusSmall = 0.dp,
    cornerRadiusMedium = 0.dp,
    cornerRadiusLarge = 0.dp,
    buttonCornerRadius = 0.dp,
    cardElevation = 0.dp,
    buttonElevation = 0.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.SHARP,
    spacingDensity = SpacingDensity.COMPACT,
    backgroundPattern = BackgroundPattern.GRID,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: type carries everything — oversized serif headings on ink-and-cream, no shape decoration. */
private fun typographicTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.TYPOGRAPHIC,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFE8DFCF) else Color(0xFF1B1815),
    colorSecondary = if (dark) Color(0xFFC2B49C) else Color(0xFF5A4F42),
    colorBackground = if (dark) Color(0xFF14120F) else Color(0xFFF7F2E7),
    colorSurface = if (dark) Color(0xFF1C1915) else Color(0xFFFFFBF2),
    colorSurfaceVariant = if (dark) Color(0xFF262119) else Color(0xFFEDE5D4),
    colorOnPrimary = if (dark) Color(0xFF14120F) else Color(0xFFF7F2E7),
    colorOnBackground = if (dark) Color(0xFFF2EADB) else Color(0xFF1B1815),
    colorOnSurface = if (dark) Color(0xFFF2EADB) else Color(0xFF1B1815),
    colorAccent = if (dark) Color(0xFFC08A4E) else Color(0xFF8A5A22),
    colorBorder = if (dark) Color(0xFF342E24) else Color(0xFFDDD2BC),
    colorError = if (dark) Color(0xFFD9776B) else Color(0xFFA33427),
    colorOutline = if (dark) Color(0xFF3E372B) else Color(0xFFCFC3AA),
    textPrimary = if (dark) Color(0xFFF2EADB) else Color(0xFF1B1815),
    textSecondary = if (dark) Color(0xFFB0A48E) else Color(0xFF6B6154),
    headingFontFamily = AppFonts.PlayfairDisplay,
    bodyFontFamily = AppFonts.Lora,
    labelFontFamily = AppFonts.Lora,
    headingFontSize = 34.sp,
    bodyFontSize = 17.sp,
    headingFontWeight = FontWeight.Bold,
    headingLetterSpacing = (-0.5).sp,
    cornerRadiusSmall = 0.dp,
    cornerRadiusMedium = 0.dp,
    cornerRadiusLarge = 0.dp,
    buttonCornerRadius = 0.dp,
    cardElevation = 0.dp,
    buttonElevation = 0.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.OUTLINED,
    iconStyle = IconStyle.OUTLINE,
    spacingDensity = SpacingDensity.COMFORTABLE,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: modular card grid — soft neutral tiles, medium radii, dense packing. */
private fun bentoGridTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.BENTO_GRID,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF8FB3FF) else Color(0xFF3A5BC7),
    colorSecondary = if (dark) Color(0xFFA8C8B4) else Color(0xFF4E7C63),
    colorBackground = if (dark) Color(0xFF15161A) else Color(0xFFF4F5F7),
    colorSurface = if (dark) Color(0xFF1E2026) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF272A32) else Color(0xFFE9EBEF),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFEDEFF3) else Color(0xFF1B1D22),
    colorOnSurface = if (dark) Color(0xFFEDEFF3) else Color(0xFF1B1D22),
    colorAccent = if (dark) Color(0xFFF0B27A) else Color(0xFFC97B2E),
    colorBorder = if (dark) Color(0xFF2E323B) else Color(0xFFE0E3E8),
    colorError = if (dark) Color(0xFFF08585) else Color(0xFFCB3B3B),
    colorOutline = if (dark) Color(0xFF373C46) else Color(0xFFD3D7DE),
    textPrimary = if (dark) Color(0xFFEDEFF3) else Color(0xFF1B1D22),
    textSecondary = if (dark) Color(0xFF9BA2AF) else Color(0xFF666D79),
    headingFontFamily = AppFonts.Archivo,
    bodyFontFamily = AppFonts.Archivo,
    labelFontFamily = AppFonts.Archivo,
    headingFontSize = 24.sp,
    headingFontWeight = FontWeight.SemiBold,
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 18.dp,
    cornerRadiusLarge = 24.dp,
    buttonCornerRadius = 14.dp,
    cardElevation = 2.dp,
    buttonElevation = 0.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.COMPACT,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: raw and unpolished — heavy borders, hard un-blurred offset shadows, a harsh accent, no motion. */
private fun brutalismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.BRUTALISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF00FF66) else Color(0xFFFFFF00),
    colorSecondary = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorBackground = if (dark) Color(0xFF000000) else Color(0xFFFFFFFF),
    colorSurface = if (dark) Color(0xFF0A0A0A) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF161616) else Color(0xFFEFEFEF),
    colorOnPrimary = Color(0xFF000000),
    colorOnBackground = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorOnSurface = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorAccent = if (dark) Color(0xFF00FF66) else Color(0xFFFFFF00),
    colorBorder = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    colorError = if (dark) Color(0xFFFF4B4B) else Color(0xFFFF0000),
    colorOutline = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    textPrimary = if (dark) Color(0xFFFFFFFF) else Color(0xFF000000),
    textSecondary = if (dark) Color(0xFFBFBFBF) else Color(0xFF3D3D3D),
    headingFontFamily = AppFonts.SpaceMono,
    bodyFontFamily = AppFonts.SpaceMono,
    labelFontFamily = AppFonts.SpaceMono,
    headingFontSize = 28.sp,
    bodyFontSize = 15.sp,
    headingFontWeight = FontWeight.Bold,
    headingLetterSpacing = (-0.5).sp,
    cornerRadiusSmall = 0.dp,
    cornerRadiusMedium = 0.dp,
    cornerRadiusLarge = 0.dp,
    buttonCornerRadius = 0.dp,
    // Consumed as the hard shadow offset distance, not a blur radius.
    cardElevation = 6.dp,
    buttonElevation = 4.dp,
    borderWidth = 2.dp,
    buttonStyle = ButtonStyle.OUTLINED,
    iconStyle = IconStyle.SHARP,
    spacingDensity = SpacingDensity.COMPACT,
    animationStyle = AnimationStyle.NONE,
    surfaceStyle = SurfaceStyle.HARD_SHADOW
)
