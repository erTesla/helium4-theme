package com.helium4.theme.components

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.dim

/** Material 3 slider fed entirely from tokens. */
@Composable
public fun AppSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    enabled: Boolean = true
) {
    val theme = LocalAppTheme.current

    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = theme.sliderActiveColor,
            activeTrackColor = theme.sliderActiveColor,
            inactiveTrackColor = theme.sliderInactiveColor,
            activeTickColor = theme.colorOnPrimary,
            inactiveTickColor = theme.colorBorder,
            disabledThumbColor = theme.dim(theme.sliderActiveColor),
            disabledActiveTrackColor = theme.dim(theme.sliderActiveColor),
            disabledInactiveTrackColor = theme.dim(theme.sliderInactiveColor)
        )
    )
}
