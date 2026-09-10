package com.helium4.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.captureRoboImage
import com.helium4.theme.components.AppBadge
import com.helium4.theme.components.AppBottomNav
import com.helium4.theme.components.AppBottomNavItem
import com.helium4.theme.components.AppSearchBar
import com.helium4.theme.components.AppSnackbar
import com.helium4.theme.components.AppTabRow
import com.helium4.theme.components.AppTooltip
import com.helium4.theme.components.AppButton
import com.helium4.theme.components.AppButtonVariant
import com.helium4.theme.components.AppCard
import com.helium4.theme.components.AppCheckbox
import com.helium4.theme.components.AppChip
import com.helium4.theme.components.AppDivider
import com.helium4.theme.components.AppFAB
import com.helium4.theme.components.AppListItem
import com.helium4.theme.components.AppProgressBar
import com.helium4.theme.components.AppRadioButton
import com.helium4.theme.components.AppSlider
import com.helium4.theme.components.AppSwitch
import com.helium4.theme.components.AppTextField
import com.helium4.theme.components.AppTopAppBar
import com.helium4.theme.components.ThemeSwatchCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * The visual baseline for this library.
 *
 * Every [ThemeId] is rendered in both light and dark against a fixed slice of the
 * component set, and the PNG is compared byte-for-byte against a checked-in reference.
 * This exists specifically so that "renders identically" is a falsifiable claim rather
 * than an assertion: a smoke test that only calls [ThemeRegistry.resolve] cannot detect a
 * changed font, shadow, corner radius, spacing scale, or gradient.
 *
 * Record after an intentional visual change:
 * ```
 * ./gradlew :theme:testDebugUnitTest -Proborazzi.record
 * ```
 */
@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w400dp-h2400dp-mdpi")
class ThemeScreenshotTest(
    private val themeId: ThemeId,
    private val dark: Boolean
) {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun renders() {
        composeRule.setContent {
            PreviewAppTheme(themeId = themeId, dark = dark) {
                ComponentSlice()
            }
        }
        val mode = if (dark) "dark" else "light"
        composeRule.onRoot().captureRoboImage(
            "src/test/screenshots/${themeId.storageKey}-$mode.png"
        )
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}-dark={1}")
        fun params(): List<Array<Any>> =
            ThemeId.entries.flatMap { id ->
                listOf(arrayOf<Any>(id, false), arrayOf<Any>(id, true))
            }
    }
}

/** Outlined and ghost button styles, which the filled default does not exercise. */
@Composable
private fun AppTextButtonRow() {
    val theme = LocalAppTheme.current
    Column(verticalArrangement = Arrangement.spacedBy(theme.spacing.xs)) {
        AppButton(text = "Secondary", onClick = {}, style = AppButtonVariant.SECONDARY)
        AppButton(text = "Ghost", onClick = {}, style = AppButtonVariant.GHOST)
        AppButton(text = "Destructive", onClick = {}, style = AppButtonVariant.DESTRUCTIVE)
    }
}

/**
 * A deliberately dense slice of the component set: enough tokens in one frame that a
 * regression in color, type, spacing, shape or elevation shows up as a pixel diff.
 */
@Composable
private fun ComponentSlice() {
    val theme = LocalAppTheme.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(theme.spacing.sm)
    ) {
        AppTopAppBar(title = theme.displayName, onNavigationClick = {})
        Column(
            modifier = Modifier.padding(theme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(theme.spacing.sm)
        ) {
            AppCard {
                Column(verticalArrangement = Arrangement.spacedBy(theme.spacing.xs)) {
                    AppListItem(title = "List item", subtitle = "Secondary text")
                    AppDivider()
                    AppButton(text = "Primary", onClick = {})
                    AppButton(text = "Disabled", onClick = {}, enabled = false)
                }
            }
            AppSearchBar(query = "query", onQueryChange = {})
            AppTextField(value = "Typed text", onValueChange = {}, label = "Field")
            AppTabRow(tabs = listOf("One", "Two"), selectedIndex = 0, onSelect = {})
            AppChip(label = "Chip", selected = true, onClick = {})
            AppBadge(text = "3")
            AppSwitch(checked = true, onCheckedChange = {})
            AppCheckbox(checked = true, onCheckedChange = {})
            AppRadioButton(selected = true, onClick = {})
            AppSlider(value = 0.6f, onValueChange = {}, modifier = Modifier.width(200.dp))
            AppProgressBar(progress = 0.4f)
            ThemeSwatchCard(themeId = theme.id, selected = true, onClick = {})
            AppSnackbar(message = "Saved", actionLabel = "Undo", onAction = {})
            AppTooltip(text = "Tooltip") {
                AppBadge(text = "?")
            }
            AppTextButtonRow()
            AppFAB(onClick = {}, icon = Icons.Default.Check, contentDescription = "Done")
            AppBottomNav(
                items = listOf(
                    AppBottomNavItem("Home", Icons.Default.Home),
                    AppBottomNavItem("Likes", Icons.Default.Favorite),
                    AppBottomNavItem("Settings", Icons.Default.Settings)
                ),
                selectedIndex = 0,
                onSelect = {}
            )
        }
    }
}
