# Changelog

All notable changes to this library. Format loosely follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versioning is
[Semantic Versioning](https://semver.org/).

## Versioning policy

`AppTheme` is a public `data class`, so its generated `copy()` and `componentN()` methods
are part of the binary API. This has concrete consequences:

| Change | Bump |
| --- | --- |
| Adding a token to `AppTheme` (with a default value) | **minor** — source-compatible, binary-breaking |
| Adding a new theme to `ThemeId` | **minor** |
| Adding a component or an optional parameter with a default | **minor** |
| Removing/renaming a token, theme, or component; changing a type | **major** |
| Changing a shipped theme's colors, spacing, or type scale | **minor** — no API change, but it repaints every consumer |
| Doc, comment, or internal-only change | **patch** |

Adding a token is never a patch release. See the "Stability contract" section of
`AppTheme`'s KDoc for why the `data class` is kept despite this.

There is no automated ABI check yet — `binary-compatibility-validator` registers no tasks
on this module because the build uses AGP 9's built-in Kotlin support rather than the
`kotlin-android` plugin BCV hooks into. `explicitApi()` is enabled, so nothing becomes
public by accident, but the table above is enforced by review.

## [Unreleased]

Pre-`1.0.0` hardening, done in-place in the Wavlog repo before extraction.

### Added

- `ThemeId.displayName` — a label for pickers, so consumers no longer have to call
  `ThemeRegistry.resolve(id, isDark).displayName` (resolving an entire theme) just to
  render a menu row. `AppTheme.displayName` now defaults to it.
- `AppTopAppBar(navigationContentDescription = …)` and
  `ThemeSwatchCard(label = …, selectedContentDescription = …)`. These strings were
  hardcoded English and the library ships no `res/`, so a localizing consumer had no way
  to override them.
- `AppScaffold(paintBackground = …, contentWindowInsets = …)`, plus KDoc covering the
  edge-to-edge behaviour that was previously only encoded in Wavlog's own screen chrome.
- `explicitApi()` — every public declaration now states its visibility and return type.
- **A screenshot baseline: 56 checked-in PNGs under `src/test/screenshots/`.** Every
  `ThemeId` in light and dark against a dense component slice, plus `AppScaffold` /
  `AppDialog` / `AppBottomSheet` across the five structurally distinct surface styles.
  Recorded with `./gradlew :theme:testDebugUnitTest -Proborazzi.record`, verified by a
  plain `./gradlew :theme:testDebugUnitTest`. This makes "renders identically" falsifiable
  — changing `DEFAULT`'s action blue fails exactly the two `DEFAULT` images and nothing
  else, which was confirmed. Roborazzi is wired deps-only; its Gradle plugin hooks the
  `kotlin-android` plugin, which this build does not apply.
- `testImplementation(junit)`, plus Robolectric, Roborazzi and `compose-ui-test`; the
  module previously had no test dependencies at all, so any new test failed to compile.
- `foundation` and `animation` as explicit `api` dependencies. Both were already used
  directly and appear in public signatures; they were resolving transitively via
  `material3`.

### Changed

- **`minSdk` 26 → 24.** Nothing in the module calls an ungated API above 24. `minSdk`
  lands in the published AAR manifest, where it becomes a hard floor for every consumer.
- **`platform(compose-bom)` is now `api`, not `implementation`.** `material3`, `ui` and
  `ui-graphics` are declared without versions and take them from the BOM; exported as
  `implementation`, the published POM would have listed all three with no version at all.
- `AppFonts.System`/`Mono`/`Serif`/`Cursive` → `SystemSans`/`SystemMono`/`SystemSerif`/
  `SystemCursive`. The 14 named aliases (`Inter`, `Righteous`, `Amiri`, …) are unchanged:
  they all currently resolve to one of these four bundled families, but they are the
  per-theme design record and the mapping a future font-bundling change would consume.
- `PreviewAppTheme` now installs `MaterialTheme` (color scheme **and** typography)
  alongside `LocalAppTheme`, matching `AppThemeProvider`. Previously previews silently
  dropped `toTypography()`, which is the single largest difference between the
  typographic themes — every theme previewed in the same type scale.
- `AppScaffold` no longer paints `colorBackground`. `AppThemeProvider` has already painted
  the backdrop, and repainting the flat color flattens a gradient theme back out.
- The deprecated `Window.statusBarColor`/`navigationBarColor` writes are now skipped on
  API 35+, where they are no-ops under enforced edge-to-edge. Setting system-bar *icon*
  brightness, the half that actually works, is unchanged on all API levels.

### Removed

- `ThemeSwitcherFab`. It early-returned on the **library's own** `BuildConfig.DEBUG`,
  which is compiled `false` in a release AAR — so it could never render for a consumer
  regardless of their build type. It was the module's only `BuildConfig` use, so
  `buildConfig = true` is gone too. Wavlog keeps a copy in its `debug` source set, where
  the guarantee is structural.
- `neumorphicShadows(dark:)` is now `internal`. It was a public top-level function with
  exactly one call site, inside `Modifier.themedSurface` in this module.

### Fixed

- **`AppBadge` no longer uses the destructive accent.** `badgeBackgroundColor` defaulted to
  `colorError` (and `DEFAULT` set it to its destructive red explicitly), so a neutral count
  was painted in the destructive colour in all 23 themes — a third semantic use of that
  accent, which the one-action/one-destructive rule forbids. It now defaults to
  `colorPrimary`, which is what `badgeTextColor` was already pairing with
  (`colorOnPrimary`). 44 of the 56 screenshot baselines moved; the 12 that did not are the
  10 overlay captures, which contain no badge, and the 2 themes where `colorPrimary` and
  `colorError` are the same value.
- **`AppBadge`'s label was white-on-white in every `BORDER_ONLY` theme.** Found while
  verifying the accent change above, and pre-existing rather than caused by it.
  `SurfaceStyle.BORDER_ONLY` deliberately discards `fill` — correct for a card on a theme
  with no greys, where the border is the only thing making it a card — but `AppBadge`
  passes an *accent* fill and picks its text colour to contrast with that accent, not with
  the page. The result was an outlined box containing invisible text. Now re-applies the
  accent fill on top of `themedSurface`, the same way `AppButton` already did for a filled
  container, so the surface treatment (Brutalism's offset block, the neumorphic extrusion)
  is preserved underneath.

- KDoc that would mislead a consumer: `LocalAppTheme`'s error message named
  `AppThemeWrapper` (an *app* symbol, not in this library); `PreviewAppTheme` claimed Hilt
  and DataStore were required (this library depends on neither); `CoreThemes` credited
  dynamic color to `AppThemeWrapper` rather than `AppThemeProvider.toColorScheme`;
  `ThemeId` described itself as "bundled in the APK" (it ships as an AAR) and "chosen in
  Settings" (the host app decides). Remaining `Wavlog`-specific mentions were genericised.
- `AppThemeProvider` now documents its two real constraints instead of leaving consumers
  to discover them: it fills the available space and paints the backdrop, so it is a
  window-root composable rather than a subtree wrapper; and its system-bar handling
  requires an `Activity` context, doing nothing in a dialog window or a `Service`. Use
  `PreviewAppTheme` to theme a subtree.
