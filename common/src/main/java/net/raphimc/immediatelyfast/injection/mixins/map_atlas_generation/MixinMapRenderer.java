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

//? if <=26.1.2 {
/*package net.raphimc.immediatelyfast.injection.mixins.map_atlas_generation;

import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if (>=1.21.1 && <=26.1.2) {
/^import net.minecraft.world.level.saveddata.maps.MapId;
^///?}
//? if (>=1.21.4 && <=26.1.2) {
/^import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.resources.MapTextureManager;
import net.raphimc.immediatelyfast.injection.interfaces.IMapRenderState;
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.ATLAS_SIZE;
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.MAP_SIZE;
^///?}
//? if (>=1.21.10 && <=26.1.2) {
/^import net.minecraft.client.renderer.SubmitNodeCollector;
import org.spongepowered.asm.mixin.injection.ModifyArg;
^///?}
//? if <=1.21.1 {
/^import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.gui.MapRenderer;
import net.raphimc.immediatelyfast.injection.interfaces.IMapRenderer;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
^///?}
//? if =1.21.4 {
/^import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
^///?}

//? if (>=1.21.10 && <=26.1.2) {
/^@Mixin(MapRenderer.class)
public abstract class MixinMapRenderer {
^///?}
//? if =1.21.4 {
/^@Mixin(value = MapRenderer.class, priority = 1100) // Workaround for Porting-Lib which relies on the LVT to be intact
public abstract class MixinMapRenderer {
^///?}
//? if <=1.21.1 {
/^@Mixin(MapRenderer.class)
public abstract class MixinMapRenderer implements IMapRenderer {
^///?}
    //? if (>=1.21.4 && <=26.1.2) {
    /^@Shadow
    @Final
    private MapTextureManager mapTextureManager;
    ^///?}

    //? if (>=1.21.10 && <=26.1.2) {
    /^@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitCustomGeometry(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;)V", ordinal = 0))
    private SubmitNodeCollector.CustomGeometryRenderer modifyTextureCoordinates(final SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer, final @Local(argsOnly = true) MapRenderState mapRenderState, final @Local(argsOnly = true) int lightCoords) {
        final IMapRenderState immediatelyFast$mapRenderState = (IMapRenderState) mapRenderState;
        if (immediatelyFast$mapRenderState.immediatelyFast$getAtlasTexture() != null && immediatelyFast$mapRenderState.immediatelyFast$getAtlasTexture().getTextureId().equals(mapRenderState.texture)) {
            final float u1 = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
            final float u2 = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
            final float v1 = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            final float v2 = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
            return (matrix, vertexConsumer) -> {
                vertexConsumer.addVertex(matrix, 0F, MAP_SIZE, -0.01F).setColor(-1).setUv(u1, v2).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, MAP_SIZE, MAP_SIZE, -0.01F).setColor(-1).setUv(u2, v2).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, MAP_SIZE, 0F, -0.01F).setColor(-1).setUv(u2, v1).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, 0F, 0F, -0.01F).setColor(-1).setUv(u1, v1).setLight(lightCoords);
            };
        } else {
            return customGeometryRenderer;
        }
    }
    ^///?}

    //? if =1.21.4 {
    /^@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setUv(FF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0), to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setLight(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 3)))
    private VertexConsumer modifyTextureCoordinates(final VertexConsumer instance, float u, float v, final @Local(argsOnly = true) MapRenderState mapRenderState) {
        final IMapRenderState immediatelyFast$mapRenderState = (IMapRenderState) mapRenderState;
        if (immediatelyFast$mapRenderState.immediatelyFast$getAtlasTexture() != null && immediatelyFast$mapRenderState.immediatelyFast$getAtlasTexture().getIdentifier().equals(mapRenderState.texture)) {
            if (u == 0 && v == 1) {
                u = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
                v = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
            } else if (u == 1 && v == 1) {
                u = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
                v = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
            } else if (u == 1 && v == 0) {
                u = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
                v = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            } else if (u == 0 && v == 0) {
                u = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
                v = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            }
        }
        return instance.setUv(u, v);
    }
    ^///?}

    //? if (>=1.21.4 && <=26.1.2) {
    /^@Inject(method = "extractRenderState", at = @At("RETURN"))
    private void initAtlasParameters(final MapId mapId, final MapItemSavedData mapState, final MapRenderState mapRenderState, final CallbackInfo ci) {
        final int packedLocation = ((IMapTextureManager) this.mapTextureManager).immediatelyFast$getAtlasMapping(mapId.id());
        if (packedLocation == -1) {
            ImmediatelyFast.LOGGER.warn("Map " + mapId.id() + " is not in an atlas");
            // Leave atlasTexture null to indicate that this map is not in an atlas, and it should use the vanilla system instead
            return;
        }
        final IMapRenderState immediatelyFast$mapRenderState = (IMapRenderState) mapRenderState;
        immediatelyFast$mapRenderState.immediatelyFast$setAtlasX(((packedLocation >> 8) & 0xFF) * MAP_SIZE);
        immediatelyFast$mapRenderState.immediatelyFast$setAtlasY((packedLocation & 0xFF) * MAP_SIZE);
        immediatelyFast$mapRenderState.immediatelyFast$setAtlasTexture(((IMapTextureManager) this.mapTextureManager).immediatelyFast$getMapAtlasTexture(packedLocation >> 16));
        if (immediatelyFast$mapRenderState.immediatelyFast$getAtlasTexture() == null) {
            throw new IllegalStateException("getMapAtlasTexture returned null for packedLocation " + packedLocation + " (map " + mapId.id() + ")");
        }
    }
    ^///?}

    //? if <=1.21.1 {
    /^@Unique
    private final Int2ObjectMap<MapAtlasTexture> immediatelyFast$mapAtlasTextures = new Int2ObjectOpenHashMap<>();

    @Unique
    private final Int2IntMap immediatelyFast$mapIdToAtlasMapping = new Int2IntOpenHashMap();

    @Inject(method = "resetData", at = @At("RETURN"))
    private void clearMapAtlasTextures(final CallbackInfo ci) {
        for (MapAtlasTexture texture : this.immediatelyFast$mapAtlasTextures.values()) {
            texture.close();
        }

        this.immediatelyFast$mapAtlasTextures.clear();
        this.immediatelyFast$mapIdToAtlasMapping.clear();
    }

    //? if =1.21.1 {
    /^¹@Inject(method = "getOrCreateMapInstance", at = @At("HEAD"))
    private void createMapAtlasTexture(final MapId mapId, final MapItemSavedData state, final CallbackInfoReturnable<MapRenderer.MapInstance> cir) {
    ¹^///?}
    //? if =1.20.4 {
    /^¹@Inject(method = "getOrCreateMapInstance", at = @At("HEAD"))
    private void createMapAtlasTexture(final int id, final MapItemSavedData state, final CallbackInfoReturnable<MapRenderer.MapInstance> cir) {
    ¹^///?}
        //? if =1.21.1 {
        /^¹this.immediatelyFast$mapIdToAtlasMapping.computeIfAbsent(mapId.id(), k -> {
            for (MapAtlasTexture atlasTexture : this.immediatelyFast$mapAtlasTextures.values()) {
                final int location = atlasTexture.getNextMapLocation();
                if (location != -1) {
                    return location;
                }
            }

            final MapAtlasTexture atlasTexture = new MapAtlasTexture(this.immediatelyFast$mapAtlasTextures.size());
            this.immediatelyFast$mapAtlasTextures.put(atlasTexture.getId(), atlasTexture);
            return atlasTexture.getNextMapLocation();
        });
        ¹^///?}
        //? if =1.20.4 {
        /^¹this.immediatelyFast$mapIdToAtlasMapping.computeIfAbsent(id, k -> {
            for (MapAtlasTexture atlasTexture : this.immediatelyFast$mapAtlasTextures.values()) {
                final int location = atlasTexture.getNextMapLocation();
                if (location != -1) {
                    return location;
                }
            }

            final MapAtlasTexture atlasTexture = new MapAtlasTexture(this.immediatelyFast$mapAtlasTextures.size());
            this.immediatelyFast$mapAtlasTextures.put(atlasTexture.getId(), atlasTexture);
            return atlasTexture.getNextMapLocation();
        });
        ¹^///?}
    }

    @Override
    public MapAtlasTexture immediatelyFast$getMapAtlasTexture(final int id) {
        return this.immediatelyFast$mapAtlasTextures.get(id);
    }

    @Override
    public int immediatelyFast$getAtlasMapping(final int mapId) {
        return this.immediatelyFast$mapIdToAtlasMapping.getOrDefault(mapId, -1);
    }
    ^///?}
}
*///?}
