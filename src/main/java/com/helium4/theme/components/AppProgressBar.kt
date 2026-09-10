package com.helium4.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.shapes
import com.helium4.theme.themedSurface

/**
 * Linear progress. A null [progress] renders the indeterminate variant.
 * The track is a themed surface so BORDER_ONLY themes still show the extent.
 */
@Composable
public fun AppProgressBar(
    progress: Float? = null,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current

    val barModifier = modifier
        .fillMaxWidth()
        // Structural bar thickness.
        .height(4.dp)
        .themedSurface(
            theme = theme,
            shape = theme.shapes.small,
            fill = theme.colorSurfaceVariant,
            borderColor = theme.colorBorder
        )

    if (progress == null) {
        LinearProgressIndicator(
            modifier = barModifier,
            color = theme.progressIndicatorColor,
            trackColor = theme.colorSurfaceVariant
        )
    } else {
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = barModifier,
            color = theme.progressIndicatorColor,
            trackColor = theme.colorSurfaceVariant
        )
    }
}
