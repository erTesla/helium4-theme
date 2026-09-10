package com.helium4.theme

import com.helium4.theme.ThemeId
import com.helium4.theme.themes.coreTheme
import com.helium4.theme.themes.editorialTheme
import com.helium4.theme.themes.expressiveTheme
import com.helium4.theme.themes.seasonalTheme
import com.helium4.theme.themes.surfaceTheme

/**
 * Lookup facade over the per-family theme definitions.
 *
 * The definitions themselves live in `ui/theme/themes/` split by family, purely because
 * 21 themes x light/dark in one file is unreadable.
 */
public object ThemeRegistry {

    public val all: List<ThemeId> = ThemeId.entries.toList()

    /**
     * Resolves a theme. Falls back to DEFAULT if a family file somehow does not cover an
     * id, so an incomplete registry degrades to the spec design system rather than crashing.
     */
    public fun resolve(id: ThemeId, dark: Boolean): AppTheme =
        coreTheme(id, dark)
            ?: surfaceTheme(id, dark)
            ?: editorialTheme(id, dark)
            ?: expressiveTheme(id, dark)
            ?: seasonalTheme(id, dark)
            ?: coreTheme(ThemeId.DEFAULT, dark)!!
}
