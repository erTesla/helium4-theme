package com.helium4.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Resolved spacing scale for the active [SpacingDensity]. */
@Immutable
public data class AppSpacing(
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp
) {
    val screenPadding: PaddingValues get() = PaddingValues(horizontal = md, vertical = sm)
}

/** Spacing derived from the theme's density, so COMPACT themes tighten everything at once. */
public val AppTheme.spacing: AppSpacing
    get() = when (spacingDensity) {
        SpacingDensity.COMPACT -> AppSpacing(2.dp, 4.dp, 8.dp, 12.dp, 20.dp)
        SpacingDensity.NORMAL -> AppSpacing(4.dp, 8.dp, 16.dp, 24.dp, 40.dp)
        SpacingDensity.COMFORTABLE -> AppSpacing(6.dp, 12.dp, 24.dp, 36.dp, 56.dp)
    }

/** Shapes built from the theme's corner tokens. Components must not build their own. */
@Immutable
public data class AppShapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val button: Shape
)

public val AppTheme.shapes: AppShapes
    get() = AppShapes(
        small = RoundedCornerShape(cornerRadiusSmall),
        medium = RoundedCornerShape(cornerRadiusMedium),
        large = RoundedCornerShape(cornerRadiusLarge),
        button = RoundedCornerShape(buttonCornerRadius)
    )

/** Convenience accessor so composables can write `appTheme` instead of the local name. */
public val appTheme: AppTheme
    @Composable @ReadOnlyComposable get() = LocalAppTheme.current

/**
 * Dims a color for a disabled/secondary state — but only when the theme allows it.
 *
 * Two-tone themes (DEFAULT) forbid alpha because an alpha'd black composites to grey.
 * There the color is returned untouched and the caller must convey the state some other
 * way (type weight, a border change, an explicit icon or label).
 */
public fun AppTheme.dim(color: Color, alpha: Float = 0.5f): Color =
    if (allowsAlphaDimming) color.copy(alpha = alpha) else color

/**
 * Whether a component should signal "disabled" structurally rather than with opacity.
 * True exactly when the theme bans alpha dimming.
 */
public val AppTheme.usesStructuralDisabledState: Boolean get() = !allowsAlphaDimming

/** Animation durations scale with the theme's personality; NONE means no transition. */
public val AppTheme.motionDurationMs: Int
    get() = when (animationStyle) {
        AnimationStyle.NONE -> 0
        AnimationStyle.SUBTLE -> 150
        AnimationStyle.PLAYFUL -> 400
        AnimationStyle.DRAMATIC -> 700
    }

/**
 * True when the surface fill is translucent, so the backdrop needs something
 * out-of-focus behind it for the glass to diffuse.
 */
public val AppTheme.isTranslucentSurface: Boolean get() = surfaceStyle == SurfaceStyle.GLASS

/**
 * The one place surface styling is implemented. Every card, sheet, field and dialog
 * delegates here instead of reimplementing shadow logic, so adding a theme style is a
 * single-file change.
 *
 * Three rules every branch below obeys, because breaking any of them is what makes a
 * themed surface look broken rather than styled:
 *  - **Depth comes from shadow, not from a colour change.** A neumorphic surface is the
 *    *same colour* as the page behind it; if you can only tell a card from the background
 *    because it is a lighter grey, the style has been faked.
 *  - **Nothing is painted outside the shape.** Sheens and highlights are clipped to the
 *    resolved shape's outline, so a 24dp-radius card never shows square corners.
 *  - **Nothing blurs its own content.** A translucent panel is drawn as a translucent
 *    panel; the text on it stays sharp.
 *
 * @param fill overrides the surface fill; defaults to the theme's card background.
 * @param borderColor overrides the border; defaults to the theme's card border.
 * @param inset renders the surface pressed *into* the page instead of raised out of it,
 *   by swapping the light and dark shadows. This is what a held neumorphic button and a
 *   neumorphic text field both want; styles without a dual shadow ignore it.
 */
public fun Modifier.themedSurface(
    theme: AppTheme,
    shape: Shape? = null,
    fill: Color? = null,
    borderColor: Color? = null,
    elevation: Dp? = null,
    inset: Boolean = false
): Modifier {
    val resolvedShape = shape ?: theme.shapes.medium
    val resolvedFill = fill ?: theme.cardBackgroundColor
    val resolvedBorder = borderColor ?: theme.cardBorderColor
    val resolvedElevation = elevation ?: theme.cardElevation
    // Styles whose entire identity is an outline still need one when the theme leaves
    // borderWidth at 0. Everywhere else the token is honoured exactly as authored.
    val hairline = theme.borderWidth.coerceAtLeast(1.dp)

    return when (theme.surfaceStyle) {
        // DEFAULT: no fill and no elevation - the 1dp border is the *only* thing that
        // makes a card a card, because there are no greys available to fill it with.
        SurfaceStyle.BORDER_ONLY -> this
            .border(hairline, resolvedBorder, resolvedShape)

        // Flat still means *outlined* on the themes that author a border width: Swiss,
        // Bento, Memphis and Cyberpunk are hairline designs, not fill-only ones, and
        // dropping their border was what flattened them all into the same plain block.
        SurfaceStyle.FLAT -> this
            .ambientShadow(theme, resolvedShape, resolvedElevation)
            .background(resolvedFill, resolvedShape)
            .themedBorder(theme, resolvedBorder, resolvedShape)

        // The real thing: two tinted shadows, light from the top-left and dark to the
        // bottom-right, both drawn *outside* the shape. The fill is the page colour, so
        // the shadows are the entire reason the surface reads as a surface - which is why
        // this branch draws no border and ignores cardElevation.
        SurfaceStyle.NEUMORPHIC -> {
            val (light, dark) = neumorphicShadows(theme.isDark)
            // Elevation is the extrusion *distance* here rather than a separate drop
            // shadow, so a 40dp icon button can sit closer to the page than a card.
            val raised = if (resolvedElevation > 0.dp) resolvedElevation else NEUMORPHIC_RAISED_OFFSET
            val offset = if (inset) raised / 2 else raised
            val blur = offset * NEUMORPHIC_BLUR_RATIO
            this
                .softShadow(dark, resolvedShape, blur, offset, offset)
                .softShadow(light, resolvedShape, blur, -offset, -offset)
                .background(resolvedFill, resolvedShape)
        }

        // Translucent panel. The fill token already carries the alpha, so all this adds
        // is the diagonal sheen and the bright hairline edge that read as glass. No
        // render-effect blur here: blurring this layer also blurs the label on top of it.
        SurfaceStyle.GLASS -> this
            .background(resolvedFill, resolvedShape)
            .drawBehind { drawGlassSheen(resolvedShape, theme.isDark) }
            .border(hairline, resolvedBorder, resolvedShape)

        // Puffy clay: a soft shadow tinted with the theme's own border colour rather than
        // black, plus an inner highlight along the top.
        SurfaceStyle.CLAY -> this
            .softShadow(resolvedBorder, resolvedShape, resolvedElevation * 2, offsetY = resolvedElevation)
            .background(resolvedFill, resolvedShape)
            .drawBehind { drawClayHighlight(resolvedShape, theme.isDark) }

        // Brutalism: a solid, un-blurred copy of the shape offset down-right. Drawn from
        // the shape's own outline so it tracks whatever corner radius the theme sets.
        SurfaceStyle.HARD_SHADOW -> this
            .drawBehind { drawHardShadow(resolvedShape, resolvedBorder, resolvedElevation.toPx()) }
            .background(resolvedFill, resolvedShape)
            .border(hairline, resolvedBorder, resolvedShape)

        // Skeuomorphic bevel: a material fill inside a hard border, lit from the top-left.
        // Unlike NEUMORPHIC this one *is* an inner gradient, because a skeuomorphic panel
        // is pretending to be a lit physical object rather than an extruded one.
        SurfaceStyle.EMBOSSED -> this
            .ambientShadow(theme, resolvedShape, resolvedElevation)
            .background(resolvedFill, resolvedShape)
            .drawBehind { drawBevel(resolvedShape, theme.isDark, strength = 0.5f) }
            .themedBorder(theme, resolvedBorder, resolvedShape)
    }
}

/** Applies the theme's authored border, or nothing at all when it authored none. */
private fun Modifier.themedBorder(theme: AppTheme, color: Color, shape: Shape): Modifier =
    if (theme.borderWidth > 0.dp) border(theme.borderWidth, color, shape) else this

/**
 * The generic elevation shadow, as a tint rather than the platform's fixed black. Drawn
 * through [softShadow] so every style in this file casts shadows the same way.
 */
private fun Modifier.ambientShadow(theme: AppTheme, shape: Shape, elevation: Dp): Modifier =
    softShadow(
        color = Color.Black.copy(alpha = if (theme.isDark) 0.45f else 0.20f),
        shape = shape,
        blurRadius = elevation * 2,
        offsetY = elevation
    )

/**
 * A blurred drop shadow in an arbitrary colour, offsettable in both axes.
 *
 * Compose's own `Modifier.shadow` can tint via ambient/spot colours but cannot be offset,
 * and offset is the whole point of a dual neumorphic shadow. So this drops to the
 * framework paint's shadow layer, which can do both.
 */
public fun Modifier.softShadow(
    color: Color,
    shape: Shape,
    blurRadius: Dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
): Modifier = drawBehind {
    val blur = blurRadius.toPx()
    if (blur <= 0f || color.alpha == 0f) return@drawBehind
    val radius = cornerRadiusOf(shape)
    drawIntoCanvas { canvas ->
        val paint = Paint()
        paint.asFrameworkPaint().apply {
            isAntiAlias = true
            // The shape itself must be invisible - only its shadow layer should paint.
            this.color = android.graphics.Color.TRANSPARENT
            setShadowLayer(blur, offsetX.toPx(), offsetY.toPx(), color.toArgb())
        }
        canvas.drawRoundRect(0f, 0f, size.width, size.height, radius, radius, paint)
    }
}

/**
 * Light shadow then dark shadow for the neumorphic pair. In light mode the light one is
 * plain white; in dark mode both are slate, because white on a dark surface reads as a
 * glow rather than as a lit edge.
 */
internal fun neumorphicShadows(dark: Boolean): Pair<Color, Color> =
    if (dark) {
        Color(0xFF4A5568) to Color(0xFF1A202C)
    } else {
        Color.White.copy(alpha = 0.70f) to Color(0xFFA3B1C6).copy(alpha = 0.50f)
    }

private val NEUMORPHIC_RAISED_OFFSET = 6.dp
private const val NEUMORPHIC_BLUR_RATIO = 1.7f

/** The shape's corner radius, for the round-rect the shadow helpers paint. */
private fun DrawScope.cornerRadiusOf(shape: Shape): Float =
    when (val outline = shape.createOutline(size, layoutDirection, this)) {
        is Outline.Rounded -> outline.roundRect.topLeftCornerRadius.x
        else -> 0f
    }

/**
 * The resolved shape's own outline as a [Path], so every overlay below can be clipped to
 * it instead of painting a square gradient across rounded corners.
 */
private fun DrawScope.shapePath(shape: Shape): Path {
    val outline = shape.createOutline(size, layoutDirection, this)
    return Path().apply {
        when (outline) {
            is Outline.Rectangle -> addRect(outline.rect)
            is Outline.Rounded -> addRoundRect(outline.roundRect)
            is Outline.Generic -> addPath(outline.path)
        }
    }
}

/**
 * Light from the top-left, shade to the bottom-right, clipped to the shape. An *inner*
 * gradient, so only EMBOSSED uses it - see the note on that branch.
 */
private fun DrawScope.drawBevel(shape: Shape, isDark: Boolean, strength: Float) {
    clipPath(shapePath(shape)) {
        val light = Color.White.copy(alpha = (if (isDark) 0.10f else 0.55f) * strength)
        val shade = Color.Black.copy(alpha = (if (isDark) 0.30f else 0.10f) * strength)
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(light, Color.Transparent),
                start = Offset.Zero,
                end = Offset(size.width * 0.65f, size.height * 0.65f)
            )
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, shade),
                start = Offset(size.width * 0.35f, size.height * 0.35f),
                end = Offset(size.width, size.height)
            )
        )
    }
}

private fun DrawScope.drawGlassSheen(shape: Shape, isDark: Boolean) {
    clipPath(shapePath(shape)) {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = if (isDark) 0.14f else 0.35f),
                    Color.Transparent
                ),
                start = Offset.Zero,
                end = Offset(size.width * 0.8f, size.height)
            )
        )
    }
}

private fun DrawScope.drawClayHighlight(shape: Shape, isDark: Boolean) {
    clipPath(shapePath(shape)) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = if (isDark) 0.08f else 0.40f),
                    Color.Transparent
                ),
                endY = size.height * 0.45f
            )
        )
    }
}

/** Solid offset copy of the shape. Deliberately un-blurred - that is the entire look. */
private fun DrawScope.drawHardShadow(shape: Shape, color: Color, offset: Float) {
    if (offset <= 0f) return
    translate(offset, offset) { drawPath(shapePath(shape), color) }
}

/** Body text style built from tokens. Components never construct [TextStyle] themselves. */
public val AppTheme.bodyTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = bodyFontFamily,
        fontSize = bodyFontSize,
        color = textPrimary
    )

public val AppTheme.headingTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = headingFontFamily,
        fontSize = headingFontSize,
        fontWeight = headingFontWeight,
        letterSpacing = headingLetterSpacing,
        color = textPrimary
    )

public val AppTheme.labelTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = labelFontFamily,
        fontSize = labelFontSize,
        color = textSecondary
    )
