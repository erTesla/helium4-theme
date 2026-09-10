package com.helium4.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/**
 * Small count/status badge. Always textual, so it never conveys meaning by color alone.
 *
 * Uses the theme's *action* accent, not its error accent: a badge is a neutral count, and
 * painting it with the destructive colour would give that colour a third meaning.
 */
@Composable
public fun AppBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current
    Box(
        modifier = modifier
            .themedSurface(
                theme = theme,
                shape = theme.shapes.small,
                fill = theme.badgeBackgroundColor,
                borderColor = theme.badgeBackgroundColor
            )
            // The accent fill is re-applied on top, the same way AppButton does it for a
            // filled container. SurfaceStyle.BORDER_ONLY deliberately drops `fill` -
            // correct for a card on a theme with no greys, but fatal here, because
            // badgeTextColor is chosen to contrast with the *accent*, not with the page.
            // Without this the label was white-on-white in every BORDER_ONLY theme.
            .background(theme.badgeBackgroundColor, theme.shapes.small)
            .padding(horizontal = theme.spacing.sm, vertical = theme.spacing.xs),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = theme.labelTextStyle.copy(color = theme.badgeTextColor),
            fontWeight = FontWeight.SemiBold
        )
    }
}
