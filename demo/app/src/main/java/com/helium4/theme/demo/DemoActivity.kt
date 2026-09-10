package com.helium4.theme.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.helium4.theme.AppThemeProvider
import com.helium4.theme.DarkMode
import com.helium4.theme.ThemeId

/**
 * The gallery: every published component, under every theme, in light and dark.
 *
 * This is not decoration. Fourteen of the library's components had no call site anywhere
 * when it was extracted, which means their API had never been exercised by anything but
 * the compiler. Rendering all of them across all 23 themes is the gate on a release, and
 * the reason `AppBadge`'s white-on-white label was found before the first tag rather than
 * after.
 *
 * Theme choice is held in `rememberSaveable` rather than persisted: the library has no
 * opinion on storage, and a demo that hardcoded DataStore would imply one it does not
 * have. A real consumer supplies [ThemeId] and [DarkMode] from wherever it likes.
 */
class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Deliberately on: it is the configuration that breaks naive theming, so the
        // demo should be permanently exposed to it rather than opting out.
        enableEdgeToEdge()

        setContent {
            var themeId by rememberSaveable { mutableStateOf(ThemeId.MIDNIGHT) }
            var darkMode by rememberSaveable { mutableStateOf(DarkMode.SYSTEM) }

            AppThemeProvider(themeId = themeId, darkMode = darkMode) {
                GalleryScreen(
                    themeId = themeId,
                    darkMode = darkMode,
                    onThemeChange = { themeId = it },
                    onDarkModeChange = { darkMode = it }
                )
            }
        }
    }
}
