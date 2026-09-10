package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.dim
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface
import com.helium4.theme.usesStructuralDisabledState

/**
 * Switch built from primitives rather than M3's, so the off state is distinguishable by
 * thumb position, border weight and a glyph — not by fill color alone.
 */
@Composable
public fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null
) {
    val theme = LocalAppTheme.current

    val thumbColor = if (enabled) theme.switchThumbColor else theme.dim(theme.switchThumbColor)
    val trackFill = if (checked) theme.switchTrackColor else theme.colorBackground
    val borderColor = if (enabled) theme.colorBorder else theme.dim(theme.colorBorder)
    // Two-tone themes can't dim, so disabled is signalled by lighter type weight instead.
    val structural = theme.usesStructuralDisabledState

    Row(
        modifier = modifier
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(vertical = theme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.sm)
    ) {
        Box(
            modifier = Modifier
                // Structural control size.
                .size(width = 52.dp, height = 30.dp)
                .themedSurface(
                    theme = theme,
                    shape = CircleShape,
                    fill = trackFill,
                    borderColor = borderColor
                )
                .padding(theme.spacing.xs),
            contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .themedSurface(
                        theme = theme,
                        shape = CircleShape,
                        fill = thumbColor,
                        borderColor = borderColor,
                        elevation = theme.buttonElevation
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (checked) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null,
                    tint = if (checked) theme.colorOnPrimary else theme.textPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        if (label != null) {
            Text(
                text = label,
                style = theme.bodyTextStyle.copy(
                    color = if (enabled) theme.textPrimary else theme.dim(theme.textPrimary)
                ),
                fontWeight = if (structural && !enabled) FontWeight.Light else FontWeight.Normal
            )
        }
    }
}
