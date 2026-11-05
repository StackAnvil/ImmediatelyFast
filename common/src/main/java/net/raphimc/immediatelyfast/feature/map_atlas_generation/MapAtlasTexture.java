//? if <=26.1.2 {
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
package net.raphimc.immediatelyfast.feature.map_atlas_generation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;

*///?}
//? if (>=1.21.11 && <=26.1.2) {
/*import net.minecraft.resources.Identifier;
*/
//?}
//? if <=1.21.10 {
/*import net.minecraft.resources.ResourceLocation;

*///?}
//? if (>=1.21.10 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;
*/
//?}
//? if <=26.1.2 {

/*public class MapAtlasTexture implements AutoCloseable {


*///?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    public static final int ATLAS_SIZE = ImmediatelyFast.config.map_atlas_size;
*/
    //?}
    //? if <=1.21.4 {
    /*public static final int ATLAS_SIZE = 4096;

    *///?}
    //? if <=26.1.2 {
    /*public static final int MAP_SIZE = 128;
    public static final int MAPS_PER_ATLAS = (ATLAS_SIZE / MAP_SIZE) * (ATLAS_SIZE / MAP_SIZE);

    private final int id;

    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private final Identifier textureId;
*/
    //?}
    //? if =1.21.10 {
/*    private final ResourceLocation textureId;
*/
    //?}
    //? if <=1.21.4 {
    /*private final ResourceLocation identifier;

    *///?}
    //? if <=26.1.2 {
    /*private final DynamicTexture texture;
    private int mapCount;

    public MapAtlasTexture(final int id) {
        this.id = id;

    *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.textureId = Identifier.fromNamespaceAndPath("immediatelyfast", "map_atlas/" + id);
*/
        //?}
        //? if =1.21.10 {
/*        this.textureId = ResourceLocation.fromNamespaceAndPath("immediatelyfast", "map_atlas/" + id);
*/
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        this.texture = new DynamicTexture("ImmediatelyFast Map Atlas", ATLAS_SIZE, ATLAS_SIZE, true);
        Minecraft.getInstance().getTextureManager().register(this.textureId, this.texture);
*/
        //?}

        //? if (>=1.21.1 && <=1.21.4) {
        /*this.identifier = ResourceLocation.fromNamespaceAndPath("immediatelyfast", "map_atlas/" + id);

        *///?}
        //? if =1.20.4 {
/*        this.identifier = new ResourceLocation("immediatelyfast", "map_atlas/" + id);
*/
        //?}
        //? if <=1.21.4 {
        /*this.texture = new DynamicTexture(ATLAS_SIZE, ATLAS_SIZE, true);
        Minecraft.getInstance().getTextureManager().register(this.identifier, this.texture);

        *///?}
    //? if <=26.1.2 {
    /*}

    public int getNextMapLocation() {
        if (this.mapCount >= MAPS_PER_ATLAS) {
            return -1;
        }

        final byte atlasX = (byte) (this.mapCount % (ATLAS_SIZE / MAP_SIZE));
        final byte atlasY = (byte) (this.mapCount / (ATLAS_SIZE / MAP_SIZE));
        this.mapCount++;

        return (this.id << 16) | (atlasX << 8) | atlasY;
    }

    public int getId() {
        return this.id;
    }


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public Identifier getTextureId() {
*/
    //?}
    //? if =1.21.10 {
/*    public ResourceLocation getTextureId() {
*/
    //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        return this.textureId;
*/
        //?}
    //? if <=1.21.4 {
    /*public ResourceLocation getIdentifier() {
        return this.identifier;

    *///?}
    //? if <=26.1.2 {
    /*}

    public DynamicTexture getTexture() {
        return this.texture;
    }


    *///?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    public int getMapCount() {
        return this.mapCount;
    }

*/
    //?}
    //? if <=26.1.2 {
    /*@Override
    public void close() {

    *///?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        Minecraft.getInstance().getTextureManager().release(this.textureId);
*/
        //?}
        //? if <=1.21.4 {
        /*this.texture.close();
        Minecraft.getInstance().getTextureManager().release(this.identifier);

        *///?}
    //? if <=26.1.2 {
    /*}

}

    *///?}
