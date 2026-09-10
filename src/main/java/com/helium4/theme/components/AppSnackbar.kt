package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/** Transient message strip with an optional inline action. */
@Composable
public fun AppSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    val theme = LocalAppTheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .themedSurface(
                theme = theme,
                shape = theme.shapes.small,
                fill = theme.snackbarBackground,
                borderColor = theme.snackbarTextColor
            )
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.md)
    ) {
        Text(
            text = message,
            style = theme.bodyTextStyle.copy(color = theme.snackbarTextColor),
            modifier = Modifier.weight(1f)
        )
        if (actionLabel != null && onAction != null) {
            Text(
                text = actionLabel,
                style = theme.labelTextStyle.copy(color = theme.colorPrimary),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = onAction)
                    .padding(theme.spacing.xs)
            )
        }
    }
}
