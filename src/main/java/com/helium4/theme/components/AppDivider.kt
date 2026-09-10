package com.helium4.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.helium4.theme.LocalAppTheme

/**
 * Hairline separator. Thickness is [com.helium4.theme.AppTheme.dividerThickness], which
 * is deliberately *not* the card border width: Minimalism wants a 0.5dp rule, Brutalism a
 * 2dp one, and Neumorphism wants a rule while having no card outline at all.
 */
@Composable
public fun AppDivider(modifier: Modifier = Modifier) {
    val theme = LocalAppTheme.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(theme.dividerThickness)
            .background(theme.dividerColor)
    )
}
