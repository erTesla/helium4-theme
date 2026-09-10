package com.helium4.theme.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes

/** Wraps [content] in a long-press tooltip using the theme's tooltip tokens. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun AppTooltip(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val theme = LocalAppTheme.current

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        tooltip = {
            PlainTooltip(
                shape = theme.shapes.small,
                containerColor = theme.tooltipBackgroundColor,
                contentColor = theme.tooltipTextColor
            ) {
                Text(
                    text = text,
                    style = theme.labelTextStyle.copy(color = theme.tooltipTextColor)
                )
            }
        },
        state = rememberTooltipState(),
        modifier = modifier,
        content = content
    )
}
