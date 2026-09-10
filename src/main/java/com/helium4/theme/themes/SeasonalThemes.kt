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
import com.helium4.theme.DecorativeOverlay
import com.helium4.theme.IconStyle
import com.helium4.theme.SpacingDensity
import com.helium4.theme.SurfaceStyle

internal fun seasonalTheme(id: ThemeId, dark: Boolean): AppTheme? = when (id) {
    ThemeId.CHRISTMAS -> christmasTheme(dark)
    ThemeId.EASTER -> easterTheme(dark)
    ThemeId.EID -> eidTheme(dark)
    else -> null
}

/** Defining trait: pine green and berry red on snow, with falling snowflakes. */
private fun christmasTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.CHRISTMAS,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF4CAF7D) else Color(0xFF0F5132),
    colorSecondary = if (dark) Color(0xFFEF5350) else Color(0xFFC62828),
    colorBackground = if (dark) Color(0xFF0B1A12) else Color(0xFFFBFDFB),
    colorSurface = if (dark) Color(0xFF13251A) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF1B3325) else Color(0xFFE8F1EA),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFEFF7F1) else Color(0xFF10261A),
    colorOnSurface = if (dark) Color(0xFFEFF7F1) else Color(0xFF10261A),
    colorAccent = if (dark) Color(0xFFE0C36A) else Color(0xFFB8912F),
    colorBorder = if (dark) Color(0xFF244233) else Color(0xFFD5E4DA),
    colorError = if (dark) Color(0xFFEF5350) else Color(0xFFC62828),
    colorOutline = if (dark) Color(0xFF2D5140) else Color(0xFFC2D6C9),
    textPrimary = if (dark) Color(0xFFEFF7F1) else Color(0xFF10261A),
    textSecondary = if (dark) Color(0xFFA6BFB0) else Color(0xFF56705F),
    headingFontFamily = AppFonts.Lora,
    bodyFontFamily = AppFonts.Nunito,
    labelFontFamily = AppFonts.Nunito,
    headingFontSize = 26.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 14.dp,
    cornerRadiusLarge = 20.dp,
    buttonCornerRadius = 16.dp,
    cardElevation = 2.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.NORMAL,
    animationStyle = AnimationStyle.PLAYFUL,
    decorativeOverlay = DecorativeOverlay.SNOWFLAKES,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: spring pastels — lilac, mint and butter yellow, with drifting eggs. */
private fun easterTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.EASTER,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFC4A8E8) else Color(0xFF8E6FC4),
    colorSecondary = if (dark) Color(0xFF8FD8BE) else Color(0xFF3FA383),
    colorBackground = if (dark) Color(0xFF241C2E) else Color(0xFFFDFAF2),
    colorSurface = if (dark) Color(0xFF302640) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF3B2F4E) else Color(0xFFF2ECFA),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF6F0FB) else Color(0xFF2E2438),
    colorOnSurface = if (dark) Color(0xFFF6F0FB) else Color(0xFF2E2438),
    colorAccent = if (dark) Color(0xFFF2DA8A) else Color(0xFFD9AE33),
    colorBorder = if (dark) Color(0xFF463859) else Color(0xFFE8DFF3),
    colorError = if (dark) Color(0xFFF08A9C) else Color(0xFFCC4560),
    colorOutline = if (dark) Color(0xFF52416B) else Color(0xFFDCD1EC),
    textPrimary = if (dark) Color(0xFFF6F0FB) else Color(0xFF2E2438),
    textSecondary = if (dark) Color(0xFFBCAFCE) else Color(0xFF6B5F7A),
    headingFontFamily = AppFonts.Quicksand,
    bodyFontFamily = AppFonts.Quicksand,
    labelFontFamily = AppFonts.Quicksand,
    headingFontSize = 26.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 12.dp,
    cornerRadiusMedium = 18.dp,
    cornerRadiusLarge = 24.dp,
    buttonCornerRadius = 22.dp,
    cardElevation = 2.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.COMFORTABLE,
    animationStyle = AnimationStyle.PLAYFUL,
    decorativeOverlay = DecorativeOverlay.EGGS,
    surfaceStyle = SurfaceStyle.FLAT
)

/** Defining trait: emerald and gold on cream or midnight, with crescents and calligraphic headings. */
private fun eidTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.EID,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF3FAE9C) else Color(0xFF00695C),
    colorSecondary = if (dark) Color(0xFFE3C670) else Color(0xFFD4AF37),
    colorBackground = if (dark) Color(0xFF0A1420) else Color(0xFFFAF5E9),
    colorSurface = if (dark) Color(0xFF11202F) else Color(0xFFFFFDF7),
    colorSurfaceVariant = if (dark) Color(0xFF1A2C3E) else Color(0xFFEFE6D2),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF3EEE1) else Color(0xFF16261F),
    colorOnSurface = if (dark) Color(0xFFF3EEE1) else Color(0xFF16261F),
    colorAccent = Color(0xFFD4AF37),
    colorBorder = if (dark) Color(0xFF24394D) else Color(0xFFE0D4B8),
    colorError = if (dark) Color(0xFFE07A72) else Color(0xFFB3392F),
    colorOutline = if (dark) Color(0xFF2E465C) else Color(0xFFCFC0A0),
    textPrimary = if (dark) Color(0xFFF3EEE1) else Color(0xFF16261F),
    textSecondary = if (dark) Color(0xFFAEB8C0) else Color(0xFF62705F),
    headingFontFamily = AppFonts.Amiri,
    bodyFontFamily = AppFonts.Inter,
    labelFontFamily = AppFonts.Inter,
    headingFontSize = 28.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 8.dp,
    cornerRadiusMedium = 16.dp,
    cornerRadiusLarge = 22.dp,
    buttonCornerRadius = 18.dp,
    cardElevation = 2.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.FILLED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.NORMAL,
    animationStyle = AnimationStyle.SUBTLE,
    decorativeOverlay = DecorativeOverlay.CRESCENTS,
    surfaceStyle = SurfaceStyle.FLAT
)
