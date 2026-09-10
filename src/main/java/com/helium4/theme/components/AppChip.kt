package com.helium4.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/**
 * Selectable filter chip. Selection is carried by three cues at once — a thicker accent
 * border, a heavier label, and a check icon — so it never relies on fill color alone.
 */
@Composable
public fun AppChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    val theme = LocalAppTheme.current
    val contentColor = if (selected) theme.colorPrimary else theme.chipTextColor
    val borderColor = if (selected) theme.colorPrimary else theme.colorBorder
    val borderWidth = if (selected) theme.borderWidth * 2 else theme.borderWidth

    Row(
        modifier = modifier
            .themedSurface(
                theme = theme,
                shape = theme.shapes.small,
                fill = theme.chipBackgroundColor,
                borderColor = borderColor
            )
            .border(borderWidth, borderColor, theme.shapes.small)
            .clickable(onClick = onClick)
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.xs)
    ) {
        val icon = leadingIcon ?: if (selected) Icons.Filled.Check else null
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = label,
            style = theme.labelTextStyle.copy(color = contentColor),
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
