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
package net.raphimc.immediatelyfast.injection.interfaces;

//?}
//? if >=26.2 {
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
*/
//?}
//? if >=1.21.10 {

import java.util.Collection;
//?}
//? if >=1.21.4 {

public interface IMapTextureManager {

//?}
    //? if >=26.2 {
    MapAtlas immediatelyFast$getAtlas(final int id);
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    MapAtlasTexture immediatelyFast$getMapAtlasTexture(final int id);
*/
    //?}

    //? if >=26.2 {
    int immediatelyFast$getAtlasLocation(final int mapId);
    //?}
    //? if (>=1.21.4 && <=26.1.2) {
/*    int immediatelyFast$getAtlasMapping(final int mapId);
*/
    //?}

    //? if >=26.2 {
    Collection<MapAtlas> immediatelyFast$getAtlases();
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    Collection<MapAtlasTexture> immediatelyFast$getAllMapAtlasTextures();
*/
    //?}

//? if >=1.21.4 {
}
//?}
