package com.helium4.theme.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.helium4.theme.DarkMode
import com.helium4.theme.LocalAppTheme
import com.helium4.theme.ThemeFamily
import com.helium4.theme.ThemeId
import com.helium4.theme.bodyTextStyle
import com.helium4.theme.components.AppBadge
import com.helium4.theme.components.AppBottomNav
import com.helium4.theme.components.AppBottomNavItem
import com.helium4.theme.components.AppBottomSheet
import com.helium4.theme.components.AppButton
import com.helium4.theme.components.AppButtonVariant
import com.helium4.theme.components.AppCard
import com.helium4.theme.components.AppCheckbox
import com.helium4.theme.components.AppChip
import com.helium4.theme.components.AppDialog
import com.helium4.theme.components.AppDivider
import com.helium4.theme.components.AppFAB
import com.helium4.theme.components.AppListItem
import com.helium4.theme.components.AppProgressBar
import com.helium4.theme.components.AppRadioButton
import com.helium4.theme.components.AppScaffold
import com.helium4.theme.components.AppSearchBar
import com.helium4.theme.components.AppSlider
import com.helium4.theme.components.AppSnackbar
import com.helium4.theme.components.AppSwitch
import com.helium4.theme.components.AppTabRow
import com.helium4.theme.components.AppTextField
import com.helium4.theme.components.AppTooltip
import com.helium4.theme.components.AppTopAppBar
import com.helium4.theme.components.ThemeSwatchCard
import com.helium4.theme.headingTextStyle
import com.helium4.theme.spacing

/**
 * Every published component in one scroll, plus a picker for all 23 themes.
 *
 * The top bar is wrapped in the status-bar inset rather than left bare, which is the
 * pairing `AppScaffold`'s KDoc describes: the bar does not consume insets for you,
 * because a bar that bleeds under the status bar is an equally valid design.
 */
@Composable
fun GalleryScreen(
    themeId: ThemeId,
    darkMode: DarkMode,
    onThemeChange: (ThemeId) -> Unit,
    onDarkModeChange: (DarkMode) -> Unit
) {
    val theme = LocalAppTheme.current
    var dialogOpen by remember { mutableStateOf(false) }
    var sheetOpen by remember { mutableStateOf(false) }

    AppScaffold(
        topBar = {
            Column(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                AppTopAppBar(title = "Helium 4 - " + themeId.displayName, onNavigationClick = null)
            }
        },
        bottomBar = {
            AppBottomNav(
                items = listOf(
                    AppBottomNavItem("Home", Icons.Default.Home),
                    AppBottomNavItem("Likes", Icons.Default.Favorite),
                    AppBottomNavItem("Settings", Icons.Default.Settings)
                ),
                selectedIndex = 0,
                onSelect = {}
            )
        },
        floatingActionButton = {
            AppFAB(
                onClick = { sheetOpen = true },
                icon = Icons.Default.Check,
                contentDescription = "Open bottom sheet"
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(padding),
            contentPadding = theme.spacing.screenPadding,
            verticalArrangement = Arrangement.spacedBy(theme.spacing.sm)
        ) {
            item { SectionHeading("Dark mode") }
            item { DarkModeRow(darkMode, onDarkModeChange) }

            item { SectionHeading("Controls") }
            item { ControlsBlock(onShowDialog = { dialogOpen = true }) }

            item { SectionHeading("Themes") }
            ThemeFamily.entries.forEach { family ->
                item { SectionHeading(family.displayName, small = true) }
                items(family.themes) { id ->
                    ThemeSwatchCard(
                        themeId = id,
                        selected = id == themeId,
                        onClick = { onThemeChange(id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    if (dialogOpen) {
        AppDialog(
            onDismissRequest = { dialogOpen = false },
            title = "Delete this theme?",
            text = "Nothing is actually deleted - this is the destructive dialog treatment.",
            confirmLabel = "Delete",
            onConfirm = { dialogOpen = false },
            dismissLabel = "Cancel",
            onDismiss = { dialogOpen = false },
            isDestructive = true
        )
    }

    if (sheetOpen) {
        AppBottomSheet(onDismissRequest = { sheetOpen = false }) {
            AppListItem(title = "Bottom sheet", subtitle = "Rendered in its own window")
            AppListItem(title = "Second row", subtitle = "Surface treatment follows the theme")
        }
    }
}

@Composable
private fun SectionHeading(text: String, small: Boolean = false) {
    val theme = LocalAppTheme.current
    Text(
        text = text.uppercase(),
        style = if (small) theme.bodyTextStyle else theme.headingTextStyle,
        color = theme.textSecondary,
        modifier = Modifier.padding(top = theme.spacing.sm)
    )
}

@Composable
private fun DarkModeRow(current: DarkMode, onChange: (DarkMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(LocalAppTheme.current.spacing.xs)) {
        DarkMode.entries.forEach { mode ->
            AppChip(
                label = mode.name,
                selected = mode == current,
                onClick = { onChange(mode) }
            )
        }
    }
}

/** Every remaining component, in one block, so a theme change is judged in one glance. */
@Composable
private fun ControlsBlock(onShowDialog: () -> Unit) {
    val theme = LocalAppTheme.current
    var query by remember { mutableStateOf("") }
    var typed by remember { mutableStateOf("Editable text") }
    var checked by remember { mutableStateOf(true) }
    var switched by remember { mutableStateOf(true) }
    var tab by remember { mutableIntStateOf(0) }
    var slider by remember { mutableFloatStateOf(0.6f) }

    Column(verticalArrangement = Arrangement.spacedBy(theme.spacing.sm)) {
        AppCard {
            Column(verticalArrangement = Arrangement.spacedBy(theme.spacing.xs)) {
                AppListItem(title = "List item", subtitle = "Secondary text")
                AppDivider()
                AppButton(text = "Primary", onClick = {})
                AppButton(text = "Secondary", onClick = {}, style = AppButtonVariant.SECONDARY)
                AppButton(text = "Ghost", onClick = {}, style = AppButtonVariant.GHOST)
                AppButton(text = "Delete", onClick = onShowDialog, style = AppButtonVariant.DESTRUCTIVE)
                AppButton(text = "Disabled", onClick = {}, enabled = false)
            }
        }
        AppSearchBar(query = query, onQueryChange = { query = it })
        AppTextField(value = typed, onValueChange = { typed = it }, label = "Field")
        AppTabRow(tabs = listOf("One", "Two", "Three"), selectedIndex = tab, onSelect = { tab = it })
        Row(horizontalArrangement = Arrangement.spacedBy(theme.spacing.sm)) {
            AppBadge(text = "3")
            AppTooltip(text = "A tooltip") { AppBadge(text = "?") }
            AppSwitch(checked = switched, onCheckedChange = { switched = it })
            AppCheckbox(checked = checked, onCheckedChange = { checked = it })
            AppRadioButton(selected = checked, onClick = { checked = !checked })
        }
        AppSlider(value = slider, onValueChange = { slider = it })
        AppProgressBar(progress = slider)
        AppSnackbar(message = "Saved", actionLabel = "Undo", onAction = {})
    }
}
