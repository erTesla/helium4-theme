package com.helium4.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.helium4.theme.components.AppBottomSheet
import com.helium4.theme.components.AppDialog
import com.helium4.theme.components.AppListItem
import com.helium4.theme.components.AppScaffold
import com.helium4.theme.components.AppTopAppBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Baseline for the three window-level components, which cannot go in
 * [ThemeScreenshotTest]'s inline column: [AppScaffold] fills the window, and [AppDialog]
 * and [AppBottomSheet] render into their own windows above it.
 *
 * Only a handful of themes are covered rather than all 23. These components carry very
 * few theme-specific tokens of their own — the surface treatment they inherit is already
 * pinned 23 ways by [ThemeScreenshotTest] — so the sample is chosen to hit each
 * structurally distinct surface style instead: strict two-tone with no alpha, flat,
 * translucent glass, neumorphic extrusion, and hard-offset brutalist blocks.
 */
@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w400dp-h900dp-mdpi")
class ThemeOverlayScreenshotTest(private val themeId: ThemeId) {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scaffoldWithDialog() {
        capture("scaffold-dialog") {
            ScaffoldFrame()
            AppDialog(
                onDismissRequest = {},
                title = "Delete entry?",
                text = "This removes the recording and its transcript.",
                confirmLabel = "Delete",
                onConfirm = {},
                dismissLabel = "Cancel",
                onDismiss = {},
                isDestructive = true
            )
        }
    }

    @Test
    fun scaffoldWithBottomSheet() {
        capture("scaffold-sheet") {
            ScaffoldFrame()
            AppBottomSheet(onDismissRequest = {}) {
                AppListItem(title = "Save to Files", subtitle = "Export the audio")
                AppListItem(title = "Save as…", subtitle = "Choose a location")
            }
        }
    }

    private fun capture(name: String, content: @androidx.compose.runtime.Composable () -> Unit) {
        composeRule.setContent {
            PreviewAppTheme(themeId = themeId, dark = false, content = content)
        }
        composeRule.onRoot().captureRoboImage(
            "src/test/screenshots/overlay-${themeId.storageKey}-$name.png"
        )
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        fun params(): List<Array<Any>> = listOf(
            ThemeId.DEFAULT,      // two-tone, alpha banned
            ThemeId.FLAT,         // flat fills
            ThemeId.GLASSMORPHISM,// translucent surface
            ThemeId.NEUMORPHISM,  // extruded surface
            ThemeId.BRUTALISM     // hard offset blocks
        ).map { arrayOf(it) }
    }
}

@androidx.compose.runtime.Composable
private fun ScaffoldFrame() {
    val theme = LocalAppTheme.current
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppTopAppBar(title = theme.displayName, onNavigationClick = {}) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(theme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(theme.spacing.sm)
        ) {
            Text("Scaffold content", style = theme.bodyTextStyle, color = theme.textPrimary)
            AppListItem(title = "Row", subtitle = "Under the scaffold")
        }
    }
}
