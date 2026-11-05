/*
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
 */

//? if (>=1.21.4 && <=26.1.2) {
/*package net.raphimc.immediatelyfast.injection.mixins.map_atlas_generation;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import java.util.function.Supplier;
import net.lenni0451.reflect.Objects;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.MapTextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.MAP_SIZE;
//? if (>=1.21.10 && <=26.1.2) {
/^import com.mojang.blaze3d.systems.RenderSystem;
^///?}

//? if (>=1.21.10 && <=26.1.2) {
/^@Mixin(MapTextureManager.MapInstance.class)
public abstract class MixinMapTextureManager_MapInstance {
^///?}
//? if =1.21.4 {
/^@Mixin(value = MapTextureManager.MapInstance.class, priority = 1100) // Workaround for Porting-Lib which relies on the LVT to be intact
public abstract class MixinMapTextureManager_MapInstance {
^///?}
    @Shadow
    private MapItemSavedData data;

    @Mutable
    @Shadow
    @Final
    private DynamicTexture texture;

    @Shadow
    private boolean requiresUpload;

    @Shadow
    @Final
    @Mutable
    Identifier location;

    @Unique
    private static final DynamicTexture DUMMY_TEXTURE = Objects.allocate(DynamicTexture.class);

    @Unique
    private int immediatelyFast$atlasX;

    @Unique
    private int immediatelyFast$atlasY;

    @Unique
    private MapAtlasTexture immediatelyFast$atlasTexture;

    //? if (>=1.21.10 && <=26.1.2) {
    /^//? if (>=1.21.11 && <=26.1.2) {
    /^¹@Redirect(method = "<init>", at = @At(value = "NEW", target = "(Ljava/util/function/Supplier;IIZ)Lnet/minecraft/client/renderer/texture/DynamicTexture;"))
    private DynamicTexture initAtlasParametersAndDontAllocateTexture(final Supplier<String> label, final int width, final int height, final boolean useCalloc, final @Local(argsOnly = true) MapTextureManager mapTextureManager, final @Local(argsOnly = true) int id) {
    ¹^///?}
    //? if =1.21.10 {
    /^¹@Redirect(method = "<init>", at = @At(value = "NEW", target = "(Ljava/util/function/Supplier;IIZ)Lnet/minecraft/client/renderer/texture/DynamicTexture;"))
    private DynamicTexture initAtlasParametersAndDontAllocateTexture(final Supplier<String> nameSupplier, final int width, final int height, final boolean useStb, final @Local(argsOnly = true) MapTextureManager mapTextureManager, final @Local(argsOnly = true) int id) {
    ¹^///?}
        final int packedLocation = ((IMapTextureManager) mapTextureManager).immediatelyFast$getAtlasMapping(id);
        if (packedLocation == -1) {
            ImmediatelyFast.LOGGER.warn("Map " + id + " is not in an atlas");
            //? if (>=1.21.11 && <=26.1.2) {
            /^¹return new DynamicTexture(label, width, height, useCalloc);
            ¹^///?}
            //? if =1.21.10 {
            /^¹return new DynamicTexture(nameSupplier, width, height, useStb);
            ¹^///?}
        }
        this.immediatelyFast$atlasX = ((packedLocation >> 8) & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasY = (packedLocation & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasTexture = ((IMapTextureManager) mapTextureManager).immediatelyFast$getMapAtlasTexture(packedLocation >> 16);
        if (this.immediatelyFast$atlasTexture == null) {
            throw new IllegalStateException("getMapAtlasTexture returned null for packedLocation " + packedLocation + " (map " + id + ")");
        }
        return DUMMY_TEXTURE;
    }
    ^///?}

    //? if =1.21.4 {
    /^@Redirect(method = "<init>", at = @At(value = "NEW", target = "(IIZ)Lnet/minecraft/client/renderer/texture/DynamicTexture;"))
    private DynamicTexture initAtlasParametersAndDontAllocateTexture(final int width, final int height, final boolean useMipmaps, final @Local(argsOnly = true) MapTextureManager mapTextureManager, final @Local(argsOnly = true) int id) {
        final int packedLocation = ((IMapTextureManager) mapTextureManager).immediatelyFast$getAtlasMapping(id);
        if (packedLocation == -1) {
            ImmediatelyFast.LOGGER.warn("Map " + id + " is not in an atlas");
            // Leave atlasTexture null to indicate that this map is not in an atlas, and it should use the vanilla system instead
            return new DynamicTexture(width, height, useMipmaps);
        }

        this.immediatelyFast$atlasX = ((packedLocation >> 8) & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasY = (packedLocation & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasTexture = ((IMapTextureManager) mapTextureManager).immediatelyFast$getMapAtlasTexture(packedLocation >> 16);
        if (this.immediatelyFast$atlasTexture == null) {
            throw new IllegalStateException("getMapAtlasTexture returned null for packedLocation " + packedLocation + " (map " + id + ")");
        }

        return DUMMY_TEXTURE;
    }
    ^///?}

    //? if (>=1.21.11 && <=26.1.2) {
    /^@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"))
    private void getAtlasTextureIdentifier(final TextureManager instance, final Identifier path, final AbstractTexture texture) {
    ^///?}
    //? if (>=1.21.4 && <=1.21.10) {
    /^@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"))
    private void getAtlasTextureIdentifier(final TextureManager instance, final Identifier id, final AbstractTexture texture) {
    ^///?}
        if (this.immediatelyFast$atlasTexture != null) {
            this.texture = null;
            //? if (>=1.21.10 && <=26.1.2) {
            /^this.location = this.immediatelyFast$atlasTexture.getTextureId();
            ^///?}
            //? if =1.21.4 {
            /^this.location = this.immediatelyFast$atlasTexture.getIdentifier();
            ^///?}
        } else {
            //? if (>=1.21.11 && <=26.1.2) {
            /^instance.register(path, texture);
            ^///?}
            //? if (>=1.21.4 && <=1.21.10) {
            /^instance.register(id, texture);
            ^///?}
        }
    }

    @Inject(method = "updateTextureIfNeeded", at = @At("HEAD"), cancellable = true)
    private void updateAtlasTexture(final CallbackInfo ci) {
        if (this.requiresUpload && this.immediatelyFast$atlasTexture != null) {
            ci.cancel();
            final DynamicTexture atlasTexture = this.immediatelyFast$atlasTexture.getTexture();
            final NativeImage atlasImage = atlasTexture.getPixels();
            if (atlasImage == null) {
                throw new IllegalStateException("Atlas texture has already been closed");
            }
            for (int x = 0; x < MAP_SIZE; x++) {
                for (int y = 0; y < MAP_SIZE; y++) {
                    final int i = x + y * MAP_SIZE;
                    atlasImage.setPixel(this.immediatelyFast$atlasX + x, this.immediatelyFast$atlasY + y, MapColor.getColorFromPackedId(this.data.colors[i]));
                }
            }
            //? if (>=1.21.10 && <=26.1.2) {
            /^RenderSystem.getDevice().createCommandEncoder().writeToTexture(atlasTexture.getTexture(), atlasImage, 0, 0, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, MAP_SIZE, MAP_SIZE, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY);
            ^///?}
            //? if =1.21.4 {
            /^atlasTexture.bind();
            atlasImage.upload(0, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, MAP_SIZE, MAP_SIZE, false);
            ^///?}
            this.requiresUpload = false;
        }
    }

    @Inject(method = "close", at = @At("HEAD"), cancellable = true)
    private void dontCloseDummyTexture(final CallbackInfo ci) {
        if (this.immediatelyFast$atlasTexture != null) {
            ci.cancel();
        }
    }
}
*///?}
