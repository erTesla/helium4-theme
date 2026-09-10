package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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

/** Radio button: selection is shown by an inner dot appearing, not by a color swap alone. */
@Composable
public fun AppRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true
) {
    val theme = LocalAppTheme.current
    val structural = theme.usesStructuralDisabledState

    val markColor = if (enabled) theme.radioButtonColor else theme.dim(theme.radioButtonColor)
    val borderColor = if (enabled) theme.colorBorder else theme.dim(theme.colorBorder)

    Row(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
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
                    shape = CircleShape,
                    fill = theme.colorBackground,
                    borderColor = if (selected) markColor else borderColor
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(11.dp)
                        .themedSurface(
                            theme = theme,
                            shape = CircleShape,
                            fill = markColor,
                            borderColor = markColor
                        )
                )
            }
        }

        if (label != null) {
            Text(
                text = label,
                style = theme.bodyTextStyle.copy(
                    color = if (enabled) theme.textPrimary else theme.dim(theme.textPrimary)
                ),
                fontWeight = when {
                    structural && !enabled -> FontWeight.Light
                    selected -> FontWeight.SemiBold
                    else -> FontWeight.Normal
                }
            )
        }
    }
}
