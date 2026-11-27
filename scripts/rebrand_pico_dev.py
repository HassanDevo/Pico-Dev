#!/usr/bin/env python3
"""Apply the Pico Dev brand to the PicoDev source tree.

- Replaces the display name "PicoDev" with "Pico Dev" only inside Kotlin/Java string
  literals (never identifiers, package names, class names or URLs).
- Replaces it in XML resources and the English i18n catalogue.
- Installs the Pico Dev launcher icon pack supplied by the user.

This script is idempotent: running it again makes no further changes.
"""
from pathlib import Path
import re
import shutil
import sys

ROOT = Path(__file__).resolve().parent.parent
APP = ROOT / "app" / "src" / "main"
ICON_PACK = Path("/data/user/0/ai.picodev/files/usr/tmp/pico_icon_pack/pico_dev_icon_pack")

OLD = "PicoDev"
NEW = "Pico Dev"

# Kotlin/Java string literals: ordinary and triple-quoted, with escapes.
STRING_LITERAL = re.compile(r'("""(?:.|\n)*?"""|"(?:[^"\\\n]|\\.)*")')


def repl_literal(match: re.Match) -> str:
    return match.group(0).replace(OLD, NEW)


def rebrand_sources() -> tuple[int, int]:
    files = changed = 0
    for path in APP.rglob("*.kt"):
        text = path.read_text(encoding="utf-8")
        if OLD not in text:
            continue
        files += 1
        new = STRING_LITERAL.sub(repl_literal, text)
        if new != text:
            path.write_text(new, encoding="utf-8")
            changed += 1
    return files, changed


def rebrand_resources() -> tuple[int, int]:
    files = changed = 0
    targets = list((ROOT / "app" / "src").rglob("*.xml")) + list(APP.rglob("*.json"))
    for path in targets:
        text = path.read_text(encoding="utf-8")
        if OLD not in text:
            continue
        files += 1
        new = text.replace(OLD, NEW)
        if new != text:
            path.write_text(new, encoding="utf-8")
            changed += 1
    return files, changed


def install_icons() -> int:
    if not ICON_PACK.exists():
        print(f"icon pack not staged at {ICON_PACK}; skipping icons", file=sys.stderr)
        return 0
    count = 0
    for src in (ICON_PACK / "res").rglob("*"):
        if not src.is_file():
            continue
        rel = src.relative_to(ICON_PACK / "res")
        dst = APP / "res" / rel
        dst.parent.mkdir(parents=True, exist_ok=True)
        if not dst.exists() or src.read_bytes() != dst.read_bytes():
            shutil.copy2(src, dst)
            count += 1
    return count


def main() -> None:
    a, b = rebrand_sources()
    c, d = rebrand_resources()
    icons = install_icons()
    print(f"Kotlin files scanned with brand text: {a}, rewritten: {b}")
    print(f"Resource files scanned with brand text: {c}, rewritten: {d}")
    print(f"Icon files installed/updated: {icons}")


if __name__ == "__main__":
    main()
