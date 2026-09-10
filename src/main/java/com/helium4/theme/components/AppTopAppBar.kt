package com.helium4.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.headingTextStyle
import com.helium4.theme.spacing

/**
 * Convenience overload — shows a back arrow when [onNavigationClick] is non-null.
 *
 * [navigationContentDescription] defaults to English because this library ships no
 * `res/`; a localizing consumer must pass its own string.
 */
@Composable
public fun AppTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    navigationContentDescription: String = "Back",
    actions: @Composable RowScope.() -> Unit = {}
) {
    val theme = LocalAppTheme.current
    AppTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = if (onNavigationClick != null) {
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = navigationContentDescription,
                    tint = theme.topAppBarContentColor,
                    modifier = Modifier.clickable { onNavigationClick() }
                )
            }
        } else null,
        actions = actions
    )
}

/** The app's top bar: title, optional navigation slot, trailing actions. */
@Composable
public fun AppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val theme = LocalAppTheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(theme.topAppBarColor)
            .padding(horizontal = theme.spacing.md, vertical = theme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(theme.spacing.sm)
    ) {
        if (navigationIcon != null) {
            navigationIcon()
        }
        Text(
            text = title,
            style = theme.headingTextStyle.copy(color = theme.topAppBarContentColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        actions()
    }
}
