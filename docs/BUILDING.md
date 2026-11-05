# Building and maintaining the fork

The StackAnvil fork builds all maintained Minecraft versions from the `26.3` Git branch.
Stonecutter selects version-specific code from shared Java sources.
Fabric, Forge, and NeoForge keep separate entry points and loader metadata.
The layout follows Stonecutter's [branched multiloader setup](https://stonecutter.kikugie.dev/wiki/v2/guides/tips/multiloader).

## Build

Use JDK 25 to run the Gradle wrapper. Gradle selects JDK 17, 21, or 25 for each target.
The Foojay resolver downloads missing toolchains. Artifact verification also requires Python 3.11 or later.

To build and inspect all distributable jars, run:

```sh
./gradlew build verifyArtifacts
```

To build one Minecraft target and its supported loaders, run:

```sh
./gradlew -Pminecraft=1.21.1 build verifyArtifacts
```

The `minecraft` property also accepts a comma-separated list of targets.
It limits project configuration as well as compilation.
For example, `-Pminecraft=1.21.11,26.3` selects those two targets.

To build one loader, select its Gradle task:

```sh
./gradlew -Pminecraft=1.21.1 :fabric:1.21.1:build
```

Find jars in `<loader>/versions/<target>/build/libs/`.
Use the jar without a `-dev` classifier for installation.
The artifact checks inspect platform services, Mixin classes, metadata, licenses, and Java bytecode versions.
These checks do not test rendering inside Minecraft.

## Supported targets

`gradle/versions.json` records each target, its dependencies, and the exact upstream commit used for migration.
Targets represent the same Minecraft compatibility ranges as the maintained upstream branches.
Branches under `discontinued/` are outside this project.

| Upstream branch | Build target | Compatible Minecraft versions | Loaders | Java |
| --- | --- | --- | --- | --- |
| `1.20` | `1.20.4` | 1.20 through 1.20.4 | Fabric, Forge, NeoForge | 17 |
| `1.21` | `1.21.1` | 1.21, 1.21.1 | Fabric, Forge, NeoForge | 21 |
| `1.21.4` | `1.21.4` | 1.21.4 | Fabric, Forge, NeoForge | 21 |
| `1.21.9` | `1.21.10` | 1.21.9, 1.21.10 | Fabric, NeoForge | 21 |
| `1.21.11` | `1.21.11` | 1.21.11 | Fabric, NeoForge | 21 |
| `26.1` | `26.1.2` | 26.1 through 26.1.2 | Fabric, NeoForge | 25 |
| `26.2` | `26.2` | 26.2 | Fabric, NeoForge | 25 |
| `26.3` | `26.3` | 26.3 | Fabric, NeoForge | 25 |

## Shared sources and mappings

Keep code shared across rendering generations in `common/src/main/java`.
The `common/src/main/legacy` directory supports 1.20.4, 1.21.1, and 1.21.4.
The `common/src/main/modern` directory supports 1.21.10 and later.
The `source_family` property in `gradle/versions.json` selects the additional source directory for each target.
Stonecutter processes both the shared directory and the selected family directory.

Put a class used by one target in `common/versions/<target>/src/main/java`.
Each target must compile exactly one implementation of a class.
If a local class has a shared counterpart, guard the shared class out for that target.
Local files do not safely override shared files when their target is active.

Loader entry points remain in their matching loader directories.
Stonecutter conditions select small differences within a rendering generation.
For example, `//? if >=26.1 {` starts a section for 26.1 and later, and `//?}` closes it.

All obfuscated targets use official Mojang mappings. The 1.21.11 target also retains its upstream Parchment layer.
Minecraft 26.1 and later use the names in the unobfuscated game.
The older Yarn sources, Mixin selectors, and access wideners were migrated with Loom before consolidation.
Yarn mapping patches are no longer required.
`immediatelyfast.mapping-conventions.gradle` reverses a small set of Mojang namespace changes for older targets.
It handles Java imports and Mixin descriptors with explicit regular expressions and reverse patterns.
Keep these replacements limited to namespace changes. Internal method names remain explicit in the source.

The default active version is 26.3. Its code remains readable without preprocessing.
Forge uses 1.21.4 as its active source view because it has no newer target.
Stonecutter comments out other versions and generates their sources during the build.
Builds with `-Pminecraft` use generated sources for every selected target and leave the shared sources unchanged.

To switch the active version for development, omit `-Pminecraft` and run:

```sh
./gradlew 'Set active project to 1.21.1'
```

Before committing shared sources, restore the active version:

```sh
./gradlew 'Reset active project'
```

Version-specific resources live in `<module>/versions/<target>/src/main/resources`.
Identical resources remain in `<module>/src/main/resources`.
This keeps strict JSON files and loader descriptors valid without source comments.
Generated sources and build outputs stay under each target's ignored `build` directory.

Release notes and upstream API documentation live in `versions/<target>/`.
Keep changes to common behavior in shared Java sources.
Use version-specific resources for changes to Mixin lists, access rules, or loader compatibility ranges.

## Refactoring checks

Before a mechanical refactor, build every target and save its named bytecode and jar resources:

```sh
./gradlew build verifyArtifacts
python3 scripts/compare_bytecode.py capture /path/to/baseline
```

After the refactor, rebuild and compare the outputs:

```sh
./gradlew build verifyArtifacts
python3 scripts/compare_bytecode.py compare /path/to/baseline
```

For intentional internal renames, pass `--renames /path/to/renames.json`.
The JSON object maps old names to new names.
The comparison ignores debug tables and constant-pool indices, but retains instructions, descriptors, and string constants.
It also compares jar resources, including Mixin lists and loader metadata.
Instruction comparisons do not validate Mixin annotation selectors.

For an intentional method extraction, pass `--allow-changed` with the exact class name and review that class separately.
Keep the allowed list small. Compile and restore active versions from both source families before committing.
Run the comparison tool's regression tests with `python3 -m unittest discover -s scripts -p 'test_*.py'`.
Rendering changes also require game tests with inventory, chat, signs, maps, and compatible Iris versions.
Enable experimental sign buffering in the test instance to exercise atlas population.
Use `-Dmixin.debug.countInjections=true -Dmixin.checks.interfaces=true` during these tests.
Run `MixinEnvironment.getCurrentEnvironment().audit()` on the render thread to load targets that Minecraft has not used yet.
For NeoForge 1.21.10, disable `enableB3DValidationLayer` in the test instance's `config/neoforge-client.toml`.
That development-only wrapper conflicts with the upstream implementation's direct OpenGL texture access.

## Fork publishing

Fork versions include `-stackanvil.1` before the snapshot suffix.
The Java packages and mod ID retain their upstream names for compatibility.
Mod metadata directs source links and bug reports to `StackAnvil/ImmediatelyFast`.

GitHub publishing targets the fork and uses the consolidated `26.3` branch.
Snapshot builds run the publishing plugin in dry-run mode.
Each target has a separate release tag that includes its Minecraft version.

CurseForge and Modrinth publishing require explicit fork project IDs.
Set `curseforge.project_id` or `modrinth.project_id` with the matching publishing token in your private Gradle properties.
The build does not use upstream project IDs for publishing.
The README download badges and archived API documentation still reference upstream releases.
