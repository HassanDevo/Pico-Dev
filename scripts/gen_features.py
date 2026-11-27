#!/usr/bin/env python3
"""Generate FEATURES.md from the checked-in source: every @Composable, manifest component,
agent backend and asset bundle. Read-only with respect to the app source."""
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parent.parent
JAVA = ROOT / "app" / "src" / "main" / "java" / "ai" / "picodev"
MANIFEST = ROOT / "app" / "src" / "main" / "AndroidManifest.xml"
OUT = ROOT / "FEATURES.md"

COMPOSABLE = re.compile(r"@Composable(?:\s*\n)?\s*(?:@[^\n]*\n\s*)*fun\s+([A-Za-z_][A-Za-z0-9_]*)\s*\(")


def composables() -> dict[str, list[str]]:
    result: dict[str, list[str]] = {}
    for path in sorted(JAVA.rglob("*.kt")):
        text = path.read_text(encoding="utf-8")
        names = sorted(set(COMPOSABLE.findall(text)))
        if not names:
            continue
        rel = path.relative_to(JAVA).parent.as_posix()
        result.setdefault(rel, []).extend(names)
    for names in result.values():
        names.sort()
    return result


def manifest_components() -> list[str]:
    text = MANIFEST.read_text(encoding="utf-8")
    kind = re.compile(r"<(activity|service|receiver|provider)\b[^>]*?android:name=\"([^\"]+)\"", re.S)
    alias = re.compile(r"<activity-alias\b[^>]*?android:name=\"([^\"]+)\"", re.S)
    items = [f"{m.group(1)}: {m.group(2)}" for m in kind.finditer(text)]
    items += [f"activity-alias: {m.group(1)}" for m in alias.finditer(text)]
    return sorted(set(items))


def main() -> None:
    groups = composables()
    total = sum(len(v) for v in groups.values())
    lines = [
        "# Pico Dev — feature inventory",
        "",
        "Generated from the checked-in Kotlin source. The UI is Jetpack Compose: every",
        "`@Composable` function is a screen, dialog, sheet, card, widget or reusable UI piece.",
        "",
        f"- UI composables: **{total}** across **{len(groups)}** packages",
        f"- Manifest components: **{len(manifest_components())}**",
        "- Kotlin/Java source files: **{}**".format(len(list((ROOT / 'app').rglob('*.kt')))),
        "",
        "## Agent backends",
        "",
        "- Antigravity / AGY cloud (`data/agent/agy`)",
        "- Claude Code (`data/agent/claude`)",
        "- Codex (`data/agent/codex`)",
        "- OpenCode (`data/agent/opencode`)",
        "",
        "## Major subsystems",
        "",
    ]
    for name in sorted({p.name for p in (JAVA / "data").iterdir() if p.is_dir()}):
        files = len(list((JAVA / "data" / name).rglob("*.kt")))
        lines.append(f"- `data/{name}` — {files} Kotlin files")
    lines += ["", "## UI composables by package", ""]
    for package, names in sorted(groups.items()):
        lines.append(f"### `{package}` ({len(names)})")
        lines.append("")
        lines.append(", ".join(f"`{n}`" for n in names))
        lines.append("")
    lines += ["## Manifest components", ""]
    lines += [f"- {c}" for c in manifest_components()]
    lines.append("")
    OUT.write_text("\n".join(lines), encoding="utf-8")
    print(f"wrote {OUT} ({total} composables)")


if __name__ == "__main__":
    main()
