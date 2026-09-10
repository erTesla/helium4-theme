package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/**
 * A content container. All separation from the background is delegated to
 * `Modifier.themedSurface`, so a BORDER_ONLY theme correctly renders an unfilled card.
 */
@Composable
public fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val theme = LocalAppTheme.current
    Column(
        modifier = modifier
            .themedSurface(
                theme = theme,
                shape = theme.shapes.medium,
                fill = theme.cardBackgroundColor,
                borderColor = theme.cardBorderColor,
                elevation = theme.cardElevation
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(theme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(theme.spacing.sm),
        content = content
    )
}
