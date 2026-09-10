package com.helium4.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.helium4.theme.ThemeId
import com.helium4.theme.AppTheme
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.ThemeRegistry
import com.helium4.theme.labelTextStyle
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/**
 * One theme's preview: its own background, primary and accent, plus its display name.
 *
 * The swatch renders in the *previewed* theme's colors while its frame follows the
 * *active* theme, so the picker reads consistently no matter what is currently applied.
 * Selection is marked by a check icon as well as the border, never by color alone.
 *
 * [label] and [selectedContentDescription] default to English because this library ships
 * no `res/`; a localizing consumer must pass its own strings.
 */
@Composable
public fun ThemeSwatchCard(
    themeId: ThemeId,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = themeId.displayName,
    selectedContentDescription: String = "Selected"
) {
    val active = LocalAppTheme.current
    val preview: AppTheme = ThemeRegistry.resolve(themeId, active.isDark)

    Column(
        modifier = modifier
            .themedSurface(
                theme = active,
                shape = active.shapes.medium,
                borderColor = if (selected) active.colorPrimary else active.cardBorderColor
            )
            .clickable(onClick = onClick)
            .padding(active.spacing.sm),
        verticalArrangement = Arrangement.spacedBy(active.spacing.xs)
    ) {
        // The preview strip is deliberately painted in the previewed theme's own tokens.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(SWATCH_STRIP_HEIGHT)
                .background(preview.colorBackground, active.shapes.small)
                .border(active.borderWidth, preview.colorBorder, active.shapes.small)
                .padding(active.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(active.spacing.xs)
        ) {
            SwatchDot(preview, preview.colorPrimary)
            SwatchDot(preview, preview.colorAccent)
            SwatchDot(preview, preview.colorError)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = active.labelTextStyle.copy(color = active.textPrimary),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = selectedContentDescription,
                    tint = active.colorPrimary,
                    modifier = Modifier.size(SELECTED_ICON_SIZE)
                )
            }
        }
    }
}

@Composable
private fun SwatchDot(preview: AppTheme, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier
            .size(SWATCH_DOT_SIZE)
            .background(color, preview.shapes.small)
            .border(preview.borderWidth, preview.colorBorder, preview.shapes.small)
    ) {}
}

// Fixed affordance geometry — not spacing, so no density token applies.
private val SWATCH_STRIP_HEIGHT = 34.dp
private val SWATCH_DOT_SIZE = 18.dp
private val SELECTED_ICON_SIZE = 16.dp
