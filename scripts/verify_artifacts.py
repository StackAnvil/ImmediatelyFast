"""Verify the distributable jars for the selected Stonecutter targets."""

import argparse
import json
import struct
import tomllib
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
LABELS = {"fabric": "Fabric", "forge": "Forge", "neoforge": "NeoForge"}


def verify_jar(version, target, loader):
    release = target["maven_version"].replace("-SNAPSHOT", "-stackanvil.1-SNAPSHOT")
    path = ROOT / loader / "versions" / version / "build/libs"
    path /= f"ImmediatelyFast-{LABELS[loader]}-{release}.jar"
    with zipfile.ZipFile(path) as jar:
        names = set(jar.namelist())
        assert any(name.startswith("LICENSE") for name in names), f"{path}: missing license"
        assert "assets/immediatelyfast/icon.png" in names, f"{path}: missing icon"
        assert "assets/immediatelyfast/icon-full.png" not in names, f"{path}: unneeded full icon"
        for name in names:
            if name.startswith("net/raphimc/") and name.endswith(".class"):
                magic, _, major = struct.unpack(">IHH", jar.read(name)[:8])
                assert magic == 0xCAFEBABE
                assert major == int(target["java_version"]) + 44, f"{path}: wrong Java version for {name}"

        service = "META-INF/services/net.raphimc.immediatelyfast.service.PlatformService"
        providers = [line.split("#", 1)[0].strip() for line in jar.read(service).decode().splitlines()]
        providers = [name for name in providers if name]
        expected_provider = f"net.raphimc.immediatelyfast.{loader}.{LABELS[loader]}PlatformService"
        assert providers == [expected_provider], f"{path}: wrong platform service: {providers}"
        assert expected_provider.replace(".", "/") + ".class" in names
        assert "net/raphimc/immediatelyfast/ImmediatelyFast.class" in names

        configs = ["immediatelyfast-common.mixins.json", f"immediatelyfast-{loader}.mixins.json"]
        for config in configs:
            metadata = json.loads(jar.read(config))
            mixins = metadata.get("mixins", []) + metadata.get("client", [])
            for mixin in mixins:
                cls = (metadata["package"] + "." + mixin).replace(".", "/") + ".class"
                assert cls in names, f"{path}: missing Mixin class {cls}"
            if "plugin" in metadata:
                assert metadata["plugin"].replace(".", "/") + ".class" in names

        if loader == "fabric":
            metadata = json.loads(jar.read("fabric.mod.json"))
            assert metadata["id"] == "immediatelyfast"
            assert metadata["version"] == release
            assert metadata["environment"] == "client"
            assert metadata["mixins"] == configs
            assert metadata["accessWidener"] == target["access_file"]
            assert metadata["accessWidener"] in names
            assert metadata["contact"]["sources"] == "https://github.com/StackAnvil/ImmediatelyFast"
            for dependency in metadata.get("jars", []):
                assert dependency["file"] in names, f"{path}: missing nested dependency"
        else:
            descriptor = "META-INF/neoforge.mods.toml"
            if loader == "forge" or version == "1.20.4":
                descriptor = "META-INF/mods.toml"
            metadata = tomllib.loads(jar.read(descriptor).decode())
            mod = next(mod for mod in metadata["mods"] if mod["modId"] == "immediatelyfast")
            assert mod["version"] == release
            assert metadata["issueTrackerURL"] == "https://github.com/StackAnvil/ImmediatelyFast/issues"
            if "mixins" in metadata:
                assert [mixin["config"] for mixin in metadata["mixins"]] == configs
            else:
                manifest = jar.read("META-INF/MANIFEST.MF").decode().replace("\r\n ", "")
                assert f"MixinConfigs: {','.join(configs)}" in manifest
    print(f"Verified {version} {LABELS[loader]}")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("versions", nargs="*", help="Minecraft targets; defaults to the full matrix")
    args = parser.parse_args()
    matrix = json.loads((ROOT / "gradle/versions.json").read_text())
    for version in args.versions or matrix:
        for loader in matrix[version]["loaders"]:
            verify_jar(version, matrix[version], loader)


if __name__ == "__main__":
    main()
