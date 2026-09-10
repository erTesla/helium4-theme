package com.helium4.theme.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helium4.theme.AnimationStyle
import com.helium4.theme.ButtonStyle
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.SurfaceStyle
import com.helium4.theme.dim
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.softShadow
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface
import com.helium4.theme.usesStructuralDisabledState

/** Semantic role of a button. DESTRUCTIVE always renders a text label, never icon-only. */
public enum class AppButtonVariant { PRIMARY, SECONDARY, GHOST, DESTRUCTIVE }

/**
 * The single button in the app.
 *
 * Container rendering is driven by [com.helium4.theme.AppTheme.buttonStyle], the
 * semantic color by [style]. Disabled state is conveyed structurally (thinner border,
 * lighter weight) on themes that ban alpha dimming.
 */
@Composable
public fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppButtonVariant = AppButtonVariant.PRIMARY,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    val theme = LocalAppTheme.current
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    // Semantic pair: what the container is filled with, and what sits on top of it.
    val accent = when (style) {
        AppButtonVariant.PRIMARY -> theme.colorPrimary
        AppButtonVariant.SECONDARY -> theme.colorSecondary
        AppButtonVariant.GHOST -> theme.textPrimary
        AppButtonVariant.DESTRUCTIVE -> theme.colorError
    }
    val onAccent = when (style) {
        AppButtonVariant.GHOST -> theme.colorBackground
        else -> theme.colorOnPrimary
    }

    // Brutalism inverts on press instead of using a ripple/alpha state.
    val invertOnPress = theme.surfaceStyle == SurfaceStyle.HARD_SHADOW && pressed && enabled

    // Everything else dips very slightly. Themes that opted out of animation get the
    // instant inversion above instead, which is the point of opting out.
    val pressScale by animateFloatAsState(
        targetValue = if (pressed && enabled && theme.animationStyle != AnimationStyle.NONE) {
            PRESSED_SCALE
        } else {
            1f
        },
        animationSpec = tween(durationMillis = PRESS_MILLIS),
        label = "buttonPressScale"
    )

    val hasContainer = theme.buttonStyle != ButtonStyle.GHOST && style != AppButtonVariant.GHOST
    // BEVELED hands the container to the theme's own surfaceStyle, so a neumorphic or
    // glass theme extrudes its buttons the same way it extrudes its cards.
    val sculpted = hasContainer && theme.buttonStyle == ButtonStyle.BEVELED
    // Sculpted-from-the-surface themes lift the button *out of the background* rather
    // than filling it with the accent - so the accent moves to the label instead. A
    // neumorphic button filled with primary blue is not neumorphism, it is a blue box.
    val sculptedFromSurface = sculpted && theme.surfaceStyle in SURFACE_SCULPTED_STYLES
    val filledContainer = hasContainer &&
        (theme.buttonStyle == ButtonStyle.FILLED || sculpted)

    val baseContainer: Color = when {
        !hasContainer -> Color.Transparent
        sculptedFromSurface -> theme.colorSurface
        filledContainer -> accent
        else -> Color.Transparent
    }
    val baseContent: Color = when {
        sculptedFromSurface -> accent
        filledContainer -> onAccent
        else -> accent
    }

    val containerColor = when {
        invertOnPress -> if (filledContainer) baseContent else accent
        enabled -> baseContainer
        baseContainer == Color.Transparent -> Color.Transparent
        else -> theme.dim(baseContainer)
    }
    val contentColor = when {
        invertOnPress -> if (filledContainer) baseContainer else theme.colorOnPrimary
        enabled -> baseContent
        else -> theme.dim(baseContent)
    }

    val outlined = hasContainer && !filledContainer
    // Structural disabled cue: a hairline border where a full-width one would be.
    val borderWidth = when {
        !outlined -> theme.borderWidth
        enabled -> theme.borderWidth
        theme.usesStructuralDisabledState -> theme.borderWidth / 2
        else -> theme.borderWidth
    }
    val fontWeight = if (!enabled && theme.usesStructuralDisabledState) {
        FontWeight.Light
    } else {
        FontWeight.Medium
    }

    val container = if (sculpted) {
        // One shared implementation of bevels, sheens and offset blocks - the button
        // never rolls its own shadow logic. Holding it presses the surface *into* the
        // page, which on a neumorphic theme is the only press affordance there is: with
        // one tone and no border, there is nothing else to change.
        Modifier
            .themedSurface(
                theme = theme,
                shape = theme.shapes.button,
                fill = containerColor,
                borderColor = if (sculptedFromSurface) theme.colorBorder else contentColor,
                elevation = theme.buttonElevation,
                inset = pressed && enabled
            )
            // An accent-filled bevel gets a lit face: brighter at the top, darker at the
            // bottom, flattening while held. Surface-sculpted themes skip it - their
            // extrusion is the shadow, and a gradient here would tint the one tone.
            .then(
                if (!sculptedFromSurface && enabled) {
                    Modifier.background(
                        brush = litFace(containerColor, pressed),
                        shape = theme.shapes.button
                    )
                } else {
                    Modifier
                }
            )
    } else {
        Modifier
            .then(
                if (containerColor != Color.Transparent) {
                    Modifier.background(containerColor, theme.shapes.button)
                } else {
                    Modifier
                }
            )
            .then(
                if (outlined) {
                    Modifier.border(borderWidth, contentColor, theme.shapes.button)
                } else {
                    Modifier
                }
            )
    }

    // A themed glow sits under everything else so it reads as light spilling from the
    // control, not as another border. Primary actions only - a page where every button
    // glows is a page where the glow has stopped meaning "this is the main action".
    val glow = theme.glowColor
    val glowModifier = if (glow != null && style == AppButtonVariant.PRIMARY && enabled) {
        Modifier.softShadow(glow.copy(alpha = 0.55f), theme.shapes.button, GLOW_BLUR)
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .scale(pressScale)
            .then(glowModifier)
            .then(container)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.sm, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Destructive actions are never color-only: they carry an icon *and* the label.
        val icon = leadingIcon ?: if (style == AppButtonVariant.DESTRUCTIVE) Icons.Filled.Delete else null
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = if (!enabled && theme.usesStructuralDisabledState) "$text (unavailable)" else text,
            style = theme.labelTextStyle.copy(color = contentColor),
            fontWeight = fontWeight
        )
    }
}

/**
 * Surface styles that sculpt a control out of the background itself. A BEVELED button on
 * one of these takes the surface fill and an accent label; every other style fills with
 * the accent.
 */
private val SURFACE_SCULPTED_STYLES = setOf(
    SurfaceStyle.NEUMORPHIC,
    SurfaceStyle.GLASS,
    SurfaceStyle.EMBOSSED
)

/** Lighter at the top, darker at the bottom - reads as a lit, extruded face. */
private fun litFace(base: Color, pressed: Boolean): Brush = Brush.verticalGradient(
    listOf(
        lerp(base, Color.White, if (pressed) 0.05f else 0.30f),
        lerp(base, Color.Black, if (pressed) 0.30f else 0.18f)
    )
)

private const val PRESSED_SCALE = 0.97f
private const val PRESS_MILLIS = 120

private val GLOW_BLUR = 14.dp
