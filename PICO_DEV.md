# Pico Dev

A native Android (Kotlin + Jetpack Compose) rebuild of the Pico Dev beta app, branded as
**Pico Dev** with the logo supplied by the user.

## What this project is

- Complete Kotlin/Compose source tree, released as **Pico Dev 1.0.0** (version code 1).
- Where the upstream source depends on git submodules or a Go toolchain that are not present
  here, the already-compiled artefacts are preserved from the reference beta so the app stays
  functional.
- `applicationId` is `ai.picodev` (10 characters, matching the Termux relocation assumption).
- Kotlin package names intentionally remain `ai.picodev.*`: they are internal namespaces and
  keeping them preserves the original working code byte-for-byte where possible.

## Build

```sh
cd "Pico Dev"
gradle :app:assembleDebug --no-daemon
```

Output: `app/build/outputs/apk/debug/picoDev-debug.apk` (packaged as `PicoDev-v1.0.0.apk`)

The debug build is signed with the standard Android debug key. The release signing block
still expects a release keystore and is not used for local builds.

A cloud build is also available through `.github/workflows/build-debug.yml` on GitHub Actions.

## Branding

The supplied logo is stored at `docs/readme/icon.png` and `docs/readme/icon.svg`; it is used
by the README and the documentation site. The source tree is self-contained and does not bundle
the upstream project.

## Status

See `BUILD_STATUS.md` for the build/fix log and `FEATURES.md` for the feature inventory.
