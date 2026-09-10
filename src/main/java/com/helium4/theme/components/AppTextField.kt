package com.helium4.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.SurfaceStyle
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.dim
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface
import com.helium4.theme.usesStructuralDisabledState

/**
 * Single text input. The error state is never color-only — it always surfaces an icon and
 * supporting text alongside the error tint.
 */
@Composable
public fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false
) {
    val theme = LocalAppTheme.current

    val borderColor = when {
        isError -> theme.colorError
        enabled -> theme.inputFieldBorderColor
        else -> theme.dim(theme.inputFieldBorderColor)
    }
    val textColor = if (enabled) theme.inputFieldTextColor else theme.dim(theme.inputFieldTextColor)
    val borderWidth = when {
        // An error is never signalled by colour alone, so on a borderless theme the
        // error state is where a border gets forced into existence.
        isError -> (theme.borderWidth * 2).coerceAtLeast(1.dp)
        !enabled && theme.usesStructuralDisabledState -> theme.borderWidth / 2
        else -> theme.borderWidth
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(theme.spacing.xs)
    ) {
        if (label != null) {
            Text(
                text = if (!enabled && theme.usesStructuralDisabledState) "$label (read-only)" else label,
                style = theme.labelTextStyle.copy(
                    color = if (isError) theme.colorError else theme.textSecondary
                ),
                fontWeight = if (isError) FontWeight.SemiBold else FontWeight.Normal
            )
        }

        Box(
            modifier = Modifier
                .themedSurface(
                    theme = theme,
                    shape = theme.shapes.small,
                    fill = theme.inputFieldBackground,
                    borderColor = borderColor,
                    // A neumorphic field is engraved, not raised - and since that theme is
                    // one tone with no borders, the inset shadow is the only thing that
                    // makes this read as somewhere you can type.
                    inset = theme.surfaceStyle == SurfaceStyle.NEUMORPHIC
                )
                // An input must always read as an input, so the border is explicit here even
                // on surface styles that draw none. Width also carries the error/disabled state.
                .border(borderWidth, borderColor, theme.shapes.small)
                .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                singleLine = singleLine,
                textStyle = theme.bodyTextStyle.copy(color = textColor),
                cursorBrush = SolidColor(if (isError) theme.colorError else theme.colorPrimary),
                decorationBox = { inner ->
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = theme.bodyTextStyle.copy(color = theme.dim(theme.textSecondary))
                        )
                    }
                    inner()
                }
            )
        }

        if (isError) {
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = Arrangement.spacedBy(theme.spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = theme.colorError,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = label?.let { "$it is invalid" } ?: "Invalid input",
                    style = theme.labelTextStyle.copy(color = theme.colorError)
                )
            }
        }
    }
}
