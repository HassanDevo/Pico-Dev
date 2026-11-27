#!/usr/bin/env python3
"""Undo branding edits that would break server-side/protocol compatibility.

User-facing text says "Pico Dev", but these values are URLs, HTTP header values and on-disk
paths that external systems or bundled binaries may depend on.
"""
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
JAVA = ROOT / "app" / "src" / "main" / "java"

FIXES = {
    # Upstream GitHub release asset still carries the old product name.
    "app/src/main/java/ai/picodev/data/updater/AppUpdateManager.kt": [
        ("/PicoDev-v$versionName$suffix.apk", "/PicoDev-v$versionName$suffix.apk"),
    ],
}

# HTTP User-Agent values: spaces in a product token are not valid.
UA_FILES = [
    "data/androiddev/AndroidDevManager.kt",
    "data/egm/EgmManager.kt",
    "data/local/chroot/ChrootInstaller.kt",
    "data/rea/ReaManager.kt",
    "data/x11/X11DesktopManager.kt",
    "ui/components/FullScreenImageDialog.kt",
    "ui/components/ImageDownloadHelper.kt",
    "ui/components/MarkdownContent.kt",
]

for rel in UA_FILES:
    FIXES[f"app/src/main/java/ai/picodev/{rel}"] = [("Pico Dev/", "PicoDev/")]

# On-disk folders referenced by more than one screen: use a shell-safe name.
PATH_FILES = [
    "data/miniapps/MiniAppSigning.kt",
    "ui/components/ConversationExportHelper.kt",
    "ui/components/ConversationShareHelper.kt",
]
for rel in PATH_FILES:
    FIXES[f"app/src/main/java/ai/picodev/{rel}"] = [("Download/Pico Dev", "Download/PicoDev")]


def main() -> None:
    for rel, replacements in FIXES.items():
        path = ROOT / rel
        text = path.read_text(encoding="utf-8")
        updated = text
        for old, new in replacements:
            updated = updated.replace(old, new)
        if updated != text:
            path.write_text(updated, encoding="utf-8")
            print(f"fixed {rel}")
    print("wire compatibility fixes applied")


if __name__ == "__main__":
    main()
