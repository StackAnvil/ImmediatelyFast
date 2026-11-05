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

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.resources.MapTextureManager;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
//?}
//? if >=26.2 {
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
*/
//?}
//? if >=1.21.4 {
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//?}
//? if >=1.21.10 {
import java.util.Collection;

//?}
//? if >=1.21.4 {
@Mixin(MapTextureManager.class)
public abstract class MixinMapTextureManager implements IMapTextureManager {

    @Unique
//?}
    //? if >=26.2 {
    private final Int2ObjectMap<MapAtlas> immediatelyFast$atlases = new Int2ObjectOpenHashMap<>();
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    private final Int2ObjectMap<MapAtlasTexture> immediatelyFast$mapAtlasTextures = new Int2ObjectOpenHashMap<>();
*/
    //?}
//? if >=1.21.4 {

    @Unique
//?}
    //? if >=26.2 {
    private final Int2IntMap immediatelyFast$mapIdToAtlasLocation = new Int2IntOpenHashMap();
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    private final Int2IntMap immediatelyFast$mapIdToAtlasMapping = new Int2IntOpenHashMap();
*/
    //?}
//? if >=1.21.4 {

    @Inject(method = "resetData", at = @At("RETURN"))
//?}
    //? if >=26.2 {
    private void resetAtlases(final CallbackInfo ci) {
        for (MapAtlas atlas : this.immediatelyFast$atlases.values()) {
            atlas.close();
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    private void clearMapAtlas(final CallbackInfo ci) {
        for (MapAtlasTexture texture : this.immediatelyFast$mapAtlasTextures.values()) {
            texture.close();
*/
    //?}
        //? if >=1.21.4 {
        }
        //?}
        //? if >=26.2 {
        this.immediatelyFast$atlases.clear();
        this.immediatelyFast$mapIdToAtlasLocation.clear();
        //?}
//? if (>=1.21.4 && <=26.1.2) {
/*
        this.immediatelyFast$mapAtlasTextures.clear();
        this.immediatelyFast$mapIdToAtlasMapping.clear();
*/
//?}
    //? if >=1.21.4 {
    }

    @Inject(method = "getOrCreateMapInstance", at = @At("HEAD"))
    //?}
    //? if >=26.2 {
    private void ensureHasAtlasLocation(final MapId id, final MapItemSavedData data, final CallbackInfoReturnable<?> cir) {
        if (ImmediatelyFast.runtimeConfig.map_atlas_generation) {
            this.immediatelyFast$mapIdToAtlasLocation.computeIfAbsent(id.id(), _ -> {
                for (MapAtlas atlas : this.immediatelyFast$atlases.values()) {
                    final int location = atlas.getNextLocation();
                    if (location != -1) {
                        return location;
                    }
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private void createMapAtlasTexture(MapId mapId, MapItemSavedData data, CallbackInfoReturnable<MapTextureManager.MapInstance> cir) {
*/
    //?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    private void createMapAtlasTexture(MapId mapId, MapItemSavedData state, CallbackInfoReturnable<MapTextureManager.MapInstance> cir) {
*/
    //?}
        //? if (>=1.21.4 && <=26.1.2) {
/*        this.immediatelyFast$mapIdToAtlasMapping.computeIfAbsent(mapId.id(), k -> {
            for (MapAtlasTexture atlasTexture : this.immediatelyFast$mapAtlasTextures.values()) {
                final int location = atlasTexture.getNextMapLocation();
                if (location != -1) {
                    return location;
*/
        //?}
                //? if >=1.21.4 {
                }
                //?}
            //? if (>=1.21.4 && <=26.1.2) {
/*            }
*/
            //?}

                //? if >=26.2 {
                final MapAtlas atlas = new MapAtlas(this.immediatelyFast$atlases.size());
                this.immediatelyFast$atlases.put(atlas.getId(), atlas);
                return atlas.getNextLocation();
            });
        }
                //?}
            //? if (>=1.21.4 && <=26.1.2) {
/*            final MapAtlasTexture atlasTexture = new MapAtlasTexture(this.immediatelyFast$mapAtlasTextures.size());
            this.immediatelyFast$mapAtlasTextures.put(atlasTexture.getId(), atlasTexture);
            return atlasTexture.getNextMapLocation();
        });
*/
            //?}
    //? if >=1.21.4 {
    }

    @Override
    //?}
    //? if >=26.2 {
    public MapAtlas immediatelyFast$getAtlas(final int id) {
        return this.immediatelyFast$atlases.get(id);
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    public MapAtlasTexture immediatelyFast$getMapAtlasTexture(final int id) {
        return this.immediatelyFast$mapAtlasTextures.get(id);
*/
    //?}
    //? if >=1.21.4 {
    }

    @Override
    //?}
    //? if >=26.2 {
    public int immediatelyFast$getAtlasLocation(final int mapId) {
        return this.immediatelyFast$mapIdToAtlasLocation.getOrDefault(mapId, -1);
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    public int immediatelyFast$getAtlasMapping(final int mapId) {
        return this.immediatelyFast$mapIdToAtlasMapping.getOrDefault(mapId, -1);
*/
    //?}
    //? if >=1.21.4 {
    }

    //?}
    //? if >=1.21.10 {
    @Override
    //?}
    //? if >=26.2 {
    public Collection<MapAtlas> immediatelyFast$getAtlases() {
        return this.immediatelyFast$atlases.values();
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    public Collection<MapAtlasTexture> immediatelyFast$getAllMapAtlasTextures() {
        return this.immediatelyFast$mapAtlasTextures.values();
*/
    //?}
    //? if >=1.21.10 {
    }

    //?}
//? if >=1.21.4 {
}
//?}
