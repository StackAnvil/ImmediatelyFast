//? if =1.21.4 {
/*/^
 * This file is part of ImmediatelyFast - https://github.com/RaphiMC/ImmediatelyFast
 * Copyright (C) 2023-2026 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ^/
package net.raphimc.immediatelyfast.injection.mixins.core.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.compat.CoreShaderBlacklist;
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastResourcePackMetadata;
import net.raphimc.immediatelyfast.injection.interfaces.IShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.shaders.CompiledShader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Mixin(ShaderManager.class)
public abstract class MixinShaderManager {

    @Shadow
    private ShaderManager.CompilationCache compilationCache;

    @Inject(method = "apply(Lnet/minecraft/client/renderer/ShaderManager$Configs;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("RETURN"))
    private void checkForCoreShaderModifications(ShaderManager.Configs definitions, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        if (ImmediatelyFast.config.experimental_disable_resource_pack_conflict_handling) {
            return;
        }

        PackResources resourcePackWhichBreaksFontAtlasResizing = null;
        PackResources resourcePackWhichBreaksHudBatching = null;
        PackResources resourcePackWhichBreaksScreenBatching = null;
        try {
            final Set<PackResources> breakingResourcePacks = new HashSet<>();
            for (ShaderProgram shaderProgramKey : CoreShaderBlacklist.getBlacklist()) {
                if (this.compilationCache.getOrCompileProgram(shaderProgramKey) instanceof IShaderProgram mixinShaderProgram) {
                    if (mixinShaderProgram.immediatelyFast$getVertexShader() == null || mixinShaderProgram.immediatelyFast$getFragmentShader() == null) {
                        continue;
                    }
                    final ResourceLocation vertexShaderIdentifier = CompiledShader.Type.VERTEX.idConverter().idToFile(mixinShaderProgram.immediatelyFast$getVertexShader().getId());
                    final PackResources vertexShaderResourcePack = resourceManager.getResource(vertexShaderIdentifier).map(Resource::source).orElse(null);
                    if (vertexShaderResourcePack != null && !vertexShaderResourcePack.equals(Minecraft.getInstance().getVanillaPackResources())) {
                        breakingResourcePacks.add(vertexShaderResourcePack);
                    }
                    final ResourceLocation fragmentShaderIdentifier = CompiledShader.Type.FRAGMENT.idConverter().idToFile(mixinShaderProgram.immediatelyFast$getFragmentShader().getId());
                    final PackResources fragmentShaderResourcePack = resourceManager.getResource(fragmentShaderIdentifier).map(Resource::source).orElse(null);
                    if (fragmentShaderResourcePack != null && !fragmentShaderResourcePack.equals(Minecraft.getInstance().getVanillaPackResources())) {
                        breakingResourcePacks.add(fragmentShaderResourcePack);
                    }
                }
            }
            for (PackResources resourcePack : breakingResourcePacks) {
                ImmediatelyFastResourcePackMetadata metadata = resourcePack.getMetadataSection(ImmediatelyFastResourcePackMetadata.SERIALIZER);
                if (metadata == null) {
                    metadata = ImmediatelyFastResourcePackMetadata.DEFAULT;
                }
                if (!metadata.compatibleFeatures().contains("font_atlas_resizing")) {
                    resourcePackWhichBreaksFontAtlasResizing = resourcePack;
                }
                if (!metadata.compatibleFeatures().contains("hud_batching")) {
                    resourcePackWhichBreaksHudBatching = resourcePack;
                }
                if (!metadata.compatibleFeatures().contains("experimental_screen_batching")) {
                    resourcePackWhichBreaksScreenBatching = resourcePack;
                }
            }
        } catch (ShaderManager.CompilationException | IOException e) {
            ImmediatelyFast.LOGGER.error("Failed to check for core shader modifications", e);
        }

        if (ImmediatelyFast.config.font_atlas_resizing) {
            if (resourcePackWhichBreaksFontAtlasResizing != null) {
                ImmediatelyFast.LOGGER.warn("Resource pack " + resourcePackWhichBreaksFontAtlasResizing.packId() + " is not compatible with font atlas resizing. Temporarily disabling font atlas resizing.");
                if (ImmediatelyFast.runtimeConfig.font_atlas_resizing) {
                    ImmediatelyFast.runtimeConfig.font_atlas_resizing = false;
                    this.immediatelyFast$reloadFontStorages();
                }
            } else if (!ImmediatelyFast.runtimeConfig.font_atlas_resizing) {
                ImmediatelyFast.LOGGER.info("Re-enabling font atlas resizing because no incompatible resource packs are loaded.");
                ImmediatelyFast.runtimeConfig.font_atlas_resizing = true;
                this.immediatelyFast$reloadFontStorages();
            }
        }
        if (ImmediatelyFast.config.hud_batching) {
            if (resourcePackWhichBreaksHudBatching != null) {
                ImmediatelyFast.LOGGER.warn("Resource pack " + resourcePackWhichBreaksHudBatching.packId() + " is not compatible with HUD batching. Temporarily disabling HUD batching.");
                ImmediatelyFast.runtimeConfig.hud_batching = false;
            } else if (!ImmediatelyFast.runtimeConfig.hud_batching) {
                ImmediatelyFast.LOGGER.info("Re-enabling HUD batching because no incompatible resource packs are loaded.");
                ImmediatelyFast.runtimeConfig.hud_batching = true;
            }
        }
        if (ImmediatelyFast.config.experimental_screen_batching) {
            if (resourcePackWhichBreaksScreenBatching != null) {
                ImmediatelyFast.LOGGER.warn("Resource pack " + resourcePackWhichBreaksScreenBatching.packId() + " is not compatible with experimental screen batching. Temporarily disabling experimental screen batching.");
                ImmediatelyFast.runtimeConfig.experimental_screen_batching = false;
            } else if (!ImmediatelyFast.runtimeConfig.experimental_screen_batching) {
                ImmediatelyFast.LOGGER.info("Re-enabling experimental screen batching because no incompatible resource packs are loaded.");
                ImmediatelyFast.runtimeConfig.experimental_screen_batching = true;
            }
        }
    }

    @Unique
    private void immediatelyFast$reloadFontStorages() {
        // Force reload the font storages to rebuild the font atlas textures
        Minecraft.getInstance().fontManager.updateOptions(Minecraft.getInstance().options);
    }

}
*/
//?}
