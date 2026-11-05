* Consolidate all eight maintained Minecraft branches into one Stonecutter project.
* Share Java sources across Fabric, Forge, and NeoForge builds.
* Migrate the older Yarn targets to official Mojang mappings.
* Add checks for distributable jars and a CI matrix for every maintained target.
* Publish the fork under StackAnvil with distinct version names and repository links.
* Organize rendering sources into shared, legacy, modern, and single-version implementations.
* Normalize internal names and keep namespace changes in reversible Stonecutter replacements.
* Extract sign-atlas population from the sign Mixin and keep rendering state restoration with the draw operation.
* Add bytecode and jar-resource comparisons for mechanical refactors.
* Fix development dependency loading and stale Mixin selectors from the Yarn migration.
