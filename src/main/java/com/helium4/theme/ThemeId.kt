package com.helium4.theme

/**
 * Every theme this library ships. All are compiled into the artifact — no remote
 * config, no network, no runtime download.
 *
 * [DEFAULT] is the library's baseline design system (strict two-tone black/white plus
 * blue-for-action and red-for-destructive) and the fallback for unknown or corrupt
 * stored values. Every other value is opt-in expression: the host app decides how the
 * user picks one, and nothing here is ever applied automatically.
 */
public enum class ThemeId(
    /** Stable key used for persistence. Never change these — stored values depend on them. */
    public val storageKey: String,
    /**
     * Human-readable English label for pickers.
     *
     * Not localized: the library ships no `res/`. A localizing consumer should map
     * [storageKey] to its own string resource rather than showing this directly.
     */
    public val displayName: String
) {
    // Core
    DEFAULT("default", "Default"),
    MIDNIGHT("midnight", "Midnight"),
    FLAT("flat", "Flat"),
    MATERIAL("material", "Material"),
    DARK_UI("dark_ui", "Dark UI"),

    // Surface
    NEUMORPHISM("neumorphism", "Neumorphism"),
    GLASSMORPHISM("glassmorphism", "Glassmorphism"),
    CLAYMORPHISM("claymorphism", "Claymorphism"),
    SKEUOMORPHISM("skeuomorphism", "Skeuomorphism"),

    // Editorial
    MINIMALISM("minimalism", "Minimalism"),
    SWISS_STYLE("swiss_style", "Swiss Style"),
    TYPOGRAPHIC("typographic", "Typographic"),
    BENTO_GRID("bento_grid", "Bento Grid"),
    BRUTALISM("brutalism", "Brutalism"),

    // Expressive
    MEMPHIS("memphis", "Memphis"),
    RETRO_Y2K("retro_y2k", "Retro Y2K"),
    CYBERPUNK("cyberpunk", "Cyberpunk"),
    AURORA_UI("aurora_ui", "Aurora UI"),
    ORGANIC("organic", "Organic"),
    MAXIMALISM("maximalism", "Maximalism"),

    // Seasonal
    CHRISTMAS("christmas", "Christmas"),
    EASTER("easter", "Easter"),
    EID("eid", "Eid");

    public companion object {
        /** Maps a stored key back to a theme, falling back to [DEFAULT] on unknown/corrupt input. */
        public fun fromStorageKey(key: String?): ThemeId =
            entries.firstOrNull { it.storageKey == key } ?: DEFAULT

        public val seasonal: List<ThemeId> = listOf(CHRISTMAS, EASTER, EID)
    }
}

/** Grouping used purely to organise the Settings theme picker. */
public enum class ThemeFamily(public val displayName: String, public val themes: List<ThemeId>) {
    CORE(
        "Core",
        listOf(
            ThemeId.DEFAULT,
            ThemeId.MIDNIGHT,
            ThemeId.FLAT,
            ThemeId.MATERIAL,
            ThemeId.DARK_UI
        )
    ),
    SURFACE(
        "Surface",
        listOf(
            ThemeId.NEUMORPHISM,
            ThemeId.GLASSMORPHISM,
            ThemeId.CLAYMORPHISM,
            ThemeId.SKEUOMORPHISM
        )
    ),
    EDITORIAL(
        "Editorial",
        listOf(
            ThemeId.MINIMALISM,
            ThemeId.SWISS_STYLE,
            ThemeId.TYPOGRAPHIC,
            ThemeId.BENTO_GRID,
            ThemeId.BRUTALISM
        )
    ),
    EXPRESSIVE(
        "Expressive",
        listOf(
            ThemeId.MEMPHIS,
            ThemeId.RETRO_Y2K,
            ThemeId.CYBERPUNK,
            ThemeId.AURORA_UI,
            ThemeId.ORGANIC,
            ThemeId.MAXIMALISM
        )
    ),
    SEASONAL("Seasonal", listOf(ThemeId.CHRISTMAS, ThemeId.EASTER, ThemeId.EID));
}
