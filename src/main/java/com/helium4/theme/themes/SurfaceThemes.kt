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

internal fun surfaceTheme(id: ThemeId, dark: Boolean): AppTheme? = when (id) {
    ThemeId.NEUMORPHISM -> neumorphismTheme(dark)
    ThemeId.GLASSMORPHISM -> glassmorphismTheme(dark)
    ThemeId.CLAYMORPHISM -> claymorphismTheme(dark)
    ThemeId.SKEUOMORPHISM -> skeuomorphismTheme(dark)
    else -> null
}

/**
 * Defining trait: one single tone for the page *and* every surface on it, with depth
 * supplied entirely by the dual shadow in `Modifier.themedSurface`.
 *
 * That is why this theme looks wrong the moment it starts tinting surfaces: if you can
 * tell a card from the background because the card is a lighter grey, the extrusion is
 * decoration rather than structure. So `colorSurface`, `colorSurfaceVariant`,
 * `cardBackgroundColor` and `inputFieldBackground` are all the background colour and the
 * borders are transparent. The elevation tokens survive, but they mean the dual shadow's
 * offset rather than a drop shadow's depth.
 */
private fun neumorphismTheme(dark: Boolean): AppTheme {
    val base = if (dark) Color(0xFF2D3748) else Color(0xFFE0E5EC)
    val fg = if (dark) Color(0xFFCBD5E0) else Color(0xFF5C6B7A)
    return AppTheme(
        id = ThemeId.NEUMORPHISM,
        isDark = dark,
        colorPrimary = if (dark) Color(0xFF7A9CC6) else Color(0xFF6E8EAD),
        colorSecondary = if (dark) Color(0xFF5A7A9A) else Color(0xFF8FA5BC),
        colorBackground = base,
        colorSurface = base,
        colorSurfaceVariant = base,
        colorOnPrimary = Color(0xFFFFFFFF),
        colorOnBackground = fg,
        colorOnSurface = fg,
        colorAccent = if (dark) Color(0xFF7A9CC6) else Color(0xFF6E8EAD),
        // Depth comes from shadows, never borders.
        colorBorder = Color.Transparent,
        colorError = if (dark) Color(0xFFE57373) else Color(0xFFC0392B),
        colorOutline = if (dark) Color(0xFF1A202C) else Color(0xFFA3B1C6),
        textPrimary = fg,
        textSecondary = if (dark) Color(0xFF94A3B8) else Color(0xFF8496A8),
        inputFieldBackground = base,
        inputFieldBorderColor = Color.Transparent,
        cardBackgroundColor = base,
        cardBorderColor = Color.Transparent,
        // The one place a line is allowed: a separator has no shadow to fall back on.
        dividerColor = if (dark) {
            Color(0xFF1A202C).copy(alpha = 0.6f)
        } else {
            Color(0xFFA3B1C6).copy(alpha = 0.4f)
        },
        headingFontFamily = AppFonts.Nunito,
        bodyFontFamily = AppFonts.Nunito,
        labelFontFamily = AppFonts.Nunito,
        headingFontSize = 24.sp,
        headingFontWeight = FontWeight.Light,
        cornerRadiusSmall = 8.dp,
        cornerRadiusMedium = 12.dp,
        cornerRadiusLarge = 16.dp,
        buttonCornerRadius = 12.dp,
        // Not a drop shadow's depth but the dual shadow's *offset* - how far the
        // surface stands off the page. See the NEUMORPHIC branch of themedSurface.
        cardElevation = 6.dp,
        buttonElevation = 4.dp,
        borderWidth = 0.dp,
        buttonStyle = ButtonStyle.BEVELED,
        iconStyle = IconStyle.ROUNDED,
        spacingDensity = SpacingDensity.COMFORTABLE,
        animationStyle = AnimationStyle.SUBTLE,
        surfaceStyle = SurfaceStyle.NEUMORPHIC
    )
}

/** Defining trait: translucent frosted panels — alpha lives in the fill tokens only, so text stays fully opaque and legible. */
private fun glassmorphismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.GLASSMORPHISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFF7AA7FF) else Color(0xFF3D6BE0),
    colorSecondary = if (dark) Color(0xFF9ED7E8) else Color(0xFF2E8FA8),
    colorBackground = if (dark) Color(0xFF141A2B) else Color(0xFFE8EEF9),
    colorSurface = if (dark) Color(0x33FFFFFF) else Color(0x99FFFFFF),
    colorSurfaceVariant = if (dark) Color(0x1FFFFFFF) else Color(0x66FFFFFF),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF2F5FC) else Color(0xFF17203A),
    colorOnSurface = if (dark) Color(0xFFF2F5FC) else Color(0xFF17203A),
    colorAccent = if (dark) Color(0xFFB6A6FF) else Color(0xFF7A5AF0),
    colorBorder = if (dark) Color(0x4DFFFFFF) else Color(0x66FFFFFF),
    colorError = if (dark) Color(0xFFFF8A8A) else Color(0xFFD32F2F),
    colorOutline = if (dark) Color(0x59FFFFFF) else Color(0x80FFFFFF),
    textPrimary = if (dark) Color(0xFFF2F5FC) else Color(0xFF17203A),
    textSecondary = if (dark) Color(0xFFB4BFD6) else Color(0xFF56618A),
    headingFontFamily = AppFonts.SpaceGrotesk,
    bodyFontFamily = AppFonts.Inter,
    labelFontFamily = AppFonts.Inter,
    headingFontSize = 26.sp,
    headingFontWeight = FontWeight.SemiBold,
    cornerRadiusSmall = 10.dp,
    cornerRadiusMedium = 18.dp,
    cornerRadiusLarge = 24.dp,
    buttonCornerRadius = 18.dp,
    cardElevation = 2.dp,
    // Glass has to have something behind it. The gradient is the light source the
    // blurred blooms in AppThemeProvider diffuse; without it the panels are just
    // translucent grey rectangles.
    backgroundGradient = if (dark) {
        listOf(Color(0xFF141A2B), Color(0xFF1B2340), Color(0xFF10203A))
    } else {
        listOf(Color(0xFFE8EEF9), Color(0xFFD9E4F7), Color(0xFFE4E9FA))
    },
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.BEVELED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundPattern = BackgroundPattern.ORGANIC,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.GLASS
)

/** Defining trait: puffy pastel clay — oversized radii, pill buttons, and rounded friendly type. */
private fun claymorphismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.CLAYMORPHISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFB6A4F5) else Color(0xFF8B72E8),
    colorSecondary = if (dark) Color(0xFF87DCC4) else Color(0xFF4FC3A1),
    colorBackground = if (dark) Color(0xFF2A2640) else Color(0xFFF3EEFF),
    colorSurface = if (dark) Color(0xFF362F52) else Color(0xFFFFFFFF),
    colorSurfaceVariant = if (dark) Color(0xFF413964) else Color(0xFFEADFFB),
    colorOnPrimary = Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF4F0FF) else Color(0xFF2E2745),
    colorOnSurface = if (dark) Color(0xFFF4F0FF) else Color(0xFF2E2745),
    colorAccent = if (dark) Color(0xFFFFB59B) else Color(0xFFFF8F6B),
    colorBorder = if (dark) Color(0xFF473E6D) else Color(0xFFE0D4F7),
    colorError = if (dark) Color(0xFFFF8F8F) else Color(0xFFE05252),
    colorOutline = if (dark) Color(0xFF554A80) else Color(0xFFD6C7F2),
    textPrimary = if (dark) Color(0xFFF4F0FF) else Color(0xFF2E2745),
    textSecondary = if (dark) Color(0xFFBBB0DC) else Color(0xFF6F6591),
    headingFontFamily = AppFonts.Nunito,
    bodyFontFamily = AppFonts.Quicksand,
    labelFontFamily = AppFonts.Quicksand,
    headingFontSize = 26.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 14.dp,
    cornerRadiusMedium = 24.dp,
    cornerRadiusLarge = 32.dp,
    buttonCornerRadius = 28.dp,
    cardElevation = 10.dp,
    buttonElevation = 6.dp,
    borderWidth = 0.dp,
    buttonStyle = ButtonStyle.BEVELED,
    iconStyle = IconStyle.ROUNDED,
    spacingDensity = SpacingDensity.COMFORTABLE,
    animationStyle = AnimationStyle.PLAYFUL,
    surfaceStyle = SurfaceStyle.CLAY
)

/** Defining trait: real-world materials — warm leather browns, cream paper, bevelled controls and a noise grain. */
private fun skeuomorphismTheme(dark: Boolean): AppTheme = AppTheme(
    id = ThemeId.SKEUOMORPHISM,
    isDark = dark,
    colorPrimary = if (dark) Color(0xFFC89B62) else Color(0xFF8B5E34),
    colorSecondary = if (dark) Color(0xFFA98457) else Color(0xFFA9743F),
    colorBackground = if (dark) Color(0xFF241C14) else Color(0xFFF3E7D3),
    colorSurface = if (dark) Color(0xFF31261B) else Color(0xFFFBF3E4),
    colorSurfaceVariant = if (dark) Color(0xFF3E3123) else Color(0xFFE7D6BB),
    colorOnPrimary = if (dark) Color(0xFF241C14) else Color(0xFFFFFFFF),
    colorOnBackground = if (dark) Color(0xFFF3E7D3) else Color(0xFF3A2A1B),
    colorOnSurface = if (dark) Color(0xFFF3E7D3) else Color(0xFF3A2A1B),
    colorAccent = if (dark) Color(0xFFD9B26B) else Color(0xFFB07A2E),
    colorBorder = if (dark) Color(0xFF4A3A29) else Color(0xFFCBB690),
    colorError = if (dark) Color(0xFFE07A6A) else Color(0xFFA83227),
    colorOutline = if (dark) Color(0xFF54422F) else Color(0xFFBBA378),
    textPrimary = if (dark) Color(0xFFF3E7D3) else Color(0xFF3A2A1B),
    textSecondary = if (dark) Color(0xFFC0AB90) else Color(0xFF7A6349),
    headingFontFamily = AppFonts.Lora,
    bodyFontFamily = AppFonts.Lora,
    labelFontFamily = AppFonts.SystemSans,
    headingFontSize = 25.sp,
    headingFontWeight = FontWeight.Bold,
    cornerRadiusSmall = 6.dp,
    cornerRadiusMedium = 10.dp,
    cornerRadiusLarge = 14.dp,
    buttonCornerRadius = 10.dp,
    cardElevation = 4.dp,
    buttonElevation = 3.dp,
    borderWidth = 1.dp,
    buttonStyle = ButtonStyle.BEVELED,
    iconStyle = IconStyle.FILLED,
    spacingDensity = SpacingDensity.NORMAL,
    backgroundPattern = BackgroundPattern.NOISE,
    animationStyle = AnimationStyle.SUBTLE,
    surfaceStyle = SurfaceStyle.EMBOSSED
)
