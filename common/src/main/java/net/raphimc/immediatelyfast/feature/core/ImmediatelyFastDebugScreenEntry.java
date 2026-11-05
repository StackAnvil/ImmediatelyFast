//? if >=1.21.10 {
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
package net.raphimc.immediatelyfast.feature.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? if =1.21.10 {
/*import net.minecraft.resources.ResourceLocation;
*/
//?}
//? if >=1.21.10 {
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.raphimc.immediatelyfast.ImmediatelyFast;
//?}
//? if >=26.1.2 {
import net.raphimc.immediatelyfast.feature.batch_animated_item_updates.AnimatedItemAtlas;
//?}
//? if >=26.2 {
import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlas;
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.feature.map_atlas_generation.MapAtlasTexture;
*/
//?}
//? if >=26.1.2 {
import net.raphimc.immediatelyfast.injection.interfaces.IGuiRenderer;
//?}
//? if >=1.21.10 {
import net.raphimc.immediatelyfast.injection.interfaces.IMapTextureManager;
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.util.MathUtil;
*/
//?}
//? if >=1.21.10 {

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImmediatelyFastDebugScreenEntry implements DebugScreenEntry {

//?}
    //? if >=1.21.11 {
    public static final Identifier ENTRY_ID = Identifier.fromNamespaceAndPath("immediatelyfast", "immediatelyfast");
    private static final Identifier SECTION_ID = ENTRY_ID;
    //?}
    //? if =1.21.10 {
/*    public static final ResourceLocation ENTRY_ID = ResourceLocation.fromNamespaceAndPath("immediatelyfast", "immediatelyfast");
    private static final ResourceLocation SECTION_ID = ENTRY_ID;
*/
    //?}
//? if >=1.21.10 {

    @Override
//?}
    //? if >=1.21.11 {
    public void display(final DebugScreenDisplayer displayer, final Level level, final LevelChunk clientChunk, final LevelChunk serverChunk) {
    //?}
    //? if =1.21.10 {
/*    public void display(final DebugScreenDisplayer debugHudLines, final Level world, final LevelChunk clientChunk, final LevelChunk chunk) {
*/
    //?}
        //? if >=1.21.10 {
        final List<String> lines = new ArrayList<>();
        lines.add("ImmediatelyFast " + ImmediatelyFast.VERSION);
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        lines.add("Buffer Pool: " + ByteBufferBuilderPool.getSize() + " buffers (" + MathUtil.formatBytes(ByteBufferBuilderPool.getAllocatedBytes()) + ")");
*/
        //?}
        //? if >=1.21.10 {
        if (Minecraft.getInstance().getMapTextureManager() instanceof IMapTextureManager mapTextureManager) {
        //?}
            //? if >=26.2 {
            final Collection<MapAtlas> atlasTextures = mapTextureManager.immediatelyFast$getAtlases();
            final int totalMapCount = atlasTextures.stream().mapToInt(MapAtlas::getMapCount).sum();
            lines.add("Map Atlas: " + atlasTextures.size() + "x" + MapAtlas.ATLAS_SIZE + "x" + MapAtlas.ATLAS_SIZE + " (" + totalMapCount + " maps)");
            //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            final Collection<MapAtlasTexture> atlasTextures = mapTextureManager.immediatelyFast$getAllMapAtlasTextures();
            final int totalMapCount = atlasTextures.stream().mapToInt(MapAtlasTexture::getMapCount).sum();
            lines.add("Map Atlas: " + atlasTextures.size() + "x" + MapAtlasTexture.ATLAS_SIZE + "x" + MapAtlasTexture.ATLAS_SIZE + " (" + totalMapCount + " maps)");
*/
            //?}
        //? if =1.21.10 || >=26.1.2 {
        }
        //?}
        //? if >=26.1.2 {
        if (Minecraft.getInstance().gameRenderer.guiRenderer instanceof IGuiRenderer guiRenderer) {
            final AnimatedItemAtlas animatedItemAtlas = guiRenderer.immediatelyFast$getAnimatedItemAtlas();
            if (animatedItemAtlas != null) {
                lines.add("Animated Item Atlas: " + animatedItemAtlas.textureSize() + "x" + animatedItemAtlas.textureSize() + " (" + animatedItemAtlas.getLastUsedSlotCount() + " items)");
            } else {
                lines.add("Animated Item Atlas: Not allocated");
            }
        //?}
        //? if >=1.21.11 {
        }
        //?}
        //? if >=1.21.10 {
        if (ImmediatelyFast.signTextCache != null) {
            lines.add("Sign Text Cache: " + ImmediatelyFast.signTextCache.slotCache.size() + " entries");
        }
        //?}
        //? if >=1.21.11 {
        displayer.addToGroup(SECTION_ID, lines);
        //?}
        //? if =1.21.10 {
/*        debugHudLines.addToGroup(SECTION_ID, lines);
*/
        //?}
    //? if >=1.21.10 {
    }

    @Override
    public boolean isAllowed(final boolean reducedDebugInfo) {
        return true;
    }

}
    //?}
