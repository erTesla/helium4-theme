package com.helium4.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helium4.theme.ThemeId

/**
 * How buttons render their container.
 *
 * [BEVELED] is the one that defers: it hands the container to the theme's own
 * [SurfaceStyle], so a neumorphic theme extrudes its buttons exactly the way it extrudes
 * its cards instead of the button reinventing a shadow.
 */
public enum class ButtonStyle { FILLED, OUTLINED, GHOST, BEVELED }

/** Preferred icon treatment; consumed by components that pick between icon variants. */
public enum class IconStyle { OUTLINE, FILLED, ROUNDED, SHARP }

/** Drives the dp values returned by [AppTheme.spacing]. */
public enum class SpacingDensity { COMPACT, NORMAL, COMFORTABLE }

/** Low-opacity texture drawn *behind* content. */
public enum class BackgroundPattern { NONE, GEOMETRIC, ORGANIC, NOISE, GRID, SCANLINE }

/** Global animation budget. NONE also disables theme cross-fades. */
public enum class AnimationStyle { NONE, SUBTLE, PLAYFUL, DRAMATIC }

/** Animated particles drawn *above* content. Never interactive. */
public enum class DecorativeOverlay { NONE, SNOWFLAKES, EGGS, CRESCENTS, STARS, FLOWERS }

/**
 * How a surface (card, sheet, field) separates itself from the background. The single
 * switch consumed by `Modifier.themedSurface` — components never branch on this themselves.
 */
public enum class SurfaceStyle {
    /** Plain fill, optional elevation. */
    FLAT,

    /** No fill at all; a 1dp border is the only separator. Used by DEFAULT (no greys allowed). */
    BORDER_ONLY,

    /** Neumorphic dual shadow — light from top-left, dark from bottom-right. */
    NEUMORPHIC,

    /** Translucent fill + blur + hairline highlight border. */
    GLASS,

    /** Puffy clay: large radius, outer drop shadow plus inner light. */
    CLAY,

    /** Hard, un-blurred offset shadow with a heavy border. */
    HARD_SHADOW,

    /** Subtle emboss/bevel, gradient-free. */
    EMBOSSED
}

/**
 * Every visual token in the app, in one immutable object.
 *
 * Components read this via [LocalAppTheme] and must never hardcode a color, corner
 * radius, or text style. Every field has a sensible default so a theme definition only
 * states what actually differs from the baseline.
 *
 * The [ThemeId.DEFAULT] instance *is* the baseline design system: pure `#FFFFFF` /
 * `#000000` with no greys at all, blue reserved for actions and red for destructive
 * actions. Hierarchy in that theme comes from type and whitespace, never from color —
 * which is why [textSecondary] there is the same value as [textPrimary].
 *
 * ### Stability contract
 *
 * This is a `data class` **on purpose**, and that is a deliberate trade:
 *
 * - You get `copy()`, so a consumer can derive a custom theme from a shipped one without
 *   restating ~60 tokens. That is the main way to extend this library, and losing it
 *   would make custom themes impractical.
 * - You get structural `equals`/`hashCode`, which is what lets Compose skip recomposition
 *   of every component reading [LocalAppTheme] when an unrelated state change happens.
 *   Hand-writing that over this many fields would be worse code and easy to get wrong.
 *
 * The cost: `copy()` and `componentN()` are part of the binary API. **Adding a token is
 * source-compatible but binary-breaking** — a consumer compiled against an older version
 * will get a `NoSuchMethodError` on `copy()` until it recompiles. Therefore:
 *
 * - Any new token is a **minor** version bump at minimum, never a patch.
 * - Any new token must have a default value, so existing named-argument construction in
 *   consumer code keeps compiling.
 * - Removing or renaming a token, or changing a type, is a **major** bump.
 *
 * There is no automated binary-compatibility check on this module yet: `binary-
 * compatibility-validator` registers no tasks here, because the build uses AGP 9's
 * built-in Kotlin support rather than the `kotlin-android` plugin it hooks into. Until
 * that changes, the contract above is enforced by review — `explicitApi()` at least
 * guarantees nothing becomes public by accident.
 */
@Immutable
public data class AppTheme(
    // ---- Meta ----
    val id: ThemeId,
    /** Defaults to [ThemeId.displayName]; override only for a variant label. */
    val displayName: String = id.displayName,
    val isDark: Boolean,

    // ---- Core colors ----
    val colorPrimary: Color,
    val colorSecondary: Color,
    val colorBackground: Color,
    val colorSurface: Color,
    val colorSurfaceVariant: Color,
    val colorOnPrimary: Color,
    val colorOnBackground: Color,
    val colorOnSurface: Color,
    val colorAccent: Color,
    val colorBorder: Color,
    val colorError: Color,
    val colorOutline: Color,

    // ---- Text ----
    val textPrimary: Color,
    /** May legitimately equal [textPrimary] (DEFAULT does), since greys are banned there. */
    val textSecondary: Color,

    // ---- System bars ----
    val statusBarColor: Color = colorBackground,
    val navigationBarColor: Color = colorBackground,

    // ---- Component colors ----
    val topAppBarColor: Color = colorBackground,
    val topAppBarContentColor: Color = textPrimary,
    val bottomNavBackgroundColor: Color = colorSurface,
    val bottomNavSelectedColor: Color = colorPrimary,
    val bottomNavUnselectedColor: Color = textSecondary,
    val inputFieldBackground: Color = colorSurface,
    val inputFieldBorderColor: Color = colorBorder,
    val inputFieldTextColor: Color = textPrimary,
    val cardBackgroundColor: Color = colorSurface,
    val cardBorderColor: Color = colorBorder,
    val dividerColor: Color = colorBorder,
    val snackbarBackground: Color = colorSurfaceVariant,
    val snackbarTextColor: Color = textPrimary,
    val dialogBackground: Color = colorSurface,
    val shimmerBaseColor: Color = colorSurfaceVariant,
    val shimmerHighlightColor: Color = colorSurface,
    val fabBackgroundColor: Color = colorPrimary,
    val fabIconColor: Color = colorOnPrimary,
    val chipBackgroundColor: Color = colorSurfaceVariant,
    val chipTextColor: Color = textPrimary,
    val switchThumbColor: Color = colorPrimary,
    val switchTrackColor: Color = colorSurfaceVariant,
    val checkboxColor: Color = colorPrimary,
    val radioButtonColor: Color = colorPrimary,
    val sliderActiveColor: Color = colorPrimary,
    val sliderInactiveColor: Color = colorSurfaceVariant,
    val progressIndicatorColor: Color = colorPrimary,
    val tooltipBackgroundColor: Color = colorSurfaceVariant,
    val tooltipTextColor: Color = textPrimary,
    // `colorPrimary`, not `colorError`: a badge is a neutral count or status, not a
    // destructive or error state. Painting it with the destructive accent gave that
    // colour a third meaning, which the one-action/one-destructive rule forbids. Note
    // [badgeTextColor] already pairs with primary. A theme that genuinely wants an
    // error badge should set this explicitly at the call site's theme, not globally.
    val badgeBackgroundColor: Color = colorPrimary,
    val badgeTextColor: Color = colorOnPrimary,

    // ---- Typography ----
    val headingFontFamily: FontFamily = FontFamily.SansSerif,
    val bodyFontFamily: FontFamily = FontFamily.SansSerif,
    val labelFontFamily: FontFamily = FontFamily.SansSerif,
    val headingFontSize: TextUnit = 24.sp,
    val bodyFontSize: TextUnit = 16.sp,
    val labelFontSize: TextUnit = 13.sp,
    val headingFontWeight: FontWeight = FontWeight.SemiBold,
    /** Extra tracking; Swiss/Typographic themes widen this, Brutalism tightens it. */
    val headingLetterSpacing: TextUnit = 0.sp,

    // ---- Shape / elevation ----
    val cornerRadiusSmall: Dp = 4.dp,
    val cornerRadiusMedium: Dp = 12.dp,
    val cornerRadiusLarge: Dp = 20.dp,
    val buttonCornerRadius: Dp = 12.dp,
    val cardElevation: Dp = 0.dp,
    val buttonElevation: Dp = 0.dp,
    val borderWidth: Dp = 1.dp,
    /**
     * Separator thickness. Its own token rather than a second reading of [borderWidth],
     * because a theme can legitimately want no card outline and still want a rule between
     * rows - Neumorphism does exactly that, and reusing borderWidth made its dividers
     * disappear. Defaults to the border width so themes that want one line stay terse.
     */
    val dividerThickness: Dp = borderWidth.coerceAtLeast(1.dp),

    // ---- Style enums ----
    val buttonStyle: ButtonStyle = ButtonStyle.FILLED,
    val iconStyle: IconStyle = IconStyle.OUTLINE,
    val spacingDensity: SpacingDensity = SpacingDensity.NORMAL,
    val backgroundPattern: BackgroundPattern = BackgroundPattern.NONE,
    val animationStyle: AnimationStyle = AnimationStyle.SUBTLE,
    val decorativeOverlay: DecorativeOverlay = DecorativeOverlay.NONE,
    val surfaceStyle: SurfaceStyle = SurfaceStyle.FLAT,

    /**
     * Page backdrop, painted instead of [colorBackground] when set. Two or three stops;
     * this is the whole identity of Aurora and Glassmorphism, which have no business
     * looking like a flat fill. Null on every restrained theme.
     */
    val backgroundGradient: List<Color>? = null,

    /**
     * Light spill under primary actions, for the themes whose accent is meant to read as
     * emitted rather than printed - neon on Cyberpunk, aurora green on Aurora. Null means
     * no glow, which is the correct answer for most themes.
     */
    val glowColor: Color? = null,

    /**
     * True when the palette is strictly two-tone and no token may be alpha-dimmed —
     * an alpha'd black composites to grey, which DEFAULT forbids. Components check this
     * before reaching for opacity and use type, border, or an icon cue instead.
     */
    val allowsAlphaDimming: Boolean = true
)
