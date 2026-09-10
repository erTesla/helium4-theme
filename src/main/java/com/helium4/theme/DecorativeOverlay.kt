package com.helium4.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.clearAndSetSemantics
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/**
 * Animated particles drawn above all content.
 *
 * Two things make this safe to layer over a live UI:
 *  - it attaches no pointer-input modifier, so touches pass straight through to the
 *    content underneath;
 *  - it is marked [clearAndSetSemantics], so TalkBack never announces the particles.
 *
 * Particle counts and alpha are capped low so text below stays readable, and the whole
 * field is driven by one frame clock rather than N per-particle animations.
 */
@Composable
public fun DecorativeOverlayLayer(
    overlay: DecorativeOverlay,
    animationStyle: AnimationStyle,
    theme: AppTheme,
    modifier: Modifier = Modifier
) {
    if (overlay == DecorativeOverlay.NONE || animationStyle == AnimationStyle.NONE) return

    val count = particleCount(animationStyle)
    val maxAlpha = maxAlpha(animationStyle)
    val particles = remember(overlay, count) { generateParticles(count, seed = overlay.ordinal) }
    val time by frameSeconds()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            // No pointerInput here on purpose: the overlay must never eat touches.
            .clearAndSetSemantics { }
    ) {
        particles.forEach { p ->
            when (overlay) {
                DecorativeOverlay.SNOWFLAKES -> drawSnowflake(p, time, maxAlpha)
                DecorativeOverlay.EGGS -> drawEgg(p, time, maxAlpha, theme)
                DecorativeOverlay.CRESCENTS -> drawCrescent(p, time, maxAlpha, theme)
                DecorativeOverlay.STARS -> drawStar(p, time, maxAlpha, theme)
                DecorativeOverlay.FLOWERS -> drawPetal(p, time, maxAlpha, theme)
                DecorativeOverlay.NONE -> Unit
            }
        }
    }
}

/**
 * Low-opacity texture drawn *behind* content. Same passthrough guarantees as the overlay:
 * no pointer input, no semantics.
 */
@Composable
public fun BackgroundPatternLayer(
    pattern: BackgroundPattern,
    theme: AppTheme,
    modifier: Modifier = Modifier
) {
    if (pattern == BackgroundPattern.NONE) return

    val tint = theme.colorOutline.copy(alpha = patternAlpha(theme.animationStyle))
    Canvas(modifier = modifier.fillMaxSize().clearAndSetSemantics { }) {
        when (pattern) {
            BackgroundPattern.GRID -> drawGrid(tint)
            BackgroundPattern.SCANLINE -> drawScanlines(tint)
            BackgroundPattern.GEOMETRIC -> drawGeometric(tint)
            BackgroundPattern.ORGANIC -> drawOrganic(tint)
            BackgroundPattern.NOISE -> drawNoise(tint)
            BackgroundPattern.NONE -> Unit
        }
    }
}

// ---------------------------------------------------------------------------
// Particle model
// ---------------------------------------------------------------------------

/**
 * Texture strength scales with the theme's animation budget, which is the closest thing
 * to a loudness dial the tokens have. A restrained theme keeps the pattern at the edge of
 * perception; a DRAMATIC one is meant to be seen.
 */
private fun patternAlpha(style: AnimationStyle): Float = when (style) {
    AnimationStyle.NONE, AnimationStyle.SUBTLE -> 0.06f
    AnimationStyle.PLAYFUL -> 0.10f
    AnimationStyle.DRAMATIC -> 0.16f
}
private const val MAX_PARTICLES = 25

/** Normalised (0..1) position plus per-particle variation, generated once and reused. */
private data class Particle(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float,
    val phase: Float
)

private fun particleCount(style: AnimationStyle): Int = when (style) {
    AnimationStyle.NONE -> 0
    AnimationStyle.SUBTLE -> 10
    AnimationStyle.PLAYFUL -> 18
    AnimationStyle.DRAMATIC -> MAX_PARTICLES
}

private fun maxAlpha(style: AnimationStyle): Float = when (style) {
    AnimationStyle.NONE -> 0f
    AnimationStyle.SUBTLE -> 0.12f
    AnimationStyle.PLAYFUL -> 0.20f
    AnimationStyle.DRAMATIC -> 0.25f
}

private fun generateParticles(count: Int, seed: Int): List<Particle> {
    val random = Random(seed * 31 + count)
    return List(count) {
        Particle(
            x = random.nextFloat(),
            y = random.nextFloat(),
            size = 0.4f + random.nextFloat() * 0.6f,
            speed = 0.3f + random.nextFloat() * 0.7f,
            phase = random.nextFloat() * 2f * PI.toFloat()
        )
    }
}

/** A single shared frame clock; far cheaper than one animation per particle. */
@Composable
private fun frameSeconds() = produceState(0f) {
    val start = withFrameMillis { it }
    while (true) {
        withFrameMillis { millis -> value = (millis - start) / 1000f }
    }
}

// ---------------------------------------------------------------------------
// Overlay drawing
// ---------------------------------------------------------------------------

/** Wraps a normalised coordinate into 0..1 so particles loop forever. */
private fun wrap(v: Float): Float = v - kotlin.math.floor(v)

private fun DrawScope.particleCenter(p: Particle, time: Float, fallSpeed: Float): Offset {
    val drift = sin(time * 0.5f * p.speed + p.phase) * 0.03f
    return Offset(
        x = wrap(p.x + drift) * size.width,
        y = wrap(p.y + time * fallSpeed * p.speed * 0.05f) * size.height
    )
}

private fun DrawScope.drawSnowflake(p: Particle, time: Float, alpha: Float) {
    val center = particleCenter(p, time, fallSpeed = 1f)
    val radius = p.size * size.minDimension * 0.008f
    // Snow is white by definition rather than by theme, so this one is not a token.
    drawCircle(Color.White.copy(alpha = alpha * p.size), radius, center)
}

private fun DrawScope.drawEgg(p: Particle, time: Float, alpha: Float, theme: AppTheme) {
    // Slow float rather than fall, biased toward the screen edges.
    val center = particleCenter(p, time, fallSpeed = 0.25f)
    val w = p.size * size.minDimension * 0.018f
    drawOval(
        color = theme.colorAccent.copy(alpha = alpha * p.size),
        topLeft = Offset(center.x - w, center.y - w * 1.35f),
        size = Size(w * 2f, w * 2.7f)
    )
}

private fun DrawScope.drawCrescent(p: Particle, time: Float, alpha: Float, theme: AppTheme) {
    val center = particleCenter(p, time, fallSpeed = 0.15f)
    val r = p.size * size.minDimension * 0.02f
    // A crescent is one disc with a second, offset disc subtracted from it.
    val outer = Path().apply { addOval(Rect(center, r)) }
    val inner = Path().apply { addOval(Rect(center + Offset(r * 0.45f, -r * 0.2f), r)) }
    val crescent = Path().apply { op(outer, inner, PathOperation.Difference) }
    drawPath(crescent, theme.colorAccent.copy(alpha = alpha * p.size))
}

private fun DrawScope.drawStar(p: Particle, time: Float, alpha: Float, theme: AppTheme) {
    // Twinkle: alpha pulses, position barely moves.
    val twinkle = (sin(time * 1.6f * p.speed + p.phase) + 1f) / 2f
    val center = Offset(p.x * size.width, p.y * size.height)
    val r = p.size * size.minDimension * 0.006f
    drawCircle(theme.colorAccent.copy(alpha = alpha * twinkle), r, center)
}

private fun DrawScope.drawPetal(p: Particle, time: Float, alpha: Float, theme: AppTheme) {
    val center = particleCenter(p, time, fallSpeed = 0.6f)
    val r = p.size * size.minDimension * 0.012f
    rotate(degrees = (time * 20f * p.speed + p.phase * 57f) % 360f, pivot = center) {
        drawOval(
            color = theme.colorAccent.copy(alpha = alpha * p.size),
            topLeft = Offset(center.x - r, center.y - r * 0.45f),
            size = Size(r * 2f, r * 0.9f)
        )
    }
}

// ---------------------------------------------------------------------------
// Background patterns
// ---------------------------------------------------------------------------

private fun DrawScope.drawGrid(tint: Color) {
    val step = size.minDimension / 12f
    var x = 0f
    while (x <= size.width) {
        drawLine(tint, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
        x += step
    }
    var y = 0f
    while (y <= size.height) {
        drawLine(tint, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
        y += step
    }
}

private fun DrawScope.drawScanlines(tint: Color) {
    var y = 0f
    while (y <= size.height) {
        drawLine(tint, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
        y += 4f
    }
}

private fun DrawScope.drawGeometric(tint: Color) {
    val step = size.minDimension / 6f
    var i = 0
    var y = 0f
    while (y <= size.height) {
        var x = if (i % 2 == 0) 0f else step / 2f
        while (x <= size.width) {
            if ((i + (x / step).toInt()) % 3 == 0) {
                drawCircle(tint, step * 0.22f, Offset(x, y), style = Stroke(width = 2f))
            } else {
                drawRect(tint, Offset(x - step * 0.15f, y - step * 0.15f), Size(step * 0.3f, step * 0.3f), style = Stroke(width = 2f))
            }
            x += step
        }
        y += step
        i++
    }
}

private fun DrawScope.drawOrganic(tint: Color) {
    // Soft overlapping blobs, no gradients — just large, faint circles.
    val random = Random(7)
    repeat(6) {
        drawCircle(
            color = tint,
            radius = size.minDimension * (0.25f + random.nextFloat() * 0.35f),
            center = Offset(random.nextFloat() * size.width, random.nextFloat() * size.height)
        )
    }
}

private fun DrawScope.drawNoise(tint: Color) {
    val random = Random(11)
    val dots = 600
    repeat(dots) {
        drawCircle(
            color = tint,
            radius = 1f,
            center = Offset(random.nextFloat() * size.width, random.nextFloat() * size.height)
        )
    }
}
