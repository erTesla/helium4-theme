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

    private val cache = java.util.concurrent.ConcurrentHashMap<Pair<ThemeId, Boolean>, AppTheme>()

    /**
     * Resolves a theme. Falls back to DEFAULT if a family file somehow does not cover an
     * id, so an incomplete registry degrades to the spec design system rather than crashing.
     *
     * Results are memoised: each (id, dark) pair is computed once and reused. AppTheme is
     * @Immutable and theme definitions are deterministic, so this is always safe.
     */
    public fun resolve(id: ThemeId, dark: Boolean): AppTheme =
        cache.computeIfAbsent(id to dark) { (resolveId, resolveDark) ->
            coreTheme(resolveId, resolveDark)
                ?: surfaceTheme(resolveId, resolveDark)
                ?: editorialTheme(resolveId, resolveDark)
                ?: expressiveTheme(resolveId, resolveDark)
                ?: seasonalTheme(resolveId, resolveDark)
                ?: coreTheme(ThemeId.DEFAULT, resolveDark)!!
        }
}
