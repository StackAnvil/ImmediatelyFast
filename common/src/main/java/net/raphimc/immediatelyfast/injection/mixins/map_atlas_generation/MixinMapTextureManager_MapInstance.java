//? if >=1.21.4 {
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
package net.raphimc.immediatelyfast.injection.mixins.map_atlas_generation;

//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
*/
//?}
//? if >=1.21.10 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.lenni0451.reflect.Objects;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.minecraft.client.renderer.texture.TextureManager;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.client.resources.MapTextureManager;
//?}
//? if (>=1.21.11 && <=26.1.2) {
/*import net.minecraft.resources.Identifier;
*/
//?}
//? if (>=1.21.4 && <=1.21.10) {
/*import net.minecraft.resources.ResourceLocation;
*/
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.minecraft.world.level.material.MapColor;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
//?}
//? if >=26.2 {
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
*/
//?}
//? if >=1.21.4 {
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import org.spongepowered.asm.mixin.Mutable;
*/
//?}
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import org.spongepowered.asm.mixin.injection.Redirect;
*/
//?}
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//?}
//? if >=26.2 {
@Mixin(targets = "net.minecraft.client.resources.MapTextureManager$MapInstance")
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import java.util.function.Supplier;

*/
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.MAP_SIZE;

*/
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*@Mixin(MapTextureManager.MapInstance.class)
*/
//?}
//? if =1.21.4 {
/*@Mixin(value = MapTextureManager.MapInstance.class, priority = 1100) // Workaround for Porting-Lib which relies on the LVT to be intact
*/
//?}
//? if >=1.21.4 {
public abstract class MixinMapTextureManager_MapInstance {

//?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    @Shadow
    private MapItemSavedData data;

    @Mutable
*/
    //?}
    //? if >=1.21.4 {
    @Shadow
    @Final
    private DynamicTexture texture;

    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    @Shadow
    private boolean requiresUpload;

    @Shadow
    @Final
    @Mutable
*/
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    Identifier location;
*/
    //?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    ResourceLocation location;
*/
    //?}

    //? if >=1.21.4 {
    @Unique
    //?}
    //? if >=26.2 {
    private AbstractTexture immediatelyFast$atlasTexture;
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    private static final DynamicTexture DUMMY_TEXTURE = Objects.allocate(DynamicTexture.class);
*/
    //?}
//? if >=1.21.4 {

    @Unique
    private int immediatelyFast$atlasX;

    @Unique
    private int immediatelyFast$atlasY;

//?}
    //? if >=26.2 {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void getAtlasParameters(final MapTextureManager mapTextureManager, final int id, final MapItemSavedData data, final CallbackInfo ci) {
        final int location = ((IMapTextureManager) mapTextureManager).immediatelyFast$getAtlasLocation(id);
        if (location != -1) {
            this.immediatelyFast$atlasTexture = ((IMapTextureManager) mapTextureManager).immediatelyFast$getAtlas(MapAtlas.getAtlasIdFromLocation(location)).getTexture();
            this.immediatelyFast$atlasX = MapAtlas.getAtlasXFromLocation(location) * MapAtlas.MAP_SIZE;
            this.immediatelyFast$atlasY = MapAtlas.getAtlasYFromLocation(location) * MapAtlas.MAP_SIZE;
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    @Unique
    private MapAtlasTexture immediatelyFast$atlasTexture;

*/
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(Ljava/util/function/Supplier;IIZ)Lnet/minecraft/client/renderer/texture/DynamicTexture;"))
*/
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private DynamicTexture initAtlasParametersAndDontAllocateTexture(Supplier<String> label, int width, int height, boolean useCalloc, @Local(argsOnly = true) MapTextureManager mapTextureManager, @Local(argsOnly = true) int id) {
*/
    //?}
    //? if =1.21.10 {
/*    private DynamicTexture initAtlasParametersAndDontAllocateTexture(Supplier<String> nameSupplier, int width, int height, boolean useStb, @Local(argsOnly = true) MapTextureManager mapTextureManager, @Local(argsOnly = true) int id) {
*/
    //?}
    //? if =1.21.4 {
/*    @Redirect(method = "<init>", at = @At(value = "NEW", target = "(IIZ)Lnet/minecraft/client/renderer/texture/DynamicTexture;"))
    private DynamicTexture initAtlasParametersAndDontAllocateTexture(int width, int height, boolean useMipmaps, @Local(argsOnly = true) MapTextureManager mapTextureManager, @Local(argsOnly = true) int id) {
*/
    //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        final int packedLocation = ((IMapTextureManager) mapTextureManager).immediatelyFast$getAtlasMapping(id);
        if (packedLocation == -1) {
            ImmediatelyFast.LOGGER.warn("Map " + id + " is not in an atlas");
            // Leave atlasTexture null to indicate that this map is not in an atlas, and it should use the vanilla system instead
*/
        //?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            return new DynamicTexture(label, width, height, useCalloc);
*/
            //?}
            //? if =1.21.10 {
/*            return new DynamicTexture(nameSupplier, width, height, useStb);
*/
            //?}
            //? if =1.21.4 {
/*            return new DynamicTexture(width, height, useMipmaps);
*/
            //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        }

        this.immediatelyFast$atlasX = ((packedLocation >> 8) & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasY = (packedLocation & 0xFF) * MAP_SIZE;
        this.immediatelyFast$atlasTexture = ((IMapTextureManager) mapTextureManager).immediatelyFast$getMapAtlasTexture(packedLocation >> 16);
        if (this.immediatelyFast$atlasTexture == null) {
            throw new IllegalStateException("getMapAtlasTexture returned null for packedLocation " + packedLocation + " (map " + id + ")");
        }

        return DUMMY_TEXTURE;
    }

*/
        //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"))
    private void getAtlasTextureIdentifier(TextureManager instance, Identifier path, AbstractTexture texture) {
*/
    //?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"))
    private void getAtlasTextureIdentifier(TextureManager instance, ResourceLocation id, AbstractTexture texture) {
*/
    //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        if (this.immediatelyFast$atlasTexture != null) {
            this.texture = null; // Don't leave the texture field pointing to the uninitialized dummy texture
*/
        //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            this.location = this.immediatelyFast$atlasTexture.getTextureId();
*/
            //?}
            //? if =1.21.4 {
/*            this.location = this.immediatelyFast$atlasTexture.getIdentifier();
*/
            //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        } else {
*/
        //?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            instance.register(path, texture);
*/
            //?}
            //? if (>=1.21.4 && <=1.21.10) {
/*            instance.register(id, texture);
*/
            //?}
        //? if >=1.21.4 {
        }
    }

        //?}
    //? if >=26.2 {
    @Inject(method = "updateTextureIfNeeded", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;upload()V", shift = At.Shift.AFTER))
    private void updateAtlasTexture(final CallbackInfo ci) {
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    @Inject(method = "updateTextureIfNeeded", at = @At("HEAD"), cancellable = true)
    private void updateAtlasTexture(CallbackInfo ci) {
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
*/
    //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            RenderSystem.getDevice().createCommandEncoder().writeToTexture(atlasTexture.getTexture(), atlasImage, 0, 0, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, MAP_SIZE, MAP_SIZE, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY);
*/
            //?}
            //? if =1.21.4 {
/*            atlasTexture.bind();
            atlasImage.upload(0, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY, MAP_SIZE, MAP_SIZE, false);
*/
            //?}
            //? if (>=1.21.4 && <=26.1.2) {
/*            this.requiresUpload = false;
        }
    }

    @Inject(method = "close", at = @At("HEAD"), cancellable = true)
    private void dontCloseDummyTexture(CallbackInfo ci) {
*/
            //?}
        //? if >=1.21.4 {
        if (this.immediatelyFast$atlasTexture != null) {
        //?}
            //? if >=26.2 {
            RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.immediatelyFast$atlasTexture.getTexture(), this.texture.getPixels(), 0, 0, this.immediatelyFast$atlasX, this.immediatelyFast$atlasY);
            //?}
            //? if (>=1.21.4 && <=26.1.2) {
/*            ci.cancel();
*/
            //?}
        //? if >=1.21.4 {
        }
    }

}
        //?}
