package com.helium4.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/** Floating action button. Always carries a [contentDescription] since it is icon-only. */
@Composable
public fun AppFAB(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val theme = LocalAppTheme.current
    Box(
        modifier = modifier
            .themedSurface(
                theme = theme,
                shape = theme.shapes.large,
                fill = theme.fabBackgroundColor,
                borderColor = theme.fabIconColor,
                elevation = theme.buttonElevation
            )
            .clickable(onClick = onClick)
            .padding(theme.spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = theme.fabIconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}
