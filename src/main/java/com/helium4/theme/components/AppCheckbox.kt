package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.dim
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface
import com.helium4.theme.usesStructuralDisabledState

/**
 * Checkbox where the unchecked state is a bordered empty box and the checked state adds a
 * fill *and* a check glyph, so the difference never depends on color alone.
 */
@Composable
public fun AppCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true
) {
    val theme = LocalAppTheme.current
    val structural = theme.usesStructuralDisabledState

    val boxColor = if (enabled) theme.checkboxColor else theme.dim(theme.checkboxColor)
    val borderColor = if (enabled) theme.colorBorder else theme.dim(theme.colorBorder)

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
                .size(22.dp)
                .themedSurface(
                    theme = theme,
                    shape = theme.shapes.small,
                    fill = if (checked) boxColor else theme.colorBackground,
                    borderColor = if (checked) boxColor else borderColor
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = theme.colorOnPrimary,
                    modifier = Modifier.size(16.dp)
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
