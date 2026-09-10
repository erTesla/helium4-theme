package com.helium4.theme

/** How the light/dark variant of the active theme is chosen. */
public enum class DarkMode(public val storageKey: String) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark");

    public companion object {
        public fun fromStorageKey(key: String?): DarkMode =
            entries.firstOrNull { it.storageKey == key } ?: SYSTEM
    }
}
