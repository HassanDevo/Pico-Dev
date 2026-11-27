# Pico Dev — build status

## Current release

- Version: **1.0.0**
- Version code: **1**
- Package: `ai.picodev`
- App label: `Pico Dev`
- Update URL: `https://raw.githubusercontent.com/HassanDevo/Pico-Dev/main/Updates/version.json`
- APK assets: `PicoDev-v1.0.0.apk`, `PicoDev-v1.0.0-chroot.apk`, `PicoDev-v1.0.0-x86_64.apk`

## Source state

- The checked-in source tree is self-contained; no Git submodules or local SDK paths are required.
- Debug builds use `ai.picodev` and the same debug signing key, so the in-app updater and background update checks work.
- Release signing passwords are read from `RELEASE_STORE_PASSWORD` / `RELEASE_KEY_PASSWORD`, never stored in source.
- Generated build caches under `app/build/` and `build/` are disposable; Gradle rebuilds them.
- The logo is at `docs/readme/icon.png` and `docs/readme/icon.svg`.
- Visible README/docs copyright text credits **HassanDevo**; the license is GNU GPL v3.0.

## Cloud build

`.github/workflows/build-debug.yml` builds all three debug variants on GitHub Actions (JDK 21, Android SDK, Go, larger
Kotlin heap) and uploads `output/*.apk` as the `PicoDev-debug` artifact. Those APKs are published as the v1.0.0 release assets.

## Local build note

Local Kotlin compilation on this device may hit the Kotlin/Gradle memory limit. Use GitHub Actions or a machine with more RAM.

## Feature preservation

UI, design, features, logic, flow and behaviour are preserved from the reference beta. Only branding, resource/update
links, version 1.0.0 and the fixes listed in `CHANGE_LOG.md` differ.
