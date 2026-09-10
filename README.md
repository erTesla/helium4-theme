# Helium4 Theme

[![](https://jitpack.io/v/erTesla/helium4-theme.svg)](https://jitpack.io/#erTesla/helium4-theme)

A standalone, publishable Android Compose design-system library.  
23 bundled themes, token-based, zero network dependencies, zero analytics.

---

## What it is

`helium4-theme` provides:

- **`AppTheme`** — one immutable data class holding every visual token (colors, typography, shape, spacing, surface style, overlay)
- **`ThemeRegistry`** — resolves any of the 23 themes × light/dark to an `AppTheme` instance
- **`AppThemeProvider`** — root composable that applies the theme via `CompositionLocal`
- **22 themed components** (`AppButton`, `AppCard`, `AppTextField`, …) that read tokens from the composition local and never hardcode a value
- **`Modifier.themedSurface()`** — single branching point for neumorphic/glass/clay/hard-shadow/border-only/flat/embossed surfaces
- **Decorative overlays** — Canvas-drawn, touch-transparent particles (snowflakes, eggs, crescents, stars, flowers) for seasonal themes

The library ships no Hilt, no DataStore, no Firebase, no network code.  
Persistence, DI, and theme-preference logic live in the host app.

---

## Themes

| Family | Themes |
|--------|--------|
| **Core** | DEFAULT · FLAT · MATERIAL · DARK_UI |
| **Surface** | NEUMORPHISM · GLASSMORPHISM · CLAYMORPHISM · SKEUOMORPHISM |
| **Editorial** | MINIMALISM · SWISS_STYLE · TYPOGRAPHIC · BENTO_GRID · BRUTALISM |
| **Expressive** | MEMPHIS · RETRO_Y2K · CYBERPUNK · AURORA_UI · ORGANIC · MAXIMALISM |
| **Seasonal** | CHRISTMAS · EASTER · EID |

`DEFAULT` is the strictest theme: pure `#000000` / `#FFFFFF`, `#0066FF` for actions, `#FF0000` for destructive actions — no greys, no alpha-dimmed colors, surfaces separated by 1dp borders only.

---

## Installation

### JitPack

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.erTesla:helium4-theme:<tag>")
}
```

### The demo app

`demo/` is a runnable gallery: every published component in one scroll, with a picker for
all 23 themes and the three dark-mode settings. It is a **separate Gradle build**, not a
subproject, so JitPack never tries to build an Android application.

```bash
cd demo
../gradlew :app:installDebug     # uses the root wrapper
```

It consumes the library through the composite build below, so editing a token and
re-running is a single step. It is also the release gate: 14 of the components had no call
site anywhere when this library was extracted, and rendering all of them across all 23
themes is how their APIs actually get exercised.

### Developing against a local checkout (the escape hatch)

Do **not** iterate on a token by tagging. Edit, tag, wait for JitPack, bump the version,
repeat is intolerable for design work. Use a composite build instead: Gradle substitutes
the module coordinate for your local checkout, so a change to a token is edit-then-run.

```kotlin
// consumer's settings.gradle.kts
includeBuild("../helium4-theme") {
    dependencySubstitution {
        substitute(module("com.github.erTesla:helium4-theme")).using(project(":"))
    }
}
```

Leave the `implementation("com.github.erTesla:helium4-theme:<tag>")` line untouched — the
substitution intercepts it, so switching back to the published artifact is deleting four
lines. The bundled `demo/` build works exactly this way; read its `settings.gradle.kts` for
a working example.

Note that a consumer whose `settings.gradle.kts` sets
`repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)` — as this repo and Wavlog
both do — cannot add an ad-hoc repository inside a module's `build.gradle.kts`. The
composite build sidesteps that entirely, which is another reason to prefer it over
pointing at `mavenLocal()`.

### The persistence contract

This library deliberately ships **no** persistence: no DataStore, no Room, no DI. It takes
`ThemeId` and `DarkMode` as plain parameters, and the host app decides where they come
from. That leaves one invariant the boundary cannot enforce for you:

> **The default your storage layer returns for "nothing saved yet" must equal
> `ThemeId.fromStorageKey(null)`, which is `ThemeId.DEFAULT`.**

If your preferences layer defaults to, say, `MIDNIGHT` while `fromStorageKey` falls back to
`DEFAULT`, a fresh install renders one theme for the first frame and then snaps to the
other. Nothing in the type system catches this — before extraction the two halves lived in
one repo and a comment kept them in sync; now they do not.

Persist `ThemeId.storageKey` and `DarkMode.storageKey`, never `name` or `ordinal`:
`storageKey` is the stable contract, and `ordinal` shifts whenever a theme is added.

Read both back **before the first composition** and pass them straight into
`AppThemeProvider`. Resolving them asynchronously after the first frame is what produces
an unthemed flash on cold start; the library cannot prevent that on your behalf, because
it never sees your storage.

### Requirements

- `minSdk 24`
- `compileSdk 37`
- Jetpack Compose BOM `2026.02.01`
- Material 3

---

## Basic usage

### 1. Wrap your app

```kotlin
// No Hilt or DI needed — plain parameters.
setContent {
    AppThemeProvider(
        themeId = ThemeId.DEFAULT,
        darkMode = DarkMode.SYSTEM
    ) {
        MyApp()
    }
}
```

### 2. Read the active theme in any composable

```kotlin
@Composable
fun MyScreen() {
    val theme = LocalAppTheme.current

    Box(
        Modifier
            .fillMaxSize()
            .background(theme.colorBackground)
    ) {
        Text(
            text = "Hello",
            color = theme.textPrimary,
            style = theme.bodyTextStyle
        )
    }
}
```

### 3. Use themed components

```kotlin
AppButton(
    text = "Record",
    onClick = { /* … */ }
)

AppCard {
    Text("Today's entry", color = LocalAppTheme.current.textPrimary)
}

AppTextField(
    value = text,
    onValueChange = { text = it },
    placeholder = "Start typing…"
)
```

### 4. Use `Modifier.themedSurface()` for custom containers

```kotlin
Box(
    Modifier
        .themedSurface(theme)
        .padding(theme.spacing.md)
) {
    content()
}
```

`themedSurface` reads `theme.surfaceStyle` and applies the right treatment (flat / border-only / neumorphic dual-shadow / glass blur / clay / hard-shadow offset / embossed) — you never branch on style yourself.

---

## Token reference

### Colors

| Token | Purpose |
|-------|---------|
| `colorBackground` | Screen background |
| `colorSurface` | Card / sheet background |
| `colorSurfaceVariant` | Alternate surface (e.g. input fill) |
| `textPrimary` | Body and heading text |
| `textSecondary` | Supporting / caption text |
| `action` (= `colorPrimary`) | All primary buttons, links, confirm |
| `destructive` (= `colorError`) | Delete, discard, error states |
| `colorBorder` | `BORDER_ONLY` surface separator |
| `dividerColor` | `AppDivider` |
| `shimmerBaseColor` / `shimmerHighlightColor` | Loading skeletons |

### Typography

| Token | Use |
|-------|-----|
| `headingFontFamily` / `headingFontSize` / `headingFontWeight` | Section headings |
| `bodyFontFamily` / `bodyFontSize` | Body text |
| `labelFontFamily` / `labelFontSize` | Chips, captions |

Extension properties: `theme.headingTextStyle`, `theme.bodyTextStyle`, `theme.labelTextStyle`

### Shape

| Token | Default |
|-------|---------|
| `cornerRadiusSmall` | `4.dp` |
| `cornerRadiusMedium` | `12.dp` |
| `cornerRadiusLarge` | `24.dp` |
| `buttonCornerRadius` | `8.dp` |

Extension: `theme.shapes` → `ShapeTokens(small, medium, large, button)`

### Spacing

```kotlin
theme.spacing.xs   // 4.dp  (NORMAL)
theme.spacing.sm   // 8.dp
theme.spacing.md   // 16.dp
theme.spacing.lg   // 24.dp
theme.spacing.xl   // 32.dp
```

Values scale with `theme.spacingDensity` (COMPACT / NORMAL / COMFORTABLE).

### Style enums

| Token | Values |
|-------|--------|
| `buttonStyle` | FILLED · OUTLINED · GHOST · BEVELED |
| `iconStyle` | OUTLINE · FILLED · ROUNDED · SHARP |
| `surfaceStyle` | FLAT · BORDER_ONLY · NEUMORPHIC · GLASS · CLAY · HARD_SHADOW · EMBOSSED |
| `backgroundPattern` | NONE · GEOMETRIC · ORGANIC · NOISE · GRID · SCANLINE |
| `decorativeOverlay` | NONE · SNOWFLAKES · EGGS · CRESCENTS · STARS · FLOWERS |
| `animationStyle` | NONE · SUBTLE · PLAYFUL · DRAMATIC |

---

## Component catalog

| Component | File |
|-----------|------|
| `AppScaffold` | Themed background + optional pattern layer |
| `AppTopAppBar` | Token-colored top bar |
| `AppBottomNav` | Bottom navigation bar |
| `AppButton` | Primary / secondary / ghost / destructive |
| `AppFAB` | Floating action button |
| `AppCard` | Surface-styled card |
| `AppTextField` | Input field with border treatment |
| `AppSearchBar` | Search input |
| `AppDialog` | Alert / confirmation dialog |
| `AppBottomSheet` | Modal sheet |
| `AppSnackbar` | In-app notification |
| `AppChip` | Selectable / filter chip |
| `AppSwitch` | Toggle |
| `AppCheckbox` | Checkbox |
| `AppRadioButton` | Radio button |
| `AppSlider` | Value slider |
| `AppProgressBar` | Linear / circular progress |
| `AppDivider` | Horizontal rule |
| `AppBadge` | Notification count badge |
| `AppTooltip` | Hover / long-press tooltip |
| `AppListItem` | Row with leading/trailing slots |
| `AppTabRow` | Horizontal tab strip |
| `ThemeSwatchCard` | Mini theme preview (for pickers) |

---

## Previews

Every composable in the library can be previewed with `PreviewAppTheme`:

```kotlin
@Preview
@Composable
private fun MyButtonPreview() {
    PreviewAppTheme(ThemeId.BRUTALISM, dark = false) {
        AppButton(text = "Save", onClick = {})
    }
}
```

---

## How to add a theme

1. Add a value to `ThemeId` with a unique `storageKey` string:

```kotlin
// ThemeId.kt
NEON_NOIR("neon_noir"),
```

2. Add it to a family in `ThemeFamily`:

```kotlin
EXPRESSIVE("Expressive", listOf(…, ThemeId.NEON_NOIR))
```

3. Create the theme function in the appropriate file under `themes/`:

```kotlin
// themes/ExpressiveThemes.kt
internal fun neonNoirTheme(dark: Boolean) = AppTheme(
    id = ThemeId.NEON_NOIR,
    displayName = "Neon Noir",
    isDark = true,
    colorBackground = Color(0xFF0D0D0D),
    colorPrimary = Color(0xFF00FFCC),
    colorError = Color(0xFFFF003C),
    // … only override what differs from the defaults
)
```

4. Register it in `ThemeRegistry`:

```kotlin
// ThemeRegistry.kt
ThemeId.NEON_NOIR -> neonNoirTheme(dark)
```

That's it. `ThemeFamily.entries` drives the Settings picker automatically — no UI changes needed.

---

## Connecting persistence (host app pattern)

The library is DI-free. The recommended pattern for apps that need to persist the user's choice:

```kotlin
// In your app module — thin Hilt-backed bridge
@Composable
fun AppThemeWrapper(content: @Composable () -> Unit) {
    val viewModel: AppThemeViewModel = hiltViewModel()
    val prefs by viewModel.preferences.collectAsStateWithLifecycle()
    AppThemeProvider(
        themeId = prefs.themeId,
        darkMode = prefs.darkMode,
        content = content
    )
}
```

`AppThemeProvider` only accepts plain `ThemeId` / `DarkMode` values, so it stays publishable without pulling in Hilt or DataStore as transitive dependencies.

---

## Design constraints

The library enforces these by convention (enforced through code review / `themedSurface` architecture):

- **Zero hardcoded colors** in components — every color token comes from `LocalAppTheme.current`
- **Zero hardcoded corners** — all radius values come from `AppTheme` tokens
- **`Modifier.themedSurface()`** is the only place that branches on `SurfaceStyle` — components delegate, never duplicate
- **No network** — all 23 themes and all fonts are bundled. Font faces map to system families (`FontFamily.SansSerif` etc.) by default, keeping the library offline-safe and Play-Services-free
- **Decorative overlays never consume touch** — they sit on a `Box` with no pointer-input modifier and `clearAndSetSemantics {}`, invisible to TalkBack
- **`allowsAlphaDimming`** — the DEFAULT theme sets this to `false` to prevent `theme.dim()` from producing grey via alpha compositing

---

## Publishing to Maven Central / JitPack

### JitPack (quickest)

1. Push a Git tag: `git tag 1.0.0 && git push origin 1.0.0` — no leading `v`, because a JitPack coordinate is the literal tag and `v1.0.0` would appear in every consumer's dependency line
2. Open `https://jitpack.io/#erTesla/helium4-theme`
3. Click **Get it** on the tag — JitPack builds and caches the AAR
4. Users add `maven("https://jitpack.io")` + `implementation("com.github.erTesla:helium4-theme:1.0.0")`

### Maven Central (recommended for production)

1. Register a Sonatype namespace (`com.helium4`)
2. Add signing config to `build.gradle.kts` (the library is the root project — there is no subproject to scope with `:theme:`)
3. Set `GROUP`, `ARTIFACT`, `VERSION` in `gradle.properties`
4. Run `./gradlew publishToSonatype closeAndReleaseStagingRepository`

The `maven-publish` block is already in `build.gradle.kts`; extend it with the Sonatype repository and a signing block.

---

## Known issues / roadmap

| # | Component | Issue |
|---|-----------|-------|
| 1 | `AppTabRow` | Uses deprecated `TabRow` / `TabRowDefaults.Indicator` / `tabIndicatorOffset`. Replacement is `PrimaryTabRow` + `TabIndicatorScope.tabIndicatorOffset`. Tracked for the next minor release. |
| 2 | `AppTooltip` | Uses deprecated `rememberPlainTooltipPositionProvider`. Replacement is `rememberTooltipPositionProvider`. Tracked for the next minor release. |

---

## License

MIT
