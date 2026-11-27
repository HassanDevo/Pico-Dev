# Pico Dev — UI / UX map

The app is a single `ComponentActivity` (`MainActivity`) with a custom floating page switcher. It does not use
`MainScreen.kt`; that file is an unused template from the upstream starter.

## Seven primary pages (`AppViewMode`)

| Mode | Screen source | Purpose |
| --- | --- | --- |
| `CHAT` | `ui/chat/ChatScreen.kt` | Main agent conversation, model picker, attachments, artifacts drawer, chat history drawer |
| `IDE` | `ui/ide/IdeScreen.kt` | Code editor / project tree with daemon-backed tooling |
| `TERMINAL` | `TerminalActivity` + local terminal bridge | Built-in Termux-compatible environment |
| `BROWSER` | `ui/browser/BrowserScreen.kt` | Tabbed WebView browser with REA bridge and DevTools endpoint |
| `NOTES` | `data/notes` + `ui/notes` | Notes editor |
| `FILES` | `ui/files`, `data/files/PicoDevDocumentsProvider.kt` | Environment / chroot / SD-card file explorer |
| `RUN` | `ui/run` | Per-folder commands with live output that keeps running across pages |

## Shell and navigation

- Edge-to-edge Compose with `GeminiTheme` (light / dark / system, synced to the splash).
- Floating, movable, scalable page switcher with configurable items, orientation, auto-collapse and handle icon.
- Optional page overview with snapshot cards, opened from the switcher pill or its own pill.
- Back at the chat root moves the app to the background instead of closing it.
- Onboarding dialogs: `GemmyIntroDialog`, `TerminalLauncherOnboardingDialog`.
- Drawers: chat history, artifacts, profile detail.
- Floating overlays: Jarvis bubble, voice-call bubble, network inspector, diagnostics.
- Home-screen shortcuts/widgets route through `HomeRouter` and `HomeWidgets`.

## Settings surfaces

`SettingsDialog` plus dedicated screens for servers, agents/memory/skills, slash commands, language, themes, GitHub,
ADB connect, Android automation, Android dev setup, EGM, REA groups, WebView extensions, X11 desktop, chroot,
update, switcher overview and manage-space.

## Branding / visuals

- Launcher icons replaced with the user's `Pico Dev` icon pack (22 mipmap files, adaptive + round + legacy).
- App label is `Pico Dev` in main, beta and debug variants.
- User-facing strings from "Pico Dev" to "Pico Dev"; machine-facing identifiers (HTTP headers, updater asset URL,
  internal intent actions, Kotlin package names) intentionally stay upstream-compatible.

## Generated inventory

`FEATURES.md` lists all named composables (317), manifest components (44), agent backends and 40+ data subsystems.
