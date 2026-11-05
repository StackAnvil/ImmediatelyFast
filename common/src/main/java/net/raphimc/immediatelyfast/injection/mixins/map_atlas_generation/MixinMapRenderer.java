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

//? if >=1.21.4 {
import com.llamalad7.mixinextras.sugar.Local;
//?}
//? if =1.21.4 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.client.renderer.MapRenderer;
//?}
//? if >=1.21.10 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}
//? if >=1.21.4 {
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.resources.MapTextureManager;
//?}
//? if <=1.21.1 {
/*import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.gui.MapRenderer;

*///?}
//? if >=1.21.1 {
import net.minecraft.world.level.saveddata.maps.MapId;
//?}
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
//? if >=26.2 {
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;
*/
//?}
//? if >=1.21.4 {
import net.raphimc.immediatelyfast.injection.interfaces.IMapRenderState;
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
import org.spongepowered.asm.mixin.Final;
//?}
//? if <=1.21.1 {
/*import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
import net.raphimc.immediatelyfast.injection.interfaces.IMapRenderer;

*///?}
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.Shadow;
//?}
//? if <=1.21.1 {
/*import org.spongepowered.asm.mixin.Unique;

*///?}
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//? if >=1.21.10 {
import org.spongepowered.asm.mixin.injection.ModifyArg;
//?}
//? if =1.21.4 {
/*import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
*/
//?}
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.2 {
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas.ATLAS_SIZE;
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas.MAP_SIZE;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.ATLAS_SIZE;
import static net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture.MAP_SIZE;
*/
//?}
//? if <=1.21.1 {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

*///?}

//? if <=1.21.1 || >=1.21.10 {
@Mixin(MapRenderer.class)
//?}
//? if =1.21.4 {
/*@Mixin(value = MapRenderer.class, priority = 1100) // Workaround for Porting-Lib which relies on the LVT to be intact
*/
//?}
//? if >=1.21.4 {
public abstract class MixinMapRenderer {
//?}
//? if <=1.21.1 {
/*public abstract class MixinMapRenderer implements IMapRenderer {

*///?}

    //? if >=1.21.4 {
    @Shadow
    @Final
    private MapTextureManager mapTextureManager;
    //?}
    //? if <=1.21.1 {
    /*@Unique
    private final Int2ObjectMap<MapAtlasTexture> immediatelyFast$mapAtlasTextures = new Int2ObjectOpenHashMap<>();

    *///?}

    //? if >=1.21.11 {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitCustomGeometry(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;)V", ordinal = 0))
    //?}
    //? if >=26.2 {
    private SubmitNodeCollector.CustomGeometryRenderer modifyTextureCoordinates(final SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer, @Local(name = "mapRenderState", argsOnly = true) final MapRenderState mapRenderState, @Local(name = "lightCoords", argsOnly = true) final int lightCoords) {
        final IMapRenderState immediatelyFast$mapRenderState = (IMapRenderState) mapRenderState;
        if (immediatelyFast$mapRenderState.immediatelyFast$getAtlasTextureId() != null && immediatelyFast$mapRenderState.immediatelyFast$getAtlasTextureId().equals(mapRenderState.texture)) {
            final float u0 = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
            final float u1 = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
            final float v0 = (float) immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            final float v1 = (float) (immediatelyFast$mapRenderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private SubmitNodeCollector.CustomGeometryRenderer modifyTextureCoordinates(SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer, @Local(argsOnly = true) MapRenderState renderState, @Local(argsOnly = true) int light) {
*/
    //?}
    //? if =1.21.10 {
/*    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitCustom(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;)V", ordinal = 0))
    private SubmitNodeCollector.CustomGeometryRenderer drawAtlasTexture(SubmitNodeCollector.CustomGeometryRenderer customRenderer, @Local(argsOnly = true) MapRenderState renderState, @Local(argsOnly = true) int light) {
*/
    //?}
    //? if =1.21.4 {
/*    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setUv(FF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;addVertex(Lorg/joml/Matrix4f;FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0), to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setLight(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 3)))
    private VertexConsumer drawAtlasTexture(VertexConsumer instance, float u, float v, @Local(argsOnly = true) MapRenderState renderState) {
*/
    //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        final IMapRenderState immediatelyFast$renderState = (IMapRenderState) renderState;
*/
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        if (immediatelyFast$renderState.immediatelyFast$getAtlasTexture() != null && immediatelyFast$renderState.immediatelyFast$getAtlasTexture().getTextureId().equals(renderState.texture)) {
            final float u1 = (float) immediatelyFast$renderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
            final float u2 = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
            final float v1 = (float) immediatelyFast$renderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            final float v2 = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
*/
        //?}
            //? if >=1.21.11 {
            return (matrix, vertexConsumer) -> {
            //?}
                //? if >=26.2 {
                vertexConsumer.addVertex(matrix, 0F, MAP_SIZE, -0.01F).setColor(-1).setUv(u0, v1).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, MAP_SIZE, MAP_SIZE, -0.01F).setColor(-1).setUv(u1, v1).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, MAP_SIZE, 0F, -0.01F).setColor(-1).setUv(u1, v0).setLight(lightCoords);
                vertexConsumer.addVertex(matrix, 0F, 0F, -0.01F).setColor(-1).setUv(u0, v0).setLight(lightCoords);
                //?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                vertexConsumer.addVertex(matrix, 0F, MAP_SIZE, -0.01F).setColor(-1).setUv(u1, v2).setLight(light);
                vertexConsumer.addVertex(matrix, MAP_SIZE, MAP_SIZE, -0.01F).setColor(-1).setUv(u2, v2).setLight(light);
                vertexConsumer.addVertex(matrix, MAP_SIZE, 0F, -0.01F).setColor(-1).setUv(u2, v1).setLight(light);
                vertexConsumer.addVertex(matrix, 0F, 0F, -0.01F).setColor(-1).setUv(u1, v1).setLight(light);
*/
                //?}
            //? if =1.21.10 {
/*            return (entry, vertexConsumer) -> {
                vertexConsumer.addVertex(entry, 0F, MAP_SIZE, -0.01F).setColor(-1).setUv(u1, v2).setLight(light);
                vertexConsumer.addVertex(entry, MAP_SIZE, MAP_SIZE, -0.01F).setColor(-1).setUv(u2, v2).setLight(light);
                vertexConsumer.addVertex(entry, MAP_SIZE, 0F, -0.01F).setColor(-1).setUv(u2, v1).setLight(light);
                vertexConsumer.addVertex(entry, 0F, 0F, -0.01F).setColor(-1).setUv(u1, v1).setLight(light);
*/
            //?}
            //? if >=1.21.10 {
            };
        } else {
            //?}
            //? if >=1.21.11 {
            return customGeometryRenderer;
            //?}
            //? if =1.21.10 {
/*            return customRenderer;
*/
            //?}
        //? if =1.21.4 {
/*        if (immediatelyFast$renderState.immediatelyFast$getAtlasTexture() != null && immediatelyFast$renderState.immediatelyFast$getAtlasTexture().getIdentifier().equals(renderState.texture)) {
            if (u == 0 && v == 1) {
                u = (float) immediatelyFast$renderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
                v = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
            } else if (u == 1 && v == 1) {
                u = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
                v = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasY() + MAP_SIZE) / ATLAS_SIZE;
            } else if (u == 1 && v == 0) {
                u = (float) (immediatelyFast$renderState.immediatelyFast$getAtlasX() + MAP_SIZE) / ATLAS_SIZE;
                v = (float) immediatelyFast$renderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            } else if (u == 0 && v == 0) {
                u = (float) immediatelyFast$renderState.immediatelyFast$getAtlasX() / ATLAS_SIZE;
                v = (float) immediatelyFast$renderState.immediatelyFast$getAtlasY() / ATLAS_SIZE;
            }
*/
        //?}
    //? if <=1.21.1 {
    /*@Unique
    private final Int2IntMap immediatelyFast$mapIdToAtlasMapping = new Int2IntOpenHashMap();

    @Inject(method = "resetData", at = @At("RETURN"))
    private void clearMapAtlasTextures(final CallbackInfo ci) {
        for (MapAtlasTexture texture : this.immediatelyFast$mapAtlasTextures.values()) {
            texture.close();

    *///?}
        }
        //? if =1.21.4 {
/*        return instance.setUv(u, v);
*/
        //?}
//? if <=1.21.1 {

        /*this.immediatelyFast$mapAtlasTextures.clear();
        this.immediatelyFast$mapIdToAtlasMapping.clear();

*///?}
    }

    //? if >=1.21.4 {
    @Inject(method = "extractRenderState", at = @At("RETURN"))
    //?}
    //? if >=26.2 {
    private void setAtlasParameters(final MapId mapId, final MapItemSavedData mapData, final MapRenderState mapRenderState, final CallbackInfo ci) {
        final IMapRenderState immediatelyFast$mapRenderState = (IMapRenderState) mapRenderState;
        final int location = ((IMapTextureManager) this.mapTextureManager).immediatelyFast$getAtlasLocation(mapId.id());
        if (location != -1) {
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasTextureId(((IMapTextureManager) this.mapTextureManager).immediatelyFast$getAtlas(MapAtlas.getAtlasIdFromLocation(location)).getTextureId());
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasX(MapAtlas.getAtlasXFromLocation(location) * MAP_SIZE);
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasY(MapAtlas.getAtlasYFromLocation(location) * MAP_SIZE);
            mapRenderState.texture = immediatelyFast$mapRenderState.immediatelyFast$getAtlasTextureId();
        } else {
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasTextureId(null);
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasX(0);
            immediatelyFast$mapRenderState.immediatelyFast$setAtlasY(0);
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    private void initAtlasParameters(MapId mapId, MapItemSavedData mapState, MapRenderState renderState, CallbackInfo ci) {
        final int packedLocation = ((IMapTextureManager) this.mapTextureManager).immediatelyFast$getAtlasMapping(mapId.id());
        if (packedLocation == -1) {
            ImmediatelyFast.LOGGER.warn("Map " + mapId.id() + " is not in an atlas");
            // Leave atlasTexture null to indicate that this map is not in an atlas, and it should use the vanilla system instead
            return;
        }
*/
    //?}
        //? if =1.21.4 {
/*        final IMapRenderState immediatelyFast$renderState = (IMapRenderState) renderState;
*/
        //?}
    //? if <=1.21.1 {
    /*@Inject(method = "getOrCreateMapInstance", at = @At("HEAD"))

    *///?}
    //? if =1.21.1 {
    /*private void createMapAtlasTexture(MapId mapId, MapItemSavedData state, CallbackInfoReturnable<MapRenderer.MapInstance> cir) {
        this.immediatelyFast$mapIdToAtlasMapping.computeIfAbsent(mapId.id(), k -> {

    *///?}
    //? if =1.20.4 {
/*    private void createMapAtlasTexture(int id, MapItemSavedData state, CallbackInfoReturnable<MapRenderer.MapInstance> cir) {
        this.immediatelyFast$mapIdToAtlasMapping.computeIfAbsent(id, k -> {
*/
    //?}
            //? if <=1.21.1 {
            /*for (MapAtlasTexture atlasTexture : this.immediatelyFast$mapAtlasTextures.values()) {
                final int location = atlasTexture.getNextMapLocation();
                if (location != -1) {
                    return location;
                }
            }

            *///?}

        //? if (>=1.21.10 && <=26.1.2) {
/*        final IMapRenderState immediatelyFast$renderState = (IMapRenderState) renderState;
*/
        //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        immediatelyFast$renderState.immediatelyFast$setAtlasX(((packedLocation >> 8) & 0xFF) * MAP_SIZE);
        immediatelyFast$renderState.immediatelyFast$setAtlasY((packedLocation & 0xFF) * MAP_SIZE);
        immediatelyFast$renderState.immediatelyFast$setAtlasTexture(((IMapTextureManager) this.mapTextureManager).immediatelyFast$getMapAtlasTexture(packedLocation >> 16));
        if (immediatelyFast$renderState.immediatelyFast$getAtlasTexture() == null) {
            throw new IllegalStateException("getMapAtlasTexture returned null for packedLocation " + packedLocation + " (map " + mapId.id() + ")");
*/
        //?}
        //? if >=1.21.4 {
        }
        //?}
            //? if <=1.21.1 {
            /*final MapAtlasTexture atlasTexture = new MapAtlasTexture(this.immediatelyFast$mapAtlasTextures.size());
            this.immediatelyFast$mapAtlasTextures.put(atlasTexture.getId(), atlasTexture);
            return atlasTexture.getNextMapLocation();
        });
    }

    @Override
    public MapAtlasTexture immediatelyFast$getMapAtlasTexture(int id) {
        return this.immediatelyFast$mapAtlasTextures.get(id);
    }

    @Override
    public int immediatelyFast$getAtlasMapping(int mapId) {
        return this.immediatelyFast$mapIdToAtlasMapping.getOrDefault(mapId, -1);

            *///?}
    }

}
