package com.helium4.theme

import androidx.compose.ui.text.font.FontFamily

/**
 * Font families used by the themes.
 *
 * These are all **bundled system families**, deliberately.
 *
 * The original design called for downloadable Google Fonts, but that route resolves fonts
 * through the Play Services font provider — a network fetch and a cloud SDK dependency,
 * both of which an offline-capable, dependency-light library cannot take on. Rather than
 * ship a theme that silently degrades on every offline launch, each named face below maps
 * to its nearest bundled family.
 *
 * Themes still read as distinct, because family is only one of several typography tokens:
 * size, weight and letter-spacing carry the rest. If downloadable fonts are wanted later,
 * this is the single file to change — nothing else references a font family directly.
 */
public object AppFonts {
    // ---- The four families actually available. These are what render today. ----

    public val SystemSans: FontFamily = FontFamily.SansSerif
    public val SystemMono: FontFamily = FontFamily.Monospace
    public val SystemSerif: FontFamily = FontFamily.Serif
    public val SystemCursive: FontFamily = FontFamily.Cursive

    // ---- Named design intent. ----
    //
    // Each alias below records the face a theme was *designed* for and currently
    // resolves to its nearest bundled family, so several aliases are the same instance
    // at runtime (`AppFonts.Inter === AppFonts.Archivo` is true). Do not rely on them
    // being distinguishable; do keep using them, because they are the mapping a future
    // font-bundling change would consume.

    // Geometric / neutral sans faces.
    public val Inter: FontFamily = SystemSans
    public val SpaceGrotesk: FontFamily = SystemSans
    public val Archivo: FontFamily = SystemSans
    public val Quicksand: FontFamily = SystemSans
    public val Nunito: FontFamily = SystemSans
    public val Comfortaa: FontFamily = SystemSans

    // Monospace / technical faces.
    public val SpaceMono: FontFamily = SystemMono
    public val Orbitron: FontFamily = SystemMono

    // Serif / editorial faces.
    public val PlayfairDisplay: FontFamily = SystemSerif
    public val Lora: FontFamily = SystemSerif
    public val Amiri: FontFamily = SystemSerif

    // Display faces.
    public val Righteous: FontFamily = SystemCursive
}
