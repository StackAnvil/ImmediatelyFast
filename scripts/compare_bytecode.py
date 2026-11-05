"""Capture and compare named mod bytecode during a mechanical refactor.

Run `capture DIRECTORY` before editing, then `compare DIRECTORY` after building.
An optional JSON object maps old class or member names to their new names.
Debug tables and constant-pool indices do not affect the comparison.
"""

import argparse
import difflib
import hashlib
import json
from io import BytesIO
import re
import subprocess
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent


def disassemble(directory):
    classes = sorted(directory.rglob("net/raphimc/**/*.class"))
    if not classes:
        raise ValueError(f"No compiled mod classes in {directory}; build this target first")
    result = subprocess.run(
        ["javap", "-c", "-p", "-s", *map(str, classes)],
        check=True, capture_output=True, text=True,
    )
    bodies = re.split(r'^Compiled from "[^\n]+"\n', result.stdout, flags=re.MULTILINE)[1:]
    if len(bodies) != len(classes):
        raise ValueError(f"Cannot split javap output for {directory}")
    return {str(path.relative_to(directory)): body for path, body in zip(classes, bodies)}


def snapshot():
    matrix = json.loads((ROOT / "gradle/versions.json").read_text())
    result = {}
    for version, target in matrix.items():
        for module in ["common", *target["loaders"]]:
            directory = ROOT / module / "versions" / version / "build/classes/java/main"
            result[f"{module}/{version}"] = disassemble(directory)
    return result


def resource_content(name, body):
    if name.endswith(".jar"):
        with zipfile.ZipFile(BytesIO(body)) as nested:
            entries = {entry: resource_content(entry, nested.read(entry))
                       for entry in nested.namelist() if not entry.endswith("/")}
        return json.dumps(entries, sort_keys=True)
    try:
        text = body.decode("utf-8")
    except UnicodeDecodeError:
        return "sha256:" + hashlib.sha256(body).hexdigest()
    if name.endswith("accesstransformer.cfg"):
        return "\n".join(sorted(line.strip() for line in text.splitlines() if line.strip()))
    return text


def resource_snapshot(baseline=None):
    matrix = json.loads((ROOT / "gradle/versions.json").read_text())
    result = {}
    for version, target in matrix.items():
        for module in ["common", *target["loaders"]]:
            directory = (baseline / module / version / "libs" if baseline else
                         ROOT / module / "versions" / version / "build/libs")
            for archive in sorted(directory.glob("*.jar")):
                with zipfile.ZipFile(archive) as jar:
                    for name in jar.namelist():
                        if name.endswith(("/", ".class")):
                            continue
                        result[f"{module}/{version}/{archive.name}/{name}"] = resource_content(name, jar.read(name))
    if not result:
        raise ValueError("No baseline jar resources found")
    return result


def normalize(text, renames, *, bytecode=False):
    if bytecode:
        lines = []
        for line in text.splitlines():
            instruction, marker, literal = line.partition("// String ")
            instruction = re.sub(r"#\d+", "#", normalize(instruction, renames))
            lines.append(instruction + marker + literal)
        return "\n".join(line for line in lines if line.strip())
    for old, new in sorted(renames.items(), key=lambda item: -len(item[0])):
        text = re.sub(r"(?<![\w$])" + re.escape(old) + r"(?![\w$])", lambda _: new, text)
        text = text.replace(f"${old}$", f"${new}$")
    return "\n".join(line.rstrip() for line in text.splitlines() if line.strip())


def compare(baseline, current, renames, allowed):
    def normalized_classes(classes):
        result = {}
        for name, body in classes.items():
            normalized_name = normalize(name, renames)
            if normalized_name in result:
                raise ValueError(f"Rename collision for {normalized_name}")
            result[normalized_name] = normalize(body, renames, bytecode=True)
        return result

    failures = []
    checked = 0
    for target in sorted(baseline.keys() | current.keys()):
        before = normalized_classes(baseline.get(target, {}))
        after = normalized_classes(current.get(target, {}))
        for name in sorted(before.keys() | after.keys()):
            previous = before.get(name, "")
            updated = after.get(name, "")
            if previous == updated:
                checked += 1
                continue
            qualified = name.removesuffix(".class").replace("/", ".")
            if qualified in allowed and name in before and name in after:
                print(f"Expected structural change: {target}/{name}")
                continue
            failures.append(f"{target}/{name}")
            print("\n".join(difflib.unified_diff(
                previous.splitlines(), updated.splitlines(),
                fromfile=f"baseline/{target}/{name}", tofile=f"current/{target}/{name}",
            )))
    print(f"Bytecode matched for {checked} classes; {len(failures)} unexpected changes")
    if failures:
        raise SystemExit(1)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("action", choices=["capture", "compare"])
    parser.add_argument("directory", type=Path)
    parser.add_argument("--renames", type=Path, help="JSON object mapping old names to new names")
    parser.add_argument("--allow-changed", action="append", default=[], help="Exact class name for a deliberate structural change")
    args = parser.parse_args()
    current = snapshot()
    path = args.directory / "named-bytecode.json"
    if args.action == "capture":
        args.directory.mkdir(parents=True, exist_ok=True)
        if path.exists():
            raise SystemExit(f"Baseline already exists: {path}")
        path.write_text(json.dumps(current, indent=2) + "\n")
        (args.directory / "resources.json").write_text(json.dumps(resource_snapshot(), indent=2) + "\n")
        print(f"Captured bytecode from {len(current)} target modules in {path}")
    else:
        renames = json.loads(args.renames.read_text()) if args.renames else {}
        compare(json.loads(path.read_text()), current, renames, set(args.allow_changed))
        resource_file = args.directory / "resources.json"
        before_resources = json.loads(resource_file.read_text()) if resource_file.exists() else resource_snapshot(args.directory)
        before_resources = {normalize(name, renames): normalize(body, renames) for name, body in before_resources.items()}
        after_resources = {normalize(name, renames): normalize(body, renames) for name, body in resource_snapshot().items()}
        changed_resources = [name for name in before_resources.keys() | after_resources.keys()
                             if before_resources.get(name) != after_resources.get(name)]
        if changed_resources:
            raise SystemExit("Unexpected resource changes:\n" + "\n".join(sorted(changed_resources)))
        print(f"Jar resources matched for {len(after_resources)} entries")


if __name__ == "__main__":
    main()
