package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.labelTextStyle
import com.helium4.theme.spacing

/** A single row: optional leading/trailing slots, a title and an optional subtitle. */
@Composable
public fun AppListItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val theme = LocalAppTheme.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.md)
    ) {
        if (leading != null) {
            Box(contentAlignment = Alignment.Center) { leading() }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(theme.spacing.xs)
        ) {
            Text(
                text = title,
                style = theme.bodyTextStyle.copy(color = theme.textPrimary)
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = theme.labelTextStyle.copy(color = theme.textSecondary)
                )
            }
        }

        if (trailing != null) {
            Box(contentAlignment = Alignment.Center) { trailing() }
        }
    }
}
