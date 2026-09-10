package com.helium4.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.dim
import com.helium4.theme.shapes
import com.helium4.theme.spacing
import com.helium4.theme.themedSurface

/** Single-line search field on a themed surface, with a leading search icon. */
@Composable
public fun AppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search"
) {
    val theme = LocalAppTheme.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .themedSurface(
                theme = theme,
                shape = theme.shapes.medium,
                fill = theme.inputFieldBackground,
                borderColor = theme.inputFieldBorderColor
            )
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = theme.inputFieldTextColor,
            // Structural icon size.
            modifier = Modifier.size(20.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = theme.spacing.sm),
            contentAlignment = Alignment.CenterStart
        ) {
            if (query.isEmpty()) {
                Text(
                    text = placeholder,
                    style = theme.bodyTextStyle.copy(
                        color = theme.dim(theme.inputFieldTextColor)
                    )
                )
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = theme.bodyTextStyle.copy(color = theme.inputFieldTextColor),
                cursorBrush = SolidColor(theme.colorPrimary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )
        }
    }
}
